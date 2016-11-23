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

/** Panel displaying sales based on dates. */
public class SalesPanel extends JSplitPane implements ActionListener, FocusListener
{

	private static final long serialVersionUID = 4654999826343276791L;

	/** @return The list of countries where there have been sales. */
	private static String[] getCountries()
	{
		try
		{
			// Retrieving all countries from database
			ResultSet rs = Database.getConnection().createStatement().executeQuery("SELECT DISTINCT country FROM customers;");
			ArrayList<String> countries = new ArrayList<String>();
			while (rs.next())
				countries.add(rs.getString(1));

			// Sort countries alphabetically
			countries.sort(new Comparator<String>()
			{
				@Override
				public int compare(String o1, String o2)
				{
					return o1.compareTo(o2);
				}
			});

			// Add all before first
			String[] toreturn = new String[countries.size() + 1];
			toreturn[0] = "All";
			for (int i = 1; i < toreturn.length; ++i)
				toreturn[i] = countries.get(i - 1);
			return toreturn;

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return new String[]
		{ "All" };
	}

	/** @param date - date to test.
	 * @return true if the input date is a valid date. (Format: YYYY-MM-DD) */
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

	/** Button to load the graph when pressed. */
	private CButton buttonLoad;
	/** Combobox used to select teh country. */
	private CComboBox comboboxCountry;
	/** Dataset used to display the graph. */
	private DefaultCategoryDataset dataset;
	/** Entries to input the starting and ending date. */
	private CEntry entryBeginDate, entryEndDate;
	/** Panel containing the graph. */
	private CPanel panelResult;

	public SalesPanel()
	{
		super(VERTICAL_SPLIT);

		// Creating the top panel, with search criteria
		CPanel panelTop = new CPanel("Search criteria");
		panelTop.add(this.comboboxCountry = new CComboBox(getCountries()));
		panelTop.add((this.entryBeginDate = new CEntry("Begin date :", "DD-MM-YYYY")).container);
		panelTop.add((this.entryEndDate = new CEntry("End date :", "DD-MM-YYYY")).container);
		panelTop.add(this.buttonLoad = new CButton("Load"));

		this.entryBeginDate.setColumns(6);
		this.entryEndDate.setColumns(6);

		this.entryBeginDate.addFocusListener(this);
		this.entryEndDate.addFocusListener(this);
		this.buttonLoad.addActionListener(this);

		// Creating the bottom panel, with the graph
		JFreeChart chart = ChartFactory.createLineChart("Sales :", "Date", "Sales", this.dataset = new DefaultCategoryDataset());
		chart.getCategoryPlot().getDomainAxis().setTickLabelFont(new Font("Consolas", Font.PLAIN, 8));
		this.panelResult = new CPanel("Result");
		this.panelResult.setLayout(new BorderLayout());
		this.panelResult.add(new ChartPanel(chart), BorderLayout.CENTER);

		this.setTopComponent(panelTop);
		this.setBottomComponent(this.panelResult);
		this.setDividerSize(3);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Prepare calculations
		this.dataset.clear();
		String country = this.comboboxCountry.getValue();
		String start = Month.fromDMYtoYMD(this.entryBeginDate.getText());
		String end = Month.fromDMYtoYMD(this.entryEndDate.getText());
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
				this.dataset.addValue(map.get(m), "Sales", m);

		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}

	@Override
	public void focusGained(FocusEvent e)
	{
		if (((CEntry) e.getSource()).getText().trim().equals("DD-MM-YYYY")) ((CEntry) e.getSource()).setText("");

	}

	@Override
	public void focusLost(FocusEvent e)
	{
		if (((CEntry) e.getSource()).getText().trim().equals("")) ((CEntry) e.getSource()).setText("DD-MM-YYYY");

	}
}
