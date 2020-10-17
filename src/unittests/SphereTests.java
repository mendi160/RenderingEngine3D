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
 * @author Mendi Shneorson Yehonatan Eliyahu
 *
 */
public class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Sphere spe = new Sphere(new Point3D(1, 0, 0), 2);

		// ====================Equivelence Partiotion Value=============
		// simple test gor get normal
		// TC01 check simple normal on the surface of sphere
		assertEquals("worng normal", new Vector(-1, 0, 0), spe.getNormal(new Point3D(-1, 0, 0)));
	}

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray's line is outside the sphere (0 points)
		Ray ray = new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0));
		assertEquals("Ray's line out of sphere", null, sphere.findIntersections(ray));

		// TC02: Ray starts before and crosses the sphere (2 points)
		GeoPoint p1 =new GeoPoint(sphere, new Point3D(0.0651530771650466, 0.355051025721682, 0));
		GeoPoint p2 = new GeoPoint(sphere,new Point3D(1.53484692283495, 0.844948974278318, 0));
		ray = new Ray(new Point3D(-1, 0, 0), new Vector(3, 1, 0));
		List<GeoPoint> result = sphere.findIntersections(ray);
		assertEquals("Wrong number of points", 2, result.size());
		if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Ray crosses sphere", List.of(p1, p2), result);

		// TC03: Ray starts inside the sphere (1 point)
		ray = new Ray(new Point3D(1.5, 0.5, 0.2), new Vector(-1.5, -0.5, -0.2));
		result = sphere.findIntersections(ray);
		assertEquals("Worng intersection point", List.of(new GeoPoint(sphere,new Point3D(0, 0, 0))), result);

		// TC04: Ray starts after the sphere (0 points)
		ray = new Ray(new Point3D(0.5, 0.5, 1.5), new Vector(0, 0, 1));
		result = sphere.findIntersections(ray);
		assertNull("Error: result should be null", result);

		// =============== Boundary Values Tests ==================
		// **** Group: Ray's line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		ray = new Ray(new Point3D(1.5, Math.sqrt(3 / 4), 0), new Vector(-0.5, -Math.sqrt(3 / 4), -1));
		result = sphere.findIntersections(ray);
		assertEquals("Worng intersection point", List.of(new GeoPoint(sphere, new Point3D(1, 0, -1))), result);
		// TC12: Ray starts at sphere and goes outside (0 points)
		ray = new Ray(new Point3D(1.5, 0, Math.sqrt(3d / 4)), new Vector(0, 0, 1));
		result = sphere.findIntersections(ray);
		assertNull("Error: result should be null", result);
		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the sphere (2 points)
		GeoPoint zeroGeoPoint = new GeoPoint(sphere, Point3D.ZERO);
		p2 = new GeoPoint(sphere,new Point3D(2, 0, 0));
		ray = new Ray(new Point3D(3, 0, 0), new Vector(-1, 0, 0));
		result = sphere.findIntersections(ray);
		assertEquals("Wrong points", 2, result.size());
		if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Ray crosses sphere", List.of(zeroGeoPoint, p2), result);
		// TC14: Ray starts at sphere and goes inside (1 points)
		ray = new Ray(new Point3D(2, 0, 0), new Vector(-1, 0, 0));
		result = sphere.findIntersections(ray);
		assertEquals("Wrong point", List.of(zeroGeoPoint), result);
		// TC15: Ray starts inside (1 points)
		ray = new Ray(new Point3D(1.2345, 0, 0), new Vector(-1, 0, 0));
		result = sphere.findIntersections(ray);
		assertEquals("Wrong point", List.of(zeroGeoPoint), result);
		// TC16: Ray starts at the center (1 points)
		ray = new Ray(new Point3D(1, 0, 0), new Vector(-1, 0, 0));
		result = sphere.findIntersections(ray);
		assertEquals("Wrong point", List.of(zeroGeoPoint), result);
		// TC17: Ray starts at sphere and goes outside (0 points)
		ray = new Ray(Point3D.ZERO, new Vector(-1, 0, 0));
		result = sphere.findIntersections(ray);
		assertNull("Error: result should be null", result);
		// TC18: Ray starts after sphere (0 points)
		ray = new Ray(new Point3D(-1, 0, 0), new Vector(-1, 0, 0));
		result = sphere.findIntersections(ray);
		assertNull("Error: result should be null", result);
		// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
		// TC19: Ray starts before the tangent point
		ray = new Ray(new Point3D(0, 0, -1), new Vector(0, 0, 1));
		result = sphere.findIntersections(ray);
		assertNull("Error: result should be null", result);
		// TC20: Ray starts at the tangent point
		ray = new Ray(Point3D.ZERO, new Vector(0, 0, 1));
		result = sphere.findIntersections(ray);
		assertNull("Error: result should be null", result);
		// TC21: Ray starts after the tangent point
		ray = new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0));
		result = sphere.findIntersections(ray);
		assertNull("Error: result should be null", result);
		// **** Group: Special cases
		// TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's
		// center line
		ray = new Ray(new Point3D(-2, 0, 0), new Vector(0, 0, 1));
		result = sphere.findIntersections(ray);
		assertNull("Error: result should be null", result);
	}

}
