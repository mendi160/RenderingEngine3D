/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class PolygonTests {

	/**
	 * Test method for
	 * {@link geometries.Polygon#Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}

		// TC02: Wrong vertices order
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0), new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
			fail("Constructed a polygon with wrong order of vertices");
		} catch (IllegalArgumentException e) {
		}

		// TC03: Not in the same plane
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 2, 2));
			fail("Constructed a polygon with vertices that are not in the same plane");
		} catch (IllegalArgumentException e) {
		}

		// TC04: Concave quadrangular
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
			fail("Constructed a concave polygon");
		} catch (IllegalArgumentException e) {
		}

		// =============== Boundary Values Tests ==================

		// TC10: Vertix on a side of a quadrangular
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
			fail("Constructed a polygon with vertix on a side");
		} catch (IllegalArgumentException e) {
		}

		// TC11: Last point = first point
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));
			fail("Constructed a polygon with vertice on a side");
		} catch (IllegalArgumentException e) {
		}

		// TC12: Collocated points
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 1, 0));
			fail("Constructed a polygon with vertice on a side");
		} catch (IllegalArgumentException e) {
		}

	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
				new Point3D(-1, 1, 1));
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
	}

	/**
	 * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		// this points reprsent pentagon (*convex* polygon)
		Point3D a = new Point3D(2, 0, 0);
		Point3D b = new Point3D(1, 1, 0);
		Point3D c = new Point3D(3, 2, 0);
		Point3D d = new Point3D(5, 1, 0);
		Point3D e = new Point3D(4, 0, 0);
		Polygon polygon = new Polygon(a, b, c, d, e);
		// ==================================Equivelence Partition Test================
		// TC01: The Ray intersect the polygon
		Ray ray = new Ray(new Point3D(2, 1, 1), new Vector(0, 0, -1));
		assertEquals("Wrong intersection", List.of(new GeoPoint(polygon, new Point3D(2, 1, 0))),
				polygon.findIntersections(ray));
		// TC02: The Ray intersect the plane against the edge of the polygon (outside)
		ray = new Ray(new Point3D(1, 0.5, 1), new Vector(0, 0, -1));
		assertNull("should be null", polygon.findIntersections(ray));
		// TC03: The Ray intersect the plane against the Vertex of the polygon
		// (outside)
		ray = new Ray(new Point3D(3, 2.5, 1), new Vector(0, 0, -1));
		assertNull("should be null", polygon.findIntersections(ray));

		// ======================Boundery Value Test==============
		// TC11: The Ray intersect at the edge of the polygon
		ray = new Ray(new Point3D(1.5, 1.25, 1), new Vector(0, 0, -1));
		assertNull("should be null", polygon.findIntersections(ray));
		// TC12: The Ray intersect at the vertex of the polygon
		ray = new Ray(new Point3D(1, 1, 1), new Vector(0, 0, -1));
		assertNull("should be null", polygon.findIntersections(ray));
		// TC13: The Ray intersect the plane On edge's continuation (outside polygon's)
		ray = new Ray(new Point3D(4, 2.5, 1), new Vector(0, 0, -1));
		assertNull("should be null", polygon.findIntersections(ray));
	}

}
