package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * This Class Represent Light source at infinty (such as sun), so location is
 * not interesting - No attenuation with distance
 * 
 * @author Yehonatan Eliyahu & Mendi Shneorson
 * 
 */
public class DirectionalLight extends Light implements LightSource {
	private Vector _direction;

	/**
	 * Ctor of Directional Light
	 * 
	 * @param color Intensity of Directional Light
	 * @param dir   Direction of the Light
	 */
	public DirectionalLight(Color color, Vector dir) {
		super(color);
		_direction = dir.normalize();

	}

	@Override
	public Color getIntensity(Point3D p) {
		return super.getIntensity();
	}

	@Override
	public Vector getL(Point3D p) {
		return _direction;
	}

	@Override
	public double getDistance(Point3D p) {
		return Double.POSITIVE_INFINITY;
	}

}
