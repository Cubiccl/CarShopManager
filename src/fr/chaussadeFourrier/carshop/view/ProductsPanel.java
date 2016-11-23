package fr.chaussadeFourrier.carshop.view;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.chaussadeFourrier.carshop.controller.Database;
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
	private CTable table;

	public ProductsPanel()
	{
		super(VERTICAL_SPLIT);

		this.detailsShowed = false;

		this.table = new CTable(new String[0][0], COLUMNS)
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
		this.panelDetails.setVisible(false);

		GridBagConstraints gbc = this.panelList.createGridBagLayout();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTH;
		this.panelList.add(this.table.container, gbc);

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
		this.panelDetails.add(this.areaDescription = new CTextArea("This is the car's description."), gbc);
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
		this.panelDetails.add(this.buttonValidate = new CButton("Validate changes"), gbc);
		++gbc.gridx;
		this.panelDetails.add(this.buttonCancel = new CButton("Cancel changes"), gbc);

		this.table.getSelectionModel().addListSelectionListener(this);
		this.buttonShow.addActionListener(this);
		this.buttonValidate.addActionListener(this);
		this.buttonCancel.addActionListener(this);

		this.setTopComponent(new JScrollPane(this.panelList));
		this.setBottomComponent(new JScrollPane(this.panelDetails));
		this.updateProducts();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.buttonShow) this.toggleDetails();
		if (e.getSource() == this.buttonValidate) this.applyChanges();
		if (e.getSource() == this.buttonCancel) this.showDetails(this.selectedProduct());
	}

	private void applyChanges()
	{
		// TODO Auto-generated method stub

	}

	private Product selectedProduct()
	{
		System.out.println("Selected: " + this.table.getSelectedRow());
		// TODO Auto-generated method stub
		return null;
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

		this.labelImage.setIcon(product.getPhoto());
	}

	private void toggleDetails()
	{
		this.detailsShowed = !this.detailsShowed;
		this.panelDetails.setVisible(this.detailsShowed);
		if (this.detailsShowed) this.buttonShow.setText("Hide details");
		else this.buttonShow.setText("Show details");
	}

	private void updateProducts()
	{
		//ResultSet rs= Database.getConnection().createStatement().executeQuery("select");
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		this.showDetails(this.selectedProduct());
	}

}
