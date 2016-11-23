package fr.chaussadeFourrier.carshop.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import fr.chaussadeFourrier.carshop.controller.Database;
import fr.cubi.cubigui.CButton;
import fr.cubi.cubigui.CPanel;
import fr.cubi.cubigui.CTable;
import fr.cubi.cubigui.CTextArea;

public class FreePanel extends JSplitPane implements ActionListener,
		KeyListener, FocusListener {
	private static final long serialVersionUID = 2163073256397683674L;
	private CPanel entry;
	private CPanel display;
	private CTextArea textField;
	private CTextArea textArea;
	private CButton button;
	private CButton reset;
	private ChartPanel chart;
	private CTable table;

	public FreePanel() {
		/*
		 * Setting up graphics
		 */
		super(JSplitPane.VERTICAL_SPLIT);
		entry = new CPanel("Free SQL");
		entry.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		display = new CPanel("Result");
		display.setLayout(new GridBagLayout());
		textField = new CTextArea("");
		textField.setEditable(true);
		textField.setText("Insert query here (select only)");
		button = new CButton("Validate");
		reset = new CButton("Reset Display");
		textArea = new CTextArea("");

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		entry.add(textField, c);
		display.add(textArea, c);

		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.insets = (new Insets(5, 0, 5, 5));
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 0;
		entry.add(button, c);

		c.anchor = GridBagConstraints.WEST;
		c.insets = (new Insets(5, 5, 5, 0));
		c.gridx = 0;
		entry.add(reset, c);

		button.addActionListener(this);
		reset.addActionListener(this);
		textField.addKeyListener(this);
		textField.addFocusListener(this);

		this.setDividerSize(3);
		this.setTopComponent(new JScrollPane(entry));
		this.setBottomComponent(new JScrollPane(display));
		this.setVisible(true);
	}

	
	//Save the data in a log file
	public void saveToFile(String query, ResultSet rs) {
		try {
			//Put lines of the log file in a buffer and rewrite them to position pointer
			String temp;
			ArrayList<String> lines = new ArrayList<String>();
			File file = new File("Log.txt");
			if(!file.exists())
				file.createNewFile();
			FileReader fr = new FileReader(file.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			while ((temp = br.readLine()) != null) {
				lines.add(temp);
			}
			br.close();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < lines.size(); i++) {
				bw.write(lines.get(i));
				bw.write(System.getProperty("line.separator"));
			}
			
			//write query
				bw.write(query);
				bw.write(System.getProperty("line.separator"));
				bw.write(System.getProperty("line.separator"));
			
			//if query isn't correct, write the error message in the file	
			if(rs == null){
				bw.write("Erreur : Requête invalide !");
				bw.write(System.getProperty("line.separator"));
				bw.write(System.getProperty("line.separator"));
				bw.write(System.getProperty("line.separator"));
				bw.close();
				return;
			}
			
			//else, write the result
			rs.beforeFirst();
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				bw.write(rsmd.getColumnName(i));
				for (int j = 0; j < (rsmd.getColumnDisplaySize(i) - rsmd
						.getColumnName(i).length()); j++)
					bw.write(" ");
				bw.write("|");
			}
			bw.write(System.getProperty("line.separator"));
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				for (int j = 0; j < Math.max(rsmd.getColumnDisplaySize(i), rsmd
						.getColumnName(i).length()); j++)
					bw.write("-");
				bw.write("+");
			}
			bw.write(System.getProperty("line.separator"));
			int x = 0;
			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {					
					if (rs.getString(i) == null)
						x = 0;
					else{
						x = rs.getString(i).length();
						bw.write(rs.getString(i));
					}
						
					for (int j = 0; j < Math.max(rsmd.getColumnDisplaySize(i), rsmd
							.getColumnName(i).length())
							- x; j++)
						bw.write(" ");
					bw.write("|");
				}
				bw.write(System.getProperty("line.separator"));
			}
			bw.write(System.getProperty("line.separator"));
			bw.write(System.getProperty("line.separator"));
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// clear the display area when reset is pressed
		if (e.getSource() == reset) {
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.weightx = 1;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 0;
			display.removeAll();
			table = null;
			chart = null;
			display.add(textArea, c);
			display.revalidate();
			display.repaint();
			this.resetToPreferredSizes();
			return;
		}

		/*
		 * clean statement and prevent some easy sql injection
		 */
		String text = textField.getText().toLowerCase();
		int index = text.indexOf("select");
		switch (index) {
		case -1:
			return;

		case 0:
			break;

		default:
			text = text.substring(index);
			break;
		}
		
		text = text.replace(";"," ");
		text = text.replaceAll("\n", " ");
		text = text.replaceAll(" +"," ");				
		
		textField.setText(text);
		this.setDividerLocation(88);
		
		
		// Connect to database
		Connection connect = Database.getConnection();
		try {
			// Execute query
			ResultSet rs = connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(text);
			ResultSetMetaData rsmd = rs.getMetaData();
			String type = "";

			saveToFile(text, rs);
			

			// For Pie Chart if possible

			if (rsmd.getColumnCount() == 2) {
				type = rsmd.getColumnTypeName(2);
			}
			System.out.println(type);
			if (type == "INT" || type == "FLOAT" || type == "DOUBLE"
					|| type == "TINYINT" || type == "MEDIUMINT"
					|| type == "SMALLINT" || type == "BIGINT"
					|| type == "DECIMAL" || type == "BOOLEAN" || type == "REAL"
					|| type == "BIT" || type == "SERIAL") {
				display.removeAll();
				rs.beforeFirst();
				DefaultPieDataset pc = new DefaultPieDataset();
				while (rs.next()) {
					if(type == "DOUBLE")
						pc.setValue(rs.getString(1), rs.getDouble(2));
					if(type == "INT")
						pc.setValue(rs.getString(1), (double)rs.getInt(2));
				}
				JFreeChart pc2 = ChartFactory.createPieChart(
						rsmd.getColumnName(1), pc, true, true, true);
				chart = new ChartPanel(pc2);
				chart.getChart().removeLegend();
				display.add(chart);
				display.revalidate();
				display.repaint();
				return;
			}

			// Setting up array (if Pie Chart impossible)
			rs.last();
			int rows = rs.getRow();
			rs.beforeFirst();
			String[][] data = new String[rows][rsmd.getColumnCount()];
			String[] names = new String[rsmd.getColumnCount()];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				names[i] = rsmd.getColumnName(i + 1);
			}
			for (int i = 0; i < rows; i++) {
				rs.next();
				for (int j = 0; j < rsmd.getColumnCount(); j++) {
					data[i][j] = rs.getString(j + 1);
				}
			}

			table = new CTable(data, names);

			// Display
			display.removeAll();
			GridBagConstraints c = new GridBagConstraints();
			c.weightx = 0;
			c.weighty = 0;
			c.gridx = 0;
			c.gridy = 0;
			display.add(table.getTableHeader(), c);
			c.gridx = 0;
			c.gridy = 1;
			display.add(table, c);
			display.revalidate();
			display.repaint();

			// Tells if query isn't correct
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Requête invalide !", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			saveToFile(text, null);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	/*
	 * Because it's cool
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		if ((table == null || (table != null && table.getRowCount() < 24)) && chart == null)
			this.resetToPreferredSizes();
	}

	/*
	 * Because it's cool too !
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if (textField.getText().trim()
				.equals("Insert query here (select only)"))
			textField.setText("");

	}

	@Override
	public void focusLost(FocusEvent e) {
		if (textField.getText().trim().equals(""))
			textField.setText("Insert query here (select only)");

	}

}
