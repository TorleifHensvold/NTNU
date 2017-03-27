package interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

public class TicTacToeV2FileIO implements TicTacToeSaveV1
{
	public void save(TicTacToeV2 ticTacToe, String outputFile) throws IOException
	{
		try
		{
			PrintWriter out = new PrintWriter(outputFile);
			ArrayList<Command> lsn = undoStackToCorrectCommandOrder(ticTacToe.getUndoStack());
			//System.out.println(lsn);
			ArrayList<String> stringy =  commandsToStringArrayList(lsn);
			System.out.println(stringy);
			for (String string : stringy)
			{
				out.println(string);
			}
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
//		try
//		{
//			PrintWriter out = new PrintWriter(outputFile);
//			out.print(gameToSaveString(ticTacToe));
//			out.close();
//		}
//		catch (FileNotFoundException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println(e);
//		}
	}
	
	public void load(TicTacToeV2 ticTacToe, String inputFile) throws IOException
	{
		File file = new File(inputFile);
		String localString = "";
		ArrayList<Command> commandHistory = new ArrayList<>();
		try
		{
			Scanner in = new Scanner(file);
			while (in.hasNext())
			{
				localString = in.nextLine();
				Command localCommand = new Command(localString.charAt(2) - '0', localString.charAt(4) -'0', localString.charAt(0));
				commandHistory.add(localCommand);
			}
			in.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		if (ticTacToe.getStartingPlayer() != commandHistory.get(0).player)
		{
			throw new IOException("Failed to Load file.");
		}
		ticTacToe.loadHistory(commandHistory);
	}
	
	private ArrayList<Command> undoStackToCorrectCommandOrder(ArrayList<Command> undoStack)
	{
		ArrayList<Command> localCommandArray = new ArrayList<Command>();
		for (Command command: undoStack)
		{
			localCommandArray.add(0, command);
		}
		return localCommandArray;
	}
	
	private ArrayList<String> commandsToStringArrayList(ArrayList<Command> commandList)
	{
		String localString = "";
		ArrayList<String> localArray = new ArrayList<>();
		for (Command c: commandList)
		{
			localString = c.player + " " + c.x + " " + c.y;
			localArray.add(localString);
		}
		System.out.println(localString);
		return localArray;
	}
	
	private String gameToSaveString(TicTacToeV2 ticTacToe)
	{
		String localString = "";
		localString += "Player: " + ticTacToe.getCurrentPlayer() + "\n";
		localString += "Gameboard: " + boardToSimpleString(ticTacToe);
		return localString;
	}
	
	private String boardToSimpleString(TicTacToeV2 ticTacToe)
	{
		String localString = "";
		// the rows (y-axis)
		for (int i = 0; i < 3; i++)
		{
			// the columns (x-axis)
			for (int j = 0; j < 3; j++)
			{
				localString += ticTacToe.getCell(j, i);
			}
		}
		return localString;
	}
	
	public static void main(String[] args) throws IOException
	{
		TicTacToeV2 brett = new TicTacToeV2();
		System.out.println(brett.toString());
		brett.getInput("1 1"); // X
		System.out.println(brett.toString());
		brett.getInput("1 2"); // O
		System.out.println(brett.toString());
		brett.getInput("2 2"); // X
		System.out.println(brett.toString());
		brett.save("maximus.txt");
	}
}
