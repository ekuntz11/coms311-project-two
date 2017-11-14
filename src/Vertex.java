import java.util.Hashtable;

/**
 * Class to represent vertices in a graph.
 * @author Eva Kuntz & Merritt Harvey
 *
 */
public class Vertex {
	/**
	 * Name of vertex
	 */
    protected String name;
    
    /**
     * Hashtable of outgoing edges for this vertex
     */
    protected Hashtable<String, Edge> edges;

    /**
     * Constructor for a vertex
     * @param name
     * 	Name of vertex
     */
    protected Vertex(String name)
    {
      this.name = name;
      edges = new Hashtable<String, Edge>();
    }

    /**
     * Returns the name of this vertex
     * @return
     * 	name of this vertex
     */
    public String get_name()
    {
      return name;
    }

    /**
     * Returns a hashtable of the outgoing edges for this vertex
     * @return
     * 	Hashtable<String, Edge> of this outgoing edges for this vertex.
     * 	Returns an empty hashtable if this vertex has no outgoing edges
     */
    public Hashtable<String, Edge> get_edges()
    {
      return edges;
    }

    /**
     * Checks whether two vertices are equal or not.
     * @return
     * 	True if the vertices are the same, false otherwise
     */
    public boolean equals(Object obj)
    {
      if ( this == obj ) return true;
      if ( (obj == null) || (obj.getClass() != this.getClass()) )
           return false;
      // object must be Vertex at this point
      Vertex test = (Vertex) obj;
      return (name.equals(test.name) && edges.equals(test.edges));
    }

  };