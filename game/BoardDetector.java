package game;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	private static final int TETRIMINO_OFFSET = 4;

	private final int width;
	private final int height;

	private Robot robot;
	private BufferedImage screenshot;
	private Point startingPixel, playfieldStartingPixel;
	private Rectangle screenResolution, boardResolution, playfieldResolution;

	private int tileSize, tileGap;
	private boolean foundGame, gameInView;

	public BoardDetector(int width, int height) {
		this.width = width;
		this.height = height;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		calibrate();

	}
	
	/**
	 * Method used to redetect the game.
	 */
	public void calibrate() {
		// Takes a screenshot of the entire screen
		// Code taken from: http://www.java2s.com/Code/JavaAPI/java.awt/RobotcreateScreenCaptureRectanglescreenRect.htm
		screenResolution = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		screenshot = robot.createScreenCapture(screenResolution);

		determineStartingPixel();
		
		if (foundGame) {
			determineBoardResolution();

			// Takes a screenshot of the game board only
			screenshot = robot.createScreenCapture(boardResolution);

			determinePlayFieldStartingPixel();
			
			if (gameInView) {
				determinePlayFieldSpecifications();

				// Takes a screenshot of the play field only
				screenshot = robot.createScreenCapture(playfieldResolution);
			}	
		}
	}

	/**
	 * This method uses the screenshot of the screen in order to determine
	 * the starting pixel of the game board.
	 */
	private void determineStartingPixel() {
		foundGame = false;

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
						foundGame = true;
						break;
					}

				}
			}

			// Breaks out of loop if the starting pixel has been found
			if (foundGame) break;
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

		gameInView = false;

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
					for (int m = j; m < j + 15; m++) {

						// Prevents out of coordinates exception
						if (m < screenshot.getWidth()) {

							Color tempPixel = new Color(screenshot.getRGB(m, i));

							if (!tempPixel.equals(Colors.EMPTY_TILE_1)) {
								tempFound = false;
								break;
							}
						}

					}

					// Iterate through next vertical 15 pixels
					// and checks if they are the same color
					for (int n = i; n < i + 15; n++) {

						// Prevents out of coordinates exception
						if (n < screenshot.getHeight()) {
							Color tempPixel = new Color(screenshot.getRGB(j, n));

							if (!tempPixel.equals(Colors.EMPTY_TILE_1)) {
								tempFound = false;
								break;
							}
						}

					}

					// Create starting pixel if it has been found
					if (tempFound) {
						gameInView = true;
						playfieldStartingPixel = new Point(j, i);
						break;
					}
				}
			}

			if (gameInView) break;
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
		while (pixel.equals(Colors.EMPTY_TILE_1) && x < screenshot.getWidth() - 1) {
			tileSize++;
			x++;
			pixel = new Color(screenshot.getRGB(x, y));
		}

		// Determine gap by iterating until color changes
		while (!pixel.equals(Colors.EMPTY_TILE_2) && x < screenshot.getWidth() - 1) {
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
		
		// Ensures that getState is not using an invalid x value.
		// This happens when the game has been found but the playfield is in an invalid state (e.g. not in progress).
		if (x == screenshot.getWidth() - 1) {
			tileSize = 0;
			tileGap = 0;
		}

	}


	/**
	 * TODO: Possibly more testing
	 * Method used to determine what the current tetrimino is.
	 * 
	 * @return the current tetrimino 
	 */
	public Tetrimino getCurrentTetrimino() {

		// Determine color of current tetrimino
		Color currentTetrimino = new Color(screenshot.getRGB((4 * tileSize) + (4 * tileGap) + TETRIMINO_OFFSET, TETRIMINO_OFFSET));

		// Iterate through all Tetriminos and determine which one it is
		for (Tetrimino tetrimino : Tetrimino.values()) {
			if (tetrimino.getColor().equals(currentTetrimino)) {
				return tetrimino;
			}
		}

		return null;
	}

	/**
	 * This method is used to determine the state (empty or filled) of the specified tile. 
	 * 
	 * @param row row of the tile
	 * @param column column of the tile
	 * @return a TileState enum describing the tile's state
	 */
	public TileState getState(int row, int column) {
		// Calculate x and y coordinates
		int x = (column * tileSize) + (column * tileGap);
		int y = (row * tileSize) + (row * tileGap);

		// Get color of tile
		Color tileColor = new Color(screenshot.getRGB(x, y));

		// Checks if it is empty by comparing with the 'empty tile' colors
		if (tileColor.equals(Colors.EMPTY_TILE_1) || tileColor.equals(Colors.EMPTY_TILE_2) 
				|| tileColor.equals(Colors.TETRIMINO_HIGHLIGHT_1) || tileColor.equals(Colors.TETRIMINO_HIGHLIGHT_2)) {
			return TileState.EMPTY;
		}

		// Get color of pixel used to determine the tetrimino type
		Color tetriminoColor = new Color(screenshot.getRGB(x + TETRIMINO_OFFSET, y + TETRIMINO_OFFSET));

		// Determine if the tile is that of the current tetrimino.
		for (Tetrimino tetrimino : Tetrimino.values()) {
			if (tetrimino.getColor().equals(tetriminoColor)) {
				return TileState.EMPTY; // Returns empty as we do not want the algorithm to consider it as a filled tile.
			}
		}


		return TileState.FILLED;
	}

	/**
	 * Used for testing purposes.
	 * 
	 * @return the screenshot made by the Robot object
	 */
	public BufferedImage getScreenshot() {
		return screenshot;
	}
	
	public void saveScreenshot() {
		BufferedImage bi = screenshot;
	    File outputfile = new File("saved.png");
	    
	    try {
			ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {
		}
	}
	
	public boolean gameDetected() {
		return gameInView;
	}



}
