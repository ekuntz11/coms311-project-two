// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;


public class GraphProcessor
{
	/**
	 * Graph for this instance
	 */
	Graph graph;
	
	/**
	 * Number of vertices in this graph
	 */
	int num_vertices;

	// NOTE: graphData should be an absolute file path
	/**
	 * Constructor for GraphProcessor class
	 * @param graphData
	 * 	Absolute file path for the graph data
	 * @throws FileNotFoundException
	 *  if file is not found
	 */
	public GraphProcessor(String graphData) throws FileNotFoundException
	{
		File file = new File(graphData);
		Scanner inScanner = new Scanner(file);
		//first line is number of vertices
		num_vertices = Integer.parseInt(inScanner.nextLine());
		String s = "";
		String[] strings;
		
		graph = new Graph(num_vertices);
		while(inScanner.hasNextLine()) {
			s = inScanner.nextLine();
			strings = s.split(" ");
			graph.add_edge(strings[0], strings[1]);		
		}
		//close scanner; release resources back to system
		inScanner.close();		
	}

	/**
	 * Returns the out degree of the input vertex string
	 * @param v
	 * 	vertex string to get out degree for
	 * @return
	 * 	out degree of the input vertex string, else -1 	
	 */
	public int outDegree(String v)
	{
		return graph.get_out_degree(v);
	}


	public ArrayList<String> bfsPath(String u, String v)
	{
		//TODO implementation
		return null;
	}

	public int diameter()
	{
		// TODO implementation
		return 0;
	}

	public int centrality(String v)
	{
		//TODO implementation
		return 0;
	}
	
	/**
	 * Private helper method to get array list of this
	 * instance's graph's vertices
	 * @return
	 * 	array list of vertices
	 */
	private ArrayList<Vertex> get_vertices(){
		return graph.get_vertices();
	}
	
	/**
	 * Private helper method to return the set of edges
	 * for a given vertex
	 * @param v
	 * 	Vertex to return the set of outgoing edges
	 * @return
	 * 	set of outgoing edges
	 */
	private Hashtable<String, Edge> get_edges(Vertex v){
		return v.get_edges();
	}
	
	public static void main(String[] args) {
		try{
			GraphProcessor gp = new GraphProcessor("C:\\Users\\kuntz\\Development\\coms311-project-two\\src\\eva_test.txt");
			
			ArrayList<Vertex> vertices = gp.get_vertices();
			for(int i = 0; i < vertices.size(); i++) {
				System.out.println(vertices.get(i).name + " Edges Size: " + vertices.get(i).edges.size());
			}
			
			System.out.print(gp.outDegree("test"));
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

}