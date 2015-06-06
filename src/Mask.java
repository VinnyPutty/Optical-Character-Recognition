import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mask {
	// starting point of mask
	private int x;
	private int y;

	private double threshold;
	private List<Point> points;

	// direction arrays
	private int[] dX = { -1, 0, 1 };
	private int[] dY = { -1, 0, 1 };

	/**
	 * Instantiates a new mask.
	 *
	 * @param col
	 *            the col
	 * @param row
	 *            the row
	 * @param t
	 *            the threshold
	 * @param pixels
	 *            the pixels
	 */
	public Mask(int col, int row, double t, int pixels[][]) {
		x = col;
		y = row;
		threshold = t;
		dilateMask(pixels);
		for (int i = 0; i < points.size(); i++) {
			// System.out.println(points.get(i));
		}
	}

	/**
	 * Dilate mask.
	 *
	 * @param pixels
	 *            the pixels
	 */
	private void dilateMask(int[][] pixels) {
		// initialize variables
		points = Collections.synchronizedList(new ArrayList<Point>());
		points.add(new Point(x, y));
		// System.out.println(points.size());
		addAdjacentPoints(pixels);
		// System.out.println(points.size());

		// iterate until amount of new points added to mask is insignificant
		int size = 0;
		while (points.size() > size) {
			size = points.size();
			// addPossiblePoints();
			addAdjacentPoints(pixels);
			// System.out.println(points.size());

			// for (Point p : points)
			// System.out.print(p + " ");
			//
			// System.out.println();

			// remove points not above threshold

			// Iterator<Point> pIterator = points.iterator();
			// while (pIterator.hasNext()) {
			// Point p = pIterator.next();
			// if (pixels[p.y][p.x] > threshold) {
			// pIterator.remove();
			// }
			// }

			// for (Point p : points) {
			// if (pixels[p.y][p.x] > threshold)
			// points.remove(points.indexOf(p));
			//
			// }
		}

		// System.out.println();
		// System.out.println("Mask complete.");

	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	/**
	 * Find surrounding pixels of the Set of points
	 */
	private void addPossiblePoints() {
		for (Point p : points) {
			for (int i : dX) {
				for (int j : dY) {
					points.add(new Point(p.x + i, p.y + j));
				}
			}
		}
	}

	private void addAdjacentPoints(int[][] pixels) {
		// for (Point p : points) {
		// if (p.x + 1 < pixels[0].length && !points.contains(new Point(p.x + 1,
		// p.y)) && pixels[p.y][p.x + 1] <= threshold) points.add(new Point(p.x
		// + 1, p.y));
		// if (p.x - 1 >= 0 && !points.contains(new Point(p.x - 1, p.y)) &&
		// pixels[p.y][p.x - 1] <= threshold) points.add(new Point(p.x - 1,
		// p.y));
		// if (p.y + 1 < pixels.length && !points.contains(new Point(p.x, p.y +
		// 1)) && pixels[p.y + 1][p.x] <= threshold) points.add(new Point(p.x,
		// p.y + 1));
		// if (p.y - 1 >= 0 && !points.contains(new Point(p.x, p.y - 1)) &&
		// pixels[p.y - 1][p.x] <= threshold) points.add(new Point(p.x, p.y -
		// 1));
		// }

		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).x + 1 < pixels[0].length && !points.contains(new Point(points.get(i).x + 1, points.get(i).y)) && pixels[points.get(i).y][points.get(i).x + 1] <= threshold) points.add(new Point(points.get(i).x + 1, points.get(i).y));
			if (points.get(i).x - 1 >= 0 && !points.contains(new Point(points.get(i).x - 1, points.get(i).y)) && pixels[points.get(i).y][points.get(i).x - 1] <= threshold) points.add(new Point(points.get(i).x - 1, points.get(i).y));
			if (points.get(i).y + 1 < pixels.length && !points.contains(new Point(points.get(i).x, points.get(i).y + 1)) && pixels[points.get(i).y + 1][points.get(i).x] <= threshold) points.add(new Point(points.get(i).x, points.get(i).y + 1));
			if (points.get(i).y - 1 >= 0 && !points.contains(new Point(points.get(i).x, points.get(i).y - 1)) && pixels[points.get(i).y - 1][points.get(i).x] <= threshold) points.add(new Point(points.get(i).x, points.get(i).y - 1));
		}
	}

	/*
	 * Return a rectangle for the tile public Rectangle getTile() {
	 * Iterator<Point> pIterator = points.iterator(); Point p =
	 * pIterator.next(); int x = p.x, x1 = p.x; int y = p.y, y1 = p.y; while
	 * (pIterator.hasNext()) { p = pIterator.next(); if (p.x > x1) { x1 = p.x; }
	 * if (p.y > y1) { y1 = p.y; } if (p.x < x) { x = p.x; } if (p.y < y) { y =
	 * p.y; } } return new Rectangle(x, y, x1-x, y1-y); }
	 */

	/*
	 * public Rectangle getTile() { // Iterator<Point> pIterator =
	 * points.iterator(); // Point p = pIterator.next(); // int x = p.x, x1 =
	 * p.x; // int y = p.y, y1 = p.y; // while (pIterator.hasNext()) { // p =
	 * pIterator.next(); // if (p.x > x1) { // x1 = p.x; // } // if (p.y > y1) {
	 * // y1 = p.y; // } // if (p.x < x) { // x = p.x; // } // if (p.y < y) { //
	 * y = p.y; // } // } }
	 */

}
