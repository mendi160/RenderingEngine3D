package elements;

import primitives.Color;

/**
 * This Class represent an Ambient Light inherit From Light which is constant
 * light in the background of thhe image
 * 
 * @see elements.Light
 * @author yehonatan Eliyahu & Mendi Shneorson
 *
 */
public class AmbientLight extends Light {

	/**
	 * Construct The Ambient Light using the super ctor
	 * 
	 * @param iA Intensity of Ambient Light
	 * @param kA Coefiicient
	 */
	public AmbientLight(Color iA, double kA) {
		super(iA.scale(kA));
	}
}
