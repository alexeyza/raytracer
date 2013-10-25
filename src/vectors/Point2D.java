package vectors;

/**
 * This class represents a point in 2D.
 *
 */
public class Point2D {

	private double x;
	private double y;
	
	/**
	 * Constructs a point in 2D.
	 */
	public Point2D() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Constructs a point in 2D.
	 * 
	 * @param x
	 * @param y
	 */
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the X coordinate of the point.
	 * 
	 * @return The X coordinate of the point.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Sets the X coordinate of the point.
	 * 
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Returns the Y coordinate of the point.
	 * 
	 * @return The Y coordinate of the point.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Sets the Y coordinate of the point.
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Returns a normalized point of this point (does not change this point).
	 * 
	 * @return A normalized point of this point.
	 */
	public Point2D normalize() {
		double x = Math.max(Math.min(this.x, 1), 0);
		double y = Math.max(Math.min(this.y, 1), 0);
		
		return new Point2D(x, y);
	}
	
}
