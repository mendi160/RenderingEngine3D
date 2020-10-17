package unittests;

import files.*;
import org.junit.Test;
import renderer.*;

public class XML_Test {
	/**
	 * Simple Test for reading scene from xml file and rendering to image
	 */
	@Test
	public void basicRenderTwoColorTest() {
		Xml xml = new Xml("myxml.xml");
		// Getting render for the scene in the file
		Render render = xml.getRender();
		if (render != null) {
			render.renderImage();
			render.printGrid(50, java.awt.Color.YELLOW);
			render.writeToImage();
		} else {
			System.out.println("Something goes worng at opening the file");
		}
	}
}
