package ClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceive extends Thread{
	String modifiedSentence;
	Socket clientSocket;
	public ClientReceive(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
public void run(){
	while(true)
	 {
	         
	        BufferedReader inFromServer;
			try {
				inFromServer = new BufferedReader(new
				InputStreamReader(clientSocket.getInputStream()));
				modifiedSentence = inFromServer.readLine();
				System.out.println(modifiedSentence); 	 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	                 			                         
	 }
}
}
