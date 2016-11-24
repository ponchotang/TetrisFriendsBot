package bot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 * Class containing a method used to parse and execute the inputs
 * as dictated by the inputed string.
 * 
 * @author Andy
 *
 */
public class Inputer {
	private static final char ROTATE = 'r';
	private static final char SET_PIECE = 's';
	private Robot robot;
	
	public Inputer() {
		try {
			robot = new Robot();
		} catch (AWTException e) {

		}
	}
	
	/**
	 * Parses and executes the list of inputs dictated by
	 * the passed in string.
	 * 
	 * The string must not have any whitespace, and only consist of the following characters:
	 * r: rotate
	 * s: set piece
	 * 
	 * @param moves
	 */
	public void executeMoves(String moves) {
		
		// Iterate through all characters in the string
		for (char move : moves.toCharArray()) {
			
			if (move == ROTATE) {
				rotate();
			}
			
			else if (move == SET_PIECE) {
				setPiece();
			}
		}
		
	}
	
	/**
	 * Helper method to execute keypresses
	 * @param keycode keycode of the key to be pressed
	 */
	private void pressKey(int keycode) {
		robot.keyPress(keycode);
		robot.delay(50);
		
		robot.keyRelease(keycode);
		robot.delay(50);
	}
	
	/**
	 * Rotates the piece 90 degrees
	 */
	private void rotate() {
		pressKey(KeyEvent.VK_UP);
	}
	
	/**
	 * Sets the piece
	 */
	private void setPiece() {
		pressKey(KeyEvent.VK_SPACE);
	}

}
