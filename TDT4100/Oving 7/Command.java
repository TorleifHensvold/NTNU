package interfaces;

public class Command
{
	public final int x;
	public final int y;
	public final char player;
	
	public Command(int x, int y, char player) 
	{
		// We store the x-value of the command in the object
		this.x = x;
		// We store the y-value of the command in the object
		this.y = y;
		// We store which player issued the command in the object
		this.player = player;
	}
	
	public String toString()
	{
		return "Player: " + this.player + "\t(x,y): (" + this.x + "," + this.y + ")";
	}
}
