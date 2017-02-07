package stateandbehavior;

public class Digit 
{
	private int tallsystem;
	private int verdi;
	private boolean endt;
	
	public Digit()
	{
		tallsystem = 10;
		verdi = 0;
		endt = false;
	}
	
	public Digit(int tallsystem)
	{
		this.tallsystem = tallsystem;
		verdi = 0;
		endt = false;
	}
	
	public Digit(int tallsystem, int verdi)
	{
		this.tallsystem = tallsystem;
		this.verdi = verdi;
		if (verdi < tallsystem)
		{
			endt = false;
		}
		else
		{
			endt = true;
		}
	}
	
	private boolean checkEnd()
	{
		if (verdi == tallsystem)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean increment()
	{
		if (!endt)
		{
			verdi ++;
			boolean ferdig = checkEnd();
			if (ferdig)
			{
				verdi = 0;
				endt = ferdig;
			}
			return ferdig;
		}
		else if (endt)
		{
			verdi = 0;
			return endt;
		}
	}
	
	int getValue()
	{
		return this.verdi;
	}
	
	int getBase()
	{
		return this.tallsystem;
	}
}
