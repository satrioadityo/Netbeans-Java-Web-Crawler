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
 * class ini berguna untuk pemrosesan crawling web page
 */
public class SpiderLeg {
    // menggunakan user_agent tipuan, agar browser mengenali robot sebagai browser beneran haha
    private static final String USER_AGENT =
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    private ArrayList<String> links = new ArrayList<String>();      // arraylist untuk menampung link yang didapat hasil crawl
    private Document htmlDocument;                                  // document ini gunanya untuk mentransform web page ke document agar bisa diextract

    /**
     * Contributor : 
     *  - satrio adityo (satrioadityo@gmail.com)
     * method yang digunakan untuk mengcrawl web page, mendapatkan semua link, download file dan image
     * IS : link, file, dan image belum didapatkan
     * FS : setelah proses crawl dari suatu url, didapatkan semua link yg ada di page tersebut beserta 
     *      file dan imagenya jika file tersebut downloadable
     * @param url , url yang akan dicrawl
     */
    public void crawl(String url) {
        try {
            // membuat koneksi ke url
            Connection connection = Jsoup.connect(url);
            
            // mentransform page menjadi document untuk diextract nantinya
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            
            // 200 itu tanda kalo semua koneksi OK
            if(connection.response().statusCode() == 200) { 
                System.out.println("\n**Visiting** Received web page at " + url);
                
                // output to console info dari page/document dalam bentuk html
                System.out.println(htmlDocument.html());
                
                // save html ke txt
                // code here !!!!!!!!!!!!!!!!!!!!!!
            }

            // jika page yg dibuka bukan html
            if(!connection.response().contentType().contains("text/html")) {
                // show failure message, not crawl
                System.out.println("**Failure** Retrieved something other than HTML");
            }

            // get all link
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");

            // untuk setiap link akan ditampung di arraylist links
            for(Element link : linksOnPage) {
                //System.out.println(link.absUrl("href"));
                this.links.add(link.absUrl("href"));
                
                // jika linknya downloadable maka download !
                if(link.absUrl("href").lastIndexOf("/")!=link.absUrl("href").length()){
                    // proses download file, disimpan ke folder device
                    getFile(link.absUrl("href"));
                }
            }

            // get image dari document
            Elements img = htmlDocument.getElementsByTag("img");
            
            // untuk setiap image
            for (Element el : img) {
                // untuk setiap element dapatkan link imagenya
                String src = el.absUrl("src");
                System.out.println("Image Found!");
                System.out.println("src attribute is : "+src);
                //proses simpan image ke folder device
                getImages(src);
            }
        }
        catch(IOException ioe) {
                // Tidak berhasil request HTTP
        }
    }


    /**
     * Contributor : 
     *  - satrio adityo (satrioadityo@gmail.com)
     * method yang digunakan untuk menyimpan file ke device
     * IS : terdapat link file yg downloadable
     * FS : link tersebut didownload, filenya disimpan ke folder
     * @param absUrl , absurl link file yg downloadable
     */
    private void getFile(String absUrl) {
        // cari '/' dari link
        int indexname = absUrl.lastIndexOf("/");
        
        // jika '/' ada di paling akhir, hilangkan '/' yg paling akhir, biar bisa ambil nama filenya
        if (indexname == absUrl.length()) {
            absUrl = absUrl.substring(1, indexname); // '/' paling akhir sudah hilang
        }
        
        // cari '/' baru yg terakhir
        indexname = absUrl.lastIndexOf("/");
        
        // ambil kata-kata setelah '/', simpan ke variable nama. Nama disini akan jadi nama file yg disimpan ke folder
        String name = absUrl.substring(indexname+1, absUrl.length());
        System.out.println("the name is " +name);
        
        // proses filter, jika namanya mengandung .pdf .doc .docx .txt akan disimpan
        if( name.contains(".pdf") || name.contains(".doc") 
            || name.contains(".docx") || name.contains(".txt")) {
            URL url;
            try {
                // membuka link file yg downloadable
                url = new URL(absUrl);
                System.out.println("url file = "+url);
                
                // menggunakan java i/o untuk penyimpanan file ke folder
                InputStream in = url.openStream();
                // "/home/satrio/FileCrawl/" adalah letak foldernya
                OutputStream out = new BufferedOutputStream(
                        new FileOutputStream( "/home/satrio/FileCrawl/"+ name));
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

    /**
     * Contributor : 
     *  - satrio adityo (satrioadityo@gmail.com)
     * method yang digunakan untuk menyimpan image ke device
     * IS : terdapat link file yg downloadable
     * FS : link tersebut didownload, filenya disimpan ke folder
     * 
     * proses yg ada di method ini hampir sama dengan method getFile !!!
     * 
     * @param absUrl , absurl link file yg downloadable
     */
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

    /**
     * Contributor : 
     *  - satrio adityo (satrioadityo@gmail.com)
     * method yang digunakan untuk mengembalikan links
     * IS : terdapat link file yg downloadable
     * FS : link tersebut didownload, filenya disimpan ke folder
     * @return links, kumpulan link hasil crawl
     */
    public ArrayList<String> getLinks() {
        return this.links;
    }
}
