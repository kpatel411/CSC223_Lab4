/**
 * authors: Grace Warren, Khushi Patel, and Wick Martin 
 * JSONParserTest: Tests for JSONParser functionality and returns string format of figure objects.
 */

package input.parser;


import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import input.components.ComponentNode;
import input.components.FigureNode;
import input.exception.ParseException;

class JSONParserTest
{
	public static ComponentNode runFigureParseTest(String filename)
	{
		JSONParser parser = new JSONParser();

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);
		
		return parser.parse(figureStr);
	}
	
	@Test
	void empty_json_string_test()
	{
		JSONParser parser = new JSONParser();

		assertThrows(ParseException.class, () -> { parser.parse("{}"); });
	}

	@Test
	void single_triangle_test()
	{
		//
		// The input String ("single_triangle.json") assumes the file is
		// located at the top-level of the project. If you move your input
		// files into a folder, update this String with the path:
		//                                       e.g., "my_folder/single_triangle.json"
		//
		ComponentNode node = JSONParserTest.runFigureParseTest("single_triangle.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	
	@Test
	void collinear_line_segments_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("collinear_line_segments.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	
	@Test
	void crossing_symmetric_triangle_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("crossing_symmetric_triangle.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	
	@Test
	void fully_connected_irregular_polygon_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("fully_connected_irregular_polygon.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	
//	    /_\__/_\
//	   /  .  .  \
//	  |   /_\   |
// 	  |__ \|/ __| 
//     \/_____\/
//     	
	@Test
	void catWithTriangles_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("catWithTriangles.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	
//			______
//		   |      |
//		   |	  |
//		___|______|___
//		   /     \
//		  /       \
//	     |    |\   |
//		 | 	  |/   |
//		  \       /
//	_\_    \_____/    _/_
//	  \   /        \  /
//	   \ /          \/
//		|    	     |
//		| 	   		 |
//		 \          /
//	      \________/
//	     /         \
//		/           \
//	   /             \
//	  |    	          |
//	  | 	   		  |
//	   \             /
// 	    \___________/
	
	
	@Test
	void octogonSnowman_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("octogonSnowman.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	
//    ____________
//   /            \
// 	/              \
//  |  	 .    .    |
//  |              |
//  |     ____     |
//  |              |
//  |          	   |
//  |          	   |
//  |              |
//  |/_\/_\/_\/_\/_|
	
	@Test
	void pacmanGhost_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("pacmanGhost.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
}
//TODO: write images into JSON files and consider more triangle shape test
