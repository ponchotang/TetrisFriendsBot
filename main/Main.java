package main;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.BoardDetector;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		// Initialise objects
		// Detect gameboard
		
		// Loop
		
		BoardDetector test = new BoardDetector(0, 0);
		
		File file = new File("image.png");
		file.createNewFile();
		ImageIO.write(test.getScreenshot(), "png", file);
	}

}
