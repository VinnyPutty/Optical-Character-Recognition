import java.awt.Rectangle;

public class Tile extends Rectangle implements Comparable {

	private Mask mask;
	private int row;

	public Tile(Mask m) {
		super(m.getTile());
		mask = m;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public Mask getMask() {
		return mask;
	}

	public void setMask(Mask mask) {
		this.mask = mask;
	}

	@Override
	public int compareTo(Object o) {
		if( o instanceof Tile )
		{
			if( ((Tile) o).height + ((Tile) o).y != this.height + this.y )
			{
				return (int) (((Tile) o).height+((Tile) o).y - (this.height + this.y));
			}
			return (int) (((Tile) o).width + ((Tile) o).x - (this.width + this.x));
		}
		return 0;
	}

}
