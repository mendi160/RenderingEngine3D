package files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class Xml {

	private String _path;

	public Xml(String path) {
		_path = path;
	}

	public Render getRender() {
		Render render = null;
		try {
			Element root = getXML_Root();
			if (root != null) {
				render = GetOBJ_From_XML(root);
			}
		} catch (Exception e) {
			System.out.println("A problem in the file loading");
		}
		return render;
	}

	private Render GetOBJ_From_XML(Element scene) {
		Render render = null;
		Scene sce = new Scene("scene");
		// =================read scene attributes from xml file================
		NamedNodeMap nmap = scene.getAttributes();
		double distance = Float.parseFloat(nmap.getNamedItem("screen-distance").getNodeValue());
		List<Double> l = nodeToDoubleList(nmap.getNamedItem("background-color"));
		double r = l.get(0);
		double g = l.get(1);
		double b = l.get(2);
		Color BG = new Color(r, g, b);
		sce.setBackground(BG);
		sce.setDistance(distance);
		// =================read image from xml file================
		Node imageNode = scene.getElementsByTagName("image").item(0);
		nmap = imageNode.getAttributes();
		int nX = Integer.parseInt(nmap.getNamedItem("Nx").getNodeValue());
		int nY = Integer.parseInt(nmap.getNamedItem("Ny").getNodeValue());
		double width = Float.parseFloat(nmap.getNamedItem("screen-width").getNodeValue());
		double height = Float.parseFloat(nmap.getNamedItem("screen-height").getNodeValue());
		ImageWriter imageWriter = new ImageWriter("Xml_image", width, height, nX, nY);
		// =================read camera from xml file================
		Node cameraNode = scene.getElementsByTagName("camera").item(0);
		nmap = cameraNode.getAttributes();
		Point3D p0 = nodeToPoint3D(nmap.getNamedItem("P0"));
		Vector vTo = nodeToVector(nmap.getNamedItem("Vto"));
		Vector vUp = nodeToVector(nmap.getNamedItem("Vup"));
		Camera camera = new Camera(p0, vTo, vUp);
		sce.setCamera(camera);
		// =================read ambient-light from xml file================
		Node AL = scene.getElementsByTagName("ambient-light").item(0);
		nmap = AL.getAttributes();
		l = nodeToDoubleList(nmap.getNamedItem("color"));
		Color c = new Color(l.get(0), l.get(1), l.get(2));
		AmbientLight aLight = new AmbientLight(c, 1);
		sce.setAmbientLight(aLight);
		// =================read all geometries from xml file================
		Geometries geometries = new Geometries();
		Node geoNode = scene.getElementsByTagName("geometries").item(0);
		if (geoNode != null && geoNode.getNodeType() == Node.ELEMENT_NODE) {
			Element geoElement = (Element) geoNode;
			addPolygons(geometries, geoElement);
			addTriangles(geometries, geoElement);
			addSpheres(geometries, geoElement);
			sce.addGeometries(geometries);
			render = new Render(imageWriter, sce);
		}
		return render;
	}

	private List<Double> nodeToDoubleList(Node n) {
		String[] str = n.getNodeValue().split(" ");
		List<Double> list = new ArrayList<Double>();
		for (String s : str)
			list.add(Double.parseDouble(s));
		return list;
	}

	private Vector nodeToVector(Node n) {
		List<Double> l = nodeToDoubleList(n);
		return new Vector(l.get(0), l.get(1), l.get(2));
	}

	private Point3D nodeToPoint3D(Node n) {
		try {
			return nodeToVector(n).getHeadPoint();
		} catch (IllegalArgumentException e) {
			// point (0,0,0)
			return Point3D.ZERO;
		}
	}

	private void addPolygonsOrTriangles(Geometries g, Element e, String type) {
		NodeList polygonList = e.getElementsByTagName(type);
		NamedNodeMap nmap;
		if (polygonList.getLength() > 0) {
			for (int i = 0; i < polygonList.getLength(); i++) {
				nmap = polygonList.item(i).getAttributes();
				Node[] nodes = new Node[nmap.getLength()];
				for (int j = 0; j < nmap.getLength(); j++) {
					nodes[j] = nmap.getNamedItem("p" + j);
				}
				g.add(nodeToPolygonOrTriangle(type, nodes));
			}
		}
	}

	private void addPolygons(Geometries g, Element e) {
		addPolygonsOrTriangles(g, e, "polygon");
	}

	private void addTriangles(Geometries g, Element e) {
		addPolygonsOrTriangles(g, e, "triangle");
	}

	private void addSpheres(Geometries g, Element e) {
		NodeList sphereList = e.getElementsByTagName("sphere");
		NamedNodeMap nmap;
		Point3D p0;
		if (sphereList.getLength() > 0) {
			double radius;
			for (int i = 0; i < sphereList.getLength(); i++) {
				nmap = sphereList.item(i).getAttributes();
				p0 = nodeToPoint3D(nmap.getNamedItem("center"));
				radius = Double.parseDouble(nmap.getNamedItem("radius").getNodeValue());
				g.add(new Sphere(p0, radius));
			}
		}
	}

	private Polygon nodeToPolygonOrTriangle(String type, Node... nodes) {
		int size = nodes.length;
		Point3D[] listPoint = new Point3D[size];
		for (int i = 0; i < size; ++i) {
			listPoint[i] = (nodeToPoint3D(nodes[i]));
		}
		if (type == "polygon")
			return new Polygon(listPoint);
		return new Triangle(listPoint[0], listPoint[1], listPoint[2]);
	}

	private Element getXML_Root() throws Exception {
		File xmlDoc = new File(_path);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(xmlDoc);
		document.getDocumentElement().normalize();
		return document.getDocumentElement();
	}
}