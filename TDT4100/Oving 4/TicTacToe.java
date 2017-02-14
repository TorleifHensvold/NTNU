package objectstructures;

import java.lang.Character;

public class TicTacToe 
{
	
	private char[][] board;
	private int xLength, yLength, gameState;
	private char currentPlayer;
	@SuppressWarnings("unused")
	private String[] gameStateArray = {"Playing", "'X' win", "'O' win", "Draw"}; 
	
	public TicTacToe()
	{
		this.xLength = 3;
		this.yLength = 3;
		fillBoard(this.xLength, this.yLength);
		this.currentPlayer = 'X';
		this.gameState = 0;
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
	
	private boolean isGameOver()
	{
		if (isRowsWon())
		{
			setWinningGameState();
			return true;
		}
		if (isColumnsWon())
		{
			setWinningGameState();
			return true;
		}
		if (isDiagonalsWon())
		{
			setWinningGameState();
			return true;
		}
		if (isBoardFull())
		{
			this.gameState = 3;
			return true;
		}
		return false;
	}
	
	private void setWinningGameState()
	{
		if (currentPlayer == 'X')
		{
			this.gameState = 1;
		}
		else if (currentPlayer == 'O')
		{
			this.gameState = 2;
		}
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
		localX = ((int) charInput[0]) - 48;
		localY = ((int) charInput[2]) - 48;
		//System.out.println(localX + " " + localY);
		playOnCell(localX, localY);
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
		String localString = "It is " + this.currentPlayer + "'s turn.\nGive input on format 'x y'.\nX and Y can be "
				+ "between 1 and 3\n\n";
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
		return runningGameScreen() + "\nThe winner is " + winner + ".";
	}
	
	private String gameDrawScreen()
	{
		return "The game is a draw.";
	}
	
	public static void main(String[] args) 
	{
		TicTacToe brett = new TicTacToe();
		System.out.println(brett.toString());
		brett.setCell('X', 0, 0);
		System.out.println(brett.toString());
		brett.setCell('X', 0, 1);
		System.out.println(brett.toString());
		brett.setCell('O', 0, 2);
		System.out.println(brett.toString());
		brett.setCell('O', 1, 2);
		System.out.println(brett.toString());
		System.out.println("Rows: " + brett.isRowsWon());
		System.out.println("Columns: " + brett.isColumnsWon());
		System.out.println("Diagonals: " + brett.isDiagonalsWon());
		System.out.println("Gamestate: " + brett.gameState);
		System.out.println("Game over: " + brett.isGameOver());
		brett.setCell('O', 2, 2);
		System.out.println(brett.toString());
		System.out.println("Rows: " + brett.isRowsWon());
		System.out.println("Columns: " + brett.isColumnsWon());
		System.out.println("Diagonals: " + brett.isDiagonalsWon());
		System.out.println("Gamestate: " + brett.gameState);
		System.out.println("Game over: " + brett.isGameOver());
		System.out.println("Gamestate: " + brett.gameState);
		
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
