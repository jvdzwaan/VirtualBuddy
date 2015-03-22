package virtualbuddy;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

/**
 * Adapted from wozBuddy.TextInputPanel
 * @author Janneke van der Zwaan
 */
public class TextInputPanel extends JPanel {

	public JTextArea input; 
	public JButton send;

	public TextInputPanel(){
		super();

		Border blackline = BorderFactory.createLineBorder(Color.gray);
		Border innerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border compound = BorderFactory.createCompoundBorder(blackline, innerBorder);

		send = new JButton("Zenden");

		input = new JTextArea(3, 25);
		input.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
		input.getActionMap().put("enter",
				new AbstractAction("enter") {
			public void actionPerformed(ActionEvent evt) {
				//client.send();
			}
		}
		);
		input.getInputMap().put(KeyStroke.getKeyStroke("shift ENTER"), "newline");
		input.getActionMap().put("newline",
				new AbstractAction("newline") {
			public void actionPerformed(ActionEvent evt) {
				input.append("\n");
				//input.setCaretPosition(input.getText().length() - 1);
				//TODO actie moet iets doen in chatwindow en in enviroment
			}
		}
		);
		
		input.setBorder(compound);
		input.setWrapStyleWord(true);
		input.setLineWrap(true);

		setLayout(new GridBagLayout());
		GridBagConstraints c;

		// input text
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 5, 10, 5);
		c.anchor = GridBagConstraints.LINE_START;

		add(input, c);

		// send button
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0, 5, 8, 5);

		add(send, c);
	}

	public void disableEnterKey(){
		input.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "none");
		input.getInputMap().put(KeyStroke.getKeyStroke("shift ENTER"), "none");

	}

}
