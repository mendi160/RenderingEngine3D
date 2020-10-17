package elements;

import primitives.Color;

/**
 * This abstract Class represent a Light. All types of lights inherit from it
 * and they needs to implement the "getIntesity" method.
 * 
 * @author Mendi Shneorson & Yehonatan Eliyahu
 *
 */
abstract class Light {
	protected Color _intesity;

	/**
	 * Ctor of Light
	 * 
	 * @param color Intensity of The light
	 */
	public Light(Color color) {
		_intesity = color;
	}

	/**
	 * Get the Light intensity
	 * 
	 * @return Color
	 */
	public Color getIntensity() {
		return _intesity;
	}
}
