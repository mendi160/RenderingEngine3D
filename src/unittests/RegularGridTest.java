package unittests;

import java.util.List;
import java.util.Random;
import org.junit.Test;
import elements.*;
import geometries.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class RegularGridTest {
	/**
	 * test that create spheres
	 */
	@Test
	public void createSpheres() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1)));
		scene.setDistance(500);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));
		Random rand = new Random();
		final int NUM = 1000, MOVE = 50;
		Geometries geometries = new Geometries();
		for (int i = 1; i <= NUM; ++i) {
			geometries.add(new Sphere(
					new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255)),
					new Material(0.4, 0.7, 100), new Point3D(i * 100 - 200, -MOVE * i, MOVE * i / 2), 50));
		}
		for (int i = NUM; i >= 0; --i) {
			geometries.add(new Sphere(
					new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255)),
					new Material(0.4, 0.7, 100), new Point3D(i * 100 - 200, MOVE * i, MOVE * i / 2), 50));
		}
		for (int i = 1; i <= NUM; ++i) {
			geometries.add(new Sphere(
					new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255)),
					new Material(0.4, 0.7, 100, 0, 0), new Point3D(i * 100 - 200, -MOVE * i, -MOVE * i / 2), 50));
		}
		for (int i = NUM; i > 1; i--) {
			geometries.add(new Sphere(
					new Color(Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255)),
					new Material(0.7, 0.3, 45), new Point3D(i * 100, MOVE * i, -MOVE * i / 2), 50));
		}

		scene.addGeometries(geometries);
		scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 0)),
				new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));
		ImageWriter imageWriter = new ImageWriter("withoutBox", 14000, 14000, 2000, 2000);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3);// .setBox(4);
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * tets with five sphere
	 */
	@Test
	public void testSetMap() {
		Scene scene = new Scene("test2_1");
		scene.setCamera(new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));
		Sphere sphere = new Sphere(new Color(100, 100, 100), new Material(0.5, 0.5, 100, 0, 0.5),
				new Point3D(100, 100, 100), 50);
		scene.addGeometries(sphere);
		Sphere spheree = new Sphere(new Color(150, 150, 150), new Material(0.5, 0.5, 100, 0, 0.5),
				new Point3D(100, -100, 100), 50);
		scene.addGeometries(spheree);
		Sphere sphere1 = new Sphere(new Color(100, 100, 0), new Material(0.5, 0.5, 100, 0, 0.5),
				new Point3D(100, 100, -100), 50);
		scene.addGeometries(sphere1);
		Sphere spheree2 = new Sphere(new Color(100, 0, 100), new Material(0.5, 0.5, 100, 0, 0.5),
				new Point3D(100, -100, -100), 50);
		scene.addGeometries(spheree2);
		Sphere spheree3 = new Sphere(new Color(100, 0, 100), new Material(0.5, 0.5, 100, 0, 0.5),
				new Point3D(100, 0, 0), 50);
		scene.addGeometries(spheree3);
		scene.addLights(new DirectionalLight(new Color(400, 235, 486), new Vector(1, 0, 0)));
		scene.setBox(4);
		ImageWriter imageWriter = new ImageWriter("rr", 300, 300, 500, 500);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3);
		render.renderImage();
		render.writeToImage();
	}

	@Test
	/**
	 * test that create thousands spheres
	 */
	public void test() {
		Camera camera = new Camera(new Point3D(0, 0, 150), new Vector(0, -1, 0), new Vector(0, 0, -1));
		Scene scene = new Scene("test2_1");
		SpotLight spot = new SpotLight(new Color(100, 100, 100), new Point3D(100, 0, 0),
				new Vector(-100, 0, -250).normalize(), 1, 2, 0.01);
		PointLight pointLight = new PointLight(new Color(77, 0, 0), new Point3D(200, -200, -100), 1, 0.01, 0.01);
		DirectionalLight directionalLight = new DirectionalLight(new Color(1, 1, 0), new Vector(1, 0, 0));

		for (int i = 0; i < 150; i++) {
			for (int j = 0; j < 150; j++) {
				Sphere sphere = new Sphere(new Color(100 * (i % 2), 100 * (i % 2 + 1), 0),
						new Material(500, 20, 100, 0, 0), new Point3D(800 - 25 * i, 800 - 25 * j, -300), 10);
				scene.addGeometries(sphere);
			}
		}
		scene.setAmbientLight(new AmbientLight(new Color(130, 130, 130), 0.1));
		scene.addLights(spot);
		scene.addLights(pointLight);
		scene.addLights(directionalLight);
		scene.setBackground(new Color(0, 0, 0));
		scene.setDistance(149);
		scene.setCamera(camera);
		ImageWriter imageWriter = new ImageWriter("test grid thousands sphere", 500, 500, 1000, 1000);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3);// .setBox(4);
		render.renderImage();
		render.writeToImage();
	}

	@Test
	/**
	 * test that create thousands geometries(spheres, polygons, cylinders)
	 */
	public void testForDanTheMaster() {
		Scene scene = new Scene("test2_1");
		scene.setCamera(new Camera(new Point3D(150, 0, 250), new Vector(1, 0, -0.1), new Vector(0.1, 0, 1)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		final int NUM = 100;
		Geometries geometries = new Geometries();
		// floor
		for (int i = 1; i <= NUM - 50; ++i) {
			geometries.add(new Cylinder(new Color(java.awt.Color.darkGray), new Material(0.4, 0.7, 100), 10,
					new Ray(new Point3D(0, i * 20 - 500, -300), new Vector(1, 0, 0)), 1800));
		}
		// wall back buttom
		for (int i = 1; i <= NUM - 40; ++i) {
			geometries.add(new Cylinder(new Color(java.awt.Color.darkGray), new Material(0.4, 0.7, 100), 10,
					new Ray(new Point3D(1700, i * 20 - 600, -300), new Vector(0, 0, 1)), 400));
		}
		// wall back top right
		for (int i = 1; i <= NUM - 79; ++i) {
			geometries.add(new Cylinder(new Color(java.awt.Color.darkGray), new Material(0.4, 0.7, 100), 10,
					new Ray(new Point3D(1700, i * 20 - 440, 100), new Vector(0, 0, 1)), i * 20));
		}
		// wall back top left
		for (int i = 1; i <= NUM - 78; ++i) {
			geometries.add(new Cylinder(new Color(java.awt.Color.darkGray), new Material(0.4, 0.7, 100), 10,
					new Ray(new Point3D(1700, i * 20 - 20, 100), new Vector(0, 0, 1)), 420 - i * 18));
		}
		// wall right
		for (int i = 1; i <= NUM; ++i) {
			geometries.add(new Cylinder(new Color(java.awt.Color.darkGray), new Material(0.4, 0.7, 100), 10,
					new Ray(new Point3D(i * 20, -500, -300), new Vector(0, 0, 1)), 400));
		}
		// wall left
		for (int i = 1; i <= NUM; ++i) {
			geometries.add(new Cylinder(new Color(java.awt.Color.darkGray), new Material(0.4, 0.7, 100), 10,
					new Ray(new Point3D(i * 20, 500, -300), new Vector(0, 0, 1)), 400));
		}
		// roof right
		for (int i = 1; i <= NUM; ++i) {
			geometries.add(new Cylinder(new Color(java.awt.Color.darkGray), new Material(0.4, 0.7, 100), 10,
					new Ray(new Point3D(i * 20, -500, 100), new Vector(0, 1.5, 1)), 600));
		}
		// roof left
		for (int i = 1; i <= NUM; ++i) {
			geometries.add(new Cylinder(new Color(java.awt.Color.darkGray), new Material(0.4, 0.7, 100, 0.3, 0), 10,
					new Ray(new Point3D(i * 20, 500, 100), new Vector(0, -1.5, 1)), 600));
		}
		// mirrors
		for (int i = 0; i <= 15; ++i)
			for (int j = 1; j <= 44; ++j) {
				geometries.add(new Polygon(Color.BLACK, new Material(0.5, 0.5, 100, 0.5, 0, 0.11 * (i % 2), 0),
						new Point3D(0, 520 - j * 19, -300 + i * 25), new Point3D(0, 520 - j * 19, -250 + i * 25),
						new Point3D(0, 350 - j * 19, -250 + i * 25), new Point3D(0, 350 - j * 19, -300 + i * 25)));
			}
		// chandelier
		geometries.add((new Cylinder(Color.BLACK, new Material(0.3, 0.7, 100), 3,
				new Ray(new Point3D(400, 0, 700), new Vector(0, 0, -1)), 450)));
		Ray mainRay = new Ray(new Point3D(400, 0, 250), new Vector(0, 0, -1));
		List<Ray> rays = mainRay.generateBeam(new Vector(0, 0, -1), 150, 100, 500);
		System.out.println(rays.size());
		Point3D randomPoint;
		for (Ray ray : rays) {
			geometries.add((new Cylinder(Color.BLACK, new Material(0.5, 0.5, 100, 0, 0), 1, ray, 96)));
			randomPoint = ray.getPoint(100);
			Sphere spheree2 = new Sphere(new Color(java.awt.Color.GRAY), new Material(1, 0, 100, 0.7, 0), randomPoint,
					8);
			geometries.add(spheree2);
		}
		System.out.println(geometries.getShapes().size());
		scene.addGeometries(geometries);
		scene.setAmbientLight(new AmbientLight(new Color(0, 0, 0), 0));
//		scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 0)),
//				new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));
		scene.addLights(new PointLight(new Color(200, 110, 13), new Point3D(200, 0, 200), 1, 0.0000001, 0.000001));
		scene.addLights(new PointLight(new Color(200, 110, 13), new Point3D(700, 0, 200), 1, 0.0000001, 0.000001));
		scene.addLights(new PointLight(new Color(200, 110, 13), new Point3D(1200, 0, 200), 1, 0.0000001, 0.000001));
		scene.addLights(new SpotLight(new Color(400, 684, 364), new Point3D(40, 0, 150), new Vector(0, 0, -1), 1,
				0.0000001, 0.00000001, 10));
		scene.setBox(4);
		ImageWriter imageWriter = new ImageWriter("FinalPictureWithBoxCh", 1800, 1500, 1900, 1900);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3);
		render.renderImage();
		render.writeToImage();
	}
}
