import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.imgscalr.Scalr;

public class Tile implements Comparable<Tile> {

	private Mask mask;
	private Rectangle rectangle;
	private int row;

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

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
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

	public int fetchConfidence(String file, int[][] pixels) {
		BufferedImage image = new BufferedImage(rectangle.width + 1, rectangle.height + 1, BufferedImage.TYPE_BYTE_GRAY);

		for (int l = 0; l < image.getHeight(); l++) {
			for (int m = 0; m < image.getWidth(); m++) {
				image.setRGB(m, l, Color.WHITE.getRGB());

			}

		}

		for (Point p : mask.getPoints())
			image.setRGB(p.x - rectangle.x, p.y - rectangle.y, Color.BLACK.getRGB());

		Picture character = null;
		Picture thisTile = null;
		try {
			character = new Picture(file);
			if (character.getImage().getHeight() >= character.getImage().getWidth()) image = Scalr.resize(image, character.getImage().getHeight(), Scalr.OP_ANTIALIAS);
			else image = Scalr.resize(image, character.getImage().getWidth(), character.getImage().getHeight(), Scalr.OP_ANTIALIAS);
			thisTile = new Picture(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int confidence = 0;
		// int[][] charPixels = character.getGrayscaleSimplest();
		// int[][] tilePixels = thisTile.getGrayscaleSimplest();

		int[][] charPixels = character.getBlackAndWhite(CharExtract.t);
		int[][] tilePixels = thisTile.getBlackAndWhite(CharExtract.t);

		System.out.print("||" + charPixels[0].length + " " + tilePixels[0].length + "|| ");

		if (charPixels[0].length == tilePixels[0].length && charPixels.length == tilePixels.length) {
			for (int x = 0; x < charPixels[0].length; x++) {
				for (int y = 0; y < charPixels.length; y++) {
					confidence += Math.abs(tilePixels[y][x] - charPixels[y][x]);
				}
			}
			return confidence;
		}

		return Integer.MAX_VALUE - 1;
	}

	public int fetchLineConfidence(String file, int[][] pixels) {
		BufferedImage image = new BufferedImage(rectangle.width + 1, rectangle.height + 1, BufferedImage.TYPE_BYTE_GRAY);

		for (int l = 0; l < image.getHeight(); l++) {
			for (int m = 0; m < image.getWidth(); m++) {
				image.setRGB(m, l, Color.WHITE.getRGB());

			}

		}

		for (Point p : mask.getPoints())
			image.setRGB(p.x - rectangle.x, p.y - rectangle.y, Color.BLACK.getRGB());

		Picture character = null;
		Picture thisTile = null;
		try {
			character = new Picture(file);
			if (character.getImage().getHeight() >= character.getImage().getWidth()) image = Scalr.resize(image, Scalr.Mode.FIT_TO_HEIGHT, character.getImage().getHeight(), Scalr.OP_ANTIALIAS);
			else image = Scalr.resize(image, Scalr.Mode.FIT_TO_HEIGHT, character.getImage().getWidth(), character.getImage().getHeight(), Scalr.OP_ANTIALIAS);
			thisTile = new Picture(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int confidence = 0;
		// int[][] charPixels = character.getGrayscaleSimplest();
		// int[][] tilePixels = thisTile.getGrayscaleSimplest();

		int[][] charPixels = character.getBlackAndWhite(CharExtract.t);
		int[][] tilePixels = thisTile.getBlackAndWhite(CharExtract.t);

		int[] charPixelLines = new int[charPixels.length];
		int[] tilePixelLines = new int[tilePixels.length];

		System.out.print("||" + charPixelLines.length + " " + tilePixelLines.length + "|| ");

		for (int row = 0; row < charPixels.length; row++) {
			for (int element = 0; element < charPixels[row].length; element++)
				charPixelLines[row] += charPixels[row][element];
		}

		for (int row = 0; row < tilePixels.length; row++) {
			for (int element = 0; element < tilePixels[row].length; element++)
				tilePixelLines[row] += tilePixels[row][element];
		}

		for (int element = 0; element < charPixelLines.length; element++)
			confidence += Math.abs(tilePixelLines[element] - charPixelLines[element]);
		return confidence;

	}

	@Override
	public int compareTo(Tile o) {
		if (rectangle.y > o.getRectangle().y) return 1;
		if (o.getRectangle().y == rectangle.y) {
			if (rectangle.width - o.getRectangle().width > rectangle.width / 2 && (rectangle.x - o.getRectangle().x > 4 * Double.parseDouble(String.valueOf(o.getRectangle().width)))) return 2;
			if (rectangle.width - o.getRectangle().width <= rectangle.width / 2 && (rectangle.x - o.getRectangle().x > 1.5 * Double.parseDouble(String.valueOf(o.getRectangle().width)))) return 3;
		}
		return 0;

		// if (o.getRectangle().height + o.getRectangle().y != rectangle.height
		// + rectangle.y) return o.getRectangle().height + o.getRectangle().y -
		// (rectangle.height + rectangle.y);
		// return o.getRectangle().width + o.getRectangle().x - (rectangle.width
		// + rectangle.x);
	}
}
