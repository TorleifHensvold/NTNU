package encapsulation;


//Hadde så mye styr med denne for å trikse til, men som endte opp enkelt at jeg ikke orker å skrive all kommenteringa.


public class Nim 
{
	
	private int numberOfPiles;
	private int[] piles;
	private int pileSize;
	
	public Nim()
	{
		this.numberOfPiles = 3;
		this.piles = new int[this.numberOfPiles];
		this.pileSize = 10;
		fillPiles();
	}
	
	public Nim(int pileSize)
	{
		this.numberOfPiles = 3;
		this.piles = new int[this.numberOfPiles];
		this.pileSize = pileSize;
		fillPiles();
	}
	
	private void fillPiles()
	{
		for (int i = 0; i < this.piles.length; i++) 
		{
			piles[i] = this.pileSize;
		}
	}
	
	public boolean isValidMove(int number, int targetPile)
	{
		if (isGameOver())
		{
			return false;
		}
		if (number < 1)
		{
			return false;
		}
		if (checkIfValidPile(targetPile, false))
		{
			if (number <= this.piles[targetPile])
			{
				return true;
			}
			return false;
		}
		return false;
	}
	
	public void removePieces(int number, int targetPile)
	{
		if (isGameOver())
		{
			throw new IllegalStateException("Fuck off.");
		}
		if (!isValidMove(number, targetPile))
		{
			throw new IllegalArgumentException("C'mon");
		}
		this.piles[targetPile] -= number;
	}
	
	private boolean checkIfValidPile(int targetPile, boolean throwException)
	{
		if (targetPile < 0 || targetPile >= this.numberOfPiles)
		{
			if (throwException)
			{
				throw new IllegalArgumentException("The pile must exist.");
			}
			return false;
		}
		return true;
	}
	
	public boolean isGameOver()
	{
		for (int i = 0; i < this.piles.length; i++) 
		{
			if (this.piles[i] == 0)
			{
				return true;
			}
		}
		return false;
	}
	
	public int getPile(int targetPile)
	{
		if (!checkIfValidPile(targetPile, false))
		{
			throw new IllegalArgumentException("The pile doesn't exist.");
		}
		return this.piles[targetPile];
	}
	
	public String toString()
	{
		String tilbakesend = "";
		for (int i = 0; i < piles.length; i++) 
		{
			tilbakesend += String.format("Pile %: % pieces left.\n", i, piles[i]);
		}
		return tilbakesend;
	}
	
	
	
}
