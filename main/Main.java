package main;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bot.Algorithm;
import bot.Inputer;
import game.BoardDetector;
import game.TetrisGame;

public class Main {
	
	public static void main(String[] args) throws IOException, AWTException, InterruptedException {
		
		// Initialise objects
		// Detect gameboard
		
		// Loop
		
//		BoardDetector test = new BoardDetector(10, 20);
//		
//		File file = new File("image.png");
//		file.createNewFile();
//		ImageIO.write(test.getScreenshot(), "png", file);
		
		
		
		
//		TetrisGame game = TetrisGame.getInstance();
//		
//		game.getCurrentTetrimino();
//		
//		System.out.println(game);
		TetrisGame game = TetrisGame.getInstance();
		System.out.println(game);
		//Algorithm dm = new Algorithm();
		Inputer inputer = new Inputer();
		
		int test = 0;
		
		while(true) {
			//inputer.executeMoves("ccllrrrs");		
			if (game.gameDetected()) {
				Algorithm alg;
				
				if (game.getCurrentTetrimino() != null) {
					switch(game.getCurrentTetrimino()) {
					case I:
						break;
					case J:
						break;
					case L:
						break;
					case O:
						break;
					case S:
						break;
					case T:
						break;
					case Z:
						break;
					default:
						break;
					
					}
					
					System.out.println(game);
				}
				
				// Get current tetrimino
				// Create a dm based on current tetrimino
				// dm returns a string containing list of moves to make
				// pass that string to inputer
				
				
				new Robot().delay(5000);
				
			}
			
			else {
				// do nothing. since no game found
			}
			game.update();
		}
		
	}

}
