/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//package telnettoimapserver;

package IMAPmethods;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.*;

import common.*;


public class IMAPMethods {
 
    static Socket IMAPSocket = null;
    static PrintWriter out = null;
    static BufferedReader in = null;
    static char[] Serverrcv = null;
    //static int index = 0;
    static String[] foldersName;
    static String[] Fnames = new String[100];
    static String checkIfINBOX = "";
    //TODO: add some header here.  
/*  
    public static void main(String[] args) throws IOException {

        String line = null;
        boolean DEBUG = true;

        // It opens the socket here.
        //OpenConnection();

        // here we print the line after we create the socket.
        line = in.readLine();
        print(line);

        // here it logs into the IMAP server through the LOGIN method
        //Login();
        
        // here it prints whatever comes from IMAP server after loging.
        line = in.readLine();
        if (DEBUG) print(line);


        // 
        //    HERE ARE BUNCH OF TEST CASES TO TEST EACH METHOD
        //
        //System.out.println(CreateFolder("INBOX.DEL2"));

        // TEST: deleteEmail and moveEmail
        Email[] fooEmails = LisOfEmails("INBOX");
        Email foo = fooEmails[0];
        System.out.println(foo.getEmailNumber());
        System.out.println(foo.getSubject());
        //deleteEmail
        //System.out.println(deleteEmail(foo));
        
        //moveEmail
        //System.out.println(moveEmail(foo,"something")); 

        /*  
         // TEST for ListofFolders method
          Folder[] foo = ListofFolders();
          String fooName = foo[0].getFldName();
          String fooRecent = foo[0].getFldRecentMsg();
          String fooUnseen = foo[0].getFldUnseenMsg();
          String fooTotal = foo[0].getFldTotalMsg();
          System.out.println("Folder Name:\t"+fooName);
          System.out.println("Recent Emails:\t"+fooRecent);
          System.out.println("Unseen Emails:\t"+fooUnseen);
          System.out.println("Total Emails:\t"+fooTotal);
        */ 

        /* 
          // TEST for ListOfEmails AND getTHEamil methods
          Email[] fooEmails = LisOfEmails("INBOX");
          Email foo = fooEmails[2];
          
          System.out.println(fooEmails.length);
          System.out.println(foo.getEmailNumber());
          System.out.println(foo.getSubject());
          System.out.println(foo.getDate());
          System.out.println(foo.getFrom());
          System.out.println(foo.gethasatt());
          
          //this part is to test the getTHEemail()
          Email fullFoo = getTHEemail(foo);
          System.out.print("\nBody:\n"+fullFoo.getBody());
        */
          
        // It closes the socket here.
        //CloseConnection(); 
    //}


