package vectors;

/**
 * This class represents a vector in 3D.
 * 
 */
public class Vector3D implements IVector3D {
	private IPoint3D endPoint;
	
	/**
	 * Constructs a vector in 3D.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector3D(double x, double y, double z){
		this(new Point3D(x,y,z));
	}
	
	/**
	 * Constructs a vector in 3D.
	 * 
	 * @param endPoint
	 */
	public Vector3D(IPoint3D endPoint){
		this.endPoint = endPoint;
	}
	
	/**
	 * Constructs a vector in 3D.
	 * 
	 * @param origin
	 * @param endPoint
	 */
	public Vector3D(IPoint3D origin, IPoint3D endPoint){
		this.endPoint = new Point3D(endPoint.getX()-origin.getX(),endPoint.getY()-origin.getY(),endPoint.getZ()-origin.getZ());
	}
	
	/**
	 * Returns the result vector of multiplying this vector by the given scalar (does not change this vector).
	 * 
	 * @param scalar
	 * @return The result vector of multiplying this vector by the given scalar.
	 */
	@Override
	public IVector3D multiplyByScalar(double scalar){
		IPoint3D p = new Point3D((endPoint.getX()*scalar),(endPoint.getY()*scalar),(endPoint.getZ()*scalar));
		return new Vector3D(p);
	}

	/**
	 * Returns the result of applying dot product on this vector and the given vector (does not change this vector).
	 * 
	 * @param otherVector
	 * @return The result of applying dot product on this vector and the given vector.
	 */
	@Override
	public double dotProduct(IVector3D otherVector) {
		double dx = endPoint.getX()*otherVector.getEndPoint().getX();
		double dy = endPoint.getY()*otherVector.getEndPoint().getY();
		double dz = endPoint.getZ()*otherVector.getEndPoint().getZ();
		return dx+dy+dz;
	}

	/**
	 * Returns the result vector of applying cross product on this vector and the given vector (does not change this vector).
	 * 
	 * @param otherVector
	 * @return The result vector of applying cross product on this vector and the given vector.
	 */
	@Override
	public IVector3D crossProduct(IVector3D otherVector) {
		double x = endPoint.getY()*otherVector.getEndPoint().getZ() - endPoint.getZ()*otherVector.getEndPoint().getY();
		double y = endPoint.getZ()*otherVector.getEndPoint().getX() - endPoint.getX()*otherVector.getEndPoint().getZ();
		double z = endPoint.getX()*otherVector.getEndPoint().getY() - endPoint.getY()*otherVector.getEndPoint().getX();
		return new Vector3D(x,y,z);
	}
	
	/**
	 * Returns the end point of the vector.
	 * 
	 * @return The end point of the vector.
	 */
	@Override
	public IPoint3D getEndPoint() {
		return new Point3D(endPoint.getX(),endPoint.getY(),endPoint.getZ());
	}
	
	/**
	 * Return the origin point of the vector.
	 * 
	 * @return The origin point of the vector.
	 */
	@Override
	public IPoint3D getOrigin() {
		return new Point3D(0d,0d,0d);
	}

	/**
	 * Returns a normalized vector of this vector (does not change this vector).
	 * 
	 * @return A normalized vector of this vector.
	 */
	@Override
	public IVector3D normalize() {
		double x = getEndPoint().getX();
		double y = getEndPoint().getY();
		double z = getEndPoint().getZ();
		double vectorLength = x*x + y*y + z*z;
        if ((vectorLength != 0)&&(vectorLength != 1)){
        	vectorLength = (1 / Math.sqrt(vectorLength));
        }
        x *= vectorLength;
        y *= vectorLength;
        z *= vectorLength;
		return new Vector3D(new Point3D(x,y,z));
	}
	
	/**
	 * Returns the result vector of adding this vector to the given vector (does not change this vector).
	 * 
	 * @param otherVector
	 * @return The result vector of adding this vector to the given vector.
	 */
	@Override
	public IVector3D addVector(IVector3D otherVector) {
		double newX = getEndPoint().getX() + otherVector.getEndPoint().getX();
		double newY = getEndPoint().getY() + otherVector.getEndPoint().getY();
		double newZ = getEndPoint().getZ() + otherVector.getEndPoint().getZ();
		return new Vector3D(newX, newY, newZ);
	}
	
	/**
	 * Returns the result vector of subtracting the given vector from this vector (does not change this vector).
	 * 
	 * @param otherVector
	 * @return The result vector of subtracting the given vector from this vector.
	 */
	@Override
	public Vector3D subtractVector(IVector3D otherVector) {
		double newX = getEndPoint().getX() - otherVector.getEndPoint().getX();
		double newY = getEndPoint().getY() - otherVector.getEndPoint().getY();
		double newZ = getEndPoint().getZ() - otherVector.getEndPoint().getZ();
		return new Vector3D(newX, newY, newZ);
	}
	
	/**
	 * Returns the String representation of the vector.
	 * 
	 * @return The String representation of the vector.
	 */
	@Override
	public String toString(){
		return (endPoint.toString());
	}

	/**
	 * Returns 'true' if this vector equals (length and direction) to the given vector, otherwise returns 'false'.
	 * 
	 * @param otherVector
	 * @return 'true' if this vector equals (length and direction) to the given vector, otherwise returns 'false'.
	 */
	@Override
	public boolean equals(IVector3D otherVector) {
		if (getEndPoint().equals(otherVector.getEndPoint())){
			return true;
		}
		return false;
	}
	
	/**
	 * Returns 'true' if this vector is co-linear to the given vector, otherwise returns 'false'.
	 * 
	 * @param otherVector
	 * @return 'true' if this vector is co-linear to the given vector, otherwise returns 'false'.
	 */
	@Override
	public boolean isCoLinear(IVector3D otherVector) {
		IVector3D vThisNorm = this.normalize();
		IVector3D vOtherNorm = otherVector.normalize();
		// Two vectors, one of which is a non-zero scalar multiple of the other.
		return ((vThisNorm.equals(vOtherNorm))||(vThisNorm.equals(vOtherNorm.multiplyByScalar(-1))));
	}

	/**
	 * Returns the reflected (mirror) vector to this vector, using the given normal.
	 * 
	 * @param mirrorVector
	 * @return The reflected (mirror) vector to this vector.
	 */
	@Override
	public IVector3D mirror(IVector3D mirrorVector) {
		// I = incident vector
		// N = normal of surface

		// the reflected vector is:

		// I' = I - 2*(N.I)*N;

		double dot = this.dotProduct(mirrorVector);
		return this.multiplyByScalar(-1).addVector(mirrorVector.multiplyByScalar(2d*dot)).normalize();
	}
}
