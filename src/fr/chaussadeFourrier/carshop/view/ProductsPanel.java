package fr.chaussadeFourrier.carshop.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.chaussadeFourrier.carshop.Main;
import fr.chaussadeFourrier.carshop.controller.Database;
import fr.chaussadeFourrier.carshop.controller.ProductDAO;
import fr.chaussadeFourrier.carshop.model.Product;
import fr.cubi.cubigui.CButton;
import fr.cubi.cubigui.CEntry;
import fr.cubi.cubigui.CLabel;
import fr.cubi.cubigui.CPanel;
import fr.cubi.cubigui.CTable;
import fr.cubi.cubigui.CTextArea;

public class ProductsPanel extends JSplitPane implements ActionListener, ListSelectionListener
{
	private static final String[] COLUMNS =
	{ "Product code", "Product Name", "Quantity in Stock", "Buy Price" };
	private static final long serialVersionUID = -7058499330472853742L;

	private CTextArea areaDescription, areaProductLineDescription;
	private CButton buttonShow, buttonCancel, buttonValidate;
	private boolean detailsShowed;
	private CEntry entryName, entryQuantity, entryPrice;
	private CLabel labelCode, labelVendor, labelMSRP;
	private JLabel labelImage;
	private CPanel panelList, panelDetails;
	private JScrollPane scrollpaneTable, scrollpaneDetails;
	private CTable table;

	public ProductsPanel()
	{
		super(VERTICAL_SPLIT);

		this.detailsShowed = true;

		this.table = new CTable(this.getData(), COLUMNS)
		{
			private static final long serialVersionUID = -6588307240212217769L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				// prevent cell editing
				return false;
			}
		};
		this.buttonShow = new CButton("Show details");

		this.panelList = new CPanel("Product list");
		this.panelDetails = new CPanel("Product details");

		GridBagConstraints gbc = this.panelList.createGridBagLayout();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTH;
		this.panelList.add(this.scrollpaneTable = new JScrollPane(this.table.container), gbc);
		this.scrollpaneTable.getVerticalScrollBar().setUnitIncrement(20);
		this.scrollpaneTable.setBorder(null);

		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		this.panelList.add(this.buttonShow, gbc);

		gbc = this.panelDetails.createGridBagLayout();
		gbc.anchor = GridBagConstraints.NORTH;
		++gbc.gridwidth;
		this.panelDetails.add((this.entryName = new CEntry("Product name :", "Car 1")).container, gbc);

		++gbc.gridy;
		this.panelDetails.add((this.entryQuantity = new CEntry("Quantity in Stock :", "42")).container, gbc);

		++gbc.gridy;
		this.panelDetails.add((this.entryPrice = new CEntry("Price :", "1337")).container, gbc);

		++gbc.gridy;
		--gbc.gridwidth;
		gbc.anchor = GridBagConstraints.WEST;
		this.panelDetails.add(new CLabel("Product description :"), gbc);
		++gbc.gridx;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JScrollPane scrollpane = new JScrollPane(this.areaDescription = new CTextArea("This is the car's description."));
		scrollpane.setPreferredSize(new Dimension(400, 100));
		this.panelDetails.add(scrollpane, gbc);
		this.areaDescription.setEditable(true);

		--gbc.gridx;
		++gbc.gridy;
		this.panelDetails.add(this.labelCode = new CLabel("Product code : 001"), gbc);
		++gbc.gridx;
		this.panelDetails.add(this.labelVendor = new CLabel("Product vendor : someone"), gbc);

		--gbc.gridx;
		++gbc.gridy;
		++gbc.gridwidth;
		gbc.fill = GridBagConstraints.NONE;
		this.panelDetails.add(this.labelImage = new JLabel(), gbc);

		++gbc.gridy;
		this.panelDetails.add(this.labelMSRP = new CLabel("MSRP: 16784312"), gbc);

