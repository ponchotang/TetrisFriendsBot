package bot;

import java.util.List;

import game.Tile;

public abstract class AlgorithmLJ extends Algorithm{
	
	protected static final int DEFAULT_VERTICAL_POSITION_1 = 4;
	protected static final int DEFAULT_VERTICAL_POSITION_2 = 3;
	protected static final int DEFAULT_HORIZONTAL_POSITION = 3;
	
	protected static String VERTICAL_HANGING;
	protected static String HORIZONTAL_HANGING;
	protected static String VERTICAL_FLAT;
	protected static String HORIZONTAL_FLAT;

	@Override
	public String execute() {
		String moves = "";

		// Vertical fitting gap
		List<Tile> gaps = findFittingVerticalGap();

		if (gaps.size() > 0) {
			moves += VERTICAL_HANGING + determineHorizontalMovement(DEFAULT_VERTICAL_POSITION_1, gaps.get(0).column()) + "s";
		}

		// Horizontal fitting gap
		if (moves.length() == 0) {
			gaps = findFittingHorizontalGap();

			if (gaps.size() > 0) {
				moves += HORIZONTAL_HANGING + determineHorizontalMovement(DEFAULT_HORIZONTAL_POSITION, gaps.get(0).column()) + "s";
			}		
		}

		// Horizontal flat space 
		if (moves.length() == 0) {
			gaps = findFlatSpace(3);

			if (gaps.size() > 0) {
				moves += HORIZONTAL_FLAT + determineHorizontalMovement(DEFAULT_HORIZONTAL_POSITION, gaps.get(0).column()) + "s";
			}		
		}

		// Vertical flat space 
		if (moves.length() == 0) {
			gaps = findFlatSpace(2);

			if (gaps.size() > 0) {
				moves += VERTICAL_FLAT + determineHorizontalMovement(DEFAULT_VERTICAL_POSITION_2, gaps.get(0).column()) + "s";
			}		
		}
		
		return moves;
	}
	
	protected abstract List<Tile> findFittingVerticalGap();
	protected abstract List<Tile> findFittingHorizontalGap();

}
