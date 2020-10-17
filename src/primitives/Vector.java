package primitives;

/**
 * Vector the _head is the direction and size from zero vector(0,0,0)
 *
 * @author Mendi Shneorson & Yehonatan Eliyahu
 *
 */
public class Vector {
	Point3D _head;

	/**
	 * ctors overview argument must be Coordiante. first one getting 3 coordinates
	 * in case of zero vector - IllegalArgumentException will thrown
	 * 
	 * @param x X
	 * @param y Y
	 * @param z Z
	 */
	public Vector(Coordinate x, Coordinate y, Coordinate z) {
		_head = new Point3D(x, y, z);
		if (Point3D.ZERO.equals(_head)) {
			_head = null;
			throw new IllegalArgumentException("Zero Vector is Invalid");
		}
	}

	/**
	 * get 3 doubles that represent coordinates (x,y,z) ** in case of zero vector we
	 * throw IllegalArgumentException**
	 * 
	 * @param x X
	 * @param y Y
	 * @param z Z
	 */
	public Vector(double x, double y, double z) {
		_head = new Point3D(x, y, z);
		if (Point3D.ZERO.equals(_head)) {
			_head = null;
			throw new IllegalArgumentException("Zero Vector is Invalid");
		}
	}

	/**
	 * ctor get point. in case of zero vector - IllegalArgumentException will thrown
	 * 
	 * @param point Point 3D
	 */
	public Vector(Point3D point) {
		if (Point3D.ZERO.equals(point))
			throw new IllegalArgumentException("Zero Vector is Invalid");
		this._head = point;
	}

	/**
	 * ctor the gets only Vector
	 * 
	 * @param vector
	 */
	public Vector(Vector vector) {
		_head = vector._head;
	}

	/**
	 * get the the head point
	 * 
	 * @return origin point
	 */
	public Point3D getHeadPoint() {
		return _head;
	}

	/**
	 * subtract vector form vector using subtract method of point 3 d
	 * 
	 * @return new Vector
	 * @param vec
	 */
	public Vector subtract(Vector vec) {
		return new Vector(this._head.subtract(vec._head));
	}

	/**
	 * add Vector to Vector using point3d method
	 * 
	 * @param vec
	 * @return new Vector
	 */
	public Vector add(Vector vec) {
		return new Vector(this._head.add(vec));
	}

	/**
	 * multiply each coordinate by scalar
	 * 
	 * @param scalar
	 * @return new scalar*Vector
	 */
	public Vector scale(double scalar) {
		return new Vector(_head._x.get() * scalar, _head._y.get() * scalar, _head._z.get() * scalar);
	}

	/**
	 * multiply each coordinate lhs with rhs vector coordinate
	 * 
	 * @param Vector rhs
	 * @return Double number
	 */
	public double dotProduct(Vector vec) {
		return this._head._x.get() * vec._head._x.get() + this._head._y.get() * vec._head._y.get()
				+ this._head._z.get() * vec._head._z.get();
	}

	/**
	 * this product generate new vector that Normal to the both Vectors
	 * {@link moreinfo https://en.wikipedia.org/wiki/Cross_product}
	 * 
	 * @param vec
	 * @return Normal Vector (orthogonal to the boths Vectors)
	 */
	public Vector crossProduct(Vector vec) {
		double i = this._head._y.get() * vec._head._z.get() - this._head._z.get() * vec._head._y.get();
		// -(X0*Z1-Z0*X1)==(Z0*X1-X0*Z1)
		double j = this._head._z.get() * vec._head._x.get() - this._head._x.get() * vec._head._z.get();
		double k = this._head._x.get() * vec._head._y.get() - this._head._y.get() * vec._head._x.get();
		return new Vector(i, j, k);
	}

	/**
	 * x^2+y^2+z^2 this is the distance squared the get the correct distance
	 * use{@link distance}
	 * 
	 * @param point Point 3D
	 * @return distance^2
	 */
	public double lengthSquared() {
		return _head._x.get() * _head._x.get() + _head._y.get() * _head._y.get() + _head._z.get() * _head._z.get();
	}

	/**
	 * squareroot of distanance
	 * 
	 * @return actuall distance
	 */
	public double length() {
		return Math.sqrt(lengthSquared());
	}

	/**
	 * normal the vector and return it. (change the original)
	 * 
	 * @return normal vector
	 */
	public Vector normalize() {
		double norma = length();
		_head = scale(1d / norma)._head;
		return this;
	}

	/**
	 * return new normal vector (doesn't change the original)
	 * 
	 * @return
	 */
	public Vector normalized() {
		Vector temp = new Vector(this);
		return temp.normalize();
	}

	/**
	 * This function return a normal Vector to "this" vector
	 * 
	 * @return normalized normal vector
	 */
	public Vector createNormal() {
		int min = 1;
		double x = _head.getX().get(), y = _head.getY().get(), z = _head.getZ().get();
		double minCoor = x > 0 ? x : -x;
		if (Math.abs(y) < minCoor) {
			minCoor = y > 0 ? y : -y;
			min = 2;
		}
		if (Math.abs(z) < minCoor) {
			min = 3;
		}
		switch (min) {
		case 1: {
			return new Vector(0, -z, y).normalize();
		}
		case 2: {
			return new Vector(-z, 0, x).normalize();
		}
		case 3: {
			return new Vector(y, -x, 0).normalize();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + min);
		}
	}

	@Override
	public String toString() {
		return this._head.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector))
			return false;
		Vector temp = (Vector) obj;
		return this._head.equals(temp._head);
	}

}
