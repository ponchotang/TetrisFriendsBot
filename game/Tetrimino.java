package game;

import java.awt.Color;

public enum Tetrimino {
	I(new Color(50, 190, 250), 2, 3, 5, 3, 5, 4, 1), 
	O(new Color(255, 194, 37), 1, 4, 4, 4, 4, 2, 2),
	T(new Color(210, 76, 173), 4, 3, 4, 3, 3, 3, 2), 
	S(new Color(124, 212, 36), 2, 3, 4, 3, 4, 3, 2), 
	Z(new Color(250, 50, 90), 2, 3, 4, 3, 4, 3, 2), 
	J(new Color(68, 100, 233), 4, 3, 4, 3, 3, 3, 2),
	L(new Color(255, 126, 37), 4, 3, 4, 3, 3, 3, 2);
	
	private Color color;
	private int orientations, horizontalWidth, verticalWidth;
	private int[] startingPositions;
	
	private Tetrimino(Color color, int orientations, int startPos0, int startPos1, int startPos2, int startPos3, int horizontalWidth, int verticalWidth) {
		this.color = color;
		
		this.orientations = orientations;
		
		startingPositions = new int[4];
		
		startingPositions[0] = startPos0;
		startingPositions[1] = startPos1;
		startingPositions[2] = startPos2;
		startingPositions[3] = startPos3;
		
		this.horizontalWidth = horizontalWidth;
		this.verticalWidth = verticalWidth;
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
	public int width(int axis) {
		if (axis == 0) {
			return horizontalWidth;
		}
		
		else {
			return verticalWidth;
		}
	}

}
