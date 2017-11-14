import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Class to represent a Graph
 * @author Eva Kuntz & Merritt Harvey
 *
 */
public class Graph
{
  /**
   * Hashtable to store the vertices in this graph
   */
	private Hashtable<String, Vertex> vertices;
  
	/**
	 * Default array size for this instance
	 */
	private int array_size;

	/**
	 * Default Graph constructor.
	 */
	public Graph()
	{
		array_size = 128;
		vertices = new Hashtable<String, Vertex>(array_size);
	}
  
  
	/**
	 * Graph constructor
	 * @param size
	 * 	size of the desired graph
	 */
	public Graph(int size) {
	  array_size = size;
	  vertices = new Hashtable<String, Vertex>(array_size);
	}
  
  /**
   * Returns a vertex in the graph represented by the input
   * String (vertice's name).
   * @param v
   * 	vertice's name
   * @return
   * 	the vertex represented by the input string, null if this vertex does not exist in the graph.
   */
	public Vertex get_vertex(String v) {
	  return vertices.get(v);
  }

  /**
   * Method that adds an edge to the graph
   * @param from
   * 	Vertex edge starts from
   * @param to
   * 	Vertex edge ends at
   */
	public void add_edge(String from, String to)
  {

    if (vertices.get(from) == null) {
      vertices.put(from, new Vertex(from));
    }
    if (vertices.get(to) == null) {
      vertices.put(to, new Vertex(to));
    }
    if(!vertices.get(from).edges.contains(to)){
    	vertices.get(from).edges.put(to, new Edge(vertices.get(from), vertices.get(to)));
    }
  }

  /**
   * Method that returns an arraylist of all the vertices in the graph.
   * @return
   * 	Arraylist<Vertex> of all the vertices in the graph. If there are no vertices
   * 	in the graph, returns an empty ArrayList<Vertex>.
   */
	public ArrayList<Vertex> get_vertices()
  {
	ArrayList<Vertex> toreturn = new ArrayList<Vertex>();
	Set<String> keys = vertices.keySet();
	Iterator<String> itr = keys.iterator();
	while (itr.hasNext()) { 
		toreturn.add(vertices.get(itr.next()));
	}
    return toreturn;
  }
  
  /**
   * Returns the out degree of a vertex represented by the String v.
   * @param v
   * 	String that represents a vertex to get out degree of
   * @return
   * 	out degree of the vertex represented by the string s
   */
  public int get_out_degree(String v) {
	  if(vertices.get(v) != null) {
		  return vertices.get(v).edges.size();
	  }
	  return -1;
  }

  public String toString()
  {
    String s;
    s = "";
    
    for (int i = 0; i < vertices.size(); i++) {
      if (vertices.get(i) == null) {
        continue;
      }
      s += i + ": " + vertices.get(i).toString() + "\n";
    }

    return s;
  }
}
