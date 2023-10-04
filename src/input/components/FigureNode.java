/**
 * authors: Grace Warren, Khushi Patel, and Wick Martin 
 * FigureNode: reads JSON file and reads parsed information to be returned as a final string format figure.
 */

package input.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import input.components.PointNode;
import input.components.PointNodeDatabase;
import input.components.SegmentNodeDatabase;
import utilities.io.StringUtilities;

/**
 * A basic figure consists of points, segments, and an optional description
 * 
 * Each figure has distinct points and segments (thus unique database objects).
 * 
 */
public class FigureNode implements ComponentNode
{
	protected String              _description;
	protected PointNodeDatabase   _points;
	protected SegmentNodeDatabase _segments;

	public String              getDescription()    { return _description; }
	public PointNodeDatabase   getPointsDatabase() { return _points; }
	public SegmentNodeDatabase getSegments()       { return _segments; }

	public FigureNode(String description, PointNodeDatabase points, SegmentNodeDatabase segments)
	{
		_description = description;
		_points = points;
		_segments = segments;
	}

	@Override
	public void unparse(StringBuilder sb, int level)
	{
		//initialize level of the tree for reading
		level = 0;
		//add indentation and formating into the string builder for "Figure"
		sb.append(StringUtilities.indent(level) + "Figure" + "\n");
		sb.append(StringUtilities.indent(level) + "{" + "\n");
		//handle writing the description at the next level below Figure 
		handleDescription(sb, level+1);
		//handle writing the points at the next level below Description
		handlePoints(sb, level+1);
		//handle writing the segments at the next level below Points
		handleSegments(sb, level+1);
		//add and complete remaining details of the figure into the string builder 
		sb.append(StringUtilities.indent(level) + "}" + "\n");
	}

	public void handleDescription(StringBuilder sb, int level) {
		//adds a description to be output as a string
		sb.append(StringUtilities.indent(level) + "Description: " + _description + "\n");
	}

	public void handlePoints(StringBuilder sb, int level) {
		//adds string structure for the list of points
		sb.append(StringUtilities.indent(level) + "Points: " + "\n");
		sb.append(StringUtilities.indent(level) + "{" + "\n");
		
		//create a list of strings containing all points 
		List<String> names = _points.getAllNodeNames();
		//loop through all names of points and add them to the string builder from the DB
		for (String name : names) {
			//get the name of current node in the list 
			PointNode currNode = _points.getNodeByName(name);
			//add to string output
			sb.append(StringUtilities.indent(level + 1) + "Point(" + currNode.getName() 
			+ ")(" + currNode.getX() + ", " + currNode.getY() + ")" + "\n");
		}
		sb.append(StringUtilities.indent(level) + "}" + "\n");
	}

	public void handleSegments(StringBuilder sb, int level) {
		//adds string structure for the list of segments
		sb.append(StringUtilities.indent(level) + "Segments: " + "\n");
		sb.append(StringUtilities.indent(level) + "{" + "\n");
		
		//create a list of strings containing all segments
		List<String> names = _points.getAllNodeNames();
		//loop through all names of segments and add them to the string builder from the DB
		for (String name : names) {
			//adds to string builder basic structure 
			sb.append(StringUtilities.indent(level+1) + name + " : ");
			//loop to create line segments by getting points and writing them as a segment from the DB
			for (String edgeName : _segments.edgesAsList(_points.getNodeByName(name))) {
				sb.append(edgeName + "    ");
			}			
			sb.append("\n");
		}
		sb.append(StringUtilities.indent(level) + "}" + "\n");
	}

}