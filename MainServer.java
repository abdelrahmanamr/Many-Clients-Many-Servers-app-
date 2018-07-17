package ClientServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {
	final static String name = "MainServer"; 
	static Socket connectionSocket;
	 static ArrayList<ClientInfo>clients =new ArrayList<ClientInfo>();
	  public static void main(String argv[]) throws Exception 
	    { 
	      ServerSocket welcomeSocket = new ServerSocket(6790); 
	  
	      while(true) { 
	    	    Socket connectionSocket = welcomeSocket.accept(); 
	           TCPServerThread t =  new TCPServerThread(connectionSocket,name);
	           t.start();
	              } 
	      }
}
