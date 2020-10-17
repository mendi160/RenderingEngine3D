package geometries;

import primitives.*;
import java.util.List;

/**
 * This abstract class is used to implement Intersections to all the Geometrys
 * that implement it and also minimum and maximum bounderies
 * 
 * @author Mendi Shneorson Yehonatan Eliyahus
 */
public abstract class Intersectable {
	protected Point3D _minBoundary;
	protected Point3D _maxBoundary;

	/**
	 * This Function Return all the intersection Points of the Ray in the Geometry
	 * 
	 * @param Ray The ray That gonna Intersect The intersectable Geometries
	 * @return List of The intersect Point , null if there is no intersection point
	 */
	public List<GeoPoint> findIntersections(Ray ray) {
		return findIntersections(ray, Double.POSITIVE_INFINITY);
	}

	/**
	 * This Function Return all the intersection Points of the Ray in the Geometry
	 * in specific distance ( that not bigger than max)
	 * 
	 * @param ray ray you want to check the intersectoin
	 * @param max maximum distance of intersection
	 * @return list of the intersection points
	 */
	public abstract List<GeoPoint> findIntersections(Ray ray, double max);

	/**
	 * this function set the maximum coordinates for this gemetry
	 */
	protected abstract void setMaxCoordinates();

	/**
	 * this function set the minimum coordinates for this gemetry
	 */
	protected abstract void setMinCoordinates();

	/**
	 * 
	 * @return - Returns the min point of Geometry
	 */
	public Point3D getMinCoordinates() {
		return _minBoundary;
	}

	/**
	 * 
	 * @return - Returns the max point of Geometry
	 */
	public Point3D getMaxCoordinates() {
		return _maxBoundary;
	}

	/**
	 * This Class Represent Point that on Geometry that is intersectable include the
	 * Point3D and the Geometry that its placed on
	 * 
	 * @author Mendi Shneorson Yehonatan Eliyahus
	 *
	 */
	public static class GeoPoint {
		public Geometry geometry;
		public Point3D point;

		/**
		 * Ctor of GeoPoint that gets Point 3d and the Geometry reference (not type)
		 * 
		 * @param geometry Intersectable geometry
		 * @param point    Point of the geometry
		 */
		public GeoPoint(Geometry geometry, Point3D point) {
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof GeoPoint))
				return false;
			GeoPoint geoPoint = (GeoPoint) obj;
			return geoPoint.geometry == this.geometry && geoPoint.point.equals(this.point);
		}
	}

}
