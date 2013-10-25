package sceneObjects;

import java.io.FileNotFoundException;

import application.Parser.ParseException;
import scene.Intersection;
import vectors.*;

/**
 * 
 * This class represents the cylinder scene object (finite non capped cylinder).
 *
 */
public class Cylinder extends SceneObject{
	private IPoint3D start;
	private IVector3D direction;
	private double length;
	private double radius;
	
	/**
	 * Constructs a cylinder object.
	 */
	public Cylinder(){
		start = null;
		direction = null;
		length = 0;
		radius = 0;
	}
	
	/**
	 * Returns the start point of the cylinder.
	 * 
	 * @return The start point of the cylinder.
	 */
	public IPoint3D getStart(){
		return start;
	}
	
	/**
	 * Returns the direction vector of the cylinder.
	 * 
	 * @return the direction vector of the cylinder.
	 */
	public IVector3D getDirection(){
		return direction.normalize();
	}
	
	/**
	 * Returns the length of the cylinder.
	 * 
	 * @return The length of the cylinder.
	 */
	public double getLength(){
		return length;
	}
	
	/**
	 * Return the radius of the cylinder.
	 * 
	 * @return The radius of the cylinder.
	 */
	public double getRadius(){
		return radius;
	}
	
	/**
	 * Sets the start point of the cylinder.
	 * 
	 * @param start The start point of the cylinder.
	 */
	public void setStart(IPoint3D start){
		this.start =  start;
	}
	
	/**
	 * Sets the direction vector of the cylinder.
	 * 
	 * @param direction
	 */
	public void setDirection(IVector3D direction){
		this.direction = direction;
	}
	
	/**
	 * Sets the length of the cylinder.
	 * 
	 * @param length
	 */
	public void setLength(double length){
		if (length>=0){
			this.length = length;
		}
	}
	
	/**
	 * Sets the radius of the cylinder.
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
		// THIS is taken from http://www.gamedev.net/community/forums/topic.asp?topic_id=467789
		//
		//--------------------------------------------------------------------------
		// Ray : P(t) = O + V * t
		// Cylinder [O, D, r].
		// point Q on cylinder if ((Q - O) x D)^2 = r^2
		//
		// Cylinder [A, B, r].
		// Point P on infinite cylinder if ((P - A) x (B - A))^2 = r^2 * (B - A)^2
		// expand : ((O - A) x (B - A) + t * (V x (B - A)))^2 = r^2 * (B - A)^2
		// equation in the form (X + t * Y)^2 = d
		// where : 
		//  X = (O - A) x (B - A)
		//  Y = V x (B - A)
		//  d = r^2 * (B - A)^2
		// expand the equation :
		// t^2 * (Y . Y) + t * (2 * (X . Y)) + (X . X) - d = 0
		// => second order equation in the form : a*t^2 + b*t + c = 0 where
		// a = (Y . Y)
		// b = 2 * (X . Y)
		// c = (X . X) - d
		//--------------------------------------------------------------------------

		IVector3D X = (new Vector3D(getStart(),ray.getP0())).crossProduct(getDirection());
		IVector3D Y = ray.getV().crossProduct(getDirection());
		double a = Y.dotProduct(Y);
		double b = 2 * (X.dotProduct(Y));
		double c = (X.dotProduct(X)) - getRadius()*getRadius();
		
		// Solve quadratic equation
		double[] sol = solveEquation(a, b, c);
		// no solutions were found
		if (sol==null){
			return new Intersection();
		}
		Intersection hit = new Intersection();
		IPoint3D hitPoint1 = ray.getPointOnRay(sol[0]);
		IPoint3D hitPoint2 = ray.getPointOnRay(sol[1]);
		double dist1 = ray.getP0().getDistanceBetweenPoints(hitPoint1);
		double dist2 = ray.getP0().getDistanceBetweenPoints(hitPoint2);
		if (dist1>dist2&&(sol[0]>=0&&sol[1]>=0)){
			IPoint3D temp = hitPoint1;
			hitPoint1 = hitPoint2;
			hitPoint2 = temp;
		}
		
		IVector3D vecFromStartToHitPoint1 = new Vector3D(getStart(),hitPoint1);
		IVector3D vecFromStartToHitPoint2 = new Vector3D(getStart(),hitPoint2);
		
		double projection1 = vecFromStartToHitPoint1.dotProduct(getDirection());
		double projection2 = vecFromStartToHitPoint2.dotProduct(getDirection());
		
		IVector3D normal = null;
		
		if ((projection1>getLength())||(projection1<0)||(sol[0]<0)){
			if ((projection2>getLength())||(projection2<0)||(sol[1]<0)){
				return new Intersection();
			}
			normal = getNormal(vecFromStartToHitPoint2.normalize());
			hit.setIntersectionHit(hitPoint2, ray.getP0().getDistanceBetweenPoints(hitPoint2), this,normal,ray);
			return hit;
		}
		normal = getNormal(vecFromStartToHitPoint1.normalize());
		hit.setIntersectionHit(hitPoint1, ray.getP0().getDistanceBetweenPoints(hitPoint1), this,normal,ray);
		return hit;
	}

	/**
	 * Returns the normal of the surface at a given start point (using a vector from start point to hit point).
	 * 
	 * @param vecFromStartToHitPoint The vector from start point to hit point.
	 * @return The normal of the surface at a given start point.
	 */
	private IVector3D getNormal(IVector3D vecFromStartToHitPoint) {
		return vecFromStartToHitPoint.crossProduct(getDirection()).crossProduct(getDirection()).normalize().multiplyByScalar(-1);
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
			if (name.equals("start")){
				setStart(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("direction")){
				setDirection(new Vector3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("length")){
				setLength(Double.parseDouble(args[0]));
			}
			if (name.equals("radius")){
				setRadius(Double.parseDouble(args[0]));
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
		if ((getStart()==null)||(getDirection()==null)||(getLength()<=0)||(getRadius()<=0)){
			throw new ParseException("Parameters given for Cylinder are not valid or missing");
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
		IVector3D movedDirection = getDirection().subtractVector(new Vector3D(start)).normalize();
		double dist = start.getDistanceBetweenPoints(point3d);
		double projectionLength = Math.sqrt(dist*dist - radius*radius);
		IPoint3D hitPointProjectedToDirection = new Point3D(0,0,0).movePointByVector(movedDirection.multiplyByScalar(projectionLength));
		double height = hitPointProjectedToDirection.getDistanceBetweenPoints(new Point3D(0,0,0));
		//IVector3D angleVector = new Vector3D(hitPointProjectedToDirection,point3d).normalize();
		IVector3D angleVector = new Vector3D(point3d,start.movePointByVector(getDirection().multiplyByScalar(height))).normalize();
		IVector3D randomVector = new Vector3D(0,1,0);
		//IVector3D refrenceVector = randomVector.crossProduct(getDirection());
		double angle = angleVector.dotProduct(randomVector);
		double x = Math.acos(angle)/(2*Math.PI);
		
		
		return new Point2D(height/length,1.5d*x);
	}
}
