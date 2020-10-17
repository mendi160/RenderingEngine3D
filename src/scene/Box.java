package scene;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import static primitives.Util.*;

/**
 * This Class represent a AABB(Axis Aligned Bounding Box) this Box contain the
 * whole scene and we divide this box into grid . each grid cell is a Voxel and
 * each voxel contains the geometries that should be in this cell we map all
 * geometries into hashmap <Voxel,Geoemetries>
 * 
 * @author Yehonatan Eliyahu and Mendi Shneorson
 *
 */
public class Box {
	private int _boxDensity;
	private static double _minX, _minY, _minZ;
	private static double _maxX, _maxY, _maxZ;
	private static double _voxelSizeX, _voxelSizeY, _voxelSizeZ;
	private Map<Voxel, Geometries> _map;

	/**
	 * Constructor takes the geometries in the scene set minimum value and maximum
	 * 
	 * @param lambda     Value for set optimize density of the box
	 * @param geometries The geometries in the scene
	 */
	public Box(int lambda, Geometries geometries) {
		setMinBox(geometries.getMinCoordinates());
		setMaxBox(geometries.getMaxCoordinates());
		setDensity(geometries.getShapes().size(), lambda);
		setVoxelSize();
		SetMap(geometries);
	}
	// ---------------------------------------Setters--------------------------------------------------------------

	/**
	 * This function calculate the box density using special formula
	 * 
	 * @param numGeometries number of geometries in the scene
	 * @param lambda        Lambda
	 */
	private void setDensity(int numGeometries, int lambda) {
		double boxVolume = (_maxX - _minX) * (_maxY - _minY) * (_maxZ - _minZ);
		double averageDimensionSize = ((_maxX - _minX) + (_maxY - _minY) + (_maxZ - _minZ)) / 3;
		_boxDensity = (int) (averageDimensionSize * Math.pow((lambda * numGeometries) / boxVolume, 1 / 3d));
	}

	/**
	 * This function set the minimum boundery of the Box
	 * 
	 * @param min minmum x y z possible in scene
	 */

	private void setMinBox(Point3D min) {
		_minX = min.getX().get();
		_minY = min.getY().get();
		_minZ = min.getZ().get();
	}

	/**
	 * This function set the max boundery of the Box
	 * 
	 * @param max maximum x y z possible in scene
	 */
	private void setMaxBox(Point3D max) {
		_maxX = max.getX().get();
		_maxY = max.getY().get();
		_maxZ = max.getZ().get();
	}

	/**
	 * This function calculate the grid size for each axis
	 */
	private void setVoxelSize() {
		_voxelSizeX = (_maxX - _minX) / _boxDensity;
		_voxelSizeY = (_maxY - _minY) / _boxDensity;
		_voxelSizeZ = (_maxZ - _minZ) / _boxDensity;
	}

	/**
	 * This function mapping the geometries into the correct Voxels/grid Cells
	 * 
	 * @param geometries all geometries in the Scene
	 */
	private void SetMap(Geometries geometries) {
		_map = new HashMap<Voxel, Geometries>();
		int[] minIndexs, maxIndexs;
		Voxel voxel;
		for (Intersectable geometry : geometries.getShapes()) {
			minIndexs = getMInIndexs(geometry.getMinCoordinates());
			maxIndexs = getMaxIndexs(geometry.getMaxCoordinates());
			for (int x = minIndexs[0]; x <= maxIndexs[0]; x++)
				for (int y = minIndexs[1]; y <= maxIndexs[1]; y++)
					for (int z = minIndexs[2]; z <= maxIndexs[2]; z++) {
						voxel = new Voxel(x, y, z);
						if (_map.containsKey(voxel))
							_map.get(voxel).add(geometry);
						else {
							Geometries g = new Geometries(geometry);
							_map.put(voxel, g);
						}
					}
		}
	}

	// ---------------------------------operations-----------------------------------
	/**
	 * This function return the maximum indexses for mapping the geometry into
	 * grid/voxel for specific point
	 * 
	 * @param max the maximum point of the geometry
	 * @return the maximum indexes
	 */
	private int[] getMaxIndexs(Point3D max) {
		int[] maxIndexs = new int[3];
		maxIndexs[0] = (int) ((max.getX().get() - _minX) / _voxelSizeX);
		maxIndexs[1] = (int) ((max.getY().get() - _minY) / _voxelSizeY);
		maxIndexs[2] = (int) ((max.getZ().get() - _minZ) / _voxelSizeZ);
		return maxIndexs;
	}

