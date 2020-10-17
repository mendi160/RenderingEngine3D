/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.Intersectable.GeoPoint;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Mendi Shneorson & Yehonatan Eliyahu
 *
 */
public class TriangleTests {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Triangle trnglTriangle = new Triangle(new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));
		// ==================================Equivelence Value Test================
		assertEquals("Wronng Normal ", new Vector(1, 1, 1).normalize(), trnglTriangle.getNormal(new Point3D(0, 0, 0)));
	}

	/**
	 * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Point3D a = new Point3D(1, 0, 0);
		Point3D b = new Point3D(3, 2, 0);
		Point3D c = new Point3D(5, 0, 0);
		Triangle triangle = new Triangle(a, b, c);
		// ==================================Equivelence Partition Test================
		// TC01: The Ray intersect the triangle
		Ray ray = new Ray(new Point3D(2, 0.5, 1), new Vector(0, 0, -1));
		assertEquals("Wrong intersection", List.of(new GeoPoint(triangle, new Point3D(2, 0.5, 0))),
				triangle.findIntersections(ray));
		// TC02: The Ray intersect the plane against the edge of the Triangle (outside)
		ray = new Ray(new Point3D(1, 1, 1), new Vector(0, 0, -1));
		assertNull("should be null", triangle.findIntersections(ray));
		// TC03: The Ray intersect the plane against the Vertex of the Triangle
		// (outside)
		ray = new Ray(new Point3D(0, -0.5, 1), new Vector(0, 0, -1));
		assertNull("should be null", triangle.findIntersections(ray));

		// ======================Boundery Value Test==============
		// TC11: The Ray intersect at the edge of the Triangle
		ray = new Ray(new Point3D(2, 1, 1), new Vector(0, 0, -1));
		assertNull("should be null", triangle.findIntersections(ray));
		// TC12: The Ray intersect at the vertex of the Triangle
		ray = new Ray(new Point3D(1, 0, 1), new Vector(0, 0, -1));
		assertNull("should be null", triangle.findIntersections(ray));
		// TC13: The Ray intersect the plane On edge's continuation (outside Triangle's)
		ray = new Ray(new Point3D(4, 3, 1), new Vector(0, 0, -1));
		assertNull("should be null", triangle.findIntersections(ray));
	}

}
