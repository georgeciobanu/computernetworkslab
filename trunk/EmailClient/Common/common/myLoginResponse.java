package common;

import java.io.Serializable;

public class myLoginResponse implements Serializable {
	LoginStatus reply;

	public LoginStatus getReply() {
		return reply;
	}

	public void setReply(LoginStatus reply) {
		this.reply = reply;
	}

	public myLoginResponse(LoginStatus reply) {		
		this.reply = reply;
	}
	
	public myLoginResponse(){
		this.reply = LoginStatus.INVALID_IMAP;
	}	
}
