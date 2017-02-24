package objectstructures;

import java.util.ArrayList;

public class Person 
{
	private Person mother, father;
	private boolean hasMother, hasFather, hasChildren;
	private int numberOfChildren;
	private ArrayList<Person> children;
	private char gender;
	private String name;
	private boolean sentRequest;
	
	public Person(String name, char gender)
	{
		setName(name);
		setGender(gender);
		this.mother = null;
		this.father = null;
		this.children = new ArrayList<Person>();
		this.sentRequest = false;
		updateBooleanRelations();
	}
	
	private void setName(String name)
	{
		this.name = name;
	}
	
	private void setGender(char gender)
	{
		if (gender != 'F' && gender != 'M')
		{
			throw new IllegalArgumentException("The gender was not 'M' or 'F'.");
		}
		this.gender = gender;
	}
	
	private void update()
	{
		updateNumberOfChildren();
		updateBooleanRelations();
	}
	
	private void updateBooleanRelations()
	{
		updateBooleanChildren();
		updateBooleanMother();
		updateBooleanFather();
	}
	
	private void updateBooleanChildren()
	{
		this.hasChildren = !this.children.isEmpty();	
	}
	
	private void updateBooleanMother()
	{
		// We check if this.mother is null, and if it is then the Person has no mother, otherwise it has.
		if (this.mother == null)
		{
			this.hasMother = false;
		}
		else
		{
			this.hasMother = true;
		}
	}
	
	private void updateBooleanFather()
	{
		// We check if this.father is null, and if it is then the Person has no father, otherwise it has.
		if (this.father == null)
		{
			this.hasFather = false;
		}
		else
		{
			this.hasFather = true;
		}
	}

	private void updateNumberOfChildren()
	{
		// if this.children.isEmpty is true, set this.numberOfChildren to 0, otherwise set it to this.children.size()
		this.numberOfChildren = (this.children.isEmpty()) ? 0 : this.children.size(); 
	}
	
	public String getName() // returnerer navnet knyttet til dette Person-objektet
	{
		return this.name;
	}
	
	public char getGender() //  returnerer tegnet som representerer kjønnet, enten 'F' eller 'M'
	{
		return this.gender;
	}
	
	public Person getMother() // returnerer Person-objektet som er moren, evt. null
	{
		return this.mother;
	}
	
	public Person getFather() // returnerer Person-objektet som er faren, evt. null
	{
		return this.father;
	}
	
	public int getChildCount() // returnerer antall barn dette Person-objektet har
	{
		return this.numberOfChildren;
	}
	
	public Person getChild(int n) // returnerer barn nr. n (altså et Person-objekt), evt. utløser (et passende) unntak
									//om n er for stor (eller liten)
	{
		Person[] childrenArray = this.children.toArray(new Person[children.size()]);
		if (n < 0)
		{
			throw new IllegalArgumentException("The integer 'n' for the index of the child of " + this.getName()
												+ " was less than 0, which is not permitted.");
		}
		if (n+1 > this.numberOfChildren)
		{
			throw new IllegalArgumentException("The integer 'n' for the index of the child of " + this.getName()
			+ " was larger than the number of children, which is not permitted.");
		}
		return childrenArray[n];
	}
	
	public void addChild(Person name)
	{
		// If this object already has the "name" Person-object as a child we abort/return immediately.
		if (this.children.contains(name))
		{
			return;
		}
		// Else we add the Person as Child
		this.children.add(name);
		// We then send a request to set parent, based on parent gender
		if (this.getGender() == 'F')
		{
			name.setMother(this);
		}
		if (this.getGender() == 'M')
		{
			name.setFather(this);
		}
		update();
	}
	
