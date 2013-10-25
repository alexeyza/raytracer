package vectors;

/**
 * This interface represents a vector in 3D.
 *
 */
public interface IVector3D {
	
	/**
	 * Returns the result vector of multiplying this vector by the given scalar (does not change this vector).
	 * 
	 * @param scalar
	 * @return The result vector of multiplying this vector by the given scalar.
	 */
	public IVector3D multiplyByScalar(double scalar);
	
	/**
	 * Returns the result of applying dot product on this vector and the given vector (does not change this vector).
	 * 
	 * @param otherVector
	 * @return The result of applying dot product on this vector and the given vector.
	 */
	public double dotProduct(IVector3D otherVector);
	
	/**
	 * Returns the result vector of applying cross product on this vector and the given vector (does not change this vector).
	 * 
	 * @param otherVector
	 * @return The result vector of applying cross product on this vector and the given vector.
	 */
	public IVector3D crossProduct(IVector3D otherVector);
	
	/**
	 * Return the origin point of the vector.
	 * 
	 * @return The origin point of the vector.
	 */
	public IPoint3D getOrigin();
	
	/**
	 * Returns the end point of the vector.
	 * 
	 * @return The end point of the vector.
	 */
	public IPoint3D getEndPoint();

	/**
	 * Returns a normalized vector of this vector (does not change this vector).
	 * 
	 * @return A normalized vector of this vector.
	 */
	public IVector3D normalize();
	
	/**
	 * Returns the result vector of adding this vector to the given vector (does not change this vector).
	 * 
	 * @param otherVector
	 * @return The result vector of adding this vector to the given vector.
	 */
	public IVector3D addVector(IVector3D otherVector);
	
	/**
	 * Returns the result vector of subtracting the given vector from this vector (does not change this vector).
	 * 
	 * @param otherVector
	 * @return The result vector of subtracting the given vector from this vector.
	 */
	public Vector3D subtractVector(IVector3D otherVector);
	
	/**
	 * Returns 'true' if this vector equals (length and direction) to the given vector, otherwise returns 'false'.
	 * 
	 * @param otherVector
	 * @return 'true' if this vector equals (length and direction) to the given vector, otherwise returns 'false'.
	 */
	public boolean equals(IVector3D otherVector);
	
	/**
	 * Returns 'true' if this vector is co-linear to the given vector, otherwise returns 'false'.
	 * 
	 * @param otherVector
	 * @return 'true' if this vector is co-linear to the given vector, otherwise returns 'false'.
	 */
	public boolean isCoLinear(IVector3D otherVector);
	
	/**
	 * Returns the String representation of the vector.
	 * 
	 * @return The String representation of the vector.
	 */
	public String toString();
	
	/**
	 * Returns the reflected (mirror) vector to this vector, using the given normal.
	 * 
	 * @param mirrorVector
	 * @return The reflected (mirror) vector to this vector.
	 */
	public IVector3D mirror(IVector3D mirrorVector);
}
