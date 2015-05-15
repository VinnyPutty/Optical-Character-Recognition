import java.awt.Rectangle;


public class Tile extends Rectangle {

	private Mask mask;
	
	public Tile( Mask m )
	{
		super( m.getTile() );
		mask = m;
	}

}
