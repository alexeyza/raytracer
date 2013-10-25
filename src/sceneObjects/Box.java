package sceneObjects;

import java.io.FileNotFoundException;

import scene.Intersection;
import vectors.*;
import application.Parser.ParseException;

/**
 * This class represents a Box which consists of 6 rectangles.
 *
 */
public class Box extends SceneObject {
	private IPoint3D p0,p1,p2,p3;
	private Rectangle[] boxFaces;

	/**
	 * Constructs a box scene object.
	 */
	public Box(){
		p0 = null;
		p1 = null;
		p2 = null;
		p3 = null;
		boxFaces = new Rectangle[6];
	}

	/**
	 * Constructs a box scene object.
	 * 
	 * @param p0
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Box(IPoint3D p0, IPoint3D p1, IPoint3D p2, IPoint3D p3){
		this();
		setP0(p0);
		setP1(p1);
		setP2(p2);
		setP3(p3);
		findBoxFaces();
	}

	/**
	 * Returns the p0 point of the box.
	 * 
	 * @return The p0 point of the box.
	 */
	public IPoint3D getP0(){
		return p0;
	}

	/**
	 * Returns the p1 point of the box.
	 * 
	 * @return The p1 point of the box.
	 */
	public IPoint3D getP1(){
		return p1;
	}

	/**
	 * Returns the p2 point of the box.
	 * 
	 * @return The p2 point of the box.
	 */
	public IPoint3D getP2(){
		return p2;
	}

	/**
	 * Returns the p3 point of the box.
	 * 
	 * @return The p3 point of the box.
	 */
	public IPoint3D getP3(){
		return p3;
	}

	/**
	 * Sets the p0 point of the box.
	 * 
	 * @param p0
	 */
	public void setP0(IPoint3D p0){
		this.p0 = p0;
	}

	/**
	 * Sets the p1 point of the box.
	 * 
	 * @param p1
	 */
	public void setP1(IPoint3D p1){
		this.p1 = p1;
	}

	/**
	 * Sets the p2 point of the box.
	 * 
	 * @param p2
	 */
	public void setP2(IPoint3D p2){
		this.p2 = p2;
	}

	/**
	 * Sets the p3 point of the box.
	 * 
	 * @param p3
	 */
	public void setP3(IPoint3D p3){
		this.p3 = p3;
	}

	/**
	 * Finishes parsing the scene object and validates that all mandatory values were given and valid.
	 * 
	 * @throws ParseException
	 */
	@Override
	public void commit() throws ParseException {
		if ((getP0()==null)||(getP1()==null)||(getP2()==null)||(getP3()==null)){
			throw new ParseException("Parameters given for Box are not valid or missing");
		}
		if ((getP0().isCoLinear(getP1(), getP2()))||(getP0().isCoLinear(getP1(), getP3()))||(getP1().isCoLinear(getP2(), getP3()))||(getP0().isCoLinear(getP2(), getP3()))){
			throw new ParseException("Points of the Box are co-linear");
		}
		findBoxFaces();
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
		Intersection[] possibleIntesctions = new Intersection[6];
		Intersection bestHit = null;
		double minDistance = Double.MAX_VALUE;
		for (int i=0; i<6; i++){
			possibleIntesctions[i] = boxFaces[i].isIntersects(ray);
		}
		for (Intersection hit:possibleIntesctions){
			if (hit!=null){
				if (hit.getDistance()<minDistance){
					bestHit = hit;
					minDistance = hit.getDistance();
				}
			}
		}
		if (bestHit!=null){
			bestHit.setIntersectionHit(bestHit.getIntersectionPoint(), bestHit.getDistance(), this,bestHit.getNormal(),ray);
		}else{
			bestHit = new Intersection();
		}
		return bestHit;
	}

	/**
	 * Builds 6 rectangles to create the box.
	 */
	private void findBoxFaces(){

		// obvious rectangles
		boxFaces[0] = new Rectangle(p0,p1,p2);
		boxFaces[1] = new Rectangle(p0,p1,p3);
		boxFaces[2] = new Rectangle(p0,p2,p3);

		// find other points of box
		IPoint3D p4 = p1.movePointByVector(new Vector3D(p0,p2));
		IPoint3D p5 = p3.movePointByVector(new Vector3D(p0,p2));
		IPoint3D p6 = p1.movePointByVector(new Vector3D(p0,p3));

		// other 3 rectangles
		boxFaces[3] = new Rectangle(p3,p5,p6);
		boxFaces[4] = new Rectangle(p1,p4,p6);
		boxFaces[5] = new Rectangle(p2,p4,p5);
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
			if (name.equals("p0")){
				setP0(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("p1")){
				setP1(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("p2")){
				setP2(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("p3")){
				setP3(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
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
		for (int i = 0; i < boxFaces.length; i++) {
			if (boxFaces[i].isOnSamePlane(point3d)) {
				Point2D point2d = boxFaces[i].parametrize(point3d);
				return point2d;
			}
		}
		return null;
	}
}