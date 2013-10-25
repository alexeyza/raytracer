package sceneObjects;

import java.io.FileNotFoundException;

import application.Parser.ParseException;
import scene.Intersection;
import vectors.*;

/**
 * This class represents the Camera of the scene.
 *
 */
public class Camera extends SceneObject{
	
	private IPoint3D eyePosition;
	private IVector3D viewDirection;	// also called Towards in slides
	private IVector3D upDirection;
	private IVector3D rightDirection;
	private double screenDistance;
	private double screenWidth;
	private double screenHeight;
	private static final double MIN_SCREEN_DIST = 0.003d;
	
	/**
	 * Constructs a camera for the scene.
	 * 
	 * @param eyePos
	 * @param viewDir
	 * @param screenDist
	 * @param upDir
	 */
	public Camera (IPoint3D eyePos, IVector3D viewDir,double screenDist, IVector3D upDir){
		eyePosition = eyePos;
		viewDirection = viewDir;
		screenDistance = screenDist;
		upDirection = upDir;
		screenWidth = 2.0d;
		if ((viewDirection!=null)&&(upDirection!=null)){
			rightDirection = viewDir.crossProduct(upDir);
		}else{
			rightDirection = null;
		}
	}
	
	/**
	 * Constructs a camera for the scene.
	 */
	public Camera (){
		this(null,null,MIN_SCREEN_DIST,null);
	}
	
	/**
	 * Returns the point which represents the eye position of the camera.
	 * 
	 * @return The eye position of the camera.
	 */
	public IPoint3D getEyePosition(){
		return eyePosition;
	}
	
	/**
	 * Returns the vector which represents the view direction of the camera.
	 * 
	 * @return The view direction vector of the camera.
	 */
	public IVector3D getViewDirection(){
		return viewDirection;
	}
	
	/**
	 * Returns the distance between the camera and the view plane.
	 * 
	 * @return The distance between the camera and the view plane.
	 */
	public double getScreenDistance(){
		return screenDistance;
	}
	
	/**
	 * Returns the vector which represents the up direction of the camera.
	 * 
	 * @return The up direction vector of the camera.
	 */
	public IVector3D getUpDirection(){
		return upDirection;
	}
	
	/**
	 * Returns the vector which represents the right direction of the camera.
	 * 
	 * @return The right direction vector of the camera.
	 */
	public IVector3D getRightDirection(){
		if (rightDirection!=null){
			return rightDirection;
		}
		if ((getViewDirection()!=null)&&(getUpDirection()!=null)){
			rightDirection = getViewDirection().crossProduct(getUpDirection()).normalize();
			return rightDirection;
		}
		return null;
	}
	
	/**
	 * Returns the width of the view plane.
	 * 
	 * @return The width of the view plane.
	 */
	public double getScreenWidth(){
		return screenWidth;
	}
	
	/**
	 * Returns the height of the view plane.
	 * 
	 * @return The height of the view plane.
	 */
	public double getScreenHeight(){
		return screenHeight;
	}
	
	/**
	 * Sets the point which represents the eye position of the camera.
	 * 
	 * @param eyePos The point which represents the eye position of the camera.
	 */
	public void setEyePosition(IPoint3D eyePos){
		eyePosition = eyePos;
	}
	
	/**
	 * Sets the vector which represents the view direction of the camera.
	 * 
	 * @param viewDir The vector which represents the view direction of the camera.
	 */
	public void setViewDirection(IVector3D viewDir){
		viewDirection = viewDir;
	}
	
	/**
	 * Sets the distance between the camera and the view plane.
	 * 
	 * @param screenDist The distance between the camera and the view plane.
	 */
	public void setScreenDistance(double screenDist){
		if (screenDist>MIN_SCREEN_DIST){
			screenDistance = screenDist;
		}
	}
	
	/**
	 * Sets the width of the view plane.
	 * 
	 * @param width The width of the view plane.
	 */
	public void setScreenWidth(double width){
		if (width>0){
			screenWidth = width;
		}
	}
	
	/**
	 * Sets the height of the view plane.
	 * 
	 * @param height The height of the view plane.
	 */
	public void setScreenHeight(double height){
		if (height>0){
			screenHeight = height;
		}
	}
	
	/**
	 * Sets the vector which represents the up direction of the camera.
	 * 
	 * @param upDir The vector which represents the up direction of the camera.
	 */
	public void setUpDirection(IVector3D upDir){
		upDirection = upDir;
	}
	
	/**
	 * Sets the point at which the camera looks at. This will set the view direction vector.
	 * 
	 * @param point The point which the camera looks at.
	 */
	public void setLookAtPoint(IPoint3D point){
		if (viewDirection==null){
			viewDirection = (new Vector3D(eyePosition,point)).normalize();
		}
	}

    /**
     * Parses the given parameters to create a scene object.
     * 
     * @param name The name of the parameter.
     * @param args The values for the parameter.
     * @throws NumberFormatException
     * @throws FileNotFoundException
     */
	@Override
	public void parseParameter(String name, String[] args) throws NumberFormatException {
		try{
			if (name.equals("eye")){
				setEyePosition(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("direction")){
				setViewDirection(new Vector3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("screen-dist")){
				setScreenDistance(Double.parseDouble(args[0]));
			}
			if (name.equals("up-direction")){
				setUpDirection(new Vector3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("look-at")){
				setLookAtPoint(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("screen-width")){
				setScreenWidth(Double.parseDouble(args[0]));
			}
		}catch (ArrayIndexOutOfBoundsException e){
			throw new NumberFormatException();
		}
	}

	/**
	 * Return the intersection information for a given ray.
	 * The camera will return intersection with 'miss' information.
	 * 
	 * @param ray
	 * @return The intersection information for a given ray.
	 */
	@Override
	public Intersection isIntersects(IRay ray) {
		return null;
	}

	/**
	 * Finishes parsing the camera and validates that all mandatory values were given and valid.
	 * 
	 * @throws ParseException
	 */
	@Override
	public void commit() throws ParseException {
		if ((getEyePosition()==null)||(getUpDirection()==null)||(getViewDirection()==null)
				||(getScreenDistance()<=MIN_SCREEN_DIST)||(getScreenWidth()<=0)){
			throw new ParseException("Parameters given for Camera are not valid or missing");
		}
		//rightDirection = getViewDirection().crossProduct(getUpDirection());
		if (getViewDirection().isCoLinear(getUpDirection())){
			throw new ParseException("The direction and the up vector of the camera are co-linear (they need to be orthogonal)");
		}
		orthogonalizeViewDirectionUp();
	}
	
	/**
	 * Orthogonalizes the up direction vector.
	 */
	private void orthogonalizeViewDirectionUp(){
		setUpDirection(getRightDirection().crossProduct(getViewDirection()));
	}

	/**
	 * Returns the 2D point which maps the given 3D point to a texture (or checkers pattern).
	 * 
	 * @param point3d
	 * @return The 2D point which maps the given 3D point to a texture (or checkers pattern).
	 */
	@Override
	public Point2D parametrize(IPoint3D point3d) {
		return null;
	}
}
