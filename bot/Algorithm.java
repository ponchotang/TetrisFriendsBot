package bot;

import game.TetrisGame;

/**
 * This is an abstract class meant to be extended.
 * 
 * It contains methods which determines certain information about the current board state,
 * which will be used by child classes in order to determine the best move.
 * 
 * It also contains a template method which executes a set of methods in order which will be
 * overridden and implemented by child classes. These methods will contain the actual algorithm and 
 * each method will be used to consider a different set of placement possibilities (from best to worst).
 * 
 * For example, the first method may determining where to place it when the board is empty. The second one 
 * may be to determine placement given a certain available gap. In other words, the template method will cycle
 * through these methods so that the best possible move is determined.
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
	 * Template method which executes a specific method depending on the game state.
	 * 
	 * The first method will be when the board is empty.
	 */
	public final void execute() {
		
	}

}
