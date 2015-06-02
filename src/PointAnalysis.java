import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;


public class PointAnalysis {

	public static double slopeVariance( ArrayList<Point> tilePoints, ArrayList<Point> letterPoints, Point p1 , Point p2 )
	{
		Iterator<Point> tilePointsIterator = tilePoints.iterator();
		Iterator<Point> letterPointsIterator = letterPoints.iterator();
		
		double v = 0;
		double s1 = 0;
		double s2 = 0;
		
		// compare from a point
		while( tilePointsIterator.hasNext() )
		{
			Point p = tilePointsIterator.next();
			s1 += findSlope(p1, p);
		}
		while( letterPointsIterator.hasNext() )
		{
			Point p = letterPointsIterator.next();
			s2 += findSlope(p2, p);
		}
		v = s2 - s1;
		return v;
	}
	
	public static double rowClusterVariance( ArrayList<Point> tilePoints, ArrayList<Point> letterPoints , int h1 , int h2 )
	{
		// initialize
		Iterator<Point> tilePointsIterator = tilePoints.iterator();
		Iterator<Point> letterPointsIterator = letterPoints.iterator();
		Point p1 = tilePointsIterator.next();
		Point p2 = letterPointsIterator.next();
		
		int y1 = p1.y;
		int x1 = p1.x;
		int y2 = p2.y;
		int x2 = p2.x;
		
		// normalize first row
		while( tilePointsIterator.hasNext() && p1.y == y1 )
		{
			
		}
		
		for( int i = 1; i < h1; i++ )
		{
			
		}
		
		
		return x2;
	}
	
	/**
	 * Find slope between two points
	 * @param Point p1
	 * @param Point p2
	 * @return slope
	 */
	private static double findSlope( Point p1 , Point p2 )
	{
		if( p1.x < p2.x )
		{
			return (p2.y - p1.y) * 1.0/(p2.x - p1.x); 
		}
		return (p1.y - p2.y) * 1.0/(p1.x - p2.x); 
	}
}
