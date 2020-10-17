/**
 * 
 */
package unittests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import elements.Camera;
import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * this unit test is to test the integration of camera rays intersections with intersectable geometries
 * @author yehonatan Eliyahu & Mendi Shneorson
 *
 */
public class CameraIntegrationTests {
	private Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
	private Camera camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
	private List<GeoPoint> intersections;
	private Ray ray;
	private int count, Nx = 3, Ny = 3;
	private double screenDistance = 1, screenWidth = 3, screenHeight = 3;

	/**
	 * This function Calculate the number of intersecetion with the Camera Rays to
	 * the Intersectable (implement the Interface Intersectable) object
	 * 
	 * @param cam            A Camera Object
	 * @param shape          Geometry shape (Must implement Intersectable Interface)
	 * @param Nx             Number of the Columns in the ViewPlane
	 * @param Ny             Number of the Rows in the ViewPlane
	 * @param screenDistance The Distance from Camera to View Plane
	 * @param screenWidth    The with of the view plane
	 * @param screenHeight   The Height of the View Plane
	 * @return The number Of intersection of Camera Rays With The Shape
	 */
	private int numOfIntersections(Camera cam, Intersectable shape, int Nx, int Ny, double screenDistance,
			double screenWidth, double screenHeight) {
		int count = 0;
		for (int i = 0; i < Ny; ++i) {
			for (int j = 0; j < Nx; ++j) {
				ray = cam.constructRayThroughPixel(Nx, Ny, j, i, screenDistance, screenWidth, screenHeight);
				intersections = shape.findIntersections(ray);
				if (intersections != null) {
					count += intersections.size();
				}
			}
		}
		return count;
	}

	@Test
	public void constructRayIntersectionSphereTest() {
		// TC 01: Only the camera ray that going through pixel (1,1) intersect the
		// sphere
		Sphere sphere = new Sphere(new Point3D(0, 0, 3), 1);
		count = numOfIntersections(cam, sphere, Nx, Ny, screenDistance, screenWidth, screenHeight);
		assertEquals("Worng number of point", 2, count);
		// TC 02: All of the camera rays intersect the sphere
		sphere = new Sphere(new Point3D(0, 0, 2.5), 2.5);
		count = numOfIntersections(camera, sphere, Nx, Ny, screenDistance, screenWidth, screenHeight);
		assertEquals("Worng number of point", 18, count);
		// TC 03: All the camera Rays Going Through the Sphere Except all four
		// Corners((0,0), (0,2), (2,0) (2,2))
		sphere = new Sphere(new Point3D(0, 0, 2), 2);
		count = numOfIntersections(camera, sphere, Nx, Ny, screenDistance, screenWidth, screenHeight);
		assertEquals("Worng number of point", 10, count);
		// TC 04:The Camera is inside the Sphere , That means all The Rays that come
		// from
		// camera intersect ony once(exit intersection)
		sphere = new Sphere(new Point3D(0, 0, 2), 4);
		count = numOfIntersections(camera, sphere, Nx, Ny, screenDistance, screenWidth, screenHeight);
		assertEquals("Worng number of point", 9, count);
		// TC 05: The sphere behind the camera
		sphere = new Sphere(new Point3D(0, 0, -1), 0.5);
		count = numOfIntersections(camera, sphere, Nx, Ny, screenDistance, screenWidth, screenHeight);
		assertEquals("Worng number of point", 0, count);
	}

	@Test
	public void constructRayIntersectionPlaneTest() {
		// TC21: Plane is Have not any angle and its infront the camera all camera rays
		// intersect the plane
		Plane plane = new Plane(new Point3D(1, 0, 1), new Vector(0, 0, 1));
		count = numOfIntersections(camera, plane, Nx, Ny, screenDistance, screenWidth, screenHeight);
		assertEquals("Worng number of point", 9, count);
		// TC22: The Plane have angle but its not so sharp and still all the camera rays
		// intersect te plane
		plane = new Plane(new Point3D(-3, 3, 0), new Point3D(-1, -1, 0.5), new Point3D(3, -5, 1));
		count = numOfIntersections(camera, plane, Nx, Ny, screenDistance, screenWidth, screenHeight);
		assertEquals("Worng number of point", 9, count);
		// TC 23: The Angle is more sharp ,So all The ray Camera That Goes throgh the
		// buttom pixel Row doesnt intersect
		plane = new Plane(new Point3D(4, -1, -1), new Point3D(-1, 1, 3), new Point3D(-5, -3, -2));
		count = numOfIntersections(camera, plane, Nx, Ny, screenDistance, screenWidth, screenHeight);
		assertEquals("Worng number of point", 6, count);
	}

	@Test
	public void constructRayIntersectionTriangleTest() {
		// TC 31: Small triangle so Only the camera ray that going through pixel (1,1)
		// intersect
		Triangle triangle = new Triangle(new Point3D(1, 1, 2), new Point3D(0, -1, 2), new Point3D(-1, 1, 2));
		count = numOfIntersections(camera, triangle, Nx, Ny, screenDistance, screenWidth, screenHeight);
		assertEquals("Worng number of point", 1, count);
		// TC 32: Taller triangle so the camera ray that going through pixel (0,1)
		// intersect too
		triangle = new Triangle(new Point3D(1, 1, 2), new Point3D(0, -20, 2), new Point3D(-1, 1, 2));
		count = numOfIntersections(camera, triangle, Nx, Ny, screenDistance, screenWidth, screenHeight);
		assertEquals("Worng number of point", 2, count);
	}

}
