package colors;

/**
 * This class represents a RGB color.
 * Each RGB channel is between 0 and 1. 
 *
 */
public class Color implements IColor {
	private double red;
	private double green;
	private double blue;
	private static final double RGB_SCALE = 255d;

	/**
	 * Constructs a new color (the given values must be between 0 and 1).
	 * 
	 * @param red Value for red channel.
	 * @param green Value for green channel.
	 * @param blue Value for blue channel.
	 */
	public Color(double red, double green, double blue){
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	/**
	 * Constructs a new color with default values of zero (Color = Black).
	 * 
	 */
	public Color(){
		this(0,0,0);
	}
	
	/**
	 * Returns the value of the blue channel of the color (value is between 0 and 1).
	 * 
	 * @return The value of the blue channel of the color (value is between 0 and 1).
	 */
	@Override
	public double getBlue() {
		return blue;
	}

	/**
	 * Returns the value of the green channel of the color (value is between 0 and 1).
	 * 
	 * @return The value of the green channel of the color (value is between 0 and 1).
	 */
	@Override
	public double getGreen() {
		return green;
	}

	/**
	 * Returns the value of the red channel of the color (value is between 0 and 1).
	 * 
	 * @return The value of the red channel of the color (value is between 0 and 1).
	 */
	@Override
	public double getRed() {
		return red;
	}
	
	/**
	 * Returns the value of the blue channel of the color (value is between 0 and 255).
	 * 
	 * @return The value of the blue channel of the color (value is between 0 and 255).
	 */
	@Override
	public int getBlueScaled() {
		return (int)(blue*RGB_SCALE);
	}

	/**
	 * Returns the value of the green channel of the color (value is between 0 and 255).
	 * 
	 * @return The value of the green channel of the color (value is between 0 and 255).
	 */
	@Override
	public int getGreenScaled() {
		return (int)(green*RGB_SCALE);
	}

	/**
	 * Returns the value of the red channel of the color (value is between 0 and 255).
	 * 
	 * @return The value of the red channel of the color (value is between 0 and 255).
	 */
	@Override
	public int getRedScaled() {
		return (int)(red*RGB_SCALE);
	}
	
	/**
	 * Returns an int RGB representation of the color.
	 * 
	 * @return An int RGB representation of the color.
	 */
	@Override
	public int getRGB() {
		return makeRGB(getRedScaled(), getGreenScaled(), getBlueScaled());
	}
	
	/**
	 * Sets the value of the blue channel of the color.
	 * 
	 * @param red New value for the blue channel of the color (value must be between 0 and 1).
	 */
	@Override
	public void setBlue(double blue) {
		if ((blue>=0)&&(blue<=1)){
			this.blue = blue;
		}
	}

	/**
	 * Sets the value of the green channel of the color.
	 * 
	 * @param red New value for the green channel of the color (value must be between 0 and 1).
	 */
	@Override
	public void setGreen(double green) {
		if ((green>=0)&&(green<=1)){
			this.green = green;
		}
	}

	/**
	 * Sets the value of the red channel of the color.
	 * 
	 * @param red New value for the red channel of the color (value must be between 0 and 1).
	 */
	@Override
	public void setRed(double red) {
		if ((red>=0)&&(red<=1)){
			this.red = red;
		}
	}
	
	/**
	 * Returns the value of the red component of a given pixel.
	 * 
	 * @param pixel
	 * @return value of the red component of a given pixel
	 */
	public static int getRedScaledFromRGB(int pixel){
		return (pixel >> 16) & 0xff;
	}
	
	/**
	 * Returns the value of the green component of a given pixel.
	 * 
	 * @param pixel
	 * @return value of the green component of a given pixel
	 */
	public static int getGreenScaledFromRGB(int pixel){
		return (pixel >> 8) & 0xff;
	}
	
	/**
	 * Returns the value of the blue component of a given pixel.
	 * 
	 * @param pixel
	 * @return value of the blue component of a given pixel
	 */
	public static int getBlueScaledFromRGB(int pixel){
		return (pixel) & 0xff;
	}
	
	/**
	 * Prints the values of each of the pixel ARGB components.
	 * 
	 * @param pixel
	 */
	public static void printPixelARGB(int pixel){
		int alpha = (pixel >> 24) & 0xff;
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;
		System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
	}
	
	/**
	 * Returns the (int) value of a pixel based sum of each ARGB component.
	 * 
	 * @param a
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	public static int makeARGB(int a, int r, int g, int b){
		return a << 24 | r << 16 | g << 8 | b;
	}
	
	/**
	 * Returns the (int) value of a pixel based sum of each RGB component.
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @return the (int) value of a pixel based sum of each RGB component.
	 */
	public static int makeRGB(int r, int g, int b){
		return r << 16 | g << 8 | b;
	}
	
	/**
	 * Clamps the values of the RGB channels (makes sure the values are between 0 and 1).
	 */
	public void clamp(){
		red = (red>0)? ((red<1)? red : 1) : 0;
		
		green = (green>0)? ((green<1)? green : 1) : 0;
		
		blue = (blue>0)? ((blue<1)? blue : 1) : 0;
	}

	/**
	 * Sets the value of the blue channel of the color.
	 * 
	 * @param blue New value for the blue channel of the color (value must be between 0 and 255).
	 */
	@Override
	public void setBlueScaled(int blue) {
		setBlue(blue/RGB_SCALE);
	}

	/**
	 * Sets the value of the green channel of the color.
	 * 
	 * @param green New value for the green channel of the color (value must be between 0 and 255).
	 */
	@Override
	public void setGreenScaled(int green) {
		setGreen(green/RGB_SCALE);
	}

	/**
	 * Sets the value of the red channel of the color.
	 * 
	 * @param red New value for the red channel of the color (value must be between 0 and 255).
	 */
	@Override
	public void setRedScaled(int red) {
		setRed(red/RGB_SCALE);
	}
	
	/**
	 * Returns a color whose values are the average of the given colors.
	 * This is used for super sampling.
	 * 
	 * @param colorMatrix The input colors whose average is to be calculated.
	 * @return a color whose values are the average of the given colors.
	 */
	public static IColor getAverageColor(IColor[][] colorMatrix){
		double red = 0;
		double green = 0;
		double blue = 0;
		double matrixSize = colorMatrix.length*colorMatrix.length;
		
		for (int i=0; i<colorMatrix.length;i++){
			for (int j=0; j<colorMatrix.length;j++){
				red += colorMatrix[i][j].getRed();
				green += colorMatrix[i][j].getGreen();
				blue += colorMatrix[i][j].getBlue();
			}
		}
		return new Color(red/matrixSize,green/matrixSize,blue/matrixSize);
	}

	/**
	 * Adds the color (values) to another color (values) and returns the result color (not changing the current color).
	 * 
	 * @param otherColor The color to add to this color.
	 * @return Result color.
	 */
	@Override
	public IColor addColor(IColor otherColor) {
		double red = this.red + otherColor.getRed();
		double green = this.green + otherColor.getGreen();
		double blue = this.blue + otherColor.getBlue();
		
		Color result = new Color(red, green, blue);
		return result;
	}

	/**
	 * Multiplies the color (values) by a constant scalar (not changing the current color).
	 * 
	 * @param c The multiplication constant.
	 * @return Result color.
	 */
	@Override
	public IColor multiplyByConstant(double c) {
		double red = this.red * c;
		double green = this.green * c;
		double blue = this.blue * c;
		
		Color result = new Color(red, green, blue);
		return result;
	}

	/**
	 * Multiplies the color (values) by another color (values) and returns the result color (not changing the current color).
	 * 
	 * @param otherColor The multiplication color.
	 * @return Result color.
	 */
	@Override
	public IColor multiplyByColor(IColor otherColor) {
		double red = this.red * otherColor.getRed();
		double green = this.green * otherColor.getGreen();
		double blue = this.blue * otherColor.getBlue();
		
		Color result = new Color(red, green, blue);
		return result;
	}
	
	/**
	 * Returns the string representation of the color.
	 * 
	 * @return The string representation of the color.
	 */
	public String toString(){
		return getRedScaled()+" "+getGreenScaled()+" "+getBlueScaled();
	}
}