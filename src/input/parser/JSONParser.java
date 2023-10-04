/**
 * @author Grace Warren, Khushi Patel, and Wick Martin 
 * JSONParser: takes JSON file and stores information to be read in FigureNode.
 * takes strings and node objects to be returned as a complete figure.
 */

package input.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import input.components.*;
import input.components.PointNode;
import input.components.PointNodeDatabase;
import input.components.SegmentNodeDatabase;
import input.exception.ParseException;

/**
 * Public class JSONParser: takes a JSON file as input and stores information to be read in FigureNode.
 * Takes strings and node objects to be returned as a complete figure.
 */
public class JSONParser
{
	/**
	 * _astRoot instance variable contains a component node.
	 * ComponentNode is implemented by FigureNode; 
	 * 		JSONParser creates a ComponentNode by traversing the JSON file,
	 * 		creating a description, PointNodeDatabase, and SegmentNodeDatabase,
	 * 		instantiating a FigureNode which contains these items, and assigning
	 * 		that FigureNode to _astRoot.
	 */
	protected ComponentNode  _astRoot;

	/**
	 * JSONParser constructor
	 * Initializes the _astRoot variable value 
	 */
	public JSONParser()
	{
		_astRoot = null;
	}

	/**
	 * error() method for JSONParser
	 * Throws a message when a portion of the JSONFile cannot be parsed 
	 * 		This occurs when there is nothing in the file to parse
	 * @param message contains the message shown to the user when this error (exception) is thrown
	 */
	private void error(String message)
	{
		throw new ParseException("Parse error: " + message);
	}

	/**
	 * parse() method for JSONParser
	 * This method reads the JSON File, and digests the nested dictionaries within that file,
	 * 		converting that information to generic classes PointNodeDatabase and SegmentNodeDatabase.
	 * 		This provides what is needed to create a tree, which is much easier to traverse.
	 * @param str contains the JSON File as a string
	 * @return _astRoot - a ComponentNode
	 * @throws ParseException when there is no figure to be parsed within str
	 */
	public ComponentNode parse(String str) throws ParseException
	{
		/**
		 * Parsing is accomplished via the JSONTokenizer class.
		 */
		JSONTokener tokenizer = new JSONTokener(str);
		JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();
		
		/**
		 * This case handles exceptions wherein the JSON File has no figure to parse
		 */
		if (!JSONroot.has("Figure")) {
			error("No figure found.");
		}
		/**
		 * This extracts the (valid) figure from the JSON File
		 */
		JSONObject figure = JSONroot.getJSONObject("Figure");
		
		/**
		 * From the figure, this extracts the corresponding data for the figure's 
		 * 		description, points (PointNode), and segments (SegmentNode)
		 * Note that description is fully handled in lines 90, while pndb and sndb are not
		 */
		String description = figure.getString("Description");
		JSONArray pndb = figure.getJSONArray("Points");
		JSONArray sndb = figure.getJSONArray("Segments");
		
		/**
		 * These method calls read the respective pndb and sndb information to create 
		 * 		instances of PointNodeDatabase and SegmentNodeDatabase
		 */
		PointNodeDatabase pointNodeDatabase = readsPNDB(pndb);
		SegmentNodeDatabase segmentNodeDatabase = readsSNDB(sndb, pointNodeDatabase);
		
		/**
		 * Instantiates a FigureNode object, and assigns it to _astRoot
		 * returns _astRoot
		 */
		_astRoot = new FigureNode(description, pointNodeDatabase, segmentNodeDatabase);
		return _astRoot;
	}
	
	/**
	 * readsPNDB() method 
	 * 		reads JSON 'pndb' information and converts it to a PointNodeDatabase
	 * @param pndbArray contains 'pndb' - a JSONArray with the point information for the file
	 * @return an instance of PointNodeDatabase, containing the points from the JSON figure
	 */
	public PointNodeDatabase readsPNDB(JSONArray pndbArray) {
		/**
		 * Stores points in an ArrayList, making them iterable
		 * Instantiates the PointNodeDatabase object 
		 */
		ArrayList<JSONObject> newPNDB = new ArrayList<JSONObject>();
		PointNodeDatabase pointNodeDB = new PointNodeDatabase();
		/**
		 * Loops through objects (points) in the pndbArray and populates the ArrayList
		 * 		again: this makes the points iterable 
		 */
		for (Object obj : pndbArray) {
			newPNDB.add((JSONObject) obj);
		}
		/**
		 * For each item in the (now populated) ArrayList, a PointNode object is created
		 * 		This is then added to the instance of PointNodeDatabase
		 */
		for (JSONObject individual_node : newPNDB) {
			String name = individual_node.getString("name");
			Double x = individual_node.getDouble("x");
			Double y = individual_node.getDouble("y");
			//adds point to the DB
			pointNodeDB.put(new PointNode(name, x, y));
		}
		return pointNodeDB;
	}
	
	/**
	 * readsSNDB() method 
	 * 		reads JSON 'sndb' information and converts it to a SegmentNodeDatabase
	 * @param sndbArray contains 'sndb' - a JSONArray with the segment information for the file
	 * @param pointNodeDatabase passes the PointNodeDatabase created by readsPNDB() 
	 * 		this makes PointNode information more easily accessible, when applicable 
	 * @return an instance of SegmentNodeDatabase, containing the segments for the JSON Figure 
	 */
	public SegmentNodeDatabase readsSNDB(JSONArray sndbArray, PointNodeDatabase pointNodeDatabase) {
		/**
		 * Stores segments in an ArrayList, making them iterable
		 * Instantiates the SegmentNodeDatabase object 
		 */
		ArrayList<JSONObject> newSNDB = new ArrayList<JSONObject>();
		SegmentNodeDatabase segmentNodeDB = new SegmentNodeDatabase();
		/**
		 * Loops through objects (segments) in the pndbArray and populates the ArrayList
		 * 		again: this makes the segments iterable 
		 */
		for (Object segmentObj : sndbArray) {
			newSNDB.add((JSONObject) segmentObj);
		}
		/**
		 * For each item in the (now populated) ArrayList, a SegmentNode object is created
		 * 		This is then added to the instance of SegmentNodeDatabase as an undirected edge
		 * This is done by iterating over the keys for a given adjacencyListObj, then iterating
		 * 		over the values associated with that key, creating an undirected edge for each
		 * 		key/value PointNode pair 
		 */
		for (JSONObject adjacencyListObj : newSNDB) {
			String key = adjacencyListObj.keys().next();
			JSONArray values = adjacencyListObj.getJSONArray(key);
			for (Object value : values) {
				segmentNodeDB.addUndirectedEdge(pointNodeDatabase.getNodeByName(key), pointNodeDatabase.getNodeByName((String) value));
			}
		}
		return segmentNodeDB;
	}

}