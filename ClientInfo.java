package ClientServer;

import java.io.BufferedReader;
import java.net.Socket;

public class ClientInfo {
String name;
Socket socket;
//BufferedReader inFromClient;
//BufferedReader outToClient;

public ClientInfo(String name,Socket socket){
	this.name=name;
	this.socket=socket;
	//this.inFromClient = inFromClient;
	//this.outToClient = outToClient;
}
}
