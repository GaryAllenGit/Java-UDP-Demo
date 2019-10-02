import java.io.*;
import java.net.*;

class UDPClient {

	private static final int SELECTED_PORT = 6000;
	private DatagramSocket clientSocket;

	private UDPClient() {
		String stringToSend = "Hello World";

		// open the datagram socket
		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e) {
			// failed to open socket, so we might as well quit
			e.printStackTrace();
			System.exit(1);
		}

		try {
			// assume for this demo that the server is on the same machine as the client,
			//    so we need to get the ip address of 'this' machine
			// Note that if the server were remote we would need to find its ip address
			InetAddress IPAddress = InetAddress.getByName("localhost");

			// set up variables for holding the data for input/output
			byte[] sendData;
			byte[] receiveData = new byte[1024];

			// create the data we want to send to the server
			sendData = stringToSend.getBytes();

			// create a datagram packet to hold the data we want to send to the server
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, SELECTED_PORT);

			// send the datagram to the server
			System.out.println("Sending text: " + stringToSend);
			clientSocket.send(sendPacket);

			// read the reply datagram packet from the socket
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);

			// extract meaningful data from the datagram packet
			String replySentence = new String(receivePacket.getData());

			// print it
			System.out.println("Reply from Server: " + replySentence);

			// close the connection
			clientSocket.close();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static void main(String[] args) {
		new UDPClient();
	}
}