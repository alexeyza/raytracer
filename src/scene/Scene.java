package scene;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.graphics.ImageData;
import colors.*;
import application.Parser.ParseException;
import sceneObjects.*;
import vectors.*;
import lighting.*;

/**
 * This class represents a scene and holds all the objects.
 *
 */
public class Scene extends SceneObject {
	private double height;
	private double width;
	private Camera cam;
	private List<ISceneObject> objects;
	private List<ILight> lights;
	private IColor backgroundColor;
	private String backgroundTextureFilename;
	private ImageData backgroundTexture;
	private IPoint3D viewPlaneP1;  //this is the left upper point of the view plane
	private int superSampleWidth;
	private IColor AmbientLightIntensity;
	private static String pathToTextureFile = "";
	
	/**
	 * Constructs a scene.
	 */
	public Scene(){
		objects = new ArrayList<ISceneObject>();
		lights = new ArrayList<ILight>();
		backgroundColor = new Color(0,0,0);
		cam = null;
		width = 0;
		height = 0;
		viewPlaneP1 = null;
		backgroundTextureFilename = null;
		backgroundTexture = null;
		superSampleWidth = 1;
		AmbientLightIntensity = new Color(0,0,0);
	}

	/**
	 * Sets the canvas size which is used to display the scene.
	 * 
	 * @param height
	 * @param width
	 */
	public void setCanvasSize(double height, double width) {
		if (height>=0){
			this.height = height;
		}
		if (width>=0){
			this.width = width;
		}
	}

	/**
	 * Returns the height of the canvas for the scene.
	 * 
	 * @return The height of the canvas for the scene.
	 */
	public double getHeight(){
		return height;
	}

	/**
	 * Returns the width of the canvas for the scene.
	 * 
	 * @return The width of the canvas for the scene.
	 */
	public double getWidth(){
		return width;
	}
	
	/**
	 * Returns the name for the texture file for the background of the scene.
	 * 
	 * @return The name for the texture file for the background of the scene.
	 */
	public String getBackgroundTextureFilename(){
		return backgroundTextureFilename;
	}
	
	/**
	 * Sets the name for the texture file for the background of the scene.
	 * 
	 * @param name New file name
	 * @throws FileNotFoundException
	 */
	public void setBackgroundTextureFilename(String name) throws FileNotFoundException{
		if (name==null){
			return;
		}
		backgroundTextureFilename = name;
		try {
			backgroundTexture = new ImageData(getPathToTextureFile()+name);
		}catch (Exception e){
			throw new FileNotFoundException();
		}
		backgroundTexture = backgroundTexture.scaledTo((int)getWidth(), (int)getHeight());
	}
	
	/**
	 * Returns the background color (not considering the background texture).
	 * 
	 * @return The background color.
	 */
	public IColor getBackgroundColor(){
		return backgroundColor;
	}

	/**
	 * Returns the background color (considering the background texture).
	 * 
	 * @param x X-coordinate for the background.
	 * @param y Y-coordinate for the background.
	 * @return The background color.
	 */
	public IColor getBackgroundColor(double x , double y){
		if (getBackgroundTextureFilename()==null){
			return getBackgroundColor();
		}
		int rgb = backgroundTexture.getPixel((int)x, (int)y);
		double red = Color.getRedScaledFromRGB(rgb)/255d;
		double green = Color.getGreenScaledFromRGB(rgb)/255d;
		double blue = Color.getBlueScaledFromRGB(rgb)/255d;
		return new Color(red,green,blue);
	}

	/**
	 * Returns the camera object of the scene.
	 * 
	 * @return The camera object of the scene.
	 */
	public Camera getCamera(){
		return cam;
	}

	/**
	 * Sets the camera object of the scene.
	 * 
	 * @param cam The new camera for the scene.
	 */
	public void setCamera(Camera cam){
		this.cam = cam;
	}

	/**
	 * Sets a path to a texture file.
	 * 
	 * @param path
	 */
	public static void setPathToTextureFile(String path){
		pathToTextureFile = path;
	}
	
	/**
	 * Returns the path to a texture path.
	 * 
	 * @return The path to a texture path.
	 */
	public static String getPathToTextureFile(){
		return pathToTextureFile;
	}
	
