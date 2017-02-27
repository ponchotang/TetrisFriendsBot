package game;

/**
 * A class for the general representation of a Tetris game.
 * 
 * @author Andy Tang
 *
 */
public class TetrisGame {
	protected static int HEIGHT = 20;
	protected static int WIDTH = 10;

	protected Tetrimino[] tetriminos = new Tetrimino[3];
	protected Tile tiles[][];

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
	 * Returns the height of the specified column.
	 * To be more specific, the height is the highest position of
	 * a filled tile in that column.
	 */
	public int columnHeight(int column) {
		int height = height();
		
		for (int i = 0; i < height(); i++) {
			
			if (tiles[i][column].filled()) {
				break;
			}
			
			height--;
		}
		
		return height;
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
	
	/**
	 * Returns the tetrimino at the specified position
	 */
	public Tetrimino getTetrimino(int position) {
		
		// Prevents out of bounds exception
		if (position < 0 || position >= tetriminos.length) {
			return null;
		}
		
		return tetriminos[position];
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
