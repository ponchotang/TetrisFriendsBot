package bot;

import game.RealGame;
import game.Tetrimino;
import game.TetrisGame;
import game.Tile;

public class SimulatedGame extends TetrisGame{
	/*
	 * Allows creation of a simulation by using the current state of the real game
	 * This allows us to test all possible moves and retrieve game states from them in order
	 * to determine which is the best move
	 * 
	 * This class will be created by using TetrisGame as a parameter.
	 * It will recreate the board state
	 * 
	 * It will have a simulate move method which takes in a tetrimino and an input of moves.
	 * 
	 * There will also be a score method which calculates the score of the current state
	 */
	
	private Tetrimino currentTetrimino;
	
	public SimulatedGame(Tetrimino currentTetrimino) {
		this.currentTetrimino = currentTetrimino;
		
		RealGame game = RealGame.getInstance();
		
		tiles = new Tile[game.height()][game.width()];
		
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				
				Tile tile = new Tile(i,j);
				tile.setState(game.get(i, j).state());
				tiles[i][j] = tile;
				
			}
		}
		
		
	}
	
}
