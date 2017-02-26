package game;

/**
 * This class represents the play field of the Tetris game.
 * 
 * @author Andy
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
		
		// Creates tiles
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j] = new Tile(i, j);
			}
		}
		
		update();
			
	}
	
	/**
	 * This method is used to update the state of each tile as well as determine
	 * the current tetrimino.
	 */
	public void update() {
		
		bd.calibrate();
		
		gameDetected = bd.gameDetected();
		
		if (gameDetected) {		
			// Iterate through all tiles
			for (int i = 0; i < tiles.length; i++) {
				for (int j = 0; j < tiles[0].length; j++) {
					tiles[i][j].setState(bd.getState(i, j)); // use BoardDetector to determine the state of the tile
				}
			}
			
			currentTetrimino = bd.getCurrentTetrimino(); // BoardDetector to determine the current tetrimino
			secondTetrimino = bd.getSecondTetrimino();
		}
	}
	
	public boolean gameDetected() {
		return gameDetected;
	}
	
	public void saveScreenshot() {
		bd.saveScreenshot();
	}
	
	
	

}
