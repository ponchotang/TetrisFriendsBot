package game;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/**
 * Class that provides a way to determine the tile states
 * This requires the user to input the board size.
 * It also requires the user to input the tile number in order to
 * get the tile state
 * 
 * @author Andy
 *
 */
public class BoardDetector {
	private static final int BOARD_IMAGE_HEIGHT = 0;
	private static final int BOARD_IMAGE_WIDTH = 0;
	
	private final int height;
	private final int width;
	
	
	
	private Robot robot;
	private BufferedImage screenshot;
	private Point startingPixel;
	private Rectangle screenResolution;
	private Rectangle boardResolution;
	
	public BoardDetector(int height, int width) {
		this.height = height;
		this.width = width;
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		// Takes a screenshot of the entire screen
		// Code taken from: http://www.java2s.com/Code/JavaAPI/java.awt/RobotcreateScreenCaptureRectanglescreenRect.htm
		screenResolution = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		screenshot = robot.createScreenCapture(screenResolution);
		
		determineStartingPixel();
		determineBoardResolution();
		
	}
	
	private void determineStartingPixel() {
		// 12 pixels from first gray
		
		boolean startFound = false;
		
		// Iterates over the screenshot
		for (int i = 0; i < screenshot.getHeight(); i++) {
			for (int j = 0; j < screenshot.getWidth(); j++) {
				
				// Gets color of current pixel
				Color pixel = new Color(screenshot.getRGB(j, i));
				
				// Determines if that pixel is the same color as the game board outline
				if (pixel.equals(Colors.BOARD_OUTLINE)) {
					boolean foundTemp = true;
					
					// Checks if the next 700 pixels are the same color
					// It is the game board outline if it is
					for (int m = j; m < j + 700; m++) {
						
						if (m < screenshot.getWidth()) {
							Color pixelTemp = new Color(screenshot.getRGB(m, i));
							
							if (!pixelTemp.equals(Colors.BOARD_OUTLINE)) {
								foundTemp = false;
							}
						}
						
					}
					
					// Initalises startingPixel
					if (foundTemp) {
						startingPixel = new Point(j - 12, i);
						startFound = true;
						break;
					}
				}
			}
			
			if (startFound) break;
		}
		
		System.out.println(startingPixel);
		
	}
	
	private void determineBoardResolution() {
		
	}
	
	public BufferedImage getScreenshot() {
		return screenshot;
	}
	
	

}
