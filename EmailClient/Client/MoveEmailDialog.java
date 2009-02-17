import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;

import javax.swing.JButton;
import common.*;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


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
public class MoveEmailDialog extends javax.swing.JDialog {
	private JTree destinationFolder;
	private JButton confirmButton;
	private DefaultMutableTreeNode treeRoot;
	private Socket toClient;
	private String emailNum;
	private String emailFolder;
	
	public MoveEmailDialog(JFrame frame, String emailNumber, String emailPath, DefaultMutableTreeNode root, Socket socket) {
		super(frame);
		treeRoot = root;
		emailNum = emailNumber;
		toClient = socket;
		emailFolder = emailPath;
		initGUI();									
	}
	
	private void initGUI() {
		try {
			{
				confirmButton = new JButton();
				getContentPane().add(confirmButton, BorderLayout.SOUTH);
				confirmButton.setText("OK");
				confirmButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						confirmButtonMouseClicked(evt);
					}
				});
			}
			{
				destinationFolder = new JTree(treeRoot);
				getContentPane().add(destinationFolder, BorderLayout.CENTER);
			}
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void confirmButtonMouseClicked(MouseEvent evt) {
		
		if ( (TreePath)destinationFolder.getSelectionPath() == null)
			return;
		DefaultMutableTreeNode currentNode = ((DefaultMutableTreeNode)destinationFolder.getSelectionPath().getLastPathComponent());		
		Folder current = (Folder)currentNode.getUserObject();		
		String [] command = {"MOVE_EMAIL", emailNum, emailFolder, current.getFldName()};

		ObjectSender.SendObject(command, MessageTypes.CLIENT_COMMAND, toClient);
		ObjectSender.WaitForObjectNoTimeout(toClient);
		this.setVisible(false);
				
	}

}
