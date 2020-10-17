package scene;

import java.util.LinkedList;
import java.util.List;
import elements.*;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

/**
 * This Class represent Scene - A complete set of objects that build image
 * Objects are : scene name background color Ambient Light Geometrie - list Of
 * intersectable shapes Camera
 * 
 * @author yehonatan Eliyahu & Mendi Shneorson
 *
 */
public class Scene {
	private String _name;
	private Color _background;
	private AmbientLight _ambientLight;
	private Geometries _geometries;
	private Camera _camera;
	private Box _box;
	private double _distance;
	private List<LightSource> _light = new LinkedList<LightSource>();

	/**
	 * Constructor That Take Only the Scene name All the rest members , use Setter
	 * please.
	 * 
	 * @param name Scene's name
	 */
	public Scene(String name) {
		_name = name;
		_geometries = new Geometries();
	}

	/**
	 * add list of Intersctable to the list
	 *
	 * @param geometries list of Intersctable
	 */
	public void addGeometries(Geometries geometries) {
		if (geometries == null)
			return;
		List<Intersectable> list;
		list = geometries.getShapes();
		for (Intersectable intersectable : list)
			_geometries.add(intersectable);
	}

	/**
	 * add list of Intersctable to the list
	 *
	 * @param geometries list of Intersctable
	 */
	public void addGeometries(Intersectable... geometries) {
		if (geometries == null)
			return;
		_geometries.add(geometries);
	}

	/**
	 * Add lights to Scene
	 * 
	 * @param lights
	 */
	public void addLights(LightSource... lights) {
		for (LightSource lightSource : lights) {
			_light.add(lightSource);
		}
	}

	// ================== Getters ==============
	/**
	 * Get the Scene name
	 * 
	 * @return The Scene name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Get the Scene background
	 * 
	 * @return background color of Scene
	 */
	public Color getBackground() {
		return _background;

	}

	/**
	 * Getter of light
	 * 
	 * @return Light
	 */
	public List<LightSource> getLights() {
		return _light;
	}

	/**
	 * Get The Ambient light
	 * 
	 * @return
	 */
	public AmbientLight getAmbientLight() {
		return _ambientLight;
	}

	/**
	 * Get Geometries (include all geometrie shapes)
	 * 
	 * @return Geometries
	 */
	public Geometries getGeometries() {
		return _geometries;

	}

	/**
	 * Get the Camera of the scene
	 * 
	 * @return Camera
	 */
	public Camera getCamera() {
		return _camera;
	}

	/**
	 * Get the distance
	 * 
	 * @return The distance between the Scene and the Camera
	 */
	public double getDistance() {
		return _distance;
	}

	/**
	 * getter of box scene
	 * 
	 * @return box
	 */
	public Box getBox() {
		return _box;
	}

	// ================== Setters ==============
	/**
	 * Set Background Color
	 * 
	 * @param BG primitives.Color
	 */
	public void setBackground(Color BG) {
		_background = BG;
	}

	/**
	 * Set Ambient Light
	 * 
	 * @param AL AmbientLight
	 */
	public void setAmbientLight(AmbientLight AL) {
		_ambientLight = AL;
	}

	/**
	 * Set the Camera
	 * 
	 * @param camera Camera
	 */
	public void setCamera(Camera camera) {
		_camera = camera;
	}

	/**
	 * Setter for box
	 * set the box for rendering acceleration
	 * 
	 * @param lambda
	 * @return this
	 */
	public Scene setBox(int lambda) {
		if (lambda < 0)
			throw new IllegalArgumentException("Box Density can't be a nagitve number\n");
		_box=new Box(lambda, _geometries);
		return this;
	}

	/**
	 * Set The distance Of the view plane from camera
	 * 
	 * @param d distance
	 */
	public void setDistance(double d) {
		_distance = d;
	}
}
