package main;

import java.awt.AWTException;
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

		while(true) {
			
			if (game.gameDetected()) {
				Tetrimino currentTetrimino = game.getTetrimino(0);
				Tetrimino secondTetrimino = game.getTetrimino(1);
				Tetrimino thirdTetrimino = game.getTetrimino(2);

				if (currentTetrimino != null && secondTetrimino != null && thirdTetrimino != null) {

					// Resetting variables
					int highestScore = 0;
					String bestMove = "";
					boolean isFirstMove = true;

					// Double nested for loop to simulate all possible moves for the next two tetrimino
					for (String firstMove: new MoveGenerator().generateMoves(currentTetrimino)) {
						for (String secondMove : new MoveGenerator().generateMoves(secondTetrimino)) {
							SimulatedGame sg = new SimulatedGame(); // create simulated game
							
							// Add the three tetriminos to the game
							sg.add(currentTetrimino);
							sg.add(secondTetrimino);
							
							// Simulate the moves
							sg.simulateMove(firstMove);						
							sg.simulateMove(secondMove);

							int score = sg.calculateScore(); // calculate score

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
