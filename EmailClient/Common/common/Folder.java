package common; 

import java.io.Serializable;

public class Folder implements Serializable{

	String FolderName = "undefined";
	String RecentMsg = "undefined";
	String TotalMsg = "undefined";
	String UnseenMsg = "undefined";
	String FolderSimplename;
	
	public Folder(String FlderName, String FldRecentMsg, String FldTotalMsg, String FldUnseenMsg){
		
		FolderName	= FlderName;
		RecentMsg	= FldRecentMsg;
		TotalMsg	= FldTotalMsg;
		UnseenMsg	= FldUnseenMsg;
		
	}
	
	public String toString(){		 
		return getFolderSimplename()+ "("+TotalMsg+"/"+UnseenMsg+"/"+RecentMsg+")";
	}
	
	public void setFldName( String name){
		
		FolderName = name;
	}
	public void setFldRecentMsg( String Msg){
		RecentMsg = Msg;
		}
	public void setFldTotalMsg( String Msg){
		TotalMsg = Msg;
	}
	public void setFldUnseenMsg( String Msg){
		UnseenMsg = Msg;
	}
	public String getFldName(){
		return FolderName;
	}
	public String getFldRecentMsg(){
		return RecentMsg;	
		}
	public String getFldTotalMsg(){
		return TotalMsg;
	}
	public String getFldUnseenMsg(){
		return UnseenMsg;
		
	}

	public String getFolderSimplename() {
		String [] path = this.FolderName.split("[.]");
		return path[path.length-1];
	}

	

}
