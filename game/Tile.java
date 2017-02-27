package game;

/**
 * Class representing the individual tiles of the Tetris game board.
 * 
 * @author Andy Tang
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
	
	public boolean empty() {
		return state.equals(TileState.EMPTY);
	}
	
	public boolean filled() {
		return state.equals(TileState.FILLED);
	}
	
	public boolean invalid() {
		return state.equals(TileState.INVALID);
	}
	
	public TileState state() {
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

