package vectors;

/**
 * This interface represents a point in 3D.
 *
 */
public interface IPoint3D {
	
	/**
	 * Returns the X coordinate of the point.
	 * 
	 * @return The X coordinate of the point.
	 */
	public double getX();
	
	/**
	 * Returns the Y coordinate of the point.
	 * 
	 * @return The Y coordinate of the point.
	 */
	public double getY();
	
	/**
	 * Returns the Z coordinate of the point.
	 * 
	 * @return The Z coordinate of the point.
	 */
	public double getZ();
	
	/**
	 * Sets the X coordinate of the point.
	 * 
	 * @param x
	 */
	public void setX(double x);

	/**
	 * Sets the Y coordinate of the point.
	 * 
	 * @param y
	 */
	public void setY(double y);

	/**
	 * Sets the Z coordinate of the point.
	 * 
	 * @param z
	 */
	public void setZ(double z);
	
	/**
	 * Returns the point which we get after moving from this point at the direction of the given vector.
	 * 
	 * @param v
	 * @return The point which we get after moving from this point at the direction of the given vector.
	 */
	public IPoint3D movePointByVector(IVector3D v);

	/**
	 * Returns a vector which starts at the origin and ends at this point.
	 * 
	 * @return A vector which starts at the origin and ends at this point.
	 */
	public IVector3D toVector();
	
	/**
	 * Returns the distance between this point and the given point.
	 * 
	 * @param otherPoint
	 * @return The distance between this point and the given point.
	 */
	public double getDistanceBetweenPoints(IPoint3D otherPoint);
	
	/**
	 * Returns 'true' if the coordinate values of this point are equal to the given point values, otherwise returns 'false'.
	 * 
	 * @param otherPoint
	 * @return 'true' if the coordinate values of this point are equal to the given point values, otherwise returns 'false'.
	 */
	public boolean equals(IPoint3D otherPoint);
	
	/**
	 * Returns 'true' if this point is co-linear to the given points, otherwise returns 'false'.
	 * 
	 * @param otherPoint1
	 * @param otherPoint2
	 * @return 'true' if this point is co-linear to the given points, otherwise returns 'false'.
	 */
	public boolean isCoLinear(IPoint3D otherPoint1, IPoint3D otherPoint2);
	
	/**
	 * Returns the string representation of the point.
	 * 
	 * @return The string representation of the point.
	 */
	public String toString();
	
	/**
	 * Returns a point which adds the coordinate values of this point to the coordinate values of the given point 
	 * (not changing the coordinate values of this point).
	 * 
	 * @param otherPoint
	 * @return A point which adds the coordinate values of this point to the coordinate values of the given point.
	 */
	public IPoint3D add(IPoint3D otherPoint);
	
	/**
	 * Returns a point which multiplies the coordinate values of this point by the given scalar
	 * (not changing the coordinate values of this point).
	 * 
	 * @param scalar
	 * @return A point which multiplies the coordinate values of this point by the given scalar.
	 */
	public IPoint3D multiplyByScalar(double scalar);
}