	/**
	 * This function return the minimum indexses for mapping the geometry into
	 * grid/voxel for specific point
	 * 
	 * @param min the minimum point of the geometry
	 * @return the minimum indexes
	 */
	private int[] getMInIndexs(Point3D min) {
		int[] minIndexs = new int[3];
		minIndexs[0] = (int) ((min.getX().get() - _minX) / _voxelSizeX);
		minIndexs[1] = (int) ((min.getY().get() - _minY) / _voxelSizeY);
		minIndexs[2] = (int) ((min.getZ().get() - _minZ) / _voxelSizeZ);
		return minIndexs;
	}

	/**
	 * Getter of map
	 * 
	 * @return Map
	 */
	public Map<Voxel, Geometries> getMap() {
		return _map;
	}

	/**
	 * Getter
	 * 
	 * @return The number of density
	 */
	public int getDensity() {
		return _boxDensity;
	}

	/**
	 * This Function get Ray and check if its intersect our bounding Box . if it
	 * intersect we retrun new ray that starrt at the intersection point else return
	 * Null * @param ray
	 * 
	 * @return Ray the start in the intersection point / null
	 */
	private Ray checkIntersection(Ray ray) {
		double minTX = 0, minTY = 0, minTZ = 0;
		double maxTX = Double.POSITIVE_INFINITY, maxTY = maxTX, maxTZ = maxTX;
		Vector v = ray.getDir();
		Point3D headV = v.getHeadPoint();
		Point3D originRay = ray.getP0();
		double rayX = alignZero(headV.getX().get());
		double rayY = alignZero(headV.getY().get());
		double rayZ = alignZero(headV.getZ().get());
		double rayPX = alignZero(originRay.getX().get());
		double rayPY = alignZero(originRay.getY().get());
		double rayPZ = alignZero(originRay.getZ().get());

		if (rayX == 0 && (rayPX > _maxX || rayPX < _minX))
			return null;
		if (rayX > 0) {
			if (_maxX < rayPX)
				return null;
			maxTX = (_maxX - rayPX) / rayX;
			minTX = Double.max(0, (_minX - rayPX) / rayX);
		}
		if (rayX < 0) {
			if (_minX > rayPX)
				return null;
			maxTX = (_minX - rayPX) / rayX;
			minTX = Double.max(0, (_maxX - rayPX) / rayX);
		}

		if (rayY == 0 && (rayPY > _maxY || rayPY < _minY))
			return null;
		if (rayY > 0) {
			if (_maxY < rayPY)
				return null;
			maxTY = (_maxY - rayPY) / rayY;
			minTY = Double.max(0, (_minY - rayPY) / rayY);
		}
		if (rayY < 0) {
			if (_minY > rayPY)
				return null;
			maxTY = (_minY - rayPY) / rayY;
			minTY = Double.max(0, (_maxY - rayPY) / rayY);
		}

		if (rayZ == 0 && (rayPZ > _maxZ || rayPZ < _minZ))
			return null;
		if (rayZ > 0) {
			if (_maxZ < rayPZ)
				return null;
			maxTZ = (_maxZ - rayPZ) / rayZ;
			minTZ = Double.max(0, (_minZ - rayPZ) / rayZ);
		}
		if (rayZ < 0) {
			if (_minZ > rayPZ)
				return null;
			maxTZ = (_minZ - rayPZ) / rayZ;
			minTZ = Double.max(0, (_maxZ - rayPZ) / rayZ);
		}
		double minT = Double.min(maxTX, maxTY);
		minT = Double.min(minT, maxTZ);
		double maxT = Double.max(minTX, minTY);
		maxT = Double.max(maxT, minTZ);
		if (minT < maxT)
			return null;
		Point3D p = ray.getPoint(maxT);
		return new Ray(p, v);
	}

	/**
	 * This funct check if the given point inside the Bounding box
	 * 
	 * @param p Point3D
	 * @return True/False
	 */
	private boolean isPointInTheBox(Point3D p) {
		double x = p.getX().get();
		double y = p.getY().get();
		double z = p.getZ().get();
		return x >= _minX && x <= _maxX && y >= _minY && y <= _maxY && z >= _minZ && z <= _maxZ;
	}

	/**
	 * This function get ray and check if the ray start inside Box
	 * 
	 * @param ray Ray
	 * @return true/false
	 */
	private boolean isRayStartInTheBox(Ray ray) {
		return isPointInTheBox(ray.getP0());
	}

