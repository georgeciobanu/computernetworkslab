import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.io.ObjectInputStream;
import java.net.*;
import javax.swing.JButton;
import common.*;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class LoginDialog extends javax.swing.JDialog {

	private Socket _toGateway = null;

	private JButton LoginButton;

	private JLabel jLabel4;

	private JLabel jLabel5;
	private JTextField portField;
	private JLabel jLabel8;

	private JTextField SMTPUsernameField;

	private JTextField SMTPHostField;

	private JPasswordField PasswordField;

	private JTextField IMAPUsernameField;

	private JTextField IMAPHostField;

	private JLabel jLabel7;

	private JLabel jLabel6;

	private JPanel SMTPPanel;

	private JLabel jLabel1;

	private JLabel jLabel2;

	private JLabel jLabel3;

	private JPanel IMAPPanel;
	private JFrame parentFrame;
	

	/**
	 * Auto-generated main method to display this JDialog
	 */

	public LoginDialog(JFrame frame, Socket ToGateway) {
		super(frame);
		initGUI();
		_toGateway = ToGateway;		
		parentFrame = frame;
	}

	public Socket getSocket() {
		return _toGateway;
	}

	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent evt) {
						System.out.println("this.windowClosing, event="+evt);
						System.exit(0);
					}
				});
			}
			{
				LoginButton = new JButton();
				getContentPane().add(LoginButton);
				LoginButton.setText("Login");
				LoginButton.setBounds(146, 182, 114, 29);
				LoginButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						LoginButtonMouseClicked(evt);
					}
				});
			}
			{
				IMAPPanel = new JPanel();
				getContentPane().add(IMAPPanel);
				IMAPPanel.setBounds(0, 0, 306, 176);
				IMAPPanel.setLayout(null);
				{
					jLabel4 = new JLabel();
					IMAPPanel.add(jLabel4);
					jLabel4.setText("Password");
					jLabel4.setBounds(12, 107, 83, 14);
				}
				{
					jLabel3 = new JLabel();
					IMAPPanel.add(jLabel3);
					jLabel3.setText("Username");
					jLabel3.setBounds(12, 76, 83, 14);
				}
				{
					jLabel2 = new JLabel();
					IMAPPanel.add(jLabel2);
					jLabel2.setText("Host");
					jLabel2.setBounds(12, 45, 28, 14);
				}
				{
					jLabel1 = new JLabel();
					IMAPPanel.add(jLabel1);
					jLabel1.setText("IMAP");
					jLabel1.setBounds(12, 12, 66, 14);
				}
				{
					IMAPHostField = new JTextField();
					IMAPPanel.add(IMAPHostField);
					IMAPHostField.setText("mail.ifimadeuptherules.com");
					IMAPHostField.setBounds(75, 42, 219, 21);
				}
				{
					IMAPUsernameField = new JTextField();
					IMAPPanel.add(IMAPUsernameField);
					IMAPUsernameField.setText("ecse489@ifimadeuptherules.com");
					IMAPUsernameField.setBounds(101, 73, 193, 21);
				}
				{
					PasswordField = new JPasswordField();
					IMAPPanel.add(PasswordField);
					PasswordField.setBounds(101, 104, 120, 21);
					PasswordField.setText("winter2009");
				}
			}
			{
				SMTPPanel = new JPanel();
				getContentPane().add(SMTPPanel);
				SMTPPanel.setBounds(318, 0, 273, 129);
				SMTPPanel.setLayout(null);
				{
					jLabel5 = new JLabel();
					SMTPPanel.add(jLabel5);
					jLabel5.setText("SMTP");
					jLabel5.setBounds(0, 12, 48, 14);
				}
				{
					jLabel6 = new JLabel();
					SMTPPanel.add(jLabel6);
					jLabel6.setText("Host");
					jLabel6.setBounds(0, 32, 48, 14);
				}
				{
					jLabel7 = new JLabel();
					SMTPPanel.add(jLabel7);
					jLabel7.setText("Username");
					jLabel7.setBounds(0, 58, 82, 14);
				}
				{
					SMTPHostField = new JTextField();
					SMTPPanel.add(SMTPHostField);
					SMTPHostField.setText("mail.ifimadeuptherules.com");
					SMTPHostField.setBounds(89, 29, 177, 21);
				}
				{
					SMTPUsernameField = new JTextField();
					SMTPPanel.add(SMTPUsernameField);
					SMTPUsernameField.setText("ecse489@ifimadeuptherules.com");
					SMTPUsernameField.setBounds(89, 55, 177, 21);
				}
				{
					jLabel8 = new JLabel();
					SMTPPanel.add(jLabel8);
					jLabel8.setText("Port:");
					jLabel8.setBounds(0, 91, 65, 14);
				}
				{
					portField = new JTextField();
					SMTPPanel.add(portField);
					portField.setText("587");
					portField.setBounds(89, 88, 81, 21);
				}
			}
			this.setSize(632, 257);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void LoginButtonMouseClicked(MouseEvent evt) {
		System.out.println("LoginButton.mouseClicked, event=" + evt);
		myLoginInfo info = new myLoginInfo(IMAPHostField.getText(),
				IMAPUsernameField.getText(), PasswordField.getText(),
				SMTPHostField.getText(), SMTPUsernameField.getText());

		ObjectSender.SendObject(info, MessageTypes.LOGIN_INFO, getSocket());
					
			myContainer objResponse = (myContainer) ObjectSender.WaitForObject(getSocket());

			while (true) {			
					if (objResponse.getMsgType() == MessageTypes.LOGIN_RESPONSE) {

						myLoginResponse ActualResponse = new myLoginResponse();
						ActualResponse = (myLoginResponse) objResponse
								.getPayload();

						if (ActualResponse.getReply() == LoginStatus.OK) {
							MainWindow mainWindow = 
								new MainWindow(
										null,
									getSocket(), 
									SMTPHostField.getText(), 
									SMTPUsernameField.getText(),
									portField.getText()
									);
							
							mainWindow.setVisible(true);
							this.setVisible(false);
							break;
						}
					}
					JOptionPane.showMessageDialog(null, "Invalid credentials");
				}
			}

		
	
	
}
