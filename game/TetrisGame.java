package game;

/**
 * This class represents the play field of the Tetris game.
 * 
 * @author Andy
 *
 */
public class TetrisGame {
	private static TetrisGame game;
	
	private static int HEIGHT = 20;
	private static int WIDTH = 10;
	
	private Tile tiles[][];
	private BoardDetector bd;
	
	private Tetrimino currentTetrimino;
	
	private boolean gameDetected;
	
	public static TetrisGame getInstance() {
		if (game == null) {
			game = new TetrisGame();
		}
		
		return game;
	}
	
	private TetrisGame() {
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
		}
	}
	
	/**
	 * Used to retrieve the tile in the specified row and column
	 * @param row row of the tile
	 * @param column column of the tile
	 * @return the Tile object in the specified row and column
	 */
	public Tile get(int row, int column) {
		return tiles[row][column];
	}
	
	/**
	 * @return Height of the game board
	 */
	public int height() {
		return HEIGHT;
	}
	
	/**
	 * @return Width of the game board
	 */
	public int width() {
		return WIDTH;
	}
	
	public Tetrimino getCurrentTetrimino() {
		return currentTetrimino;
	}
	
	public boolean gameDetected() {
		return gameDetected;
	}
	
	/**
	 * Returns a string representation of the play field
	 */
	public String toString() {
		String stringRepresentation = "";
		
		// Iterate through all tiles
		for (int i = 0; i < tiles.length; i++) {
			
			String row = "";
			
			for (int j = 0; j < tiles[0].length; j++) {
				row += tiles[i][j].toString(); // append string representation of tile to the row
			}
			
			stringRepresentation += row + "\n"; // append row to the overall string
		}
		
		return stringRepresentation;
	}
	
	public void saveScreenshot() {
		bd.saveScreenshot();
	}
	
	
	

}
