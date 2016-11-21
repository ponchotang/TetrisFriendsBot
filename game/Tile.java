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
	
	public Tile() {
		state = TileState.EMPTY;
	}
	
	public TileState getState() {
		return state;
	}
	
	public void setState(TileState state) {
		this.state = state;
	}
}

enum TileState {
	EMPTY, FILLED
}
