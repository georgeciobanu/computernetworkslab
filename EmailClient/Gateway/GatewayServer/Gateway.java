package GatewayServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import SMTPBlackBox.*;
import IMAPmethods.*;

import common.*;

public class Gateway {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Create a ServerSocket to listen on
		// Assign it to a client when it connects
		// Wait for commands
		// After each command arrives, parse it and send a reply

		final int SERVER_PORT = 8123;

		ServerSocket server = null;
		Socket client = null;

		try {
			server = new ServerSocket(SERVER_PORT);
			System.out.println("Listening on port " + 
								String.valueOf(SERVER_PORT) + 
								" Waiting for connections...");
			client = server.accept();
			System.out.println("Client connected!");
			

		} catch (Exception e) {
			// do nothing for now
		}

		while (true) {

			System.out.println("Waiting for commands...");
			myContainer container = ObjectSender.WaitForObject(client);

			if (container.getMsgType() == MessageTypes.LOGIN_INFO) {
				System.out.println("LOGIN INFO received");
				// TODO: if (!loggedIn)
				myLoginInfo loginInfo = (myLoginInfo) container.getPayload();
				myLoginResponse response = null;
				try {
					System.out.println("Loging in to IMAP Server...");
					IMAPMethods.OpenConnection(loginInfo.getIMAPHost());
					IMAPMethods.Login(loginInfo.getIMAPUsername(), loginInfo
							.getIMAPPassword());

					// if we got here then everything is OK

					System.out.println("Logged in, sending confirmation to client");
					// Confirm to the client
					response = new myLoginResponse(LoginStatus.OK);
					ObjectSender.SendObject(response,
							MessageTypes.LOGIN_RESPONSE, client);
					System.out.println("Confirmation sent.");

				} catch (Exception e) {					
					response = new myLoginResponse(LoginStatus.INVALID_IMAP);
					ObjectSender.SendObject(response,
							MessageTypes.LOGIN_RESPONSE, client);
					System.out.println("Login NOT successful.");
					//
				}
			} else if (container.getMsgType() == MessageTypes.CLIENT_COMMAND) {
				System.out.println("Command received.");
				String[] command = (String[]) container.getPayload();

				if (command.length > 0)
					if (command[0].equals("GET_FOLDER_LIST")) {
						try{					
							Folder [] folders = IMAPMethods.ListofFolders();
						 	ObjectSender.SendObject(folders, MessageTypes.FOLDER_LIST, client);
						 	System.out.println("Sent list of folders to client.");
						}catch(Exception e){
							// ok, i know
						}
						
					} else if (command[0].equals("GET_EMAIL_LIST")) {
						if (command.length > 1){
							try{
								ObjectSender.SendObject(
										IMAPMethods.LisOfEmails(command[1]), 
										MessageTypes.MESSAGE_LIST, 
										client);
							}catch (Exception e){
								//
							}							
						}					
					} else if (command[0].compareTo("CREATE_FOLDER") == 0) {

						if (command.length > 1){
							try{								
								IMAPMethods.CreateFolder(command[1]);
								ObjectSender.SendObject(null, 
										MessageTypes.CONFIRMATION_OK, 
										client);
							}catch (Exception e){
								//
							}
						}

					} else if (command[0].compareTo("RENAME_FOLDER") == 0) {
						

					} else if (command[0].compareTo("DELETE_FOLDER") == 0) {
						
						if (command.length > 1){
							try{
								
								IMAPMethods.DeleteFolder(command[1]);
								ObjectSender.SendObject(
										IMAPMethods.getTHEemail(command[1]), 
										MessageTypes.MESSAGE, 
										client);
							}catch (Exception e){
								//
							}			
						}
					} else if (command[0].compareTo("GET_EMAIL") == 0) {
						if (command.length > 1){
							try{
								ObjectSender.SendObject(
										IMAPMethods.getTHEemail(command[1]), 
										MessageTypes.MESSAGE, 
										client);
							}catch (Exception e){
								//
							}							
						}

					} else if (command[0].compareTo("SEND_EMAIL") == 0) {
						
					

					} else if (command[0].compareTo("MOVE_EMAIL") == 0) {						
						/*
						if (command.length > 1){
							try{
								
								//IMAPMethods.moveEmail(command[1], command[2]);
								ObjectSender.SendObject(
										null, 
										MessageTypes.CONFIRMATION_OK, 
										client);
							}catch (Exception e){
								//
							}							
						}*/

					}

			}

		}
	}
}