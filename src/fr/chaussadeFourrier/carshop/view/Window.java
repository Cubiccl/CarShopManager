package fr.chaussadeFourrier.carshop.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import fr.cubi.cubigui.CMenuItem;

public class Window extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 5103828148533113755L;
	/** IDs of the tabs.<br />
	 * <ul>
	 * <li>TAB_PRODUCTS=0 : Product Management.</li>
	 * <li>TAB_SALES=1 : Sales Management.</li>
	 * <li>TAB_FREE=2 : Free SQL Selections.</li>
	 * </ul> */
	public static final int TAB_PRODUCTS = 0, TAB_SALES = 1, TAB_FREE = 2;

	/** Menu buttons. */
	private CMenuItem menuProducts, menuSales, menuFree;

	public Window()
	{
		super("Car Shop Manager");
		this.setSize(900, 700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.createMenuBar();
		this.setTab(TAB_PRODUCTS);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.menuProducts) this.setTab(TAB_PRODUCTS);
		if (e.getSource() == this.menuSales) this.setTab(TAB_SALES);
		if (e.getSource() == this.menuFree) this.setTab(TAB_FREE);
	}

	/** Creates the window's menu bar. */
	private void createMenuBar()
	{

		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);

		menubar.add(this.menuProducts = new CMenuItem("Products"));
		menubar.add(this.menuSales = new CMenuItem("Sales"));
		menubar.add(this.menuFree = new CMenuItem("Free"));

		this.menuProducts.addActionListener(this);
		this.menuSales.addActionListener(this);
		this.menuFree.addActionListener(this);
	}

	/** Changes the current tab. Called when a menu button is pressed.
	 * 
	 * @param tabID - The ID of the new tab. See {@link Window#TAB_PRODUCTS}. */
	public void setTab(int tabID)
	{
		this.getContentPane().removeAll();
		switch (tabID)
		{
			case TAB_PRODUCTS:
				this.getContentPane().add(new ProductsPanel());
				break;

			case TAB_SALES:
				this.getContentPane().add(new SalesPanel());
				break;

			case TAB_FREE:
				this.getContentPane().add(new FreePanel());
				break;

			default:
				break;
		}
		this.revalidate();
	}

}
