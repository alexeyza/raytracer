package sceneObjects;

import java.io.FileNotFoundException;

import application.Parser.ParseException;
import scene.Intersection;
import vectors.*;

/**
 * This class represents the triangle scene object.
 *
 */
public class Triangle extends SceneObject{
	private IPoint3D p0;
	private IPoint3D p1;
	private IPoint3D p2;
	
	/**
	 * Constructs a triangle object.
	 */
	public Triangle(){
		this(null,null,null);
	}
	
	/**
	 * Constructs a triangle object.
	 * @param p0
	 * @param p1
	 * @param p2
	 */
	public Triangle(IPoint3D p0, IPoint3D p1, IPoint3D p2){
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
	}
	
	/**
	 * Returns the p0 point of the triangle.
	 * 
	 * @return The p0 point of the triangle.
	 */
	public IPoint3D getP0(){
		return p0;
	}
	
	/**
	 * Returns the p1 point of the triangle.
	 * 
	 * @return The p1 point of the triangle.
	 */
	public IPoint3D getP1(){
		return p1;
	}
	
	/**
	 * Returns the p2 point of the triangle.
	 * 
	 * @return The p2 point of the triangle.
	 */
	public IPoint3D getP2(){
		return p2;
	}
	
	/**
	 * Sets the p0 point of the triangle.
	 * 
	 * @param p0
	 */
	public void setP0(IPoint3D p0){
		this.p0 = p0;
	}
	
	/**
	 * Sets the p1 point of the triangle.
	 * 
	 * @param p1
	 */
	public void setP1(IPoint3D p1){
		this.p1 = p1;
	}
	
	/**
	 * Sets the p2 point of the triangle.
	 * 
	 * @param p2
	 */
	public void setP2(IPoint3D p2){
		this.p2 = p2;
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
		try {
			if (name.equals("p0")){
				setP0(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("p1")){
				setP1(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("p2")){
				setP2(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
		}catch (ArrayIndexOutOfBoundsException e){
			throw new NumberFormatException();
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
		IVector3D normal = getNormalVector();
		IPlane3D plane = new Plane3D(normal,getP0());
		
		IPoint3D hitPoint = plane.rayPlaneIntersection(ray);
		if (hitPoint==null){
			return new Intersection();
		}
		
		// Now we check to see if the hitPoint is inside 3 of the triangles the object triangle creates
		
		// triangle =  p0,p1,p2
		if(rayTriangleIntersection(hitPoint,getP0(),getP1(),getP2())==false)
			return new Intersection();
		
		// triangle = p0,p2,p1
		if(rayTriangleIntersection(hitPoint,getP0(),getP2(),getP1())==false)
			return new Intersection();
		
		
		// triangle p1,p2,p0
		if(rayTriangleIntersection(hitPoint,getP1(),getP2(),getP0())==false)
			return new Intersection();
		
		Intersection hit = new Intersection();
		hit.setIntersectionHit(hitPoint, ray.getP0().getDistanceBetweenPoints(hitPoint), this,plane.getNormal(ray),ray);
		
		return hit;
	}
	
	/**
	 * Returns 'true' if the hit point is inside the triangle "projection".
	 * 
	 * @param hitPoint
	 * @param trianglePoint1
	 * @param trianglePoint2
	 * @param trianglePoint3
	 * @return 'true' if the hit point is inside the triangle "projection".
	 */
	private boolean rayTriangleIntersection(IPoint3D hitPoint,IPoint3D trianglePoint1,IPoint3D trianglePoint2,IPoint3D trianglePoint3)
	{
		IVector3D v1 = new Vector3D(trianglePoint2,trianglePoint1);
		IVector3D v2 = new Vector3D(trianglePoint2,trianglePoint3);
		IVector3D v3 = new Vector3D(trianglePoint2,hitPoint);
		IVector3D norm1 = v1.crossProduct(v2);
		IVector3D norm2 = v1.crossProduct(v3);
		return (norm2.dotProduct(norm1)>=0);
	}

	/**
	 * Returns the normal vector of the triangle.
	 * 
	 * @return The normal vector of the triangle.
	 */
	public IVector3D getNormalVector(){
		IVector3D v1 = new Vector3D(getP0(),getP1());
		IVector3D v2 = new Vector3D(getP2(),getP0());
		IVector3D v = v1.crossProduct(v2);
		return v.normalize();
	}

	/**
	 * Finishes parsing the scene object and validates that all mandatory values were given and valid.
	 * 
	 * @throws ParseException
	 */
	@Override
	public void commit() throws ParseException {
		if ((getP0()==null)||(getP1()==null)||(getP2()==null)){
			throw new ParseException("Parameters given for Triangle are not valid or missing");
		}
		if (getP0().isCoLinear(getP1(), getP2())){
			throw new ParseException("Points of the Triangle are co-linear");
		}
	}
	
	/**
	 * Returns the 2D point which maps the given 3D point to a texture (or checkers pattern).
	 * 
	 * @param point3d
	 * @return The 2D point which maps the given 3D point to a texture (or checkers pattern).
	 */
	public Point2D parametrize(IPoint3D point3d) {		
		// Compute vectors        
		IVector3D v0 = new Vector3D(p0, p2);
		IVector3D v1 = new Vector3D(p0, p1);
		IVector3D v2 = new Vector3D(p0, point3d);

		// Compute dot products
		double dot00 = v0.dotProduct(v0);
		double dot01 = v0.dotProduct(v1);
		double dot02 = v0.dotProduct(v2);
		double dot11 = v1.dotProduct(v1);
		double dot12 = v1.dotProduct(v2);

		// Compute barycentric coordinates
		double invDenom = 1 / (dot00 * dot11 - dot01 * dot01);
		double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
		double v = (dot00 * dot12 - dot01 * dot02) * invDenom;

		if ((u > 0) && (v > 0) && (u + v < 1)) {
			return new Point2D(u, v);
		}
		else {
			return null;
		}
	}
}