	public void removeChild(Person name)
	{
		// if this object sent a request we return immediately
		if (sentRequest)
		{
			return;
		}
		// if there is no child by that name, we throw exception
		if (!this.children.contains(name))
		{
			throw new IllegalArgumentException("The name given to remove from " + this.getName() + "s children"
												+ " did not match any of their children");
		}
		// We make a copy of the reference to the Person-object being removed.
		Person oldChild = name;
		// if there IS a child by that name we remove it.
		this.children.remove(name);
		// We affirm that we send request
		this.sentRequest = true;
		// We ask the Person-object being removed to remove it's reference to the parent. (by setting parent == null
		requestRemoveParent(oldChild);
		// We affirm that we've gotten control back and the we no longer have a request outstanding
		this.sentRequest = false;
		update();
	}
	
	private void requestRemoveParent(Person name)
	{
		// We send request for removing parent based on gender, F == mother, M == father
		switch (this.gender)
		{
		case 'F': name.setMother(null);
		case 'M': name.setFather(null);
		}
	}
	
	public void setMother(Person name)
	{
		// If the name is null, then we cannot perform checks on in, thus we execute the following only when it has
		// a value / is NOT null
		if (name != null)
		{
			// If this Object is the same as the mother we're trying to set, throw exception
			if (this.equals(name))
			{
				throw new IllegalArgumentException("You cannot be your own parent!");
			}
			// If the "mother" isn't female we throw exception
			if (name.getGender() != 'F')
			{
				throw new IllegalArgumentException("Expected 'F' as gender of a Mother");
			}
		}
		// If this Object has sent a request we return immediately
		if (this.sentRequest)
		{
			return;
		}
		// Only if this Person has a father can we check if their father is the same being set.
		if (this.mother != null)
		{
			// If this object already has the "name" Person-object as a father we abort/return immediately.
			if (this.mother.equals(name))
			{
				return;
			}
			// If the father was NOT null, and NOT the same as current father, we do the else clause
			else
			{
				// We save the previous father-link as oldFather
				Person oldMother = this.mother;
				// We set the current father as "null"
				this.mother = null;
				// We affirm that we are sending a request from this Object
				this.sentRequest = true;
				// We ask the previous father to remove its link to the current object.
				oldMother.removeChild(this);
				// We have gotten control back, and affirm that we no longer have an outstanding request.
				this.sentRequest = false;
			}
		}
		// If we're not setting father as null, execute
		if (name != null)
		{
			// Else we add the Person as the father
			this.mother = name;
			// We then send a request to add child
			name.addChild(this);
		}
		// If we tried to set mother as null, and it already was null, nothing executes.
		// If we tried to set mother as null, and it was something, the "else" clause executes, but not the last block
		update();
	}
	
	public void setFather(Person name)
	{
		// If the name is null, then we cannot perform checks on in, thus we execute the following only when it has
		// a value / is NOT null
		if (name != null)
		{
			// If this Object is the same as the father we're trying to set, throw exception
			if (this.equals(name))
			{
				throw new IllegalArgumentException("You cannot be your own parent!");
			}
			// If the "father" isn't male, we throw exception
			if (name.getGender() != 'M')
			{
				throw new IllegalArgumentException("Expected 'M' as gender of a Father");
			}
		}
		// If this Object has sent a request we return immediately
		if (this.sentRequest)
		{
			return;
		}
		// Only if this Person has a father can we check if their father is the same being set.
		if (this.father != null)
		{
			
			// If this object already has the "name" Person-object as a father we abort/return immediately.
			if (this.father.equals(name))
			{
				return;
			}
			// If the father was NOT null, and NOT the same as current father, we do the else clause
			else
			{
				// We save the previous father-link as oldFather
				Person oldFather = this.father;
				// We set the current father as "null"
				this.father = null;
				// We affirm that we are sending a request from this Object
				this.sentRequest = true;
				// We ask the previous father to remove its link to the current object.
				oldFather.removeChild(this);
				// We have gotten control back, and affirm that we no longer have an outstanding request.
				this.sentRequest = false;
			}
		}
		// If we're not setting father as null, execute
		if (name != null)
		{
			// Else we add the Person as the father
			this.father = name;
			// We then send a request to add child
			name.addChild(this);
		}
		update();
	}
	
