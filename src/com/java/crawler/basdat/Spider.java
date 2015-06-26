/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.crawler.basdat;

import java.util.ArrayList;

/**
 *
 * @author satrio
 */
public class Spider {
    private static final int LIMIT = 2;
    private String seed;
    private ArrayList<String> listPageVisited = new ArrayList<String>();
    private ArrayList<String> listPageToVisit = new ArrayList<String>();
    private SpiderLeg leg;

    private void initSeeding(String seed) {
        // TODO menginisialisasi URL pertama yang akan di-crawl
        listPageToVisit.add(seed);
    }

    private void seeding() {
        // TODO menambahkan semua link hasil crawl ke listPageToVisit
        this.listPageToVisit.addAll(leg.getLinks());
    }

    public void startCrawl() {
        initSeeding(seed);

        while(this.listPageVisited.size() < LIMIT){
            String currentUrl;
            leg = new SpiderLeg();

            currentUrl = this.getNextUrl();
            System.out.println("current url = " + currentUrl);

            leg.crawl(currentUrl); // proses crawling
            this.listPageVisited.add(currentUrl); // setelah crawling add ke listPageVisited
            seeding(); // tambah semua link hasil crawling ke listPageToVisit

            for(String s : this.listPageVisited){
                    System.err.println(s + " yeah !");
            }
        }
        // proses crawling sudah selesai
        System.out.println("\n**Done** Visited " + this.listPageVisited.size() + " web page(s)");
    }

    private String getNextUrl() {
        String nextUrl;
        do {
            nextUrl = this.listPageToVisit.remove(0);
        } while(this.listPageVisited.contains(nextUrl)); // melakukan dequeue sampai link yg belum dikunjungi didapat

        return nextUrl; // return link yang belum dikunjungi untuk dicrawl
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }
}
