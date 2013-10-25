package sceneObjects;

import java.io.FileNotFoundException;

import scene.Intersection;
import vectors.*;
import application.Parser.ParseException;

/**
 * This class represents a dist scene object.
 *
 */
public class Disc extends SceneObject{
	private IPoint3D center;
	private IVector3D normal;
	private double radius;
	
	/**
	 * Constructs a disc object.
	 */
	public Disc(){
		center = null;
		normal = null;
		radius = 0;
	}
	
	/**
	 * Returns the center point of the disc.
	 * 
	 * @return The center point of the disc.
	 */
	public IPoint3D getCenter(){
		return center;
	}
	
	/**
	 * Returns the normal of the disc.
	 * 
	 * @return The normal of the disc.
	 */
	public IVector3D getNormal(){
		return normal;
	}
	
	/**
	 * Returns the radius of the disc.
	 * 
	 * @return The radius of the disc.
	 */
	public double getRadius(){
		return radius;
	}
	
	/**
	 * Sets the center point of the disc.
	 * 
	 * @param center
	 */
	public void setCenter(IPoint3D center){
		this.center = center;
	}
	
	/**
	 * Sets the normal of the disc.
	 * 
	 * @param normal
	 */
	public void setNormal(IVector3D normal){
		this.normal = normal;
	}
	
	/**
	 * Sets the radius of the disc.
	 * 
	 * @param radius
	 */
	public void setRadius(double radius){
		if (radius>0){
			this.radius = radius;
		}
	}

	/**
	 * Finishes parsing the scene object and validates that all mandatory values were given and valid.
	 * 
	 * @throws ParseException
	 */
	@Override
	public void commit() throws ParseException {
		if ((getCenter()==null)||(getNormal()==null)||(getRadius()<=0)){
			throw new ParseException("Parameters given for Disc are not valid or missing");
		}
	}

	/**
	 * Return the intersection information for a given ray.
	 * Use the returned object to check if there was a 'hit' or 'miss'.
	 * 
	 * @param ray
	 * @return The intersection information for a given ray.
	 */
	@Override
	public Intersection isIntersects(IRay ray) {
		IPlane3D plane = new Plane3D(getNormal(),getCenter());
		IPoint3D hitPoint = plane.rayPlaneIntersection(ray);
		// check if there is a hit point with the plane of the disc
		if (hitPoint == null){
			return new Intersection();
		}
		// check if the hit point is not out of the disc
		if (hitPoint.getDistanceBetweenPoints(getCenter())>getRadius()){
			return new Intersection();
		}
		Intersection hit = new Intersection();
		hit.setIntersectionHit(hitPoint, ray.getP0().getDistanceBetweenPoints(getCenter()), this,plane.getNormal(ray),ray);
		return hit;
	}

    /**
     * Parses the given parameters to create a scene object.
     * 
     * @param name The name of the parameter.
     * @param args The values for the parameter.
     * @throws NumberFormatException
     * @throws FileNotFoundException
     */
	@Override
	public void parseParameter(String name, String[] args) throws NumberFormatException {
		try{
			if (name.equals("center")){
				setCenter(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("normal")){
				setNormal(new Vector3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("radius")){
				setRadius(Double.parseDouble(args[0]));
			}
		}catch (ArrayIndexOutOfBoundsException e){
			throw new NumberFormatException();
		}
	}

	/**
	 * Returns the 2D point which maps the given 3D point to a texture (or checkers pattern).
	 * 
	 * @param point3d
	 * @return The 2D point which maps the given 3D point to a texture (or checkers pattern).
	 */
	@Override
	public Point2D parametrize(IPoint3D point3d) {
		double x = point3d.getX();
		double y = point3d.getY();

		double theta = point3d.getDistanceBetweenPoints(center) * 2d;
		double phi = Math.atan2(y, x) / (2 * Math.PI);
		
		theta = (theta < 0) ? 1 + theta : theta;
		phi = (phi < 0) ? 1 + phi : phi;
		
		Point2D point2d = new Point2D(theta, phi);
		return point2d.normalize();
	}
}