package lighting;

import java.io.FileNotFoundException;

import application.Parser.ParseException;

import vectors.IPoint3D;
import vectors.IVector3D;
import vectors.Vector3D;
import colors.Color;
import colors.IColor;

/**
 * This class represents a hemispherical light source (light which comes from the sky and ground).
 *
 */
public class HemisphericalLight extends AbstractLight {
	
	private IColor ground;
	private IVector3D up;
	
	/**
	 * Constructs a hemispherical light.
	 */
	public HemisphericalLight() {
		this.ground = new Color(0, 0, 0);
		this.up = new Vector3D(0, 1, 0);
	}

	/**
	 * Returns the intensity (color) of the ground.
	 * 
	 * @return
	 */
	public IColor getGround() {
		return ground;
	}

	/**
	 * Sets the intensity (color) of the ground.
	 * 
	 * @param ground
	 */
	public void setGround(IColor ground) {
		this.ground = ground;
	}

	/**
	 * Returns the up direction vector.
	 * 
	 * @return The up direction.
	 */
	public IVector3D getUp() {
		return up;
	}

	/**
	 * Sets the up direction vector.
	 * 
	 * @param up The new up direction.
	 */
	public void setUp(IVector3D up) {
		this.up = up;
	}

	/**
	 * Returns the direction vector from the given hit point to the light source.
	 * 
	 * @param hitPoint
	 * @return The direction vector from the given hit point to the light source.
	 */
	@Override
	public IVector3D getDirectionFromHitPointToLight(IPoint3D hitPoint) {
		return up.normalize();
	}

	/**
	 * Returns the distance from the given hit point to the light source.
	 * 
	 * @param hitPoint
	 * @return The distance from the given hit point to the light source.
	 */
	@Override
	public double getDistanceFromHitPointToLight(IPoint3D hitPoint) {
		return 0;
	}

    /**
     * Returns the intensity of the light at a given point (considers attenuation).
     * 
     * @param point
     * @param normal The normal of the surface at the given point.
     * @return The intensity of the light at a given point.
     */
	@Override
	public IColor getIntensityForPoint(IPoint3D point, IVector3D normal) {
		double theta = normal.normalize().dotProduct(up);
		double a = 0.5 + (0.5 * Math.cos(theta));
		return getIntensity().multiplyByConstant(a).addColor(ground.multiplyByConstant(1-a));
	}
	
    /**
     * Parses the given parameters to create a light source.
     * 
     * @param name The name of the parameter.
     * @param args The values for the parameter.
     * @throws NumberFormatException
     * @throws FileNotFoundException
     */
	@Override
	public void parseParameter(String name, String[] args) throws NumberFormatException, FileNotFoundException {
		super.parseParameter(name, args);
		try{
			if (name.equals("color-ground")){
				setGround(new Color(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("up-direction")){
				setUp(new Vector3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
		}catch (ArrayIndexOutOfBoundsException e){
			throw new NumberFormatException();
		}
	}
	
	/**
	 * Finishes parsing the light and validates that all mandatory values were given and valid.
	 * 
	 * @throws ParseException
	 */
	@Override
	public void commit() throws ParseException {
	}
}