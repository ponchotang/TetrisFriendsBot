package bot_old;

import java.util.ArrayList;
import java.util.List;

import game.Tile;

public class AlgorithmJ extends AlgorithmLJ {
	
	public AlgorithmJ() {
		super();
		
		DEFAULT_VERTICAL_POSITION_HANGING = 4;
		DEFAULT_VERTICAL_POSITION_FLAT = 3;
		
		VERTICAL_HANGING = "c";
		HORIZONTAL_HANGING = "cc";
		VERTICAL_FLAT = "ccc";
		HORIZONTAL_FLAT = "";
	}

	/**
	 * Overrides the method in parent class to give its own
	 * implementation.
	 */
	protected List<Tile> findFittingVerticalGap() {
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
						foundGaps.add(intersectedTile);
					}

				}
			}
		}


		return foundGaps;
	}

	/**
	 * Overrides the method in parent class to give its own
	 * implementation.
	 */
	protected List<Tile> findFittingHorizontalGap() {
		List<Tile> foundGaps = new ArrayList<Tile>();

		// Get list of gaps and spaces which can combine to make up the J shape
		List<Tile> gaps = findGap(1,1);
		List<Tile> spaces = findFlatSpace(2);

		// Iterate through all gaps and spaces
		for (int i = 0; i < gaps.size(); i++) {
			for (int j = 0; j < spaces.size(); j++) {

				// Get both respective pieces
				Tile bottomPiece = gaps.get(i);
				Tile topPiece = spaces.get(j);

				// Check if the pieces are in the correct position
				// If they are, then they form the fitting gap
				if (bottomPiece.column() == topPiece.column() + 2 &&
						bottomPiece.row() - 1 == topPiece.row()) {

					// Add to foundGaps if it is clear
					// Adds topPiece as this is the top-left corner
					if (isClear(topPiece, 3)) {
						foundGaps.add(topPiece);
					}

				}
			}
		}


		return foundGaps;
	}

}