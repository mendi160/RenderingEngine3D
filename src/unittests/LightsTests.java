package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering abasic image
 * 
 * @author Dan
 */
public class LightsTests {

	/**
	 * Produce a picture of a sphere lighted by a directional light
	 */
	@Test
	public void sphereDirectional() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), new Point3D(0, 0, 50), 50));

		scene.addLights(new DirectionalLight(new Color(500, 300, 0), new Vector(1, -1, 1)));

		ImageWriter imageWriter = new ImageWriter("sphereDirectional", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a point light
	 */
	@Test
	public void spherePoint() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), new Point3D(0, 0, 50), 50));

		scene.addLights(new PointLight(new Color(500, 300, 0), new Point3D(-50, 50, -50), 1, 0.00001, 0.000001));

		ImageWriter imageWriter = new ImageWriter("spherePoint", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void sphereSpot() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), new Point3D(0, 0, 50), 50));

		scene.addLights(new SpotLight(new Color(500, 300, 0), new Point3D(-50, 50, -50), new Vector(1, -1, 2), 1,
				0.00001, 0.00000001));

		ImageWriter imageWriter = new ImageWriter("sphereSpot", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a narrow spot light
	 */
	@Test
	public void sphereNarrowSpot() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), new Point3D(0, 0, 50), 50));

		scene.addLights(new SpotLight(new Color(500, 300, 0), new Point3D(-50, 50, -50), new Vector(1, -1, 2), 1,
				0.00001, 0.00000001,6));

		ImageWriter imageWriter = new ImageWriter("sphereNSpot", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}
	/**
	 * Produce a picture of a sphere lighted by a all kindes lights
	 */
	@Test
	public void sphereAllLights() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.RED), 0.1));

		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 300), new Point3D(0, 0, 50), 50));
		scene.addLights(new DirectionalLight(new Color(153, 0, 76), new Vector(5, 1, 0)));
		scene.addLights(
				new SpotLight(new Color(0, 255, 0), new Point3D(0, -30, 0), new Vector(0, 1, 1), 1, 0.00001, 0.000001));
		scene.addLights(new PointLight(new Color(500, 300, 0), new Point3D(50, 50, -50), 1, 0.001, 0.0001));
		ImageWriter imageWriter = new ImageWriter("sphereAllLights", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

//	@Test
//	public void YoniTest() {
//		Scene scene = new Scene("Test scene");
//		scene.setCamera(new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0,0), new Vector(0, 0,1)));
//		scene.setDistance(1000);
//		scene.setBackground(new Color(3,3,3));
//		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));
//
//		//scene.addGeometries(
//				//new Cylinder(new Color(java.awt.Color.cyan), new Material(0.5, 0.5, 100),10, new Ray(new Point3D(50,-25,0),new Vector(0,0,1)), 90));
//		scene.addGeometries(
//				new Plane(new Color(java.awt.Color.lightGray), new Material(0.5, 0.5, 100), new Point3D(70, 0, 0),new Vector (1,0,0)));
//		scene.addGeometries(
//				new Sphere(new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 100), new Point3D(50, 0, 20), 20));
//		scene.addGeometries(
//				new Sphere(new Color(java.awt.Color.GREEN), new Material(0.5, 0.5, 100), new Point3D(50, 40, 20), 20));
//		scene.addGeometries(
//				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100), new Point3D(50, -40, 20), 20));
//		scene.addGeometries(new Plane(new Color(0,0,0), new Material(0, 1, 100), new Point3D(0, 0, -3),new Vector (0,0,1)));
//		scene.addLights(new DirectionalLight(new Color(100, 30, 0), new Vector(1, -1, 1)));
//		//scene.addLights(new SpotLight(new Color(java.awt.Color.ORANGE), new Point3D(50, 0, 50), new Vector(0, 0, -1), 1,
//			//	0.00001, 0.00000001));
//		scene.addLights(new SpotLight(new Color(255, 255, 255), new Point3D(40, 0, 50), new Vector(0, 0, -1), 1,
//				0.00001, 0.00000001));
//	scene.addLights(new SpotLight(new Color(350, 300, 0), new Point3D(20, -40, 20), new Vector(1, 0, 0), 1,
//		0.00001, 0.00000001));
//		//scene.addLights(new SpotLight(new Color(java.awt.Color.BLACK), new Point3D(20, 0, 50), new Vector(0, 0, -1), 1,
//		//		0.00001, 0.00000001));
//		scene.addLights(new PointLight(new Color(0, 0, 255), new Point3D(5, 0, 5), 1, 0.00001, 0.000001));
//		ImageWriter imageWriter = new ImageWriter("zzzzz", 150, 150, 1920, 1080);
//		Render render = new Render(imageWriter, scene);
//
//		render.renderImage();
//		render.writeToImage();
//	}

	@Test
	public void cylinderAllLights() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(400);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(10, 30, 30), 0.5));

		scene.addGeometries(new Cylinder(new Color(205, 133, 63), new Material(0.5, 0.5, 100), 30,
				new Ray(new Point3D(0, -20, 0), new Vector(1, 1, 1)), 200));
		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.PINK), new Material(0.5, 0.5, 100), new Point3D(0, -100, -80), 50));
		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.PINK), new Material(0.5, 0.5, 100), new Point3D(0, 100, -80), 50));
		scene.addLights(new DirectionalLight(new Color(100, 30, 0), new Vector(1, -1, 1)));
		scene.addLights(new SpotLight(new Color(350, 300, 0), new Point3D(0, -90, -120), new Vector(0, 0, 1), 1,
				0.00001, 0.00000001));
		scene.addLights(new PointLight(new Color(500, 300, 0), new Point3D(20, 0, 10), 1, 0.001, 0.0001));
		ImageWriter imageWriter = new ImageWriter("cylinderAllLights", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of two triangles lighted by a directional light
	 */
	@Test
	public void trianglesDirectional() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries(
				new Triangle(Color.BLACK, new Material(0.8, 0.2, 300), new Point3D(-150, 150, 150),
						new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
				new Triangle(Color.BLACK, new Material(0.8, 0.2, 300), new Point3D(-150, 150, 150),
						new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

		scene.addLights(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, 1)));

		ImageWriter imageWriter = new ImageWriter("trianglesDirectional", 200, 200, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of two triangles lighted by a point light
	 */
	@Test
	public void trianglesPoint() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries(
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 300), new Point3D(-150, 150, 150),
						new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 300), new Point3D(-150, 150, 150),
						new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

		scene.addLights(new PointLight(new Color(500, 250, 250), new Point3D(10, 10, 130), 1, 0.0005, 0.0005));

		ImageWriter imageWriter = new ImageWriter("trianglesPoint", 200, 200, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light
	 */
	@Test
	public void trianglesSpot() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries(
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 300), new Point3D(-150, 150, 150),
						new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 300), new Point3D(-150, 150, 150),
						new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

		scene.addLights(new SpotLight(new Color(500, 250, 250), new Point3D(10, 10, 130), new Vector(-2, 2, 1), 1,
				0.0001, 0.000005));

		ImageWriter imageWriter = new ImageWriter("trianglesSpot", 200, 200, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of two triangles lighted by a narrow spot light
	 */
	@Test
	public void trianglesNarrowSpot() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries(
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 300), new Point3D(-150, 150, 150),
						new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 300), new Point3D(-150, 150, 150),
						new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

		scene.addLights(new SpotLight(new Color(500, 250, 250), new Point3D(10, 10, 130), new Vector(-2, 2, 1), 1,
				0.0001, 0.000005, 4));

		ImageWriter imageWriter = new ImageWriter("trianglesNSpot", 200, 200, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of two triangles lighted by a all kindes lights
	 */
	@Test
	public void trianglesAllLights() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries(
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 100), new Point3D(-150, 150, 150),
						new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 100), new Point3D(-150, 150, 150),
						new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

		scene.addLights(new SpotLight(new Color(500, 250, 250), new Point3D(-40, 40, 130), new Vector(-2, 2, 1), 1,
				0.0001, 0.000005));
		scene.addLights(new PointLight(new Color(0, 0, 250), new Point3D(60, 0, 130), 1, 0.0005, 0.0005));
		scene.addLights(new DirectionalLight(new Color(20, 20, 20), new Vector(0, 0, 1)));
		ImageWriter imageWriter = new ImageWriter("trianglesAllLights", 200, 200, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

}
