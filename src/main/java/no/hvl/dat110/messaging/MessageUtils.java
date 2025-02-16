package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	public static byte[] encapsulate(Message message) {
		
		byte[] segment = new byte[128];;
		byte[] data;
		
		// TODO - START
		
		// encapulate/encode the payload data of the message and form a segment
		// according to the segment format for the messaging layer
		data = message.getData();
		
		segment[0] = (byte) data.length;
		
		for(int i = 0; i <data.length; i++) {
			segment[i+1] = data[i];
		}
		
		return segment;
		
	}

	public static Message decapsulate(byte[] segment) {

		Message message = null;
		
		// TODO - START
		// decapsulate segment and put received payload data into a message
		
		byte[] data = new byte[segment[0]];
		
		for(int i = 0; i < segment[0]; i++) {
			data[i] = segment[i+1];
		}
		
		message = new Message(data);
		
		return message;
		
	}
	
}
