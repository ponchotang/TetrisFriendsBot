package bot;

import game.TetrisGame;
import game.Tile;
import game.TileState;

/**
 * This is an abstract class meant to be extended.
 * 
 * It contains methods which determines certain information about the current board state,
 * which will be used by child classes in order to determine the best move.
 * 
 * @author Andy
 *
 */
public abstract class Algorithm {
	protected TetrisGame game;
	
	public Algorithm() {
		game = TetrisGame.getInstance();
	}
	
	/**
	 * Method which must be implemented by child classes.
	 * 
	 * It is the method which determines the best move for the
	 * current piece. 
	 */
	public abstract String execute();
	
	/**
	 * TODO:
	 * Methods needed:
	 * 
	 * Method for finding a gap of x width and y height. The returned result will be the
	 * tile that is in the top left corner of the gap.
	 * 
	 * will add more as I implement the Algorithms.
	 */
	
	
	/**
	 * This method finds the first available gap of the specified width and height in
	 * the Tetris Game.
	 * 
	 * @param gapWidth Width of the desired gap
	 * @param gapHeight Height of the desired gap
	 * @return the Tile object in the top-left corner of the found gap
	 */
	protected Tile findGap(int gapWidth, int gapHeight) {
		
		Tile currentTile = null;
		boolean gapFound = false;
		
		/*
		 * Iterate through each row
		 * 
		 * For each row:
		 * 	Until reach end of row, OR found appropriate gap:
		 *		Find empty tile whose leftward tile is filled
		 *		Determine amount of empty tiles after it.
		 *		If it doesn't match gapWidth, continue loop
		 *		If it does match
		 *			for each tile of the gap, ensure that there are empty tiles
		 *			below it which equals the gapHeight.
		 *			If all tiles in the gap have heights equal to gapHeight, the gap has been found
		 */
		
		for (int i = 0; i < game.height(); i++) {
			for (int j = 0; j < game.width(); j++) {
				
				currentTile = game.get(i, j);
				
				// If currentTile is empty
				if (currentTile.getState().equals(TileState.EMPTY)) {
					
					// Check if currentTile is left most tile OR the tile to its left is filled
					if ((j - 1) == -1 || game.get(i, j - 1).equals(TileState.FILLED)) {
						
					}
				}
			}
		}
		return currentTile;
	}
	
	


}
