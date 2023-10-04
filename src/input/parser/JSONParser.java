/**
 * authors: Grace Warren, Khushi Patel, and Wick Martin 
 * JSONParser: takes JSON file and stores information to be read in FigureNode.
 * takes strings and node objects to be returned as a complete figure.
 */

package input.parser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import input.components.*;
import input.components.PointNode;
import input.components.PointNodeDatabase;
import input.components.SegmentNodeDatabase;
import input.exception.ParseException;

public class JSONParser
{
	protected ComponentNode  _astRoot;

	public JSONParser()
	{
		_astRoot = null;
	}

	private void error(String message)
	{
		throw new ParseException("Parse error: " + message);
	}

	public ComponentNode parse(String str) throws ParseException
	{
		// Parsing is accomplished via the JSONTokenizer class.
		JSONTokener tokenizer = new JSONTokener(str);
		JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();
		
		System.out.println(JSONroot);

		//case for when JSON has no figure to parse
		if (!JSONroot.has("Figure")) {
			error("No figure found.");
		}
		//finds valid figure
		JSONObject figure = JSONroot.getJSONObject("Figure");
		
		//reads description and points
		String description = figure.getString("Description");
		JSONArray pndb = figure.getJSONArray("Points");
		JSONArray sndb = figure.getJSONArray("Segments");
		
		//reads into respective databases to create existing objects 
		PointNodeDatabase pointNodeDatabase = readsPNDB(pndb);
		SegmentNodeDatabase segmentNodeDatabase = readsSNDB(sndb, pointNodeDatabase);
		
		//creates a figure object with description and stored figure info
		_astRoot = new FigureNode(description, pointNodeDatabase, segmentNodeDatabase);
		return _astRoot;
	}
	
	//method for JSON to read points from PointNode DB
	public PointNodeDatabase readsPNDB(JSONArray pndbArray) {
		//stores points in an array list
		ArrayList<JSONObject> newPNDB = new ArrayList<JSONObject>();
		PointNodeDatabase pointNodeDB = new PointNodeDatabase();
		//loop to add points into a new DB
		for (Object obj : pndbArray) {
			newPNDB.add((JSONObject) obj);
		}
		//loop to return a point with a name and location 
		for (JSONObject individual_node : newPNDB) {
			String name = individual_node.getString("name");
			Double x = individual_node.getDouble("x");
			Double y = individual_node.getDouble("y");
			//adds point to the DB
			pointNodeDB.put(new PointNode(name, x, y));
		}
		return pointNodeDB;
	}
	
	//method for JSON to read points from SegmentNode DB
	public SegmentNodeDatabase readsSNDB(JSONArray sndbArray, PointNodeDatabase pointNodeDatabase) {
		//stores segments in an array list 
		ArrayList<JSONObject> newSNDB = new ArrayList<JSONObject>();
		//ArrayList<PointNode> adjacencyList = new ArrayList<PointNode>();
		
		//create a new segment node DB to hold created segments
		SegmentNodeDatabase segmentNodeDB = new SegmentNodeDatabase();
		//put objects in array list
		for (Object obj : sndbArray) {
			newSNDB.add((JSONObject) obj);
		}
		//loop through the new segment DB to create segments from points in the point DB
		for (JSONObject arrayObj : newSNDB) {
			String key = arrayObj.keys().next();
			JSONArray values = arrayObj.getJSONArray(key);
//			System.out.println("x: " + values);
			for (Object value : values) {
				segmentNodeDB.addUndirectedEdge(pointNodeDatabase.getNodeByName(key), pointNodeDatabase.getNodeByName((String) value));
			}
		}
		return segmentNodeDB;
	}

}