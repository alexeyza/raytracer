package sceneObjects;

import java.io.FileNotFoundException;
import application.Parser.ParseException;
import scene.Intersection;
import surfaces.ISurface;
import vectors.*;

/**
 * This interface represents the objects to be used in the scene (i.e spehre , box).
 *
 */
public interface ISceneObject {

    /**
     * Parses the given parameters to create a scene object.
     * 
     * @param name The name of the parameter.
     * @param args The values for the parameter.
     * @throws NumberFormatException
     * @throws FileNotFoundException
     */
	public void parseParameter(String name, String[] args) throws NumberFormatException, FileNotFoundException;
	
	/**
	 * Return the intersection information for a given ray.
	 * Use the returned object to check if there was a 'hit' or 'miss'.
	 * 
	 * @param ray
	 * @return The intersection information for a given ray.
	 */
	public Intersection isIntersects(IRay ray);
	
	/**
	 * Finishes parsing the scene object and validates that all mandatory values were given and valid.
	 * 
	 * @throws ParseException
	 */
	public void commit() throws ParseException;
	
	/**
	 * Returns the surface of the object.
	 * 
	 * @return The surface of the object.
	 */
	public ISurface getSurface();
	
	/**
	 * Sets the surface of the object (i.e Flat surface).
	 * 
	 * @param surface
	 */
	public void setSurface(ISurface surface);
	
	/**
	 * Returns the 2D point which maps the given 3D point to a texture (or checkers pattern).
	 * 
	 * @param point3d
	 * @return The 2D point which maps the given 3D point to a texture (or checkers pattern).
	 */
	public Point2D parametrize(IPoint3D point3d);
}
