package geometries;

import static primitives.Util.*;
import java.util.List;
import primitives.*;

/**
 * This Class represent Sphere with radius from superclass and center point we
 * generate sphere
 * 
 * @author Mendi Shneorson & Yehonatan Eliyahu
 *
 */
public class Sphere extends RadialGeometry {

	private Point3D _centerPoint;

	/**
	 * Sphere ctor get Center Point and Radius, Sets emission color and material in
	 * default value
	 * 
	 * @param center
	 * @param rad
	 */
	public Sphere(Point3D center, double rad) {
		this(Color.BLACK, center, rad);
	}

	/**
	 * sphere ctor get Center Point and Radius and Color, sets material in default
	 * value
	 * 
	 * @param color
	 * @param center
	 * @param rad    Radius
	 */
	public Sphere(Color emission, Point3D center, double rad) {
		this(emission, new Material(0, 0, 0), center, rad);
	}

	/**
	 * sphere ctor get Center Point and Radius and Color and Material
	 * 
	 * @param color
	 * @param material
	 * @param center
	 * @param rad      radius
	 */
	public Sphere(Color emission, Material material, Point3D center, double rad) {
		super(emission, material, rad);
		_centerPoint = center;
		setMinCoordinates();
		setMaxCoordinates();
	}

	/**
	 * get the center point of sphere
	 * 
	 * @return The center point of the sphere
	 */
	public Point3D getCenterPoint() {
		return _centerPoint;
	}

	@Override
	public Vector getNormal(Point3D p) {
		return p.subtract(_centerPoint).normalize();
	}

	@Override
	public String toString() {
		return super.toString() + "CP: " + _centerPoint + " ";
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray, double max) {
		Point3D p0 = ray.getP0();
		Vector v = ray.getDir();
		Vector u;
		try {
			// Case the point is equals to the center point
			u = _centerPoint.subtract(p0);
		} catch (IllegalArgumentException e) {
			// there is only exit intersection
			return List.of(new GeoPoint(this, ray.getPoint(this._radius)));
		}
		double tm = alignZero(v.dotProduct(u));
		double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
		if (d > _radius || isZero(d - _radius))
			// d is bigger than radius that mean there is no intersections
			return null;
		double th = Math.sqrt(_radius * _radius - d * d);
		double t1 = alignZero(tm + th);
		double t2 = alignZero(tm - th);
		double dis1 = alignZero(t1 - max);
		double dis2 = alignZero(t2 - max);
		if (t1 > 0 && dis1 <= 0 && t2 > 0 && dis2 <= 0) {
			// if both t1 and t2 grater than 0 that mean we have 2 intersection points
			return List.of(new GeoPoint(this, p0.add(v.scale(t1))), new GeoPoint(this, p0.add(v.scale(t2))));
		}
		if (t1 > 0 && dis1 <= 0) {
			return List.of(new GeoPoint(this, ray.getPoint(t1)));
		}
		if (t2 > 0 && dis2 <= 0) {
			return List.of(new GeoPoint(this, ray.getPoint(t2)));
		}
		// both less than 0 that mean there is no intersections
		return null;
	}

	@Override
	public void setMinCoordinates() {
		double x = _centerPoint.getX().get() - _radius;
		double y = _centerPoint.getY().get() - _radius;
		double z = _centerPoint.getZ().get() - _radius;
		_minBoundary = new Point3D(x, y, z);
	}

	@Override
	public void setMaxCoordinates() {
		double x = _centerPoint.getX().get() + _radius;
		double y = _centerPoint.getY().get() + _radius;
		double z = _centerPoint.getZ().get() + _radius;
		_maxBoundary = new Point3D(x, y, z);
	}
}
