package sceneObjects;

import surfaces.*;

/**
 * This abstract class holds all the shared functionality for all scene objects.
 *
 */
public abstract class SceneObject implements ISceneObject {
	
	private ISurface surface;
	
	/**
	 * Constructs a scene object.
	 */
	public SceneObject(){
		surface = new FlatSurface();
	}
	
	/**
	 * Solves a Quadratic equation.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return The result of the quadratic equation.
	 */
	protected static double[] solveEquation(double a, double b, double c){
		// find possible solutions (0,1 or 2 solutions)
		double delta = b*b - 4d*a*c;
		if (delta<0){
			return null;
		}
		double[] sol = new double[2];
		sol[0] = ((-1d*b) + (Math.sqrt(delta)))/(2d*a);
		sol[1] = ((-1d*b) - (Math.sqrt(delta)))/(2d*a);
		return sol;
	}
	
	/**
	 * Returns the surface of the object.
	 * 
	 * @return The surface of the object.
	 */
	public ISurface getSurface(){
		return surface;
	}
	
	/**
	 * Sets the surface of the object (i.e Flat surface).
	 * 
	 * @param surface
	 */
	public void setSurface(ISurface surface){
		this.surface = surface;
	}
}