    /**************************************************************************** 
     * Method Name:		moveEmail(Email,DestinationFolder)
     * 
     * Description:		This function Takes the email then it extracts some of its   
     *  				information and then move the email from one folder to 
     *  				another by copying and then deleting.
     *	
     * Parameters:		String DestinationFolder. NOTE: it must NOT have the "INBOX."
     *					Email Object to be deleted.				
     * 				 
     * Return: 			boolean:
     *			 					SUCCESS --------  false 
     *   							FAIL	--------  true
     * 
     *****************************************************************************/
    public static boolean moveEmail(Email Myemail,String DestinationFolder) throws IOException{

        //debug tool variables
        boolean LOGing = true;
        boolean DEBUG  = true;

        //Return Value
        boolean ReVal = true;

        String emailNum =  Myemail.getEmailNumber();
        String folderName = Myemail.getFolderName();
        String line = null;


        // it makes sure that we have a correct name for the folder.
        if (!DestinationFolder.equalsIgnoreCase("INBOX")) DestinationFolder = "INBOX."+DestinationFolder;

        // command strings
        String COPYcmd = ". COPY "+emailNum+" "+DestinationFolder;
        String SELECTcmd = ". SELECT "+folderName;

        // something to pattern an string like this: ". OK [COPYUID 1234387231 1 2] Completed"
        String OK_PATTERN = "^\\.\\s\\bOK\\b\\s\\[\\bCOPYUID\\b\\s[\\s\\:a-zA-Z0-9]*\\]\\s\\bCompleted\\b";

        // just for getting into the folder where the emial is stored in
        cmd(SELECTcmd);

        // Here copy the email from the origin to its given folder.
        cmd(COPYcmd);
        line = in.readLine();
        if (LOGing) print(line);

        Pattern p_NO = Pattern.compile("^\\.\\s\\bNO\\b\\s.*");
        Matcher m_NO = p_NO.matcher(line);

        while (line != null && !m_NO.matches()) {

            Pattern p_end = Pattern.compile(OK_PATTERN);
            Matcher m_end = p_end.matcher(line);

            if (m_end.matches()) {

                //At this point we know that we copied the email safely now we can remove the original email.
                // If there was any problem removing this email. it returns an error!
                if (DEBUG) System.out.println("Here it starts deleting the original file ... ");
                if (deleteEmail(Myemail)) {

                    return ReVal;
                }
                // updating the folder parameter of the email.	
                if (DEBUG) System.out.println("Updating the folder field of just copied email ... ");
                Myemail.setFolderName(DestinationFolder);

                ReVal = false;  // means success 
                break;

            }//if
            else {

                line = in.readLine();
                if (LOGing) print(line);

            }//else

        }//while  	

        return ReVal;
    }

