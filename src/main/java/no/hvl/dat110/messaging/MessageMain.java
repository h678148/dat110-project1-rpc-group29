package no.hvl.dat110.messaging;

import javax.swing.JOptionPane;

public class MessageMain {
	
	public static void main(String[] args) {
		
		Thread serverThread = new Thread() {
			
			public void run() {
					
				MessagingServer server = new MessagingServer(MessageUtils.MESSAGINGPORT);
				System.out.println("Server venter på tilkobling...");
				
				MessageConnection msgConnection = server.accept();
				System.out.println("Klient tilkoblet!");
				
				Message msgReceived = msgConnection.receive();
				System.out.println("Melding mottatt: " + new String(msgReceived.getData()));
				
				msgConnection.close();
				server.stop();
			}
		};
		
		
		serverThread.start();
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		
		
		
		Thread klientThread = new Thread() {
			
			public void run() {
					
				String msgSend = JOptionPane.showInputDialog("What do you want to send?");
				byte[] data = msgSend.getBytes();
				Message message = new Message(data);
				
				MessagingClient klient = new MessagingClient("localhost", MessageUtils.MESSAGINGPORT);
				MessageConnection msgConnection = klient.connect();
				msgConnection.send(message);
				
				System.out.println("Melding sendt: " + msgSend);
				
				msgConnection.close();
				
			}
		};
		
		klientThread.start();
		
	}

}
