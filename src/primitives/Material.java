package primitives;

/**
 * This Class represt Material by parametritze the property of it, how smooth it
 * is, how shine it is and if It is reflective and / or transparent .Note:
 * kS+kD<=1 for realistic result. Bigger nShiness is the smaller shine apear
 *
 */
public class Material {
	private double _kD, _kS, _kR, _kT, _kMatteD, _KMatteG;
	private int _nShininess;

	/**
	 * Ctor Material. Note: kS+kD<=1. The variables kR & kT get a default value.
	 * 
	 * @param kD         Coefficient of diffuse
	 * @param kS         Coefficient of specular
	 * @param nShininess The exponent
	 */
	public Material(double kD, double kS, int nShininess) {
		this(kD, kS, nShininess, 0, 0);
	}

	/**
	 * Ctor of material kmat is 0
	 * 
	 * @param kD         Coefficient of diffuse
	 * @param kS         Coefficient of specular
	 * @param nShininess The exponent
	 * @param kT         Coefficient of transparenecy
	 * @param kR         Coefficient of Refraction
	 */
	public Material(double kD, double kS, int nShininess, double kT, double kR) {
		this(kD, kS, nShininess, kT, kR, 0, 0);
	}

	/**
	 * Ctor of material
	 * 
	 * @param kD         Coefficient of diffuse
	 * @param kS         Coefficient of specular
	 * @param nShininess The exponent
	 * @param kT         Coefficient of transparenecy
	 * @param kR         Coefficient of Refraction
	 * @param kMatteD    Coefficient for "Diffused Glass"
	 * @param kMatteG    Coefficient for "Glossy Surfaces"
	 */
	public Material(double kD, double kS, int nShininess, double kT, double kR, double kMatteD, double kMatteG) {
		_kD = kD;
		_kS = kS;
		_nShininess = nShininess;
		_kT = kT;
		_kR = kR;
		_kMatteD = kMatteD;
		_KMatteG = kMatteG;
	}

	/**
	 * Getter
	 * 
	 * @return The Coefiicient of diffuse
	 */
	public double getKd() {
		return _kD;
	}

	/**
	 * Getter
	 * 
	 * @return The Coefiicient of specular
	 */
	public double getKs() {
		return _kS;
	}

	/**
	 * Getter
	 * 
	 * @return The Coefiicient of reflection
	 */
	public double getKr() {
		return _kR;
	}

	/**
	 * Getter
	 * 
	 * @return The Coefiicient of transparenecy
	 */
	public double getKt() {
		return _kT;
	}

	/**
	 * Getter
	 * 
	 * @return The exponent
	 */
	public int getNShininess() {
		return _nShininess;
	}

	/**
	 * Getter
	 * 
	 * @return The Coefficient for "Diffused Glass"
	 */
	public double getKMatteD() {
		return _kMatteD;
	}

	/**
	 * Getter
	 * 
	 * @return The Coefficient for "Glossy Surfaces"
	 */
	public double getKMatteG() {
		return _KMatteG;
	}
}
