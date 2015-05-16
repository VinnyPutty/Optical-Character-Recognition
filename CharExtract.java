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
		// initialize
		double t = 0;

		// create histogram and weights of intensities
		int[] bin = new int[256];
		for (int[] pixel : pixels) {
			for (int element : pixel) {
				bin[element]++;
			}
		}
		int[] weight = new int[256];
		for( int i = 0; i < 256; i++ )
		{
			weight[i] = bin[i] * (i + 1);
		}

		// balanced histogram thresholding
		// average intensity
		int aMean = Math.round( partialArraySum(weight, 0, 255)/
				partialArraySum(bin, 0, 255) );
		// find means below and above the average intensity
		double lMean = partialArraySum(weight, 0, aMean)/
				partialArraySum(bin, 0, 255);
		double hMean = partialArraySum(weight, aMean, 0)/
				partialArraySum(bin, aMean, 0);
		int temp = aMean;
		aMean = (int) Math.round( ( lMean + hMean ) / 2 );

		// iterate until upper and lower averages are the same
		while( Math.abs( temp - aMean ) >= 1 )
		{
			temp = aMean;
			lMean = partialArraySum(weight, 0, aMean)/
					partialArraySum(bin, 0, 255);
			hMean = partialArraySum(weight, aMean, 0)/ 
					partialArraySum(bin, aMean, 0);
			aMean = (int) Math.round( ( lMean + hMean ) / 2 );
		}
		// calculate t from balanced intensity
		t = ( aMean - 1.0 ) / ( 256 );
		return t;
	}

	/**
	 * Gets the partial array sum.
	 *
	 * @param a
	 *            the array
	 * @param s
	 * 			  the start index
	 * @param e
	 * 			  the end index
	 * @return the partial array sum
	 */
	private int partialArraySum( int[] a , int s , int e )
	{
		int sum = 0;
		for( int i = s; i <= e; i++ )
		{
			sum += a[i];
		}
		return sum;
	}

	// create strings from tiles
	public String charBuilder()
	{
		StringBuilder s = new StringBuilder();

		return s.toString();
	}

	private char charExtract( Tile t )
	{


	}

}
