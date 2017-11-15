// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 * Graph Processor Class
 * @author Eva Kuntz & Merritt Harvey
 *
 */
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
		//first line in file is number of vertices
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

	/**
	 * Method that performs a BFS traversal on this instance's graph
	 * and returns a path starting at the vertex u and ending at the
	 * vertex v, if a path between the two exists.
	 * @param u
	 * 	Starting vertex
	 * @param v
	 * 	Ending vertex
	 * @return
	 * 	ArrayList<String> of the path from vertex u to vertex v, if a
	 * 	path exists, else returns an empty ArrayList<String>.
	 */
	public ArrayList<String> bfsPath(String u, String v)
	{
		ArrayList<String> path = new ArrayList<String>();
		Queue<Vertex> queue = new LinkedList<Vertex>(); //queue for BFS
		HashSet<String> visited = new HashSet<String>(); //for visited vertices
		HashMap<String, String> parent_map = new HashMap<String,String>(); //'parent' array
		
		//if the starting vertex is not in the graph, return the empty list
		if(graph.get_vertex(u) == null) {
			return path;
		}
		queue.add(graph.get_vertex(u)); //get starting vertex (the 'root')
		visited.add(u);//add to visited
		parent_map.put(u, null); //starting vertex has no parent
		
		while(!queue.isEmpty()) {
			Vertex vertex = queue.remove();
			Hashtable<String, Edge> edges = vertex.get_edges();
			//for every outgoing edge of our vertex
			for(String key:edges.keySet()) {
				//if the 'to' vertex is not marked
				String to_vertex_name = key;
				String from_vertex_name = edges.get(key).get_from();
				if(from_vertex_name.equals(vertex.name) && !visited.contains(to_vertex_name)){
					//set parent
					parent_map.put(to_vertex_name, vertex.name);
					//mark 'to' vertex and add to queue
					queue.add(graph.get_vertex(to_vertex_name));
					visited.add(to_vertex_name);
				}
				
			}
		}
		
		//if the 'to' vertex does not appear in our parent array
		//return empty arrayList
		if(!parent_map.containsKey(v)) {
			return path;
		}
		
		path.add(v);
		String parent = parent_map.get(v); //get parent of v ('to' vertex)
		while(parent != null) {
			//if(parent != null) {
				path.add(parent);
				parent = parent_map.get(parent); //get next parent
			//}
		}
		//now, reverse order of 'path' so we start with the 'from' vertex and end at 'to' vertex
		Stack<String> stack = new Stack<String>();
		for(int i = 0; i < path.size(); i++) {
			stack.push(path.get(i));
		}
		path.clear(); //clear list
		
		while(!stack.isEmpty()) {
			path.add(stack.pop());
		}
		
		return path;
	}

	/**
	 * Returns the diameter of the graph.
	 * @return
	 * 	Diameter of the graph. If graph is empty, then returns 0.
	 */
	public int diameter()
	{
		ArrayList<String> bfs = bfs(graph.get_vertices().get(0).name);
		String s = bfs.get(bfs.size()-1);
		ArrayList<String> second_bfs = bfs(s);
		String t = second_bfs.get(second_bfs.size() - 1);
		
		ArrayList<String> diameter_list = bfsPath(s,t);
		int diameter = diameter_list.size();
		if(diameter == 0) {
			return 2 * num_vertices;
		}		
		return (diameter - 1);
	}
	
	/**
	 * Private helper method that implements BFS starting from a
	 * given root node.
	 * Performs BFS on entire graph, not just finding a path
	 * between two given vertices.
	 * @param root
	 * 	Root vertex to start BFS traversal from
	 * @return
	 * 	ArrayList<String> of the vertices visited in BFS traversal;
	 * 	if the graph is empty, returns an empty list.
	 */
	private ArrayList<String> bfs(String root){
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> path = new ArrayList<String>();
		Queue<Vertex> queue = new LinkedList<Vertex>(); //queue for BFS
		HashSet<String> visited = new HashSet<String>(); //for visited vertices
	
		//if the starting vertex is not in the graph, return the empty list
		if(graph.get_vertex(root) == null) {
			return path;
		}
		queue.add(graph.get_vertex(root)); //get starting vertex (the 'root')
		visited.add(root);//add to visited
		
		while(!queue.isEmpty()) {
			Vertex vertex = queue.remove();
			result.add(vertex.name);
			Hashtable<String, Edge> edges = vertex.get_edges();
			//for every outgoing edge of our vertex
			for(String key:edges.keySet()) {
				//if the 'to' vertex is not marked
				String to_vertex_name = key;
				String from_vertex_name = edges.get(key).get_from();
				if(from_vertex_name.equals(vertex.name) && !visited.contains(to_vertex_name)){
					//mark 'to' vertex and add to queue
					queue.add(graph.get_vertex(to_vertex_name));
					visited.add(to_vertex_name);
				}
				
			}
		}
		return result;
	}

	/**
	 * Calculates the centrality of a given Vertex v.
	 * @param v
	 * 	Vertex v to calculate the centrality for
	 * @return
	 * 	Number of shorts paths that pass through Vertex v, otherwise returns 0.
	 */
	public int centrality(String v)
	{
		//get all vertices for this graph
		int count = 0;
		ArrayList<Vertex> vertices = graph.get_vertices();
		//for every vertex v in vertices
		for(int i = 0; i < vertices.size(); i ++) {
			//for every vertex u in vertices
			for(int j = 0; j < vertices.size(); j++) {
				//calculate one of the shortest paths
				ArrayList<String> path = bfsPath(vertices.get(i).name, vertices.get(j).name);
				
				//if there actually exists a path between the vertices
				if(path != null) {
					//AND if the path contains the vertex that we want to calculate the centrality for, increment count
					int index = path.indexOf(v); //returns -1 if not found
					//if(index != -1 && index != 0 && index != (path.size() - 1)){
					if(index != -1) {
						count++;
					}
				}
				
			}
		}
		return count;
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
			GraphProcessor gp = new GraphProcessor("C:\\Users\\kuntz\\Development\\coms311-project-two\\src\\eva_test_two.txt");
			
			ArrayList<Vertex> vertices = gp.get_vertices();
			for(int i = 0; i < vertices.size(); i++) {
				System.out.println(vertices.get(i).name + " Edges Size: " + vertices.get(i).edges.size());
			}
			
			System.out.println("Out degree of 3: " + gp.outDegree("3"));
			
			ArrayList<String> path = gp.bfsPath("0", "3"); //Minneapoli
			System.out.print("BFSPath: ");
			for(int i = 0; i < path.size(); i ++) {
				System.out.print(path.get(i) + " ");
			}
			System.out.println("");
			
			path.clear();
			path = gp.bfs("3");
			System.out.print("BFS: ");
			for(int i = 0; i < path.size(); i ++) {
				System.out.print(path.get(i) + " ");
			}
			System.out.println("");
			
			
			System.out.println("Centrality of 0: " + gp.centrality("0"));
			System.out.println("Centrality of 1: " + gp.centrality("1"));
			System.out.println("Centrality of 2: " + gp.centrality("2"));
			System.out.println("Centrality of 3: " + gp.centrality("3"));
			
			System.out.println("Diameter: " + gp.diameter());
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

}