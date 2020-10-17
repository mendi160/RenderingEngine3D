/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

/**
 * @author Mendi Shneorson & Yehonatan Eliyahu unittest for Point3D
 * 
 *
 */
public class Point3DTests {

	/**
	 * Test method for {@link primitives.Point3D#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() {
		Point3D first = new Point3D(3, 1, 3);
		Point3D second = new Point3D(3, 5, 6);

		// =======================Equivelence Partition Value==========
		// test add vector to point
		assertEquals("Wrong Value for Add", first.add(new Vector(second)), new Point3D(6, 6, 9));
	}

	/**
	 * Test method for {@link primitives.Point3D#subtract(primitives.Point3D)}.
	 */
	@Test
	public void testSubtract() {
		Point3D first = new Point3D(3, 1, 3);
		Point3D second = new Point3D(-3, -1, -3);

		// =======================Equivelence Partition Value==========
		// test add vector to point
		assertEquals("Wrong Value for suntract", second.subtract(first), new Vector(-6, -2, -6));
		// =============== Boundary Values Tests ==================
		// test zero vector from subtract points
		try {
			second.subtract(second);
			fail("subtruct  does not throw an exception for Zero Vector");
		} catch (Exception e) {
		}
	}

	/**
	 * Test method for {@link primitives.Point3D#distance(primitives.Point3D)}.
	 */
	@Test
	public void testDistance() {
		Point3D first = new Point3D(3, 1, 3);
		Point3D second = new Point3D(3, 5, 6);

		// =======================Equivelence Partition Value==========
		// test distance between two point
		assertTrue("Worng distance", second.distance(first) == 5);
	}

}
