package surfaces;

import java.io.FileNotFoundException;
import scene.Intersection;
import colors.Color;
import colors.IColor;

/**
 * This class represents the flat surface.
 *
 */
public class FlatSurface extends AbstractSurface {
	
	/**
	 * Constructs a flat surface.
	 */
	public FlatSurface() {
		this.type = "flat";
		this.emission = new Color(0, 0, 0);
		this.ambient = new Color(0.1, 0.1, 0.1);
		this.diffuse = new Color(0.7, 0.7, 0.7);
		this.specular = new Color(1, 1, 1);
		this.shininess = 100;
		this.reflectance = 0;
	}
	
	/**
	 * Returns the type of the surface.
	 * 
	 * @return The type of the surface.
	 */
	@Override
	public String getType() {
		return type;
	}

    /**
     * Parses the given parameters to create a surface for a scene object.
     * 
     * @param name The name of the parameter.
     * @param args The values for the parameter.
     * @throws NumberFormatException
     * @throws FileNotFoundException
     */
	public void parseParameter(String name, String[] args)
	throws NumberFormatException, FileNotFoundException {
		try{
			if (name.equals("mtl-diffuse")){
				setDiffuse(new Color(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
			}
			if (name.equals("mtl-specular")){
				setSpecular(new Color(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
			}
			if (name.equals("mtl-ambient")){
				setAmbient(new Color(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
			}
			if (name.equals("mtl-emission")){
				setEmission(new Color(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
			}
			if (name.equals("mtl-shininess")){
				setShininess(Double.parseDouble(args[0]));
			}
			if (name.equals("reflectance")){
				setReflectance(Double.parseDouble(args[0]));
			}
		}catch (ArrayIndexOutOfBoundsException e){
			throw new NumberFormatException();
		}

	}
	
	/**
	 * Returns the diffuse intensity for a given hit point.
	 * 
	 * @param hit
	 * @return The diffuse intensity for a given hit point.
	 */
	protected IColor getDiffuseForHitPoint(Intersection hit) {
		return diffuse;
	}

}
