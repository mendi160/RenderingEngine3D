package renderer;

import static primitives.Util.*;
import java.util.List;
import elements.Camera;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.*;

/**
 * This class represent a rendering engine that take scene and image writer and
 * produce imgae
 * 
 * @author Mendi Shneorson & Yehonatan Eliyahu
 *
 */
public class Render {
	private ImageWriter _imageWriter;
	private Scene _scene;
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double DISTANCE = 10;
	private int _numOfRays;
	private int _threads = 1;
	private final int SPARE_THREADS = 2;
	private boolean _print = false;

	/**
	 * Constructor. "numOfRays" get defualt value 1 - only the main ray
	 *
	 * @param imageWriter Image Writer
	 * @param scene       Scene that include all the data for image (color ,ambient
	 *                    color, geometries,lights etc...)
	 */
	public Render(ImageWriter imageWriter, Scene scene) {
		this(imageWriter, scene, 1);
	}

	/**
	 * Constructor
	 *
	 * @param imageWriter Image Writer
	 * @param scene       Scene that include all the data for image (color ,ambient
	 *                    color, geometries,lights etc...)
	 * @param numOfRays   The number of the rays of the beam
	 */
	public Render(ImageWriter imageWriter, Scene scene, int numOfRays) {
		_imageWriter = imageWriter;
		_scene = scene;
		_scene = scene;
		_numOfRays = numOfRays;
	}

	/**
	 * Pixel is an internal helper class whose objects are associated with a Render
	 * object that they are generated in scope of. It is used for multithreading in
	 * the Renderer and for follow up its progress.<br/>
	 * There is a main follow up object and several secondary objects - one in each
	 * thread.
	 * 
	 * @author Dan
	 *
	 */
	private class Pixel {
		private long _maxRows = 0;
		private long _maxCols = 0;
		private long _pixels = 0;
		public volatile int row = 0;
		public volatile int col = -1;
		private long _counter = 0;
		private int _percents = 0;
		private long _nextCounter = 0;

		/**
		 * The constructor for initializing the main follow up Pixel object
		 * 
		 * @param maxRows the amount of pixel rows
		 * @param maxCols the amount of pixel columns
		 */
		public Pixel(int maxRows, int maxCols) {
			_maxRows = maxRows;
			_maxCols = maxCols;
			_pixels = maxRows * maxCols;
			_nextCounter = _pixels / 100;
			if (_print)
				System.out.printf("\r %02d%%", _percents);
		}

		/**
		 * Default constructor for secondary Pixel objects
		 */
		public Pixel() {
		}

		/**
		 * Internal function for thread-safe manipulating of main follow up Pixel object
		 * - this function is critical section for all the threads, and main Pixel
		 * object data is the shared data of this critical section.<br/>
		 * The function provides next pixel number each call.
		 * 
		 * @param target target secondary Pixel object to copy the row/column of the
		 *               next pixel
		 * @return the progress percentage for follow up: if it is 0 - nothing to print,
		 *         if it is -1 - the task is finished, any other value - the progress
		 *         percentage (only when it changes)
		 */
		private synchronized int nextP(Pixel target) {
			++col;
			++_counter;
			if (col < _maxCols) {
				target.row = this.row;
				target.col = this.col;
				if (_counter == _nextCounter) {
					++_percents;
					_nextCounter = _pixels * (_percents + 1) / 100;
					return _percents;
				}
				return 0;
			}
			++row;
			if (row < _maxRows) {
				col = 0;
				if (_counter == _nextCounter) {
					++_percents;
					_nextCounter = _pixels * (_percents + 1) / 100;
					return _percents;
				}
				return 0;
			}
			return -1;
		}

		/**
		 * Public function for getting next pixel number into secondary Pixel object.
		 * The function prints also progress percentage in the console window.
		 * 
		 * @param target target secondary Pixel object to copy the row/column of the
		 *               next pixel
		 * @return true if the work still in progress, -1 if it's done
		 */
		public boolean nextPixel(Pixel target) {
			int percents = nextP(target);
			if (percents > 0)
				if (Render.this._print)
					System.out.printf("\r %02d%%", percents);
			if (percents >= 0)
				return true;
			if (Render.this._print)
				System.out.printf("\r %02d%%", 100);
			return false;
		}
	}

	/**
	 * this function calculate the color of point with the help of beam
	 * 
	 * @param color  the color of the intersection point
	 * @param n      The normal vector of the point where beam start
	 * @param refRay reflected/refracted ray
	 * @param level  The level of recursiun
	 * @param k      kt/kr
	 * @param kk     kkt/kkr
	 * @param rad    radius/kMatte ,when radius is bigger the mattiut is more
	 *               intense
	 * @return The color
	 */
	private Color calcBeamColor(Color color, Vector n, Ray refRay, int level, double k, double kk, double rad) {
		Color addColor = Color.BLACK;
		List<Ray> rays = refRay.generateBeam(n, rad, DISTANCE, _numOfRays);
		for (Ray ray : rays) {
			GeoPoint refPoint = findClosestIntersection(ray);
			if (refPoint != null) {
				addColor = addColor.add(calcColor(refPoint, ray, level - 1, kk).scale(k));
			}
		}
		int size = rays.size();
		color = color.add(size > 1 ? addColor.reduce(size) : addColor);
		return color;
	}

