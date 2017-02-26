package main;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;

import bot.Inputer;
import bot.MoveGenerator;
import bot.SimulatedGame;
import game.Tetrimino;
import game.RealGame;

public class Main {
	
	public static void main(String[] args) throws IOException, AWTException, InterruptedException {
		
		RealGame game = RealGame.getInstance();
		Inputer inputer = new Inputer();
		Robot robot = new Robot();
			
		while(true) {
			if (game.gameDetected()) {
				Tetrimino currentTetrimino = game.getCurrentTetrimino();
				Tetrimino nextTetrimino = game.getSecondTetrimino();
							
				if (currentTetrimino != null && nextTetrimino != null) {
					
					System.out.println(game);
			
					// Resetting variables
					int highestScore = 0;
					String bestMove = "";
					boolean isFirstMove = true;
					
					for (String firstMove: new MoveGenerator().generateMoves(currentTetrimino)) {
						for (String secondMove : new MoveGenerator().generateMoves(nextTetrimino)) {
							
							SimulatedGame sg = new SimulatedGame();
							sg.add(currentTetrimino);
							sg.add(nextTetrimino);
							sg.simulateMove(firstMove);						
							sg.simulateMove(secondMove);
							
							int score = sg.calculateScore();
							
							// Set as best move if it beats the current high score
							if (isFirstMove || score > highestScore) {
								bestMove = firstMove;
								highestScore = score;
								isFirstMove = false;
							}
						}
					}
					
					// Execute best move
					inputer.executeMoves(bestMove + "s");
									
				}							
			}			
			game.update();			
		}			
	}

}
