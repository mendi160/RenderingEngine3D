package elements;

import primitives.*;

/**
 * This class represents spot light. This light has attenuation with distance
 * and also direction.The attribute "narrow" - it to narrow the beam of light.
 */
public class SpotLight extends PointLight {
	private Vector _direction;
	private int _narrow;

	/**
	 * Ctor. Note: "_narrow' getting default value that is not going to make a
	 * difference
	 * 
	 * @param intensity Intensity of spot light
	 * @param position  Position of spot light
	 * @param direction Direction of spot light
	 * @param kc        No attenuation with distance
	 * @param kl        attenuation with distance
	 * @param kq        attenuation with distance squared - The most influential
	 *                  attenuation.
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
		this(intensity, position, direction, kC, kL, kQ, 1);
	}

	/**
	 * 
	 * @param intensity Intensity of spot light
	 * @param position  Position of spot light
	 * @param direction Direction of spot light
	 * @param kc        No attenuation with distance
	 * @param kl        Coefficient to distance
	 * @param kq        Coefficient to distance squared - The most influential
	 *                  Coefficient.
	 * @param narrow    Coefficient - to narrow the beam of light (bigger =
	 *                  narrower)
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ, int narrow) {
		super(intensity, position, kC, kL, kQ);
		_direction = direction.normalize();
		_narrow = narrow;
	}

	@Override
	public Color getIntensity(Point3D p) {
		Vector l = getL(p);
		if (l == null)
			return Color.BLACK;
		double d = _direction.dotProduct(l);
		if (d <= 0)
			return Color.BLACK;
		// For narrow the beam of light
		d = Math.pow(d, _narrow);
		return super.getIntensity(p).scale(d);
	}
}
