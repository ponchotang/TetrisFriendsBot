package bot;

import java.util.List;

import game.Tile;

public class AlgorithmO extends Algorithm{
	
	private static final int DEFAULT_POSITION = 4;

	@Override
	public String execute() {
		// TODO Make use of findGap to decide on basic piece placements
		
		String moves = "";
		
		List<Tile> gaps = findGap(2,2);
		
		if (gaps.size() > 0) {
			moves += determineHorizontalMovement(DEFAULT_POSITION, gaps.get(0).column()) + "s";
		}
		
		if (moves.length() == 0) {
			for (int i = 2; i < game.width(); i++) {

				gaps = findGap(i, 1);

				if (gaps.size() > 0) {
					moves += determineHorizontalMovement(DEFAULT_POSITION, gaps.get(0).column()) + "s";
					break;
				}
			}
		}
		
		return moves;
	}
	


}
