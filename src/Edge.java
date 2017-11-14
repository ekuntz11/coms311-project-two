/**
 * Class that represents an Edge in our Graph.  
 * @author Eva Kuntz & Merritt Harvey
 *
 */
public class Edge {
	
	/**
	 * From vertex.
	 */
    protected Vertex from;
    /**
     * To vertex.
     */
    protected Vertex to;
    
    /**
     * Creates an edge from the specified 'from'
     * vertex that goes to the specified 'to' vertex.
     * @param from
     * 	from vertex in this edge
     * @param to
     * 	to vertex in this edge
     */
    protected Edge(Vertex from, Vertex to)
    {
      this.from = from;
      this.to = to;
    }

    /**
     * Returns the from vertice's name
     * @return
     * 	name of the from vertex
     */
    public String get_from()
    {
      return from.name;
    }
    
    /**
     * Returns the to vertice's name
     * @return
     * 	name of the to vertex
     */
    public String get_to()
    {
      return to.name;
    }
  }