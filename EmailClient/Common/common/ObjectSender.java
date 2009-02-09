package common;

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
}