    /**************************************************************************** 
     * Method Name:		deleteEmail(Email)
     * 
     * Description:		This function takes an email which has to be deleted and 
     * 					then deletes the email. It returns false (0) in case of   
     *  				success and true (1) in case of failure. 
     *  
     * Parameters: 		Email Object to be deleted.
     *				
     * Return: 			boolean 	SUCCESS --------  false : 0
     * 								FAIL	--------  true  : 1
     * 							 
     *****************************************************************************/
    public static boolean deleteEmail(Email Myemail) throws IOException{

        //debug tool variables
        boolean LOGing = true;
        boolean DEBUG  = true;

        //Return Value
        boolean ReVal = true;

        String emailNum =  Myemail.getEmailNumber();
        String folderName = Myemail.getFolderName();
        String line = null;

        if (!folderName.equalsIgnoreCase("INBOX")) folderName = "INBOX."+folderName;

        // SELECT command string 
        String SELECTcmd = ". SELECT "+folderName;

        // STORE command string
        String STOREcmd = ". STORE "+emailNum+" flags \\Deleted";
        // EXPUNGE command string
        String EXPUNGEcmd =". EXPUNGE";

        cmd(SELECTcmd);

        // Here we send the command to store the flag change 
        cmd(STOREcmd);
        line = in.readLine();
        if (DEBUG) print(line);

        Pattern p_NO = Pattern.compile("^\\.\\s\\bNO\\b\\s.*");
        Matcher m_NO = p_NO.matcher(line);

        // here we expunge in the case that we changed the flaged right
        if (!m_NO.matches()) {

            cmd(EXPUNGEcmd);
            line = in.readLine();
            if (DEBUG) print(line);
        }

        while (line != null && !m_NO.matches()) {


            Pattern p_end = Pattern.compile("^\\.\\s\\bOK\\b\\s\\bCompleted\\b");
            Matcher m_end = p_end.matcher(line);

            if (m_end.matches()) {

                ReVal = false;  // means success 
                break;
            }//if
            else {

                line = in.readLine();
                if (DEBUG) print(line);

            }//else

        }//while


        return ReVal;
    }
    /***************************************************************************
     * Method Name:		getTHEemail(Email theEmail)
     * 
     * Description:		This function Takes the email numbers and gets the entire 
     * 					email in the Email object form. It has all the information  
     *  				in the email.
     *  
     * Parameters:		Email Object withOUT(NAN) the body.
     *						 				 
     * Return: 			Email Object WITH the body 
     *   		ERROR:	Email NULL 	
     *   
     ****************************************************************************/
    public static Email getTHEemail(String emailNum) throws IOException{

        //debug tool variables
        boolean LOGing = true;
        boolean DEBUG  = true;

        //Return Value
        Email ReVal = new Email();
        
        String line = null;
        String emailBody = "";
        int lineCounter = 0;  // we use this variable to get rid of the first line of email

        //Here is the command to fetch the email body.
        String FETCHemailBodycmd = ". FETCH "+emailNum+" (body[text])"; 

        // Pattern Strings
        String FETCH = "^\\*\\s(\\d+)\\s\\bFETCH\\b\\s.*";
        // the first group is the email number which has to be the same with them one passed through the fetch. 
        String emailNumCheck = null;

        // Here we run the command to fetch the particular email.
        cmd(FETCHemailBodycmd);
        line = in.readLine();
        if (LOGing) print(line);

        //Check to see if we got the right data
        Pattern p_fetch = Pattern.compile(FETCH);
        Matcher m_fetch = p_fetch.matcher(line);
        boolean b_fetch = m_fetch.matches();
        if (b_fetch) emailNumCheck = m_fetch.group(1);
        // it checks if the email number is the same as one got fetched.
        boolean b_check =  emailNum.equalsIgnoreCase(emailNumCheck);

        while (line != null && b_fetch && b_check) {

            if (lineCounter != 0)emailBody = emailBody + line +"\n";

            line = in.readLine();
            if (LOGing) print(line);

            // At the end of each email we receive '(' as terminator
            if (line.length() >= 1) {
                if (line.substring(0,1).equalsIgnoreCase(")")) {

                    line = in.readLine();
                    if (LOGing) print(line);

                    // Check for the ". OK Completed (0.000 sec)" to quit the loop.
                    Pattern p_end = Pattern.compile("^\\.\\s\\bOK\\b\\s\\bCompleted\\b\\s\\(\\d+\\.\\d+\\s\\bsec\\b\\)");
                    Matcher m_end = p_end.matcher(line);
                    if (m_end.matches())break;
                }//if
            }//if

            // it prevents the emailBody from having the fetch line in it.
            lineCounter++;      
        }//while

        // setting the body field of email
        ReVal.setBody(emailBody);
        ReVal.setEmailNumber(emailNum);
        // At this point we can update the email by putting the email body into the
        // its field.

        if (DEBUG) System.out.println("___Here is the new body:");
        if (DEBUG) System.out.print(emailBody+"\n");



        return ReVal;
    }

    //TODO: RENAME the FOLDER NAME.

    /*************************************************************************** 
     * Method Name: 	CreateFolder( String FolderName)
     * 
     * Description:		This function Takes the folder name and creates  
     *  				a folder with the same name.
     *  
     * Parameters:		String FolderName 
     *					IMPORTANT : you MUST not include the INBOX. 
     *								in your folder's name 				
     * 				
     * Return: 			boolean  0: success  1: failure 
     *   					 		false		true	
     ****************************************************************************/
    public static boolean CreateFolder( String FolderName) throws IOException{

        //debug tool variables
        boolean LOGing = true;
        boolean DEBUG  = true;

        //Return Value
        boolean ReVal = true;

        String line = null;
        String DELcmd = ". CREATE INBOX."+FolderName;

        cmd(DELcmd);
        line = in.readLine();
        if (LOGing) print(line);

        Pattern p_NO = Pattern.compile("^\\.\\s\\bNO\\b\\s.*");
        Matcher m_NO = p_NO.matcher(line);

        while (line != null && !m_NO.matches()) {


            Pattern p_end = Pattern.compile("^\\.\\s\\bOK\\b\\s\\bCompleted\\b");
            Matcher m_end = p_end.matcher(line);
            if (!m_end.matches()) line = in.readLine();if (LOGing) print(line);


            if (m_end.matches()) ReVal = false;break;
        }//while

        return ReVal;
    }

