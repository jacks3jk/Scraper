package org.example.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.example.ScraperApplication;
import org.example.model.SepPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JdbcSepPageDAO implements SepPageDAO {

    private static JdbcTemplate jdbcTemplate;


    public JdbcSepPageDAO (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<SepPage> returnBibliography(String sepPageName) {
        List<SepPage> result = new ArrayList<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(
//                "SELECT ct.chapter, ct.chapter_text, t.author, t.text_id, t.publish_year  " +
//                "FROM chapter_text AS ct " +
//                "JOIN translation AS t ON ct.text_id = t.text_id " +
//                "WHERE ct.chapter = ?;"
                   "SELECT pt.page_id, pt.page_text" +
                   "FROM page_text AS pt" +
                   "WHERE pt.page_name = ?;"
                , sepPageName);
        while (rowSet.next()) {
            SepPage sep = new SepPage();
            sep.setSepPageID(rowSet.getInt("page_id"));
            //sep.setSepPageName(rowSet.getInt("page_"));
            sep.setSepPageBibliography(rowSet.getString("page_text"));
            //sep.setSepPageAuthor(rowSet.getInt("text_id"));
            //sep.setSepPageYear(rowSet.getInt("publish_year"));
            result.add(sep);
        }
        return result;


    }

    @Override
    public List<SepPage> returnInfo(String sepPageName) {
        return null;
    }

    @Override
    public List<SepPage> returnAllPagesByAuthor(String sepPageAuthor) {
        return null;
    }

    @Override
    public List<SepPage> returnAllPagesMentions(String sepPageBibliography) {
        return null;
    }

    @Override
    public void setBibliography(String sepPageName, Elements ele) throws IOException {
        String xpathSEP = "//*[@id=\"bibliography\"]/ul/li";
        String urlSEP = "https://plato.stanford.edu/entries/" + sepPageName + "/";
        //String urlDDJ = "https://terebess.hu/english/tao/addiss.html";

        Document finalDocument = JdbcSepPageDAO.getDocumentFromURL(urlSEP);
        Elements elements = JdbcSepPageDAO.getElements(finalDocument, xpathSEP);
        insertBibliography(elements);

        //Main.writeFile("texts.txt", elements);

        //JdbcSepPageDAO.writeCSV("texts.csv", elements);


    }


    //
    //Main Business Logic

        public static Document getDocumentFromURL(String url) throws IOException {

        //Testing Connection
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        int responseCode = con.getResponseCode();
        System.out.println("Response code: " + responseCode);


        //Retrieving page
        Document doc = Jsoup.connect(url).get();
        //System.out.println(doc);
        return doc;
    }


    public static Elements getElements(Document doc, String searchAttribute) {


        //Elements texts = doc.getElementsByTag(searchAttribute);


        Elements texts = doc.selectXpath(searchAttribute);


        //Elements texts = doc.select(searchAttribute);

        return texts;
    }


    public static void writeFile(String fileName, Elements elements) throws IOException {

        List<String> results = new ArrayList<>();
        FileWriter myWriter = new FileWriter(fileName);

        for (Element e : elements) {

            String text = e.text();
            results.add(text);


        }
        myWriter.write(String.valueOf(results));
        myWriter.close();
    }


    public static void writeCSV(String fileName, Elements elements) throws IOException {
        Writer writer = Files.newBufferedWriter(Paths.get(fileName), StandardCharsets.UTF_8);

        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.EXCEL);
        List<String> results = new ArrayList<>();

        for (Element e : elements) {

            String text = e.toString().replace("<li>", "").replace("</li>", "")
                    .replace("<em>", "").replace("</em>", "");
            results.add(text);

            printer.printRecord(text);
            //printer.println();
            System.out.println(text);

        }
        printer.flush();
        printer.close();
    }

    public static void insertBibliography (Elements ele) {

        List<String> results = new ArrayList<>();

        for (Element e : ele) {

            String text = e.text();
            results.add(text);
        }

        String sql = "INSERT INTO page_text (page_id, page_name, page_text) " +
                "VALUES (1, ?);";
        jdbcTemplate.update(sql, results);


    }




}
