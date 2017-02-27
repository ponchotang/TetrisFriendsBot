package game;

/**
 * This class represents the game board of the actual TetrisFriends game
 * 
 * @author Andy Tang
 *
 */
public class RealGame extends TetrisGame{
	private static RealGame game;
	
	private BoardDetector bd;
	
	private boolean gameDetected;
	
	public static RealGame getInstance() {
		if (game == null) {
			game = new RealGame();
		}
		
		return game;
	}
	
	private RealGame() {
		bd = new BoardDetector(WIDTH, HEIGHT);
		tiles = new Tile[HEIGHT][WIDTH];
		
		// Initialise tiles
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j] = new Tile(i, j);
			}
		}
		
		update();
			
	}
	
	/**
	 * This method is used to update the game board state
	 */
	public void update() {
		
		boolean validBoard = false;
		
		// Keep calibrating until all tiles are valid tiles (Empty or Filled)
		while (!validBoard) {
			
			validBoard = true;
			bd.calibrate();
			
			gameDetected = bd.gameDetected();
			
			if (gameDetected) {		
				
				// Iterate through all tiles
				for (int i = 0; i < tiles.length; i++) {
					for (int j = 0; j < tiles[0].length; j++) {
						tiles[i][j].setState(bd.getState(i, j)); // use BoardDetector to determine the state of the tile
						
						if (tiles[i][j].invalid()) {
							validBoard = false;
						}
					}
				}
				
				// Get the current and upcoming tetriminos
				tetriminos[0] = bd.getTetrimino(0);
				tetriminos[1] = bd.getTetrimino(1);
				tetriminos[2] = bd.getTetrimino(2);
			}
		}
		
	}
	
	public boolean gameDetected() {
		return gameDetected;
	}	

}
