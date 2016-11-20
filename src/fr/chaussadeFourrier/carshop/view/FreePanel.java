package fr.chaussadeFourrier.carshop.view;

import java.awt.GridLayout;



import fr.cubi.cubigui.CPanel;
import fr.cubi.cubigui.CTextArea;
import fr.cubi.cubigui.CTextField;

public class FreePanel extends CPanel
{
	private static final long serialVersionUID = 2163073256397683674L;
	private CTextField textField;
	private CTextArea textArea;
	
	public FreePanel()
	{
		super("Free SQL");
		this.setLayout(new GridLayout(2,1));
		textField = new CTextField();
		textField.setSize(800,200);
		textField.setText("test");
		textArea = new CTextArea("test2");
		this.add(textField);
		this.add(textArea);
		this.setVisible(true);
	}

}
