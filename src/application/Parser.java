package application;
import java.io.*;

/**
 * This class represents the scene configuration file parser.
 * It is a template for a parser, a specific scene parser must inherit from this class
 * and override its methods.
 *
 */
public class Parser 
{
	/**
	 * This class represents a parsing exception.
	 *
	 */
	public static class ParseException extends Exception {

		private static final long serialVersionUID = -2072472264791758783L;

		public ParseException(String msg) {  super(msg); }
	}
	
	/**
	 * Constructs a scene configuration file parser.
	 */
	public Parser() {}
	
	/**
	 * Parses the given file (given as a Reader type)
	 * 
	 * @param in Configuration file to parse
	 * @throws IOException
	 * @throws ParseException
	 */
	public final void parse(Reader in) throws IOException, ParseException
	{
		BufferedReader r = new BufferedReader(in);
		String line, curobj = null;
		int lineNum = 0;
		startFile();

		while ((line = r.readLine()) != null)
		{
			line = line.trim();
			++lineNum;
			
			if (line.isEmpty() || (line.charAt(0) == '#'))
			{  // comment
				continue;
			}
			else if (line.charAt(line.length() - 1) == ':')
			{ // new object;
				if (curobj != null)
					commit();
				curobj = line.substring(0, line.length() - 1).trim().toLowerCase();
				if (!addObject(curobj))
					reportError(String.format("Did not recognize object: %s (line %d)", curobj, lineNum));
			}
			else
			{ 
				int eqIndex = line.indexOf('=');
				if (eqIndex == -1)
				{
					reportError(String.format("Syntax error line %d: %s", lineNum, line));
					continue;
				}
				String name = line.substring(0, eqIndex).trim().toLowerCase();
				//String[] args = line.substring(eqIndex + 1).trim().toLowerCase().split("\\s+");
				String[] args = line.substring(eqIndex + 1).trim().split("\\s+");

				if (curobj == null)
				{
					reportError(String.format("parameter with no object %s (line %d)", name, lineNum));
					continue;
				}

				if (!setParameter(name, args))
					reportError(String.format("Did not recognize parameter: %s of object %s (line %d)", name, curobj, lineNum));
			}
		}
		if (curobj != null)
			commit();
		
		endFile();
		pAssert((lineNum!=0),"Scene Defenition file is empty");
	}
	
	// utility assertion.
	// use this for parameter validity checks.
	/**
	 * Assert method for the parsing process.
	 * 
	 * @throws ParseException
	 */
	static void pAssert(Boolean v, String msg) throws ParseException
	{
		if (!v)
			throw new ParseException(msg);
	}
	
	///////////////////// override these methods in your implementation //////////////////
	
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

	// start a new object definition
	// return true if recognized
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
		return true;
	}
	
	// set a specific parameter for the current object
	// return true if recognized
	
	/**
	 * Sets the parameters for an object.
	 * Note that the object is not defined, and is up to you to get the object.
	 * 
	 * @param name The name of the parameter
	 * @param args The values for the parameter (given as String[])
	 * @return 'true' if the parameter is recognized by the scene
	 * @throw ParseException
	 */
	public boolean setParameter(String name, String[] args) throws ParseException
	{
		System.out.print("PARAM: " + name);
	    for (String s : args) 
	        System.out.print(", " + s);
	    System.out.println();
	    return true;
	}
	
	// finish the parsing of the current object
	// here is the place to perform any validations over the parameters and
	// final initializations.
	/**
	 * Finishes the parsing of the current object (may perform validations and initializations).
	 * 
	 * @throws ParseException
	 */
	public void commit() throws ParseException
	{
		
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

