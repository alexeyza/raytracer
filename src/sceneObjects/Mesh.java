package sceneObjects;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import scene.Intersection;
import scene.Scene;
import vectors.*;
import application.Parser.ParseException;

/**
 * This mini-class represents a triangle, which also knows the indexes (at the vertex array) of his points. 
 * It is used so that we can compute all the normals of the vertices only once and save them to an array.
 *
 */
class AnotatedTriangle{
	public Triangle triangle;
	public int iPoint1;
	public int iPoint2;
	public int iPoint3;
}

/**
 * 
 * This class represents a mesh object which consists of triangles (supports 'off' and 'ply2' formats).
 *
 */
public class Mesh extends SceneObject{
	private AnotatedTriangle[] triangles;
	private IPoint3D[] vertices;
	private IVector3D[] verticesNormals;
	private String filename;
	private IPoint3D position;
	private double scale;
	private ISceneObject boundingBox;
	private String shaderType;
	
	/**
	 * Constructs a mesh object.
	 */
	public Mesh(){
		filename = null;
		triangles = new AnotatedTriangle[0];
		position = null;
		scale = 1;
		boundingBox = null;
		vertices = null;
		shaderType = "flat";
	}
	
	/**
	 * Returns the filename of the mesh configuration file.
	 * 
	 * @return The filename of the mesh configuration file.
	 */
	public String getFilename(){
		return filename;
	}
	
	/**
	 * Sets the filename of the mesh configuration file.
	 * 
	 * @param filename
	 */
	public void setFilename(String filename){
		this.filename = filename;
	}
	
	/**
	 * Returns the starting position of the mesh in the scene.
	 * 
	 * @return The starting position of the mesh in the scene.
	 */
	public IPoint3D getPosition(){
		return position;
	}
	
	/**
	 * Sets the starting position of the mesh in the scene.
	 * 
	 * @param position
	 */
	public void setPosition(IPoint3D position){
		this.position = position;
	}

	/**
	 * Returns the scale of the mesh.
	 * 
	 * @return The scale of the mesh.
	 */
	public double getScale(){
		return scale;
	}
	
	/**
	 * Sets the scale of the mesh (i.e 0.5 for half size).
	 * 
	 * @param scale
	 */
	public void setScale(double scale){
		if (scale>0){
			this.scale = scale;
		}
	}
	
	/**
	 * Returns the shader type ('flat' or 'phong').
	 * 
	 * @return The shader type ('flat' or 'phong').
	 */
	public String getShaderType(){
		return shaderType;
	}
	
