import java.io.*;
import java.net.*;

class UDPServer {

	private static final int SELECTED_PORT = 6000;
	private DatagramSocket serverSocket;

	private UDPServer() {
		// open the datagram socket
		try {
			serverSocket = new DatagramSocket(SELECTED_PORT);
		} catch (SocketException e) {
			// failed to open socket, so we might as well quit
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Server started and listening on port " + SELECTED_PORT + "\n");

		// loop indefinitely
		while(true) {
			// set up variables for holding the data for input/output
			byte[] receiveData = new byte[1024];
			byte[] sendData;

			// read a datagram packet from the socket
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// extract meaningful data from the received datagram packet 
			// first, the input string passed to us
			String sentence = new String(receivePacket.getData());
			System.out.println("Received from Client: " + sentence);

			// extract routing information (of the client) from the input datagram packet
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			System.out.println("Client IP: " + IPAddress + " Port: " + port);

			// create the "reply" string
			String replySentence = "You said " + sentence;

			// create a datagram packet to hold the reply
			sendData = replySentence.getBytes();
			DatagramPacket sendPacket =
					new DatagramPacket(sendData, sendData.length, IPAddress, port);

			// send the reply
			try {
				serverSocket.send(sendPacket);
				System.out.println("Replied to Client: " + replySentence + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new UDPServer();
	}
}

