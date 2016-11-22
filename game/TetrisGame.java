package game;

public class TetrisGame {
	private static TetrisGame game;
	
	private static int HEIGHT = 20;
	private static int WIDTH = 10;
	
	private Tile tiles[][];
	private BoardDetector bd;
	
	public static TetrisGame getInstance() {
		if (game == null) {
			game = new TetrisGame();
		}
		
		return game;
	}
	
	private TetrisGame() {
		bd = new BoardDetector(WIDTH, HEIGHT);
		
		tiles = new Tile[HEIGHT][WIDTH];
		
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j] = new Tile();
			}
		}
		
		update();
		
		
		
	}
	
	private void update() {
		
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				
			}
		}
	}
	
	public String toString() {
		String stringRepresentation = "";
		
		for (int i = 0; i < tiles.length; i++) {
			
			String row = "";
			
			for (int j = 0; j < tiles[0].length; j++) {
				row += tiles[i][j].toString();
			}
			
			stringRepresentation += row + "\n";
		}
		
		return stringRepresentation;
	}
	
	
	

}
