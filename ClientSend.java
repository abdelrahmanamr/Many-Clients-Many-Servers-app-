package ClientServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class ClientSend extends Thread{
	boolean request;
	String Clientname;
	DataOutputStream outToServer;
	String sentence;
	Socket clientSocket;
	public ClientSend(String Clientname,DataOutputStream outToServer,Socket clientSocket){
		this.request=true;
		this.Clientname = Clientname;
		this.outToServer= outToServer;
		this.clientSocket = clientSocket;
	}
public void run(){
	while(true){
        BufferedReader inFromUser = 
          new BufferedReader(new InputStreamReader(System.in)); 
        if(request){
	         try {
				outToServer.writeBytes(Clientname+"-0"+'\n');
			} catch (IOException e) {
				e.printStackTrace();
			}
	         System.out.println("hello");
	         request=false;
	        }
        else{
              try {
				sentence = inFromUser.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			} 
              try {
            	System.out.println("Please Enter the TTL Value");
            	Scanner sc = new Scanner (System.in);
            	int TTL = sc.nextInt();
            	while(TTL<2){
            	System.out.println("Error the TTL should be atleast 2");
            	TTL = sc.nextInt();
            	}
				outToServer.writeBytes(Clientname+"-"+sentence+"-"+TTL+'\n');
			} catch (IOException e) {
				e.printStackTrace();
			} 
              System.out.println(Clientname+"-"+sentence); 
              if(sentence.toUpperCase().equals("BYE")){
              try {
				clientSocket.close();
			} catch (IOException e) {
				break;
			} 
              
              }
                         
          } 
        //break;
    	}
}
}
