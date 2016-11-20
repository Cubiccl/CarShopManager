package fr.chaussadeFourrier.carshop.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import fr.cubi.cubigui.CButton;
import fr.cubi.cubigui.CPanel;
import fr.cubi.cubigui.CTextArea;

public class FreePanel extends JSplitPane implements ActionListener, KeyListener{
	private static final long serialVersionUID = 2163073256397683674L;
	private CPanel entry;
	private CPanel display;
	private CTextArea textField;
	private CTextArea textArea;
	private CButton button;

	public FreePanel() {
		super(JSplitPane.VERTICAL_SPLIT);
		entry = new CPanel("Free SQL");
		entry.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		display = new CPanel("Result");
		display.setLayout(new GridBagLayout());
		textField = new CTextArea("");
		textField.setEditable(true);
		textField.setText("Insert request here (select only)");
		button = new CButton("Validate");
		textArea = new CTextArea("test");
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		entry.add(textField,c);
		display.add(textArea, c);
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.insets = (new Insets(5,0,5,5));
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.weighty = 0;
		entry.add(button, c);
		button.addActionListener(this);
		textField.addKeyListener(this);
		
		this.setTopComponent(new JScrollPane(entry));
		this.setBottomComponent(new JScrollPane(display));
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String text = textField.getText().toLowerCase();
		if (!text.substring(0,6).equals("select") || text.contains(";"))
			return;
		textArea.setText("Hi !");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == e.VK_ENTER || e.getKeyCode() == e.VK_BACK_SPACE)
			this.resetToPreferredSizes();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == e.VK_ENTER || e.getKeyCode() == e.VK_BACK_SPACE)
			this.resetToPreferredSizes();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
