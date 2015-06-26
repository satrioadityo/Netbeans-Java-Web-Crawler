/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.crawler.basdat;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author satrio
 */
public class SpiderLeg {
    // menggunakan user_agent tipuan agar browser mengenali robot sebagai browser beneran haha
	private static final String USER_AGENT =
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	
	private ArrayList<String> links = new ArrayList<String>();
	private Document htmlDocument;

	public void crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url);
			Document htmlDocument = connection.get();
			this.htmlDocument = htmlDocument;
			if(connection.response().statusCode() == 200) { // 200 itu tanda kalo semua OK
				System.out.println("\n**Visiting** Received web page at " + url);
				//				System.out.println(htmlDocument.html());
			}

			if(!connection.response().contentType().contains("text/html")) {
				System.out.println("**Failure** Retrieved something other than HTML");
			}

			// get all link
			Elements linksOnPage = htmlDocument.select("a[href]");
			System.out.println("Found (" + linksOnPage.size() + ") links");

			for(Element link : linksOnPage) {
				//System.out.println(link.absUrl("href"));
				this.links.add(link.absUrl("href"));
				if(link.absUrl("href").lastIndexOf("/")!=link.absUrl("href").length()){
					getFile(link.absUrl("href"));
				}
			}

			//Connect to the website and get the html
	        //Get all elements with img tag ,
	        Elements img = htmlDocument.getElementsByTag("img");
	        for (Element el : img) {
	            //for each element get the src url
	            String src = el.absUrl("src");
	            System.out.println("Image Found!");
	            System.out.println("src attribute is : "+src);
	            getImages(src);
	        }
		}
		catch(IOException ioe) {
			// Tidak berhasil request HTTP
		}
	}

	
	private void getFile(String absUrl) {
		// TODO Auto-generated method stub
		int indexname = absUrl.lastIndexOf("/");
		if (indexname == absUrl.length()) {
			absUrl = absUrl.substring(1, indexname);
		}
		indexname = absUrl.lastIndexOf("/");
		String name = absUrl.substring(indexname+1, absUrl.length());
		System.out.println("the name is " +name);
		if(name.contains(".pdf")){
			URL url;
			try {
				url = new URL(absUrl);
				System.out.println("url pdf = "+url);
				InputStream in = url.openStream();
				OutputStream out = new BufferedOutputStream(new FileOutputStream( "/home/satrio/FileCrawl/"+ name));
				for (int b; (b = in.read()) != -1;) {
					out.write(b);
				}
				out.close();
				in.close();
				System.out.println("success save pdf to device!");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public void getImages(String src) throws IOException {
		//Exctract the name of the image from the src attribute
		int indexname = src.lastIndexOf("/");
		if (indexname == src.length()) {
			src = src.substring(1, indexname);
		}
		indexname = src.lastIndexOf("/");
		String name = src.substring(indexname, src.length());
		System.out.println("the name is " +name);
		//Open a URL Stream
		URL url = new URL(src);
		InputStream in = url.openStream();
		OutputStream out = new BufferedOutputStream(new FileOutputStream( "/home/satrio/ImageCrawl"+ name));
		for (int b; (b = in.read()) != -1;) {
			out.write(b);
		}
		out.close();
		in.close();
		System.out.println("success save image to device!");
	}

	public ArrayList<String> getLinks() {
		return this.links;
	}
}
