import java.awt.Point;
import java.awt.Rectangle;

public class Tile {

	private Mask mask;
	private Rectangle rectangle;

	public Tile(Mask m) {
		mask = m;

		int x = m.getPoints().get(0).x, x1 = m.getPoints().get(0).x;
		int y = m.getPoints().get(0).y, y1 = m.getPoints().get(0).y;
		for (Point p : m.getPoints()) {
			if (p.x > x1) x1 = p.x;
			if (p.y > y1) y1 = p.y;
			if (p.x < x) x = p.x;
			if (p.y < y) y = p.y;
		}

		rectangle = new Rectangle(x, y, x1 - x, y1 - y);

	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public Rectangle getRectangle(Mask m) {
		return new Rectangle();
	}

	public Mask getMask() {
		return mask;
	}

	public void setMask(Mask mask) {
		this.mask = mask;
	}

}
