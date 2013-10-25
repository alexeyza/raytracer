package surfaces;

import java.io.FileNotFoundException;

import application.Parser.ParseException;

import scene.*;
import colors.IColor;

/**
 * This interface represents the surface of each object in the scene.
 * There are several surfaces (i.e Flat, Checkers , Texture).
 *
 */
public interface ISurface {

	/**
	 * Returns the type of the surface.
	 * 
	 * @return The type of the surface.
	 */
	public String getType();
	
	/**
	 * Returns the diffuse intensity (color) of the surface.
	 * 
	 * @return The diffuse intensity (color) of the surface.
	 */
	public IColor getDiffuse();
	
	/**
	 * Sets the diffuse intensity (color) of the surface.
	 * 
	 * @param diffuse
	 */
	public void setDiffuse(IColor diffuse);
	
	/**
	 * Returns the specular intensity (color) of the surface.
	 * 
	 * @return The specular intensity (color) of the surface.
	 */
	public IColor getSpecular();
	
	/**
	 * Sets the specular intensity (color) of the surface.
	 * 
	 * @param specular
	 */
	public void setSpecular(IColor specular);
	
	/**
	 * Returns the ambient intensity (color) of the surface.
	 * 
	 * @return The ambient intensity (color) of the surface.
	 */
	public IColor getAmbient();
	
	/**
	 * Sets the ambient intensity (color) of the surface.
	 * 
	 * @param ambient
	 */
	public void setAmbient(IColor ambient);
	
	/**
	 * Returns the emission intensity (color) of the surface.
	 * 
	 * @return The emission intensity (color) of the surface.
	 */
	public IColor getEmission();
	
	/**
	 * Sets the emission intensity (color) of the surface.
	 * 
	 * @param emission
	 */
	public void setEmission(IColor emission);
	
	/**
	 * Returns the shininess factor of the surface.
	 * 
	 * @return The shininess factor of the surface.
	 */
	public double getShininess();
	
	/**
	 * Sets the shininess factor of the surface (default 100).
	 * 
	 * @param shininess
	 */
	public void setShininess(double shininess);
	
	/**
	 * Returns the reflectance coefficient of the surface.
	 * 
	 * @return The reflectance coefficient of the surface.
	 */
	public double getReflectance();
	
	/**
	 * Sets the reflectance coefficient of the surface.
	 * 
	 * @param reflectance
	 */
	public void setReflectance(double reflectance);
	
	/**
	 * Returns the color of the surface at a given intersection point.
	 * 
	 * @param hit The intersection information.
	 * @param scene The scene.
	 * @param iteration The iteration number (used for reflectance). Use the value 0.
	 * @return The color of the surface at a given intersection point.
	 */
	public IColor getColor(Intersection hit, Scene scene, int iteration);
	
    /**
     * Parses the given parameters to create a surface for a scene object.
     * 
     * @param name The name of the parameter.
     * @param args The values for the parameter.
     * @throws NumberFormatException
     * @throws FileNotFoundException
     */
	public void parseParameter(String name, String[] args) throws NumberFormatException, FileNotFoundException;
	
	/**
	 * Finishes parsing the surface and validates that all mandatory values were given and valid.
	 * 
	 * @throws ParseException
	 */
	public void commit() throws ParseException;
}
