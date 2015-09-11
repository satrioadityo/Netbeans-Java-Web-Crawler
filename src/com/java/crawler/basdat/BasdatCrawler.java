/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.crawler.basdat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author satrio
 */
public class BasdatCrawler extends javax.swing.JFrame {

    private int LIMIT;                            // limiter yg akan menentukan berapa page yg akan dicrawl
    private ArrayList<String> listPageVisited;    // list page yg sudah pernah dicrawl, untuk pencegahan agar tidak terjadi crawl page yg sama berkali-kali
    private ArrayList<String> listPageToVisit;    // list page yg harus dikunjungi
    
    // menggunakan user_agent tipuan, agar browser mengenali robot sebagai browser beneran haha
    private static final String USER_AGENT =
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    private ArrayList<String> links;      // arraylist untuk menampung link yang didapat hasil crawl
    private Document htmlDocument;        // document ini gunanya untuk mentransform web page ke document agar bisa diextract
    int numb = 0;
    
    
    /**
     * Creates new form BasdatCrawler
     */
    public BasdatCrawler() {
        initComponents();
        listPageVisited = new ArrayList<String>();
        listPageToVisit = new ArrayList<String>();
        links = new ArrayList<String>();
    }

    public void openFilePathDialog(JTextField textField){
        chooser.setFileSelectionMode(chooser.FILES_AND_DIRECTORIES);
        int returnval = chooser.showOpenDialog(BasdatCrawler.this);
        if (returnval == chooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            try {
                if (file.isFile()){
                    textField.setText(file.getParent().toString());
                }else if (file.isDirectory()){
                    textField.setText(file.getAbsolutePath().toString());
                }
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(BasdatCrawler.this, "Choose Folder First");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chooser = new javax.swing.JFileChooser();
        mainPane = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtURL = new javax.swing.JTextField();
        btnStartCrawl = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtFilePath = new javax.swing.JTextField();
        btnFilePath = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtLimit = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCrawlingProcess = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPane.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Basdat Java Web Crawler");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtURL.setText("http://");
        txtURL.setToolTipText("include the protocol !");

        btnStartCrawl.setText("Start Crawl");
        btnStartCrawl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartCrawlActionPerformed(evt);
            }
        });

        jLabel2.setText("Web address :");

        jLabel3.setText("Save Crawling Result to :");

        txtFilePath.setEditable(false);

