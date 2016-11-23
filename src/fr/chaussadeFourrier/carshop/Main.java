package fr.chaussadeFourrier.carshop;

import java.sql.Connection;

import javax.swing.SwingUtilities;

import fr.chaussadeFourrier.carshop.controller.Database;
import fr.chaussadeFourrier.carshop.view.Window;
import fr.cubi.cubigui.DisplayUtils;

public class Main
{
	private static Window window;

	/** @return The program's window. */
	public static Window getWindow()
	{
		return window;
	}

	public static void main(String[] args)
	{
		Connection connection = Database.getConnection();
		if (connection == null)
		{
			DisplayUtils.showMessage(null, "Error", "Could not connect to database. Exiting");
			Database.closeConnection();
		}

		SwingUtilities.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				window = new Window();
				window.setVisible(true);
			}
		});
	}

}
