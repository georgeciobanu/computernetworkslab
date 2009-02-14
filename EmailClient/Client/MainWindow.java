
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import common.Email;
import common.Folder;
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
public class MainWindow extends javax.swing.JDialog {
	private JPanel jPanel1;
	private JButton NewFolderButton;
	private JButton MoveMessageButton;
	private JButton DeleteFolderButton;
	private JButton RenameFolderButton;
	private JButton ComposeButton;
	private JTable EmailTable;
	private JTree FolderTree;
	private JPanel jPanel3;
	private JPanel jPanel2;
	
	private Socket toGateway = null;
	private Folder [] folders = null;
	private Email [] emails = null; 

	/**
	* Auto-generated main method to display this JDialog
	*/
		
	public MainWindow(JFrame frame, Socket socket) {
		super();
		toGateway = socket;				
		initGUI();		
		refreshData();		
	}
	
	private void refreshData(){
//		rebuild the gui based on received data
		getDataFromGateway();		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Inbox");
		GenerateTree(folders, top);
		FolderTree = new JTree();		
		String [] columns = {"Number", "Subject", "From", "Date"};
		EmailTable = new JTable(parseEmailList(emails, "Inbox"), columns );		
	}
	private DefaultMutableTreeNode GenerateTree(Folder [] folders, DefaultMutableTreeNode node){
		for (int i = 0; i < folders.length; i++){
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(folders[i]);
			node.add(newNode);
			//Recursively call to add all children
		}
		return node;
	}
	
	private String [][] parseEmailList(Email [] emailList, String folder){
		String [][] emails = new String[4][emailList.length];
		for (int i= 0; i < emailList.length; i++){
			emails[0][i] = emailList[i].getEmailNumber().toString();
			emails[1][i] = emailList[i].getSubject();
			
			//TODO: Use the stuff Saleh added here
			if (folder == "Sent"){
				emails[1][i] = emailList[i].getTo();
			}
			else {
				emails[1][i] = emailList[i].getFrom().toString();	
			}
						
			emails[1][i] = emailList[i].getDate().toString();				
		}
		return emails;
	}
	
	private void getDataFromGateway(){
		//send the request for the list of folders
		myContainer container = ObjectSender.WaitForObject(getToGateway());
		
		if (container.getMsgType() == MessageTypes.FOLDER_LIST ){
			folders = (Folder []) container.getPayload();
			
		}
		else if (container.getMsgType() == MessageTypes.MESSAGE_LIST){
			emails = (Email []) container.getPayload();
		}
		//else throw new IOException();			
	}
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
			}
			{
				jPanel1 = new JPanel();
				BorderLayout jPanel1Layout = new BorderLayout();				
				getContentPane().add(jPanel1);
				jPanel1.setBounds(0, 37, 186, 316);
				jPanel1.setLayout(jPanel1Layout);
				{
					FolderTree = new JTree();
					jPanel1.add(FolderTree, BorderLayout.CENTER);
					FolderTree.setPreferredSize(new java.awt.Dimension(186, 316));
				}
			}
			{
				jPanel2 = new JPanel();
				BorderLayout jPanel2Layout = new BorderLayout();
				getContentPane().add(jPanel2);
				jPanel2.setBounds(186, 37, 418, 316);
				jPanel2.setLayout(jPanel2Layout);
				{
					TableModel jTable1Model = 
						new DefaultTableModel(
								new String[][] { { "One", "Two" }, { "Three", "Four" } },
								new String[] { "Column 1", "Column 2" });
					EmailTable = new JTable();
					jPanel2.add(EmailTable, BorderLayout.CENTER);
					EmailTable.setModel(jTable1Model);
					EmailTable.setPreferredSize(new java.awt.Dimension(418, 294));
				}
			}
			{
				jPanel3 = new JPanel();
				FlowLayout jPanel3Layout = new FlowLayout();
				jPanel3Layout.setAlignment(FlowLayout.LEFT);
				getContentPane().add(jPanel3);
				jPanel3.setLayout(jPanel3Layout);
				jPanel3.setBounds(0, 0, 604, 31);
				{
					ComposeButton = new JButton();
					jPanel3.add(ComposeButton);
					ComposeButton.setText("Compose");
				}
				{
					NewFolderButton = new JButton();
					jPanel3.add(NewFolderButton);
					NewFolderButton.setText("New Folder");
				}
				{
					RenameFolderButton = new JButton();
					jPanel3.add(RenameFolderButton);
					RenameFolderButton.setText("Rename Folder");
				}
				{
					DeleteFolderButton = new JButton();
					jPanel3.add(DeleteFolderButton);
					DeleteFolderButton.setText("Delete Folder");
				}
				{
					MoveMessageButton = new JButton();
					jPanel3.add(MoveMessageButton);
					MoveMessageButton.setText("Move Message...");
				}
			}
			this.setSize(612, 387);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Socket getToGateway() {
		return toGateway;
	}

	public void setToGateway(Socket gateway) {
		toGateway = gateway;
	}

}