        btnFilePath.setText("File Path");
        btnFilePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilePathActionPerformed(evt);
            }
        });

        jLabel7.setText("Limit Crawl :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnStartCrawl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLimit)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE))
                            .addComponent(txtURL, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtURL, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnFilePath)
                    .addComponent(txtFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtLimit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(btnStartCrawl)
                .addContainerGap())
        );

        txtCrawlingProcess.setColumns(20);
        txtCrawlingProcess.setRows(5);
        jScrollPane1.setViewportView(txtCrawlingProcess);

        jLabel5.setText("Crawling Process");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPaneLayout = new javax.swing.GroupLayout(mainPane);
        mainPane.setLayout(mainPaneLayout);
        mainPaneLayout.setHorizontalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPaneLayout.setVerticalGroup(
            mainPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFilePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilePathActionPerformed
        // get folder untuk menyimpan hasil crawling
        this.openFilePathDialog(txtFilePath);
    }//GEN-LAST:event_btnFilePathActionPerformed

    private void btnStartCrawlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartCrawlActionPerformed
        
        // ketika button start crawl di klik akan memulai crawling
        System.out.println("starting crawl");
        
        // pastikan listPageToVisit & listPageVisited dimulai dari kosong
        listPageToVisit.clear();
        listPageVisited.clear();
        
        // check user input
        if (!"".equals(txtURL.getText()) &&
                !"".equals(txtFilePath.getText()) &&
                !"".equals(txtLimit.getText())) {
            // user input is valid, get value dari field
            String url = txtURL.getText();
            String filePath = txtFilePath.getText();
            int limit = 10; // set default limit
            try {
                limit = Integer.parseInt(txtLimit.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(BasdatCrawler.this, "Input limitnya angka!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            
            // set root url to crawl // init seeding
            listPageToVisit.add(url);
            // set limit
            LIMIT = limit;
            
            // start crawling
            // proses crawl, selama belum mencapai LIMIT proses crawling akan terus berjalan
            while(this.listPageVisited.size() < LIMIT){
                String currentUrl;

                if(listPageToVisit.size() > 0){
                    currentUrl = this.getNextUrl();
                    System.out.println("current url to crawl = " + currentUrl);

                    // proses crawling, banyak yg terjadi disini
                    this.crawl(currentUrl, filePath); 

                    // setelah crawling add current URL ke listPageVisited
                    this.listPageVisited.add(currentUrl); 

                    // tambah semua link hasil crawling ke listPageToVisit
                    listPageToVisit.addAll(links);

                    // nandain doang URL apa aja yg udah dicrawl
                    for(String s : this.listPageVisited) {
                        System.out.println("\n" + s + " sudah dicrawl, yeah !");
                    }
                }
                else{
                    break;
                }
            }
            // proses crawling sudah selesai, show message finished
            System.out.println("\n**Done** Visited " + this.listPageVisited.size() + " web page(s)");
            
        }
        else{
            JOptionPane.showMessageDialog(BasdatCrawler.this, "Lengkapi inputan", 
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnStartCrawlActionPerformed
    
    private String getNextUrl() {
        String nextUrl;
        do {
            // melakukan dequeue sampai link yg belum dikunjungi didapat atau melakukan dequeue selama nextUrl ada dalam listPageVisited
            nextUrl = this.listPageToVisit.remove(0);
        } while(listPageVisited.contains(nextUrl)); 

        return nextUrl; // return link yang belum dikunjungi untuk dicrawl
    }
    
    public void getImages(String src, String folderImagePath) throws IOException {
        //Exctract the name of the image from the src attribute
        int indexname = src.lastIndexOf("/");
        
        // jika '/' ada pada posisi paling akhir dari suatu url, hilangkan
        if (indexname == src.length() && src.length()<255 && src.length() > 0) {
            src = src.substring(1, indexname);
        }
        
        //cari '/' lagi untuk dapatkan nama gambarnya
        indexname = src.lastIndexOf("/");
        
        String name = src.substring(indexname+1, src.length());
        
        // Open a URL Stream, proses simpan gambar ke disk
        URL url = new URL(src);
        InputStream in = url.openStream();
        OutputStream out = new BufferedOutputStream(new FileOutputStream(folderImagePath+"/"+ name));
        for (int b; (b = in.read()) != -1;) {
                out.write(b);
        }
        out.close();
        in.close();
    }
    
    private void getFile(String absUrl, String folderFilePath) {
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
        //System.out.println("the name is " +name);
        
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
                
                // proses penyimpanan file ke disk
                OutputStream out = new BufferedOutputStream(
                        new FileOutputStream(folderFilePath + "/" + name));
                for (int b; (b = in.read()) != -1;) {
                        out.write(b);
                }
                out.close();
                in.close();
                System.out.println("success save file to device!");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    
    public void crawl(String url, String folderFilePath) {
        try {
            // membuat koneksi ke url
            Connection connection = Jsoup.connect(url);
            
            // mentransform page menjadi document untuk diextract nantinya
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            
            File NewFolder = null;
            
            // 200 itu tanda kalo semua koneksi OK
            if(connection.response().statusCode() == 200) { 
                System.out.println("**Visiting** Received web page at " + url);
                // save html ke txt
                NewFolder = new File(""+folderFilePath+"/"+numb);
                NewFolder.mkdir();
                PrintWriter pw = new PrintWriter(NewFolder.getAbsolutePath()+"/html"+numb+".txt");
                pw.println(htmlDocument.html());
                pw.close();
            }
            else{
                System.err.println("terjadi error !");
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

                // link yg didapat dari suatu page, difilter terlebih dahulu agar dapat page berita saja
                if(link.absUrl("href").contains("merdeka.com") && (!link.absUrl("href").contains("#"))
                        && (!link.absUrl("href").contains("?")) && (link.absUrl("href").contains(".html"))){
                    this.links.add(link.absUrl("href"));
                }
                

                // jika linknya downloadable maka download !
                if(link.absUrl("href").lastIndexOf("/")!=link.absUrl("href").length()){
                    // proses download file, disimpan ke folder device
                    getFile(link.absUrl("href"), NewFolder.getAbsolutePath());
                }
            }

            // get image dari document
            Elements img = htmlDocument.getElementsByTag("img");

            // untuk setiap image
            for (Element el : img) {
                // untuk setiap element dapatkan link imagenya
                String src = el.absUrl("src");
//                System.out.println("Image Found!");
//                System.out.println("src attribute is : "+src);
                //proses simpan image ke folder device
                if(src.length()<255)
                    getImages(src,NewFolder.getAbsolutePath());
            }
            
            // jika proses 1 url sudah dilakukan, tambahkan numb
            numb++;

        }
        catch(IOException ioe) {
                // Tidak berhasil request HTTP
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BasdatCrawler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BasdatCrawler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BasdatCrawler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BasdatCrawler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BasdatCrawler().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilePath;
    private javax.swing.JButton btnStartCrawl;
    private javax.swing.JFileChooser chooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPane;
    private javax.swing.JTextArea txtCrawlingProcess;
    private javax.swing.JTextField txtFilePath;
    private javax.swing.JTextField txtLimit;
    private javax.swing.JTextField txtURL;
    // End of variables declaration//GEN-END:variables
}
