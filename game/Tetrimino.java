package game;

import java.awt.Color;

public enum Tetrimino {
	// TODO: Change colours since the placed tetriminos have a different shade
	I(new Color(50, 190, 250)), 
	O(new Color(255, 194, 37)),
	T(new Color(210, 76, 173)), 
	S(new Color(124, 212, 36)), 
	Z(new Color(250, 50, 90)), 
	J(new Color(68, 100, 233)),
	L(new Color(255, 126, 37));
	
	private Color color;
	
	private Tetrimino(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

}
