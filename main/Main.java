package main;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bot.MoveGenerator;
import bot.SimulatedGame;
import bot_old.Algorithm;
import bot_old.AlgorithmI;
import bot_old.AlgorithmJ;
import bot_old.AlgorithmL;
import bot_old.AlgorithmO;
import bot_old.AlgorithmS;
import bot_old.AlgorithmT;
import bot_old.AlgorithmZ;
import bot_old.Inputer;
import game.BoardDetector;
import game.Tetrimino;
import game.Tile;
import game.RealGame;

public class Main {
	
	public static void main(String[] args) throws IOException, AWTException, InterruptedException {
		
		RealGame game = RealGame.getInstance();
		Inputer inputer = new Inputer();
		Robot robot = new Robot();
		
		while(true) {
			if (game.gameDetected()) {
				Tetrimino currentTetrimino = game.getCurrentTetrimino();
				
				
				
				if (currentTetrimino != null) {
					// Create a list of moves for the tetrimino DONE
					// Iterate through each move >> Main 
					// For each, create a simulated game and simulate the move >> SimulatedGame
					// Get the calculated score for that move >> SimulatedGame
					// If it is highest, set move and score as highest >> Main
					// Afterwards, execute the best move >> Inputer
					
					for (String move : new MoveGenerator().generateMoves(currentTetrimino)) {
						SimulatedGame sg = new SimulatedGame(currentTetrimino);
						sg.simulateMove(move);
						System.out.println(sg);
					}
					
					
				}	
				
				robot.delay(1000);
			}			
			game.update();
			
		}
		
//		System.out.println(Tetrimino.O.toString());
//		System.out.println(Tetrimino.I.toString());
//		System.out.println(Tetrimino.S.toString());
//		System.out.println(Tetrimino.Z.toString());
//		System.out.println(Tetrimino.L.toString());
//		System.out.println(Tetrimino.J.toString());
//		System.out.println(Tetrimino.T.toString());
//		
//		Tile[][] tiles = Tetrimino.T.tileRepresentation(3);
//		
//		String stringRepresentation = "";
//
//		// Iterate through all tiles
//		for (int i = 0; i < tiles.length; i++) {
//
//			String row = "";
//
//			for (int j = 0; j < tiles[0].length; j++) {
//				row += tiles[i][j].toString(); // append string representation of tile to the row
//			}
//
//			stringRepresentation += row + "\n"; // append row to the overall string
//		}
//
//		System.out.println(stringRepresentation);
//		
	}

}
