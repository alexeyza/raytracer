package vectors;

/**
 * 
 * This interface represents a plane in 3D.
 *
 */
public interface IPlane3D {
	
	/**
	 * Returns the point of intersection for the given ray and the plane.
	 * 
	 * @param ray
	 * @return The point of intersection for the given ray and the plane.
	 */
	public IPoint3D rayPlaneIntersection(IRay ray);
	
	/**
	 * Returns the normal vector of the plane.
	 * 
	 * @param ray
	 * @return The normal vector of the plane.
	 */
	public IVector3D getNormal(IRay ray);
	
	/**
	 * Returns 'true' if the given hitPoint is on the plane, otherwise returns 'false'.
	 * 
	 * @param hitPoint
	 * @return 'true' if the given hitPoint is on the plane, otherwise returns 'false'.
	 */
	public boolean isOnSamePlane(IPoint3D hitPoint);

}