	/**
	 * this function is to calculate color at specific point in specipic direction.
	 * this function calls another calcColor (overloaded) with more paramters that
	 * neccessary For recursion. after the overloaded calcColor finished we add the
	 * ambient light to the color we have and return
	 * 
	 * @param geopoint Point you want to Calculate
	 * @param inRay    the ray from viewPlane
	 * @return the color of the point
	 */
	private Color calcColor(GeoPoint geopoint, Ray inRay) {
		return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(_scene.getAmbientLight().getIntensity());
	}

	/**
	 * This function calculates the color of point with all of kindes of lights. We
	 * use Phong's model of lightTogather To got the precise color on the
	 * intersection point. We combine The Diffuse light and Spuecular Light and
	 * emission light and also using recursion to calculta refraction and
	 * reflectence
	 * 
	 * @param geoPoint Point you want to Calculate
	 * @param inRay    the ray from viewPlane
	 * @param level    the current level of rucursion (stop when is equal 1)
	 * @param k        the current attenuation level (stop when is equal or smaller
	 *                 0.001)
	 * @return color
	 */
	private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
		Color color = geoPoint.geometry.getEmission();
		Vector v = geoPoint.point.subtract(_scene.getCamera().getP0()).normalize();
		Vector n = geoPoint.geometry.getNormal(geoPoint.point);
		Material material = geoPoint.geometry.getMaterial();
		int nShininess = material.getNShininess();
		double kd = material.getKd();
		double ks = material.getKs();
		double nv = alignZero(n.dotProduct(v));
		if (nv != 0)
			for (LightSource lightSource : _scene.getLights()) {
				Vector l = lightSource.getL(geoPoint.point);
				double nl = alignZero(n.dotProduct(l));
				if (nl > 0 && nv > 0 || nl < 0 && nv < 0) {// same sign(case zero not relevant)
					double ktr = transparency(lightSource, l, n, geoPoint);
					if (ktr * k > MIN_CALC_COLOR_K) {
						Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
						color = color.add(calcDiffusive(kd, nl, lightIntensity),
								calcSpecular(ks, nl, l, n, v, nShininess, lightIntensity));
					}
				}
			}
		if (level == 1)
			return Color.BLACK;
		double kr = material.getKr(), kkr = k * kr, kMG = alignZero(material.getKMatteG());
		if (kkr > MIN_CALC_COLOR_K) {
			Ray reflectedRay = constructReflectedRay(n, geoPoint.point, inRay, nv);
			color = calcBeamColor(color, n, reflectedRay, level, kr, kkr, kMG);
		}
		double kt = material.getKt(), kkt = k * kt, kMD = alignZero(material.getKMatteD());
		if (kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(n, geoPoint.point, inRay);
			color = calcBeamColor(color, n, refractedRay, level, kt, kkt, kMD);
		}
		return color;
	}

	/**
	 * This function calculate the amount of shadow in the point somtimes we need
	 * light shadow and somtimes not
	 * 
	 * @param ls       lighsource
	 * @param l        deirection from lightsource
	 * @param n        normal vector
	 * @param geoPoint the point we wnat to check the shadow
	 * @return the amount of shadow
	 */
	private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geoPoint) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
		double dis = ls.getDistance(geoPoint.point);
		List<GeoPoint> intersections = _scene.getGeometries().findRelevantIntersections(lightRay, _scene.getBox(), true,
				dis);
		if (intersections == null)
			return 1.0;
		double ktr = 1.0;
		double kt;
		for (GeoPoint gp : intersections) {
			kt = gp.geometry.getMaterial().getKt();
			if (kt < MIN_CALC_COLOR_K)
				return 0.0;
			else
				ktr *= kt;
		}
		return ktr;
	}

	/**
	 * This function check if the point is without shadow
	 * 
	 * 
	 * @param l  vector from light to the point
	 * @param n  normal vector of the geopoint
	 * @param gp the point
	 * @return true if its unshaded
	 */
	private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource light) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
		if (intersections != null) {
			double dis = light.getDistance(gp.point);
			for (GeoPoint geoPoint : intersections) {
				if (dis > gp.point.distance(geoPoint.point) && geoPoint.geometry.getMaterial().getKt() == 0)
					return false;
			}
		}
		return true;
	}

	/**
	 * this function construct ray that is a reflaction from Camera direction
	 * 
	 * @param n     normal of the hit
	 * @param point actual point
	 * @param inRay the ray from light source
	 * @return Reflection ray
	 */
	private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay, double nv) {
		Vector v = inRay.getDir();
		Vector r = v.add(n.scale(-2 * nv));
		return new Ray(point, r, n);
	}

	/**
	 * this function construct the refracted ray from the intersection point in the
	 * same direction of camera
	 * 
	 * @param n     normal of the hit
	 * @param point actual point
	 * @param inRay the ray from light source
	 * @return refracted ray
	 */
	private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay) {
		return new Ray(point, inRay.getDir(), n);
	}

	/**
	 * This function renders image's pixel color map from the scene included with
	 * the Renderer object
	 */
	public void renderImage() {
		final int nX = _imageWriter.getNx();
		final int nY = _imageWriter.getNy();
		final double dist = _scene.getDistance();
		final double width = _imageWriter.getWidth();
		final double height = _imageWriter.getHeight();
		final Camera camera = _scene.getCamera();
		final Pixel thePixel = new Pixel(nY, nX);
		java.awt.Color background = _scene.getBackground().getColor();
		// Generate threads
		Thread[] threads = new Thread[_threads];
		for (int i = _threads - 1; i >= 0; --i) {
			threads[i] = new Thread(() -> {
				Pixel pixel = new Pixel();
				while (thePixel.nextPixel(pixel)) {
					Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, //
							dist, width, height);
					// no intersection - we color this pixel in the background color.
					// if there is intersection- we finding the closest point to the camera and
					// color only this point in its color
					GeoPoint closestPoint = findClosestIntersection(ray);
					_imageWriter.writePixel(pixel.col, pixel.row,
							closestPoint == null ? background : calcColor(closestPoint, ray).getColor());
				}
			});
		}

		// Start threads
		for (Thread thread : threads)
			thread.start();

		// Wait for all threads to finish
		for (Thread thread : threads)
			try {
				thread.join();
			} catch (Exception e) {
			}
		if (_print)
			System.out.printf("\r100%%\n");
	}

	/**
	 * Set multithreading <br>
	 * - if the parameter is 0 - number of coress less 2 is taken
	 * 
	 * @param threads number of threads
	 * @return the Render object itself
	 */
	public Render setMultithreading(int threads) {
		if (threads < 0)
			throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
		if (threads != 0)
			_threads = threads;
		else {
			int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
			if (cores <= 2)
				_threads = 1;
			else
				_threads = cores;
		}
		return this;
	}

	/**
	 * Set debug printing on
	 * 
	 * @return the Render object itself
	 */
	public Render setDebugPrint() {
		_print = true;
		return this;
	}

	/**
	 * This function is Writing the image the in the imageBuffer
	 */
	public void writeToImage() {
		_imageWriter.writeToImage();
	}

	/**
	 * This function return the closest intersection point with the ray. if there is
	 * no intersection it returns null
	 * 
	 * @param ray Ray that intersect
	 * @return geoPoint of the closest point
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> releventPoint = _scene.getGeometries().findRelevantIntersections(ray, _scene.getBox(), false,
				Double.POSITIVE_INFINITY);
		if (releventPoint == null) {
			return null;
		}
		Point3D head = ray.getP0();
		double distance, minDistance = Double.MAX_VALUE;
		GeoPoint pointToReturn = null;
		for (GeoPoint gPoint : releventPoint) {
			distance = head.distance(gPoint.point);
			if (distance < minDistance) {
				// A point with smaller distance to Camera was found
				minDistance = distance;
				pointToReturn = gPoint;
			}
		}
		return pointToReturn;
	}

	/**
	 * This function calculate the specular light according to Phong's model
	 * 
	 * @param ks             Coefficient for specual normally be 0<=ks<=1
	 * @param l              Vector from light Source to the Point
	 * @param nL             nL equal to n.dotProduct(l)
	 * @param n              The Normal to the Point
	 * @param v              The Vector from Camera to the Point
	 * @param nSh            The exponent
	 * @param lightIntensity The Light intensity
	 * @return The specular color of the point
	 */
	private Color calcSpecular(double ks, double nl, Vector l, Vector n, Vector v, int nSh, Color lightIntensity) {
		Vector r = l.add(n.scale(-2 * nl));
		double d = -alignZero(v.dotProduct(r));
		if (d <= 0)
			return Color.BLACK;
		return lightIntensity.scale(ks * Math.pow(d, nSh));
	}

	/**
	 * This function calculate the diffusive light according to Phong's model
	 * 
	 * @param kd             Coefficient for diffusive normally be 0<=kd<=1
	 * @param nL             nL equal to n.dotProduct(l)
	 * @param lightIntensity The Light intensity
	 * @return The diffusive color of the point
	 */
	private Color calcDiffusive(double kd, double nL, Color lightIntensity) {
		if (nL < 0)
			nL = -nL;
		return lightIntensity.scale(kd * nL);
	}

	/**
	 * This function writings grid for the image
	 * 
	 * @param interval Determines the density of the grid (smaller interval means A
	 *                 denser grid)
	 * @param color    The grid color
	 */
	public void printGrid(int interval, java.awt.Color color) {
		int nX = _imageWriter.getNx();
		int nY = _imageWriter.getNy();
		for (int i = 0; i < nY; i++) {
			for (int j = 0; j < nX; j++) {
				if (i % interval == 0 || j % interval == 0) {
					_imageWriter.writePixel(j, i, color);
				}
			}
		}
	}
}
