

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import common.*;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;

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
public class ViewMessageDialog extends javax.swing.JDialog {

	/**
	* Auto-generated main method to display this JDialog
	*/

	private String emailSubject = null;
	private JPanel BodyPanel;
	private JLabel fromLabel;
	private JScrollPane jScrollPane1;
	private JEditorPane jEditorPane1;
	private JLabel dateLabel;
	private JLabel toLabel;
	private JPanel InfoLine;
	private Email email;

	public ViewMessageDialog(JFrame frame, String subject, Email mail) {		
		super(frame);
		emailSubject = subject;
		email = mail;
		initGUI();
	}
	
	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			{
				InfoLine = new JPanel();
				getContentPane().add(InfoLine);
				InfoLine.setBounds(0, 0, 392, 29);
				{
					fromLabel = new JLabel();
					InfoLine.add(fromLabel);
					fromLabel.setText("From:");
				}
				{
					toLabel = new JLabel();
					InfoLine.add(toLabel);
					toLabel.setText("To:");
				}
				{
					dateLabel = new JLabel();
					InfoLine.add(dateLabel);
					dateLabel.setText("Date:");
				}
			}
			{
				BodyPanel = new JPanel();
				getContentPane().add(BodyPanel);
				BodyPanel.setMinimumSize(new java.awt.Dimension(16, 100));
				BodyPanel.setBounds(0, 29, 392, 236);
				{
					jScrollPane1 = new JScrollPane();
					BodyPanel.add(jScrollPane1);
					jScrollPane1.setPreferredSize(new java.awt.Dimension(389, 232));
					jScrollPane1.getHorizontalScrollBar().setPreferredSize(new java.awt.Dimension(386, 15));
					{
						jEditorPane1 = new JEditorPane();
						jScrollPane1.setViewportView(jEditorPane1);
						jEditorPane1.setText("jEditorPane1");
						jEditorPane1.setPreferredSize(new java.awt.Dimension(389, 176));
					}
				}
			}
			setSize(400, 300);
			
			setTitle(getEmailSubject());
			jEditorPane1.setText(email.getBody());
			fromLabel.setText(email.getFrom());
			toLabel.setText(email.getTo());
			dateLabel.setText(email.getDate());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

}
