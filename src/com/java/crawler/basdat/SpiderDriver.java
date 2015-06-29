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
        String seed = "http://www.tribunnews.com/techno/2015/06/25/telkomsel-sukses-ujicoba-volte-dan-wifi-calling";
        spider.setSeed(seed);
        
        //mulai crawling
        //spider.startCrawl();
    }
    
}
