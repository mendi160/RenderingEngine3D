/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import java.util.List;

import geometries.*;
import geometries.Intersectable.GeoPoint;

import org.junit.Test;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * @author Mendi Shneorson and Yehonatan Eliyahu
 *
 */
public class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Tube tube = new Tube(1, new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1)));

		// ============Equivelence Partition Test===============
		// TC01 Standart normal test
		assertEquals("worng normal", new Vector(1, 0, 0).normalize(), tube.getNormal(new Point3D(2, 0, 60)));
		// =============== Boundary Values Tests ==================
		// Test in case that new vector (p-p0) is normal to diraction vector
		assertEquals("worng normal", new Vector(1, 0, 0).normalize(), tube.getNormal(new Point3D(2, 0, 0)));
	}

	/**
	 * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Tube tube = new Tube(1, new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1)));
		GeoPoint geoP1;
		GeoPoint geoP2;
		// ============Equivelence Partition Test===============
		// TC01: The ray intersect the Tube at two points
		Ray ray = new Ray(new Point3D(5, 0.8, 4), new Vector(-1, 0, 3));
		List<GeoPoint> result = tube.findIntersections(ray);
		assertEquals("Wrong number of points", 2, result.size());
		if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
			result = List.of(result.get(1), result.get(0));
		geoP1 = new GeoPoint(tube, new Point3D(0.4, 0.8, 17.8));
		geoP2 = new GeoPoint(tube, new Point3D(1.6, 0.8, 14.2));
		assertEquals("should be two points of intersect", List.of(geoP1, geoP2), result);
		// TC02 ray intersect tube at one point (exit point)
		ray = new Ray(new Point3D(1.5, 0, 4), new Vector(2.5, 0, -2));
		result = tube.findIntersections(ray);
		geoP1 = new GeoPoint(tube, new Point3D(2, 0, 3.6));
		assertEquals("should be one point of intersect", List.of(geoP1), result);
		// TC03 ray doesnt intersect the tube
		ray = new Ray(new Point3D(8, 0, 4), new Vector(2.5, 0, -2));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// ================== Boundary Values Tests ==================
		// **** Group: Ray orthogonal to the tube Cases
		// TC 11: ray start before and going through the tube(2
		// point)
		ray = new Ray(new Point3D(3, 0.8, 5), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
			result = List.of(result.get(1), result.get(0));
		geoP1 = new GeoPoint(tube, new Point3D(0.4, 0.8, 5));
		geoP2 = new GeoPoint(tube, new Point3D(1.6, 0.8, 5));
		assertEquals("Ray crosses tube", List.of(geoP1, geoP2), result);
		// TC 12: ray start before and going through axis the tube(2
		// point)
		ray = new Ray(new Point3D(3, 0, 5), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
			result = List.of(result.get(1), result.get(0));
		geoP1 = new GeoPoint(tube, new Point3D(0, 0, 5));
		geoP2 = new GeoPoint(tube, new Point3D(2, 0, 5));
		assertEquals("Ray crosses tube", List.of(geoP1, geoP2), result);
		// TC 13: ray start before and going through the center point of tube(2 point)
		ray = new Ray(new Point3D(3, 0, 0), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
			result = List.of(result.get(1), result.get(0));
		geoP1 = new GeoPoint(tube, Point3D.ZERO);
		geoP2 = new GeoPoint(tube, new Point3D(2, 0, 0));
		assertEquals("Ray crosses tube", List.of(geoP1, geoP2), result);
		// TC 14:The ray start inside the tube(1 point)
		ray = new Ray(new Point3D(1.5, 0.8, 5), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		geoP1 = new GeoPoint(tube, new Point3D(0.4, 0.8, 5));
		assertEquals("Ray crosses tube", List.of(geoP1), result);
		// TC 15:The ray start inside the tube and going through axis the tube(1
		// point)
		ray = new Ray(new Point3D(1.5, 0, 5), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		geoP1 = new GeoPoint(tube, new Point3D(0, 0, 5));
		assertEquals("Ray crosses tube", List.of(geoP1), result);
		// TC 16:The ray start inside the tube and going through center point of the
		// tube(1
		// point)
		ray = new Ray(new Point3D(1.5, 0, 0), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		geoP1 = new GeoPoint(tube, Point3D.ZERO);
		assertEquals("Ray crosses tube", List.of(geoP1), result);
		// TC 17 :The ray start at the tube and go inside (1
		// point)
		ray = new Ray(new Point3D(1.6, 0.8, 5), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		geoP1 = new GeoPoint(tube, new Point3D(0.4, 0.8, 5));
		assertEquals("Ray crosses tube", List.of(geoP1), result);
		// TC 18 :The ray start at the tube and go inside through center point of the
		// tube(1
		// point)
		ray = new Ray(new Point3D(2, 0, 0), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		geoP1 = new GeoPoint(tube, Point3D.ZERO);
		assertEquals("Ray crosses tube", List.of(geoP1), result);
		// TC 19 :The ray start at the tube and go inside through axis of the tube (1
		// point)
		ray = new Ray(new Point3D(2, 0, 5), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		geoP1 = new GeoPoint(tube, new Point3D(0, 0, 5));
		assertEquals("Ray crosses tube", List.of(geoP1), result);
		// TC 20:The ray start after the tube and orthogonal to tube (null)
		ray = new Ray(new Point3D(-3, 0, 5), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// TC 21:The ray start at the tube and go outside (null)
		ray = new Ray(new Point3D(0, 0, 0), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// TC 22:The ray start at the tube center point and orthogonal to tube (1 point)
		ray = new Ray(new Point3D(1, 0, 0), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		geoP1 = new GeoPoint(tube, Point3D.ZERO);
		assertEquals("Ray crosses tube", List.of(geoP1), result);
		// TC 23: The ray start at the at tube axis vector and orthogonal to tube (1
		// point)
		ray = new Ray(new Point3D(1, 0, 80), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		geoP1 = new GeoPoint(tube, new Point3D(0, 0, 80));
		assertEquals("Ray crosses tube", List.of(geoP1), result);
		// TC 24:The Ray orthogonal and tangent the tube
		ray = new Ray(new Point3D(2, 3, 5), new Vector(0, 1, 0));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// TC 25:The Ray orthogonal and the ray not tangent the tube
		ray = new Ray(new Point3D(3, 3, 5), new Vector(0, 1, 0));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);

		// **** Group: Ray Parallel to the tube Cases (all cases return null)
		// TC 26:The ray start outside the tube and Parallel to tube (null)
		ray = new Ray(new Point3D(-3, 0, 5), new Vector(0, 0, 1));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// TC 27:The ray start inside the tube and Parallel to tube (null)
		ray = new Ray(new Point3D(1.5, 0, 5), new Vector(0, 0, 1));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// TC 28:The ray with same direction of the tube and start at the tube center
		// point(null)
		ray = new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// TC 29:The ray with oppsite direction of the tube and start at the tube center
		// point(null)
		ray = new Ray(new Point3D(1, 0, 0), new Vector(0, 0, -1));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// TC 30:The tube contain the ray (null)
		ray = new Ray(new Point3D(2, 0, 4), new Vector(0, 0, 1));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// TC 31:The ray start tube axis vector and they have same dircetion(null)
		ray = new Ray(new Point3D(1, 0, 69), new Vector(0, 0, 1));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// TC 31:The ray start tube axis vector and go in oppsite dircetion(null)
		ray = new Ray(new Point3D(1, 0, 69), new Vector(0, 0, -1));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// **** Group: Ray not Parallel neither orthogonal the tube Cases
		// 31: The Ray start at tube and go outside (null)
		ray = new Ray(new Point3D(2, 0, 4), new Vector(1, 0, 1));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// TC32: The Ray start at tube and go inside (1 point)
		ray = new Ray(new Point3D(2, 0, 4), new Vector(-2, 0, 1));
		result = tube.findIntersections(ray);
		geoP1 = new GeoPoint(tube, new Point3D(0, 0, 5));
		assertEquals("Ray crosses tube", List.of(geoP1), result);
		// TC33: The Ray start at tube and go inside through the Tube point (1 point)
		ray = new Ray(new Point3D(2, 0, 1), new Vector(-1, 0, -1));
		result = tube.findIntersections(ray);
		assertEquals("Ray crosses tube", List.of(new GeoPoint(tube, new Point3D(0, 0, -1))), result);
		// 34: The Ray start at tube center point (1 point)
		ray = new Ray(new Point3D(1, 0, 0), new Vector(-1, 0, 1));
		result = tube.findIntersections(ray);
		assertEquals("Ray crosses tube", List.of(new GeoPoint(tube, new Point3D(0, 0, 1))), result);
		// TC35: The Ray start at tube axis vector (1 point)
		ray = new Ray(new Point3D(1, 0, 80), new Vector(-1, 0, 1));
		result = tube.findIntersections(ray);
		assertEquals("Ray crosses tube", List.of(new GeoPoint(tube, new Point3D(0, 0, 81))), result);
		// TC 36:The Ray tangent to the Tube
		ray = new Ray(new Point3D(2, 3, 5), new Vector(0, 1, 3));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// TC 37: The Ray tangent to the Tube
		ray = new Ray(new Point3D(2, 3, 5), new Vector(1, 1, 3));
		result = tube.findIntersections(ray);
		assertNull("should be null", result);
		// TC 38: The Ray alomst in the same tube direction
		ray = new Ray(new Point3D(1, 0, 0), new Vector(0.001, 0, 1));
		result = tube.findIntersections(ray);
		assertEquals("Ray crosses tube", List.of(new GeoPoint(tube, new Point3D(2, 0, 1000))), result);

		// *********Group The ray orthogonal to the axis of the tube but but not going
		// through the tube

		// **** Group: Special cases
		// TC 39: dalta vector (ray point - tube point) is orthogonal to tube direction
		tube = new Tube(1, new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)));
		ray = new Ray(new Point3D(1, 0, 0), new Vector(-1, 0, 0));
		result = tube.findIntersections(ray);
		assertEquals("Ray crosses tube", List.of(new GeoPoint(tube, new Point3D(-1, 0, 0))), result);
	}

	@Test
	public void testFindIntersectionsRay_DanZilberstein() {
		Tube tube1 = new Tube(1d, new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0)));
		Vector vAxis = new Vector(0, 0, 1);
		Tube tube2 = new Tube(1d, new Ray(new Point3D(1, 1, 1), vAxis));
		Ray ray;
		GeoPoint geoP1;
		GeoPoint geoP2;
		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray's line is outside the tube (0 points)
		ray = new Ray(new Point3D(1, 1, 2), new Vector(1, 1, 0));
		assertNull("Must not be intersections", tube1.findIntersections(ray));

		// TC02: Ray's crosses the tube (2 points)
		ray = new Ray(new Point3D(0, 0, 0), new Vector(2, 1, 1));
		List<GeoPoint> result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 2 intersections", 2, result.size());
		if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
			result = List.of(result.get(1), result.get(0));
		geoP1 = new GeoPoint(tube2, new Point3D(0.4, 0.2, 0.2));
		geoP2 = new GeoPoint(tube2, new Point3D(2, 1, 1));
		assertEquals("Bad intersections", List.of(geoP1, geoP2), result);

		// TC03: Ray's starts within tube and crosses the tube (1 point)
		ray = new Ray(new Point3D(1, 0.5, 0.5), new Vector(2, 1, 1));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(2, 1, 1));
		assertEquals("Bad intersections", List.of(geoP1), result);

		// =============== Boundary Values Tests ==================

		// **** Group: Ray's line is parallel to the axis (0 points)
		// TC11: Ray is inside the tube (0 points)
		ray = new Ray(new Point3D(0.5, 0.5, 0.5), vAxis);
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC12: Ray is outside the tube
		ray = new Ray(new Point3D(0.5, -0.5, 0.5), vAxis);
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC13: Ray is at the tube surface
		ray = new Ray(new Point3D(2, 1, 0.5), vAxis);
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC14: Ray is inside the tube and starts against axis head
		ray = new Ray(new Point3D(0.5, 0.5, 1), vAxis);
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC15: Ray is outside the tube and starts against axis head
		ray = new Ray(new Point3D(0.5, -0.5, 1), vAxis);
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC16: Ray is at the tube surface and starts against axis head
		ray = new Ray(new Point3D(2, 1, 1), vAxis);
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC17: Ray is inside the tube and starts at axis head
		ray = new Ray(new Point3D(1, 1, 1), vAxis);
		assertNull("must not be intersections", tube2.findIntersections(ray));

		// **** Group: Ray is orthogonal but does not begin against the axis head
		// TC21: Ray starts outside and the line is outside (0 points)
		ray = new Ray(new Point3D(0, 2, 2), new Vector(1, 1, 0));
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC22: The line is tangent and the ray starts before the tube (0 points)
		ray = new Ray(new Point3D(0, 2, 2), new Vector(1, 0, 0));
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC23: The line is tangent and the ray starts at the tube (0 points)
		ray = new Ray(new Point3D(1, 2, 2), new Vector(1, 0, 0));
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC24: The line is tangent and the ray starts after the tube (0 points)
		ray = new Ray(new Point3D(2, 2, 2), new Vector(1, 0, 0));
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC25: Ray starts before (2 points)
		ray = new Ray(new Point3D(0, 0, 2), new Vector(2, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 2 intersections", 2, result.size());
		if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
			result = List.of(result.get(1), result.get(0));
		geoP1 = new GeoPoint(tube2, new Point3D(0.4, 0.2, 2));
		geoP2 = new GeoPoint(tube2, new Point3D(2, 1, 2));
		assertEquals("Bad intersections", List.of(geoP1, geoP2), result);
		// TC26: Ray starts at the surface and goes inside (1 point)
		ray = new Ray(new Point3D(0.4, 0.2, 2), new Vector(2, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(2, 1, 2));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC27: Ray starts inside (1 point)
		ray = new Ray(new Point3D(1, 0.5, 2), new Vector(2, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(2, 1, 2));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC28: Ray starts at the surface and goes outside (0 points)
		ray = new Ray(new Point3D(2, 1, 2), new Vector(2, 1, 0));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC29: Ray starts after
		ray = new Ray(new Point3D(4, 2, 2), new Vector(2, 1, 0));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC30: Ray starts before and crosses the axis (2 points)
		ray = new Ray(new Point3D(1, -1, 2), new Vector(0, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 2 intersections", 2, result.size());
		if (result.get(0).point.getY().get() > result.get(1).point.getY().get())
			result = List.of(result.get(1), result.get(0));
		geoP1 = new GeoPoint(tube2, new Point3D(1, 0, 2));
		geoP2 = new GeoPoint(tube2, new Point3D(1, 2, 2));
		assertEquals("Bad intersections", List.of(geoP1, geoP2), result);
		// TC31: Ray starts at the surface and goes inside and crosses the axis
		ray = new Ray(new Point3D(1, 0, 2), new Vector(0, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(1, 2, 2));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC32: Ray starts inside and the line crosses the axis (1 point)
		ray = new Ray(new Point3D(1, 0.5, 2), new Vector(0, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(1, 2, 2));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC33: Ray starts at the surface and goes outside and the line crosses the
		// axis (0 points)
		ray = new Ray(new Point3D(1, 2, 2), new Vector(0, 1, 0));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC34: Ray starts after and crosses the axis (0 points)
		ray = new Ray(new Point3D(1, 3, 2), new Vector(0, 1, 0));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC35: Ray start at the axis
		ray = new Ray(new Point3D(1, 1, 2), new Vector(0, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(1, 2, 2));
		assertEquals("Bad intersections", List.of(geoP1), result);

		// **** Group: Ray is orthogonal to axis and begins against the axis head
		// TC41: Ray starts outside and the line is outside (
		ray = new Ray(new Point3D(0, 2, 1), new Vector(1, 1, 0));
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC42: The line is tangent and the ray starts before the tube
		ray = new Ray(new Point3D(0, 2, 1), new Vector(1, 0, 0));
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC43: The line is tangent and the ray starts at the tube
		ray = new Ray(new Point3D(1, 2, 1), new Vector(1, 0, 0));
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC44: The line is tangent and the ray starts after the tube
		ray = new Ray(new Point3D(2, 2, 2), new Vector(1, 0, 0));
		assertNull("must not be intersections", tube2.findIntersections(ray));
		// TC45: Ray starts before
		ray = new Ray(new Point3D(0, 0, 1), new Vector(2, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 2 intersections", 2, result.size());
		if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
			result = List.of(result.get(1), result.get(0));
		geoP1 = new GeoPoint(tube2, new Point3D(0.4, 0.2, 1));
		geoP2 = new GeoPoint(tube2, new Point3D(2, 1, 1));
		assertEquals("Bad intersections", List.of(geoP1, geoP2), result);
		// TC46: Ray starts at the surface and goes inside
		ray = new Ray(new Point3D(0.4, 0.2, 1), new Vector(2, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(2, 1, 1));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC47: Ray starts inside
		ray = new Ray(new Point3D(1, 0.5, 1), new Vector(2, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(2, 1, 1));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC48: Ray starts at the surface and goes outside
		ray = new Ray(new Point3D(2, 1, 1), new Vector(2, 1, 0));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC49: Ray starts after
		ray = new Ray(new Point3D(4, 2, 1), new Vector(2, 1, 0));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC50: Ray starts before and goes through the axis head
		ray = new Ray(new Point3D(1, -1, 1), new Vector(0, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 2 intersections", 2, result.size());
		if (result.get(0).point.getY().get() > result.get(1).point.getY().get())
			result = List.of(result.get(1), result.get(0));
		geoP1 = new GeoPoint(tube2, new Point3D(1, 0, 1));
		geoP2 = new GeoPoint(tube2, new Point3D(1, 2, 1));
		assertEquals("Bad intersections", List.of(geoP1, geoP2), result);
		// TC51: Ray starts at the surface and goes inside and goes through the axis
		// head
		ray = new Ray(new Point3D(1, 0, 1), new Vector(0, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(1, 2, 1));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC52: Ray starts inside and the line goes through the axis head
		ray = new Ray(new Point3D(1, 0.5, 1), new Vector(0, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(1, 2, 1));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC53: Ray starts at the surface and the line goes outside and goes through
		// the axis head
		ray = new Ray(new Point3D(1, 2, 1), new Vector(0, 1, 0));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC54: Ray starts after and the line goes through the axis head
		ray = new Ray(new Point3D(1, 3, 1), new Vector(0, 1, 0));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC55: Ray start at the axis head
		ray = new Ray(new Point3D(1, 1, 1), new Vector(0, 1, 0));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(1, 2, 1));
		assertEquals("Bad intersections", List.of(geoP1), result);

		// **** Group: Ray's line is neither parallel nor orthogonal to the axis and
		// begins against axis head
		Point3D p0 = new Point3D(0, 2, 1);
		// TC61: Ray's line is outside the tube
		ray = new Ray(p0, new Vector(1, 1, 1));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC62: Ray's line crosses the tube and begins before
		ray = new Ray(p0, new Vector(2, -1, 1));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 2 intersections", 2, result.size());
		if (result.get(0).point.getY().get() > result.get(1).point.getY().get())
			result = List.of(result.get(1), result.get(0));
		geoP1 = new GeoPoint(tube2, new Point3D(2, 1, 2));
		geoP2 = new GeoPoint(tube2, new Point3D(0.4, 1.8, 1.2));
		assertEquals("Bad intersections", List.of(geoP1, geoP2), result);
		// TC63: Ray's line crosses the tube and begins at surface and goes inside
		ray = new Ray(new Point3D(0.4, 1.8, 1), new Vector(2, -1, 1));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(2, 1, 1.8));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC64: Ray's line crosses the tube and begins inside
		ray = new Ray(new Point3D(1, 1.5, 1), new Vector(2, -1, 1));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(2, 1, 1.5));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC65: Ray's line crosses the tube and begins at the axis head
		ray = new Ray(new Point3D(1, 1, 1), new Vector(0, 1, 1));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(1, 2, 2));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC66: Ray's line crosses the tube and begins at surface and goes outside
		ray = new Ray(new Point3D(2, 1, 1), new Vector(2, -1, 1));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC67: Ray's line is tangent and begins before
		ray = new Ray(p0, new Vector(0, 2, 1));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC68: Ray's line is tangent and begins at the tube surface
		ray = new Ray(new Point3D(1, 2, 1), new Vector(1, 0, 1));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC69: Ray's line is tangent and begins after
		ray = new Ray(new Point3D(2, 2, 1), new Vector(1, 0, 1));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);

		// **** Group: Ray's line is neither parallel nor orthogonal to the axis and
		// does not begin against axis head
		double sqrt2 = Math.sqrt(2);
		double denomSqrt2 = 1 / sqrt2;
		double value1 = 1 - denomSqrt2;
		double value2 = 1 + denomSqrt2;
		// TC71: Ray's crosses the tube and the axis
		ray = new Ray(new Point3D(0, 0, 2), new Vector(1, 1, 1));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 2 intersections", 2, result.size());
		if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
			result = List.of(result.get(1), result.get(0));
		geoP1 = new GeoPoint(tube2, new Point3D(value1, value1, 2 + value1));
		geoP2 = new GeoPoint(tube2, new Point3D(value2, value2, 2 + value2));
		assertEquals("Bad intersections", List.of(geoP1, geoP2), result);
		// TC72: Ray's crosses the tube and the axis head
		ray = new Ray(new Point3D(0, 0, 0), new Vector(1, 1, 1));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 2 intersections", 2, result.size());
		if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
			result = List.of(result.get(1), result.get(0));
		geoP1 = new GeoPoint(tube2, new Point3D(value1, value1, value1));
		geoP2 = new GeoPoint(tube2, new Point3D(value2, value2, value2));
		assertEquals("Bad intersections", List.of(geoP1, geoP2), result);
		// TC73: Ray's begins at the surface and goes inside
		// TC74: Ray's begins at the surface and goes inside crossing the axis
		ray = new Ray(new Point3D(value1, value1, 2 + value1), new Vector(1, 1, 1));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(value2, value2, 2 + value2));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC75: Ray's begins at the surface and goes inside crossing the axis head
		ray = new Ray(new Point3D(value1, value1, value1), new Vector(1, 1, 1));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(value2, value2, value2));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC76: Ray's begins inside and the line crosses the axis
		ray = new Ray(new Point3D(0.5, 0.5, 2.5), new Vector(1, 1, 1));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(value2, value2, 2 + value2));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC77: Ray's begins inside and the line crosses the axis head
		ray = new Ray(new Point3D(0.5, 0.5, 0.5), new Vector(1, 1, 1));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(value2, value2, value2));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC78: Ray's begins at the axis
		ray = new Ray(new Point3D(1, 1, 3), new Vector(1, 1, 1));
		result = tube2.findIntersections(ray);
		assertNotNull("must be intersections", result);
		assertEquals("must be 1 intersections", 1, result.size());
		geoP1 = new GeoPoint(tube2, new Point3D(value2, value2, 2 + value2));
		assertEquals("Bad intersections", List.of(geoP1), result);
		// TC79: Ray's begins at the surface and goes outside
		ray = new Ray(new Point3D(2, 1, 2), new Vector(2, 1, 1));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC80: Ray's begins at the surface and goes outside and the line crosses the
		// axis
		ray = new Ray(new Point3D(value2, value2, 2 + value2), new Vector(1, 1, 1));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
		// TC81: Ray's begins at the surface and goes outside and the line crosses the
		// axis head
		ray = new Ray(new Point3D(value2, value2, value2), new Vector(1, 1, 1));
		result = tube2.findIntersections(ray);
		assertNull("Bad intersections", result);
	}
}
