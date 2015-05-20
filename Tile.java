import java.awt.Rectangle;

public class Tile extends Rectangle {

	private Mask mask;

	public Tile(Mask m) {
		super(m.getTile());
		mask = m;
	}

	public Mask getMask() {
		return mask;
	}

	public void setMask(Mask mask) {
		this.mask = mask;
	}

}
