package unittests;

import org.junit.Test;
import elements.*;
import geometries.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class MiniProjectTests {

	/**
	 * Produce a picture of a few glasses with diffrent level of matte lighted by
	 * spot light
	 */
	@Test
	public void diffusedGlass() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(-900, 50, 20), new Vector(1, 0, 0.02), new Vector(-0.02, 0, 1)));
		scene.setDistance(1000);
		scene.setBackground(new Color(java.awt.Color.red));
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		// fron wall
		scene.addGeometries(new Plane(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 100),
				new Point3D(60, 0, 0), new Vector(1, 0, 0)));
		// floor
		scene.addGeometries(
				new Plane(Color.BLACK, new Material(0.5, 0.5, 100), new Point3D(0, 0, 0), new Vector(0, 0, 1)));
		// ceiling
		scene.addGeometries(
				new Plane(new Color(java.awt.Color.WHITE), new Material(0, 1, 100), new Point3D(0, 0, 400),
						new Vector(0, 0, 1)),
				new Tube(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 100), 10,
						new Ray(new Point3D(50, 0, 50), new Vector(0, 1, 0))));
		scene.addGeometries(
				new Polygon(new Color(10, 10, 10), new Material(0.5, 0.5, 100, 0.8, 0, 0.05, 0), new Point3D(0, 150, 0),
						new Point3D(0, 150, 90), new Point3D(0, 120, 90), new Point3D(0, 120, 0)),
				new Polygon(new Color(10, 10, 10), new Material(0.5, 0.5, 100, 0.8, 0, 0.15, 0), new Point3D(0, 110, 0),
						new Point3D(0, 110, 90), new Point3D(0, 80, 90), new Point3D(0, 80, 0)),
				new Polygon(new Color(10, 10, 10), new Material(0.5, 0.5, 100, 0.8, 0, 0.25, 0), new Point3D(0, 70, 0),
						new Point3D(0, 70, 90), new Point3D(0, 40, 90), new Point3D(0, 40, 0)),
				new Polygon(new Color(10, 10, 10), new Material(0.5, 0.5, 100, 0.8, 0, 0.35, 0), new Point3D(0, 30, 0),
						new Point3D(0, 30, 90), new Point3D(0, 0, 90), new Point3D(0, 0, 0)),
				new Polygon(new Color(10, 10, 10), new Material(0.5, 0.5, 100, 0.8, 0, 1, 0), new Point3D(0, -50, 0),
						new Point3D(0, -50, 90), new Point3D(0, -20, 90), new Point3D(0, -20, 0)));
		scene.addLights(
				new SpotLight(new Color(500, 500, 500), new Point3D(-400, 55, 100), new Vector(-0.1, 0, -1), 1,
						0.0000001, 0.000000001, 4),
				new SpotLight(new Color(java.awt.Color.yellow), new Point3D(-100, 55, 30), new Vector(1, 0, 0), 1,
						0.0000001, 0.0000001),
				new SpotLight(new Color(500, 500, 500), new Point3D(-600, 55, 100), new Vector(-0.1, 0, -1), 1,
						0.0000001, 0.000000001, 4));
		ImageWriter imageWriter = new ImageWriter("12", 600, 600, 500, 500);
		scene.setBox(4);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3);
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere that reflected on glossy surface
	 */
	@Test
	public void glossySurfaceTest() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(-1000, 0, 90), new Vector(1, 0, -0.09), new Vector(0.09, 0, 1)));
		scene.setDistance(1000);
		scene.setBackground(new Color(3, 3, 3));
		scene.setBackground(new Color(java.awt.Color.red));
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(new Plane(new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 100),
				new Point3D(300, 0, 0), new Vector(1, 0, 0)));
		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.green), new Material(0.5, 0.5, 100), new Point3D(50, 0, 20), 20),
				new Polygon(new Color(10, 10, 10), new Material(0.5, 0.5, 100, 0, 0.5, 0, 0.1),
						new Point3D(-700, -75, 0), new Point3D(900, -75, 0), new Point3D(900, 75, 0),
						new Point3D(-700, 75, 0)));
		scene.addLights(new DirectionalLight(new Color(255, 255, 255), new Vector(1, 0.1, 0)));
		ImageWriter imageWriter = new ImageWriter("power_s", 150, 150, 1000, 1000);
		Render render = new Render(imageWriter, scene, 200).setDebugPrint().setMultithreading(3);
		render.renderImage();
		render.writeToImage();
	}
}
