package primitives;

import java.lang.Math;

/**
 * Point 3D is in the cartesian coordiante system represented by 3 coordinates
 * (X,Y,Z) this class also includes Static member which is the (0,0,0) Vector
 * 
 * @author Yehonatan Eliyahu & Mendi Shneorson 
 *
 */
public class Point3D {
	protected Coordinate _x;
	protected Coordinate _y;
	protected Coordinate _z;
	public final static Point3D ZERO = new Point3D(0.0, 0.0, 0.0);

	/**
	 * point 3D ctor
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point3D(Coordinate x, Coordinate y, Coordinate z) {
		_x = x;
		_y = y;
		_z = z;
	}

	/**
	 * point 3D ctor
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point3D(double x, double y, double z) {
		_x = new Coordinate(x);
		_y = new Coordinate(y);
		_z = new Coordinate(z);
	}

	/**
	 * point 3D ctor
	 * 
	 * @param point
	 */
	public Point3D(Point3D point) {
		_x = point._x;
		_y = point._y;
		_z = point._z;
	}

	/**
	 * getter
	 * 
	 * @return coordinate
	 */
	public Coordinate getX() {
		return _x;
	}

	/**
	 * getter
	 * 
	 * @return coordinate
	 */
	public Coordinate getY() {
		return _y;
	}

	/**
	 * getter
	 * 
	 * @return coordinate
	 */
	public Coordinate getZ() {
		return _z;
	}

	/**
	 * add x+x and y+y etc..
	 * 
	 * @param vec
	 * @return new point
	 */
	public Point3D add(Vector vec) {
		Point3D newPoint = new Point3D(vec._head.getX().get() + this.getX().get(),
				vec._head.getY().get() + this.getY().get(), vec._head.getZ().get() + this.getZ().get());
		return newPoint;
	}

	/**
	 * subtract each coordinate
	 * 
	 * @param point Point3D
	 * @return new vector with direction from the parameter to the base
	 */
	public Vector subtract(Point3D point) {
		Vector newVec = new Vector(this._x.get() - point.getX().get(), this._y.get() - point.getY().get(),
				this._z.get() - point.getZ().get());
		return newVec;
	}

	/**
	 * x^2+y^2+z^2 this is the distance squared the get the correct distance
	 * use{@link distance}
	 * 
	 * @param point Point 3D
	 * @return distance squared between two points
	 */
	public double distanceSquared(Point3D point) {
		Vector tempVector;
		try {
			tempVector = point.subtract(this);
		} catch (Exception e) {
			return 0d;
		}
		return tempVector.lengthSquared();
	}

	/**
	 * using in "distanceSquared" method
	 * 
	 * @param point
	 * @return distance between two points
	 */
	public double distance(Point3D point) {
		return Math.sqrt(distanceSquared(point));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point3D))
			return false;
		Point3D temp = (Point3D) obj;
		return _x.equals(temp._x) && _y.equals(temp._y) && _z.equals(temp._z);
	}

	@Override
	public String toString() {
		return "(" + _x.toString() + "," + _y.toString() + "," + _z.toString() + ")";
	}

}
