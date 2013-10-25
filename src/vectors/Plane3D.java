package vectors;

/**
 * This class represents a plane in 3D.
 * 
 */
public class Plane3D implements IPlane3D{

	private IVector3D normalVector;
	private IPoint3D normalPoint;
	
	/**
	 * Constructs a plane.
	 * 
	 * @param normalVector
	 * @param point
	 */
	public Plane3D(IVector3D normalVector, IPoint3D point){
		this.normalVector = normalVector;
		normalPoint = point;
	}

	/**
	 * Returns the point of intersection for the given ray and the plane.
	 * 
	 * @param ray
	 * @return The point of intersection for the given ray and the plane.
	 */
	@Override
	public IPoint3D rayPlaneIntersection(IRay ray) {
		IPoint3D p0 = ray.getP0();
		IVector3D v = ray.getV();
		
		// from slides -> c = N * -p0
		double c = -1f * normalVector.dotProduct(normalPoint.toVector());
		
		// from slides -> V*N
		double dotProd = v.dotProduct(normalVector);
		
		if (dotProd==0){
			// no such t exist that fits the Ray formula and the Plane Formula
			return null;
		}
		
		// from slides -> t = -1 * (p0*N + c)/(V*N);
		double t = -1 * (p0.toVector().dotProduct(normalVector)+c) / dotProd;
		
		if (t<0) {
			// then the ray is backwards and not to be counted
			return null;
		}
		
		// t>=0 which means there is an intersection with the plane
		IPoint3D hitPoint = ray.getPointOnRay(t);
		
		return hitPoint;
	}

	/**
	 * Returns the normal vector of the plane.
	 * 
	 * @param ray
	 * @return The normal vector of the plane.
	 */
	@Override
	public IVector3D getNormal(IRay ray) {
		IVector3D rayVec = ray.getV().normalize().multiplyByScalar(-1);
		if (normalVector.dotProduct(rayVec)>=0){
			return normalVector.normalize();
		}
		return normalVector.multiplyByScalar(-1).normalize();
	}
	
	/**
	 * Returns 'true' if the given hitPoint is on the plane, otherwise returns 'false'.
	 * 
	 * @param hitPoint
	 * @return 'true' if the given hitPoint is on the plane, otherwise returns 'false'.
	 */
	public boolean isOnSamePlane(IPoint3D hitPoint){
		IVector3D vec = new Vector3D(normalPoint,hitPoint);
		double result = normalVector.dotProduct(vec);
		if (Math.abs(result)<0.00001){
			return true;
		}
		return false;
	}
}
