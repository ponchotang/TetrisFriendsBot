package bot;

import game.TetrisGame;

/**
 * This is an abstract class meant to be extended.
 * 
 * It contains methods which determines certain information about the current board state,
 * which will be used by child classes in order to determine the best move.
 * 
 * @author Andy
 *
 */
public abstract class Algorithm {
	protected TetrisGame game;
	
	public Algorithm() {
		game = TetrisGame.getInstance();
	}
	
	/**
	 * Method which must be implemented by child classes.
	 * 
	 * It is the method which determines the best move for the
	 * current piece. 
	 */
	public abstract String execute();
	
	/**
	 * TODO:
	 * Methods needed:
	 * 
	 * Method for finding a gap of x width and y height. The returned result will be the
	 * tile that is in the top left corner of the gap.
	 * 
	 * will add more as I implement the Algorithms.
	 */
	
	


}
