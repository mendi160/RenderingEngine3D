package geometries;

import primitives.*;

/**
 * This is the interface of all the geometrys shape in the 3d cartesian
 * coordante each class the implements this interface must implement getNormal
 * method the func return... Normal vector
 * 
 * @author Yehonatan Eliyahu & Mendi Shneorson
 *
 */
public abstract class Geometry extends Intersectable {
	protected Color _emission;
	protected Material _material;

	/**
	 * Default Ctor: init _emision to Black and init Material with default value
	 */
	public Geometry() {
		this(Color.BLACK);
	}

	/**
	 * Ctor with emission parameter set and init Material with default value
	 * 
	 * @param emission
	 */
	public Geometry(Color emission) {
		this(emission, new Material(0, 0, 0));
	}

	/**
	 * ctor the get material and Color
	 * 
	 * @param emission
	 * @param material
	 */
	public Geometry(Color emission, Material material) {
		_emission = emission;
		_material = material;
	}

	/**
	 * Get the Emmssion
	 * 
	 * @return primitives.Color
	 */
	public Color getEmission() {
		return _emission;
	}

	/**
	 * Get The Material
	 * 
	 * @return Material
	 */
	public Material getMaterial() {
		return _material;
	}

	/**
	 * Important: In every Geometries shapes, we assume that the point is placed on
	 * the shape's surfacem that's why we don't verify it
	 * 
	 * Return the normal at specific point
	 * 
	 * @param p
	 * @return The Vector the Normal To the Point
	 */
	public abstract Vector getNormal(Point3D p);
}
