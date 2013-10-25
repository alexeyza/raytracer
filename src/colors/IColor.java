package colors;

/**
 * This interface represents a RGB color.
 * Each RGB channel is between 0 and 1. 
 *
 */
public interface IColor {
	
	/**
	 * Returns an int RGB representation of the color.
	 * 
	 * @return An int RGB representation of the color.
	 */
	public int getRGB();
	
	/**
	 * Returns the value of the red channel of the color (value is between 0 and 1).
	 * 
	 * @return The value of the red channel of the color (value is between 0 and 1).
	 */
	public double getRed();
	
	/**
	 * Returns the value of the green channel of the color (value is between 0 and 1).
	 * 
	 * @return The value of the green channel of the color (value is between 0 and 1).
	 */
	public double getGreen();
	
	/**
	 * Returns the value of the blue channel of the color (value is between 0 and 1).
	 * 
	 * @return The value of the blue channel of the color (value is between 0 and 1).
	 */
	public double getBlue();
	
	/**
	 * Clamps the values of the RGB channels (makes sure the values are between 0 and 1).
	 */
	public void clamp();
	
	/**
	 * Sets the value of the red channel of the color.
	 * 
	 * @param red New value for the red channel of the color (value must be between 0 and 1).
	 */
	public void setRed(double red);
	
	/**
	 * Sets the value of the green channel of the color.
	 * 
	 * @param green New value for the green channel of the color (value must be between 0 and 1).
	 */
	public void setGreen(double green);
	
	/**
	 * Sets the value of the blue channel of the color.
	 * 
	 * @param blue New value for the blue channel of the color (value must be between 0 and 1).
	 */
	public void setBlue(double blue);
	
	/**
	 * Returns the value of the red channel of the color (value is between 0 and 255).
	 * 
	 * @return The value of the red channel of the color (value is between 0 and 255).
	 */
	public int getRedScaled();
	
	/**
	 * Returns the value of the green channel of the color (value is between 0 and 255).
	 * 
	 * @return The value of the green channel of the color (value is between 0 and 255).
	 */
	public int getGreenScaled();
	
	/**
	 * Returns the value of the blue channel of the color (value is between 0 and 255).
	 * 
	 * @return The value of the blue channel of the color (value is between 0 and 255).
	 */
	public int getBlueScaled();
	
	/**
	 * Sets the value of the red channel of the color.
	 * 
	 * @param red New value for the red channel of the color (value must be between 0 and 255).
	 */
	public void setRedScaled(int red);
	
	/**
	 * Sets the value of the green channel of the color.
	 * 
	 * @param green New value for the green channel of the color (value must be between 0 and 255).
	 */
	public void setGreenScaled(int green);
	
	/**
	 * Sets the value of the blue channel of the color.
	 * 
	 * @param blue New value for the blue channel of the color (value must be between 0 and 255).
	 */
	public void setBlueScaled(int blue);
	
	/**
	 * Adds the color (values) to another color (values) and returns the result color (not changing the current color).
	 * 
	 * @param otherColor The color to add to this color.
	 * @return Result color.
	 */
	public IColor addColor(IColor otherColor);
	
	/**
	 * Multiplies the color (values) by a constant scalar (not changing the current color).
	 * 
	 * @param c The multiplication constant.
	 * @return Result color.
	 */
	public IColor multiplyByConstant(double c);
	
	/**
	 * Multiplies the color (values) by another color (values) and returns the result color (not changing the current color).
	 * 
	 * @param otherColor The multiplication color.
	 * @return Result color.
	 */
	public IColor multiplyByColor(IColor otherColor);
}
