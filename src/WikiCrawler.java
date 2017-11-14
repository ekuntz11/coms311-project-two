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
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;



public class WikiCrawler
{
	static final String BASE_URL = "https://en.wikipedia.org";
	// other member fields and methods
	String seed;
	int max, req_num;
	ArrayList<String> topics;
	String fileName;
	PrintWriter writer;
	Queue<String> q;
	HashSet<String> visited;

	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName)
	{
		// implementation
		seed = seedUrl;
		max = this.max;
		this.topics = topics;
		this.fileName = fileName;
		q = new LinkedList<String>();
		q.add(seedUrl);
		visited = new HashSet<String>();
		visited.add(seedUrl);
		try{
			new PrintWriter(fileName, "UTF-8");
		}catch(UnsupportedEncodingException e){
			System.out.println("encoding exception");
		}catch(FileNotFoundException e){
			System.out.println("FNF exception");
		}
		
		
	}

	// NOTE: extractLinks takes the source HTML code, NOT a URL
	public ArrayList<String> extractLinks(String doc)
	{
		// TODO implementation
		ArrayList<String> toreturn = new ArrayList<String>();
		doc = doc.substring(doc.indexOf("<p>"));
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

	public void crawl()
	{
		// TODO implementation
		try{
			
			String s;
			String page = "";
			String text_component;
			ArrayList<String> links;
			String cur_page;
			while(!q.isEmpty()){
				cur_page = q.remove();
				if(req_num%50 ==0){
					Thread.sleep(3000);
				}
				//read html from page into stream
				URL url = new URL(BASE_URL+"/wiki/Physics");
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
				} else {
					//check to make sure page has all topics before extracting links
					boolean valid = true;
					for(int i=0; i<topics.size(); i++){
						if(!text_component.contains(topics.get(i))){
							valid = false;
							break;
						}
					}
					if(valid){
						//it has all the topics, continue with BFS as normal
						links = extractLinks(page);
						for(int i=0;i<links.size();i++){
							//for each link check if it has been visited if it hasnt add it to the queue
							if(!visited.contains(links.get(i)) && visited.size() <= max){ // this way we stop adding verticies once we reach our max
								q.add(links.get(i));
								visited.add(links.get(i));
							}
							//TODO add the edge to the graph
						}
					} else {
						//if it doesn't have all topics do not add it to the graph
					}
				}
			}
			
			
			
			
			
		} catch(IOException e){
			System.out.println("IO Exception in crawl()");
		} catch (InterruptedException e) {
			System.out.println("sleep exception");
		}
		writer.close();
	}
	
	
	public static void main(String [] args) throws FileNotFoundException{
		WikiCrawler w = new WikiCrawler("/wiki/Physics", 200, null, "test.txt");
		//w.crawl();
		Scanner scanner = new Scanner( new File("src/sample.txt") );
		String text = scanner.useDelimiter("\\A").next();
		scanner.close();
		ArrayList<String> result = w.extractLinks(text);
		for(int i=0;i<result.size();i++){
			System.out.println(result.get(i));
		}
		
	
	}
}



