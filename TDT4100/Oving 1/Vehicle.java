package encapsulation;

import java.util.ArrayList;

public class Vehicle 
{
	private char type;
	private char drivstoff;
	private String regNummer;	
	//private String[] motorcycleDisallowedRegistrationNumberPrefix = {"HY"};
	//private String[] electricCarAllowedRegistrationNumberPrefix = {"EL","EK"};
	//private static String[] hydrogenCarAllowedRegistrationNumberPrefix = {"HY"};
	private static char[] allowedVehicleTypes = {'M', 'C'};
	//private static String[] allowedRegistrationNumberPrefixPool = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N",
	//														"O","P","Q","R","S","T","U","W","X","Y","Z"};
	private static String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	//private String[] disallowedRegistrationNumberPrefixPool = {"Æ","Ø","Å"};
	private char[] allowedFuelTypes = {'E','H','D','G'};
	//private String[] allowedNumbers = {"1","2","3","4","5","6","7","8","9","0"};
	private static ArrayList<String> prefixListe = generatePrefixArrayList();
	
	private static ArrayList<String> generatePrefixArrayList()
	{
		ArrayList<String> prefixArrayListe = new ArrayList<>();
		for (int i = 0; i < allowedCharacters.length(); i++) 
		{
			for (int j = 0; j < allowedCharacters.length(); j++) 
			{
				String toAdd = (String.valueOf(allowedCharacters.charAt(i)) + String.valueOf(allowedCharacters.charAt(j)));
				prefixArrayListe.add(toAdd);
			}
		}
		return prefixArrayListe;
	}
			
	/*private static List generatePrefixList()
	{
		List prefixListe = new List();
		for (int i = 0; i < allowedCharacters.length(); i++) 
		{
			for (int j = 0; j < allowedCharacters.length(); j++) 
			{
				String toAdd = (String.valueOf(allowedCharacters.charAt(i)) + String.valueOf(allowedCharacters.charAt(j)));
				prefixListe.add(toAdd);
			}
		}
		return prefixListe;
	}*/
	
	public Vehicle(char type, char drivstoff, String regNummer)
	{
		setType(type);
		setFuel(drivstoff);
		setRegistrationNumber(regNummer);
	}
	
	private void setType(char type)
	{
		if (checkType(type, true))
		{
			this.type = type;
		}
	}
	
	private void setFuel(char drivstoff)
	{
		if (checkFuel(drivstoff, true))
		{
			this.drivstoff = drivstoff;
		}
	}
	
	private boolean checkIfArrayContains(char[] liste, char checkForValue)
	{
		boolean found = false;
		for (int i = 0; i < liste.length; i++) 
		{
			if (liste[i] == checkForValue)
			{
				found = true;
			}
		}
		return found;
	}
	
	private boolean checkType(char type, boolean throwException)
	{
		if (!checkIfArrayContains(allowedVehicleTypes, type))
		{
			if (throwException)
			{
				throw new IllegalArgumentException("The vehicle type selected is not supported. Use M or C");
			}
			return false; 
		}
		return true;
	}
	
	private boolean checkFuel(char fuelType, boolean throwException)
	{
		// Sjekker om fuelType finnes i allowedFuelTypes
		if (checkIfArrayContains(allowedFuelTypes, fuelType))
		{
			// Sjeker om det er motorsykkel
			if (this.type == 'M')
			{
				// Sjekker om motorsykkelen har fått fuelType hydrogen
				if (fuelType == 'H')
				{
					// Sjekker om vi skal kaste Exception
					if (throwException)
					{
						throw new IllegalArgumentException("The fuel type is not supported by the vehicle type.");
					}
					// Hvis vi ikke skal kaste Exception returnerer vi false
					return false;
				}
				// Hvis det var motorsykkel og fuelType IKKE var hydrogen, returnerer vi true
				return true;
			}
			// Hvis det ikke var motorsykkel vet vi at det er bil, og bil kan ha alle fuelTypes i allowedFuelTypes
			return true;
		}
		// Hvis fuelType IKKE var i allowedFuelTypes og vi skal kaste Exception
		if (throwException)
		{
			throw new IllegalArgumentException("The fuel type is not recognized.");
		}
		// Hvis fuelType IKKE var i allowedFuelTypes og vi ikke kaster Exception returnerer vi false 
		return false;
	}
	
	private String getPrefix(String registrationNumber)
	{
		return registrationNumber.substring(0, 2);
	}
	
	private boolean checkPrefixByFuelType(String registrationNumber, boolean throwException)
	{
		// Vi sjekker om type E starter på "EL" eller "EK"
		if (getFuelType() == 'E' && (registrationNumber.startsWith("EK") || registrationNumber.startsWith("EL")))
		{
			return true;
		}
		// Vi sjekker om type H starter på HY
		if (getFuelType() == 'H' && registrationNumber.startsWith("HY"))
		{
			return true;
		}
		if (getFuelType() == 'D' || getFuelType() == 'G')
		{
			String prefix = getPrefix(registrationNumber);
			if (prefixListe.contains(prefix))
			{
				if (registrationNumber.startsWith("HY") || registrationNumber.startsWith("EK") || registrationNumber.startsWith("EL"))
				{
					if (throwException)
					{
						throw new IllegalArgumentException("The prefix is not allowed for the fuel type");
					}	
					return false;
				}
				return true;
			}
			if (throwException)
			{
				throw new IllegalArgumentException("The prefix is not allowed for the fuel type");
			}
			return false;
		}
		if (throwException)
		{
			throw new IllegalArgumentException("The prefix is not allowed for the fuel type");
		}
		return false;
	}

