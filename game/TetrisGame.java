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
				tiles[i][j] = new Tile();
			}
		}
		
		update();
			
	}
	
	/**
	 * This method is used to update the state of each tile as well as determine
	 * the current tetrimino.
	 */
	private void update() {
		
		// Iterate through all tiles
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j].setState(bd.getState(i, j)); // use BoardDetector to determine the state of the tile
			}
		}
		
		currentTetrimino = bd.getCurrentTetrimino(); // BoardDetector to determine the current tetrimino
	}
	
	public Tetrimino getCurrentTetrimino() {
		return currentTetrimino;
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
	
	
	

}
