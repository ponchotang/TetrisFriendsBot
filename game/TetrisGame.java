package game;

public class TetrisGame {
	private TetrisGame game;
	
	
	
	public TetrisGame getInstance() {
		if (game == null) {
			game = new TetrisGame();
		}
		
		return game;
	}
	
	private TetrisGame() {
		// 20 * 10 board
		
	}
	
	

}
