package bot;

import java.util.ArrayList;
import java.util.List;

import game.Tetrimino;
import game.TetrisGame;

public class MoveGenerator {
	/*
	 * This class has one primary method: generateMoves(Tetrimino tetrimino)
	 * The goal of this is to return a list of Strings where each String describes one
	 * of the possible moves for the current tetrimino.
	 */
	
	public List<String> generateMoves(Tetrimino tetrimino) {
		List<String> moves = new ArrayList<String>();
		
		String rotationString = "";
		
		for (int i = 0; i < tetrimino.orientations(); i++) {
			moves.add(rotationString);
			
			// calculate possible left and right movements
			// iterate through them and add to list
			
			int leftMovements = tetrimino.startingPosition(i);
			int rightMovements = TetrisGame.getInstance().width() - tetrimino.startingPosition(i) - tetrimino.width(i);
			
			String leftString = "";
			String rightString = "";
			
			for (int j = 0; j < leftMovements; j++) {
				leftString += 'l';
				moves.add(rotationString + leftString);
			}
			
			for (int j = 0; j < rightMovements; j++) {
				rightString += 'r';
				moves.add(rotationString + rightString);
			}
			
			rotationString += 'c';
		}
		
		
		
		return moves;
	}
}
