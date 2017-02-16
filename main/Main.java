package main;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bot.MoveGenerator;
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
import game.TetrisGame;

public class Main {
	
	public static void main(String[] args) throws IOException, AWTException, InterruptedException {
		
//		TetrisGame game = TetrisGame.getInstance();
//		Inputer inputer = new Inputer();
//		Robot robot = new Robot();
//		
//		while(true) {
//			if (game.gameDetected()) {
//				Tetrimino currentTetrimino = game.getCurrentTetrimino();
//				
//				if (currentTetrimino != null) {
//					// Create a list of moves for the tetrimino DONE
//					// Iterate through each move >> Main 
//					// For each, create a simulated game and simulate the move >> SimulatedGame
//					// Get the calculated score for that move >> SimulatedGame
//					// If it is highest, set move and score as highest >> Main
//					// Afterwards, execute the best move >> Inputer
//					
//					
//				}	
//			}			
//			game.update();
//		}
		
		MoveGenerator test = new MoveGenerator();
		
		
		for (String s : test.generateMoves(Tetrimino.J)) {
			System.out.println(s);
		}
		
	}

}
