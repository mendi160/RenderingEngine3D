/**
 * 
 */
package unittests;

import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

/**
 * @author Mendi Shneorson and Yehonatan Eliyahu
 *
 */
public class PlaneTest {

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormalPoint3D() {
		Plane plane = new Plane(new Point3D(1, 0, 0), new Point3D(2, 2, 0), new Point3D(3, 1, 0));

		// ====================Equivelence Value Test==============
		// simple test for normal
		assertEquals("Worng normal", new Vector(0, 0, -1), plane.getNormal(new Point3D(1.5, 1.5, 0)));
	}

	@Test
	public void testFindIntersections() {
		Plane plane = new Plane(new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));
		Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0));

		// ====================Equivelence Partition Test=========
		// TC01 Vector intersect the plane (not orthogonal)
		assertEquals("Error: no intersection", List.of(new GeoPoint(plane, new Point3D(1, 0, 0))),
				plane.findIntersections(ray));
		ray = new Ray(new Point3D(0, 0, 0), new Vector(-1, 0, 0));
		// TC02 The ray have no intersection with the plane (not parallel)
		assertNull("Error: Not null", plane.findIntersections(ray));

		// =============== Boundary Values Tests ==================
		// TC11 The Ray parallel to the plane (not composed)
		ray = new Ray(new Point3D(0, 0, 0), new Vector(1, -1, 0));
		assertNull("Error: Not null", plane.findIntersections(ray));
		// TC12 The Ray parallel to the plane and composed
		ray = new Ray(new Point3D(0, 0, 0), new Vector(1, -1, 0));
		assertNull("Error: Not null", plane.findIntersections(ray));
		// TC13 The ray start at the plane and orthogonal
		ray = new Ray(new Point3D(0, 0, 1), new Vector(1, 1, 1));
		assertNull("Error: Not null", plane.findIntersections(ray));
		// TC14 The ray start before the plane and orthogonal
		ray = new Ray(new Point3D(-2, -2, -1), new Vector(1, 1, 1));
		assertEquals("Error: no intersection", List.of(new GeoPoint(plane, new Point3D(0, 0, 1))),
				plane.findIntersections(ray));
		// TC15 The ray start after the plane and orthogonal
		ray = new Ray(new Point3D(3, 2, 2), new Vector(1, 1, 1));
		assertNull("Error: Not null", plane.findIntersections(ray));
		// TC16 The ray is neither orthogonal nor parallel to and begins at the plane
		ray = new Ray(new Point3D(0.5, 0.5, 0), new Vector(1, 1, -2));
		assertNull("Error: Not null", plane.findIntersections(ray));
		// TC17 The ray is neither orthogonal nor parallel to the plane and begins in
		// the same point which appears as reference point in the plane
		ray = new Ray(new Point3D(1, 0, 0), new Vector(1, 1, -2));
		assertNull("Error: Not null", plane.findIntersections(ray));
	}

}
