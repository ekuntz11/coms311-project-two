// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;



public class WikiCrawler
{
	static final String BASE_URL = "https://en.wikipedia.org";
	// other member fields and methods
	String seed;
	int max, req_num;
	ArrayList<String> topics;
	String fileName;

	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName)
	{
		// implementation
		seed = seedUrl;
		max = this.max;
		topics = this.topics;
		fileName = this.fileName;
	}

	// NOTE: extractLinks takes the source HTML code, NOT a URL
	public ArrayList<String> extractLinks(String doc)
	{
		// TODO implementation
		ArrayList<String> toreturn = new ArrayList<String>();
		int cur =0;
		while((cur = doc.indexOf("<a href=\"",cur)) != -1){
			int idx = doc.indexOf("\"", cur+9);
			String toadd = doc.substring(cur+9, idx);
			if(!toadd.contains(";") && !toadd.contains("#") && toadd.substring(0, 6).compareTo("/wiki/") ==0){
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
			ArrayList<String> links;
			boolean started = false;
			URL url = new URL(BASE_URL+"/wiki/Physics");
			InputStream is = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			req_num++;
			while ((s=br.readLine())!=null)
		    {
				if(!started && s.contains("<p>")){
					started = true;
				}
				if(started){
					page += s + '\n';
				}
		    }
			if(topics == null || topics.size() == 0){
				//continue with BFS as normal
				links = extractLinks(page);
			} else {
				//return empty graph if seed doesn't contain all topics
				boolean valid = true;
				for(int i=0; i<topics.size(); i++){
					if(!page.contains(topics.get(i))){
						valid = false;
						break;
					}
				}
				if(valid){
					//it has all the topics, continue with BFS as normal
					links = extractLinks(page);
				} else {
					//return empty webgraph;
				}
			}
			
		} catch(IOException e){
			System.out.println("IO Exception in crawl()");
		}
		
	}
	
	public static void main(String [] args){
		WikiCrawler w = new WikiCrawler("/wiki/Physics", 200, null, "test.txt");
		w.crawl();
	
	}
}



