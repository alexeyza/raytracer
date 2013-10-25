package lighting;

import java.io.FileNotFoundException;
import colors.IColor;
import application.Parser.ParseException;
import vectors.*;

/**
 * This class represents a directional light source (like the sun).
 *
 */
public class DirectionalLight extends AbstractLight {

	private IVector3D direction;
	
	/**
	 * Constructs a directional light source (direction must be set separately).
	 */
	public DirectionalLight(){
		direction = null;
	}
	
	/**
	 * Returns the intensity of the light at a given point (considers attenuation).
	 * 
	 * @param point
	 * @return The intensity of the light at a given point.
	 */
	public IColor getIntensityForPoint(IPoint3D point) {
		return getIntensity();
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
			if (name.equals("direction")){
				setDirection(new Vector3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
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
		if (getDirection()==null){
			throw new ParseException("Parameters given for direction of Direct Light are not valid or missing");
		}
	}
	
	/**
	 * Sets the direction of the directional light source.
	 * 
	 * @param direction The direction vector for the light source.
	 */
	public void setDirection(IVector3D direction) {
		this.direction = direction;
	}

	/**
	 * Returns the direction vector of the light.
	 * 
	 * @return The direction vector of the light.
	 */
	public IVector3D getDirection() {
		return direction;
	}

	/**
	 * Returns the direction vector from the given hit point to the light source.
	 * 
	 * @param hitPoint
	 * @return The direction vector from the given hit point to the light source.
	 */
	@Override
	public IVector3D getDirectionFromHitPointToLight(IPoint3D hitPoint) {
		return direction.multiplyByScalar(-1).normalize();
	}

	/**
	 * Returns the distance from the given hit point to the light source.
	 * 
	 * @param hitPoint
	 * @return The distance from the given hit point to the light source.
	 */
	@Override
	public double getDistanceFromHitPointToLight(IPoint3D hitPoint) {
		return Double.POSITIVE_INFINITY;
	}
}