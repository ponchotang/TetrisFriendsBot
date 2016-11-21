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
	private static final int START_PIXEL_OFFSET_X = 12;
	private static final int START_PIXEL_OFFSET_Y = 9;
	private static final int HORIZONTAL_OFFSET = 12;
	private static final int VERTICAL_OFFSET = 10;
	
	private final int width;
	private final int height;

	private Robot robot;
	private BufferedImage screenshot;
	private Point startingPixel, playfieldStartingPixel;
	private Rectangle screenResolution, boardResolution, playfieldResolution;
	
	private int tileSize, tileGap;
	
	public BoardDetector(int width, int height) {
		this.width = width;
		this.height = height;
		
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
		
		// Takes a screenshot of the game board only
		screenshot = robot.createScreenCapture(boardResolution);
		
		determinePlayFieldStartingPixel();
		determinePlayFieldSpecifications();
		
		// Takes a screenshot of the play field only
		screenshot = robot.createScreenCapture(playfieldResolution);
		
	}
	
	/**
	 * This method uses the screenshot of the screen in order to determine
	 * the starting pixel of the game board.
	 */
	private void determineStartingPixel() {
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
					
					// Initalises startingPixel if it has been found
					if (foundTemp) {
						startingPixel = new Point(j - START_PIXEL_OFFSET_X, i);
						startFound = true;
						break;
					}
					
				}
			}
			
			// Breaks out of loop if the starting pixel has been found
			if (startFound) break;
		}
		
	}
	
	/**
	 * Uses the starting pixel of the game board in order to determine
	 * the area on the screen containing the game board.
	 * 
	 * The offsets are due to the fact that the corners of the board are curved and so
	 * the corner points are colored white.
	 * 
	 * The offsets are therefore used to make up for the unaccounted pixels since the outline
	 * is determine by a continuous line of gray pixels.
	 */
	private void determineBoardResolution() {
		
		// Starting point in which to detect the game board outline
		int x = startingPixel.x + START_PIXEL_OFFSET_X;
		int y = startingPixel.y + START_PIXEL_OFFSET_Y;
		
		// Starting width and heights (accounts for offsets)
		int width = START_PIXEL_OFFSET_X + HORIZONTAL_OFFSET;
		int height = START_PIXEL_OFFSET_Y + VERTICAL_OFFSET;
		
		// Starting pixels
		Color xPixel = new Color(screenshot.getRGB(x, startingPixel.y));
		Color yPixel = new Color(screenshot.getRGB(startingPixel.x, y));
	
		// Iterates horizontally until the outline ends (determines width)
		while (xPixel.equals(Colors.BOARD_OUTLINE)) {
			width++;
			x++;
			xPixel = new Color(screenshot.getRGB(x, startingPixel.y));
		}
		
		//Iterates vertically until the outline ends (determines height)
		while (yPixel.equals(Colors.BOARD_OUTLINE)) {
			height++;
			y++;
			yPixel = new Color(screenshot.getRGB(startingPixel.x, y));
		}
		
		// Initalises boardResolution with the calculated values
		boardResolution = new Rectangle(startingPixel.x, startingPixel.y, width, height);
	}
	
	/**
	 * This method determines the starting pixel of the actual play field.
	 * This pixel will be used in order to determine the dimensions of the play field.
	 */
	private void determinePlayFieldStartingPixel() {
		
		boolean startFound = false;
		
		// Iterate through all pixels of screenshot
		for (int i = 0; i < screenshot.getHeight(); i++) {
			for (int j = 0; j < screenshot.getWidth(); j++) {
				
				// Get color of current pixel
				Color pixel = new Color(screenshot.getRGB(j, i));
				
				// If it equals the first empty tile
				if (pixel.equals(Colors.EMPTY_TILE_1)) {
					
					boolean tempFound = true;
					
					// Iterate through next horizontal 15 pixels
					// and checks if they are the same color
					for (int m = i; m < i + 15; m++) {
						
						// Prevents out of coordinates exception
						if (m < screenshot.getWidth()) {
							
							Color tempPixel = new Color(screenshot.getRGB(j, m));
							
							if (!tempPixel.equals(Colors.EMPTY_TILE_1)) {
								tempFound = false;
								break;
							}
						}
						
					}
					
					// Iterate through next vertical 15 pixels
					// and checks if they are the same color
					for (int n = j; n < j + 15; n++) {
						
						// Prevents out of coordinates exception
						if (n < screenshot.getHeight()) {
							Color tempPixel = new Color(screenshot.getRGB(n, i));
							
							if (!tempPixel.equals(Colors.EMPTY_TILE_1)) {
								tempFound = false;
								break;
							}
						}
						
					}
					
					// Create starting pixel if it has been found
					if (tempFound) {
						startFound = true;
						playfieldStartingPixel = new Point(j, i);
						break;
					}
				}
			}
			
			if (startFound) break;
		}
		
	}
	
	/**
	 * This method uses the starting pixel of the play field in order
	 * to determine the pixel sizes of the tiles and gaps between tiles.
	 * 
	 * It then uses these values in order to calculate the area of the 
	 * play field.
	 */
	private void determinePlayFieldSpecifications() {
		
		// Starting pixel positions
		int x = playfieldStartingPixel.x;
		int y = playfieldStartingPixel.y;
		
		// Initializing variables
		tileSize = 0;
		tileGap = 0;
		
		// Initial color
		Color pixel = new Color(screenshot.getRGB(x, y));
		
		// Determine size by iterating until color changes
		while (pixel.equals(Colors.EMPTY_TILE_1)) {
			tileSize++;
			x++;
			pixel = new Color(screenshot.getRGB(x, y));
		}
		
		// Determine gap by iterating until color changes
		while (!pixel.equals(Colors.EMPTY_TILE_2)) {
			tileGap++;
			x++;
			pixel = new Color(screenshot.getRGB(x, y));
		}
		
		// Calculate width and height of play field
		int playfieldWidth = (tileSize * width) + (tileGap * (width - 1));
		int playfieldHeight = (tileSize * height) + (tileGap * (height - 1));
		
		// Create rectangle containing the play field
		playfieldResolution = new Rectangle(startingPixel.x + playfieldStartingPixel.x,
				startingPixel.y + playfieldStartingPixel.y, playfieldWidth, playfieldHeight);
			
	}
	
	//TODO: Find colors of all tetris pieces. Implement internal representation of play field.
	
	public BufferedImage getScreenshot() {
		return screenshot;
	}
	
	

}
