package com.java.crawler.basdat;

import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author satrio
 * class ini digunakan untuk menjalankan crawler leg
 */
public class Spider {
    private static int LIMIT;                                     // limiter yg akan menentukan berapa page yg akan dicrawl
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
    public void startCrawl(JTextArea crawlingProcess, JTextArea contentProcess,
            String folderFilePath, String folderImagePath) {
        // seeding pertama
        initSeeding(seed);

        // proses crawl, selama belum mencapai LIMIT proses crawling akan terus berjalan
        while(this.listPageVisited.size() < LIMIT){
            String currentUrl;
            
            // instant SpiderLeg
            leg = new SpiderLeg(); 

            if(listPageToVisit.size() > 0){
                currentUrl = this.getNextUrl();
                String dataCrawling = crawlingProcess.getText() + "\n";
                crawlingProcess.setText(dataCrawling + "current url = " + currentUrl);
                //System.out.println("current url = " + currentUrl);

                // proses crawling, banyak yg terjadi disini
                leg.setNumb(this.listPageVisited.size());
                leg.crawl(currentUrl, crawlingProcess, contentProcess, folderFilePath, folderImagePath); 

                // setelah crawling add current URL ke listPageVisited
                this.listPageVisited.add(currentUrl); 

                // tambah semua link hasil crawling ke listPageToVisit
                seeding(); 

                // nandain doang URL apa aja yg udah dicrawl
                dataCrawling = crawlingProcess.getText() + "\n";
                crawlingProcess.setText(dataCrawling);
                for(String s : this.listPageVisited) {
                    crawlingProcess.setText(crawlingProcess.getText() + "\n" + s + " sudah dicrawl, yeah !");
                    //System.err.println(crawlingProcess.getText() + "\n" + s + " sudah dicrawl, yeah !");
                }
            }
            else{
                break;
            }
        }
        // proses crawling sudah selesai, show message finished
        crawlingProcess.setText(crawlingProcess.getText() + "\n**Done** Visited " + this.listPageVisited.size() + " web page(s)");
        //System.out.println("\n**Done** Visited " + this.listPageVisited.size() + " web page(s)");
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

    /**
     * Contributor : 
     *  - satrio adityo (satrioadityo@gmail.com)
     * method yang digunakan untuk get seed
     * @return 
     */
    public String getSeed() {
        return seed;
    }

    public static void setLIMIT(int LIMIT) {
        Spider.LIMIT = LIMIT;
    }

    public static int getLIMIT() {
        return LIMIT;
    }
}
