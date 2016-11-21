package game;

public class TetrisGame {
	private TetrisGame game;
	
	private static int HEIGHT = 20;
	private static int WIDTH = 10;
	
	private Tile tiles[][];
	
	public TetrisGame getInstance() {
		if (game == null) {
			game = new TetrisGame();
		}
		
		return game;
	}
	
	private TetrisGame() {
		tiles = new Tile[HEIGHT][WIDTH];
		
		
		
	}
	
	
	

}