		++gbc.gridy;
		this.panelDetails.add(this.areaProductLineDescription = new CTextArea("Product line: This is the product line's description."), gbc);

		++gbc.gridy;
		--gbc.gridwidth;
		gbc.insets = new Insets(50, 5, 5, 5);
		gbc.anchor = GridBagConstraints.EAST;
		this.panelDetails.add(this.buttonValidate = new CButton("Validate changes"), gbc);
		++gbc.gridx;
		gbc.anchor = GridBagConstraints.WEST;
		this.panelDetails.add(this.buttonCancel = new CButton("Cancel changes"), gbc);

		this.table.getSelectionModel().addListSelectionListener(this);
		this.buttonShow.addActionListener(this);
		this.buttonValidate.addActionListener(this);
		this.buttonCancel.addActionListener(this);

		this.setTopComponent(this.panelList);
		this.setBottomComponent(this.scrollpaneDetails = new JScrollPane(this.panelDetails));
		this.scrollpaneDetails.getVerticalScrollBar().setUnitIncrement(20);
		this.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent pce)
			{
				resizeTable();
			}
		});
		this.toggleDetails();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.buttonShow)
		{
			if (this.table.getSelectedRow() != -1) this.toggleDetails();
		} else if (e.getSource() == this.buttonValidate) this.applyChanges();
		else if (e.getSource() == this.buttonCancel) this.showDetails(this.selectedProduct());
	}

	private void applyChanges()
	{
		Product p = this.selectedProduct();
		p.setProductName(this.entryName.getText());
		p.setProductDescription(this.areaDescription.getText());
		p.setQuantityInStock((int) Double.parseDouble(this.entryQuantity.getText()));
		p.setBuyPrice(Double.parseDouble(this.entryPrice.getText()));
		new ProductDAO(Database.getConnection()).update(p);
		Main.getWindow().setTab(Window.TAB_PRODUCTS);
	}

	private String[][] getData()
	{
		try
		{
			ResultSet rs = Database.getConnection().createStatement().executeQuery("SELECT productCode, productName, quantityInStock, buyPrice FROM products;");
			rs.last();
			int size = rs.getRow();
			rs.beforeFirst();
			String[][] data = new String[size][4];
			int curr = 0;
			while (rs.next())
			{
				data[curr] = new String[]
				{ rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) };
				++curr;
			}
			return data;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return new String[0][0];
	}

	private void resizeTable()
	{
		this.setDividerLocation(Math.max(200, this.getDividerLocation()));
		this.scrollpaneTable.setPreferredSize(new Dimension(this.table.getWidth() + 30, this.getDividerLocation() - 100));
	}

	private Product selectedProduct()
	{
		return new ProductDAO(Database.getConnection()).find((String) this.table.getValueAt(this.table.getSelectedRow(), 0));
	}

	public void showDetails(Product product)
	{
		if (product == null) return;
		this.entryName.setText(product.getProductName());
		this.entryQuantity.setText(Double.toString(product.getQuantityInStock()));
		this.entryPrice.setText(Double.toString(product.getBuyPrice()));

		this.areaDescription.setText(product.getProductDescription());
		this.areaProductLineDescription.setText(product.getProductLine());

		this.labelCode.setText("Product code :" + product.getProductCode());
		this.labelVendor.setText("Product vendor :" + product.getProductVendor());
		this.labelMSRP.setText("Product MSRP :" + product.getMSRP());

		if (product.getPhoto() == null) this.labelImage.setText("[image unavailable]");
		else this.labelImage.setText("");
		this.labelImage.setIcon(product.getPhoto());
	}

	private void toggleDetails()
	{
		this.detailsShowed = !this.detailsShowed;
		this.scrollpaneDetails.setVisible(this.detailsShowed);
		if (this.detailsShowed)
		{
			this.buttonShow.setText("Hide details");
			this.setDividerLocation(this.getHeight() / 2);
		} else this.buttonShow.setText("Show details");
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		this.showDetails(this.selectedProduct());
	}

}
