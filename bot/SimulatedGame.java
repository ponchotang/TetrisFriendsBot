package bot;

import game.RealGame;
import game.Tetrimino;
import game.TetrisGame;
import game.Tile;
import game.TileState;

public class SimulatedGame extends TetrisGame{
	/*
	 * Allows creation of a simulation by using the current state of the real game
	 * This allows us to test all possible moves and retrieve game states from them in order
	 * to determine which is the best move
	 * 
	 * This class will be created by using TetrisGame as a parameter.
	 * It will recreate the board state
	 * 
	 * It will have a simulate move method which takes in a tetrimino and an input of moves.
	 * 
	 * There will also be a score method which calculates the score of the current state
	 */
	
	private Tetrimino currentTetrimino;
	private boolean hasLost;
	
	public SimulatedGame(Tetrimino currentTetrimino) {
		this.currentTetrimino = currentTetrimino;
		
		hasLost = false;
		
		RealGame game = RealGame.getInstance();
		
		tiles = new Tile[game.height()][game.width()];
		
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				
				Tile tile = new Tile(i,j);
				tile.setState(game.get(i, j).state());
				tiles[i][j] = tile;
				
			}
		}
		
	}
	
	/**
	 * This method simulates what the board state will look like after inputting a move
	 * Essentially, what this method is doing is determining where to position the tetrmino (from the move String).
	 * It then checks IF the tetrimino was placed here, would it collide with the exisitng board? If none of the tiles collide, it moves down
	 * one row and repeats.
	 * What it is doing then is determining the lowest the tetrimino and be placed and thus what the board state would be if we placed
	 * it in this position in an actual game.
	 * 
	 */
	public void simulateMove(String move) {
		
		int orientation = 0;
		int leftMovement = 0;
		int rightMovement = 0;
		
		// Iterate through the moves String to determine orientation
		// and starting position
		for (char c : move.toCharArray()) {
			
			if (c == 'c') {
				orientation++;
			}
			
			else if (c == 'l') {
				leftMovement++;
			}
			
			else {
				rightMovement++;
			}
			
		}
		
		// Get tile representation of the tetrimino
		Tile[][] tileRepresentation = currentTetrimino.tileRepresentation(orientation);
		
		// Caclulate startingPosition
		int startingPosition = currentTetrimino.startingPosition(orientation) - leftMovement + rightMovement;
		
		// Initialising variables
		boolean hasCollided = false;
		int startingHeight = 0;
		
		// Iterate through entire height of board (minus the height of the tetrimino to prevent array out of bounds)
		for (startingHeight = 0; startingHeight < height() - currentTetrimino.height(orientation); startingHeight++) {
			
			// Resetting variables (these are used to retrieve tiles from the tetrimino)
			int tetriminoI = 0;
			int tetriminoJ = 0;
			
			// This nested loop iterates through the size of the tetrimino (its width and height)
			for (int i = startingHeight; i < startingHeight + currentTetrimino.height(orientation); i++) {
				tetriminoJ = 0;
				
				// Checks whether or not each tile of the tetrimino collides with the tile on the board
				for (int j = startingPosition; j < startingPosition + currentTetrimino.width(orientation); j++) {
					
					// Breaks if collision found (since now we have our starting height)
					if (tiles[i][j].filled() && tileRepresentation[tetriminoI][tetriminoJ].filled()) {
						hasCollided = true;
						break;
					}
					
					tetriminoJ++;
				}
				
				if (hasCollided) {
					break;
					
				}
				tetriminoI++;
			}
			
			if (hasCollided) {
				break;
			}
		}
		
		
		int tetriminoI = 0;
		int tetriminoJ = 0;
		
		// Decrease startingHeight by 1 to get the startingHeight right before the collision
		// Only decrease if it has collided because if it hasn't, there is no need to account
		// for a collision
		if (hasCollided) {
			startingHeight--;
		}
		
		if (startingHeight >= 0) {
			
			// Iterate through the size of the tetrimino, starting at the startingHeight that was found
			for (int i = startingHeight; i < startingHeight + currentTetrimino.height(orientation); i++) {
				tetriminoJ = 0;

				for (int j = startingPosition; j < startingPosition + currentTetrimino.width(orientation); j++) {
					
					// Replaces the tile in the board with the tile in the tetrimino ONLY if it is filled
					// This is essentially 'drawing' in the tetrimino
					if (tileRepresentation[tetriminoI][tetriminoJ].filled()) {
						tiles[i][j].setState(tileRepresentation[tetriminoI][tetriminoJ].state());

					}
							
					tetriminoJ++;
				}
				
				tetriminoI++;
			}		
		}
		
		else {
			// If startingHeight is < 0, then the move will result in a fail.
			hasLost = true;
		}
		

		
	
		removeFilledLines();
		
	}
	
	private void removeFilledLines() {
		
		// Iterate through all rows of the game
		for (int i = 0; i < height(); i++) {
			boolean isFilled = true; // reset boolean
			
			// Checks if the entire row is filled
			for (int j = 0; j < width(); j++) {
				if (tiles[i][j].empty()) {
					isFilled = false;
					break;
				}
			}
			
			// If entire row is filled
			if (isFilled) {
				
				// Iterate from that row and continue upwards
				for (int m = i; m > 0; m--) {
					
					// Replace tiles in that row with the tiles in the row above
					for (int n = 0; n < width(); n++) {
						tiles[m][n].setState(tiles[m-1][n].state());
					}
				}
				
				// Set row 0 to all empty tiles
				for (int n = 0; n < width(); n++) {
					tiles[0][n].setState(TileState.EMPTY);
				}
			}
		}
	}
	
	public int calculateScore() {
		
		// Score is 0 if the simulated move results in a loss
		if (hasLost) {
			return 0;
		}
		
		/*
		 * Scoring system
		 * 
		 * Takes into account maximum height, and gaps
		 * Future iterations of the scoring system would take into account
		 * the feasible of the board in fitting as many types of the tetriminos as possible
		 * 
		 * For now:
		 * Lower height = higher score
		 * Less gaps = higher score
		 * 
		 * Gap calculation
		 * 
		 * Iterate column by column:
		 * Set a flag once a filled tile is seen
		 * As you iterate down, if any tiles after the flagged tile are empty, deduce points (as gap is found)
		 */
		int height = height();
		
		for (int i = 0; i < height(); i++) {
			
			boolean rowEmpty = true;
			
			for (int j = 0; j < width(); j++) {
				if (tiles[i][j].filled()) {
					rowEmpty = false;
					break;
				}
			}
			
			if (rowEmpty) {
				height--;
			}
			
			else {
				break;
			}
		}
		
		return height() - height;
	}
	
}




















