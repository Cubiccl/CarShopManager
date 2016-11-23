package fr.chaussadeFourrier.carshop.model;

public class Month implements Comparable<Month>
{
	public static final String[] MONTH_NAMES =
	{ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

	public final int month, year;

	public Month(int month, int year)
	{
		this.month = month;
		this.year = year;
	}

	public Month(String date)
	{
		this(Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(0, 4)));
	}

	@Override
	public int compareTo(Month o)
	{
		return (this.year - o.year) * 12 + (this.month - o.month);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Month)) return false;
		return this.month == ((Month) obj).month && this.year == ((Month) obj).year;
	}

	/** @param month - Another month.
	 * @return true if this month is lower than the input month. */
	public boolean lower(Month month)
	{
		if (this.year > month.year) return false;
		if (this.year < month.year) return true;
		return this.month < month.month;
	}

	@Override
	public String toString()
	{
		// return MONTH_NAMES[this.month - 1] + " " + this.year; Too big for graph :(
		return this.month + "/" + this.year;
	}
}