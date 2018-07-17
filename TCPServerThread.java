package ClientServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class TCPServerThread extends Thread {
	Socket socket;
	String serverName;
	String senderName;
	String recepientName;
	String message;
	int TTL;

	public TCPServerThread(Socket s, String serverName) {
		this.socket = s;
		this.serverName = serverName;
	}

	public void run() {
		String clientSentence;
		String capitalizedSentence;
		try {
			while (true) {
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));

				clientSentence = inFromClient.readLine();
				if (clientSentence != null) {// to connect to one of the servers
//					if(clientSentence.split("-")[1].toLowerCase().equals("bye")){
//						for(int i =0;i<TCPServer.clients.size();i++ ){
//							if(TCPServer.clients.get(i).name.equals(clientSentence.split("-")[0])){
//								TCPServer.clients.get(i).socket.close();
//								TCPServer.clients.remove(i);
//								break;
//							}
//							
//						}
//						for(int i =0;i<MainServer.clients.size();i++ ){
//							if(MainServer.clients.get(i).name.equals(clientSentence.split("-")[0])){
//								MainServer.clients.get(i).socket.close();
//								MainServer.clients.remove(i);
//								break;
//							}
//							
//						}
//					}
//					else{
					if (clientSentence.split("-")[1].equals("0")) {
						if (clientSentence.split("-")[0].equals("Server1")) {
							DataOutputStream outToServer = new DataOutputStream(
									this.socket.getOutputStream());
							outToServer.writeBytes("MainServer" + "-0" + '\n');
							MainServer.clients.add(new ClientInfo(
									clientSentence.split("-")[0], this.socket));
							System.out
									.println("Server1 Connected To the MainServer");
						} else {
							if (this.serverName.equals("MainServer")) {
								MainServer.clients.add(new ClientInfo(
										clientSentence.split("-")[0],
										this.socket));
								System.out
										.println("Connection to server 2 done Sucessfully");
							} else {
								TCPServer.clients.add(new ClientInfo(
										clientSentence.split("-")[0],
										this.socket));
								System.out
										.println("Connection to server 1 done Sucessfully");
							}
						}
						// --------------------------------------------------------------------------------------------------------
					} else {
						// to get the members list in every Server
						if (clientSentence.split("-")[1].toLowerCase().equals(
								"getmemberslist")) {
							if (this.serverName.equals("MainServer")) {
								if (MainServer.clients.size() != 0) {
									String clientsInMainServer = "";
									Socket senderSocket = new Socket();
									Socket serverSocket = MainServer.clients.get(0).socket;
									boolean clientFound = false;
									for (int i = 1; i < MainServer.clients
											.size(); i++) {
										if (MainServer.clients.get(i).name
												.equals(clientSentence
														.split("-")[0])) {
											senderSocket = MainServer.clients
													.get(i).socket;
											clientFound = true;
										}
										if (!(MainServer.clients.get(i).name
												.equals("Server1"))) {
											clientsInMainServer +=  MainServer.clients.get(i).name;
											if(MainServer.clients.size()>(i+1)){
												clientsInMainServer +=",";
											}
										} 

									}
									if (clientFound) {
										DataOutputStream outtoClient = new DataOutputStream(
												senderSocket.getOutputStream());
										outtoClient
												.writeBytes("From MainServer:"
														+ clientsInMainServer
														+ '\n');
									} else {
										DataOutputStream outToServer = new DataOutputStream(
												serverSocket.getOutputStream());
										outToServer.writeBytes("MainServer"
												+ "-" + clientsInMainServer
												+ "-"
												+ clientSentence.split("-")[0]
												+ "-" + "2" + '\n');
									}
									if (clientSentence.split("-")[2]
											.toLowerCase().equals("true")) {
										DataOutputStream outToServer = new DataOutputStream(
												serverSocket.getOutputStream());
										outToServer.writeBytes(clientSentence
												.split("-")[0]
												+ "-"
												+ clientSentence.split("-")[1]
												+ "-" + "false" + '\n');
									}
									// System.out.println(MainServer.clients.get(i).name);

								}
							} else {
								if (TCPServer.clients.size() != 0) {
									String clientsInTCPServer = "";
									Socket senderSocket = new Socket();
									Socket serverSocket = TCPServer.clients.get(0).socket;
									boolean clientFound = false;
									for (int i = 1; i < TCPServer.clients
											.size(); i++) {
										if (TCPServer.clients.get(i).name
												.equals(clientSentence
														.split("-")[0])) {
											senderSocket = TCPServer.clients
													.get(i).socket;
											clientFound = true;
										}
										if (TCPServer.clients.get(i).name
												.equals("MainServer")) {
											serverSocket = TCPServer.clients
													.get(i).socket;
										} else {
											clientsInTCPServer += TCPServer.clients.get(i).name;
											if((i+1)<TCPServer.clients.size()){
												clientsInTCPServer += ",";
											}
										}

									}
									if (clientFound) {
										DataOutputStream outtoClient = new DataOutputStream(
												senderSocket.getOutputStream());
										outtoClient.writeBytes("From Server1:"
												+ clientsInTCPServer + '\n');
									} else {
										DataOutputStream outToServer = new DataOutputStream(
												serverSocket.getOutputStream());
										outToServer.writeBytes("Server1" + "-"
												+ clientsInTCPServer + "-"
												+ clientSentence.split("-")[0]
												+ "-" + "2" + '\n');
										;
									}
									if (clientSentence.split("-")[2]
											.toLowerCase().equals("true")) {
										DataOutputStream outToServer = new DataOutputStream(
												serverSocket.getOutputStream());
										outToServer.writeBytes(clientSentence
												.split("-")[0]
												+ "-"
												+ clientSentence.split("-")[1]
												+ "-" + "false" + '\n');
									}
								}
							}
						}
						// -----------------------------------------------------------------------------------------------------------------
						else {
							// code for sending regular messages till the end of
							// the page
							senderName = clientSentence.split("-")[0];
							recepientName = clientSentence.split("-")[2];
							message = clientSentence.split("-")[1];
					     TTL = Integer
									.parseInt(clientSentence.split("-")[3]);
						TTL--;
							if (TTL > 0) {
								boolean flag = false;
								for (int i = 0; i < TCPServer.clients.size(); i++) {
									if (TCPServer.clients.get(i).name
											.toUpperCase()
											.equals(recepientName.toUpperCase())) {
										DataOutputStream outToClient = new DataOutputStream(
												TCPServer.clients.get(i).socket
														.getOutputStream());
										outToClient.writeBytes("From "
												+ senderName + ':' + message
												+ '\n');
										flag = true;
										break;
									}
								}
								for (int i = 0; i < MainServer.clients.size(); i++) {
									if (MainServer.clients.get(i).name
											.toUpperCase()
											.equals(recepientName.toUpperCase())) {
										DataOutputStream outToClient = new DataOutputStream(
												MainServer.clients.get(i).socket
														.getOutputStream());
										outToClient.writeBytes("From "
												+ senderName + ':' + message
												+ '\n');
										flag = true;
										break;
									}
								}
								if (!flag) {

									if (this.serverName.equals("MainServer")) {
										for (int i = 0; i < MainServer.clients
												.size(); i++) {
											if (MainServer.clients.get(i).name
													.equals("Server1")) {
												DataOutputStream outToServer1 = new DataOutputStream(
														MainServer.clients
																.get(i).socket
																.getOutputStream());

												outToServer1
														.writeBytes(senderName
																+ "-" + message
																+ "-"
																+ recepientName
																+ "-" + TTL
																+ '\n');
												break;
											}
										}
									} else {
										for (int i = 0; i < TCPServer.clients
												.size(); i++) {
											if (TCPServer.clients.get(i).name
													.equals("MainServer")) {
												DataOutputStream outToServer1 = new DataOutputStream(
														TCPServer.clients
																.get(i).socket
																.getOutputStream());

												outToServer1
														.writeBytes(senderName
																+ "-" + message
																+ "-"
																+ recepientName
																+ "-" + TTL
																+ '\n');
												break;
											}
										}
									}
								}

							} else {
								Socket currentUser = new Socket();
								if(this.senderName.equals("Server1")){
									for(int i = 0;i<TCPServer.clients.size();i++){
										if(senderName.equals(TCPServer.clients.get(i).name)){
											currentUser=TCPServer.clients.get(i).socket;
											break;
										}
									}
								}
								else{
									for(int i = 0;i<MainServer.clients.size();i++){
										if(senderName.equals(MainServer.clients.get(i).name)){
											currentUser=MainServer.clients.get(i).socket;
											break;
										}
									}
								}
								if(!(currentUser.isConnected()) ){
									if(this.serverName.equals("Server1")){
										DataOutputStream outToServer1 = new DataOutputStream(
												TCPServer.clients
														.get(0).socket
														.getOutputStream());

										outToServer1
												.writeBytes(senderName
														+ "-" + message
														+ "-"
														+ recepientName
														+ "-" + 0
														+ '\n');
									}
									else{
										DataOutputStream outToServer1 = new DataOutputStream(
												MainServer.clients
														.get(0).socket
														.getOutputStream());

										outToServer1
												.writeBytes(senderName
														+ "-" + message
														+ "-"
														+ recepientName
														+ "-" + 0
														+ '\n');
									}
								}
								else{
								DataOutputStream outToClient = new DataOutputStream(
										currentUser.getOutputStream());
								outToClient
										.writeBytes("There is no such client or the TTL value is not sufficent try to increase its value to atleast 2, if it works okay otherwise la ykalef allaho nafsan ela wes3aha" + '\n');
								clientSentence=null;
							}
							}
						}
					}
				}
			}
		
		}catch (IOException e) {
			e.printStackTrace();
		}

	}
}
