package vectors;

/**
 * This class represents a point in 3D.
 *
 */
public class Point3D implements IPoint3D {
	private double x;
	private double y;
	private double z;
	
	/**
	 * Constructs a point in 3D.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Returns the X coordinate of the point.
	 * 
	 * @return The X coordinate of the point.
	 */
	@Override
	public double getX(){
		return x;
	}
	
	/**
	 * Returns the Y coordinate of the point.
	 * 
	 * @return The Y coordinate of the point.
	 */
	@Override
	public double getY(){
		return y;
	}
	
	/**
	 * Returns the Z coordinate of the point.
	 * 
	 * @return The Z coordinate of the point.
	 */
	@Override
	public double getZ(){
		return z;
	}

	/**
	 * Sets the X coordinate of the point.
	 * 
	 * @param x
	 */
	@Override
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Sets the Y coordinate of the point.
	 * 
	 * @param y
	 */
	@Override
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Sets the Z coordinate of the point.
	 * 
	 * @param z
	 */
	@Override
	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * Returns the point which we get after moving from this point at the direction of the given vector.
	 * 
	 * @param v
	 * @return The point which we get after moving from this point at the direction of the given vector.
	 */
	@Override
	public IPoint3D movePointByVector(IVector3D v) {
		double nx = getX() + v.getEndPoint().getX();
		double ny = getY() + v.getEndPoint().getY();
		double nz = getZ() + v.getEndPoint().getZ();
		return new Point3D(nx,ny,nz);
	}
	
	/**
	 * Returns the distance between this point and the given point.
	 * 
	 * @param otherPoint
	 * @return The distance between this point and the given point.
	 */
	@Override
	public double getDistanceBetweenPoints(IPoint3D otherPoint){
		double dx = getX() - otherPoint.getX();
		double dy = getY() - otherPoint.getY();
		double dz = getZ() - otherPoint.getZ();
		return Math.sqrt(dx*dx+dy*dy+dz*dz);		
	}
	
	/**
	 * Returns the string representation of the point.
	 * 
	 * @return The string representation of the point.
	 */
	@Override
	public String toString(){
		return "("+getX()+","+getY()+","+getZ()+")";
	}

	/**
	 * Returns a vector which starts at the origin and ends at this point.
	 * 
	 * @return A vector which starts at the origin and ends at this point.
	 */
	@Override
	public IVector3D toVector() {
		return new Vector3D(getX(),getY(),getZ());
	}

	/**
	 * Returns 'true' if the coordinate values of this point are equal to the given point values, otherwise returns 'false'.
	 * 
	 * @param otherPoint
	 * @return 'true' if the coordinate values of this point are equal to the given point values, otherwise returns 'false'.
	 */
	@Override
	public boolean equals(IPoint3D otherPoint) {
		if ((getX()==otherPoint.getX())&&(getY()==otherPoint.getY())&&(getZ()==otherPoint.getZ())){
			return true;
		}
		return false;
	}

	/**
	 * Returns 'true' if this point is co-linear to the given points, otherwise returns 'false'.
	 * 
	 * @param otherPoint1
	 * @param otherPoint2
	 * @return 'true' if this point is co-linear to the given points, otherwise returns 'false'.
	 */
	@Override
	public boolean isCoLinear(IPoint3D otherPoint1, IPoint3D otherPoint2) {
		IVector3D v1 = new Vector3D(this,otherPoint1);
		IVector3D v2 = new Vector3D(this,otherPoint2);
		return v1.isCoLinear(v2);
	}
	
	/**
	 * Returns a point which adds the coordinate values of this point to the coordinate values of the given point 
	 * (not changing the coordinate values of this point).
	 * 
	 * @param otherPoint
	 * @return A point which adds the coordinate values of this point to the coordinate values of the given point.
	 */
	public IPoint3D add(IPoint3D otherPoint) {
		double x = getX() + otherPoint.getX();
		double y = getY() + otherPoint.getY();
		double z = getZ() + otherPoint.getZ();
		
		return new Point3D(x, y, z);
	}
	
	/**
	 * Returns a point which multiplies the coordinate values of this point by the given scalar
	 * (not changing the coordinate values of this point).
	 * 
	 * @param scalar
	 * @return A point which multiplies the coordinate values of this point by the given scalar.
	 */
	public IPoint3D multiplyByScalar(double scalar) {
		double x = getX() * scalar;
		double y = getY() * scalar;
		double z = getZ() * scalar;
		
		return new Point3D(x, y, z);
	}
}