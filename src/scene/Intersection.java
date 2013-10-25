package scene;

import sceneObjects.ISceneObject;
import vectors.*;

/**
 * This class represents all the information for an intersection of a ray with an object.
 *
 */
public class Intersection {

	private boolean intersects;
	private IPoint3D intersectionPoint;
	private ISceneObject intersectionObject;
	private double distance;
	private IVector3D normal;
	private IRay ray;
	
	/**
	 * Constructs an intersection object.
	 */
	public Intersection(){
		intersects = false;
		distance = Double.MAX_VALUE;
		normal = null;
		ray = null;
	}
	
	/**
	 * Returns 'true' if the ray intersected with an object in the scene, or 'false' otherwise.
	 * 
	 * @return 'true' if the ray intersected with an object in the scene, or 'false' otherwise.
	 */
	public boolean isIntersects(){
		return intersects;
	}
	
	/**
	 * Marks this intersection as a successful hit with an object in the scene.
	 * 
	 * @param hitPoint The intersection point with the object.
	 * @param distance The distance of the intersection point from the originating point.
	 * @param obj The object which intersected with the ray.
	 * @param normal The normal of the surface at the intersection point.
	 * @param ray
	 */
	public void setIntersectionHit(IPoint3D hitPoint, double distance, ISceneObject obj, IVector3D normal, IRay ray){
		intersectionPoint = hitPoint;
		intersects = true;
		intersectionObject = obj;
		this.distance = distance;
		this.normal = normal;
		this.ray = ray;
	}
	
	/**
	 * Returns the intersection point.
	 * 
	 * @return The intersection point.
	 */
	public IPoint3D getIntersectionPoint(){
		return intersectionPoint;
	}
	
	/**
	 * Returns the object which intersected with the ray.
	 * 
	 * @return The object which intersected with the ray.
	 */
	public ISceneObject getIntersectionObject(){
		return intersectionObject;
	}
	
	/**
	 * Returns the distance of the intersection point from the origination point.
	 * 
	 * @return The distance of the intersection point from the origination point.
	 */
	public double getDistance(){
		return distance;
	}
	
	/**
	 * Returns the normal of the surface at the intersection point.
	 * 
	 * @return The normal of the surface at the intersection point.
	 */
	public IVector3D getNormal(){
		return normal;
	}
	
	/**
	 * Returns the Ray for the intersection.
	 * 
	 * @return The Ray for the intersection.
	 */
	public IRay getRay(){
		return ray;
	}
}
