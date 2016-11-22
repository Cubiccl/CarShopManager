package fr.chaussadeFourrier.carshop.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSplitPane;

import fr.cubi.cubigui.CButton;
import fr.cubi.cubigui.CComboBox;
import fr.cubi.cubigui.CEntry;
import fr.cubi.cubigui.CPanel;

public class SalesPanel extends JSplitPane implements ActionListener
{
	private static final long serialVersionUID = 4654999826343276791L;

	private CButton buttonLoad;
	private CComboBox comboboxCountry;
	private CEntry entryBeginDate, entryEndDate;
	private CPanel panelResult;

	public SalesPanel()
	{
		super(VERTICAL_SPLIT);
		CPanel panelTop = new CPanel("Criterias");

		panelTop.add(this.comboboxCountry = new CComboBox("All", "France", "United States of Antartica"));
		panelTop.add((this.entryBeginDate = new CEntry("Begin date :")).container);
		panelTop.add((this.entryEndDate = new CEntry("End date :")).container);
		panelTop.add(this.buttonLoad = new CButton("Load"));

		this.entryBeginDate.setColumns(6);
		this.entryEndDate.setColumns(6);

		this.buttonLoad.addActionListener(this);

		this.setTopComponent(panelTop);
		this.setBottomComponent(this.panelResult = new CPanel("Result"));
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Draw Graph
	}

}
