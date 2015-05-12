import java.util.ArrayList;

// TODO: Auto-generated Javadoc
public class Mask {
	// starting point of mask
	private int x;
	private int y;
	private double threshold;

	private ArrayList<Integer> points = new ArrayList<>();

	/**
	 * Instantiates a new mask.
	 *
	 * @param col
	 *            the col
	 * @param row
	 *            the row
	 * @param t
	 *            the t
	 * @param pixels
	 *            the pixels
	 */
	public Mask(int col, int row, double t, int pixels[][]) {
		x = row;
		y = col;
		threshold = t;
		dilateMask(pixels);
	}

	/**
	 * Dilate mask.
	 *
	 * @param pixels
	 *            the pixels
	 */
	private void dilateMask(int[][] pixels) {

	}

}
