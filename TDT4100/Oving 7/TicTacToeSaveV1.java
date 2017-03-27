package interfaces;

import java.io.File;
import java.io.IOException;

public interface TicTacToeSaveV1
{
	void save(TicTacToeV2 ticTacToe, String outputFile) throws IOException;
	void load(TicTacToeV2 ticTacToe, String inputFile) throws IOException;
}
