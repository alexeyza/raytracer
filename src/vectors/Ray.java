package vectors;

/**
 * This class represents a ray in 3D.
 *  A ray is represented by the following equation: P = p0 + t*V.
 *
 */
public class Ray implements IRay {
	private IVector3D v;
	private IPoint3D p0;
	
	/**
	 * Constructs a ray in 3D.
	 * 
	 * @param p0
	 * @param v
	 */
	public Ray (IPoint3D p0, IVector3D v){
		this.p0 = p0;
		this.v = v.normalize();
	}

	/**
	 * Returns the starting point of the ray.
	 * 
	 * @return The starting point of the ray.
	 */
	@Override
	public IPoint3D getP0() {
		return p0;
	}

	/**
	 * Returns the direction vector of the ray.
	 * 
	 * @return The direction vector of the ray.
	 */
	@Override
	public IVector3D getV() {
		return v;
	}

	/**
	 * Returns the String representation of the ray.
	 * 
	 * @return The String representation of the ray.
	 */
	@Override
	public String toString(){
		return (p0+" "+v);
	}

	/**
	 * Returns a point on the ray which is calculated by the following equation: P = p0 + t*V.
	 * 
	 * @param t
	 * @return A point on the ray which is calculated by the following equation: P = p0 + t*V.
	 */
	@Override
	public IPoint3D getPointOnRay(double t) {
		return getP0().movePointByVector(getV().multiplyByScalar(t));
	}
}
