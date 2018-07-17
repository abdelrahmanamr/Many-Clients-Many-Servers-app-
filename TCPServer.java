package ClientServer;

import java.io.*; 
import java.net.*; 
import java.util.ArrayList;

class TCPServer { 
	final static String name = "Server1"; 
	static Socket connectionSocket;
 static ArrayList<ClientInfo>clients =new ArrayList<ClientInfo>();
  public static void main(String argv[]) throws Exception 
    { 
      ServerSocket welcomeSocket = new ServerSocket(6789); 
      Socket clientSocket =
      		new Socket("abdelrahmans-MacBook-Air.local", 6790);
      connectToServer(clientSocket);
      
      
      while(true) { 
    	  TCPServerThread t1 = new TCPServerThread(clientSocket,name);
          t1.start();
           Socket connectionSocket = welcomeSocket.accept(); 
           TCPServerThread t =  new TCPServerThread(connectionSocket,name);
           t.start();
              } 
       
      }
  
  public static void connectToServer(Socket clientServer) throws IOException{
	  DataOutputStream  outToMainServer = 
               new DataOutputStream(clientServer.getOutputStream()); 
      outToMainServer.writeBytes("Server1"+"-0"+ '\n'); 
     // BufferedReader inFromMainServer = new BufferedReader(new
		//	InputStreamReader(clientServer.getInputStream()));
	 // String modifiedSentence = inFromMainServer.readLine();
     // clients.add(new ClientInfo(modifiedSentence.split("-")[0], clientServer));
	 // System.out.println(modifiedSentence); 
      
    
  }
  
          } 
