import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Mask {
	// starting point of mask
	private int x;
	private int y;
	
	private double threshold;
	private Set<Point> points;

	// direction arrays
	private int[] dX = {-1,0,1};
	private int[] dY = {-1,0,1};
	
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
		// initialize variables
		points = new HashSet<>();
		points.add(new Point(x,y));
		
		// iterate until amount of new points added to mask is insignificant
		int size = 1;
		while( 1.0 * points.size() - size / size > 0.05 )
		{
			size = points.size();
			addPossiblePoints();
			
			// remove points not above threshold
			Iterator<Point> p1 = points.iterator();
			while( p1.hasNext() )
			{
				Point p = p1.next();
				if( pixels[p.y][p.x] < threshold )
				{
					p1.remove();
				}
			}
		}
		
	}

	/**
	 * Find surrounding pixels of the Set of points
	 */
	private void addPossiblePoints() {
		for( Point p : points )
		{
			for( int i : dX )
			{
				for( int j : dY )
				{
					points.add( new Point( p.x + dX[i] , p.y + dY[j] ) );
				}
			}
		}
	}

	/**
	 * Return a rectangle for the tile
	 */
	
	
}
