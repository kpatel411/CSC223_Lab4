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

		if (!JSONroot.has("Figure")) {
			error("No figure found.");
		}
		JSONObject figure = JSONroot.getJSONObject("Figure");
		
		String description = figure.getString("Description");
		JSONArray pndb = figure.getJSONArray("Points");
		JSONArray sndb = figure.getJSONArray("Segments");
		
		PointNodeDatabase pointNodeDatabase = readsPNDB(pndb);
		SegmentNodeDatabase segmentNodeDatabase = readsSNDB(sndb, pointNodeDatabase);
		
		_astRoot = new FigureNode(description, pointNodeDatabase, segmentNodeDatabase);
		return _astRoot;
	}
	
	// TODO: implement supporting functionality
	public PointNodeDatabase readsPNDB(JSONArray pndbArray) {
		ArrayList<JSONObject> newPNDB = new ArrayList<JSONObject>();
		PointNodeDatabase pointNodeDB = new PointNodeDatabase();
		for (Object obj : pndbArray) {
			newPNDB.add((JSONObject) obj);
		}
		for (JSONObject individual_node : newPNDB) {
			String name = individual_node.getString("name");
			Double x = individual_node.getDouble("x");
			Double y = individual_node.getDouble("y");
			pointNodeDB.put(new PointNode(name, x, y));
		}
		return pointNodeDB;
	}
	
	public SegmentNodeDatabase readsSNDB(JSONArray sndbArray, PointNodeDatabase pointNodeDatabase) {
		ArrayList<JSONObject> newSNDB = new ArrayList<JSONObject>();
		ArrayList<PointNode> adjacencyList = new ArrayList<PointNode>();
		SegmentNodeDatabase segmentNodeDB = new SegmentNodeDatabase();
		//put objects in array list
		for (Object obj : sndbArray) {
			newSNDB.add((JSONObject) obj);
		}
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