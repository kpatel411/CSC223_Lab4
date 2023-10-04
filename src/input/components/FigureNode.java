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
		level = 0;
		sb.append(StringUtilities.indent(level) + "Figure" + "\n");
		sb.append(StringUtilities.indent(level) + "{" + "\n");
		handleDescription(sb, level+1);
		handlePoints(sb, level+1);
		handleSegments(sb, level+1);
		sb.append(StringUtilities.indent(level) + "}" + "\n");
		//TODO: print the string builder
	}

	public void handleDescription(StringBuilder sb, int level) {
		sb.append(StringUtilities.indent(level) + "Description: " + _description + "\n");
	}

	public void handlePoints(StringBuilder sb, int level) {
		sb.append(StringUtilities.indent(level) + "Points: " + "\n");
		sb.append(StringUtilities.indent(level) + "{" + "\n");
		List<String> names = _points.getAllNodeNames();
		for (String name : names) {
			PointNode currNode = _points.getNodeByName(name);
			sb.append(StringUtilities.indent(level + 1) + "Point(" + currNode.getName() 
			+ ")(" + currNode.getX() + ", " + currNode.getY() + ")" + "\n");
		}
		sb.append(StringUtilities.indent(level) + "}" + "\n");
	}

	public void handleSegments(StringBuilder sb, int level) {
		sb.append(StringUtilities.indent(level) + "Segments: " + "\n");
		sb.append(StringUtilities.indent(level) + "{" + "\n");
		List<String> names = _points.getAllNodeNames();
		System.out.println("Name: " + names);
		for (String name : names) {
			sb.append(StringUtilities.indent(level+1) + name + " : ");
			for (String edgeName : _segments.edgesAsList(_points.getNodeByName(name))) {
				System.out.println("current edgeName: " + edgeName);
				sb.append(edgeName + "    ");
			}			
			sb.append("\n");
		}
		sb.append(StringUtilities.indent(level) + "}" + "\n");
	}

}