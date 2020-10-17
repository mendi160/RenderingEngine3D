package geometries;

import java.util.List;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;

/**
 * this class represent triangle its inherit from Polygon actually is polygon
 * with 3 verticies
 * 
 * @author Mendi Shneorson & Yehonatan Eliyahu
 *
 */

public class Triangle extends Polygon {
	/**
	 * Triangle ctor gets 3 points. sets Color and material in default value
	 * 
	 * @param p
	 * @param q
	 * @param r
	 */

	public Triangle(Point3D p, Point3D q, Point3D r) {
		this(Color.BLACK, p, q, r);
	}

	/**
	 * Triangle ctor gets 3 points And also Color. sets material in default value
	 * 
	 * @param color
	 * @param p
	 * @param q
	 * @param r
	 */
	public Triangle(Color color, Point3D p, Point3D q, Point3D r) {
		this(color, new Material(0, 0, 0), p, q, r);
	}

	/**
	 **
	 * Triangle ctor gets 3 points And also Color and Material
	 * 
	 * @param color
	 * @param material
	 * @param p
	 * @param q
	 * @param r
	 */
	public Triangle(Color color, Material material, Point3D p, Point3D q, Point3D r) {
		super(color, material, p, q, r);
	}

	@Override
	public String toString() {
		String str = "";
		// its gonna print: point A and the point, point B etc..
		for (Point3D point3d : _vertices) {
			str += point3d.toString() + " ";
		}
		return str;
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray,double max) {
		// Using in findIntersections of polygon
		List<GeoPoint> intersections = super.findIntersections(ray,max);
		if (intersections == null)
			return null;
		intersections.get(0).geometry = this;
		return intersections;
	}
}
