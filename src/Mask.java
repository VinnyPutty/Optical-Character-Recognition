import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Mask {
	// starting point of mask
	private int x;
	private int y;

	private double threshold;
	private List<Point> points;
	private List<Point> pointsAverage;

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

		Collections.sort( points , new PointComparator() );
		findPointsAverage();
	}

	private void findPointsAverage() {
		pointsAverage = new ArrayList<Point>();
		Iterator<Point> pIterator = points.iterator();
		Point p = pIterator.next();
		int y = p.y;
		int xStart = p.x;
		int xEnd = p.x;
		while( pIterator.hasNext() )
		{
			p = pIterator.next();
			while( p.y == y && p.x - 1 == xEnd )
			{
				xEnd = p.x;
				if( pIterator.hasNext() )
				{
					p = pIterator.next();
				}
			}
			pointsAverage.add( new Point( (xStart+xEnd) / 2 , y ) );
			y = p.y;
			xEnd = p.x;
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

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
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
		/*
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).x + 1 < pixels[0].length && !points.contains(new Point(points.get(i).x + 1, points.get(i).y)) && pixels[points.get(i).y][points.get(i).x + 1] <= threshold) points.add(new Point(points.get(i).x + 1, points.get(i).y));
			if (points.get(i).x - 1 >= 0 && !points.contains(new Point(points.get(i).x - 1, points.get(i).y)) && pixels[points.get(i).y][points.get(i).x - 1] <= threshold) points.add(new Point(points.get(i).x - 1, points.get(i).y));
			if (points.get(i).y + 1 < pixels.length && !points.contains(new Point(points.get(i).x, points.get(i).y + 1)) && pixels[points.get(i).y + 1][points.get(i).x] <= threshold) points.add(new Point(points.get(i).x, points.get(i).y + 1));
			if (points.get(i).y - 1 >= 0 && !points.contains(new Point(points.get(i).x, points.get(i).y - 1)) && pixels[points.get(i).y - 1][points.get(i).x] <= threshold) points.add(new Point(points.get(i).x, points.get(i).y - 1));
		}
		*/
		
		int x = points.size();
		for( int i = 0; i < x; i++ )
		{
			for( int j = 0; j < 3; j++ )
			{
				for( int k = 0; k < 3; k++ )
				{
					Point p = points.get(i);
					Point p1 = new Point( p.x + dX[j] , p.y + dY[k] );
					if( insideArray( p1 , pixels ) && !points.contains(p1) && pixels[p1.y][p1.x] <= threshold )
					{
						points.add( p1 );
					}
				}
			}
		}
		
	}

	private boolean insideArray( Point p , int[][] a )
	{
		return p.x >= 0 && p.x < a[0].length && p.y >= 0 && p.y < a.length;
	}
	
	public List<Point> getPointsAverage() {
		return pointsAverage;
	}

	public void setPointsAverage(List<Point> pointAverage) {
		this.pointsAverage = pointAverage;
	}

}

class PointComparator implements Comparator<Point> {

	@Override
	public int compare(Point p1, Point p2) {
		if( p1.y != p2.y )
		{
			return p1.y - p2.y;
		}
		return p1.x - p2.x;
	}
	
}
