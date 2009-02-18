package common;

import java.io.Serializable;

public class Email implements Serializable{

	String Date = "";
	String Subject = "";
	String Body = "";
	String From = "";
	String To 	= "";
	String EmailNumber = "";
	String FolderName = "";
	private String SMTPHost, SMTPUser;
	boolean Read = false;
	boolean Answered = false; 
	boolean Flagged = false; 
	boolean Draft = false; 
	boolean Deleted = false; 
	boolean Seen = false;
	boolean hasatt = false;
	boolean hasnoatt = false;
	
	public Email(){}
	
	public Email( String date, String Sbj, String body, String frm, String to,String num,String fldName,String[] flags){ 

		// Strings 
		Date = date;
		Subject = Sbj;
		Body = body;
		From = frm;
		To 	= to;
		EmailNumber = num;
		FolderName = fldName;
		//Flags (boolean)
		Read = false;
		Answered = false; 
		Flagged = false; 
		Draft = false; 
		Deleted = false; 
		Seen = false;
		hasatt = false;
		hasnoatt = false;
		
		int index = 0;
		String[] KnownFlags = {"Answered","Flagged","Draft","Deleted","Seen","hasatt","hasnoatt"};
		if(flags != null){
			
			while(index<flags.length){
				for(int i=0 ;i<7;i++){
					if(flags[index].equalsIgnoreCase(KnownFlags[i])){
						
						switch(i){
						
							case 0: Answered = true; break;
							case 1: Flagged  = true; break;
							case 2: Draft	 = true; break;
							case 3: Deleted	 = true; break;
							case 4: Seen     = true; break;
							case 5: hasatt   = true; break;
							case 6: hasnoatt = true; break;
							 default: break;
							
						}//switch
					} //if
						
				}//for
				index++;
			}//while
		}//if
		
		

	}
	
	public void setDate(String str){
		
		Date = str;
	}
	
	public void setBody(String str){
		
		Body = str;
	}
	
	public void setFrom(String str){
		
		From = str;
	}
	
	public void setTo(String str){
		
		To = str;
	}
	
	public void setSubject(String str){
		
		Subject = str;
	}
	
	public void setEmailNumber(String str){
		
		EmailNumber = str;
	}
	
	public void setFolderName(String str){
		
		FolderName = str;
	}
	
	public void setRead(boolean val){
		
		Read = val;
	}
	
	public void setFlagged(boolean val){
		
		Flagged = val;
	}

	public void setAnswered(boolean val){
		
		Answered = val;
	}
	public void setDraft(boolean val){
		
		Draft = val;
	}
	public void setDeleted(boolean val){
		
		Deleted = val;
	}
	public void setSeen(boolean val){
		
		Seen = val;
	}
	
	public void sethasatt(boolean val){
		
		hasatt = val;
	}
	
	public void sethasnoatt(boolean val){
		
		hasnoatt = val;
	}
	
	//Get functions 
	public String getDate(){
		
		return Date;
	}
	
	public String getBody(){
		
		return Body;
	}
	
	public String getFrom(){
		
		return From;
	}
	
	public String getTo(){
		
		return To;
	}
	
	public String getSubject(){
		
		return Subject;
	}
	
	public String getEmailNumber(){
		
		return EmailNumber;
	}
	
	public String getFolderName(){
		
		return FolderName;
	}
	
	public boolean getRead(){
		
		return Read;
	}
	
	public boolean getFlagged(){
		
		return Flagged;
	}
	public boolean get(){
		
		return Read;
	}
	public boolean getAnswered(){
		
		return Answered;
	}
	public boolean getDraft(){
		
		return Draft;
	}
	public boolean getDeleted(){
		
		return Deleted;
	}
	public boolean getSeen(){
		
		return Seen;
	}
	
	public boolean gethasatt(){
		
		return hasatt;
	}
	
	public boolean gethasnoatt(){
		
		return hasnoatt;
	}

	public boolean isHasatt() {
		return hasatt;
	}

	public void setHasatt(boolean hasatt) {
		this.hasatt = hasatt;
	}

	public boolean isHasnoatt() {
		return hasnoatt;
	}

	public void setHasnoatt(boolean hasnoatt) {
		this.hasnoatt = hasnoatt;
	}

	public boolean isDeleted() {
		return Deleted;
	}

	public boolean isRead() {
		return Read;
	}

	public String getSMTPHost() {
		return SMTPHost;
	}

	public void setSMTPHost(String host) {
		SMTPHost = host;
	}

	public String getSMTPUser() {
		return SMTPUser;
	}

	public void setSMTPUser(String user) {
		SMTPUser = user;
	}
	
	// End of Get functions 
}
