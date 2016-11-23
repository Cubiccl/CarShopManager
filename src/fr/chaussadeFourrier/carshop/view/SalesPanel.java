package fr.chaussadeFourrier.carshop.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.JSplitPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import fr.chaussadeFourrier.carshop.controller.Database;
import fr.chaussadeFourrier.carshop.model.Month;
import fr.cubi.cubigui.CButton;
import fr.cubi.cubigui.CComboBox;
import fr.cubi.cubigui.CEntry;
import fr.cubi.cubigui.CPanel;

public class SalesPanel extends JSplitPane implements ActionListener, FocusListener
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
		panelTop.add((this.entryBeginDate = new CEntry("Begin date :", "YYYY-MM-DD")).container);
		panelTop.add((this.entryEndDate = new CEntry("End date :", "YYYY-MM-DD")).container);
		panelTop.add(this.buttonLoad = new CButton("Load"));

		this.entryBeginDate.setColumns(6);
		this.entryEndDate.setColumns(6);

		this.entryBeginDate.addFocusListener(this);
		this.entryEndDate.addFocusListener(this);
		this.buttonLoad.addActionListener(this);

		JFreeChart chart = ChartFactory.createLineChart("Sales :", "Date", "Sales", this.dataset = new DefaultCategoryDataset());
		chart.getCategoryPlot().getDomainAxis().setTickLabelFont(new Font("Consolas", Font.PLAIN, 8));
		this.panelResult = new CPanel("Result");
		this.panelResult.setLayout(new BorderLayout());
		this.panelResult.add(new ChartPanel(chart), BorderLayout.CENTER);

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

		// Create SQL query
		String query = "SELECT orderDate, priceEach * quantityOrdered FROM customers JOIN orders ON customers.customerNumber = orders.customerNumber JOIN orderDetails ON orders.orderNumber = orderDetails.orderNumber";
		if (hasCountry || hasStart || hasEnd) query += " WHERE";

		if (hasCountry) query += " country = '" + country + "'";

		if (hasStart || hasEnd)
		{
			if (hasCountry) query += " AND";
			if (hasStart) query += " orderDate >= '" + start + "'";
			if (hasStart && hasEnd) query += " AND";
			if (hasEnd) query += " orderDate <= '" + end + "'";
		}

		query += ";";
		System.out.println("Request is: " + query);

		try
		{
			ResultSet rs = Database.getConnection().createStatement().executeQuery(query);
			HashMap<Month, Double> map = new HashMap<Month, Double>();
			double sales = 0;
			Month month = null;

			// Sum up all sales per month
			while (rs.next())
			{
				month = new Month(rs.getString(1));
				for (Month m : map.keySet())
					if (m.equals(month))
					{
						month = m;
						break;
					}
				sales = rs.getDouble(2);
				if (map.containsKey(month)) sales += map.get(month);
				map.put(month, sales);
			}

			// Sort months
			ArrayList<Month> times = new ArrayList<Month>();
			times.addAll(map.keySet());
			times.sort(new Comparator<Month>()
			{
				@Override
				public int compare(Month o1, Month o2)
				{
					return o1.compareTo(o2);
				}
			});

			// Insert data
			for (Month m : times)
				this.dataset.addValue(map.get(m), SERIES, m);

		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}

	@Override
	public void focusGained(FocusEvent e)
	{
		if (((CEntry) e.getSource()).getText().trim().equals("YYYY-MM-DD")) ((CEntry) e.getSource()).setText("");

	}

	@Override
	public void focusLost(FocusEvent e)
	{
		if (((CEntry) e.getSource()).getText().trim().equals("")) ((CEntry) e.getSource()).setText("YYYY-MM-DD");

	}
}
