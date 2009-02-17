import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.sun.jmx.snmp.Timestamp;

import common.Email;
import common.MessageTypes;
import common.ObjectSender;
import common.myContainer;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ComposeDialog extends javax.swing.JDialog {
	private JPanel ToFromSend;
	private JScrollPane Body;
	private JButton sendButton;
	private JTextArea emailBody;
	private JTextField subjectField;
	private JLabel sLabel1;
	private JTextField toField;
	private JLabel toLabel;
	private Socket client;
	private String SMTPhost, SMTPuser;

	
	public ComposeDialog(JFrame frame, Socket socket, String SMTPHost, String SMTPUser) {
		super(frame);
		initGUI();
		SMTPuser = SMTPUser;
		SMTPhost = SMTPHost;
		client = socket;
	}
	
	private void initGUI() {
		try {
			{
				ToFromSend = new JPanel();
				getContentPane().add(ToFromSend, BorderLayout.CENTER);
				ToFromSend.setPreferredSize(new java.awt.Dimension(392, 129));
				{
					toLabel = new JLabel();
					ToFromSend.add(toLabel);
					toLabel.setText("To:");
				}
				{
					toField = new JTextField();
					ToFromSend.add(toField);
					toField.setPreferredSize(new java.awt.Dimension(360, 21));
				}
				{
					sLabel1 = new JLabel();
					ToFromSend.add(sLabel1);
					sLabel1.setText("Subject:");
				}
				{
					subjectField = new JTextField();
					ToFromSend.add(subjectField);
					subjectField.setPreferredSize(new java.awt.Dimension(339, 21));
				}
				{
					sendButton = new JButton();
					ToFromSend.add(sendButton);
					sendButton.setText("Send");
					sendButton.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							sendButtonMouseClicked(evt);
						}
					});
				}
			}
			{
				Body = new JScrollPane();
				getContentPane().add(Body, BorderLayout.NORTH);
				Body.setPreferredSize(new java.awt.Dimension(392, 155));
				{
					emailBody = new JTextArea();
					Body.setViewportView(emailBody);
					emailBody.setText("jTextArea1");
					emailBody.setPreferredSize(new java.awt.Dimension(389, 107));
				}
			}
			this.setSize(432, 294);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendButtonMouseClicked(MouseEvent evt) {
		System.out.println("sendButton.mouseClicked, event="+evt);
		
		Email email = new Email();
		Date now = new Date();
		email.setDate(now.toLocaleString());
		email.setTo(toField.getText());
		email.setBody(emailBody.getText());
		email.setSubject(subjectField.getText());
		email.setSMTPHost(SMTPhost);
		email.setSMTPUser(SMTPuser);
		
		ObjectSender.SendObject(email, MessageTypes.MESSAGE, client );
		myContainer container = ObjectSender.WaitForObject(client);
		
		if (container.getMsgType() == MessageTypes.CONFIRMATION_OK){
			JOptionPane.showMessageDialog(null, "Message sent");			
		}
		else {
			JOptionPane.showMessageDialog(null, "Message could NOT be sent");			
		}
		this.setVisible(false);
	}

}
