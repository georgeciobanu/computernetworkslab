package common;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;


public class ObjectSender {

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
	
	public static myContainer WaitForObject(Socket socket){
		myContainer objResponse = null;
		
		try{
			ObjectInputStream response = new ObjectInputStream(socket.getInputStream());			
			objResponse =  (myContainer) response.readObject();					
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return objResponse;
	}
}
