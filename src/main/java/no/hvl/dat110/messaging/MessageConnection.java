package no.hvl.dat110.messaging;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import no.hvl.dat110.TODO;


public class MessageConnection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection
	
	public MessageConnection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream (socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {

		byte[] data;
		
		// TODO - START
		// encapsulate the data contained in the Message and write to the output stream
		try {
			
			byte[] encMsg = MessageUtils.encapsulate(message);
			outStream.write(encMsg);
			outStream.flush();
			
		} catch (IOException ex) {
			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}

	}

	public Message receive() {

		Message message = null;
		byte[] data = new byte[128];
		
		// TODO - START
		// read a segment from the input stream and decapsulate data into a Message
		
		try {
			
			inStream.readFully(data);
			message = MessageUtils.decapsulate(data);
			
		} catch (IOException ex) {
			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
		
		return message;
		
	}

	// close the connection by closing streams and the underlying socket	
	public void close() {

		try {
			
			outStream.close();
			inStream.close();

			socket.close();
			
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}