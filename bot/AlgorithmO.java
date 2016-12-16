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
			gaps = findGap(2, 1);
			
			if (gaps.size() > 0) {
				moves += determineHorizontalMovement(DEFAULT_POSITION, gaps.get(0).column()) + "s";
			}		
		}
		
		if (moves.length() == 0) {
			List<Tile> flatSpaces = findFlatSpace(2);
			
			if (flatSpaces.size() > 0) {
				moves += determineHorizontalMovement(DEFAULT_POSITION, flatSpaces.get(0).column()) + "s";
			}
		}
		
		game.saveScreenshot();
		System.out.println(game);
		System.out.println(moves);
		return moves;
	}
	


}
