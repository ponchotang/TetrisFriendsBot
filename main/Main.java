package main;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.BoardDetector;
import game.TetrisGame;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		// Initialise objects
		// Detect gameboard
		
		// Loop
		
//		BoardDetector test = new BoardDetector(10, 20);
//		
//		File file = new File("image.png");
//		file.createNewFile();
//		ImageIO.write(test.getScreenshot(), "png", file);
		
		
		
		
		TetrisGame game = TetrisGame.getInstance();
		
		System.out.println(game);
	}

}