	private boolean checkIfStringIsNumbers(String checkValue, boolean throwException)
	{
		for (char c : checkValue.toCharArray())
		{
			if (!Character.isDigit(c))
			{
				if (throwException)
				{
					throw new IllegalArgumentException("The string is not numbers");
				}
				return false;
			}
		}
		return true;
	}
	
	private boolean checkRegistrationNumber(String registrationNumber, boolean throwException)
	{
		// Sjekker om det er motorsykkel
		if (this.type == 'M')
		{
			// sjekker om registreringnummeret har riktig lengde
			if (registrationNumber.length() != 6)
			{
				// Sjekker om vi skal kaste Exception hvis det IKKE er riktig lengde
				if (throwException)
				{
					throw new IllegalArgumentException("The registration number has an incorrect length");
				}
				// returnerer false hvis det IKKE er riktig lengde og vi IKKE kaster Exception
				return false;
			}
			// Vi sjekker om prefikset er lovlig for fueltype
			if (checkPrefixByFuelType(registrationNumber, throwException))
			{
				// siden vi allerede har sjekket at drivstoffet passer til typen under konstruktøren vet vi at 'M' ikke kan ha H som drivstoff.
				// Vi sjekker om det kun er tall etter prefikset
				if (checkIfStringIsNumbers(registrationNumber.substring(2, registrationNumber.length()), throwException))
				{
					return true;
				}
				if (throwException)
				{
					throw new IllegalArgumentException("The prefix is not allowed for the fuel type");
				}
				return false;
			}
			// Hvis det er type motorsykkel, vi IKKE skal kaste Exception og prefikset ikke er lovlig vi false
			if (throwException)
			{
				throw new IllegalArgumentException("The prefix is not allowed for the fuel type");
			}
			return false;
		}
		// Sjekker om registreringsummeret til bilen har riktig lengde
		if (registrationNumber.length() != 7)
		{
			// Sjekker om vi skal kaste Exception hvis det IKKE er riktig lengde
			if (throwException)
			{
				throw new IllegalArgumentException("The registration number has an incorrect length");
			}
			// returnerer false hvis det IKKE er riktig lengde og vi IKKE kaster Exception
			return false;
		}
		// Vi sjekker om prefikset det er lovlig for fueltype
		if (checkPrefixByFuelType(registrationNumber, throwException))
		{
			// Vi sjekker om det kun er tall etter prefikset
			if (checkIfStringIsNumbers(registrationNumber.substring(2, registrationNumber.length()), throwException))
			{
				return true;
			}
			// Hvis vi ikke skal kaste Exception og prefikset er riktig, men tallene IKKE er tall returnerer vi false
			if (throwException)
			{
				throw new IllegalArgumentException("The prefix is not allowed for the fuel type");
			}
			return false;
		}
		// Hvis alt annet ikke slår inn så er det sikkert feil.
		if (throwException)
		{
			throw new IllegalArgumentException("The prefix is not allowed for the fuel type");
		}
		return false;
	}
	
	public char getFuelType()
	{
		return this.drivstoff;
	}
	
	public String getRegistrationNumber()
	{
		return this.regNummer;
	}
	
	public char getVehicleType()
	{
		return this.type;
	}
	
	public void setRegistrationNumber(String nummer)
	{
		if (checkRegistrationNumber(nummer, true))
		{
			this.regNummer = nummer;
		}
	}
	
	public static void main(String[] args) 
	{
		Vehicle vehicle = new Vehicle('C', 'E', "EK15324");
		System.out.println();
		System.out.println("Vehicletype: " + vehicle.type + "\nFueltype: " + vehicle.getFuelType() +
				"\nRegistrationNumber: " + vehicle.getRegistrationNumber());
		vehicle.setRegistrationNumber("EL12345");
		System.out.println("Vehicletype: " + vehicle.type + "\nFueltype: " + vehicle.getFuelType() +
				"\nRegistrationNumber: " + vehicle.getRegistrationNumber());
		vehicle.setRegistrationNumber("EM12345");
		System.out.println("Vehicletype: " + vehicle.type + "\nFueltype: " + vehicle.getFuelType() +
				"\nRegistrationNumber: " + vehicle.getRegistrationNumber());
	}
}




/*Vehicle(char, char, String) - Konstruktør der argument-rekkefølgen må være kjøretøystype, drivstofftype og registreringsnummer. Ved ugyldige argumenter utløses unntak av typen IllegalArgumentException.
getFuelType() - returnerer type drivstoff som følgende: ‘H’ for hydrogen, ‘E’ for elektrisitet, ‘D’ for diesel eller ‘G’ for bensin.
getRegistrationNumber() - returnerer registreringsnummeret

setRegistrationNumber(String)  - endrer registreringsnummeret dersom det er gyldig i henhold til kravene over, og utløser unntak av typen IllegalArgumentException dersom det ikke er gyldig.

getVehicleType() - returnerer kjøretøystype: 'M' for motosykkel, 'C' for bil.'*/