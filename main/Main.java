package main;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bot.Algorithm;
import bot.AlgorithmI;
import bot.AlgorithmJ;
import bot.AlgorithmL;
import bot.AlgorithmO;
import bot.AlgorithmS;
import bot.AlgorithmT;
import bot.AlgorithmZ;
import bot.Inputer;
import game.BoardDetector;
import game.TetrisGame;

public class Main {
	
	public static void main(String[] args) throws IOException, AWTException, InterruptedException {
		
		TetrisGame game = TetrisGame.getInstance();
		Inputer inputer = new Inputer();
		Robot robot = new Robot();
		
		while(true) {
			if (game.gameDetected()) {
				Algorithm alg = null;
				
				if (game.getCurrentTetrimino() != null) {
					switch(game.getCurrentTetrimino()) {
					case I:
						alg = new AlgorithmI();
						break;
					case J:
						alg = new AlgorithmJ();
						break;
					case L:
						alg = new AlgorithmL();
						break;
					case O:
						alg = new AlgorithmO();
						break;
					case S:
						//alg = new AlgorithmS();
						break;
					case T:
						//alg = new AlgorithmT();
						break;
					case Z:
						//alg = new AlgorithmZ();
						break;
					default:
						break;
					
					}
					
					if (alg != null) {
						System.out.println(alg.execute());
						inputer.executeMoves(alg.execute());
						robot.delay(1000);
					}
					
				}	
			}			
			game.update();
		}
		
	}

}
