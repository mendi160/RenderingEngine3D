package elements;

import static primitives.Util.*;
import primitives.*;

/**
 * This Class Represent The Camera Object that gonna look on the View plane. The
 * camera construct rays through all the view plane and see if its hit the
 * objects
 * 
 * @author Yehonatan Eliyahu & Mendi Shneorson
 *
 */
public class Camera {
	private Point3D _p0;
	private Vector _vUp;
	private Vector _vTo;
	private Vector _vRight;

	/**
	 * Camera Constructor Please make sure the Vectors Are Orthogonal, otherwise
	 * exception will thrown. the Vectors are should be normalize. The verctor
	 * vRight create by cross product between vTo and vUp
	 * 
	 * @param p0  Where Camera "lens" will be
	 * @param vTo The Direction Where The Camera look
	 * @param vUp (going up) Orthogonal to VTo
	 * @exception IllegalArgumentException
	 */
	public Camera(Point3D p0, Vector vTo, Vector vUp) {
		if (!isZero(vUp.dotProduct(vTo))) {
			throw new IllegalArgumentException("Vectors must be orthognal each other");
		}
		_p0 = p0;
		_vUp = vUp.normalized();
		_vTo = vTo.normalized();
		// generating vRight
		_vRight = vTo.crossProduct(vUp).normalize();
	}

	/**
	 * This Function Construct Ray that come from the Camera "lens" and hit the
	 * desired Pixel
	 * 
	 * @param nX             Number of the Columns in the ViewPlane
	 * @param nY             Number of the Rows in the ViewPlane
	 * @param i              The index of the ViewPlane Row Pixel
	 * @param j              The index of the ViewPlane Column Pixel
	 * @param screenDistance The Distance from Camera to View Plane
	 * @param screenWidth    The with of the view plane
	 * @param screenHeight   The Height of the View Plane
	 * @return Ray A Ray the come from the Camera Lens and hit the current Pixel
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth,
			double screenHeight) {
		if (alignZero(screenDistance) <= 0) { // check thistance is Positive
			throw new IllegalArgumentException("Distance have to be bigger than zero\n");
		}
		if (alignZero(nY) <= 0 || alignZero(nX) <= 0 || alignZero(screenWidth) <= 0 || alignZero(screenHeight) <= 0) {
			// Check all the Paramters is positive
			throw new IllegalArgumentException("view plane dosen't exist\n");
		}
		// Calculating According the formula step by step
		Point3D pc = _p0.add(_vTo.scale(screenDistance));
		double rY, rX, yI, xJ;
		rY = screenHeight / nY;
		rX = screenWidth / nX;
		xJ = alignZero((j - nX / 2d) * rX + rX / 2d);
		yI = alignZero((i - nY / 2d) * rY + rY / 2d);
		Point3D pij = pc;
		if (xJ != 0)
			pij = pc.add(_vRight.scale(xJ));
		if (yI != 0) {
			pij = pij.add((_vUp.scale(-yI)));// eaquel to subtract(_vUp.scale(yI)
		}
		return new Ray(_p0, pij.subtract(_p0));// Ray Ctor make sure that the ditection it normalize
	}

	/**
	 * getter
	 * 
	 * @return the Camera lens Position
	 */
	public Point3D getP0() {
		return new Point3D(_p0);
	}

	/**
	 * getter
	 * 
	 * @return The Direction Where The Camera look
	 */
	public Vector getVTo() {
		return new Vector(_vTo);
	}

	/**
	 * getter
	 * 
	 * @return The Direction that orthogonal to The Camera look
	 */
	public Vector getVUp() {
		return new Vector(_vUp);
	}

	/**
	 * getter
	 * 
	 * @return The Direction That Orthogonal to vTO and vUP
	 */
	public Vector getVRight() {
		return new Vector(_vRight);
	}

}
