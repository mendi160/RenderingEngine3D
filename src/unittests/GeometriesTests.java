/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import geometries.*;
import primitives.*;

/**
 * @author Mendi Shneorson and Yehonatan Eliyahu
 *
 */
public class GeometriesTests extends Geometries {

	@Test
	public void testFindIntersections() {
		Plane plane = new Plane(new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));
		Ray ray = new Ray(new Point3D(2, 0.5, 3), new Vector(0, 0, -1));
		Sphere sphere = new Sphere(new Point3D(0, 6, 0), 2);
		Point3D a = new Point3D(1, 0, 0);
		Point3D b = new Point3D(3, 2, 0);
		Point3D c = new Point3D(5, 0, 0);
		Triangle triangle = new Triangle(a, b, c);
		// =========================Equivalance Partition Test=============
		// TC 01: Simple test - Ray intersect some of the shapes
		Geometries geometries = new Geometries(plane, sphere, triangle);
		List<GeoPoint> intersection = geometries.findIntersections(ray);
		assertEquals(2, intersection.size());
		// ========================Boundery Value Test=====================
		// TC11: Ray doesnt intersect any shape
		assertNull("Should be null", geometries.findIntersections(new Ray(new Point3D(8, 8, 8), new Vector(0, 0, 1))));
		// TC12: Ray intersect only one of the shapes
		intersection = geometries.findIntersections(new Ray(new Point3D(0, 6, 0), new Vector(0, 1, 0)));
		assertEquals(1, intersection.size());
		// TC13: Ray intersect all of the shapes
		sphere = new Sphere(new Point3D(3, 0, -3), 2);
		geometries.add(sphere);
		intersection = geometries.findIntersections(new Ray(new Point3D(2, 0.5, 3), new Vector(0, 0, -1)));
		assertEquals(4, intersection.size());
		// TC14: Empty Geometries list
		geometries = new Geometries();
		assertNull("Should be null", geometries.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1))));
	}

}
