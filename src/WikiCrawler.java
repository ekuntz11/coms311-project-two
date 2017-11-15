// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @author Eva Kuntz & Merritt Harvey
 *
 */
public class WikiCrawler
{
	/**
	 * Base URL
	 */
	static final String BASE_URL = "https://en.wikipedia.org";
	
	/**
	 * Seed URL
	 */
	String seed;
	
	/**
	 * Maximum number of pages to crawl
	 */
	int max;
	
	/**
	 * Request number
	 */
	int req_num;
	
	/**
	 * List of topics to search for
	 */
	ArrayList<String> topics;
	
	/**
	 * The file name
	 */
	String fileName;
	
	/**
	 * PrintWriter instance
	 */
	PrintWriter writer;
	
	/**
	 * Queue for this instance
	 */
	Queue<String> q;
	
	/**
	 * Set to hold visited pages
	 */
	HashSet<String> visited;
	/**
	 * graph to hold the page and links
	 */
	Graph web_graph;
	

	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName)
	{
		//set all instance variables
		this.seed = seedUrl;
		this.max = max;
		this.topics = topics;
		this.fileName = fileName;
		q = new LinkedList<String>();
		q.add(seedUrl); //add seedURL to queue
		visited = new HashSet<String>();
		visited.add(seedUrl); //add seedURL to visited set
		web_graph = new Graph();		
		
	}

	/**
	 * Method that extracts the links from a given
	 * source HTML code (not a URL)
	 * @param doc
	 * 	HTML source code to search
	 * @return
	 * 	List of links
	 */
	public ArrayList<String> extractLinks(String doc)
	{
		ArrayList<String> toreturn = new ArrayList<String>();
		doc = doc.substring(doc.indexOf("<p>")); //get substring starting at first index of '<p>' character
		int cur =0;
		while((cur = doc.indexOf("href=\"",cur)) != -1){
			int idx = doc.indexOf("\"", cur+6);
			String toadd = doc.substring(cur+6, idx);
			if(!toadd.contains(":") && !toadd.contains("#") && toadd.substring(0, 6).compareTo("/wiki/") ==0){
				toreturn.add(toadd);
			}
		    cur = idx;
		}
		return toreturn;
	}

	
	/**
	 * WikiCrawl method.
	 */
	public void crawl()
	{
		try{			
			String s;
			String page = "";
			String text_component;
			ArrayList<String> links;
			String cur_page;
			while(!q.isEmpty()){
				page="";
				boolean valid = true;
				cur_page = q.remove();
				if(req_num%50 ==0){
					Thread.sleep(3000);
				}
				//read html from page into stream
				URL url = new URL(BASE_URL+cur_page);
				InputStream is = url.openStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				req_num++;
				//read from stream until it is complete
				while ((s=br.readLine())!=null)
			    {
						page += s + '\n';
			    }
				text_component = page.substring(page.indexOf("<p>"));
				//if there are no topics, extract all links
				if(topics == null || topics.size() == 0){
					//continue with BFS as normal
					links = extractLinks(page);
					valid = true;
				} else {
					//check to make sure page has all topics before extracting links
					valid = true;
					for(int i=0; i<topics.size(); i++){
						if(!text_component.contains(topics.get(i))){
							valid = false;
							break;
						}
					}
				}
				if(valid){
					//it has all the topics, continue with BFS as normal
					links = extractLinks(page);
					for(int i=0;i<links.size();i++){
						//for each link check if it has been visited if it hasnt add it to the queue if it has the correct topics
						if(!visited.contains(links.get(i)) && visited.size() < max){ // this way we stop adding verticies once we reach our max
							if(cur_page.compareTo(links.get(i))!=0){//if it is not a selfloop
								if(topics == null || topics.size() == 0){//if there are no topics, add the vertex to visited, q, and add the edge to the graph
									q.add(links.get(i));
									visited.add(links.get(i));
									web_graph.add_edge(cur_page, links.get(i));
								} else { //we must check the page to make sure it has the topics before adding it to the graph
									if(req_num%50 ==0){
										Thread.sleep(3000);
									}
									url = new URL(BASE_URL+links.get(i));
									is = url.openStream();
									br = new BufferedReader(new InputStreamReader(is));
									req_num++;
									String tmp ="";
									while ((s=br.readLine())!=null)
								    {
											tmp += s + '\n';
								    }
									String tmp_component = tmp.substring(tmp.indexOf("<p>"));
									valid = true;
									for(int j=0; j<topics.size(); j++){
										if(!tmp_component.contains(topics.get(j))){
											valid = false;
											break;
										}
									}
									if(valid){//if it has the topics, add the edge, put the vertex in both the q and visited
										q.add(links.get(i));
										visited.add(links.get(i));
										web_graph.add_edge(cur_page, links.get(i));
									}
								}
							}
						}else if(visited.contains(links.get(i))) {//vertex the edge is going to is already in the graph
							if(cur_page.compareTo(links.get(i))!=0){//if it is not a selfloop
								web_graph.add_edge(cur_page, links.get(i));
							}
						}
					}
				}//if it doesn't have all topics do not add it to the graph
			}
				
			writer = new PrintWriter(fileName, "UTF-8");
			writer.println(visited.size());
			ArrayList<Vertex> itr = web_graph.get_vertices();
			for(int i=0; i<itr.size(); i++){
				Hashtable<String,Edge> edges = itr.get(i).get_edges();
				Set<String> keys = edges.keySet();
				Iterator<String> itr1 = keys.iterator();
				while (itr1.hasNext()) { 
					Edge e = edges.get(itr1.next());
					writer.println(e.from.name + " " + e.to.name);
				}
			}
			
			writer.close();
		} catch(IOException e){
			System.out.println("IO Exception in crawl()");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("sleep exception");
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * Main Method for this class.
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String [] args) throws FileNotFoundException{
		ArrayList<String> topics = new ArrayList<String>();
		topics.add("computer");
		topics.add("science");
		WikiCrawler w = new WikiCrawler("/wiki/Computer_science", 200, null, "notopics.txt");
		long startTime = System.nanoTime();
		w.crawl();
		long endTime = System.nanoTime();
		System.out.println("done in " + (endTime - startTime)/1000000000 + "seconds");
		//Scanner scanner = new Scanner( new File("src/sample.txt") );
		//String text = scanner.useDelimiter("\\A").next();
		//scanner.close();
		//ArrayList<String> result = w.extractLinks(text);
		//for(int i=0;i<result.size();i++){
		//	System.out.println(result.get(i));
		//}
		
	
	}
}



