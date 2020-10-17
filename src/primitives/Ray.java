package primitives;

import static primitives.Util.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Ray which is portion of a line represented by a point and Direction Vector
 * has no bound (p0,+-infinity)
 * 
 * @author Mendi Shneorson & Yehonatan Eliyahu
 *
 */
public class Ray {

	private Point3D _p0;
	private Vector _dir;
	private static final double DELTA = 0.1;

	/**
	 * ctor.
	 * 
	 * @param p0  Begining point
	 * @param dir Direction vector
	 */
	public Ray(Point3D p0, Vector dir) {
		_p0 = p0;
		_dir = dir.normalize();// the vector "dir" must be normalized
	}

	/**
	 * this constructor is special its create ray but it also move the head point in
	 * the normal direction in DELTA or -DELTA (depend on the dotProduct)
	 * 
	 * @param head      head point of ray
	 * @param direction direction of ray
	 * @param normal    normal to the head point
	 */
	public Ray(Point3D head, Vector direction, Vector normal) {
		this(head, direction);
		double nv = normal.dotProduct(_dir);
		if (!Util.isZero(nv)) {
			Vector epsVector = normal.scale(nv > 0 ? DELTA : -DELTA);
			_p0 = head.add(epsVector);
		}
	}

	/**
	 * getter
	 * 
	 * @return Begining point
	 */
	public Point3D getP0() {
		return _p0;
	}

	/**
	 * getter
	 * 
	 * @return the direction
	 */
	public Vector getDir() {
		return _dir;
	}

	/**
	 * this function is very usefull in the Geometries packegae to calculate
	 * intersections. for refactoring we put this here (Class Ray). this function
	 * return point on the ray according to this formula (start ray point + t *
	 * dirction ray's )
	 * 
	 * @param t real number
	 * @return point on the ray
	 */
	public Point3D getPoint(double t) {
		try {
			if (Util.isZero(t))
				return _p0;
			return _p0.add(_dir.scale(t));
		} catch (IllegalArgumentException e) {
			return _p0;
		}

	}

	/**
	 * this function generat beam of rays when radius is bigger our beam spread on
	 * more area
	 * 
	 * @param n         normal vector of the point where beam start
	 * @param radius    radius of virtual circle
	 * @param distance  Distance between The intersetion point to the virtual circle
	 * @param numOfRays The number of the rays of the beam
	 * @return beam of rays
	 */
	public List<Ray> generateBeam(Vector n, double radius, double distance, int numOfRays) {
		List<Ray> rays = new LinkedList<Ray>();
		rays.add(this);// Includeing the main ray
		if (numOfRays == 1 || isZero(radius))// The component (glossy surface /diffuse glass) is turned off
			return rays;
		Vector nX = _dir.createNormal();
		Vector nY = _dir.crossProduct(nX);
		Point3D centerCircle = this.getPoint(distance);
		Point3D randomPoint;
		double x, y, d;
		double nv = alignZero(n.dotProduct(_dir));
		for (int i = 1; i < numOfRays; ++i) {
			x = getRandomNumber(-1, 1);
			y = Math.sqrt(1 - x * x);
			d = getRandomNumber(-radius, radius);
			x = alignZero(x * d);
			y = alignZero(y * d);
			randomPoint = centerCircle;
			if (x != 0)
				randomPoint = randomPoint.add(nX.scale(x));
			if (y != 0)
				randomPoint = randomPoint.add(nY.scale(y));
			Vector tPoint = randomPoint.subtract(_p0);
			double nt = alignZero(n.dotProduct(tPoint));
			if (nv * nt > 0) {
				rays.add(new Ray(_p0, tPoint));
			}
		}
		return rays;
	}

	@Override
	public String toString() {
		return _p0.toString() + " + t" + _dir.toString() + " ";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		Ray temp = (Ray) obj;
		return this._dir._head.equals(temp._dir._head) && this._p0.equals(temp._p0);
	}

}
