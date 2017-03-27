package interfaces;

import java.io.IOException;

public interface TicTacToeInterface<T>
{
	// The game needs to interface with the getInput command
	public void getInput(String s);

	// The game needs to be able to tell anyone controlling if it can undo
	public boolean canUndo();
	// and it needs to be able to get the call to undo
	public void undo();

	// Same as for undo
	public boolean canRedo();
	// same as for undo
	public void redo();

	// We need to be able to load a previous game
	public void load(String fileName) throws IOException;
	// we need to be able to save the current game
	public void save(String fileName) throws IOException;
	
	//public T getCell(int col, int row);
}