	/**
	 * Sets the shader type ('flat' or 'phong').
	 * 
	 * @param type The shader type ('flat' or 'phong').
	 */
	public void setShaderType(String type){
		if (type.equals("flat")||type.equals("phong")){
			shaderType = type;
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
		// This needs serious optimizations for speed
		Intersection hit;
		Intersection bestHit = null;
		double minDist = Double.MAX_VALUE;
		if (boundingBox!=null){
			hit = boundingBox.isIntersects(ray);
			if (!hit.isIntersects()){
				return hit;
			}
		}
		for (AnotatedTriangle t:triangles){
			hit = t.triangle.isIntersects(ray);
			if (hit.isIntersects()){
				if (hit.getDistance()<minDist){
					if (getShaderType().equals("flat")){
						hit.setIntersectionHit(hit.getIntersectionPoint(), hit.getDistance(), this,hit.getNormal(),ray);
					}
					if (getShaderType().equals("phong")){
						hit.setIntersectionHit(hit.getIntersectionPoint(), hit.getDistance(), this,getNormal(hit.getIntersectionPoint(), t),ray);
					}
					bestHit = hit;
					minDist = hit.getDistance();
				}
			}
		}
		if (bestHit==null){
			bestHit = new Intersection();
		}
		return bestHit;
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
	public void parseParameter(String name, String[] args) throws NumberFormatException, FileNotFoundException {
		try {
			if (name.equals("filename")){
				setFilename(args[0]);
				if (getFilename()!=null){
					parseFile(getFilename());
				}
			}
			if (name.equals("pos")){
				setPosition(new Point3D(Double.parseDouble(args[0]),Double.parseDouble(args[1]),Double.parseDouble(args[2])));
			}
			if (name.equals("scale")){
				setScale(Double.parseDouble(args[0]));
			}
			if (name.equals("shader")){
				setShaderType(args[0]);
			}
		}catch (ArrayIndexOutOfBoundsException e){
			throw new NumberFormatException();
		}
	}
	
	/**
	 * Finishes parsing the scene object and validates that all mandatory values were given and valid.
	 * 
	 * @throws ParseException
	 */
	@Override
	public void commit() throws ParseException {
		if ((getPosition()==null)||(getFilename()==null)){
			throw new ParseException("Parameters given for Mesh are not valid or missing");
		}
		calculateVerticesNormals();
	}
	
	/**
	 * Parses the mesh configuration file (of type 'off' or 'ply2').
	 * 
	 * @param filename
	 * @throws NumberFormatException
	 */
	@SuppressWarnings("deprecation")
	private void parseFile(String filename) throws NumberFormatException{
		File file = new File(Scene.getPathToTextureFile()+filename);
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    DataInputStream dis = null;
	    
	    try {
	    	fis = new FileInputStream(file);
	    	bis = new BufferedInputStream(fis);
	    	dis = new DataInputStream(bis);
	    	String line;
	    	int numOfVertices = 0;
	    	int numOfTriangles = 0;
	    	if (filename.toLowerCase().endsWith(".off")){
	    		// check for file type
	    		line = dis.readLine();
	    		if (!line.toLowerCase().equals("off")){
	    			return;
	    		}
	    		// get the number of vertices and number of faces from file
	    		line = dis.readLine();
	    		numOfVertices = Integer.parseInt(line.split(" ")[0]);
	    		numOfTriangles = Integer.parseInt(line.split(" ")[1]);
	    	}else if (filename.toLowerCase().endsWith(".ply2")){
		    		// get the number of vertices and number of faces from file
		    		line = dis.readLine();
		    		numOfVertices = Integer.parseInt(line);
		    		line = dis.readLine();
		    		numOfTriangles = Integer.parseInt(line);
	    		}else{
	    			return;
	    		}
	    	// create arrays to hold data
	    	vertices = new Point3D[numOfVertices];
	    	triangles = new AnotatedTriangle[numOfTriangles];
	    	// read the vertices
	    	double minX = Double.MAX_VALUE;
	    	double maxX = Double.MIN_VALUE;
	    	double minY = Double.MAX_VALUE;
	    	double maxY = Double.MIN_VALUE;	    	
	    	double minZ = Double.MAX_VALUE;
	    	double maxZ = Double.MIN_VALUE;
	    	double x,y,z;
	    	IVector3D transfer = new Vector3D(getPosition());
	    	for (int i=0; i<numOfVertices;i++){
	    		line = dis.readLine();
	    		String[] a = line.split(" ");
	    		x = Double.parseDouble(a[0])*getScale();
	    		y = Double.parseDouble(a[1])*getScale();
	    		z = Double.parseDouble(a[2])*getScale();
	    		minX = (x<minX)? x : minX;
	    		maxX = (x>maxX)? x : maxX;
	    		minY = (y<minY)? y : minY;
	    		maxY = (y>maxY)? y : maxY;
	    		minZ = (z<minZ)? z : minZ;
	    		maxZ = (z>maxZ)? z : maxZ;
	    		vertices[i] = (new Point3D(x,y,z)).movePointByVector(transfer);
	    	}
	    	// build bounding box
	    	IPoint3D p0 = new Point3D(minX,minY,minZ).movePointByVector(transfer);
	    	IPoint3D p1 = new Point3D(maxX,minY,minZ).movePointByVector(transfer);
	    	IPoint3D p2 = new Point3D(minX,maxY,minZ).movePointByVector(transfer);
	    	IPoint3D p3 = new Point3D(minX,minY,maxZ).movePointByVector(transfer);
	    	boundingBox = new Box(p0,p1,p2,p3);
	    	// create triangles from the vertices
	    	for (int i=0; i<numOfTriangles;i++){
	    		line = dis.readLine();
	    		String[] a = line.split(" ");
	    		int iPoint1 = Integer.parseInt(a[1]);
	    		int iPoint2 = Integer.parseInt(a[2]);
	    		int iPoint3 = Integer.parseInt(a[3]);
	    		triangles[i] = new AnotatedTriangle();
	    		triangles[i].triangle = new Triangle(vertices[iPoint1],vertices[iPoint2],vertices[iPoint3]);
	    		triangles[i].iPoint1 = iPoint1;
	    		triangles[i].iPoint2 = iPoint2;
	    		triangles[i].iPoint3 = iPoint3;
	    	}
	    	fis.close();
	    	bis.close();
	    	dis.close();
	    }catch(IOException e){
	    	throw new NumberFormatException();
	    }
	}
	
	/**
	 * Returns the normal for a vertex (the average of the triangles with that vertex).
	 * 
	 * @param vertexIndex The index of the vertex in the vertexArray.
	 * @return the normal for a vertex (the average of the triangles with that vertex).
	 */
	private IVector3D findNormalForVertex(int vertexIndex){
		IVector3D normal = new Vector3D(0,0,0);
		int count = 0;
		for (int i=0;i<triangles.length; i++){
			if ((triangles[i].iPoint1==vertexIndex)||(triangles[i].iPoint2==vertexIndex)||(triangles[i].iPoint3==vertexIndex)){
				normal = normal.addVector(triangles[i].triangle.getNormalVector().normalize());
				count++;
			}
		}
		if (count==0){
			return new Vector3D(0,0,0);
		}
		normal = normal.multiplyByScalar(1d/(double)count);
		return normal.normalize();
	}
	
	/**
	 * Returns the normal for a given hitPoint (interpolation of the 3 vertices of the triangle).
	 * 
	 * @param hitPoint The hit point of the intersection.
	 * @param at The annotated triangle of the intersection.
	 * @return The normal for a given hitPoint (interpolation of the 3 vertices of the triangle).
	 */
	private IVector3D getNormal(IPoint3D hitPoint, AnotatedTriangle at){
		
		// Interpolation of the Normals (u,v,w) are weights for each vertex of the triangle
		
		//IVector3D ac = new Vector3D(at.triangle.getP0(),at.triangle.getP1());
		//IVector3D bc = new Vector3D(at.triangle.getP2(),at.triangle.getP1());
		//IVector3D pc = new Vector3D(at.triangle.getP2(),hitPoint);
		//IVector3D denom = ac.crossProduct(bc);
		double u = 1d/3d;//bc.crossProduct(pc)/(denom);
		double v = 1d/3d;//ac.crossProduct(pc)/(denom);
		double w = 1 - u - v;
		//System.err.println(u+" "+v+" "+w);
		IVector3D normal = new Vector3D(0,0,0);
		normal = normal.addVector(verticesNormals[at.iPoint3].multiplyByScalar(w));
		normal = normal.addVector(verticesNormals[at.iPoint2].multiplyByScalar(u));
		normal = normal.addVector(verticesNormals[at.iPoint1].multiplyByScalar(v));
		return normal.normalize();
	}
	
	/**
	 * Calculates the normals of all the vertices and saves them to an Array.
	 */
	private void calculateVerticesNormals() {
		verticesNormals = new Vector3D[vertices.length];
		for (int i=0; i<vertices.length; i++){
			verticesNormals[i] = findNormalForVertex(i);
		}
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