package common;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;


public class ObjectSender {

	
	/**
	 * Wraps and object and sends it over the indicated socket. 
	 * @param payload The object to be sent.
	 * @param type The type of the object; @see MessageTypes
	 * @param Socket The socket to use when sending data
	 *   
	 */ 
	public static void SendObject(Serializable payload, MessageTypes type, Socket socket){
		myContainer container = new myContainer();
		container.setPayload(payload);
		container.setMsgType(type);
		
		try{
			ObjectOutputStream ObjectStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectStream.writeObject(container);
		} catch (Exception e)
		{
			e.printStackTrace();
		}						
	}
	
	
	public static myContainer WaitForObjectNoTimeout(Socket socket){
		myContainer objResponse = null;		
		try{			
			ObjectInputStream response = new ObjectInputStream(socket.getInputStream());			
			objResponse =  (myContainer) response.readObject();
			
			//Use an infinite timeout
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return objResponse;
	}
	
	public static myContainer WaitForObject(Socket socket){
		myContainer objResponse = null;		
		try{
			socket.setSoTimeout(10000); //We don't want to wait for more than 10 sec
			
			ObjectInputStream response = new ObjectInputStream(socket.getInputStream());			
			objResponse =  (myContainer) response.readObject();
			
			//Use an infinite timeout
			socket.setSoTimeout(0);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return objResponse;
	}
}
