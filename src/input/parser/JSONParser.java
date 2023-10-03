package input.parser;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

		JSONObject figure = JSONroot.getJSONObject("Figure");
		String description = JSONroot.getString("Description");
		JSONArray pndb = JSONroot.getJSONArray("Points");
		JSONArray sndb = JSONroot.getJSONArray("Segments");
		
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
			Double x = Double.parseDouble(individual_node.getString("x"));
			Double y = Double.parseDouble(individual_node.getString("y"));
			pointNodeDB.put(new PointNode(name, x, y));
		}
		return pointNodeDB;
	}
	
	public SegmentNodeDatabase readsSNDB(JSONArray sndbArray, PointNodeDatabase pointNodeDatabase) {
		ArrayList<JSONObject> newSNDB = new ArrayList<JSONObject>();
		ArrayList<JSONObject> adjacencyList = new ArrayList<JSONObject>();
		SegmentNodeDatabase segmentNodeDB = new SegmentNodeDatabase();
		for (Object obj : sndbArray) {
			newSNDB.add((JSONObject) obj);
		}
		for (JSONObject adjList : newSNDB) {
			adjacencyList.add((JSONObject) adjList);
		}
		for (JSONObject adjObj : adjacencyList) {
			List<PointNode> destinationList = new ArrayList<PointNode>();
			PointNode currentOrigin = pointNodeDatabase.getNodeByName((String) adjObj.get("Origin")); //maybe add helper in pndb to return node with name
			destinationList.add((PointNode) adjObj.get("Destination List"));
			for (PointNode dest : destinationList) {
				segmentNodeDB.addUndirectedEdge(currentOrigin, dest);
			}
		}
		return segmentNodeDB;
	}

}