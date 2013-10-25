package vectors;

/**
 * This interface represents a ray in 3D.
 * A ray is represented by the following equation: P = p0 + t*V
 *
 */
public interface IRay {
	
	/**
	 * Returns the direction vector of the ray.
	 * 
	 * @return The direction vector of the ray.
	 */
	public IVector3D getV();
	
	/**
	 * Returns the starting point of the ray.
	 * 
	 * @return The starting point of the ray.
	 */
	public IPoint3D getP0();

	/**
	 * Returns a point on the ray which is calculated by the following equation: P = p0 + t*V.
	 * 
	 * @param t
	 * @return A point on the ray which is calculated by the following equation: P = p0 + t*V.
	 */
	public IPoint3D getPointOnRay(double t);
	
	/**
	 * Returns the String representation of the ray.
	 * 
	 * @return The String representation of the ray.
	 */
	public String toString();
}
