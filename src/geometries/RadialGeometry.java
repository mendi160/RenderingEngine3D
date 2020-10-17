package geometries;

import primitives.Color;
import primitives.Material;

/**
 * Radial Geometry is a class the represent round shaped object and the common
 * thing to all the shapes is the radius so this is an abstract class that only
 * have one member which is the radius and its also implements the Geometry
 * interface (getNormal ) function
 * 
 * @author Mendi Shneorson & Yehonatan Eliyahu
 *
 */
public abstract class RadialGeometry extends Geometry {
	protected double _radius;

	/**
	 * constructor the initilize radius and default parameters for color and
	 * material
	 * 
	 * @param radius
	 */

	public RadialGeometry(double radius) {
		this(Color.BLACK, radius);
	}

	/**
	 * Constructor for radial geometry that takes color and radius , and set
	 * material to default
	 * 
	 * @param emission
	 * @param radius
	 */
	public RadialGeometry(Color emission, double radius) {
		this(emission, new Material(0, 0, 0), radius);
	}

	/**
	 * Constructor for Radial Geometry that takes color material and radius
	 * 
	 * @param emission
	 * @param material
	 * @param radius
	 */
	public RadialGeometry(Color emission, Material material, double radius) {
		super(emission, material);
		if (radius < 0)// radius cannot be negative number
			radius = -radius;
		_radius = radius;
	}

	/**
	 * copy constructor neccessary for classes that derieved from Radial geometry
	 * 
	 * @param rad
	 */
	public RadialGeometry(RadialGeometry rad) {
		_radius = rad.getRadius();
	}

	/**
	 * get radius
	 * 
	 * @return shape's radius
	 */
	public double getRadius() {
		return _radius;
	}

	@Override
	public String toString() {
		return "R is: " + _radius + " ";
	}
}
