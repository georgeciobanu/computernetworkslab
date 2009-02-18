/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SMTPBlackBox;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;     // Used for date formatting.


/**
 *
 * @author Imad
 */
public class SendSMTP {

    public static void SendEmail(String HOST, String FROM, String TO, String SUBJECT, String DATA, String SMTPPort){
        Socket smtpSocket = null;
         DataOutputStream os = null;
         DataInputStream is = null;
         
        Date dDate = new Date();
        DateFormat dFormat = DateFormat.getDateInstance(DateFormat.FULL,Locale.US);

try
{ // Open port to server
	int port = Integer.parseInt(SMTPPort);	
  smtpSocket = new Socket(HOST, port);
  os = new DataOutputStream(smtpSocket.getOutputStream());
  is = new DataInputStream(smtpSocket.getInputStream());
            
  if(smtpSocket != null && os != null && is != null)
  { // Connection was made.  Socket is ready for use.
    //////////////////////////////////////////////////////////////////////////////////////////////
      
      try                        
{   os.writeBytes("HELO "+HOST+"\r\n");
    // You will add the email address that the server 
    // you are using know you as.
    os.writeBytes("MAIL From: <"+FROM+">\r\n");
           
    // Who the email is going to.
    os.writeBytes("RCPT To: <"+TO+">\r\n");
  
    //IF you want to send a CC then you will have to add this
    //os.writeBytes("RCPT To: <mohamad.aleagha@gmail.com>\r\n");

              
    // Now we are ready to add the message and the 
    // header of the email to be sent out.                
    os.writeBytes("DATA\r\n");
                 
    //os.writeBytes("X-Mailer: Via Java\r\n");
    
    os.writeBytes("DATE: " + dFormat.format(dDate) + "\r\n");
   
    os.writeBytes("From: <"+FROM+">\r\n");
   
    os.writeBytes("To:   <"+TO+">\r\n");
    
               
    //Again if you want to send a CC then add this.
    //os.writeBytes("To: CCDUDE <mohamad.aleagha@gmail.com>\r\n");
                
    //Here you can now add a BCC to the message as well
    //os.writeBytes("RCPT Bcc: BCCDude<BCC@invisiblecompany.com>\r\n");
    
                
    
    

    os.writeBytes("Subject: "+SUBJECT+"\r\n");
   
    os.writeBytes(DATA + "\r\n");
   
    os.writeBytes("\r\n.\r\n");
    os.writeBytes("QUIT\r\n");
                
    // Now send the email off and check the server reply.  
    // Was an OK is reached you are complete.
    String responseline;
    while((responseline = is.readLine())!=null)
    {  System.out.println(responseline);
        if(responseline.indexOf("Ok") != -1)
            break;
    }
    System.out.println("message SENT");
}
catch(Exception e)
{  System.out.println("Cannot send email as an error occurred.");  }
      
    //////////////////////////////////////////////////////////////////////////////////////////////  
  }
}catch(Exception e)
{ System.out.println("Host "+HOST+" unknown"); }

    }
    
}
