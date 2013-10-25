package lighting;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import application.Parser.ParseException;
import vectors.*;
import colors.IColor;

/**
 * This class represents an area light (like point light but it has a area which is not zero).
 *
 */
public class AreaLight extends AbstractLight {

	private IPoint3D p0;
	private IPoint3D p1;
	private IPoint3D p2;
	private PointLight[][] lightGrid;


	/**
	 * Returns the p0 point of the rectangle for the area light.
	 * 
	 * @return The p0 point of the rectangle for the area light.
	 */
	public IPoint3D getP0() {
		return p0;
	}

	/**
	 * Sets the p0 point for the rectangle of the area light.
	 * 
	 * @param p0
	 */
	public void setP0(IPoint3D p0) {
		this.p0 = p0;
	}

	/**
	 * Returns the p1 point of the rectangle for the area light.
	 * 
	 * @return The p1 point of the rectangle for the area light.
	 */
	public IPoint3D getP1() {
		return p1;
	}

	/**
	 * Sets the p1 point for the rectangle of the area light.
	 * 
	 * @param p1
	 */
	public void setP1(IPoint3D p1) {
		this.p1 = p1;
	}

	/**
	 * Returns the p1 point of the rectangle for the area light.
	 * 
	 * @return The p1 point of the rectangle for the area light.
	 */
	public IPoint3D getP2() {
		return p2;
	}

	/**
	 * Sets the p2 point for the rectangle of the area light.
	 * 
	 * @param p2
	 */
	public void setP2(IPoint3D p2) {
		this.p2 = p2;
	}

	/**
	 * Returns the inner lights grid (as a PointLight array).
	 * 
	 * @return The inner lights grid (as a PointLight array).
	 */
	public PointLight[][] getLightGrid() {
		return lightGrid;
	}

	/**
	 * Sets the inner lights grid.
	 * 
	 * @param lightGrid New inner lights grid.
	 */
	public void setLightGrid(PointLight[][] lightGrid) {
		this.lightGrid = lightGrid;
	}

	/**
	 * Initializes the inner lights grid to the given size.
	 * 
	 * @param n Size for the new grid light.
	 */
	public void setLightGridSize(int n) {
		this.lightGrid = new PointLight[n][n];
	}

	/**
	 * Returns the direction vector from the given hit point to the light source.
	 * 
	 * @param hitPoint
	 * @return The direction vector from the given hit point to the light source.
	 */
	@Override
	public IVector3D getDirectionFromHitPointToLight(IPoint3D hitPoint) {
		return null;
	}

	/**
	 * Returns the distance from the given hit point to the light source.
	 * 
	 * @param hitPoint
	 * @return The distance from the given hit point to the light source.
	 */
	@Override
	public double getDistanceFromHitPointToLight(IPoint3D hitPoint) {
		return 0;
	}

	/**
	 * Returns the intensity of the light at a given point (considers attenuation).
	 * 
	 * @param point
	 * @return The intensity of the light at a given point.
	 */
	@Override
	public IColor getIntensityForPoint(IPoint3D point) {
		return getIntensity();
	}
	
    /**
     * Parses the given parameters to create a light source.
     * 
     * @param name The name of the parameter.
     * @param args The values for the parameter.
     * @throws NumberFormatException
     * @throws FileNotFoundException
     */
	@Override
	public void parseParameter(String name, String[] args) throws NumberFormatException, FileNotFoundException {
		super.parseParameter(name, args);
		try{
			if (name.equals("p0")){
				setP0(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("p1")){
				setP1(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("p2")){
				setP2(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("grid-width")){
				setLightGridSize(Integer.parseInt(args[0]));
			}
		}catch (ArrayIndexOutOfBoundsException e){
			throw new NumberFormatException();
		}
	}
	
	/**
	 * Initializes the inner lights grid.
	 */
	private void initLights() {
		int n = lightGrid.length;
		double xDist = p0.getDistanceBetweenPoints(p1) / (double)n;
		double yDist = p0.getDistanceBetweenPoints(p2) / (double)n;
		IVector3D v1 = new Vector3D(p0, p1).normalize();
		IVector3D v2 = new Vector3D(p0, p2).normalize();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				PointLight pointLight = new PointLight();
				pointLight.setIntensity(this.getIntensity().multiplyByConstant(1d/Math.pow(lightGrid.length, 2d)));
				pointLight.setPosition(p0.
						movePointByVector(v1.multiplyByScalar(xDist * i)).
						movePointByVector(v2.multiplyByScalar(yDist * j)));
				
				lightGrid[i][j] = pointLight;
			}
		}
	}
	
	/**
	 * Finishes parsing the light and validates that all mandatory values were given and valid.
	 * 
	 * @throws ParseException
	 */
	@Override
	public void commit() throws ParseException {
		if ((getP0()==null)||(getP1()==null)||(getP2()==null)||(getLightGrid()==null)){
			throw new ParseException("Parameters given for Area Light are not valid or missing");
		}
		initLights();
	}
	
	/**
	 * Returns an iterator for the light source.
	 * If it is a single light source, it will return an iterator with itself inside.
	 * Otherwise it will return an iterator with multiple inner light sources. (i.e area light).
	 * 
	 * @return Iterator for all the inner light sources.
	 */
	public Iterator<ILight> getIterator(){
		ILight[] arr = new ILight[lightGrid.length*lightGrid.length];
		for (int i=0; i<lightGrid.length; i++){
			for (int j=0; j<lightGrid.length; j++){
				arr[i+j*lightGrid.length] = lightGrid[i][j];
			}
		}
		return Arrays.asList(arr).iterator();
	}
}
