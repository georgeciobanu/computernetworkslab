

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import common.*;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

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
	private JFrame Frame;

	public ViewMessageDialog(JFrame frame, String subject, Email mail) {		
		super(frame);
		
		emailSubject = subject;
		email = mail;
		initGUI();
	}
	
	private void initGUI() {
		try {

			
			//setTitle();
			
		    JFrame Frame = new JFrame(getEmailSubject());
		    //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		    Container content = Frame.getContentPane();
		    String body = email.getBody();
		    String to   = email.getTo();
		    String from = email.getFrom();
		    String date = email.getDate();
		    String html = "<table border=\"0\">"+
						    "<tr>"+
						    "<td>From:\t</td>"+
						    "<td>"+from+"</td>"+
						    "</tr>"+
						    "<tr>"+
						    "<td>To:\t</td>"+
						    "<td>"+to+"</td>"+
						    "</tr>"+
						    "<tr>"+
						    "<td>Date:\t</td>"+
						    "<td>"+date+"</td>"+
						    "</tr>"+
						    "<tr>"+
						    body+
						    "</tr>"+
						    "</table>";
						    
		    
		    JEditorPane editor = new JEditorPane("text/html",html);
		    editor.setEditable(false);
		    JScrollPane scrollPane = new JScrollPane(editor);
		    content.add(scrollPane, BorderLayout.CENTER);
		    Frame.setSize(300, 200);
		    Frame.setVisible(true);
			
			
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
