package game;

import java.awt.Color;

public enum Tetrimino {
	I('I', new Color(50, 190, 250), 2, 3, 5, 3, 5, 4, 1), 
	O('O', new Color(255, 194, 37), 1, 4, 4, 4, 4, 2, 2),
	T('T', new Color(210, 76, 173), 4, 3, 4, 3, 3, 3, 2), 
	S('S', new Color(124, 212, 36), 2, 3, 4, 3, 4, 3, 2), 
	Z('Z', new Color(250, 50, 90), 2, 3, 4, 3, 4, 3, 2), 
	J('J', new Color(68, 100, 233), 4, 3, 4, 3, 3, 3, 2),
	L('L', new Color(255, 126, 37), 4, 3, 4, 3, 3, 3, 2);

	private Color color;
	private int orientations, width, height;
	private int[] startingPositions;
	private Tile[][] tiles;

	private Tetrimino(char type, Color color, int orientations, int startPos0, int startPos1, int startPos2, int startPos3, int width, int height) {
		this.color = color;

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

	private void constructTileRepresentation(char type) {
		tiles = new Tile[height][width];

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				Tile tile = new Tile(i, j);
				tiles[i][j] = tile;	
			}
		}

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

	public Color getColor() {
		return color;
	}

	public int orientations() {
		return orientations;
	}

	public int startingPosition(int orientation) {
		return startingPositions[orientation];
	}

	// Axis is an int referring to whether the tetrimino is horizontal or vertical
	// 0 = Horizontal
	// 1 = Vertical
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
