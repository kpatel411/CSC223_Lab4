/**
 * @author Grace Warren, Khushi Patel, and Wick Martin 
 * FigureNode: reads JSON file and reads parsed information to be returned as a 
 * 		final figure formatted as a StringBuilder.
 * 		The FigureNode object is created by the JSONParser class, and is now navigable as a tree
 */

package input.components;

import java.util.List;

import utilities.io.StringUtilities;

/**
 * A basic figure consists of points, segments, and an optional description
 * Each figure has distinct points and segments (thus unique database objects).
 * 
 *  The FigureNode class implements ComponentNode 
 *  	This is the same generic object created in the JSONParser class
 *  	The JSON File has been parsed and is now navigable as a tree, 
 *  	allowing us to print it in a more readable format with the unparse() method
 */ 
public class FigureNode implements ComponentNode
{
	/**
	 * Instantiation of instance variables _description, _points, and _segments,
	 * 		which contain instances of a String, PointNodeDatabase, and SegmentNodeDatabase,
	 * 		created in JSONParser
	 */
	protected String              _description;
	protected PointNodeDatabase   _points;
	protected SegmentNodeDatabase _segments;

	/**
	 * These methods return each instance variable object, which will be helpful in later labs
	 * @return the Description, PointNodeDatabase, and SegmentNodeDatabase objects
	 */
	public String              getDescription()    { return _description; }
	public PointNodeDatabase   getPointsDatabase() { return _points; }
	public SegmentNodeDatabase getSegments()       { return _segments; }

	/**
	 * FigureNode() constructor 
	 * @param description contains the description of the figure
	 * @param points contains the PointNode points within the figure as a PointNodeDatabase
	 * @param segments contains the SegmentNode segments within the figure as a SegmentNodeDatabase
	 */
	public FigureNode(String description, PointNodeDatabase points, SegmentNodeDatabase segments)
	{
		_description = description;
		_points = points;
		_segments = segments;
	}

	/**
	 *  unparse() method
	 *  	Takes the ComponentNode object and traverses it, creating a StringBuilder object
	 *  	This StringBuilder is then printed during testing, allowing us to verify the
	 *  	digestion of a given JSON File in a more readable format 
	 */
	@Override
	public void unparse(StringBuilder sb, int level)
	{
		//initialize level of the tree for reading
		/**
		 * initialize level to 0 for indentation purposes
		 * create basic "Figure" header and opening/closing braces
		 * within those braces, call helper method handleDescription(), handlePoints() and handleSegments()
		 * 		note that level is passed as level + 1; this indents each sub-section another level
		 */
		level = 0;
		sb.append(StringUtilities.indent(level) + "Figure" + "\n");
		sb.append(StringUtilities.indent(level) + "{" + "\n");
		handleDescription(sb, level+1);
		handlePoints(sb, level+1);
		handleSegments(sb, level+1);
		sb.append(StringUtilities.indent(level) + "}" + "\n");
	}

	/**
	 * handDescription() method for FigureNode
	 * @param sb contains the string builder we are creating
	 * @param level contains the indentation level
	 */
	public void handleDescription(StringBuilder sb, int level) {
		/**
		 * Print the description at the appropriate indentation level as a string
		 */
		sb.append(StringUtilities.indent(level) + "Description: " + _description + "\n");
	}

	/**
	 * handlePoints() method for FigureNode
	 * @param sb contains the string builder being beuilt
	 * @param level contains the indentation level 
	 */
	public void handlePoints(StringBuilder sb, int level) {
		/**
		 * Similar to Figure, create the sub-section outline with "Points: " and opening/closing braces
		 */
		sb.append(StringUtilities.indent(level) + "Points: " + "\n");
		sb.append(StringUtilities.indent(level) + "{" + "\n");
		
		/**
		 * Create a list containing the names of all points in the PointNodeDatabase 
		 * 		This makes the points iterable
		 * Loop through the points, and for each one, append the points to the string builder 
		 * 		in the following format: 
		 * 		(indent one level past sub-section outline) Point("name")("x")("y") 
		 * 		followed by a new line 
		 */
		
		//TODO: make this functionality a part of the DBs: this should not allow private access
		List<String> names = _points.getAllNodeNames();
		for (String name : names) {
			PointNode currNode = _points.getNodeByName(name);
			sb.append(StringUtilities.indent(level + 1) + "Point(" + currNode.getName() 
			+ ")(" + currNode.getX() + ", " + currNode.getY() + ")" + "\n");
		}
		sb.append(StringUtilities.indent(level) + "}" + "\n");
	}

	/**
	 * handleSegments() method for FigureNode 
	 * @param sb contains the string builder being built
	 * @param level contains the indentation level
	 */
	public void handleSegments(StringBuilder sb, int level) {
		/**
		 * Similar to Figure, create the sub-section outline with "Segments: " and opening/closing braces
		 */
		sb.append(StringUtilities.indent(level) + "Segments: " + "\n");
		sb.append(StringUtilities.indent(level) + "{" + "\n");
		
		//create a list of strings containing all segments
		/**
		 * Create a list containing the names of all points in the PointNodeDatabase 
		 * 		This makes the points iterable
		 * Loop through the points, and for each one, get an adjacency list containing 
		 * 		all of the nodes that node is connected to. 
		 * append the points to the string builder 
		 * 		in the following format: 
		 * 		(indent one level past sub-section outline) Point("name")("x")("y") 
		 * 		followed by a new line 
		 */
		List<String> names = _points.getAllNodeNames();
		/**
		 *  Loop through the points, and for each one, get an adjacency list containing 
		 * 		all of the nodes that node is connected to. 
		 */
		for (String name : names) {
			/**
			 * for each segment (edge), append the segment information in the following format:
			 * (indent one level past sub-section outline) name ":" 
			 */
			sb.append(StringUtilities.indent(level+1) + name + " : ");
			/**
			 * Populate the edges with a for loop that iterates over them, and add them behind 
			 * 		the ":" colon character 
			 * After the loop is finished, add a new line to separate the next segment adjacency list 
			 */
			for (String edgeName : _segments.edgesAsList(_points.getNodeByName(name))) {
				sb.append(edgeName + "    ");
			}			
			sb.append("\n");
		}
		sb.append(StringUtilities.indent(level) + "}" + "\n");
	}

}