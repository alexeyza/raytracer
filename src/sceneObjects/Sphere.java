package sceneObjects;

import java.io.FileNotFoundException;

import application.Parser.ParseException;
import scene.Intersection;
import vectors.*;

/**
 * This class represents the sphere scene object.
 *
 */
public class Sphere extends SceneObject {
    private IPoint3D center;
    private double radius;
    private double radiusSquare;
    
    /**
     * Constructs a sphere object.
     */
    public Sphere(){
    	center = null;
    	radius = 0;
    	radiusSquare = 0;
    }
    
    /**
     * Constructs a sphere object.
     * 
     * @param c The center of the sphere.
     * @param r The radius of the sphere.
     */
    public Sphere(IPoint3D c, double r) {
        center = c;
        radius = r;
        radiusSquare = r*r;
    }
    
    /**
     * Returns the center point of the sphere.
     * 
     * @return The center point of the sphere.
     */
    public IPoint3D getCenter(){
    	return center;
    }
    
    /**
     * Returns the radius of the sphere.
     * 
     * @return The radius of the sphere.
     */
    public double getRadius(){
    	return radius;
    }
    
    /**
     * Sets the center point of the sphere.
     * 
     * @param center
     */
    public void setCenter(IPoint3D center){
    	this.center = center;
    }
    
    /**
     * Sets the radius of the sphere.
     * 
     * @param radius
     */
    public void setRadius(double radius){
    	if (radius>=0){
    		this.radius = radius;
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
		// a*(t^2) + b*t + c = 0
		// a = 1
		double a = 1;
		// b = 2*V*(p0 - center)
		IVector3D vecFromP0toCenter = new Vector3D(getCenter(),ray.getP0());
        double b = ray.getV().multiplyByScalar(2d).dotProduct(vecFromP0toCenter);
        // c = (p0 - center)^2  - r^2
        double c = vecFromP0toCenter.dotProduct(vecFromP0toCenter) - radiusSquare;
        
        // Solve quadratic equation 
        double[] sol = solveEquation(a, b, c);
        
        return getIntersectionBySolution(ray, sol);
	}

	/**
	 * Returns the intersection information based on the given possible 't' for the equation 'P = p0 +tV'.
	 * 
	 * @param ray
	 * @param sol The possible solutions for 't'.
	 * @return The intersection information based on the given possible 't' for the equation 'P = p0 +tV'.
	 */
	private Intersection getIntersectionBySolution(IRay ray, double[] sol) {
		// no solutions could be found
        if (sol==null){
        	return new Intersection();
        }
        
        double sol1 = sol[0];
        double sol2 = sol[1];
        
        // if both solutions are not positive (or equal zero)
		if ((sol1<=0)&&(sol2<=0)){
			return new Intersection();
		}
		Intersection hit = new Intersection();
		IPoint3D hitPoint1 = ray.getPointOnRay(sol1);
		IPoint3D hitPoint2 = ray.getPointOnRay(sol2);
		double distanceSol1 = ray.getP0().getDistanceBetweenPoints(hitPoint1);
		double distanceSol2 = ray.getP0().getDistanceBetweenPoints(hitPoint2);
		
		// sol2 is the good solution 
		if (sol1<=0){
			hit.setIntersectionHit(hitPoint2, distanceSol2, this, new Vector3D(getCenter(),hitPoint2).normalize(),ray);
			return hit;
		}
		
		// sol1 is the good solution 
		if (sol2<=0){
			hit.setIntersectionHit(hitPoint1, distanceSol1, this, new Vector3D(getCenter(),hitPoint1).normalize(),ray);
			return hit;
		}
				
		// both solutions are positive (not zero) and we need to choose the closest one to p0
		if (distanceSol1<distanceSol2){
			hit.setIntersectionHit(hitPoint1, distanceSol1, this, new Vector3D(getCenter(),hitPoint1).normalize(),ray);
		}else{
			hit.setIntersectionHit(hitPoint2, distanceSol2, this,new Vector3D(getCenter(),hitPoint2).normalize(),ray);
		}
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
			if (name.equals("radius")){
				setRadius(Double.parseDouble(args[0]));
				radiusSquare = radius * radius;
			}
		}catch (ArrayIndexOutOfBoundsException e){
			throw new NumberFormatException();
		}
	}

	/**
	 * Finishes parsing the scene object and validates that all mandatory values were given and valid.
	 * 
	 * @throws ParseException
	 */
	@Override
	public void commit() throws ParseException {
		if ((getCenter()==null)||(getRadius()<=0)){
			throw new ParseException("Parameters given for Sphere are not valid or missing");
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
		double z = point3d.getZ();
		double theta = Math.acos(z / radius) / Math.PI;
		double phi = Math.atan2(y, x) / (2 * Math.PI);
		
		theta = (theta < 0) ? 1 + theta : theta;
		phi = (phi < 0) ? 1 + phi : phi;
		
		Point2D point2d = new Point2D(theta, phi);
		return point2d.normalize();
	}
}