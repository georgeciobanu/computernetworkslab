package common;

import java.io.Serializable;

public class myLoginInfo implements Serializable {
	private String IMAPHost, IMAPUsername, IMAPPassword, SMTPHost, STMPUsername;
	
	public String getIMAPHost() {
		return IMAPHost;
	}

	public void setIMAPHost(String host) {
		IMAPHost = host;
	}

	public String getIMAPPassword() {
		return IMAPPassword;
	}

	public void setIMAPPassword(String password) {
		IMAPPassword = password;
	}

	public String getIMAPUsername() {
		return IMAPUsername;
	}

	public void setIMAPUsername(String username) {
		IMAPUsername = username;
	}

	public String getSMTPHost() {
		return SMTPHost;
	}

	public void setSMTPHost(String host) {
		SMTPHost = host;
	}

	public String getSTMPUsername() {
		return STMPUsername;
	}

	public void setSTMPUsername(String username) {
		STMPUsername = username;
	}

	public myLoginInfo(String host, String username, String password, String username2, String host2) {		
		IMAPHost = host;
		IMAPUsername = username;
		IMAPPassword = password;
		STMPUsername = username2;
		SMTPHost = host2;
	}
}