package surfaces;

import java.util.Iterator;

import application.Parser.ParseException;
import lighting.ILight;
import scene.*;
import vectors.*;
import colors.*;

/**
 * This abstract class holds all the shared functionality for all surfaces.
 *
 */
public abstract class AbstractSurface implements ISurface {
	protected IColor emission;
	protected IColor ambient;
	protected IColor diffuse;
	protected IColor specular;
	protected double shininess;
	protected double reflectance;
	protected String type;
	private final static int rayTraceCounter = 4;
	
	/**
	 * Returns the emission intensity (color) of the surface.
	 * 
	 * @return The emission intensity (color) of the surface.
	 */
	public IColor getEmission() {
		return emission;
	}

	/**
	 * Sets the emission intensity (color) of the surface.
	 * 
	 * @param emission
	 */
	public void setEmission(IColor emission) {
		this.emission = emission;
	}

	/**
	 * Returns the ambient intensity (color) of the surface.
	 * 
	 * @return The ambient intensity (color) of the surface.
	 */
	public IColor getAmbient() {
		return ambient;
	}

	/**
	 * Sets the ambient intensity (color) of the surface.
	 * 
	 * @param ambient
	 */
	public void setAmbient(IColor ambient) {
		this.ambient = ambient;
	}

	/**
	 * Returns the diffuse intensity (color) of the surface.
	 * 
	 * @return The diffuse intensity (color) of the surface.
	 */
	public IColor getDiffuse() {
		return diffuse;
	}
	
	/**
	 * Sets the diffuse intensity (color) of the surface.
	 * 
	 * @param diffuse
	 */
	public void setDiffuse(IColor diffuse) {
		this.diffuse = diffuse;
	}

	/**
	 * Returns the specular intensity (color) of the surface.
	 * 
	 * @return The specular intensity (color) of the surface.
	 */
	public IColor getSpecular() {
		return specular;
	}

	/**
	 * Sets the specular intensity (color) of the surface.
	 * 
	 * @param specular
	 */
	public void setSpecular(IColor specular) {
		this.specular = specular;
	}

	/**
	 * Returns the shininess factor of the surface.
	 * 
	 * @return The shininess factor of the surface.
	 */
	public double getShininess() {
		return shininess;
	}

	/**
	 * Sets the shininess factor of the surface (default 100).
	 * 
	 * @param shininess
	 */
	public void setShininess(double shininess) {
		this.shininess = shininess;
	}

	/**
	 * Returns the reflectance coefficient of the surface.
	 * 
	 * @return The reflectance coefficient of the surface.
	 */
	public double getReflectance() {
		return reflectance;
	}

	/**
	 * Sets the reflectance coefficient of the surface.
	 * 
	 * @param reflectance
	 */
	public void setReflectance(double reflectance) {
		this.reflectance = reflectance;
	}

	/**
	 * Returns the color of the surface at a given intersection point.
	 * 
	 * @param hit The intersection information.
	 * @param scene The scene.
	 * @param iteration The iteration number (used for reflectance). Use the value 0.
	 * @return The color of the surface at a given intersection point.
	 */
	public IColor getColor(Intersection hit, Scene scene, int iteration) {
		IPoint3D hitPoint = hit.getIntersectionPoint();
		IColor hitPointDiffuse = getDiffuseForHitPoint(hit);

		// I = 0
		IColor intensity = new Color();
		//First part of the formula, before the sigma
		// I = I + Ie
		intensity = intensity.addColor(emission);
		// I = I + Ka*Ia
		intensity = intensity.addColor(ambient.multiplyByColor(scene.getAmbientLightIntensity()));

		IVector3D normal = hit.getNormal();
		IVector3D viewer = hit.getRay().getV().multiplyByScalar(-1);
		IColor lightSum = new Color(0, 0, 0);
		Iterator<ILight> lightIter = scene.getLightsList().iterator();
		double viewerDotR = 0;
		
		//Sum for all lights
		while (lightIter.hasNext()) { 
			ILight light = lightIter.next();
			if (light.getIterator()!=null){
				Iterator<ILight> iter = light.getIterator();
				while (iter.hasNext()){
					ILight innerLight = iter.next();
					IColor temp = new Color(0, 0, 0);
					IVector3D l = innerLight.getDirectionFromHitPointToLight(hitPoint);
					IVector3D r = l.mirror(normal);
					double shadow = scene.getSL(new Ray(hit.getIntersectionPoint(), l), innerLight, hit);
					//The sum of what's inside the sigma
					//(Kd (N * L)...
					temp = hitPointDiffuse.multiplyByConstant(normal.dotProduct(l));
					//...+Ks (V * R)^n)...
					viewerDotR = viewer.dotProduct(r);
					viewerDotR = (viewerDotR<0)? 0 : viewerDotR;
					if (innerLight.getDistanceFromHitPointToLight(hit.getIntersectionPoint())>0){
						temp = temp.addColor(specular.multiplyByConstant(
						Math.pow(viewerDotR, shininess)));
						temp.clamp();
					}
					//...* (Il * Sl)
					temp = temp.multiplyByColor(innerLight.getIntensityForPoint(hit.getIntersectionPoint(),normal)
							.multiplyByConstant(shadow));
					lightSum = lightSum.addColor(temp);
				}
			}
		}
		IColor reflectanceIntensity = new Color(0, 0, 0);
		if (reflectance != 0 || iteration<rayTraceCounter) {
			reflectanceIntensity = RayTrace(hit, scene, iteration);
		}

		intensity = intensity.addColor(lightSum);
		//+Kt * Ir
		intensity = intensity.addColor(reflectanceIntensity.multiplyByConstant(reflectance));

		intensity.clamp();
		return intensity;
	}
	
	/**
	 * Returns the reflectance intensity for the given hit point and iteration.
	 * 
	 * @param hit
	 * @param scene
	 * @param iteration
	 * @return The reflectance intensity for the given hit point and iteration.
	 */
	private IColor RayTrace(Intersection hit, Scene scene, int iteration) {
		IVector3D mirrorVector = hit.getRay().getV().multiplyByScalar(-1).mirror(hit.getNormal());
		IRay mirrorRay = new Ray(hit.getIntersectionPoint(), mirrorVector);
		Intersection mirrorIntersection = scene.findIntersection(mirrorRay,hit.getIntersectionObject());
		if (!mirrorIntersection.isIntersects()){
			return scene.getBackgroundColor();
		}
		return mirrorIntersection.getIntersectionObject().getSurface().getColor(mirrorIntersection, scene, iteration+1);
	}
	
	/**
	 * Returns the diffuse intensity for a given hit point.
	 * 
	 * @param hit
	 * @return The diffuse intensity for a given hit point.
	 */
	protected abstract IColor getDiffuseForHitPoint(Intersection hit);
	
	/**
	 * Finishes parsing the surface and validates that all mandatory values were given and valid.
	 * 
	 * @throws ParseException
	 */
	public void commit() throws ParseException {
	}
}