	/**
	 * This function return the geoPoints of intersection on the way of the ray if
	 * ray doesnt intersect the box it returns null
	 * 
	 * @param ray            the Ray the traverse on the box
	 * @param shadowRaysCase boolean
	 * @param distance       distance for lightsource distance
	 * @return list of intersections
	 */
	public List<GeoPoint> findIntersectionsInTheBox(Ray ray, boolean shadowRaysCase, double dis) {
		if (!this.isRayStartInTheBox(ray)) {
			ray = this.checkIntersection(ray);
		}
		if (ray == null)// there is no intersect with the box
			return null;
		// else: check for intersection point with the geometries at the box
		return traverseTheBox(ray, shadowRaysCase, dis);
	}

	/**
	 * this function travers over the voxel in ray direction and check for
	 * intersection if intersection found in current voxel we return the current
	 * points we have else we travese to the next voxel this function uses the
	 * 3D-DDA algorithm *** case of shadow rays we need to travese all the voxels
	 * even if we find intersection in current voxel
	 * 
	 * @param ray            the Ray the traverse on the box (ray p0 always be in
	 *                       the box)
	 * @param shadowRaysCase boolean
	 * @param distance       distance for lightsource distance
	 * @return the relevan intersection points
	 */
	private List<GeoPoint> traverseTheBox(Ray ray, boolean shadowRaysCase, double distance) {
		double[] daltes = calculateDeltes(ray);
		double deltaX = daltes[0], deltaY = daltes[1], deltaZ = daltes[2];
		double tX = daltes[3], tY = daltes[4], tZ = daltes[5];
		// Getting the first voxel of the ray
		Voxel currentvoxel = Voxel.convertPointToVoxel(ray.getP0());
		int[] voxelIndex = new int[] { currentvoxel._x, currentvoxel._y, currentvoxel._z };
		List<GeoPoint> geoPoints = null;
		boolean foundInretsectInVoxelRange = false;
		Set<Intersectable> alreadyTested = new HashSet<Intersectable>();
		Geometries voxelGeometris;
		Geometries currentGeometris;
		double rayX = alignZero(ray.getDir().getHeadPoint().getX().get());
		double rayY = alignZero(ray.getDir().getHeadPoint().getY().get());
		double rayZ = alignZero(ray.getDir().getHeadPoint().getZ().get());
		while (true) {
			Voxel currentVoxel = new Voxel(voxelIndex[0], voxelIndex[1], voxelIndex[2]);
			if (_map.containsKey(currentVoxel)) {
				voxelGeometris = _map.get(currentVoxel);
				if (!shadowRaysCase)
					currentGeometris = voxelGeometris;
				else {
					currentGeometris = new Geometries();
					for (Intersectable geometry : voxelGeometris.getShapes()) {
						if (!alreadyTested.contains(geometry)) {
							currentGeometris.add(geometry);
							alreadyTested.add(geometry);
						}
					}
				}
				List<GeoPoint> gPoints = currentGeometris.findIntersections(ray, distance);
				if (gPoints != null) {
					if (geoPoints == null)
						geoPoints = new LinkedList<GeoPoint>();
					if (!shadowRaysCase && !foundInretsectInVoxelRange)
						if (currentVoxel.isIntersectInVoxelRange(gPoints))
							foundInretsectInVoxelRange = true;
					geoPoints.addAll(gPoints);
				}
			}
			if (foundInretsectInVoxelRange)
				return geoPoints;
			// Check how is the next voxel in the ray way
			if (tX < tY)
				if (tX < tZ) {
					tX += deltaX; // increment, next crossing along x
					voxelIndex[0] += rayX < 0 ? -1 : +1;
				} else {
					tZ += deltaZ; // increment, next crossing along z
					voxelIndex[2] += rayZ < 0 ? -1 : +1;
				}
			else if (tY < tZ) {
				tY += deltaY; // increment, next crossing along y
				voxelIndex[1] += rayY < 0 ? -1 : +1;
			} else {
				tZ += deltaZ; // increment, next crossing along z
				voxelIndex[2] += rayZ < 0 ? -1 : +1;
			}
			// if some condition is met break from the loop
			if (voxelIndex[0] < 0 || voxelIndex[1] < 0 || voxelIndex[2] < 0 || voxelIndex[0] > _boxDensity
					|| voxelIndex[1] > _boxDensity || voxelIndex[2] > _boxDensity)
				return geoPoints;
		}
	}

