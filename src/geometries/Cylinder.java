package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * 
 * @author Yehonatan Eliyahu & Mendi Shneorson & all friends in Microsoft this
 *         class is represent Cylinder in 3D Cartesian coordinate and inherit
 *         from Tube
 *
 */
public class Cylinder extends Tube {
	/**
	 * the variable _height represent height
	 */
	private double _height;

	/**
	 * Constructor of cylinder that set color and material to default value
	 * 
	 * @param radius
	 * @param Axis   Ray
	 * @param Height
	 */
	public Cylinder(double rad, Ray axisRay, double height) {
		this(Color.BLACK, rad, axisRay, height);
	}

	/**
	 * Constructor of cylinder that get Color and set material to default
	 * 
	 * @param color  Color of the Shape
	 * @param radius
	 * @param Axis   Ray
	 * @param Height
	 */
	public Cylinder(Color color, double rad, Ray axisRay, double height) {
		this(color, new Material(0, 0, 0), rad, axisRay, height);
	}

	/**
	 * Constructor of cylinder color and Material.
	 * 
	 * @see Cylinder Previus Contro
	 * @param color
	 * @param material
	 * @param rad
	 * @param axisRay
	 * @param height
	 */
	public Cylinder(Color color, Material material, double rad, Ray axisRay, double height) {
		super(color, material, rad, axisRay, true);
		_height = height;
		setMinCoordinates();
		setMaxCoordinates();
	}

	@Override
	public Vector getNormal(Point3D p) {
		// case point is on the edge we take the normal Vector of Ray to be the normal
		// vector
		double t;
		try {
			// in case the point is very close to p0
			t = _axisRay.getDir().dotProduct(p.subtract(_axisRay.getP0()));
		} catch (IllegalArgumentException e) {
			return getAxisRay().getDir();
		}
		// if the point is on the top or on the edge of the top where the height=_height
		// or on the edge of the buttom
		if (Util.isZero(t - _height) || Util.isZero(t))
			return getAxisRay().getDir();
		Point3D o = _axisRay.getP0().add(_axisRay.getDir().scale(t));
		return p.subtract(o).normalize();
	}

	/**
	 * Getter`
	 * 
	 * @return The Height of the Cylinder
	 */
	public double getHeight() {
		return _height;
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray, double max) {
		Point3D centerP = _axisRay.getP0();
		Vector cylinderDir = _axisRay.getDir();
		List<GeoPoint> intersectios = super.findIntersections(ray, max);
		List<GeoPoint> toReturn = null;
		// Check if there are intersections with the bottum of cylinder and/or the top
		// cylinder
		Plane buttomCap = new Plane(centerP, cylinderDir);
		Point3D pointAtTop = new Point3D(centerP.add(cylinderDir.scale(_height)));
		Plane topCap = new Plane(pointAtTop, cylinderDir);
		List<GeoPoint> intsB = buttomCap.findIntersections(ray, max);
		List<GeoPoint> intsT = topCap.findIntersections(ray, max);
		if (intsT != null) {
			GeoPoint topInter = intsT.get(0);
			double d = Util.alignZero(topInter.point.distance(pointAtTop) - _radius);
			if (d < 0) {
				// intersect the top
				if (toReturn == null)
					toReturn = new LinkedList<GeoPoint>();
				topInter.geometry = this;
				toReturn.add(topInter);
			}
		}
		if (intsB != null) {
			GeoPoint bInter = intsB.get(0);
			double d = Util.alignZero(bInter.point.distance(centerP) - _radius);
			if (d < 0) {
				// intersect the buttom
				if (toReturn == null)
					toReturn = new LinkedList<GeoPoint>();
				bInter.geometry = this;
				toReturn.add(bInter);
			}
		}
		if (toReturn != null && toReturn.size() == 2) // The maximum intersection points are 2
			return toReturn;
		if (intersectios == null) {
			return toReturn;
		}
		// In this point We knows that we got minimum 1 intersection point from the
		// tube.
		// check if intersection point(s) of tube relevant also for the cylinder
		GeoPoint gPoint = intersectios.get(0);
		gPoint.geometry = this;
		intsT = topCap.findIntersections(new Ray(gPoint.point, cylinderDir), max);
		intsB = buttomCap.findIntersections(new Ray(gPoint.point, cylinderDir.scale(-1)), max);
		if (intsT != null && intsB != null) {
			if (toReturn == null)
				toReturn = new LinkedList<GeoPoint>();
			toReturn.add(gPoint);
		}
		if (intersectios.size() == 2) {
			gPoint = intersectios.get(1);
			gPoint.geometry = this;
			intsT = topCap.findIntersections(new Ray(gPoint.point, cylinderDir), max);
			intsB = buttomCap.findIntersections(new Ray(gPoint.point, cylinderDir.scale(-1)), max);
			if (intsT != null && intsB != null) {
				if (toReturn == null)
					toReturn = new LinkedList<GeoPoint>();
				toReturn.add(gPoint);
			}
		}
		return toReturn;
	}

	@Override
	public void setMinCoordinates() {
		Point3D center = _axisRay.getP0();
		double minX = center.getX().get();
		double minY = center.getY().get();
		double minZ = center.getZ().get();
		Point3D centerHeight = this._axisRay.getPoint(_height);
		double centerHeightX = centerHeight.getX().get();
		double centerHeightY = centerHeight.getY().get();
		double centerHeightZ = centerHeight.getZ().get();
		if (minX > centerHeightX)
			minX = centerHeightX;
		if (minY > centerHeightY)
			minY = centerHeightY;
		if (minZ > centerHeightZ)
			minZ = centerHeightZ;
		minX -= _radius;
		minY -= _radius;
		minZ -= _radius;
		_minBoundary = new Point3D(minX, minY, minZ);
	}

	@Override
	public void setMaxCoordinates() {
		Point3D center = this._axisRay.getP0();
		double maxX = center.getX().get();
		double maxY = center.getY().get();
		double maxZ = center.getZ().get();
		Point3D centerHeight = this._axisRay.getPoint(_height);
		double centerHeightX = centerHeight.getX().get();
		double centerHeightY = centerHeight.getY().get();
		double centerHeightZ = centerHeight.getZ().get();
		if (maxX < centerHeightX)
			maxX = centerHeightX;
		if (maxY < centerHeightY)
			maxY = centerHeightY;
		if (maxZ < centerHeightZ)
			maxZ = centerHeightZ;
		maxX += _radius;
		maxY += _radius;
		maxZ += _radius;
		_maxBoundary = new Point3D(maxX, maxY, maxZ);
	}

	@Override
	public String toString() {
		return super.toString() + "H is: " + _height + " ";
	}
}
