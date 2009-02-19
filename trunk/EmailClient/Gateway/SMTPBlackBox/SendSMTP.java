/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SMTPBlackBox;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;    // Used for date formatting.
import common.*;


/**
 *
 * @author Imad
 */
public class SendSMTP {

    public static void SendEmail(String HOST, String FROM, String pass, String TO, String SUBJECT, String DATA, String SMTPPort){
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
    
    
    os.writeBytes("AUTH LOGIN\n");
    
    //for(int i=0; i<1000000; i++) {}
    
    
    os.writeBytes(Base64Coder.encodeString("ecse489@ifimadeuptherules.com")+"\n");
    
    for(int i=0; i<1000000; i++) {}
    os.writeBytes(Base64Coder.encodeString("winter2009")+"\n");
    //for(int i=0; i<1000000; i++) {} 
    
    os.writeBytes("MAIL From: <"+FROM+">\n");
    // Who the email is going to.
    System.out.println(is.readLine());
    os.writeBytes("RCPT To: <"+TO+">\n");
    System.out.println(is.readLine());
    //IF you want to send a CC then you will have to add this
    //os.writeBytes("RCPT To: <mohamad.aleagha@gmail.com>\r\n");

              
    // Now we are ready to add the message and the 
    // header of the email to be sent out.                
    os.writeBytes("DATA\n");
    System.out.println(is.readLine());              
    //os.writeBytes("X-Mailer: Via Java\r\n");
    
    os.writeBytes("DATE: " + dFormat.format(dDate) + "\n");
   
    os.writeBytes("From: "+FROM+"\n");
   
    os.writeBytes("To:   "+TO+"\n");
    
               
    //Again if you want to send a CC then add this.
    //os.writeBytes("To: CCDUDE <mohamad.aleagha@gmail.com>\r\n");
                
    //Here you can now add a BCC to the message as well
    //os.writeBytes("RCPT Bcc: BCCDude<BCC@invisiblecompany.com>\r\n");
    
                
    
    

    os.writeBytes("Subject: "+SUBJECT+"\n");
   
    os.writeBytes(DATA + "\n");
   
    os.writeBytes("\n.\n");
    //os.writeBytes("QUIT\n");
                
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