	/**
	 * This function calculate the deltes for taversing int the grid
	 * 
	 * @param ray Ray the intersect the box
	 * @return array of essential values for 3ddda
	 */
	private double[] calculateDeltes(Ray ray) {
		Vector rayDirection = ray.getDir();
		Point3D rayHead = rayDirection.getHeadPoint();
		double rayDirectionX = rayHead.getX().get();
		double rayDirectionY = rayHead.getY().get();
		double rayDirectionZ = rayHead.getZ().get();

		// P0 of the ray it always in the grid
		Point3D rayOrigin = ray.getP0();

		// vector from the min corner of the grid to the P0 of the ray to get the
		// distance between them for each axis
		Vector rayOrigBox = rayOrigin.subtract(new Point3D(_minX, _minY, _minZ));
		double rayOrigBoxX = rayOrigBox.getHeadPoint().getX().get();
		double rayOrigBoxY = rayOrigBox.getHeadPoint().getY().get();
		double rayOrigBoxZ = rayOrigBox.getHeadPoint().getZ().get();
		double deltaX, deltaY, deltaZ, tX, tY, tZ;
		if (rayDirectionX < 0) { // Negative direction on the x axis
			deltaX = -_voxelSizeX / rayDirectionX;
			tX = (Math.floor(rayOrigBoxX / _voxelSizeX) * _voxelSizeX - rayOrigBoxX) / rayDirectionX;
		} else { // Positive direction on the x axis
			deltaX = _voxelSizeX / rayDirectionX;
			tX = (Math.floor(rayOrigBoxX / _voxelSizeX + 1) * _voxelSizeX - rayOrigBoxX) / rayDirectionX;

		}
		if (rayDirectionY < 0) { // Negative direction on the y axis
			deltaY = -_voxelSizeY / rayDirectionY;
			tY = (Math.floor(rayOrigBoxY / _voxelSizeY) * _voxelSizeY - rayOrigBoxY) / rayDirectionY;
		} else { // Positive direction on the y axis
			deltaY = _voxelSizeY / rayDirectionY;
			tY = (Math.floor(rayOrigBoxY / _voxelSizeY + 1) * _voxelSizeY - rayOrigBoxY) / rayDirectionY;

		}
		if (rayDirectionZ < 0) { // Negative direction on the z axis
			deltaZ = -_voxelSizeZ / rayDirectionZ;
			tZ = (Math.floor(rayOrigBoxZ / _voxelSizeZ) * _voxelSizeZ - rayOrigBoxZ) / rayDirectionZ;
		} else { // Positive direction on the z axis
			deltaZ = _voxelSizeZ / rayDirectionZ;
			tZ = (Math.floor(rayOrigBoxZ / _voxelSizeZ + 1) * _voxelSizeZ - rayOrigBoxZ) / rayDirectionZ;
		}
		return new double[] { deltaX, deltaY, deltaZ, tX, tY, tZ };
	}

	/**
	 * inner class that implement voxel (piece of the volume)
	 */
	private static class Voxel {
		private int _x;
		private int _y;
		private int _z;

		/**
		 * Constructor of Voxel initilize the indexes of the Voxel
		 * 
		 * @param indexX x
		 * @param indexY y
		 * @param indexZ z
		 */
		public Voxel(int indexX, int indexY, int indexZ) {
			_x = indexX;
			_y = indexY;
			_z = indexZ;
		}

		/**
		 * This function get voxel and list of intersection points if atleast one
		 * intersection point inside voxel range we return true else retrun false
		 * 
		 * @param voxel         current Voxel
		 * @param intersections list of intersection points
		 * @return True /false
		 */
		private boolean isIntersectInVoxelRange(List<GeoPoint> intersections) {
			for (GeoPoint geoPoint : intersections) {
				if (convertPointToVoxel(geoPoint.point).equals(this))
					return true;
			}
			return false;
		}

		/**
		 * Function to find for the sent point the appropriate Voxel
		 * 
		 * @param point - The point on the geometry
		 * @return - Returns the voxel that the point inside it
		 */
		private static Voxel convertPointToVoxel(Point3D point) {
			int x = (int) ((point.getX().get() - _minX) / _voxelSizeX);
			int y = (int) ((point.getY().get() - _minY) / _voxelSizeY);
			int z = (int) ((point.getZ().get() - _minZ) / _voxelSizeZ);
			return new Voxel(x, y, z);
		}

		@Override
		public String toString() {
			return "Voxel{" + _x + "," + _y + "," + _z + '}' + "\n";
		}

		@Override
		public int hashCode() {
			return Objects.hash(_x, _y, _z);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Voxel))
				return false;
			Voxel other = (Voxel) obj;
			return _x == other._x && _y == other._y && _z == other._z;
		}
	}
}