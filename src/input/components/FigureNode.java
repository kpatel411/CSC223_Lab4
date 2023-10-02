package input.components;

import java.util.Set;

import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;

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
		FigureNode figure = new FigureNode();
        System.out.println("Figure");
        System.out.format("%8s", "{");
		for (PointNode node : _points) {
			System.out.format("%16s%2s%2s", node.getName(), node.getX(), node.getY());
		
		}
		// TODO
    }
}