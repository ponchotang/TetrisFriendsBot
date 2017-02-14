package bot;

import java.util.ArrayList;
import java.util.List;

import game.Tetrimino;

public class MoveGenerator {
	/*
	 * This class has one primary method: generateMoves(Tetrimino tetrimino)
	 * The goal of this is to return a list of Strings where each String describes one
	 * of the possible moves for the current tetrimino.
	 */
	
	public List<String> generateMoves(Tetrimino tetrimino) {
		List<String> moves = new ArrayList<String>();
		
		String rotationString = "";
		
		for(int i = 0; i < tetrimino.orientations(); i++) {
			moves.add(rotationString);
			rotationString += 'c';
		}
		
		return moves;
	}
}
