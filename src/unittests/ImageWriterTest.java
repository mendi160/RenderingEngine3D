package unittests;

import renderer.*;
import org.junit.Test;
import primitives.Color;

/**
 * 
 * @author yehonatan Eliyahu & Mendi Shneorson
 *
 */
public class ImageWriterTest {
	/**
	 * simple image Writing test in this test we rendering colord Grid on a plain
	 * background
	 */
	@Test
	public void imageToWriteTests() {
		int width = 1600, hight = 1000, Nx = 801, Ny = 501;
		ImageWriter imageWriter = new ImageWriter("1st", width, hight, Nx, Ny);
		Color blue = new Color(25, 25, 100);
		Color green = new Color(25, 100, 25);
		for (int i = 0; i < Ny; i++) {
			for (int j = 0; j < Nx; j++) {
				if (i % 50 == 0 || j % 50 == 0)
					imageWriter.writePixel(j, i, blue.getColor());
				else {
					imageWriter.writePixel(j, i, green.getColor());
				}
			}
		}
		imageWriter.writeToImage();
	}
}
