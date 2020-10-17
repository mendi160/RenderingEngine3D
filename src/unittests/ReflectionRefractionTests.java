/**
 * 
 */
package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 *
 */
public class ReflectionRefractionTests {

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));
		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0, 0.5, 0),
						new Point3D(0, 0, 50), 50),
				new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), new Point3D(0, 0, 50), 25));
		scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
				0.0004, 0.0000006));
		ImageWriter imageWriter = new ImageWriter("twoSpheres_s", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene, 10).setDebugPrint().setMultithreading(3);
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(10000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
		scene.addGeometries(
				new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0.5, 0), new Point3D(-950, 900, 1000),
						400),
				new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20), new Point3D(-950, 900, 1000), 200),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));
		scene.addLights(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, 750, 150), new Vector(-1, 1, 4), 1,
				0.00001, 0.000005));
		ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored_s", 2500, 2500, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
		scene.addGeometries( //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), new Point3D(60, -50, 50),
						30));
		scene.addLights(new SpotLight(new Color(700, 400, 400), //
				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));
		ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);// .setBox(4);
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two Polygons(as mirrors), four planes, two spheres,two
	 * tubes lighted by a point light with transparent Sphere producing partial
	 * shadow
	 */
	@Test
	public void ourTest() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(-100, 40, 0), new Vector(1, -0.4235, 0), new Vector(0, 0, 1)));
		scene.setDistance(100);
		scene.setBackground(new Color(java.awt.Color.ORANGE));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.05));
		scene.addGeometries( //
				new Plane(new Color(102, 51, 0), new Material(0.5, 0.5, 100, 0, 0.05), new Point3D(0, 0, -30),
						new Vector(0, 0, 1)),
				new Plane(new Color(java.awt.Color.DARK_GRAY), new Material(0.5, 0.5, 100, 0, 0), new Point3D(0, 50, 0),
						new Vector(0, -1, 0)), //
				new Plane(new Color(java.awt.Color.DARK_GRAY), new Material(0.5, 0.5, 100, 0, 0),
						new Point3D(0, -50, 0), new Vector(0, 1, 0)),
				new Plane(new Color(java.awt.Color.DARK_GRAY), new Material(0.5, 0.5, 100, 0, 0),
						new Point3D(200, 0, 0), new Vector(1, 0, 0)), //
				new Plane(new Color(java.awt.Color.DARK_GRAY), new Material(0.5, 0.5, 100, 0, 0), new Point3D(0, 0, 70),
						new Vector(0, 0, -1)),
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100, 0.7, 0),
						new Point3D(150, 0, -15), 15),
				new Sphere(new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 100, 0, 0.7),
						new Point3D(150, 0, -15), 5),
				new Polygon(new Color(20, 20, 20), new Material(0.5, 0.5, 100, 0, 0.7), new Point3D(199, -48, 0),
						new Point3D(-200, -48, 0), new Point3D(-200, -48, -29), new Point3D(199, -48, -29)),
				new Polygon(new Color(20, 20, 20), new Material(0.5, 0.5, 100, 0, 0.7), new Point3D(199, 48, 0),
						new Point3D(-200, 48, 0), new Point3D(-200, 48, -29), new Point3D(199, 48, -29)),
				new Cylinder(Color.BLACK, new Material(1, 1, 100, 1, 0), 2,
						new Ray(new Point3D(50, 0, 65), new Vector(0, 0, 1)), 5),
				new Tube(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 300, 0, 0.2), 1,
						new Ray(new Point3D(0, -49, 69), new Vector(1, 0, 0))),
				new Tube(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 300, 0, 0.2), 1,
						new Ray(new Point3D(0, 49, 69), new Vector(1, 0, 0))));

		scene.addLights(new PointLight(new Color(150, 150, 150), //
				new Point3D(50, 0, 65), 1, 4E-5, 2E-7));
		ImageWriter imageWriter = new ImageWriter("ourTest", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two Polygons, a triangle,two spheres,a tube, 4
	 * cylinders lighted by a 2 point lights and 3 spotlighs with transparent Sphere
	 * producing partial shadow
	 */
	@Test
	public void bonusTest() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(-1000, 0, 80), new Vector(1, 0, -0.02), new Vector(0.02, 0, 1)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.05));

		scene.addGeometries(new Polygon(new Color(20, 20, 20), new Material(0.5, 0.5, 100, 0, 0.7),
				new Point3D(0, 90, -30), new Point3D(0, 90, 0), new Point3D(0, -90, 0), new Point3D(0, -90, -30)),
				new Tube(new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 300, 0, 0), 200,
						new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0))),
				new Polygon(new Color(20, 20, 20), new Material(0.5, 0.5, 100, 0, 0.4), new Point3D(0, 90, 0),
						new Point3D(200, 90, 0), new Point3D(200, -90, 0), new Point3D(0, -90, 0)),
				new Triangle(new Color(40, 40, 40), new Material(0.5, 0.5, 60, 0.8, 0,0.1,0), //
						new Point3D(100, 92, 118), new Point3D(100, 92, 0), new Point3D(100, -92, 118)), //
				new Cylinder(new Color(0, 0, 102), new Material(1, 1, 100, 0, 0.8), 2,
						new Ray(new Point3D(100, 92, -30), new Vector(0, 0, 1)), 150),
				new Cylinder(new Color(0, 0, 102), new Material(1, 1, 100, 0, 0.8), 2,
						new Ray(new Point3D(100, -92, -30), new Vector(0, 0, 1)), 150),
				new Cylinder(new Color(0, 0, 102), new Material(1, 1, 100, 0, 0.8), 2,
						new Ray(new Point3D(100, -94, 121), new Vector(0, 1, 0)), 188),
				new Cylinder(new Color(java.awt.Color.DARK_GRAY), new Material(1, 1, 100), 1,
						new Ray(new Point3D(100, 0, 121), new Vector(0, 0, -1)), 25),
				new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100, 0.7, 0), new Point3D(100, 0, 90),
						10),
				new Sphere(new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 100, 0, 0.3),
						new Point3D(100, 0, 10), 20));
		scene.addLights(
				new SpotLight(new Color(255, 255, 255), new Point3D(100, -80, 100), new Vector(-1, 0.9, -1.2), 1,
						0.00001, 0.0001),
				new SpotLight(new Color(255, 255, 255), new Point3D(100, 80, 100), new Vector(-1, -0.9, -1.2), 1,
						0.00001, 0.0001),
				new SpotLight(new Color(400, 400, 400), new Point3D(-10, 85, -28), new Vector(1, -1, 1), 1, 0.000000001,
						0.000000001),
				new SpotLight(new Color(255, 255, 255), new Point3D(200, 0, 42.369), new Vector(1, 0, 1), 1,
						0.0000000001, 0.0000000001),
				new PointLight(new Color(255, 255, 255), new Point3D(100, 0, 90), 1, 0.0001, 0.00001),
				new PointLight(new Color(255, 255, 255), new Point3D(2500, 0, 90), 1, 0.0001, 0.00001));
		//scene.setBox(4);
		ImageWriter imageWriter = new ImageWriter("bonusImageHD", 200, 200, 1800, 1800);
		Render render = new Render(imageWriter, scene,80).setDebugPrint();
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * diffrent angle Produce a picture of a two Polygons, a triangle,two spheres,a
	 * tube, 4 cylinders lighted by a 2 point lights and 3 spotlighs with
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void bonusTestDiffAngle() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(200, -120, 150), new Vector(-1, 1, -0.5), new Vector(0, 0.5, 1)));
		scene.setDistance(120);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.05));

		scene.addGeometries(
				new Polygon(new Color(20, 20, 20), new Material(0.5, 0.5, 100, 0, 0.7), new Point3D(0, 90, -30),
						new Point3D(0, 90, 0), new Point3D(0, -90, 0), new Point3D(0, -90, -30)),
				new Tube(new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 300, 0, 0), 200,
						new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0))),
				new Polygon(new Color(20, 20, 20), new Material(0.5, 0.5, 100, 0, 0.4), new Point3D(0, 90, 0),
						new Point3D(200, 90, 0), new Point3D(200, -90, 0), new Point3D(0, -90, 0)),
				new Triangle(new Color(40, 40, 40), new Material(0.5, 0.5, 60, 0.8, 0,0.1,0), //
						new Point3D(100, 92, 118), new Point3D(100, 92, 0), new Point3D(100, -92, 118)), //
				new Cylinder(new Color(0, 0, 102), new Material(1, 1, 100, 0, 0.8), 2,
						new Ray(new Point3D(100, 92, -30), new Vector(0, 0, 1)), 150),
				new Cylinder(new Color(0, 0, 102), new Material(1, 1, 100, 0, 0.8), 2,
						new Ray(new Point3D(100, -92, -30), new Vector(0, 0, 1)), 150),
				new Cylinder(new Color(0, 0, 102), new Material(1, 1, 100, 0, 0.8), 2,
						new Ray(new Point3D(100, -94, 121), new Vector(0, 1, 0)), 188),
				new Cylinder(new Color(java.awt.Color.DARK_GRAY), new Material(1, 1, 100), 1,
						new Ray(new Point3D(100, 0, 121), new Vector(0, 0, -1)), 25),
				new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100, 0.7, 0), new Point3D(100, 0, 90),
						10),
				new Sphere(new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 100, 0, 0.3),
						new Point3D(100, 0, 10), 20));
		scene.addLights(
				new SpotLight(new Color(255, 255, 255), new Point3D(100, -80, 100), new Vector(-1, 0.9, -1.2), 1,
						0.00001, 0.0001),
				new SpotLight(new Color(255, 255, 255), new Point3D(100, 80, 100), new Vector(-1, -0.9, -1.2), 1,
						0.00001, 0.0001),
				new SpotLight(new Color(400, 400, 400), new Point3D(-10, 85, -28), new Vector(1, -1, 1), 1, 0.000000001,
						0.000000001),
				new SpotLight(new Color(255, 255, 255), new Point3D(200, 0, 42.369), new Vector(1, 0, 1), 1,
						0.0000000001, 0.0000000001),
				new PointLight(new Color(255, 255, 255), new Point3D(100, 0, 90), 1, 0.0001, 0.00001),
				new PointLight(new Color(255, 0, 255), new Point3D(2500, 0, 90), 1, 0.0001, 0.00001));
		ImageWriter imageWriter = new ImageWriter("bonus_image01HD", 200, 200, 1800, 1800);
		Render render = new Render(imageWriter, scene,80).setDebugPrint().setMultithreading(3);
		render.renderImage();
		render.writeToImage();
	}
}
