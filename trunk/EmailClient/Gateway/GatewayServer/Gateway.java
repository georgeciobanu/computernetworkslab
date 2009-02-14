package GatewayServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import SMTPBlackBox.*;
import IMAPmethods.src.*;

import common.LoginStatus;
import common.MessageTypes;
import common.ObjectSender;
import common.myContainer;
import common.myLoginInfo;
import common.myLoginResponse;

public class Gateway {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Create a ServerSocket to listen on
		//Assign it to a client when it connects
		//Wait for commands
		//After each command arrives, parse it and send a reply
		
		final int SERVER_PORT = 8123;
		
		ServerSocket server = null;
		Socket client = null;
		
		
		
		try{
			server = new ServerSocket(SERVER_PORT);
			client = server.accept();
			 
		}catch (Exception e)
		{
			//do nothing for now
		}
		
		
		while (true){
			
		 myContainer container= ObjectSender.WaitForObject(client);
		 
		 
		 if (container.getMsgType() == MessageTypes.LOGIN_INFO){
			 //TODO: if (!loggedIn)
			 myLoginInfo loginInfo = (myLoginInfo) container.getPayload();
			 myLoginResponse response = null;
			 try{
				 IMAPMethods.OpenConnection(loginInfo.getIMAPHost());
				 IMAPMethods.Login(loginInfo.getIMAPUsername(), loginInfo.getIMAPPassword());
				 
				 //if we got here then everything is OK
		
				 //Confirm to the client
				 response = new myLoginResponse(LoginStatus.OK);
				 ObjectSender.SendObject(response, MessageTypes.LOGIN_RESPONSE, client);
				 
			 }catch (Exception e)
			 {
				 response = new myLoginResponse(LoginStatus.INVALID_IMAP);
				 ObjectSender.SendObject(response, MessageTypes.LOGIN_RESPONSE, client);
				 //
			 }
		 }
		 else if (container.getMsgType() == MessageTypes.FOLDER_LIST){
			 
		 }
		 else if (container.getMsgType() == MessageTypes.MESSAGE_LIST){
			 
		 }
		 
		}
		
		

	}
}