	/**
	 * Sets the background color for the scene.
	 * 
	 * @param color
	 */
	public void setBackgroundColor(IColor color){
		this.backgroundColor = color;
	}
	
	/**
	 * Sets the super sample width.
	 * 
	 * @param width
	 */
	public void setSuperSampleWidth(int width){
		if (superSampleWidth>0){
			superSampleWidth = width;
		}
	}
	
	/**
	 * Returns the super sample width.
	 * 
	 * @return The super sample width.
	 */
	public int getSuperSampleWidth(){
		return superSampleWidth;
	}
	
	/**
	 * Sets the ambient light intensity of the scene.
	 * 
	 * @param ambientLightIntensity
	 */
	public void setAmbientLightIntensity(IColor ambientLightIntensity) {
		this.AmbientLightIntensity = ambientLightIntensity;
	}

	/**
	 * Returns the ambient light intensity of the scene.
	 * 
	 * @return
	 */
	public IColor getAmbientLightIntensity() {
		return AmbientLightIntensity;
	}
	
	/**
	 * Returns the object list in the scene.
	 * 
	 * @return
	 */
	public List<ISceneObject> getObjectList(){
		return objects;
	}
	
	/**
	 * Returns the lights list in the scene.
	 * 
	 * @return
	 */
	public List<ILight> getLightsList(){
		return lights;
	}
	
	/**
	 * Initializes the scene view plane before doing the ray tracing.
	 */
	public void initSceneViewPlane(){
		if (cam!=null){
			viewPlaneP1 = findP1(getCamera().getEyePosition());
		}
	}

	/**
	 * Returns the color for pixel at coordinates (x,y) with super sampling (after doing the the ray tracing).
	 * 
	 * @param x
	 * @param y
	 * @return The color for pixel at coordinates (x,y) (after doing the the ray tracing).
	 */
	public IColor getColorWithSuperSample(double x, double y) {
		// creating an array of colors for super sampling
		IColor[][] superSampleColors = new Color[getSuperSampleWidth()][getSuperSampleWidth()];
		double divider = 1d / (double)getSuperSampleWidth();
		double ix,iy;
		for (int i=0; i<getSuperSampleWidth(); i++){
			for (int j=0; j<getSuperSampleWidth();j++){
				ix = (double)x + ((double)j)*divider;
				iy = (double)y + ((double)i)*divider;
				
				IRay ray = constructRayThroughPixel(ix,iy);
				
				Intersection hit = findIntersection(ray,null);

				if (hit.isIntersects()){
					superSampleColors[i][j] = hit.getIntersectionObject().getSurface().getColor(hit,this,0);
				}else{
					superSampleColors[i][j] =  getBackgroundColor(x,y);
				}
			}
		}
		return Color.getAverageColor(superSampleColors);
	}
	
	/**
	 * Returns the color for pixel at coordinates (x,y) (after doing the the ray tracing).
	 * 
	 * @param x
	 * @param y
	 * @return The color for pixel at coordinates (x,y) (after doing the the ray tracing).
	 */
	public IColor getColor(double x,double y){

		IRay ray = constructRayThroughPixel(x,y);
		
		Intersection hit = findIntersection(ray,null);

		if (hit.isIntersects()){
			return new Color(0,0,0);
		}else{
			return getBackgroundColor(x,y);
		}
	}

	/**
	 * Returns a ray which originates at the camera position and goes through pixel (x,y) at the view plane.
	 * 
	 * @param x
	 * @param y
	 * @return A ray which originates at the camera position and goes through pixel (x,y) at the view plane.
	 */
	private IRay constructRayThroughPixel(double x, double y) {
		IVector3D up = getCamera().getUpDirection();
		IVector3D right = getCamera().getRightDirection();
		
		double halfWidth = getCamera().getScreenWidth()/2;
		double halfHeight = getCamera().getScreenHeight()/2;
		double normalizedX = (2*x+1)/getWidth() * halfWidth;
		double normalizedY = (2*y+1)/getHeight() * halfHeight;
		
		IPoint3D p0 = getCamera().getEyePosition();
		IPoint3D p = viewPlaneP1.
				movePointByVector(right.multiplyByScalar(normalizedX)).
				movePointByVector(up.multiplyByScalar(-1*normalizedY));
		return new Ray(p0, new Vector3D(p0, p).normalize());
	}
	
