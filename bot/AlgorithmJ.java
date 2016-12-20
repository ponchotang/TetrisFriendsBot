package bot;

import java.util.ArrayList;
import java.util.List;

import game.Tile;

public class AlgorithmJ extends Algorithm {

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		
		String moves = "";

		return moves;
	}
	
	/**
	 * This method aims to find a perfect fitting gap for the J tetrimino rotated
	 * 180 degrees.
	 * 
	 * It uses the findGap and findSpaces method in order to find the fitting gap.
	 * 
	 * @return List of Tile objects that represent the top-left corner of the found gaps
	 */
	private List<Tile> findFittingGap() {
		List<Tile> foundGaps = new ArrayList<Tile>();
		
		// Get gaps of size 1,2
		// Get flat spaces of size 1
		// Iterate through both in a nested loop
		// Check if the two current gaps and spaces form the fitting gap
		
		List<Tile> gaps = findGap(1,2);
		List<Tile> spaces = findFlatSpace(1);
		
		for (int i = 0; i < gaps.size(); i++) {
			for (int j = 0; j < spaces.size(); j++) {
				Tile bottomPiece = gaps.get(i);
				Tile topPiece = spaces.get(j);
				
				if (bottomPiece.column() == topPiece.column() - 1 &&
						bottomPiece.row() - 1 == topPiece.row()) {
					foundGaps.add(game.get(topPiece.row(), bottomPiece.column()));
				}
			}
		}
		
		
		return foundGaps;
	}

}