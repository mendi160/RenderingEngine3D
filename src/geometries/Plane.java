package geometries;

import java.util.List;
import static primitives.Util.*;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * This class represent plane in 3d cartesian coordinate
 * 
 * @author Yehonatan Eliyahu & Mendi Shneorson
 *
 */
public class Plane extends Geometry {
	private Point3D _point;
	private Vector _normal;

	/**
	 * ctor from 3 points we generate 2 direction Vectors the system then operate
	 * crossProduct and generate the normal of the plane
	 * 
	 * @param p Point P
	 * @param q Point Q
	 * @param r Point R
	 */
	public Plane(Point3D p, Point3D q, Point3D r) {
		this(Color.BLACK, p, q, r);
	}

	/**
	 * ctor from 3 points we generate 2 direction Vectors the system then operate
	 * crossProduct and generate the normal of the plane and gets also Color
	 * Material will be kS=kD=nShiness=0
	 * 
	 * @param p Point P
	 * @param q Point Q
	 * @param r Point R
	 */
	public Plane(Color color, Point3D p, Point3D q, Point3D r) {
		this(color, new Material(0, 0, 0), p, q, r);
	}

	/**
	 * ctor from 3 points we generate 2 direction Vectors the system then operate
	 * crossProduct and generate the normal of the plane This Ctor also get Color of
	 * the Plane and The material
	 * 
	 * @param color    Color (primitives)
	 * @param material Matiral
	 * @param p        Point3D
	 * @param q        Point3D
	 * @param r        Point3D
	 */
	public Plane(Color color, Material material, Point3D p, Point3D q, Point3D r) {
		super(color, material);
		_point = p;
		// generate the two vectors
		Vector i = q.subtract(r);
		Vector j = q.subtract(p);
		// generate normal which is the result from cross product
		_normal = i.crossProduct(j).normalize();
		setMinCoordinates();
		setMaxCoordinates();
	}

	/**
	 * Constructor of Plane
	 * 
	 * Generating plane from point and Normal Vector simple initilze the fileds
	 * 
	 * @param point  Poind in 3D
	 * @param normal Vector
	 */
	public Plane(Point3D point, Vector normal) {
		this(Color.BLACK, point, normal);
	}

	/**
	 * Constructor of Plane
	 * 
	 * Generating plane from point and Normal Vector simple initilze the fileds
	 * 
	 * @param point  Poind in 3D
	 * @param normal Vector
	 */
	public Plane(Color color, Point3D point, Vector normal) {
		this(color, new Material(0, 0, 0), point, normal);
	}

	/**
	 * Generating plane from point and Normal Vector simple initilze the fileds and
	 * also initilize Color and Material
	 * 
	 * @param color
	 * @param material
	 * @param point
	 * @param normal
	 */
	public Plane(Color color, Material material, Point3D point, Vector normal) {
		super(color, material);
		_point = point;
		_normal = normal;
		setMinCoordinates();
		setMaxCoordinates();
	}

	/**
	 * a point of the plain get
	 * 
	 * @return point at the plane
	 */
	public Point3D getPoint() {
		return _point;
	}

	/**
	 * get normal to the plain
	 * 
	 * @return
	 */
	public Vector getNormal() {
		return _normal;
	}

	@Override
	public Vector getNormal(Point3D p) {
		return _normal;
	}

	@Override
	public String toString() {
		return _point.toString() + " " + _normal.toString() + " ";
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray, double max) {
		Vector numeratorVec;
		try {
			numeratorVec = _point.subtract(ray.getP0());
		} catch (IllegalArgumentException e) {
			// the ray start at the plane (maybe even composed in the plane) - no
			// intersection point
			return null;
		}
		double denominator = alignZero(_normal.dotProduct(ray.getDir()));
		if (denominator == 0) {
			// The ray is parallel to the plane
			return null;
		}
		double t = alignZero(_normal.dotProduct(numeratorVec) / denominator);
		if (t > 0 && alignZero(t - max) <= 0)
			return List.of(new GeoPoint(this, ray.getPoint(t)));
		return null;
	}

	@Override
	public void setMinCoordinates() {
		double x = Double.NEGATIVE_INFINITY, y = x, z = y;
		_minBoundary = new Point3D(x, y, z);
	}

	@Override
	public void setMaxCoordinates() {
		double x = Double.POSITIVE_INFINITY, y = x, z = y;
		_maxBoundary = new Point3D(x, y, z);
	}
}
