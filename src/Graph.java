import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Graph
{
  private Hashtable<String, Vertex> vertices;
  private int array_size = 128;

  public Graph()
  {
    vertices = new Hashtable<String, Vertex>(array_size);
  }

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

  public String toString()
  {
    String s;
    int i;

    s = "";
    for (i = 0; i < vertices.size(); i++) {
      if (vertices.get(i) == null) {
        continue;
      }
      s += i + ": " + vertices.get(i).toString() + "\n";
    }

    return s;
  }
}
