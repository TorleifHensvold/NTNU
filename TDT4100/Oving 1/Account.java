package stateandbehavior;

public class Account 
	{
	private double balance; // Holder rede p� hvor mye vi har p� konto
	private double interestRate; // Holder rede p� hva intrestraten er, oppgitt i %-poeng
	
	public Account() 
		// Uten argumenter lager denne en account med 0 p� konto og 0 i interestrate
		{
		balance = 0;
		interestRate = 0;
		}
	
	public Account(double initialBalance, double initialInterestRate) 
		{
		balance = initialBalance;
		interestRate = initialInterestRate;
		}
	
	public void deposit(double addToBalance)
		{
		// Sjekker om addToBalance er positiv eller 0, og tillater kun deposit hvis det er tilfellet
		if (addToBalance >= 0)
			{
			balance += addToBalance;
			}
		else
			{
			System.out.println("Cannot add the value to balance. Check value.");
			}
		}
	public void addInterest()
		//	Ganger balance med (1,<interestRate>) for � f� den nye kontoverdien
		{
		balance = (1+(interestRate/100))*balance;
		}
	public double getBalance()
		{
		return balance;
		}
	public double getInterestRate()
		{
		return interestRate;
		}
	public void setInterestRate(double newRate)
		{
		interestRate = newRate;
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
	