	public String toString()
	{
		// We simply use the private methods to generate the string to return
		String utputt = nameString() + genderString() + parentString() + childrenString();
		return utputt;
	}
	
	private String nameString()
	{
		// We know the person MUST have a name, so we just add a line with the name and linebreak at the end
		return "Name:\t\t" + this.name + "\n";
	}
	
	private String genderString()
	{
		// We know the person MUST have a gender, so we just add a line with the name and linebreak at the end.
		return "Gender:\t\t" + this.gender + "\n";
	}
	
	private String parentString()
	{
		// If the booleans for hasMother and hasFather returns false we know the Person has NO parents
		if (!this.hasMother && !this.hasFather)
		{
			return "Parents:\tNone\n";
		}
		// If the person had parents we create an empty string to add to
		String utputt = "";
		// if the Person has a mother we add a line with the mother and linebreak
		if (this.hasMother)
		{
			utputt += "Mother:\t\t" + this.mother.getName() + "\n";
		}
		// If the Person has a father we add a line with the father and linebreak
		if (this.hasFather)
		{
			utputt += "Father:\t\t" + this.father.getName() + "\n";
		}
		// We return the string with Mother and/or Father
		return utputt;
	}
	
	private String childrenString()
	{
		// If the Person has no children we can just return the string below
		if (!this.hasChildren)
		{
			return "Children:\tNone\n";
		}
		// If the Person has children we initialize a string 
		String utputt = "Children (" + this.numberOfChildren + "):\t";
		// We create a local ARRAY of appropriate size which the Person-objects from this.children are stored in
		// correct sequence. This will be scrubbed when the method returns.
		Person[] childrenArray = this.children.toArray(new Person[this.children.size()]);
		// We go through the list of children and add their names
		for (int i = 0; i < childrenArray.length; i++) 
		{
			utputt += childrenArray[i].getName();
			// If it is not the last entry, then we add a comma and space as delimiters
			if (i+1 != childrenArray.length)
			{
				utputt += ", ";
			}
			// Every 5 children we add a new line
			if (i+1%5==0)
			{
				utputt += "\n\t\t\t";
			}
		}
		utputt += "\n";
		return utputt;
	}
	
	public static void main(String[] args) 
	{
		Person torleif = new Person("Torleif Hensvold", 'M');
		System.out.println(torleif.toString());
		Person datter = new Person("Kari Nordmann",'F');
		System.out.println(datter.toString());
		System.out.println("Setting " + datter.getName() + " as child of " + torleif.getName());
		torleif.addChild(datter);
		System.out.println(torleif.toString());
		System.out.println(datter.toString());
		Person sonn = new Person("Ola Nordmann", 'M');
		System.out.println(sonn.toString());
		System.out.println("Setting " + sonn.getName() + " as child of " + torleif.getName());
		torleif.addChild(sonn);
		System.out.println(torleif.toString());
		System.out.println(datter.toString());
		System.out.println(sonn.toString());
		System.out.println("Removing " + sonn.getName() + " as child of " + torleif.getName());
		torleif.removeChild(sonn);
		System.out.println(torleif.toString());
		System.out.println(datter.toString());
		System.out.println(sonn.toString());
		System.out.println("Setting " + sonn.getName() + " as child of " + datter.getName());
		datter.addChild(sonn);
		System.out.println(torleif.toString());
		System.out.println(datter.toString());
		System.out.println(sonn.toString());
		System.out.println("Removing " + sonn.getName() + " as child of " + datter.getName());
		datter.removeChild(sonn);
		System.out.println(torleif.toString());
		System.out.println(datter.toString());
		System.out.println(sonn.toString());
		System.out.println("Setting " + datter.getName() + " as child of " + sonn.getName());
		sonn.addChild(datter);
		System.out.println(torleif.toString());
		System.out.println(datter.toString());
		System.out.println(sonn.toString());
	}
}


