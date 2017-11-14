import java.util.Hashtable;


public class Vertex {
    protected String name;
    protected Hashtable<String, Edge> edges;

    protected Vertex(String name)
    {
      this.name = name;
      edges = new Hashtable<String, Edge>();
    }


    public String get_name()
    {
      return name;
    }


    public Hashtable<String, Edge> get_edges()
    {
      return edges;
    }

    public boolean equals(Object obj)
    {
      if ( this == obj ) return true;
      if ( (obj == null) || (obj.getClass() != this.getClass()) )
           return false;
      // object must be Vertex at this point
      Vertex test = (Vertex) obj;
      return name == test.name;
    }

  };