package bot;

import java.awt.AWTException;
import java.awt.Robot;

public class Inputer {
	private Robot robot;
	
	public Inputer() {
		try {
			robot = new Robot();
		} catch (AWTException e) {

		}
	}
	
	public void executeMoves(String moves) {
		
	}

}
