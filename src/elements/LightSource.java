package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * This interfase is For Lighs Sources Classes Who implement this Interface must
 * implement The following Functions: getIntensity and getL
 * 
 * @author Mendi Shneorson And Yehonatan Eliyahu
 *
 */
public interface LightSource {

	/**
	 * Get Intensity this func returns the correct intensity at specific point
	 * 
	 * @param p the point you wish to know the intensity
	 * @return
	 */
	public Color getIntensity(Point3D p);

	/**
	 * this function is used to get the direction vector from the Point (p) to the
	 * Light Source
	 * 
	 * @param p The Point you want to get Direction
	 * @return
	 */
	public Vector getL(Point3D p);

	/**
	 * this Function get the distance between lightsource and the point P
	 * 
	 * @param p point
	 * @return the distance
	 */
	public double getDistance(Point3D p);
}
