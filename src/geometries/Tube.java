package geometries;

import static primitives.Util.*;
import java.util.List;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * tube "galil" inherit from radial geometries comprising Ray unlimited which is
 * the direction of the tube
 * 
 * @author Mendi Shneorson & Yehonatan Eliyahu
 */
public class Tube extends RadialGeometry {

	protected Ray _axisRay;

	/**
	 * Tube ctor gets radius and ray, sets emission color and materilal in default
	 * value
	 * 
	 * @param radius
	 * @param axisRay
	 */
	public Tube(double radius, Ray axisRay) {
		this(Color.BLACK, radius, axisRay);
	}

	/**
	 * Tube ctor gets radius and ray and the Color, sets material in default value
	 * 
	 * @param color   Color of the Tube
	 * @param radius
	 * @param axisRay
	 */
	public Tube(Color emission, double radius, Ray axisRay) {
		this(emission, new Material(0, 0, 0), radius, axisRay);
	}

	/**
	 * Tube ctor gets radius and ray gets Color of the Tube gets Tube's Material
	 * 
	 * @param color
	 * @param material
	 * @param radius
	 * @param axisRay
	 */
	public Tube(Color emission, Material material, double radius, Ray axisRay) {
		super(emission, material, radius);
		_axisRay = axisRay;
		setMinCoordinates();
		setMaxCoordinates();
	}

	/**
	 * Tube ctor gets radius and ray gets Color of the Tube gets Tube's Material
	 * 
	 * @param color
	 * @param material
	 * @param radius
	 * @param axisRay
	 * @param cylinder
	 */
	public Tube(Color emission, Material material, double radius, Ray axisRay, boolean cylinder) {
		super(emission, material, radius);
		_axisRay = axisRay;
	}

	/**
	 * @return The axis ray
	 */
	public Ray getAxisRay() {
		return _axisRay;
	}

	@Override
	public Vector getNormal(Point3D p) {

		Vector v = p.subtract(_axisRay.getP0());
		double t = alignZero(_axisRay.getDir().dotProduct(v));
		// Vector (p-p0) is normal to dirction vector
		if (t == 0) {
			return v.normalize();
		}
		Point3D o = _axisRay.getP0().add(_axisRay.getDir().scale(t));
		return p.subtract(o).normalize();
	}

	@Override
	public String toString() {
		return super.toString() + _axisRay.toString();
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

	@Override
	public List<GeoPoint> findIntersections(Ray ray, double max) {
		Point3D p0 = ray.getP0();
		Point3D pa = _axisRay.getP0();
		Vector v = ray.getDir();
		Vector va = _axisRay.getDir();
		Vector daltaVec = null;
		try {
			daltaVec = p0.subtract(pa);
		} catch (Exception e) {
			// the ray of tube and ray that candidate to intersect are the same
			if (v.equals(va) || v.equals(va.scale(-1)))
				return null;
		}
		Vector temp1, temp2;
		double _scale = alignZero(v.dotProduct(va));
		try {
			temp1 = v.subtract(va.scale(_scale));
		} catch (Exception e) {
			if (_scale == 0) {
				// v is ortogonal to va
				temp1 = v;
			} else {
				// v and va are parallal
				return null;
			}
		}
		double a, b, c, discriminant;
		a = alignZero(temp1.lengthSquared()); // if temp1 is null, already returned null
		if (daltaVec == null || isZero(daltaVec.dotProduct(va)))
			temp2 = daltaVec;
		else {
			try {
				temp2 = daltaVec == null ? temp2 = null
						: daltaVec.subtract(va.scale(alignZero(daltaVec.dotProduct(va))));
			} catch (IllegalArgumentException e) {
				// va is equal to dalta vector by scale
				temp2 = null;
			}
		}
		b = temp2 == null ? 0d : 2 * (alignZero(temp1.dotProduct(temp2)));
		c = temp2 == null ? -_radius * _radius : alignZero(temp2.lengthSquared() - _radius * _radius);
		discriminant = alignZero(b * b - 4 * a * c);
		if (discriminant < 0)
			// no intersection
			return null;
		if (discriminant == 0) {
			// in this case we got one solution - means maximum one intersection
			// if The ray starting outside and we got only one solution that means is a
			// tangens point - no intersection
			if (p0.distance(pa) > _radius)
				return null;
			double t = alignZero((-1 * b) / (2 * a));// a = temp1, so in this point, a for sure not zero

			if (t <= 0) {
				// no intersection(the intersection in the opposite direction)
				// or (if t=0) the ray start at the tube and we have only tangent point
				return null;
			}
			return List.of(new GeoPoint(this, ray.getPoint(t)));
		}
		// at this point we know that we have two solutions we need to choose the
		// relevant(s)(positive ones)
		discriminant = Math.sqrt(discriminant);
		double solution_1 = alignZero(((-b) + discriminant) / (2 * a));
		double solution_2 = alignZero((-b) - discriminant) / (2 * a);
		double dis1 = alignZero(solution_1 - max);
		double dis2 = alignZero(solution_2 - max);
		if (solution_1 > 0 && dis1 <= 0 && solution_2 > 0 && dis2 <= 0) {
			return List.of(new GeoPoint(this, ray.getPoint(solution_1)), new GeoPoint(this, ray.getPoint(solution_2)));
		}
		if (solution_1 > 0 && dis1 <= 0) {
			return List.of(new GeoPoint(this, ray.getPoint(solution_1)));
		}
		if (solution_2 > 0 && dis2 <= 0) {
			return List.of(new GeoPoint(this, ray.getPoint(solution_2)));
		}
		return null;
	}
}
