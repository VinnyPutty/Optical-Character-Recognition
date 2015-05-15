import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CharExtract {

	private ArrayList<Tile> tiles;
	private Picture picture;

	/**
	 * Instantiates a new char extract.
	 *
	 * @param img
	 *            the img
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public CharExtract(File img) throws IOException {
		picture = new Picture(img.getAbsolutePath());
		createTiles();
	}

	/**
	 * Creates the tiles.
	 */
	public void createTiles() {
		int[][] pixels = picture.getGrayscale();
		double t = getThreshold(pixels);

		// creating masks associated with tiles, adding to tiles ArrayList
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < pixels[row].length; col++) {
				if (pixels[row][col] > t && !withinTiles(row, col)) {
					tiles.add( new Tile( new Mask(col, row, t, pixels) ) );
				}
			}
		}

	}

	/**
	 * Within tiles.
	 *
	 * @param row
	 *            the row
	 * @param col
	 *            the col
	 * @return true, if successful
	 */
	private boolean withinTiles(int row, int col) {
		for (Tile t : tiles) {
			if (t.contains(col, row)) // x and y
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the threshold.
	 *
	 * @param pixels
	 *            the pixels
	 * @return the threshold
	 */
	private double getThreshold(int[][] pixels) {
		int t = 0;
		int[] bin = new int[256];
		for (int[] pixel : pixels) {
			for (int element : pixel) {
				
			}
		}
		return t;
	}
	
}
