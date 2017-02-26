package bot;

import java.util.ArrayList;
import java.util.List;

import game.Tetrimino;
import game.RealGame;

/**
 * This class is used to generate a list of all the possible moves
 * for the inputed Tetrimino.
 * 
 * @author Andy Tang
 *
 */
public class MoveGenerator {
	
	public List<String> generateMoves(Tetrimino tetrimino) {
		List<String> moves = new ArrayList<String>();
		
		String rotationString = "";
		
		// Iterates through all orientations of the tetrimino
		for (int i = 0; i < tetrimino.orientations(); i++) {
			moves.add(rotationString);
			
			// Calculates the amount of possible left and right movements for the current orientation
			int leftMovements = tetrimino.startingPosition(i);
			int rightMovements = RealGame.getInstance().width() - tetrimino.startingPosition(i) - tetrimino.width(i);
			
			String leftString = "";
			String rightString = "";
			
			// Iterate through amount of movements and adds them to a string as well as appending it to the list
			for (int j = 0; j < leftMovements; j++) {
				leftString += 'l';
				moves.add(rotationString + leftString);
			}
			
			for (int j = 0; j < rightMovements; j++) {
				rightString += 'r';
				moves.add(rotationString + rightString);
			}
			
			// Increment rotation string to prepare for the next orientation
			rotationString += 'c';
		}
		
		
		
		return moves;
	}
}
