package game;

/**
 * This class represents the individual tiles of the Tetris game board.
 * For now, each Tile will have one of two states: empty, or filled.
 * 
 * @author Andy
 *
 */
public class Tile {
	private TileState state;
	private int row, column;
	
	public Tile(int row, int column) {
		state = TileState.EMPTY;
		
		this.row = row;
		this.column = column;
	}
	
	public int row() {
		return row;
	}
	
	public int column() {
		return column;
	}
	
	public TileState getState() {
		return state;
	}
	
	public void setState(TileState state) {
		this.state = state;
	}
	
	public String toString() {
		
		if (state == TileState.EMPTY) {
			return "[ ]";
		}
		
		return "[*]";
	}
}

enum TileState {
	EMPTY, FILLED
}
