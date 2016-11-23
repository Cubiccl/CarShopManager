package fr.chaussadeFourrier.carshop.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JSplitPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import fr.chaussadeFourrier.carshop.controller.Database;
import fr.cubi.cubigui.CButton;
import fr.cubi.cubigui.CComboBox;
import fr.cubi.cubigui.CEntry;
import fr.cubi.cubigui.CPanel;

public class SalesPanel extends JSplitPane implements ActionListener
{
	private static final long serialVersionUID = 4654999826343276791L;
	private static final String SERIES = "SALES";

	/** @param date - date to test.
	 * @return true if the input date is a valid date. */
	private static boolean isDateValid(String date)
	{
		try
		{
			String[] parts = date.split("-");
			if (parts[0].length() != 4 || Integer.parseInt(parts[0]) < 0) return false;
			int month = Integer.parseInt(parts[1]);
			if (parts[1].length() != 2 || month < 1 || month > 12) return false;
			int day = Integer.parseInt(parts[1]);
			if (parts[2].length() != 2 || day < 1 || day > 31) return false;
		} catch (Exception e)
		{
			return false;
		}
		return true;
	}

	private CButton buttonLoad;
	private CComboBox comboboxCountry;
	private DefaultCategoryDataset dataset;
	private CEntry entryBeginDate, entryEndDate;

	private CPanel panelResult;

	public SalesPanel()
	{
		super(VERTICAL_SPLIT);
		CPanel panelTop = new CPanel("Criterias");

		panelTop.add(this.comboboxCountry = new CComboBox("All", "France", "United States of Antartica"));
		panelTop.add((this.entryBeginDate = new CEntry("Begin date :", "dd-mm-yyyy")).container);
		panelTop.add((this.entryEndDate = new CEntry("End date :", "dd-mm-yyyy")).container);
		panelTop.add(this.buttonLoad = new CButton("Load"));

		this.entryBeginDate.setColumns(6);
		this.entryEndDate.setColumns(6);

		this.buttonLoad.addActionListener(this);

		this.panelResult = new CPanel("Result");
		this.panelResult.add(new ChartPanel(ChartFactory.createLineChart("Sales :", "Date", "Sales", this.dataset = new DefaultCategoryDataset())));

		this.setTopComponent(panelTop);
		this.setBottomComponent(this.panelResult);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.dataset.clear();
		String country = this.comboboxCountry.getValue();
		String start = this.entryBeginDate.getText();
		String end = this.entryEndDate.getText();
		boolean hasCountry = !country.equals("All");
		boolean hasStart = isDateValid(start), hasEnd = isDateValid(end);

		String request = "SELECT orders.orderNumber, orderDate, priceEach * quantityOrdered FROM customers JOIN orders JOIN orderDetails";
		if (hasCountry || hasStart || hasEnd) request += " WHERE";

		if (hasCountry) request += " country = '" + country + "'";

		if (hasStart || hasEnd)
		{
			if (hasCountry) request += " AND";
			if (hasStart) request += " orderDate >= '" + start + "'";
			if (hasStart && hasEnd) request += " AND";
			if (hasEnd) request += " orderDate <= '" + end + "'";
		}

		request += ";";
		System.out.println("Request is: " + request);
		/*
		try
		{
			ResultSet rs = Database.getConnection().createStatement().executeQuery(request);
			System.out.println("done");
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}*/
	}
}
