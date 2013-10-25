package scene;

import java.io.FileNotFoundException;
import lighting.*;
import sceneObjects.*;
import surfaces.*;
import application.Parser;


/**
 * This class represents the actual scene configuration file parser.
 *
 */
public class SceneParser extends Parser{

	private Scene mScene;
	private ISceneObject curObj;
	private ILight curLight;
	
	/**
	 * Constructs a scene configuration file parser.
	 * 
	 * @param m_scene The scene to be built from the configuration file
	 */
	public SceneParser(Scene m_scene) {
		mScene = m_scene;
	}
	
	/**
	 * Returns the scene currently parsed
	 * 
	 * @return The scene currently parsed
	 */
	public Scene getScene(){
		return mScene;
	}
	
	/**
	 * Method to be called before starting the parsing process.
	 */
	public void startFile()
	{
		System.out.println("----------------");
	}

	/**
	 * Method to be called after finishing the parsing process.
	 * 
	 * @throws ParseException
	 */
	public void endFile() throws ParseException
	{
		System.out.println("================");
	}

	/**
	 * Adds a new object to the scene.
	 * 
	 * @param name The name of the new object
	 * @return 'true' if the object is recognized by the scene
	 * @throws ParseException
	 */
	public boolean addObject(String name) throws ParseException
	{
		System.out.println("OBJECT: " + name);
		if (name.equals("camera")){
			Camera cam = new Camera();
			mScene.setCamera(cam);
			curObj = cam;
			return true;
		}
		ISceneObject obj = null;
		AbstractLight light = null;
		if (name.equals("scene")){
			curObj = mScene;
			return true;
		}
		if (name.equals("rectangle")){
			obj = new Rectangle();
		}
		if (name.equals("sphere")){
			obj = new Sphere();
		}
		if (name.equals("cylinder")){
			obj = new Cylinder();
		}
		if (name.equals("disc")){
			obj = new Disc();
		}
		if (name.equals("box")){
			obj = new Box();
		}
		if (name.equals("triangle")){
			obj = new Triangle();
		}
		if (name.equals("mesh")){
			obj = new Mesh();
		}
		if (name.equals("light-directed")){
			light = new DirectionalLight();
		}
		if (name.equals("light-point")){
			light = new PointLight();
		}
		if (name.equals("light-area")){
			light = new AreaLight();
		}
		if (name.equals("light-hemisphere")){
			light = new HemisphericalLight();
		}
		if (light!=null){
			mScene.getLightsList().add(light);
			curLight = light;
			curObj = null;
			return true;
		}
		if (obj!=null){
			mScene.getObjectList().add(obj);
			curObj = obj;
			curLight = null;
			return true;
		}
		curObj = null;
		curLight = null;
		return false;
	}
	
	/**
	 * Sets the parameters for the current object.
	 * 
	 * @param name The name of the parameter
	 * @param args The values for the parameter (given as String[])
	 * @return 'true' if the parameter is recognized by the scene
	 * @throw ParseException
	 */
	public boolean setParameter(String name, String[] args) throws ParseException
	{
		try{
			if (curObj!=null){
				curObj.parseParameter(name, args);
				if (name.equals("mtl-type")){
					if (args[0].equals("flat")){
						curObj.setSurface(new FlatSurface());
					}
					if (args[0].equals("checkers")){
						curObj.setSurface(new CheckersSurface());
					}
					if (args[0].equals("texture")){
						curObj.setSurface(new TextureSurface());
					}
				}
				curObj.getSurface().parseParameter(name, args);
			}
			else if (curLight!=null){
				curLight.parseParameter(name, args);
			}
		}catch (NumberFormatException e){
			throw new ParseException("parameter missing or number format is wrong for parameter '"+name+"'");
		}catch (FileNotFoundException e2){
			String message = (e2.getMessage()==null) ? "" : e2.getMessage();
			throw new ParseException("input file "+args[0]+" could not be opened\n"+message);
		}
		System.out.print("PARAM: " + name);
	    for (String s : args) 
	        System.out.print(", " + s);
	    System.out.println();
	    return true;
	}
	
	/**
	 * Finishes the parsing of the current object (may perform validations and initializations).
	 * 
	 * @throws ParseException
	 */
	public void commit() throws ParseException
	{
		if (curObj!=null){
			curObj.commit();
		}
		else if (curLight!=null){
			curLight.commit();
		}
	}
	
	/**
	 * Reports an error to the screen.
	 * 
	 * @param err
	 */
	public void reportError(String err)
	{
		System.out.println("ERROR: " + err);
	}
}
