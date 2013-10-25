package lighting;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import vectors.*;
import colors.*;
import application.Parser.ParseException;

/**
 * This abstract class holds all the shared functionality for all lights. 
 * 
 */
public abstract class AbstractLight implements ILight{
	private IColor intensity;
	
	/**
	 * Constructs an abstract light (this will be called by all implementing lights).
	 */
	public AbstractLight(){
		intensity = new Color(1,1,1);
	}
	
	/**
	 * Returns the intensity (color) of the light.
	 *  
	 * @return The intensity of the light.
	 */
	public IColor getIntensity() {
		return intensity;
	}
	
	/**
	 * Sets the intensity (color) of the light.
	 * 
	 * @param intensity New intensity for the light.
	 */
	public void setIntensity(IColor intensity) {
		this.intensity = intensity;
	}
    
    /**
     * Parses the given parameters to create a light source (only parameters which are shared for all lights).
     * 
     * @param name The name of the parameter.
     * @param args The values for the parameter.
     * @throws NumberFormatException
     * @throws FileNotFoundException
     */
	public void parseParameter(String name, String[] args) throws NumberFormatException, FileNotFoundException{
		try{
			if (name.equals("color")){
				setIntensity(new Color(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
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
	public void commit() throws ParseException{
	}
	
	/**
	 * Returns the intensity of the light at a given point (considers attenuation).
	 * Note: Each inheriting class must override this method or the next method.
	 * 
	 * @param point
	 * @return The intensity of the light at a given point.
	 */
	public IColor getIntensityForPoint(IPoint3D point){
		return getIntensityForPoint(point, null);
	}
	
    /**
     * Returns the intensity of the light at a given point (considers attenuation).
	 * Note: Each inheriting class must override this method or the previous method.
     * 
     * @param point
     * @param normal The normal of the surface at the given point.
     * @return The intensity of the light at a given point.
     */
	public IColor getIntensityForPoint(IPoint3D point, IVector3D normal) {
		return getIntensityForPoint(point);
	}
	
	/**
	 * Returns an iterator for the light source.
	 * If it is a single light source, it will return an iterator with itself inside.
	 * Otherwise it will return an iterator with multiple inner light sources. (i.e area light).
	 * 
	 * @return Iterator for all the inner light sources.
	 */
	public Iterator<ILight> getIterator(){
		ILight[] arr = new ILight[1];
		arr[0] = this;
		return Arrays.asList(arr).iterator();
	}
}
