  public class Edge {
    protected Vertex from;
    protected Vertex to;

    protected Edge(Vertex from, Vertex to)
    {
      this.from = from;
      this.to = to;
    }

    public String get_from()
    {
      return from.name;
    }

    public String get_to()
    {
      return to.name;
    }
  }