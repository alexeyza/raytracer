package sceneObjects;

import java.io.FileNotFoundException;

import application.Parser.ParseException;
import scene.Intersection;
import vectors.*;

/**
 * This class represents the rectangle scene object. It is mainly used in the mesh object.
 *
 */
public class Rectangle extends SceneObject{
	private IPoint3D p0;
	private IPoint3D p1;
	private IPoint3D p2;
	private IPoint3D p3;

	/**
	 * Constructs a rectangle object.
	 */
	public Rectangle(){
		this(null,null,null);
	}

	/**
	 * Constructs a rectangle object.
	 * 
	 * @param p0
	 * @param p1
	 * @param p2
	 */
	public Rectangle(IPoint3D p0, IPoint3D p1, IPoint3D p2){
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		if ((getP0()!=null)&&(getP1()!=null)&&(getP2()!=null)){
			p3 = p1.movePointByVector(new Vector3D(p0,p2));
		}else{
			p3 = null;
		}
	}

	/**
	 * Returns the p0 point of the rectangle.
	 * 
	 * @return The p0 point of the rectangle.
	 */
	public IPoint3D getP0(){
		return p0;
	}

	/**
	 * Returns the p1 point of the rectangle.
	 * 
	 * @return The p1 point of the rectangle.
	 */
	public IPoint3D getP1(){
		return p1;
	}

	/**
	 * Returns the p2 point of the rectangle.
	 * 
	 * @return The p2 point of the rectangle.
	 */
	public IPoint3D getP2(){
		return p2;
	}

	/**
	 * Returns the p3 point of the rectangle.
	 * 
	 * @return The p3 point of the rectangle.
	 */
	public IPoint3D getP3(){
		if (p3!=null){
			return p3;
		}
		if ((getP0()!=null)&&(getP1()!=null)&&(getP2()!=null)){
			p3 = p1.movePointByVector(new Vector3D(p0,p2));
			return p3;
		}
		return null;
	}

	/**
	 * Sets the p0 point of the rectangle.
	 * 
	 * @param p0
	 */
	public void setP0(IPoint3D p0){
		this.p0 = p0;
	}

	/**
	 * Sets the p1 point of the rectangle.
	 * 
	 * @param p1
	 */
	public void setP1(IPoint3D p1){
		this.p1 = p1;
	}

	/**
	 * Sets the p2 point of the rectangle.
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

		// Now we check to see if the hitPoint is inside 4 of the triangles the rectangle creates

		// triangle =  p0,p1,p2
		if(rayTriangleIntersection(hitPoint,getP0(),getP1(),getP2())==false)
			return new Intersection();

		// triangle = p0,p2,p1
		if(rayTriangleIntersection(hitPoint,getP0(),getP2(),getP1())==false)
			return new Intersection();

		// triangle p3,p2,p1
		if(rayTriangleIntersection(hitPoint,getP3(),getP2(),getP1())==false)
			return new Intersection();

		// triangle = p3,p1,p2
		if(rayTriangleIntersection(hitPoint,getP3(),getP1(),getP2())==false)
			return new Intersection();

		Intersection hit = new Intersection();
		hit.setIntersectionHit(hitPoint, ray.getP0().getDistanceBetweenPoints(hitPoint), this, plane.getNormal(ray),ray);

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
	 * Returns the normal vector of the rectangle.
	 * 
	 * @return The normal vector of the rectangle.
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
			throw new ParseException("Parameters given for Rectangle are not valid or missing");
		}
		if (getP0().isCoLinear(getP1(), getP2())){
			throw new ParseException("Points of the Rectangle are co-linear");
		}
		p3 = getP1().movePointByVector(new Vector3D(getP0(),getP2()));
	}

	/**
	 * Returns 'true' if the given point is on the same plane as the rectangle, otherwise 'false'.
	 * 
	 * @param hitPoint
	 * @return 'true' if the given point is on the same plane as the rectangle, otherwise 'false'.
	 */
	public boolean isOnSamePlane(IPoint3D hitPoint){
		IPlane3D p = new Plane3D(getNormalVector(),getP0());
		return p.isOnSamePlane(hitPoint);
	}
	
	/**
	 * Returns the 2D point which maps the given 3D point to a texture (or checkers pattern).
	 * 
	 * @param point3d
	 * @return The 2D point which maps the given 3D point to a texture (or checkers pattern).
	 */
	@Override
	public Point2D parametrize(IPoint3D point3d) {
		// Compute vectors        
		IVector3D v0 = new Vector3D(p0, p2);
		IVector3D v1 = new Vector3D(p0, p1);
		IVector3D v2 = new Vector3D(p0, point3d);

		// Compute dot products
		double dot00 = v0.dotProduct(v0);
		//double dot01 = v0.dotProduct(v1);
		double dot02 = v0.dotProduct(v2);
		double dot11 = v1.dotProduct(v1);
		double dot12 = v1.dotProduct(v2);

		return new Point2D(dot02/dot00, dot12/dot11);
		
	}
}