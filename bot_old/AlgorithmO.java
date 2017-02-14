package bot_old;

import java.util.List;

import game.Tile;

public class AlgorithmO extends Algorithm{
	
	private static final int DEFAULT_POSITION = 4;

	@Override
	public String execute() {
		// TODO Improve logic
		
		String moves = "";
			
		// Perfect gap
		List<Tile> gaps = findGap(2,2);
		
		if (gaps.size() > 0) {
			moves += determineHorizontalMovement(DEFAULT_POSITION, gaps.get(0).column()) + "s";
		}
		
		// Fitting gap
		if (moves.length() == 0) {
			gaps = findGap(2, 1);
			
			if (gaps.size() > 0) {
				moves += determineHorizontalMovement(DEFAULT_POSITION, gaps.get(0).column()) + "s";
			}		
		}
		
		// Flat space
		// Change so that it considers the best flat space TODO
		if (moves.length() == 0) {
			List<Tile> flatSpaces = findFlatSpace(2);
			
			if (flatSpaces.size() > 0) {
				moves += determineHorizontalMovement(DEFAULT_POSITION, flatSpaces.get(0).column()) + "s";
			}
		}
		
		return moves;
	}
	


}
