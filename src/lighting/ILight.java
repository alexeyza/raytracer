package lighting;

import java.io.FileNotFoundException;
import java.util.Iterator;
import colors.IColor;
import vectors.*;
import application.Parser.ParseException;

/**
 * This interface represents a light object in the scene.
 *
 */
public interface ILight {

	/**
	 * Returns the intensity (color) of the light.
	 *  
	 * @return The intensity of the light.
	 */
	public IColor getIntensity();
	
	/**
	 * Sets the intensity (color) of the light.
	 * 
	 * @param intensity New intensity for the light.
	 */
	public void setIntensity(IColor intensity);
	
	/**
	 * Returns the direction vector from the given hit point to the light source.
	 * 
	 * @param hitPoint
	 * @return The direction vector from the given hit point to the light source.
	 */
	public IVector3D getDirectionFromHitPointToLight(IPoint3D hitPoint);
	
	/**
	 * Returns the distance from the given hit point to the light source.
	 * 
	 * @param hitPoint
	 * @return The distance from the given hit point to the light source.
	 */
	public double getDistanceFromHitPointToLight(IPoint3D hitPoint);

	/**
	 * Returns the intensity of the light at a given point (considers attenuation).
	 * 
	 * @param point
	 * @return The intensity of the light at a given point.
	 */
    public IColor getIntensityForPoint(IPoint3D point);
    
    /**
     * Returns the intensity of the light at a given point (considers attenuation).
     * 
     * @param point
     * @param normal The normal of the surface at the given point.
     * @return The intensity of the light at a given point.
     */
    public IColor getIntensityForPoint(IPoint3D point, IVector3D normal);
    
    /**
     * Parses the given parameters to create a light source.
     * 
     * @param name The name of the parameter.
     * @param args The values for the parameter.
     * @throws NumberFormatException
     * @throws FileNotFoundException
     */
	public void parseParameter(String name, String[] args) throws NumberFormatException, FileNotFoundException;
	
	/**
	 * Returns an iterator for the light source.
	 * If it is a single light source, it will return an iterator with itself inside.
	 * Otherwise it will return an iterator with multiple inner light sources. (i.e area light).
	 * 
	 * @return Iterator for all the inner light sources.
	 */
	public Iterator<ILight> getIterator();
	
	/**
	 * Finishes parsing the light and validates that all mandatory values were given and valid.
	 * 
	 * @throws ParseException
	 */
	public void commit() throws ParseException;
}
