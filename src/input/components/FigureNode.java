package input.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;
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
		sb.append(handleDescription(sb, level+1));
		sb.append(handlePoints(sb, level+1));
		sb.append(handleSegments(sb, level+1));
		sb.append(StringUtilities.indent(level) + "}" + "\n");
		//TODO: print out the string builder 

		//sb contains nothing
		//	goal is to add to it 
		//	unparsing is a tostring method for a tree - the figure node that we got from JSONParser 
		//	should have method calls for instance variables 
		//can add unparse to pndb/sndb or use tostring to get names
		//	you can then iterate using the helper method outlined below 
		//	unparse is recommended 


		//Questions for FigureNode class:
		//	This class does not contain a default interpretation of the string builder
		//		parameter, like JSONParser did for the string parameter.
		//		How can we break this down properly? Is this also a set of 
		//		nested dictionaries? Or is there another way to view it?
		//	How can we use the FigureNode constructor, considering the above question?
		//	While it doesn't change things, where are the getDescription(), 
		//		getPointsDatabase(), and getSegments() methods being stored?
		//	Is this unparse() method meant to print the string builder in the same
		//		format example given in the lab instructions? If so, should we
		//		be creating helper methods that print the values we assign to 
		//		the instance variables in FigureNode?
		//	How is unparse different from parse
	}
	// TODO

	public StringBuilder handleDescription(StringBuilder sb, int level) {
		
	}
	
	public StringBuilder handlePoints(StringBuilder sb, int level) {
		List<String> names = _points.getAllNodeNames();
	}
	
	public StringBuilder handleSegments(StringBuilder sb, int level) {

	}

}