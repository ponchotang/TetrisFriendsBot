package bot;

import java.util.ArrayList;
import java.util.List;

import game.TetrisGame;
import game.Tile;

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
	 * This method finds all available gaps of the specified width and height in
	 * the Tetris Game.
	 * 
	 * @param gapWidth Width of the desired gap
	 * @param gapHeight Height of the desired gap
	 * @return a list of Tile objects representing the top-left corner of each found gap.
	 */
	protected List<Tile> findGap(int gapWidth, int gapHeight) {
		
		List<Tile> gaps = new ArrayList<Tile>();
		
		Tile currentTile = null;
		
		// Iterate through all game tiles
		for (int i = 0; i < game.height(); i++) {
			for (int j = 0; j < game.width(); j++) {
				currentTile = game.get(i, j);
				
				// Check if currentTile is empty
				if (currentTile.empty()) {
					
					// Check if currentTile is left most tile OR the tile to its left is filled
					// If it is, then it is next to the wall, and is the 'start' of a gap.
					if ((j - 1) == -1 || game.get(i, j - 1).filled()) {
						
						// Initial values. 
						int gap = 0;
						int tempJ = j;
						Tile tempTile = currentTile;
						
						// Loop until it reaches the end of the row
						while (tempJ < game.width()) {
							tempTile = game.get(i, tempJ);
							
							// Check if tempTile is empty. Increment gap and tempJ values if it is.
							if (tempTile.empty()) {
								gap++;
								tempJ++;
							}
							
							// Otherwise it is filled, so break loop as now we have the gap size.
							else {
								break;
							}
						}
						
						// Check if the correct gap width has been found
						// If it has, the following block of code will check if each horizontal gap column
						// has the correct gap height
						if (gap == gapWidth) {
							
							// Temporarily set to true. The loop sets it to false as soon as one of the columns 
							// does not have the correct height gap.
							boolean gapFound = true;
							
							// Iterate through the horizontal gaps
							for (tempJ = j; tempJ < (j + gap); tempJ++) {
								
								int verticalGap = 0;
								int tempI = i;
								
								// Loop through the rows of the column to check height gap
								while (tempI < game.height()) {
									tempTile = game.get(tempI, tempJ);
									
									// Check if empty
									if (tempTile.empty()) {
										verticalGap++;
										tempI++;
									}
									
									else {
										break;
									}
								}
								
								// Sets gapFound to false if vertical gap size does not match the desired gap height.
								if (verticalGap != gapHeight) {
									gapFound = false;
									break;
								}
							}
							
							if (gapFound && isClear(currentTile, gapWidth)) {
								gaps.add(currentTile);
								break;
							}
						}			
					}
				}
			}
		}
		
		return gaps;
	}
	
	/**
	 * This method finds all flat spaces of the specified width.
	 * 
	 * @param minWidth the minimum width the flat space must be.
	 * @return a list of Tile objects represent the left most tile in the flat space.
	 */
	protected List<Tile> findFlatSpace(int minWidth) {
		
		List<Tile> flatSpaces = new ArrayList<Tile>();
		
		// Iterate through all tiles of the game
		for (int i = 0; i < game.height(); i++) {
			for (int j = 0; j < game.width() - minWidth; j++) {
				
				Tile currentTile = game.get(i,  j);
				
				// Check if empty
				if (currentTile.empty()) {
					
					boolean foundFlatSpace = true;
					
					// Iterate through successive horizontal tiles minWidth times 
					// to check if it is a flat space of at least that width
					for (int n = j; n < j + minWidth; n++) {
						
						Tile tempTile = game.get(i, n); 
						
						// Check if tile is empty AND the tile below is filled
						// The first clause of the OR expression is to prevent an index out of bounds exception
						// in the case that i is currently the last row.
						if (tempTile.empty() && (i == (game.height() - 1) || game.get(i + 1, n).filled())); // Do nothing
						
						// Otherwise, this is not a flat space.
						else {
							foundFlatSpace = false;
							break;
						}
					}
					
					// Add to list if found
					if (foundFlatSpace && isClear(currentTile, minWidth)) {
						flatSpaces.add(currentTile);
					}
				}	
			}
		}
		
		return flatSpaces;
	}
	
	/**
	 * This is a method which determines if the path towards a given tile is clear.
	 * 
	 * @param tile The starting tile
	 * @param width The width of space to check clearness
	 * @return true if path is clear, false otherwise.
	 */
	private boolean isClear(Tile tile, int width) {
		
		for (int j = tile.column(); j < tile.column() + width; j++) {
			for (int i = 0; i < tile.row(); i++) {
				
				Tile currentTile = game.get(i, j);
				
				if (currentTile.filled()) {
					return false;
				}	
			}
		}
		
		return true;
	}
	
	/**
	 * This is a helper method that determines the amount of movement
	 * required to get to a certain x position.
	 * 
	 * @param startX The x position of the left-most tile of the piece.
	 * @param endX The x position for the piece to move into.
	 * @return a String containing the horizontal moves that need to be executed
	 */
	protected String determineHorizontalMovement(int startX, int endX) {
		String moves = "";
		char direction;
		
		// Determine if piece needs to move left or right
		if (startX > endX) {
			direction = 'l';
		}
		
		else {
			direction = 'r';
		}
		
		// Add the amount of single directional moves required to the string by
		// using the difference between startX and endX.
		for (int i = 0; i < Math.abs(startX - endX); i++) {
			moves += direction;
		}
		
		return moves;
	}
	
	


}
