package encapsulation;

public class Account 
{
	private double balance; // Holder rede på hvor mye vi har på konto
	private double interestRate; // Holder rede på hva intrestraten er, oppgitt i %-poeng
	
	private boolean checkIfPositive(double value, boolean throwException)
	{
		if (value < 0)	//sjekker om verdien er mindre enn 0
		{
			if (throwException)	//sjekker om vi skal kaste exception
			{
				throw new IllegalArgumentException("The value must be positive to be valid.");
			}
			return false;	//hvis vi ikke skulle kaste exception sender vi false
		}
		return true;	// hvis value er større enn 0 returnerer vi true
	}
	
	private boolean checkIfNegative(double value, boolean throwException)
	{
		if (value < 0)	//samme opplegg som checkIfPositive, men andre veien
		{
			if (throwException)
			{
				throw new IllegalArgumentException("The value must be negative to be valid.");
			}
			return false;
		}
		return true;
	}
	
	/*
	private boolean checkValidSubtractionToZero(double baseValue, double subtractionValue, boolean throwException)
	{
		// sjekker at det vi skal subtrahere er negativt, det vi skal subtrahere fra er positivt 
		if (checkIfNegative(subtractionValue, throwException) && checkIfPositive(baseValue, throwException))
		{
			// sjekker om vi prøver å trekke fra mer enn vi har
			if (Math.abs(baseValue) < Math.abs(subtractionValue))
			{
				// sjekker om vi skal kaste exception
				if (throwException)
				{
					throw new IllegalArgumentException("You cannot withdraw more than you have.");
				}
				// returnerer false hvis subtractionValue er negativ, baseValue er positiv, og vi prøver å trekke fra mer enn vi har
				return false;
			}
			// returnerer true hvis subtractionValue er negativ, baseValue er positiv og vi IKKE prøver å trekke fra mer enn vi har
			return true;
		}
		// returnerer false hvis subtractionValue er positiv eller baseValue er negativ, eller begge deler.
		return false;
	}
	*/
	
	public Account() 
	// Uten argumenter lager denne en account med 0 på konto og 0 i interestrate
	{
		this.balance = 0;
		this.interestRate = 0;
	}
	
	public Account(double initialBalance, double initialInterestRate) 
	{
		// 
		if (checkIfPositive(initialBalance, true) && checkIfNegative(initialInterestRate, true))
		this.balance = initialBalance;
		this.interestRate = initialInterestRate;
	}
	
	public void deposit(double amount)
	{
		// Sjekker om addToBalance er positiv eller 0, og tillater kun deposit hvis det er tilfellet
		if (checkIfPositive(amount, true))
		{
			this.balance += amount;
		}
	}
	
	public void withdraw(double amount)
	{
		if (checkIfPositive(amount, true))
		{
			if (this.balance - amount < 0)
			{
				throw new IllegalArgumentException("You cannot withdraw more than you have");
			}
			this.balance -= amount;
		}
	}
	
	public void addInterest()
		//	Ganger balance med (1,<interestRate>) for å få den nye kontoverdien
	{
		this.balance = (1+(interestRate/100))*this.balance;
	}
	public double getBalance()
	{
		return this.balance;
	}
	public double getInterestRate()
	{
		return this.interestRate;
	}
	public void setInterestRate(double newRate)
	{
		if (checkIfPositive(newRate, true))
		{
			this.interestRate = newRate;
		}
	}
	public String toString()
	{
		return String.format("Balance: %s\n Interest Rate: %s\n\n", balance, interestRate); 
	}
	public static void Main(String[] args)
	{
		Account account1 = new Account();
		account1.deposit(20);
		System.out.println(account1.toString());
		account1.addInterest();
		System.out.println(account1.toString());
		account1.setInterestRate(2);
		System.out.println(account1.toString());
		account1.addInterest();
		System.out.println(account1.toString());
	}
}
	
