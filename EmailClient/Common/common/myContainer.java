package common;

import java.io.Serializable;

@SuppressWarnings("serial")
public class myContainer implements Serializable {
	private Serializable payload;
	private MessageTypes msgType;
	
	public void setPayload(Serializable payload) {
		this.payload = payload;
	}
	public Serializable getPayload() {
		return payload;
	}
	public void setMsgType(MessageTypes msgType) {
		this.msgType = msgType;
	}
	public MessageTypes getMsgType() {
		return msgType;
	}
}

