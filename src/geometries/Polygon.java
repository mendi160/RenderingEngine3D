package geometries;

import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 */
public class Polygon extends Geometry {
	private static final double DELTA = 0.1; //To add some volume for boundery box   
	/**
	 * List of polygon's vertices
	 */
	protected List<Point3D> _vertices;
	/**
	 * Associated plane in which the polygon lays
	 */
	protected Plane _plane;

	/**
	 * This constructor takes only vertices and set color and material to default
	 * values
	 * 
	 * @param vertices
	 */
	public Polygon(Point3D... vertices) {
		this(Color.BLACK, vertices);
	}

	/**
	 * This ctor get color material will Be Default (0,0,0) To see detailed
	 * information go to See Also section
	 * 
	 * @see Polygon
	 * @param color    The Color of the polygon
	 * @param vertices
	 */
	public Polygon(Color color, Point3D... vertices) {
		this(color, new Material(0, 0, 0), vertices);
	}

	/**
	 * Polygon constructor based on vertices list. The list must be ordered by edge
	 * path. The polygon must be convex.
	 * 
	 * @param vertices list of vertices according to their order by edge path
	 * @throws IllegalArgumentException in any case of illegal combination of
	 *                                  vertices:
	 *                                  <ul>
	 *                                  <li>Less than 3 vertices</li>
	 *                                  <li>Consequent vertices are in the same
	 *                                  point
	 *                                  <li>The vertices are not in the same
	 *                                  plane</li>
	 *                                  <li>The order of vertices is not according
	 *                                  to edge path</li>
	 *                                  <li>Three consequent vertices lay in the
	 *                                  same line (180&#176; angle between two
	 *                                  consequent edges)
	 *                                  <li>The polygon is concave (not convex></li>
	 *                                  </ul>
	 *
	 * @see Polygon
	 * @param color
	 * @param material
	 * @param vertices
	 */
	public Polygon(Color color, Material material, Point3D... vertices) {
		super(color, material);
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		_vertices = List.of(vertices);
		// Generate the plane according to the first three vertices and associate the
		// polygon with this plane.
		// The plane holds the invariant normal (orthogonal unit) vector to the polygon
		_plane = new Plane(vertices[0], vertices[1], vertices[2]);
		if (vertices.length == 3) {
			setMinCoordinates();
			setMaxCoordinates();
			return; // no need for more tests for a Triangle
		}

		Vector n = _plane.getNormal();

		// Subtracting any subsequent points will throw an IllegalArgumentException
		// because of Zero Vector if they are in the same point
		Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
		Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

		// Cross Product of any subsequent edges will throw an IllegalArgumentException
		// because of Zero Vector if they connect three vertices that lay in the same
		// line.
		// Generate the direction of the polygon according to the angle between last and
		// first edge being less than 180 deg. It is hold by the sign of its dot product
		// with
		// the normal. If all the rest consequent edges will generate the same sign -
		// the
		// polygon is convex ("kamur" in Hebrew).
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
		for (int i = 1; i < vertices.length; ++i) {
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices[i].subtract(vertices[i - 1]);
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
		setMinCoordinates();
		setMaxCoordinates();
	}

	@Override
	public Vector getNormal(Point3D point) {
		return _plane.getNormal();
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray, double max) {
		List<GeoPoint> intersections = _plane.findIntersections(ray, max);
		// First of all, check if there is a point of intersection with the plane
		if (intersections == null)
			return null;
		Vector v = ray.getDir();
		Point3D p0 = ray.getP0();
		Vector v1 = _vertices.get(0).subtract(p0);
		Vector v2 = _vertices.get(1).subtract(p0);
		double t = v.dotProduct(v1.crossProduct(v2).normalize());
		if (isZero(t)) {
			return null;
		}
		boolean sign = t > 0;
		int size = _vertices.size();
		Vector vn = _vertices.get(size - 1).subtract(p0);
		t = v.dotProduct(vn.crossProduct(v1).normalize());
		if (isZero(t) || sign ^ (t > 0)) {
			return null;
		}
		for (int i = 2; i < size; ++i) {
			v1 = v2;
			v2 = _vertices.get(i).subtract(p0);
			t = v.dotProduct(v1.crossProduct(v2).normalize());
			if (isZero(t) || sign ^ (t > 0)) {
				return null;
			}
		}
		intersections.get(0).geometry = this;
		return intersections;
	}

	@Override
	public void setMinCoordinates() {
		double minX = Double.POSITIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double minZ = Double.POSITIVE_INFINITY;
		double x, y, z;
		for (Point3D p : _vertices) {
			x = p.getX().get();
			y = p.getY().get();
			z = p.getZ().get();
			if (x < minX)
				minX = x;
			if (y < minY)
				minY = y;
			if (z < minZ)
				minZ = z;
		}
		_minBoundary = new Point3D(minX - DELTA, minY - DELTA, minZ - DELTA);
	}

	@Override
	public void setMaxCoordinates() {
		double maxX = Double.NEGATIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		double maxZ = Double.NEGATIVE_INFINITY;
		double x, y, z;
		for (Point3D p : _vertices) {
			x = p.getX().get();
			y = p.getY().get();
			z = p.getZ().get();
			if (x > maxX)
				maxX = x;
			if (y > maxY)
				maxY = y;
			if (z > maxZ)
				maxZ = z;
		}
		_maxBoundary = new Point3D(maxX + DELTA, maxY + DELTA, maxZ + DELTA);
	}
}
