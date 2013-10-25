package surfaces;

import java.io.FileNotFoundException;
import org.eclipse.swt.graphics.ImageData;
import scene.Intersection;
import scene.Scene;
import vectors.*;
import colors.*;

/**
 * This class represents the texture surface.
 *
 */
public class TextureSurface extends AbstractSurface {

	private String texture;
	private ImageData textureImage;

	/**
	 * Constructs a texture surface.
	 */
	public TextureSurface() {
		this.type = "texture";
		this.emission = new Color(0, 0, 0);
		this.ambient = new Color(0.1, 0.1, 0.1);
		this.diffuse = new Color(0.7, 0.7, 0.7);
		this.specular = new Color(1, 1, 1);
		this.shininess = 100;
		this.reflectance = 0;
		this.texture = null;
		this.textureImage = null;
	}

	/**
	 * Returns the file name of the texture file.
	 * 
	 * @return The file name of the texture file.
	 */
	public String getTexture() {
		return texture;
	}

	/**
	 * Returns the texture as ImageData.
	 * 
	 * @return The texture as ImageData.
	 */
	public ImageData getTextureImage() {
		return textureImage;
	}

	/**
	 * Opens and Sets the texture for the surface.
	 * 
	 * @param texture
	 * @throws FileNotFoundException
	 */
	public void setTexture(String texture) throws FileNotFoundException {
		if (texture == null){
			throw new FileNotFoundException("No texture was specified.");
		}
		if (!texture.toLowerCase().endsWith(".png")) {
			throw new FileNotFoundException("The supported file format is png.");
		}
		this.texture = texture;
		try {
			textureImage = new ImageData(Scene.getPathToTextureFile() + texture);
		}
		catch (Exception e) {
			throw new FileNotFoundException();
		}
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
	@Override
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
			if (name.equals("texture")){
				setTexture(args[0]);
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
		IPoint3D hitPoint = hit.getIntersectionPoint();
		Point2D hitParam = hit.getIntersectionObject().parametrize(hitPoint);
		if (hitParam != null && textureImage != null) {
			int xPixel = Math.max(Math.min((int)(hitParam.getX()*textureImage.width-1), textureImage.width), 0);
			int yPixel = Math.max(Math.min((int)(hitParam.getY()*textureImage.height-1), textureImage.height), 0);
			int pixel = textureImage.getPixel(xPixel, yPixel);
			double red = Color.getRedScaledFromRGB(pixel) / 255d;
			double green = Color.getGreenScaledFromRGB(pixel) / 255d;
			double blue = Color.getBlueScaledFromRGB(pixel) / 255d;
			return new Color(red, green, blue);
		}
		return diffuse;
	}
}
