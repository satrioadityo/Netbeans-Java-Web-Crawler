/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.crawler.basdat;

/**
 *
 * @author satrio
 */
public class SpiderDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Spider spider = new Spider();
        String seed = "http://satrioadityo.tk/blog/test-upload-materi/";
        spider.setSeed(seed);
        spider.startCrawl();
    }
    
}
