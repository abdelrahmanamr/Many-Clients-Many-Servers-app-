package ClientServer;

	import java.io.*; 
import java.net.*; 
import java.util.Scanner;
	class TCPClient { 
	 
	    public static void main(String argv[]) throws Exception 
	    { 
	    	 int portNumber;
	    	 String Clientname;
	    	 System.out.println("Enter Your Name Please");
	    	 Clientname=(new Scanner(System.in)).nextLine();
	    	 System.out.println("For Connecting to Server 1 please Enter 1 and to Connect to server 2 Enter 2");
	    	 Scanner sc  = new Scanner (System.in);
	    	 int choice  = sc.nextInt();
	    	 if(choice==1)
	    	 {
	    		 portNumber=6789;// server 1
	    	 }
	    	 else
	    	 {
	    		 portNumber=6790;//main server
	    	 }
	    	 Socket clientSocket =
		        		new Socket("abdelrahmans-MacBook-Air.local", portNumber); 
	    	 DataOutputStream outToServer = 
   		          new DataOutputStream(clientSocket.getOutputStream()); 
	    	 ClientSend send = new ClientSend(Clientname, outToServer, clientSocket);
	    	 ClientReceive receive = new ClientReceive(clientSocket);
	    	 send.start();
	    	 receive.start();
	    	 //getMembersList();
	    	
	    }
	    
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
