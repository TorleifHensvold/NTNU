package interfaces;

import java.io.File;
import java.io.IOException;
import java.lang.Character;
import java.nio.file.Files;
import java.util.Stack;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class TicTacToeV2 
{
	
	private char[][] board;
	private int xLength, yLength, gameState;
	private char currentPlayer;
	@SuppressWarnings("unused")
	private String[] gameStateArray = {"Playing", "'X' win", "'O' win", "Draw"}; 
	private char startingPlayer = 'X';
	
	private Deque<Command> undoStack = new ArrayDeque<>();
	private Deque<Command> redoStack = new ArrayDeque<>();
	
	private TicTacToeSaveV1 fileWorks = new TicTacToeV2FileIO();
	
	public TicTacToeV2()
	{
		this.xLength = 3;
		this.yLength = 3;
		fillBoard(this.xLength, this.yLength);
		this.currentPlayer = 'X';
		this.gameState = 0;
	}
	
	public void loadHistory(ArrayList<Command> commands)
	{
		if (commands == null)
		{
			return;
		}
		undoStack.clear();
		redoStack.clear();
		this.currentPlayer = new Character(startingPlayer);
		fillBoard(this.xLength, this.yLength);
		this.gameState = 0;
		for (Command command: commands)
		{
			checkCorrectPlayer(this.currentPlayer, command.player);
			play(command.x, command.y);
		}
	}
	
	public char getStartingPlayer()
	{
		return this.startingPlayer;
	}
	
	private void checkCorrectPlayer(char player, char toCheckPlayer)
	{
		if (player != toCheckPlayer)
		{
			throw new IllegalArgumentException("Loading failed because the player in the history of moves does not correleate to proper game rules.");
		}
	}
	
	private void fillBoard(int xLength, int yLength)
	{
		// Creates a new charArray with lengths equal to x and y lengths
		this.board = new char[xLength][yLength];
		// fills the board
		for (int i = 0; i < this.xLength; i++) 
		{
			for (int j = 0; j < this.yLength; j++) 
			{
				board[i][j] = ' ';
			}
		}
	}
	
	private boolean isValidArrayNumber(int number, boolean throwException)
	{
		// Check if number is lower than 0, or larger or equal to the length of the 2D array in any direction.
		// If any of the statements are true, then we have an illegal argument
		if (number < 0)
		{
			// Check if we should throw exception
			if (throwException)
			{
				throw new IllegalArgumentException("The number was too small.");
			}
			// if we don't throw exceptions, we return false
			return false;
		}
		if (number >= this.xLength || number >= this.yLength)
		{
			// Check if we should throw exception
			if (throwException)
				{
					throw new IllegalArgumentException("The number was too large.");
				}
				// if we don't throw exceptions, we return false
			return false;
		}
		// if the check for illegal arguments returns false then it should be valid --> return true.
		return true;
	}
	
	private boolean switchPlayer(boolean throwException)
	{
		// if the current player was X, then we set the current player to O
		if (this.currentPlayer == 'X')
		{
			this.currentPlayer = 'O';
			return true;
		}
		// If the current player was O, then we set the current player to X
		else if (this.currentPlayer == 'O')
		{
			this.currentPlayer = 'X';
			return true;
		}
		// This should be an impossibility, but if the current player isn't X or O.
		// Check if we should throw exception or just return false
		if (throwException)
		{
			throw new IllegalStateException("The current player was neither 'X' nor 'O'. ");
		}
		return false;
	}
	
	public char getCell(int x, int y)
	{
		// if x and y is valid numbers for the index we return the char at that position
		if (isValidArrayNumber(x, true) && isValidArrayNumber(y, true))
		{
			return board[x][y];
		}
		// This should never be possible, since x and y should always be a number in bounds or 
		// trigger an exception, but it's here for completeness
		return '\n';
	}
	
	private boolean setCell(char c, int x, int y)
	{
		if (this.gameState != 0)
		{
			throw new IllegalStateException("The game is not running");
		}
		// If the spot is not occupied, and it is in bounds(otherwise it triggers an exception) then we set the 
		// spot in the 2D array to the character c
		if (!isOccupied(x, y))
		{
			this.board[x][y] = c;
			return true;
		}
		// if the spot was occupied then we return false
		return false;
	}
	
	private boolean setCellVoid(int x, int y)
	{
		if (!isOccupied(x, y))
		{
			// if the spot isn't occupied, then it is already void.
			return true;
		}
		if (isOccupied(x, y))
		{
			// if the spot is occupied then we set it as ' '
			this.board[x][y] = ' ';
			return true;
		}
		// this should be impossible, but is included for completeness.
		return false;
	}
	
	public void play(int x, int y)
	{
		// we create a new Command object which remembers the command given by the current player
		if (!isOccupied(x, y))
		{
			Command command = new Command(x, y , currentPlayer);
			executeCommand(command);
			undoStack.push(command);
			redoStack.clear();
		}
	}
	
	private void executeCommand(Command command)
	{
		setCell(command.player, command.x, command.y);
		if (command.player == this.currentPlayer)
		{
			switchPlayer(true);
		}
		isGameOver();
	}
	
	public boolean isOccupied(int x, int y)
	{
		if (isValidArrayNumber(x, true) && isValidArrayNumber(y, true))
		{
			// If the slot at x y is a space character then the spot is NOT occupied
			if (this.board[x][y] == ' ')
			{
				return false;
			}
			// If there's some other character then the spot IS occupied
			return true;
		}
		// This should never be possible, since x and y should always be a number in bounds or 
		// trigger an exception, but it's here for completeness
		return true;
	}
	
	public char getCurrentPlayer()
	{
		return this.currentPlayer;
	}
	
	public boolean isWinner(char c)
	{
		return 
				// Checks the first row
				checkInARow(c, 0, 0, 1, 0) ||
				// checks the second row
				checkInARow(c, 0, 1, 1, 0) ||
				// checks the third row
				checkInARow(c, 0, 2, 1, 0) ||
				// checks the first column
				checkInARow(c, 0, 0, 0, 1) ||
				// checks the second column
				checkInARow(c, 1, 0, 0, 1) ||
				// checks the third column
				checkInARow(c, 2, 0, 0, 1) ||
				// checks the left-to-right downwards diagonal
				checkInARow(c, 0, 0, 1, 1) ||
				// checks the right-to-left downwards diagonal
				checkInARow(c, 2, 0, -1, 1)
				;
	}
	
	private boolean checkInARow(char c, int x, int y, int dx, int dy)
	{
		// This uses x as the starting point in x, y as the starting point in y and dx,dy as
		// the ways we move through the array to check.
		// We initialize n to be the same as in the n*n array we use for the game board 
		int n = this.xLength + 0;
		// we count down in the bottom of this loop, so it will run n times.
		while (n > 0)
		{
			// if the characters in spot (x,y) isn't the character c we do not have 3 c in a row
			if (getCell(x, y) != c)
			{
				return false;
			}
			// we increment x and y by their deltas
			x += dx;
			y += dy;
			// and we decrement n by 1
			n--;
		}
		// if all the checked values are of character c then we know that character c is in a row
		return true;
	}
	
	public boolean hasWinner()
	{
		// checks if either X or O has 3 in a row and returns true if either has.
		return isWinner('X') || isWinner('O');
	}
	
	private boolean isGameOver()
	{
		// this function checks if X or O has won and changes the gamestate to reflect that
		if (isWinner('X'))
		{
			this.gameState = 1;
			return true;
		}
		if (isWinner('O'))
		{
			this.gameState = 2;
			return true;
		}
		if (isBoardFull())
		{
			this.gameState = 3;
			return true;
		}
		this.gameState = 0;
		return false;
	}
	
	private boolean isRowsWon()
	{
		boolean threeInARow = false;
		int numberOfX, numberOfO;
		for (int i = 0; i < this.xLength; i++)
		{
			numberOfX = 0;
			numberOfO = 0;
			for (int j = 0; j < this.yLength; j++)
			{
				if (board[i][j] == ' ')
				{
					continue;
				}
				if (board[i][j] == 'X')
				{
					numberOfX ++;
				}
				if (board[i][j] == 'O')
				{
					numberOfO ++;
				}
			}
			if (numberOfO == this.yLength)
			{
				threeInARow = true;
			}
			if (numberOfX == this.yLength)
			{
				threeInARow = true;
			}
		}
		return threeInARow;
	}
	
	private boolean isColumnsWon()
	{
		boolean threeInARow = false;
		int numberOfX, numberOfO;
		for (int i = 0; i < this.xLength; i++)
		{
			numberOfX = 0;
			numberOfO = 0;
			for (int j = 0; j < this.yLength; j++)
			{
				if (board[j][i] == ' ')
				{
					continue;
				}
				if (board[j][i] == 'X')
				{
					numberOfX ++;
				}
				if (board[j][i] == 'O')
				{
					numberOfO ++;
				}
			}
			if (numberOfO == this.xLength)
			{
				threeInARow = true;
			}
			if (numberOfX == this.xLength)
			{
				threeInARow = true;
			}
		}
		return threeInARow;
	}
	
	private boolean isDiagonalsWon()
	{
		if (isLeftToRightDiagonalWon() || isRightToLeftDiagonalWon())
		{
			return true;
		}
		return false;
	}
	
	private boolean isLeftToRightDiagonalWon()
	{
		boolean threeInARow = false;
		int numberOfX, numberOfO;
		numberOfX = 0;
		numberOfO = 0;
		for (int i = 0; i < xLength; i++) 
		{
			if (this.board[i][i] == 'X')
			{
				numberOfX ++;
			}
			if (this.board[i][i] == 'O')
			{
				numberOfO ++;
			}
		}
		if (numberOfX == xLength)
		{
			threeInARow = true;
		}
		if (numberOfO == xLength)
		{
			threeInARow = true;
		}
		return threeInARow;
	}
	
	private boolean isRightToLeftDiagonalWon()
	{
		boolean threeInARow = false;
		int numberOfX, numberOfO;
		numberOfX = 0;
		numberOfO = 0;
		for (int i = 0; i < xLength; i++) 
		{
			if (this.board[(xLength - 1 - i)][i] == 'X')
			{
				numberOfX ++;
			}
			if (this.board[(xLength - 1 - i)][i] == 'O')
			{
				numberOfO ++;
			}
		}
		if (numberOfX == xLength)
		{
			threeInARow = true;
		}
		if (numberOfO == xLength)
		{
			threeInARow = true;
		}
		return threeInARow;
	}
	
	private boolean isBoardFull()
	{
		for (int i = 0; i < this.xLength; i++)
		{
			for (int j = 0; j < this.yLength; j++)
			{
				if (board[j][i] == ' ')
				{
					return false;
				}
			}
		}
		return true;
	}
	
	/*private int getGameState()
	{
		return this.gameState;
	}*/
	
	public void playOnCell(int x, int y)
	{
		if (setCell(currentPlayer, x-1, y-1))
		{
			if (!isGameOver())
			{
				switchPlayer(true);
			}
		}
		isGameOver();
	}
	
	public void getInput(String input)
	{
		int localX = -1;
		int localY = -1;
		char[] charInput = input.toCharArray();
		if (!Character.isDigit(charInput[0]) || !Character.isDigit(charInput[2]))
		{
			throw new IllegalArgumentException("The format is <x-integer><space><y-integer>");
		}
		localX = ((int) charInput[2]) - '1';
		//System.out.println(localX);
		localY = ((int) charInput[0]) - '1';
		//System.out.println(localY);
		//System.out.println(localX + " " + localY);
		play(localX, localY);
	}

	public boolean canUndo()
	{
		return !undoStack.isEmpty();
	}
	
	public void undo()
	{
		if (canUndo())
		{
			// We take the object remembering the action previously performed and call it command
			Command command = undoStack.pop();
			// we then clear the spot the command had marked with a symbol
			setCellVoid(command.x, command.y);
			// then we return control of the gameboard to the player whose turn it was before the
			// command was given
			this.currentPlayer = command.player;
			// We add the command to the redo stack, if it could be needed.
			redoStack.push(command);
			isGameOver();
		}
	}

	public boolean canRedo()
	{
		return !redoStack.isEmpty();
	}
	
	public void redo()
	{
		if (canRedo())
		{
			// We take the object remembering the action previously undone and call it command
			Command command = redoStack.pop();
			// We then tell tictactoe to execute the command again. Should work properly.
			executeCommand(command);
			// We add the command to the undo stack, if it could be needed.
			undoStack.push(command);
		}
	}
	
	public String toString()
	{
		switch (gameState)
		{
		case 0: return runningGameScreen();
		case 1: return winningPlayerScreen('X');
		case 2: return winningPlayerScreen('O');
		case 3: return gameDrawScreen();
		}
		return "Something went wrong.";
	}
	
	private String runningGameScreen()
	{
		String localString = "";
		if (this.gameState == 0)
		{
			localString += "It is " + this.currentPlayer + "'s turn.\nGive input on format 'x y'.\nX and Y can be "
				+ "between 1 and 3\n\n";
		}
		localString += "     x     \n\n";
		for (int i = 0; i < this.xLength; i++) 
		{
			for (int j = 0; j < this.yLength; j++) 
			{
				// Add the cells with dividers between them
				localString += " " + board[i][j] + " ";
				if (j < (yLength - 1))
				{
					localString += "|";
				}
				if (i == 1 && j == 2)
				{
					localString += "   y";
				}
			}
			// Add the divider between the rows
			localString += "\n";
			if (i < xLength - 1)
			{
				localString += "-----------\n";
			}
		}
		return localString;
	}
	
	private String winningPlayerScreen(char winner)
	{
		return "\n\n\n\n" + runningGameScreen() + "\nThe winner is " + winner + ".\n";
	}
	
	private String gameDrawScreen()
	{
		return "The game is a draw.";
	}
	
	public void save(String fileName) throws IOException
	{
		fileWorks.save(this, fileName);
	}
	
	public void load(String fileName) throws IOException
	{
		fileWorks.load(this, fileName);
	}
	
	public ArrayList<Command> getUndoStack()
	{
		return new ArrayList<Command>(undoStack);
	}
	
	public static void main(String[] args) throws IOException 
	{
		TicTacToeV2 brett = new TicTacToeV2();
		System.out.println(brett.toString());
		brett.getInput("1 1"); // X
		System.out.println(brett.undoStack.peek().player);
		System.out.println(brett.undoStack.isEmpty());
		System.out.println(brett.toString());
		brett.getInput("1 2"); // O
		System.out.println(brett.toString());
		brett.getInput("2 2"); // X
		System.out.println(brett.toString());
		brett.getInput("2 1"); // O
		System.out.println(brett.toString());
//		System.out.println("Rows: " + brett.isRowsWon());
//		System.out.println("Columns: " + brett.isColumnsWon());
//		System.out.println("Diagonals: " + brett.isDiagonalsWon());
//		System.out.println("Gamestate: " + brett.gameState);
//		System.out.println("Game over: " + brett.isGameOver());
		brett.getInput("3 3"); // X
		System.out.println(brett.toString());
//		System.out.println("Rows: " + brett.isRowsWon());
//		System.out.println("Columns: " + brett.isColumnsWon());
//		System.out.println("Diagonals: " + brett.isDiagonalsWon());
//		System.out.println("Gamestate: " + brett.gameState);
//		System.out.println("Game over: " + brett.isGameOver());
//		System.out.println("Gamestate: " + brett.gameState);
		try
		{
			brett.undo();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		System.out.println(brett.toString());
//		System.out.println(brett.redoStack.isEmpty());
//		System.out.println(brett.redoStack.peek().player);
//		System.out.println(brett.redoStack.peek().x);
//		System.out.println(brett.redoStack.peek().y);
		try
		{
			brett.redo();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		System.out.println(brett.toString());
		ArrayList<Command> coms = brett.getUndoStack();
		for (Command c: coms)
		{
			System.out.println(c.toString());
		}
		try 
		{
			brett.getInput("3 2");
		} catch (Exception e) 
		{
			System.out.println(e);
		}
		brett.save("bah");
		//brett.save("ballalaika");
//		System.out.println("Rows: " + brett.isRowsWon());
//		System.out.println("Columns: " + brett.isColumnsWon());
//		System.out.println("Diagonals: " + brett.isDiagonalsWon());
//		System.out.println("Gamestate: " + brett.gameState);
//		System.out.println("Game over: " + brett.isGameOver());
//		System.out.println("Gamestate: " + brett.gameState);
		/*brett.setCell('O', 1, 1);
		System.out.println(brett.toString());
		brett.setCell('O', 2, 0);
		System.out.println(brett.toString());
		System.out.println("Rows: " + brett.isRowsWon());
		System.out.println("Columns: " + brett.isColumnsWon());
		System.out.println("Diagonals: " + brett.isDiagonalsWon());
		System.out.println("Gamestate: " + brett.gameState);
		System.out.println("Game over: " + brett.isGameOver());
		*/
	}

}
