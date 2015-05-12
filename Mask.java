
import java.util.*;

public class Mask {
	// starting point of mask
	private int x;
	private int y;
	private double threshold;
	
	private ArrayList<Integer> points = new ArrayList<>();
	
	/**
	 * @param ( x coordinate of pixel , y coordinate of pixel , threshold
	 */
	public Mask( int col , int row , double t , int pixels[][] )
	{
		x = row;
		y = col;
		threshold = t;
		dilateMask( pixels );
	}

	private void dilateMask( int [][] pixels ) {
		
		
	}
	
}