	/**
	 * Finds the object which intersects with the ray and is closest to the camera.
	 * 
	 * @param ray
	 * @param originatingObject
	 * @return The intersection information of the closest object which intersects with the ray.
	 */
	public Intersection findIntersection(IRay ray, ISceneObject originatingObject) {
		Intersection hit = null;
		double minDistance = Double.MAX_VALUE;
		Intersection bestHit = null;
		for (ISceneObject obj:objects){
			if (obj.equals(originatingObject)){
				continue;
			}
			hit = obj.isIntersects(ray);
			if (hit!=null){
				if (hit.getDistance()<minDistance){
					minDistance = hit.getDistance();
					bestHit = hit;
				}
			}
		}
		if (bestHit==null){
			bestHit = new Intersection();
		}
		return bestHit;
	}

	/**
	 * Sets the parameters for the scene.
	 * 
	 * @param name The name of the parameter
	 * @param args The values for the parameter (given as String[])
	 * @return 'true' if the parameter is recognized by the scene
	 * @throw ParseException
	 */
	@Override
	public void parseParameter(String name, String[] args) throws NumberFormatException, FileNotFoundException {
		try{
			if (name.equals("background-col")){
				setBackgroundColor(new Color(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("ambient-light")){
				setAmbientLightIntensity(new Color(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("background-tex")){
				setBackgroundTextureFilename(args[0]);
			}
			if (name.equals("super-samp-width")){
				setSuperSampleWidth(Integer.parseInt(args[0]));
			}
		}catch (ArrayIndexOutOfBoundsException e){
			throw new NumberFormatException();
		}
	}

	/**
	 * Return the intersection information for a given ray.
	 * Use the returned object to check if there was a 'hit' or 'miss'.
	 * 
	 * @param ray
	 * @return The intersection information for a given ray.
	 */
	@Override
	public Intersection isIntersects(IRay ray) {
		return null;
	}
	
	/**
	 * Returns the point in 3D which represents the (0,0) point on the view plane.
	 * 
	 * @param p0
	 * @return The point in 3D which represents the (0,0) point on the view plane.
	 */
	private IPoint3D findP1(IPoint3D p0) {
		IVector3D towards = getCamera().getViewDirection();
		IVector3D up = getCamera().getUpDirection();
		if ((towards==null)||(up==null)){
			return null;
		}
		IVector3D right = getCamera().getRightDirection();
		double distance = getCamera().getScreenDistance();

		double screenWidth = getCamera().getScreenWidth();
		double screenRatio = getHeight()/getWidth();
		double screenHeight = screenRatio*screenWidth;
		getCamera().setScreenHeight(screenHeight);
		double halfWidth = screenWidth/2d;
		double halfHeight = screenHeight/2d;
				
		IPoint3D p1 = (p0.movePointByVector(towards.multiplyByScalar(distance)
				.subtractVector(right.multiplyByScalar(halfWidth))))
				.movePointByVector(up.multiplyByScalar(halfHeight));
		
		return p1;
	}
	
	/**
	 * Returns 0 if the given ray from a hit point to a light source intersects with other objects (blocked), or 1 otherwise (not blocked).
	 * 
	 * @param ray A ray from the hitPoint to a light source.
	 * @param light The light source.
	 * @param hitPoint The hit point which originates the ray.
	 * @return 0 if the given ray from a hit point to a light source intersects with other objects (blocked), or 1 otherwise (not blocked).
	 */
	public double getSL(IRay ray, ILight light, Intersection hitPoint){
		double distanceFromLight = light.getDistanceFromHitPointToLight(ray.getP0());
		
		for (ISceneObject obj:getObjectList()){
			if (!obj.equals(hitPoint.getIntersectionObject())){
				Intersection hit = obj.isIntersects(ray);
				if (hit!=null){
					if (hit.isIntersects()&&(hit.getDistance()<distanceFromLight)){
							return 0;
					}
				}
			}
		}
		return 1;
	}

	/**
	 * Finishes the parsing of the current object (may perform validations and initializations).
	 * 
	 * @throws ParseException
	 */
	@Override
	public void commit() throws ParseException {
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