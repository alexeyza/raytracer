package surfaces;

import java.io.FileNotFoundException;
import scene.Intersection;
import vectors.*;
import colors.*;

/**
 * This class represents the checkers pattern surface.
 *
 */
public class CheckersSurface extends AbstractSurface {

	private double checkersSize;
	private IColor checkersDiffuse1;
	private IColor checkersDiffuse2;

	/**
	 * Constructs a checkers surface.
	 */
	public CheckersSurface() {
		this.type = "checkers";
		this.emission = new Color(0, 0, 0);
		this.ambient = new Color(0.1, 0.1, 0.1);
		this.diffuse = new Color(0.7, 0.7, 0.7);
		this.specular = new Color(1, 1, 1);
		this.shininess = 100;
		this.reflectance = 0;
		this.checkersSize = 0.1;
		this.checkersDiffuse1 = new Color(1, 1, 1);
		this.checkersDiffuse2 = new Color(0.2, 0.2, 0.2);
	}

	/**
	 * Returns the type of the surface.
	 * 
	 * @return The type of the surface.
	 */
	@Override
	public String getType() {
		return type;
	}

    /**
     * Parses the given parameters to create a surface for a scene object.
     * 
     * @param name The name of the parameter.
     * @param args The values for the parameter.
     * @throws NumberFormatException
     * @throws FileNotFoundException
     */
	@Override
	public void parseParameter(String name, String[] args)
	throws NumberFormatException, FileNotFoundException {
		try{
			if (name.equals("mtl-diffuse")){
				setDiffuse(new Color(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
			}
			if (name.equals("mtl-specular")){
				setSpecular(new Color(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
			}
			if (name.equals("mtl-ambient")){
				setAmbient(new Color(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
			}
			if (name.equals("mtl-emission")){
				setEmission(new Color(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
			}
			if (name.equals("mtl-shininess")){
				setShininess(Double.parseDouble(args[0]));
			}
			if (name.equals("reflectance")){
				setReflectance(Double.parseDouble(args[0]));
			}
			if (name.equals("checkers-size")){
				setCheckersSize(Double.parseDouble(args[0]));
			}
			if (name.equals("checkers-diffuse1")){
				setCheckersDiffuse1(new Color(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
			}
			if (name.equals("checkers-diffuse2")){
				setCheckersDiffuse2(new Color(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
			}
		}catch (ArrayIndexOutOfBoundsException e){
			throw new NumberFormatException();
		}
	}

	/**
	 * Returns the size of a single square in the checkers pattern.
	 *  
	 * @return The size of a single square in the checkers pattern.
	 */
	public double getCheckersSize() {
		return checkersSize;
	}

	/**
	 * Sets the size of a single square in the checkers pattern.
	 * 
	 * @param checkersSize
	 */
	public void setCheckersSize(double checkersSize) {
		this.checkersSize = checkersSize;
	}

	/**
	 * Returns the first diffuse intensity (color) of the checkers pattern.
	 * 
	 * @return The first diffuse intensity (color) of the checkers pattern.
	 */
	public IColor getCheckersDiffuse1() {
		return checkersDiffuse1;
	}

	/**
	 * Sets the first diffuse intensity (color) of the checkers pattern.
	 * 
	 * @param checkersDiffuse1
	 */
	public void setCheckersDiffuse1(IColor checkersDiffuse1) {
		this.checkersDiffuse1 = checkersDiffuse1;
	}

	/**
	 * Returns the second diffuse intensity (color) of the checkers pattern.
	 * 
	 * @return The second diffuse intensity (color) of the checkers pattern.
	 */
	public IColor getCheckersDiffuse2() {
		return checkersDiffuse2;
	}

	/**
	 * Sets the second diffuse intensity (color) of the checkers pattern.
	 * 
	 * @param checkersDiffuse1
	 */
	public void setCheckersDiffuse2(IColor checkersDiffuse2) {
		this.checkersDiffuse2 = checkersDiffuse2;
	}
	
	/**
	 * Returns the diffuse intensity for a given hit point.
	 * 
	 * @param hit
	 * @return The diffuse intensity for a given hit point.
	 */
	protected IColor getDiffuseForHitPoint(Intersection hit) {
		IPoint3D hitPoint = hit.getIntersectionPoint();
		Point2D hitParam = hit.getIntersectionObject().parametrize(hitPoint);
		if (hitParam != null) {
			if ((int)(hitParam.getX()/checkersSize)%2 + (int)(hitParam.getY()/checkersSize)%2 == 1) {
				return checkersDiffuse1;
			}
			else {
				return checkersDiffuse2;
			}
		}
		return checkersDiffuse1;
	}
}