    /*************************************************************************** 
     * Method Name:		DeleteFolder( String FolderName)
     * 
     * Description:		This function Takes the folder name and deletes the 
     *  				given folder.
     * 
     * Parameters:		String FolderName
     * 				
     * Return: 			boolean  0: success  1: failure 
     *   					 		false		true
     *   	
     ****************************************************************************/
    public static boolean DeleteFolder( String FolderName) throws IOException{

        //debug tool variables
        boolean LOGing = true;
        boolean DEBUG  = true;

        //Return Value
        boolean ReVal = true;

        String line = null;
        String DELcmd = ". DELETE "+FolderName;

        cmd(DELcmd);
        line = in.readLine();
        if (LOGing) print(line);

        Pattern p_NO = Pattern.compile("^\\.\\s\\bNO\\b\\s.*");
        Matcher m_NO = p_NO.matcher(line);

        while (line != null && !m_NO.matches()) {


            Pattern p_end = Pattern.compile("^\\.\\s\\bOK\\b\\s\\bCompleted\\b\\s\\(\\d+\\.\\d+\\s\\bsecs\\b\\s\\d*\\s\\bcalls\\b\\)");
            Matcher m_end = p_end.matcher(line);

            if (m_end.matches()) line = in.readLine();
            if (LOGing) print(line);

            if (m_end.matches()) {
                ReVal = false; break;
            } else {
                p_end = Pattern.compile("^\\.\\s\\bOK\\b\\s\\bCompleted\\b");
                m_end = p_end.matcher(line);
                if (m_end.matches()) ReVal = false;break;
            }//else

        }//while

        return ReVal;
    }

