package stateandbehavior;

public class UpOrDownCounter 
{
	int counter;
	int stop;
	int increment;
	
	UpOrDownCounter(int start, int stop)
	{
		this.counter = start;
		this.stop = stop;
		
		// Angir hvilken vei vi skal telle ved bruk av "?", hvor resultatet er ( true : false )
		this.increment = (start < stop) ? 1 : -1;
	}
	
	public int getCounter()
	{
		return counter;
	}
	
	private boolean isFinished() 
	{
		if (increment == -1) 
		{
		// Dersom vi teller nedover
			if (counter <= stop) 
			{
				return true;
			}
			return false;
		}
		// Dersom vi teller oppover
		if (counter >= stop) 
		{
			return true;
		}
		return false;
	}
	
	public boolean count()
	{
		if (!isFinished()) 
		{	
			// legger til "increment"-verdien vår så lenge vi ikke er ferdige
			counter += increment;
		}
		// returnerer inversen av isFinished()
		return !isFinished();
	}
}