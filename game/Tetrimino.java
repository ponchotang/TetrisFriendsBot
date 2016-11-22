package game;

import java.awt.Color;

public enum Tetrimino {
	I(new Color(15, 155, 215)), 
	O(new Color(227, 159, 2)),
	T(new Color(175, 41, 138)), 
	S(new Color(89, 177, 1)), 
	Z(new Color(215, 15, 55)), 
	J(new Color(33, 65, 198)),
	L(new Color(227, 91, 2));
	
	private Color color;
	
	private Tetrimino(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

}