    /**************************************************************************
     * Method Name:		LisOfEmails(String FolderName)
     * 
     * Description:		This function Takes the folder name and returns 
     * 					list of email inside that folder.
     * 
     * Parameters: 		String FolderName
     * 				
     * 
     * Return: 			Email[] ListOfEmials
     * 					NULL if the folder is empty.	
     *   
     *************************************************************************/
    public static Email[] LisOfEmails(String FolderName) throws IOException {

        //debug tool variables
        boolean LOGing = true;
        boolean DEBUG  = true;

        // the method variables
        Email[] ReVal = null;
        String line = null;
        String fldName = FolderName;

        boolean b_fetch = false;
        String  numOFemails = null;
        String  flagsOFemails = null;
        String  delims = " ";
        String[] flagsOFemails_arr = null;

        boolean b_date = false;
        String  dateOFemails = null;


        boolean b_from = false;
        String  fromOFemails = null;


        boolean b_subject = false;
        String  subjectOFemails = null;

        Email[] ListOfEmails = null;
        int email_index = 0;

        // Here we define the patterns we like to track on the line to retrieve 
        // Several information such as DATE, SUBJECT, FLAG, FROM and TO
        String FETCH = "^\\*\\s(\\d+)\\s\\bFETCH\\b\\s\\(\\bFLAGS\\b\\s\\(([\\*\\a-zA-Z]*)\\)\\s.*";
        String DATE  = "^Date:(.*)";
        String SUBJECT = "^Subject:(.*)";
        String FROM  = "^From:(.*)";
        // END OF PATTERNs

        //TODO: check to see if the FolderName has to be with INBOX. it would be taken care of in NumberOfEmail's method????   
        // This gets the total number of email per folder.
        int TotalNumEmails_perFld = Integer.parseInt(NumberOfEmail("MESSAGES",FolderName));
        
        /*
         * EARLY RETURN 
         * 
         * It checks if there is any email in the folder, if there is none it simply returns NULL here.
         * */
        if(TotalNumEmails_perFld == 0){
        	
        	return null;
        }
        	
        //----------> here we create an array of Email based on this number here.
        ListOfEmails = new Email[TotalNumEmails_perFld];



        if (DEBUG) System.out.println("ListOfEmails Method");

        // To be able to get the required fields of each email we need to run the following
        // IMAP commands
        String SELECTcmd = ". EXAMINE \""+FolderName+"\"";
        String FETCHcmd  = ". FETCH 1:* (FLAGS BODY[HEADER.FIELDS (DATE FROM SUBJECT)])";

        //The select command gets run here
        cmd(SELECTcmd);
        line = in.readLine();
        if (LOGing) print(line);

        //We fetch the emails' SUBJ , DATE , FROM here
        cmd(FETCHcmd);
        line = in.readLine();
        if (LOGing) print(line);

        // At this point we must have the data ready to be parsed
        while ( line != null ) {


            // Here we check for the FETCH pattern to retrieve the email numbers and their flags
            Pattern p_fetch = Pattern.compile(FETCH);
            Matcher m_fetch = p_fetch.matcher(line);
            b_fetch = m_fetch.matches();
            // At this point if the "line" matches the FETCH pattern we can retrieve our data.        
            if (b_fetch) {
                // --> here we get the number of email
                //*****Email Number****//
                numOFemails = m_fetch.group(1);

                //*****Email Flags****//
                // --> here we get the flags of email
                flagsOFemails = m_fetch.group(2);
                flagsOFemails = flagsOFemails.replace("\\","");
                //here we put the flags inside an array
                //*****Email Flags Array****//
                flagsOFemails_arr =  flagsOFemails.split(delims);
                //To read the flagsOFemails_arr we always start from the second element.

                //Here in this loop we collect the information concerning each 
                // email. subject, from, date
                while (line!=null) {


                    // In this part we check the pattern of the line to find DATE of email
                    Pattern p_date = Pattern.compile(DATE);
                    Matcher m_date = p_date.matcher(line);
                    b_date = m_date.matches();

                    if (b_date) {
                        //*****Email Date****//
                        dateOFemails = m_date.group(1);
                    } else {

                        // In this part we check the pattern of the line to find FROM of email
                        Pattern p_from = Pattern.compile(FROM);
                        Matcher m_from = p_from.matcher(line);
                        b_from = m_from.matches();

                        if (b_from) {
                            //*****Email From****//
                            fromOFemails = m_from.group(1);
                        }//if
                        else {
                            // In this part we check the pattern of the line to find SUBJECT of email
                            Pattern p_subject = Pattern.compile(SUBJECT);
                            Matcher m_subject = p_subject.matcher(line);
                            b_subject = m_subject.matches();

                            if (b_subject) {
                                //*****Email Subject****//
                                subjectOFemails = m_subject.group(1);
                            }//if
                        }//else


                    }//else



                    // This line and printing should be at the bottom of the while loop
                    line = in.readLine();
                    if (LOGing) print(line);

                    // At the end of each email we receive '(' as terminator
                    if (line.length() == 0) break;
                    if (line.substring(0,1).equalsIgnoreCase(")")) break;
                }//while

                /* At this point we know that we have an email and we should have all the information
                 * about it except the body of email. Therefore at this point we create a new Email object
                 * based on the retrieved information.
                 */
                if (DEBUG) System.out.println("Email objct number: "+numOFemails+" has been created.");
                if (DEBUG) System.out.println("Email Date is: "+dateOFemails);
                if (DEBUG) System.out.println("Email is From: "+fromOFemails);
                if (DEBUG) System.out.println("Email Subject is: "+subjectOFemails);


                ListOfEmails[email_index] = new Email(dateOFemails,
                                                      subjectOFemails,
                                                      "NAN",
                                                      fromOFemails,
                                                      "",
                                                      numOFemails,
                                                      fldName,
                                                      flagsOFemails_arr); 
                //reseting
                dateOFemails = null;
                fromOFemails = null;
                subjectOFemails = null;

                // here we increment email_index which indicates the number of email
                // in the ListOfEmail array.
                email_index++;
            }//if

            // This line and printing should be at the bottom of the while loop
            line = in.readLine();
            if (LOGing) print(line);


            // Here it leaves the while loop since it got all the information it needs.
            //if( line.length() > 5){ //since we are using the substring method 
            //TODO: get rid of this method and use the regex instead!
            //	if( line.substring(0,4).equalsIgnoreCase(". OK") && !line.substring(0,27).equalsIgnoreCase(". OK [READ-WRITE] Completed")) break;
            //}
            //if(line.substring(0,5).equalsIgnoreCase(". BAD"))  ReV = "-1";break;
            Pattern p_end = Pattern.compile("^\\.\\s\\bOK\\b\\s\\bCompleted\\b\\s\\(\\d+\\.\\d+\\s\\bsec\\b\\)");
            Matcher m_end = p_end.matcher(line);
            if (m_end.matches())break;

        }

        // At this point we have all the email's' required header's info 
        // (i.e date , from , subject) we  just have to put the created
        // array of emails into the return value. 
        ReVal = ListOfEmails;

        return ReVal;
    }
    /************************************************************************************
     * Method Name:		NumberOfEmail(String statusType, String folderName)
     * 
     * Description:		This method gets the type of status WHICH HAS TO BE IN CAPITAL 
     * 					e.g  MESSAGES or UNSEEN or RECENT
     * 
     * Parameters:		String statusType ( i.e MESSAGE , UNSEEN , RECENT ) CAPITAL
     * 					String folderName 
     * 
     *  Return:			String (Number of requested type of of messages)
     * 			ERROR:	String -1  			
     * 
     *************************************************************************************/
    public static String NumberOfEmail(String statusType, String folderName) throws IOException {

        //DEBUG and LOGing varibales
        boolean DEBUG = false;
        boolean LOGing = false;

        String line = "";
        int index = 0;
        String ReV = "";
        String delims = "[( "+statusType+"]";
        String[] status_rv;
        String cmd = ". STATUS \""+folderName+"\" ("+statusType+")";

        if (DEBUG)System.out.println(" This is NumberOfEmail");

        cmd(cmd);
        line = in.readLine();
        if (LOGing) print("1"+line);
        while (line != null) {

            if ( line.substring(0,8).equalsIgnoreCase("* STATUS")) {
                status_rv  = line.split(delims);
                ReV = status_rv[status_rv.length-1].replace(")", "");
            }

            if ( line.substring(0,4).equalsIgnoreCase(". OK")) break;
            //if(line.substring(0,5).equalsIgnoreCase(". BAD"))  ReV = "-1";break;
            
            	line = in.readLine(); 
            	if (LOGing)print(line);
            
        } 
        if (DEBUG)System.out.println("DONE");
        if (DEBUG)System.out.println("ReV == "+ReV);

        return ReV;
    }

