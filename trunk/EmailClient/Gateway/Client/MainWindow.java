package Client;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
	private JTable jTable1;
	private JTree FolderTree;
	private JPanel jPanel3;
	private JPanel jPanel2;

	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				MainWindow inst = new MainWindow(frame);
				inst.setVisible(true);
			}
		});
	}
	
	public MainWindow(JFrame frame) {
		super(frame);
		initGUI();
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
					jTable1 = new JTable();
					jPanel2.add(jTable1, BorderLayout.CENTER);
					jTable1.setModel(jTable1Model);
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

}
