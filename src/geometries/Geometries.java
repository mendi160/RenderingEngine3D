package geometries;

import java.util.LinkedList;
import java.util.List;
import scene.Box;
import primitives.Point3D;
import primitives.Ray;

/**
 * Class Geometries this class represent a object the contain numbers of
 * geometries object such as triangle sphere plane tube this class using list to
 * contain the object because its much efficient and the time complexity is much
 * better !
 * 
 * @author Mendi Shneorson and Yehonatan Eliyahu
 *
 */
public class Geometries extends Intersectable {
	private List<Intersectable> _shapes;
	private Intersectable _lastAdded;

	/**
	 * Empy constructor takes 0 argument :)
	 */
	public Geometries() {
		_shapes = new LinkedList<Intersectable>();
		_minBoundary = new Point3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		_maxBoundary = new Point3D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
	}

	/**
	 * This constructor take an infinite number of geometries (or till stack
	 * overflow)
	 * 
	 * @param geometries
	 */
	public Geometries(Intersectable... geometries) {
		this(); // first we initialize the list
		add(geometries);
	}

	/**
	 * This function take any infinte number of Geometries and add the to the list
	 * we have here
	 * 
	 * @param geometries
	 */
	public void add(Intersectable... geometries) {
		for (Intersectable intersectable : geometries) {
			_shapes.add(intersectable);
			_lastAdded = intersectable;
			setMinCoordinates();
			setMaxCoordinates();
		}
	}

	/**
	 * This function returns only the relevant point of the intersection using the
	 * help of regular grid structure if the box is null that means we call the
	 * regular find intersection (without acceleration)
	 * 
	 * @param ray            Ray that intersect
	 * @param box            box of the scene
	 * @param shadowRaysCase if its shadow ray we traverse always all the way .
	 * @param dis            distannce for find intersection
	 * @return the relevant point
	 */
	public List<GeoPoint> findRelevantIntersections(Ray ray, Box box, boolean shadowRaysCase, double dis) {
		if (box == null)
			return this.findIntersections(ray, dis);
		return box.findIntersectionsInTheBox(ray, shadowRaysCase, dis);
	}

	/**
	 * Getter
	 * 
	 * @return List of shapes
	 */
	public List<Intersectable> getShapes() {
		return _shapes;
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray, double max) {
		List<GeoPoint> intersections = null;
		for (Intersectable shape : _shapes) {
			List<GeoPoint> temp = shape.findIntersections(ray, max);
			if (temp != null) {
				if (intersections == null)
					intersections = new LinkedList<GeoPoint>();
				intersections.addAll(temp);
			}
		}
		return intersections;
	}

	@Override
	public void setMinCoordinates() {
		double x, y, z;
		x = _lastAdded._minBoundary.getX().get();
		y = _lastAdded._minBoundary.getY().get();
		z = _lastAdded._minBoundary.getZ().get();
		double minX = _minBoundary.getX().get();
		double minY = _minBoundary.getY().get();
		double minZ = _minBoundary.getZ().get();
		if (x < minX)
			minX = x;
		if (y < minY)
			minY = y;
		if (z < minZ)
			minZ = z;
		_minBoundary = new Point3D(minX, minY, minZ);
	}

	@Override
	public void setMaxCoordinates() {
		double x, y, z;
		x = _lastAdded._maxBoundary.getX().get();
		y = _lastAdded._maxBoundary.getY().get();
		z = _lastAdded._maxBoundary.getZ().get();
		double maxX = _maxBoundary.getX().get();
		double maxY = _maxBoundary.getY().get();
		double maxZ = _maxBoundary.getZ().get();
		if (x > maxX)
			maxX = x;
		if (y > maxY)
			maxY = y;
		if (z > maxZ)
			maxZ = z;
		_maxBoundary = new Point3D(maxX, maxY, maxZ);
	}
}