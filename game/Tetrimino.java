package game;

import java.awt.Color;

/**
 * An enum representing each of the seven Tetriminos in the game
 * @author Andy Tang
 *
 */
public enum Tetrimino {
	I('I', new Color(50, 190, 250), new Color(15, 155, 215), 2, 3, 5, 3, 5, 4, 1), 
	O('O', new Color(255, 194, 37), new Color(227, 159, 2), 1, 4, 4, 4, 4, 2, 2),
	T('T', new Color(210, 76, 173), new Color(175, 41, 138), 4, 3, 4, 3, 3, 3, 2), 
	S('S', new Color(124, 212, 36), new Color(89, 177, 1), 2, 3, 4, 3, 4, 3, 2), 
	Z('Z', new Color(250, 50, 90), new Color(215, 15, 55), 2, 3, 4, 3, 4, 3, 2), 
	J('J', new Color(68, 100, 233), new Color(33, 65, 198), 4, 3, 4, 3, 3, 3, 2),
	L('L', new Color(255, 126, 37), new Color(227, 91, 2), 4, 3, 4, 3, 3, 3, 2);

	private Color color, filledColor;
	private int orientations, width, height;
	private int[] startingPositions;
	private Tile[][] tiles;

	/**
	 * @param type Letter representation of the tetrimino
	 * @param color Color of the tetrimino as a piece
	 * @param filledColor Color of the tetrimino as a filled tile
	 * @param orientations The amount of possible orientations for the tetrimino
	 * @param startPos0 Starting column of the first orientation
	 * @param startPos1 Starting column of the second orientation
	 * @param startPos2 Starting column of the third orientation
	 * @param startPos3 Starting column of the fourth orientation
	 * @param width Width of the tetrimino in its first orientation
	 * @param height Height of the tetrimino in its first orientation
	 */
	private Tetrimino(char type, Color color, Color filledColor, int orientations, int startPos0, int startPos1, int startPos2, int startPos3, int width, int height) {
		this.color = color;
		this.filledColor = filledColor;
		
		this.orientations = orientations;

		startingPositions = new int[4];

		startingPositions[0] = startPos0;
		startingPositions[1] = startPos1;
		startingPositions[2] = startPos2;
		startingPositions[3] = startPos3;

		this.width = width;
		this.height = height;

		constructTileRepresentation(type);
	}

	/**
	 * Constructs a tile representation of the tetrimino
	 */
	private void constructTileRepresentation(char type) {
		tiles = new Tile[height][width];
		
		// Creates an empty tiles array for the representation
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				Tile tile = new Tile(i, j);
				tiles[i][j] = tile;	
			}
		}

		// Manually input the filled tiles for the tetrimino
		switch (type) {
		case 'O':
			tiles[0][0].setState(TileState.FILLED);
			tiles[0][1].setState(TileState.FILLED);
			tiles[1][0].setState(TileState.FILLED);
			tiles[1][1].setState(TileState.FILLED);
			break;

		case 'I':
			tiles[0][0].setState(TileState.FILLED);
			tiles[0][1].setState(TileState.FILLED);
			tiles[0][2].setState(TileState.FILLED);
			tiles[0][3].setState(TileState.FILLED);
			break;

		case 'S':
			tiles[0][1].setState(TileState.FILLED);
			tiles[0][2].setState(TileState.FILLED);
			tiles[1][0].setState(TileState.FILLED);
			tiles[1][1].setState(TileState.FILLED);
			break;

		case 'Z':
			tiles[0][0].setState(TileState.FILLED);
			tiles[0][1].setState(TileState.FILLED);
			tiles[1][1].setState(TileState.FILLED);
			tiles[1][2].setState(TileState.FILLED);
			break;

		case 'L':
			tiles[0][2].setState(TileState.FILLED);
			tiles[1][0].setState(TileState.FILLED);
			tiles[1][1].setState(TileState.FILLED);
			tiles[1][2].setState(TileState.FILLED);
			break;

		case 'J':
			tiles[0][0].setState(TileState.FILLED);
			tiles[1][0].setState(TileState.FILLED);
			tiles[1][1].setState(TileState.FILLED);
			tiles[1][2].setState(TileState.FILLED);
			break;

		case 'T':
			tiles[0][1].setState(TileState.FILLED);
			tiles[1][0].setState(TileState.FILLED);
			tiles[1][1].setState(TileState.FILLED);
			tiles[1][2].setState(TileState.FILLED);
			break;

		}
	}
	
	/**
	 * Returns the tile representation in the specified orientation
	 */
	public Tile[][] tileRepresentation(int orientation) {
		Tile[][] tileRep;
		
		// Creates a Tile array of the appropriate size
		if (orientation == 0 || orientation == 2) {
			tileRep = new Tile[height][width];
		}
		
		else {
			tileRep = new Tile[width][height];
		}
		
		for (int i = 0; i < tileRep.length; i++) {
			for (int j = 0; j < tileRep[0].length; j++) {
				Tile tile = new Tile(i, j);
				
				int oldI = 0;
				int oldJ = 0;
				
				// No changes needed
				if (orientation == 0) {
					oldI = i;
					oldJ = j;
				}
				
				// The approriate calculations for determining the new position were found
				// by manually drawing the original tetrimino and its orientation on paper and figuring out
				// the conversion pattern from that.
				
				else if (orientation == 1) {
					
					if (tileRep[0].length == 2) {
						oldI = Math.abs(j - 1);
					}
					
					else {
						oldI = j;
					}
					
					oldJ = i; 
				}
				
				else if (orientation == 2) {
					oldI = Math.abs(i - 1);
					oldJ = Math.abs(j - 2);
				}
				
				else {
					oldI = j;
					oldJ = Math.abs(i - 2);
				}
				
				tile.setState(tiles[oldI][oldJ].state());
				
				tileRep[i][j] = tile;
			}
		}
		
		return tileRep;
	}

	public Color getColor() {
		return color;
	}
	
	public Color getFilledColor() {
		return filledColor;
	}

	public int orientations() {
		return orientations;
	}

	public int startingPosition(int orientation) {
		return startingPositions[orientation];
	}

	public int width(int orientation) {
		if (orientation == 0 || orientation == 2) {
			return width;
		}

		else {
			return height;
		}
	}

	public int height(int orientation) {
		if (orientation == 0 || orientation == 2) {
			return height;
		}

		else {
			return width;
		}
	}

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
