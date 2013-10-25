package lighting;

import java.io.FileNotFoundException;
import application.Parser.ParseException;
import vectors.*;
import colors.*;

/**
 * This class represents a point light.
 *
 */
public class PointLight extends AbstractLight {
	//Attenuation factors; default = (1,0,0)
	private double kc;
	private double kl;
	private double kq;
	
	//Position
	private IPoint3D position;
	
	/**
	 * Constructs a point light.
	 */
	public PointLight(){
		this.position = null;
		this.kc = 1;
		this.kl = 0;
		this.kq = 0;
	}
	
	/**
	 * Returns the position of the light.
	 * 
	 * @return The position of the light.
	 */
	public IPoint3D getPosition() {
		return position;
	}

	/**
	 * Sets the position of the light.
	 * 
	 * @param position New position for the light.
	 */
	public void setPosition(IPoint3D position) {
		this.position = position;
	}
	
	/**
	 * Returns the intensity of the light at a given point (considers attenuation).
	 * 
	 * @param point
	 * @return The intensity of the light at a given point.
	 */
	public IColor getIntensityForPoint(IPoint3D point) {
		double distance = position.getDistanceBetweenPoints(point);
		
		return getIntensity().multiplyByConstant(1 / (kc + kl*distance + kq*Math.pow(distance, 2)));
	}

	/**
	 * Finishes parsing the light and validates that all mandatory values were given and valid.
	 * 
	 * @throws ParseException
	 */
	@Override
	public void commit() throws ParseException {
		if (getPosition()==null){
			throw new ParseException("Parameters given for position of Point Light are not valid or missing");
		}
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
			if (name.equals("pos")){
				setPosition(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("attenuation")){
				setKc(Double.parseDouble(args[0]));
				setKl(Double.parseDouble(args[1]));
				setKq(Double.parseDouble(args[2]));
			}
		}catch (ArrayIndexOutOfBoundsException e){
			throw new NumberFormatException();
		}
	}

	/**
	 * Returns the Kc component of the attenuation.
	 * 
	 * @return The Kc component of the attenuation.
	 */
	public double getKc() {
		return kc;
	}

	/**
	 * Sets the Kc component of the attenuation.
	 * 
	 * @param kc
	 */
	public void setKc(double kc) {
		this.kc = kc;
	}

	/**
	 * Returns the Kl component of the attenuation.
	 * 
	 * @return The Kl component of the attenuation.
	 */
	public double getKl() {
		return kl;
	}

	/**
	 * Sets the Kl component of the attenuation.
	 * 
	 * @param kl
	 */
	public void setKl(double kl) {
		this.kl = kl;
	}

	/**
	 * Returns the Kq component of the attenuation.
	 * 
	 * @return The Kq component of the attenuation.
	 */
	public double getKq() {
		return kq;
	}

	/**
	 * Sets the Kq component of the attenuation.
	 * 
	 * @param kq
	 */
	public void setKq(double kq) {
		this.kq = kq;
	}

	/**
	 * Returns the direction vector from the given hit point to the light source.
	 * 
	 * @param hitPoint
	 * @return The direction vector from the given hit point to the light source.
	 */
	@Override
	public IVector3D getDirectionFromHitPointToLight(IPoint3D hitPoint) {
		return new Vector3D(hitPoint, getPosition()).normalize();
	}

	/**
	 * Returns the distance from the given hit point to the light source.
	 * 
	 * @param hitPoint
	 * @return The distance from the given hit point to the light source.
	 */
	@Override
	public double getDistanceFromHitPointToLight(IPoint3D hitPoint) {
		return getPosition().getDistanceBetweenPoints(hitPoint);
	}
}
