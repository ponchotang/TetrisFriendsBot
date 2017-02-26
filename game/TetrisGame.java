package game;

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

	// Calculates and returns the height of the tower (e.g. height of the highest filled tile)
	public int currentHeight() {
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
		
		return height;
	}
	
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
	
	public Tetrimino getTetrimino(int position) {
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
