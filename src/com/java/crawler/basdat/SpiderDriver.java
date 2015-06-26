package com.java.crawler.basdat;

/**
 *
 * @author satrio
 * main class untuk menjalankan crawler
 */
public class SpiderDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // buat objek spider
        Spider spider = new Spider();
        
        // pasang seed
        String seed = "http://satrioadityo.tk/blog/test-upload-materi/";
        spider.setSeed(seed);
        
        //mulai crawling
        spider.startCrawl();
    }
    
}
