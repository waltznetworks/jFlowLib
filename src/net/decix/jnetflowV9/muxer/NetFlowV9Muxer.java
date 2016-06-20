package net.decix.jnetflowV9.muxer;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.parsers.ParserConfigurationException;

import org.savarese.vserv.tcpip.IPPacket;
import org.savarese.vserv.tcpip.OctetConverter;
import org.savarese.vserv.tcpip.UDPPacket;
import org.xml.sax.SAXException;

import com.savarese.rocksaw.net.RawSocket;

import net.decix.jipfix.detector.GroupMissingDataRecordDetector;
import net.decix.jipfix.header.MessageHeader;
import net.decix.muxer.ConfigParser;
import net.decix.muxer.Pinger;
import net.decix.util.Address;
import net.decix.util.AddressPort;
import net.decix.util.HeaderBytesException;
import net.decix.util.HeaderParseException;
import net.decix.util.Utility;
import net.decix.util.UtilityException;

public class NetFlowV9Muxer implements Callable<Void> {
	private final static Logger LOGGER = Logger.getLogger(NetFlowV9Muxer.class.getName());
	
	private ConfigParser cp;
	
	public NetFlowV9Muxer(ConfigParser cp) {
		this.cp = cp;
	}
	
	@Override
	public Void call() {
		DatagramSocket dsReceive = null;
		try {

			// listen on this address and port to receive IPFIX data coming from
			// switches
			Address listenAddress = cp.getIPFIXListenAddress();
			int listenPort = cp.getIPFIXListenPort();

			// list of destinations of plain IPFIX stream
			Vector<AddressPort> plainDestinations = cp.getIPFIXPlainDestinations();
			LOGGER.log(Level.FINE, "Creating Datagram socket with listenAdress " + listenAddress.getInetAddress() + " and Port" + listenPort);
			
			dsReceive = new DatagramSocket(listenPort, listenAddress.getInetAddress());
			dsReceive.setReceiveBufferSize(26214400);
			LOGGER.log(Level.FINE, "Datagram socket for receiving created. Listening at InetAddr:" +  dsReceive.getInetAddress().toString() + " LocalSocketAddr:?");

			RawSocket socket = new RawSocket();
			socket.open(RawSocket.PF_INET, RawSocket.getProtocolByName("udp"));
			socket.setIPHeaderInclude(true);
			LOGGER.log(Level.FINE, "Raw socket for sending created.");

			DatagramPacket dp = null;
			
			GroupMissingDataRecordDetector gmdrd = new GroupMissingDataRecordDetector();
						
			while (true) {
				try {
					byte[] data = new byte[65536];
					dp = new DatagramPacket(data, data.length);
					dsReceive.receive(dp);
					
					// prepare UDP packet
					byte[] dataMuxer = null;

					if (cp.isIPFIXStartMissingDataRecordDector()) {
						// parsing is required
						MessageHeader mh = MessageHeader.parse(dp.getData());
						gmdrd.detectMissing(dp, mh);

						dataMuxer = mh.getBytes();
					} else {
						// no parsing required. So do fast and easy copying.
						dataMuxer = new byte[dp.getLength()];
						System.arraycopy(dp.getData(), 0, dataMuxer, 0, dp.getLength());
					}


					UDPPacket udp = new UDPPacket(28 + dataMuxer.length);
					udp.setIPVersion(4);
					udp.setIPHeaderLength(5);
					udp.setProtocol(IPPacket.PROTOCOL_UDP);
					udp.setTTL(5);

					udp.setUDPDataByteLength(dataMuxer.length);
					udp.setUDPPacketLength(dataMuxer.length + 8);

					udp.setDestinationPort(cp.getIPFIXListenPort());
					udp.setSourcePort(dp.getPort());

					byte[] pktData = new byte[udp.size()];

					// plain MUXER part
					for (AddressPort ap : plainDestinations) {
						udp.setDestinationAsWord(OctetConverter.octetsToInt(ap.getAddress().getBytes()));
						udp.setSourceAsWord(OctetConverter.octetsToInt(dp.getAddress().getAddress()));
						udp.setDestinationPort(ap.getPort());
						udp.getData(pktData);
						System.arraycopy(dataMuxer, 0, pktData, 28, dataMuxer.length);
						udp.setData(pktData);
						udp.computeUDPChecksum(true);
						udp.computeIPChecksum(true);
						udp.getData(pktData);
						socket.write(ap.getAddress().getInetAddress(), pktData);
					}
				} catch (HeaderParseException hpe) {
					LOGGER.log(Level.INFO, "HeaderParseException: " + Utility.dumpBytes(dp.getData()));
					hpe.printStackTrace();
					System.out.println("========================================================");
					System.out.println("HeaderParseException: *");
					System.out.println(Utility.dumpBytes(dp.getData()));
					System.out.println("========================================================");
				} catch (HeaderBytesException hbe) {
					LOGGER.log(Level.INFO, "HeaderBytesException: " + Utility.dumpBytes(dp.getData()));
					hbe.printStackTrace();
				} catch (UtilityException ue) {
					LOGGER.log(Level.INFO, "UtilityException: " + Utility.dumpBytes(dp.getData()));
					ue.printStackTrace();
				}
			}
		} catch (SocketException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (UtilityException ue) {
			ue.printStackTrace();
		} finally {
			if (dsReceive != null) dsReceive.close();
		}
		return null;
	}
	
	public static void main(String args[]) {
		String cfgPath = "/opt/jipfix-muxer/etc";
		String logPath = "/opt/jipfix-muxer/log";
		
		try {
			if (args.length == 0) {
				System.out.println("Usage: java -cp jipfix.jar net.decix.jipfix.muxer [options]\n");
				System.out.println("Options:");
				System.out.println("        -cfg: path to the jipfix.xml config file");
				System.out.println("        -log: path to log file");
				System.out.println();
				System.exit(0);
			} else {
				for (int i = 0; i < args.length; i++) {
					String str = args[i];
					if (str.equals("-cfg")) {
						if ((i + 1) < args.length) { 
							cfgPath = args[i + 1];
						}
					}
					if (str.equals("-log")) {
						if ((i + 1) < args.length) { 
							logPath = args[i + 1];
						}
					}
				}
			}
			
			FileHandler fh = new FileHandler(logPath + File.separator + "ipfix-muxer.log", 5 * 10485760, 20, true); // 20 x 50MByte
			fh.setFormatter(new SimpleFormatter());
			Logger l = Logger.getLogger("");
			l.addHandler(fh);
			l.setLevel(Level.FINEST);
			
			System.setProperty("java.net.preferIPv4Stack" , "true");
			
			LOGGER.log(Level.FINE, "Program Start");		
			
			ConfigParser cp = new ConfigParser();
			cp.loadConfig(cfgPath);
			
			NetFlowV9Muxer ipfixMuxer = new NetFlowV9Muxer(cp);
			ExecutorService executorIPFIXMuxer = Executors.newFixedThreadPool(1);
			executorIPFIXMuxer.submit(ipfixMuxer);

			LOGGER.log(Level.FINE, "IPFIX muxer startet");
			
			Pinger pinger = new Pinger(cp);
			ScheduledExecutorService executorPinger = Executors.newScheduledThreadPool(1);
			executorPinger.scheduleAtFixedRate(pinger, 10, 10, TimeUnit.SECONDS);
			LOGGER.log(Level.FINE, "Pinger service startet");
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (UtilityException ue) {
			ue.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
