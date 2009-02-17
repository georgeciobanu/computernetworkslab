
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.Position.Bias;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;

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
	private String SMTPuser, SMTPhost;
	

	/**
	* Auto-generated main method to display this JDialog
	*/
		
	public MainWindow(JFrame frame, Socket socket,String SMTPHost, String SMTPUser) {
		super();
		toGateway = socket;						
		SMTPuser = SMTPUser;
		SMTPhost = SMTPHost;
		initGUI();
		refreshData();				
	}
	
	private void refreshData(){
//		rebuild the gui based on received data
		getDataFromGateway();		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(folders[0]);
		GenerateTree(folders, top);		
					
		FolderTree = new JTree(top);	
		jPanel1.add(FolderTree, BorderLayout.CENTER);
		FolderTree.setPreferredSize(new java.awt.Dimension(186, 316));
		
		FolderTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent evt) {
				FolderTreeValueChanged(evt);
			}
		});
		String [] columns = {"Number", "Subject", "From", "Date"};
		EmailTable.setModel(new DefaultTableModel( parseEmailList(emails, "Inbox"), columns ));		
		EmailTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				EmailTableMouseClicked(evt);
			}
		});
		
		
	    TableModel model = new DefaultTableModel(parseEmailList(emails, "Inbox"), columns ) {
	        public boolean isCellEditable(int rowIndex, int mColIndex) {
	            return false;
	        }
	        
	    };
	    EmailTable.setModel(model);	    		
	}
	
	private void createNode(DefaultMutableTreeNode top, String[] path, int pos, Folder folder){
		int i = 0;
		if ( ((Folder)(top.getUserObject())).toString().equalsIgnoreCase(path[path.length-2]) ){
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(folder);
			top.add(newNode);
			return;
		}
		else{
			for (i =0; i < top.getChildCount(); i++){
				if ( ((Folder)((DefaultMutableTreeNode)top.getChildAt(i)).getUserObject()).toString().equalsIgnoreCase(path[pos+1]) )			
					break;
			}
			createNode((DefaultMutableTreeNode) top.getChildAt(i), path, pos+1, folder);
		}
	}
	
	private DefaultMutableTreeNode GenerateTree(Folder [] folders, DefaultMutableTreeNode top){			
		for (int i = 1; i < folders.length; i++){
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(folders[i]);
			String [] path = folders[i].getFldName().split("[.]");
			
			createNode(top, path, 0, folders[i]);						
		}
		return top;
	}
	
	private String [][] parseEmailList(Email [] emailList, String folder){
		if (emailList == null)
			return new String[1][1];
		String [][] emails = new String[emailList.length][4];
		for (int i= 0; i < emailList.length; i++){
			emails[i][0] = emailList[i].getEmailNumber().toString();
			emails[i][1] = emailList[i].getSubject();
			
			//TODO: Use the stuff Saleh added here
			if (folder == "Sent"){
				emails[i][2] = emailList[i].getTo();
			}
			else {
				emails[i][2] = emailList[i].getFrom().toString();	
			}
						
			emails[i][3] = emailList[i].getDate().toString();				
		}
		return emails;
	}
	
	private void getDataFromGateway(){
		//send the request for the list of folders
		String [] command = {"GET_FOLDER_LIST"};
		
		ObjectSender.SendObject(command, MessageTypes.CLIENT_COMMAND, getToGateway());
		myContainer container = ObjectSender.WaitForObjectNoTimeout(getToGateway());
		
		if (container.getMsgType() == MessageTypes.FOLDER_LIST ){
			folders = (Folder []) container.getPayload();			
		}
		
		command = new String [] {"GET_EMAIL_LIST", "INBOX"};
		
		ObjectSender.SendObject(command, MessageTypes.CLIENT_COMMAND, getToGateway());
		myContainer container2 = ObjectSender.WaitForObject(getToGateway());
		
		if (container2.getMsgType() == MessageTypes.MESSAGE_LIST){
			emails = (Email []) container2.getPayload();
		}			
		//else throw new IOException();			
	}
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent evt) {
						thisWindowClosing(evt);
					}
				});
			}
			{
				jPanel1 = new JPanel();
				BorderLayout jPanel1Layout = new BorderLayout();				
				getContentPane().add(jPanel1);
				jPanel1.setBounds(0, 37, 186, 316);
				jPanel1.setLayout(jPanel1Layout);
				{
					//FolderTree = new JTree();
					//jPanel1.add(FolderTree, BorderLayout.CENTER);
					//FolderTree.setPreferredSize(new java.awt.Dimension(186, 316));
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
					EmailTable.setPreferredSize(new java.awt.Dimension(418, 316));
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
					ComposeButton.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							ComposeButtonMouseClicked(evt);
						}
					});
				}
				{
					NewFolderButton = new JButton();
					jPanel3.add(NewFolderButton);
					NewFolderButton.setText("New Folder");
					NewFolderButton.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							NewFolderButtonMouseClicked(evt);
						}
					});
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
	
	private void EmailTableMouseClicked(MouseEvent evt) {
		if (evt.getClickCount() > 1){//double click
			
			String [] command = {"GET_EMAIL", EmailTable.getValueAt(EmailTable.getSelectedRow(),0 ).toString()};
			ObjectSender.SendObject(command, MessageTypes.CLIENT_COMMAND, getToGateway());
			
			myContainer container = ObjectSender.WaitForObject(getToGateway());
			
			if (container.getMsgType() == MessageTypes.MESSAGE){										
				ViewMessageDialog messageBody = 
					new ViewMessageDialog(null, 
							EmailTable.getValueAt(EmailTable.getSelectedRow(),1 ).toString(),
							(Email)container.getPayload());
				messageBody.setModalityType(ModalityType.APPLICATION_MODAL);
				messageBody.setVisible(true);
			}
		}
		

	}
	
	private void thisWindowClosing(WindowEvent evt) {
		System.out.println("this.windowClosing, event="+evt);
		System.exit(0);
		
	}
	
	private void FolderTreeValueChanged(TreeSelectionEvent evt) {
		System.out.println("FolderTree.valueChanged, event="+evt);
		if ( (TreePath)evt.getNewLeadSelectionPath() == null)
				return;
		DefaultMutableTreeNode currentNode = ((DefaultMutableTreeNode)evt.getNewLeadSelectionPath().getLastPathComponent());		
		Folder current = (Folder)currentNode.getUserObject();		
		String [] command = {"GET_EMAIL_LIST",current.getFldName()};
		
		ObjectSender.SendObject(command, MessageTypes.CLIENT_COMMAND, getToGateway());
		myContainer container2 = ObjectSender.WaitForObject(getToGateway());
		
		if (container2.getMsgType() == MessageTypes.MESSAGE_LIST){
			emails = (Email []) container2.getPayload();
			
		}
		String [] columns = {"Number", "Subject", "From", "Date"};					
		
		
	    TableModel model = new DefaultTableModel(parseEmailList(emails, "Inbox"), columns ) {
	        public boolean isCellEditable(int rowIndex, int mColIndex) {
	            return false;
	        }
	        
	    };
	    EmailTable.setModel(model);
	    
	    
		
	}
	
	private void ComposeButtonMouseClicked(MouseEvent evt) {
		System.out.println("ComposeButton.mouseClicked, event="+evt);
		ComposeDialog compose = new ComposeDialog(null, getToGateway(), SMTPhost, SMTPuser);
		compose.setModal(true);
		compose.setVisible(true);
	}
	
	private void NewFolderButtonMouseClicked(MouseEvent evt) {
		
		System.out.println("NewFolderButton.mouseClicked, event="+evt);
		if (FolderTree.getSelectionCount() != 1){
			JOptionPane.showMessageDialog(null, "Please select only one parent folder");			
		}
		else {
			String name = JOptionPane.showInputDialog("Please enter the folder name");
			String path = ((Folder)((DefaultMutableTreeNode)FolderTree.getSelectionPath().getLastPathComponent()).getUserObject()).getFldName() + "."+name;
			String [] command = new String [] {
				"CREATE_FOLDER",
				path
			};			
			ObjectSender.SendObject(command, MessageTypes.CLIENT_COMMAND, getToGateway());
			myContainer container = ObjectSender.WaitForObject(getToGateway());
			
			if (container.getMsgType() == MessageTypes.CONFIRMATION_OK){			
				getDataFromGateway();
								
				
				DefaultMutableTreeNode top = ((DefaultMutableTreeNode)FolderTree.getModel().getRoot());
				top.removeAllChildren();
				GenerateTree(folders, top);
				((DefaultTreeModel)FolderTree.getModel()).reload(top);
			}
			else JOptionPane.showMessageDialog(null, "Could not create folder");
		}
	}

}
