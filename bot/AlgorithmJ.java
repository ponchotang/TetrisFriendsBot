package bot;

import java.util.ArrayList;
import java.util.List;

import game.Tile;

public class AlgorithmJ extends Algorithm {
	
	private static final int DEFAULT_VERTICAL_POSITION = 4;

	@Override
	public String execute() {
		// TODO Auto-generated method stub

		String moves = "";

		// Fitting gap
		List<Tile> gaps = findFittingVerticalGap();

		if (gaps.size() > 0) {
			moves += "c" + determineHorizontalMovement(DEFAULT_VERTICAL_POSITION, gaps.get(0).column()) + "s";
		}

		return moves;
	}

	/**
	 * This method aims to find a perfect fitting gap for the J tetrimino rotated
	 * 90 degrees
	 * 
	 * It uses the findGap and findSpaces method in order to find the fitting gap.
	 * 
	 * @return List of Tile objects that represent the top-left corner of the found gaps
	 */
	private List<Tile> findFittingVerticalGap() {
		List<Tile> foundGaps = new ArrayList<Tile>();
		
		// Get list of gaps and spaces which can combine to make up the J shape
		List<Tile> gaps = findGap(1,2);
		List<Tile> spaces = findFlatSpace(1);

		// Iterate through all gaps and spaces
		for (int i = 0; i < gaps.size(); i++) {
			for (int j = 0; j < spaces.size(); j++) {
				
				// Get both respective pieces
				Tile bottomPiece = gaps.get(i);
				Tile topPiece = spaces.get(j);
				
				// Check if the pieces are in the correct position
				// If they are, then they form the fitting gap
				if (bottomPiece.column() == topPiece.column() - 1 &&
						bottomPiece.row() - 1 == topPiece.row()) {
					
					// Get the top-left corner of this fitting gap
					Tile intersectedTile = game.get(topPiece.row(), bottomPiece.column());
					
					// Add to foundGaps if it is clear
					if (isClear(intersectedTile, 2)) {
						foundGaps.add(game.get(topPiece.row(), bottomPiece.column()));
					}
					
				}
			}
		}


		return foundGaps;
	}

}