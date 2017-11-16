import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

	/*public static void main(String[] args) throws FileNotFoundException {
		WikiCrawler w = new WikiCrawler("/wiki/Computer_Science", 200, null, "WikiCS.txt");
		long startTime = System.nanoTime();
		w.crawl();
		long endTime = System.nanoTime();
		System.out.println("done in " + (endTime - startTime)/1000000000 + "seconds");
		
		System.out.println("GRAPH PROCESSING:");
		GraphProcessor gp = new GraphProcessor("C:\\Users\\kuntz\\Development\\coms311-project-two\\WikiCS.txt");
		
		int max_c = 0;
		int max_out_degree = 0;
		Vertex v = null;
		Vertex u = null;
		ArrayList<Vertex> vertices = gp.get_vertices();
		
		for(int i = 0; i < gp.num_vertices; i++) {
			int x = gp.centrality(vertices.get(i).get_name());
			int y = gp.outDegree(vertices.get(i).get_name());
			if(x > max_c) {
				v = vertices.get(i);
				max_c = x;
			}
			if(y > max_out_degree) {
				u = vertices.get(i);
				max_out_degree = y;
			}
		}
		
		System.out.println("Total Nodes: " + gp.num_vertices);
		System.out.println("Vertex with Highest Centrality: " + v.get_name() + ", " + max_c);
		System.out.println("Vertex with Highest Out Degree: " + v.get_name() + ", " + max_out_degree);
		

	}*/

}