    /***************************************************************************
     * Method Name:		CloseConnection()
     * 
     * Description:		
     * 					  
     * Parameters:		void 
     *						 				 
     * Return: 			void 
     * 
     ****************************************************************************/
    public static void CloseConnection()  throws IOException {

        out.close();
        in.close(); 
        IMAPSocket.close();
    }

    /***************************************************************************
     * Method Name:		OpenConnection()
     * 
     * Description:		
     * 					  
     * Parameters:		void 
     *						 				 
     * Return: 			void 
     * 
     ****************************************************************************/
    public static void OpenConnection(String host)  throws IOException {

        try {
            IMAPSocket = new Socket(host, 143);
            out = new PrintWriter(IMAPSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                                         IMAPSocket.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: myfastmail.com.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: myfastmail.com.");
            System.exit(1);
        }

    }
    //TODO: fix this header 
    /***************************************************************************
     * Method Name:		ListofFolders()
     * 
     * Description:		
     * 					  
     * Parameters:		void 
     *						 				 
     * Return: 			Folder[] 
     * 
     ****************************************************************************/
    public static Folder[] ListofFolders() throws IOException{
    	
    	boolean DEBUG = true;
    	boolean LOGing = true;

    	String line = "";
    	String delims = "[\"]";
    	String[] temp_arr;
    	int index = 0;
    	int NumOfFolders = 0;
    	String[] foldernames = new String[100];    // The maximum number of folders is 100
    	String[] RawFoldernames = new String[100];
    	Folder[] Re_fld;
    	String[] cmdS = new String[]{". status INBOX (recent)",". status INBOX (messages)",". status INBOX (messages)"};
    	// this string is to match with such a string ". OK Completed (0.000 secs 9 calls)"
    	String OK = "^\\.\\s\\bOK\\b\\s\\bCompleted\\b\\s\\(\\d+\\.\\d+\\s\\bsecs\\b\\s\\d*\\s\\bcalls\\b\\)";
    	
    	if(DEBUG)System.out.println(" This is ListofFolders()");
        cmd(". list \"\" \"*\"");
    	
	        
	        line = in.readLine();
	        if(LOGing)print(line);
	        while(line != null){
	        	
				if( line.substring(0,6).equalsIgnoreCase("* LIST")){
					
					temp_arr = line.split(delims); 
					foldernames[NumOfFolders] = (temp_arr[temp_arr.length-1].replace("\"", "")).trim();
					//RawFoldernames[NumOfFolders]
					if(DEBUG) System.out.println("#-- Folder Name: "+foldernames[NumOfFolders]);
					NumOfFolders++;
				}
				
				Pattern p_OK = Pattern.compile(OK);
				Matcher m_OK = p_OK.matcher(line);
				boolean	b_OK = m_OK.matches();
				
	        	if( b_OK || line.substring(0,5).equalsIgnoreCase(". BAD")) break;
	        	
	        	line = in.readLine();
	        	if(LOGing)print(line);
	        }
	        if(DEBUG) System.out.println("DONE");
	        
	        // We have the name and number of all folders at this point 
	        // So we start creating the list of folders 
	        Re_fld = new Folder[NumOfFolders];
	        if(DEBUG) System.out.println(NumOfFolders);
	        
	        index = 0;
	        while(index < NumOfFolders){

	        	// This part of code fixes the name of folders in a format
	        	// that IMAP can understand it.
	        	
	        	        	Re_fld[index] = new Folder(foldernames[index],NumberOfEmail("RECENT",foldernames[index]),
	        													NumberOfEmail("MESSAGES",foldernames[index]),
	        													NumberOfEmail("UNSEEN",foldernames[index]));
	        	
	        	index++;
	        }
	        
	        return Re_fld;
    }

    //TODO: fix this header 
    /***************************************************************************
     * This method just prints whatever comes back from the server 
     * 
     * Method name : cmd(command)
     * 
     * Parameter: 	 String received 
     * 
     * Return : void
     * 
     ***************************************************************************/
    public static void print(String rcvd)
    {   
        System.out.println("<-- "+rcvd);
    }   

    //TODO: fix this header 
    /**************************************************************************** 
     * This method writes the commands into the socket
     * 
     * Method name : cmd(command)
     * 
     * Parameter: 	 String COMMAND 
     * 
     * Return : void
     * 
     * There is LOGing variable which can set to false if you wish not to get any logs 
     * 
     ****************************************************************************/
    public static void cmd(String cmd){

        boolean LOGing = true;

        //It writes to the socket at this point
        out.println(cmd);
        if (LOGing)System.out.println("--> "+cmd);
    }

    //TODO: fix this header    
    /**************************************************************************** 
     * This method is being used ust to loginto the IMAP server
     * 
     * Method name : Login()
     * 
     * Return : void
     * 
     *****************************************************************************/
    public static void Login(String username, String password){

        //This string is to add more command if needed.
        String[] cmdS  = new String[1];

        cmdS[0] = ". LOGIN " + username + " " + password;

        int index = 0;
        
        if (index < cmdS.length) {
            out.println(cmdS[index]);
            System.out.println("--> "+cmdS[index]);
        }
        index++;
    }
}


