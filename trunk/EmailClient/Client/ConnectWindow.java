import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;


import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


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
public class ConnectWindow extends javax.swing.JDialog {
	private JButton ConnectButton;
	private JTextField GatewayAddressField;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				ConnectWindow inst = new ConnectWindow(frame);
				inst.setVisible(true);
			}
		});
	}
	
	public ConnectWindow(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private void initGUI() {
		try {
			{
				ConnectButton = new JButton();
				getContentPane().add(ConnectButton, BorderLayout.CENTER);
				ConnectButton.setText("Connect");
				ConnectButton.setPreferredSize(new java.awt.Dimension(322, 195));
				ConnectButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						ConnectButtonMouseClicked(evt);
					}
				});
			}
			{
				GatewayAddressField = new JTextField();
				getContentPane().add(GatewayAddressField, BorderLayout.NORTH);
				GatewayAddressField.setText("address");
			}
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void ConnectButtonMouseClicked(MouseEvent evt) {
		System.out.println("ConnectButton.mouseClicked, event="+evt);
		
		try{
			Socket ToGateway = new Socket(GatewayAddressField.getText(), 80);
			
			LoginDialog LoginWindow = new LoginDialog(null, ToGateway );
			this.setVisible(false);
			LoginWindow.setVisible(true);					
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(null,"Error occured, exception details ="+ e.toString());
		}					
	}

}
