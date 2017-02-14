package bot_old;

import java.util.List;

import game.Tile;

public class AlgorithmI extends Algorithm {
	
	private static final int DEFAULT_HORIZONTAL_POSITION = 3;
	private static final int  DEFAULT_VERTICAL_POSITION = 5;

	@Override
	public String execute() {
		
		String moves = "";
		
		// Perfect vertical gap
		List<Tile> gaps = findGap(1,4);
		
		if (gaps.size() > 0) {
			moves += "c" + determineHorizontalMovement(DEFAULT_VERTICAL_POSITION, gaps.get(0).column()) + "s";
		}
		
		
		// Find perfect horizontal gap if no vertical gap
		if (moves.length() == 0) {
			gaps = findGap(4, 1);
			
			if (gaps.size() > 0) {
				moves += determineHorizontalMovement(DEFAULT_HORIZONTAL_POSITION, gaps.get(0).column()) + "s";
			}		
		}
		
		
		// Flat space if no horizontal gap
		if (moves.length() == 0) {
			List<Tile> flatSpaces = findFlatSpace(4);
			
			if (flatSpaces.size() > 0) {
				moves += determineHorizontalMovement(DEFAULT_HORIZONTAL_POSITION, flatSpaces.get(0).column()) + "s";
			}
		}
		
		return moves;
	}

}
