import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Picture {

	private BufferedImage image;

	/**
	 * Instantiates a new picture.
	 *
	 * @param name
	 *            the name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Picture(String name) throws IOException {
		image = ImageIO.read(new File(name));
	}

	/**
	 * Gets the grayscale.
	 *
	 * @return the grayscale
	 */
	public int[][] getGrayscale() {
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;

		int[][] result = new int[height][width];
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += ((pixels[pixel] & 0xff) << 24); // alpha
				argb += (pixels[pixel + 1] & 0xff); // blue
				argb += ((pixels[pixel + 2] & 0xff) << 8); // green
				argb += ((pixels[pixel + 3] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += (pixels[pixel] & 0xff); // blue
				argb += ((pixels[pixel + 1] & 0xff) << 8); // green
				argb += ((pixels[pixel + 2] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}

		return result;
	}

	public int[][] getGrayscaleSimple() {
		final int[][] pixels = new int[image.getHeight()][image.getWidth()];

		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				int clr = image.getRGB(i, j);
				int red = (clr & 0x00ff0000) >> 16;
			int green = (clr & 0x0000ff00) >> 8;
				int blue = clr & 0x000000ff;
				pixels[i][j] = (red + green + blue) / 3;
			}
		}

		return pixels;
	}

}
