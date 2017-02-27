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
 * This class is used to detect the TetrisFriends game within the screen.
 * 
 * It also provides a way to retrieve the states of each tile in the board as well
 * as the tetrimino pieces.
 * 
 * @author Andy Tang
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
	private BufferedImage screenshot, gameBoard, playField, secondTetrimino, thirdTetrimino;
	private Point startingPixel, playfieldStartingPixel;
	private Rectangle screenResolution, boardResolution, playfieldResolution, secondTetriminoResolution, thirdTetriminoResolution;

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
			gameBoard = robot.createScreenCapture(boardResolution);

			determinePlayFieldStartingPixel();

			if (gameInView) {
				determinePlayFieldSpecifications();

				// Takes a screenshot of the play field only
				playField = robot.createScreenCapture(playfieldResolution);

				determineTetriminoListResolution();
				
				// Takes a screenshot of the box containing the second and third tetriminos respectively
				secondTetrimino = robot.createScreenCapture(secondTetriminoResolution);
				thirdTetrimino = robot.createScreenCapture(thirdTetriminoResolution);

			}	
		}

		else {
			gameInView = false;
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
		for (int i = 0; i < gameBoard.getHeight(); i++) {
			for (int j = 0; j < gameBoard.getWidth(); j++) {

				// Get color of current pixel
				Color pixel = new Color(gameBoard.getRGB(j, i));

				// If it equals the first empty tile
				if (pixel.equals(Colors.EMPTY_TILE_1)) {

					boolean tempFound = true;


					// Iterate through next horizontal 15 pixels
					// and checks if they are the same color
					for (int m = j; m < j + 15; m++) {

						// Prevents out of coordinates exception
						if (m < gameBoard.getWidth()) {

							Color tempPixel = new Color(gameBoard.getRGB(m, i));

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
						if (n < gameBoard.getHeight()) {
							Color tempPixel = new Color(gameBoard.getRGB(j, n));

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
		Color pixel = new Color(gameBoard.getRGB(x, y));

		// Determine size by iterating until color changes
		while (pixel.equals(Colors.EMPTY_TILE_1) && x < gameBoard.getWidth() - 1) {
			tileSize++;
			x++;
			pixel = new Color(gameBoard.getRGB(x, y));
		}

		// Determine gap by iterating until color changes
		while (!pixel.equals(Colors.EMPTY_TILE_2) && x < gameBoard.getWidth() - 1) {
			tileGap++;
			x++;
			pixel = new Color(gameBoard.getRGB(x, y));
		}

		// Calculate width and height of play field
		int playfieldWidth = (tileSize * width) + (tileGap * (width - 1));
		int playfieldHeight = (tileSize * height) + (tileGap * (height - 1));

		// Create rectangle containing the play field
		playfieldResolution = new Rectangle(startingPixel.x + playfieldStartingPixel.x,
				startingPixel.y + playfieldStartingPixel.y, playfieldWidth, playfieldHeight);

		// Ensures that getState is not using an invalid x value.
		// This happens when the game has been found but the playfield is in an invalid state (e.g. not in progress).
		if (x == gameBoard.getWidth() - 1) {
			tileSize = 0;
			tileGap = 0;
		}

	}

	/**
	 * This method sets up the resolution for the second and third tetriminos. It is hard coded because I am lazy
	 */
	private void determineTetriminoListResolution() {

		secondTetriminoResolution = new Rectangle(startingPixel.x + 305, startingPixel.y + 140, 39, 51); 
		thirdTetriminoResolution = new Rectangle(startingPixel.x + 308, startingPixel.y + 210, 33, 45);

	}

	/**
	 * This method returns the tetrimino at the specified position
	 * Position 0 is the tetrimino that is currently in play.
	 */
	public Tetrimino getTetrimino(int position) {
		BufferedImage tetriminoImage = null;

		if (position == 0) {
			return getCurrentTetrimino();
		}

		else if (position == 1) {
			tetriminoImage = secondTetrimino;
		}

		else if (position == 2) {
			tetriminoImage = thirdTetrimino;
		}

		else {
			return null;
		}

		// This for loop determines the tetrimino at the second or third position
		
		// Iterate through tetriminoList image
		for (int i = 0; i < tetriminoImage.getHeight(); i++) {
			for (int j = 0; j < tetriminoImage.getWidth(); j++) {

				Color color = new Color(tetriminoImage.getRGB(j, i));

				// Only check if pixel color isn't black
				if (!color.equals(Color.black)) {

					// Iterate through all Tetriminos and determine which one it is (if it is checking the right pixel)
					for (Tetrimino tetrimino : Tetrimino.values()) {
						if (tetrimino.getColor().equals(color)) {
							return tetrimino;
						}
					}
				}
			}
		}


		return null;


	}

	/**
	 * Method used to determine what the current tetrimino is.
	 * 
	 * @return the current tetrimino 
	 */
	private Tetrimino getCurrentTetrimino() {

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				// Determine color of current tile
				Color currentTileColor = new Color(playField.getRGB((j * tileSize) + (j * tileGap) + TETRIMINO_OFFSET,
						(i * tileSize) + (i * tileGap) +TETRIMINO_OFFSET));

				// Iterate through all Tetriminos and determine which one it is
				for (Tetrimino tetrimino : Tetrimino.values()) {
					if (tetrimino.getColor().equals(currentTileColor)) {
						return tetrimino;
					}
				}		
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
		Color tileColor = new Color(playField.getRGB(x, y));

		// Checks if it is empty by comparing with the 'empty tile' colors
		if (tileColor.equals(Colors.EMPTY_TILE_1) || tileColor.equals(Colors.EMPTY_TILE_2) 
				|| tileColor.equals(Colors.TETRIMINO_HIGHLIGHT_1) || tileColor.equals(Colors.TETRIMINO_HIGHLIGHT_2)) {
			return TileState.EMPTY;
		}
		
		// Checks if it is a sent tile
		if (tileColor.equals(Colors.SENT_TILE)) {
			return TileState.FILLED;
		}

		// Get color of pixel used to determine the tetrimino type
		Color tetriminoColor = new Color(playField.getRGB(x + TETRIMINO_OFFSET, y + TETRIMINO_OFFSET));

		// Determine if the tile is that of the current tetrimino.
		for (Tetrimino tetrimino : Tetrimino.values()) {
			if (tetrimino.getColor().equals(tetriminoColor)) {
				return TileState.EMPTY; // Returns empty as we do not want the algorithm to consider it as a filled tile.
			}

			else if (tetrimino.getFilledColor().equals(tetriminoColor)) {
				return TileState.FILLED;
			}

		}


		return TileState.INVALID;
	}

	/**
	 * Used for testing purposes.
	 * 
	 * @return the screenshot made by the Robot object
	 */
	public BufferedImage getScreenshot() {
		return screenshot;
	}

	/**
	 * Used for testing purposes.
	 * Saves a screenshot of each respective region found
	 */
	public void saveScreenshot() {
		BufferedImage screenshotBI = screenshot;
		BufferedImage gameBoardBI = gameBoard;
		BufferedImage playFieldBI = playField;
		BufferedImage secondTetriminoBI = secondTetrimino;
		BufferedImage thirdTetriminoBI = thirdTetrimino;

		File screenshotOut = new File("screenshot.png");
		File gameBoardOut = new File("gameboard.png");
		File playFieldOut = new File("playfield.png");
		File secondTetriminoOut = new File("secondtetrimino.png");
		File thirdTetriminoOut = new File("thirdtetrimino.png");

		try {
			ImageIO.write(screenshotBI, "png", screenshotOut);
			ImageIO.write(gameBoardBI, "png", gameBoardOut);
			ImageIO.write(playFieldBI, "png", playFieldOut);
			ImageIO.write(secondTetriminoBI, "png", secondTetriminoOut);
			ImageIO.write(thirdTetriminoBI, "png", thirdTetriminoOut);
		} catch (IOException e) {
		}
	}

	public boolean gameDetected() {
		return gameInView;
	}



}
