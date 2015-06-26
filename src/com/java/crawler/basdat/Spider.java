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
 * class ini digunakan untuk menjalankan crawler leg
 */
public class Spider {
    private static final int LIMIT = 2;                                     // limiter yg akan menentukan berapa page yg akan dicrawl
    private String seed;                                                    // seed adalah link yg akan dicrawl pertama kali
    private ArrayList<String> listPageVisited = new ArrayList<String>();    // list page yg sudah pernah dicrawl, untuk pencegahan agar tidak terjadi crawl page yg sama berkali-kali
    private ArrayList<String> listPageToVisit = new ArrayList<String>();    // list page yg harus dikunjungi
    private SpiderLeg leg;                                                  // objek SpiderLeg agar Spider bisa melakukan crawl

    /**
     * Contributor : 
     *  - satrio adityo (satrioadityo@gmail.com)
     * method yang digunakan untuk menginisialisasi seed
     * IS : Spider belum memiliki link yg akan dicrawl
     * FS : Spider mempunyai page yg harus dikunjungi, yaitu seed
     * @param seed , URL
     */
    private void initSeeding(String seed) {
        // memasukkan seed ke page yg harus dikunjungi
        listPageToVisit.add(seed);
    }
    
    /**
     * Contributor : 
     *  - satrio adityo (satrioadityo@gmail.com)
     * method yang digunakan untuk menginisialisasi seed
     * IS : Spider memiliki link yg harus dicrawl
     * FS : Spider mempunyai page tambahan yg harus dicrawl
     */
    private void seeding() {
        // menambahkan semua link hasil crawl ke listPageToVisit
        this.listPageToVisit.addAll(leg.getLinks());
    }

    /**
     * Contributor : 
     *  - satrio adityo (satrioadityo@gmail.com)
     * method yang digunakan untuk memulai crawling
     * IS : Spider memiliki seed
     * FS : Spider mempunyai page tambahan yg harus dicrawl
     */
    public void startCrawl() {
        // seeding pertama
        initSeeding(seed);

        // proses crawl, selama belum mencapai LIMIT proses crawling akan terus berjalan
        while(this.listPageVisited.size() < LIMIT){
            String currentUrl;
            
            // instant SpiderLeg
            leg = new SpiderLeg(); 

            currentUrl = this.getNextUrl();
            System.out.println("current url = " + currentUrl);

            // proses crawling, banyak yg terjadi disini
            leg.crawl(currentUrl); 
            
            // setelah crawling add current URL ke listPageVisited
            this.listPageVisited.add(currentUrl); 
            
            // tambah semua link hasil crawling ke listPageToVisit
            seeding(); 

            // nandain doang URL apa aja yg udah dicrawl
            for(String s : this.listPageVisited){
                    System.err.println(s + " sudah dicrawl, yeah !");
            }
        }
        // proses crawling sudah selesai, show message finished
        System.out.println("\n**Done** Visited " + this.listPageVisited.size() + " web page(s)");
    }

    /**
     * Contributor : 
     *  - satrio adityo (satrioadityo@gmail.com)
     * method yang digunakan untuk mendapatkan link baru yg tidak ada di listpagevisited
     * IS : listpagevisited tidak kosong
     * FS : Spider mempunyai currentURL yg baru untuk dicrawl
     * @return URL
     */
    private String getNextUrl() {
        String nextUrl;
        do {
            // melakukan dequeue sampai link yg belum dikunjungi didapat
            nextUrl = this.listPageToVisit.remove(0);
        } while(this.listPageVisited.contains(nextUrl)); 

        return nextUrl; // return link yang belum dikunjungi untuk dicrawl
    }

    /**
     * Contributor : 
     *  - satrio adityo (satrioadityo@gmail.com)
     * method yang digunakan untuk menset seed
     * @param seed , URL
     */
    public void setSeed(String seed) {
        this.seed = seed;
    }
}
