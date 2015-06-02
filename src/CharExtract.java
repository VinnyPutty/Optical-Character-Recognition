import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

public class CharExtract {

	private ArrayList<Tile> tiles;
	private Picture picture;
	private int[][] pixels;
	private int height;
	private char[] letters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * Instantiates a new char extract.
	 *
	 * @param img
	 *            the img
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public CharExtract(File img, int h) throws IOException {
		picture = new Picture(img.getAbsolutePath());
		height = h;
		createTiles();
		// String s = charBuilder();
		//tileSave();
		System.out.println("Complete. ");
	}

	/**
	 * Creates the tiles.
	 */
	public void createTiles() {
		tiles = new ArrayList<Tile>();

		// pixels = picture.getGrayscaleSimplest();
		pixels = picture.getGrayscaleSimplest();

		double t = getThreshold(pixels);
		//double t = 30;

		// creating masks associated with tiles, adding to tiles ArrayList
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < pixels[row].length; col++) {
				if (pixels[row][col] <= t && !withinTiles(row, col)) 
				{
					//System.out.print( "Tile found " );
					//long startTime = System.currentTimeMillis();
					tiles.add(new Tile(new Mask(col, row, t, pixels)));
					//long endTime = System.currentTimeMillis();
					//System.out.println( "Tile done" + ( endTime - startTime ) );
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
			if ((t.getRectangle().x <= col && col <= t.getRectangle().width + t.getRectangle().x) && (t.getRectangle().y <= row && row <= t.getRectangle().height + t.getRectangle().y)) return true;
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
		for (int i = 0; i < 256; i++) {
			weight[i] = bin[i] * (i + 1);
		}

		// balanced histogram thresholding
		// average intensity
		int aMean = Math.round(partialArraySum(weight, 0, 255) / partialArraySum(bin, 0, 255));
		// find means below and above the average intensity
		double lMean = partialArraySum(weight, 0, aMean) / partialArraySum(bin, 0, aMean);
		double hMean = partialArraySum(weight, aMean, 255) / partialArraySum(bin, aMean, 255);
		int temp = aMean;
		aMean = (int) Math.round((lMean + hMean) / 2);

		// iterate until upper and lower averages are the same
		while (Math.abs(temp - aMean) >= 1) {
			temp = aMean;
			lMean = partialArraySum(weight, 0, aMean) / partialArraySum(bin, 0, aMean);
			hMean = partialArraySum(weight, aMean, 255) / partialArraySum(bin, aMean, 255);
			aMean = (int) Math.round((lMean + hMean) / 2);
		}
		// calculate t from balanced intensity
		t = (aMean - 1.0);
		return t;
	}

	/**
	 * Gets the partial array sum.
	 *
	 * @param a
	 *            the array
	 * @param s
	 *            the start index
	 * @param e
	 *            the end index
	 * @return the partial array sum
	 */
	private int partialArraySum(int[] a, int s, int e) {
		int sum = 0;
		for (int i = s; i <= e; i++) {
			sum += a[i];
		}
		return sum;
	}

	// create strings from tiles
	public String charBuilder() {
		// sort tiles
		Collections.sort( tiles );
		// checkIJ();
		
		// build char
		StringBuilder s = new StringBuilder();
		
		Iterator<Tile> tileIterator = tiles.iterator();
		double v = Double.MAX_VALUE;
		int letter = 0;
		
		// iterate through learned tiles, pick letter with least variance
		while( tileIterator.hasNext() )
		{
			Tile tile = tileIterator.next();
			for( int i = 0 ; i < 26 ; i++ )
			{
				System.out.println(i);
				double x = tile.slopeVarianceLevel( i + ".png" , pixels );
				if( x < v )
				{
					v = x;
					letter = i;
				}
			}
			s.append( letters[letter] );
		}

		return s.toString();
	}
	
	public String charExtract() {
		PrintWriter out = null;
		PrintWriter conf = null;
		String s = null;
		int letter = -1;
		int confidence = Integer.MAX_VALUE;
		try {
			out = new PrintWriter("output.txt");
			for (Tile t : tiles) {
				for (int i = 0; i < 26; i++) {
					if (t.fetchConfidence(i + ".png", pixels) < confidence) {
						confidence = t.fetchConfidence(i + ".png", pixels);
						System.out.print(confidence + " ");
						letter = i;
					}
				}
				out.write(letters[letter]);
				s = s + letters[letter];
				System.out.println(letter + " ");
				confidence = Integer.MAX_VALUE;
				letter = -1;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		out.close();

		return s;
	}

	private void checkIJ()
	{
		Iterator<Tile> tileIterator = tiles.iterator();
		Tile tile = tileIterator.next();
		
		while( tileIterator.hasNext() )
		{
			
		}
	}
	

	public void tileSave() {

		BufferedImage orig = null;

		for (int i = 0; i < tiles.size(); i++) {

			BufferedImage image = new BufferedImage((int) tiles.get(i).getRectangle().getWidth() + 1, (int) tiles.get(i).getRectangle().getHeight() + 1, BufferedImage.TYPE_BYTE_GRAY);

			for (int l = 0; l < image.getHeight(); l++) {
				for (int m = 0; m < image.getWidth(); m++) {
					image.setRGB(m, l, Color.WHITE.getRGB());

				}

			}

			for (Point p : tiles.get(i).getMask().getPoints())
				image.setRGB(p.x - tiles.get(i).getRectangle().getLocation().x, p.y - tiles.get(i).getRectangle().getLocation().y, Color.BLACK.getRGB());

			PrintWriter writer = null;

			try {
				writer = new PrintWriter("hold.out", "UTF-8");

			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				e.printStackTrace();
			}

			writer.close();

			image = Scalr.resize(image, 100);

			try {
				ImageIO.write(image, "png", new File(i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
