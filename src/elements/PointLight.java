package elements;

import primitives.*;

/**
 * This Class represent a Point Light. This light has attenuation with distance,
 * and it lights up at 360 degrees - no direction.
 */
public class PointLight extends Light implements LightSource {

	protected Point3D _position;
	protected double _kC, _kL, _kQ;

	/**
	 * Ctor- Gets 3 attenuations for get better image
	 * 
	 * @param intensity Intensity of Point Light
	 * @param position  Position of Point Light
	 * @param kc        No attenuation with distance
	 * @param kl        attenuation with distance
	 * @param kq        attenuation with distance squared - The most influential
	 *                  attenuation.
	 */
	public PointLight(Color intensity, Point3D position, double kc, double kl, double kq) {
		super(intensity);
		_position = position;
		_kC = kc;
		_kL = kl;
		_kQ = kq;
	}

	@Override
	public Color getIntensity(Point3D p) {
		double dSquared = p.distanceSquared(_position);
		double d = Math.sqrt(dSquared);
		return _intesity.reduce(_kC + d * _kL + _kQ * dSquared);
	}

	@Override
	public Vector getL(Point3D p) {
		try {
			return p.subtract(_position).normalize();
		} catch (IllegalArgumentException e) {
			return null;
		}

	}

	@Override
	public double getDistance(Point3D p) {
		return Util.alignZero(p.distance(_position));
	}

}
