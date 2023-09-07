package org.example.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.example.model.SepPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcSepPageDAO implements SepPageDAO {

    private static JdbcTemplate jdbcTemplate;


    public JdbcSepPageDAO (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }






//FUTURE METHODS

    @Override
    public List<String> returnBibliography(String sepPageName) throws IOException {
        return null;
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
    public int checkAuthorCount(String authorName, String sepPage) throws IOException {
        int result = 0;
        String xpathSEP = "//*[@id=\"bibliography\"]/ul/li";
        String urlSEP = "https://plato.stanford.edu/entries/" + sepPage + "/";
        //String urlDDJ = "https://terebess.hu/english/tao/addiss.html";

        Document finalDocument = JdbcSepPageDAO.getDocumentFromURL(urlSEP);
        Elements elements = JdbcSepPageDAO.getElements(finalDocument, xpathSEP);

        for (Element e : elements) {

            String text = e.text();

            if (text.contains(authorName)) {
                result++;
            } else if (!text.contains(authorName)) {
                System.out.println("Error!");
            }
        }
        return result;
    }

//SQL CONTENT
    //        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(
    ////                "SELECT ct.chapter, ct.chapter_text, t.author, t.text_id, t.publish_year  " +
    ////                "FROM chapter_text AS ct " +
    ////                "JOIN translation AS t ON ct.text_id = t.text_id " +
    ////                "WHERE ct.chapter = ?;"
    //                   "SELECT pt.page_id, pt.page_text" +
    //                   "FROM page_text AS pt" +
    //                   "WHERE pt.page_name = ?;"
    //                , sepPageName);
    //        while (rowSet.next()) {
    //            SepPage sep = new SepPage();
    //            sep.setSepPageID(rowSet.getInt("page_id"));
    //            //sep.setSepPageName(rowSet.getInt("page_"));
    //            sep.setSepPageBibliography(rowSet.getString("page_text"));
    //            //sep.setSepPageAuthor(rowSet.getInt("text_id"));
    //            //sep.setSepPageYear(rowSet.getInt("publish_year"));
    //            result.add(sep);
    //        }







    //RETURN SINGLE ITEM METHODS

    @Override
    public String returnSingleTitle(Document doc) throws IOException {

        Elements titlesElements = doc.getElementsByTag("h1");

        return titlesElements.toString();

    }


    @Override
    public List<String> returnBibliography(Document doc) throws IOException {
        List<String> result = new ArrayList<>();
        String xpathSEP = "//*[@id=\"bibliography\"]/ul/li";
        //String urlDDJ = "https://terebess.hu/english/tao/addiss.html";

        Elements elements = JdbcSepPageDAO.getElements(doc, xpathSEP);

        for (Element e : elements) {

            String text = e.text();

            result.add(text);


        }


//        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(
////                "SELECT ct.chapter, ct.chapter_text, t.author, t.text_id, t.publish_year  " +
////                "FROM chapter_text AS ct " +
////                "JOIN translation AS t ON ct.text_id = t.text_id " +
////                "WHERE ct.chapter = ?;"
//                   "SELECT pt.page_id, pt.page_text" +
//                   "FROM page_text AS pt" +
//                   "WHERE pt.page_name = ?;"
//                , sepPageName);
//        while (rowSet.next()) {
//            SepPage sep = new SepPage();
//            sep.setSepPageID(rowSet.getInt("page_id"));
//            //sep.setSepPageName(rowSet.getInt("page_"));
//            sep.setSepPageBibliography(rowSet.getString("page_text"));
//            //sep.setSepPageAuthor(rowSet.getInt("text_id"));
//            //sep.setSepPageYear(rowSet.getInt("publish_year"));
//            result.add(sep);
//        }

        return result;


    }

    //RETURN ALL METHODS
    @Override
    public List<String> returnAllTitles() throws IOException {

        List<String> titles = new ArrayList<>();

        List<String> allURLS = returnAllURLS();

        for (int i = 0; i < allURLS.size() - 1; i++) {

            Document doc = Jsoup.connect(allURLS.get(i)).get();
            Elements titlesElements = doc.getElementsByTag("h1");
            for (Element e : titlesElements) {
                titles.add(e.text());
            }
        }
        return titles;
    }



    @Override
    public List<String> returnAllURLS() throws IOException {

        String urlSEP = "https://plato.stanford.edu/contents.html";
        Document finalDocument = JdbcSepPageDAO.getDocumentFromURL(urlSEP);
        List<String> pageUrlList = new ArrayList<>();


        Elements hrefUrlElements = finalDocument.select("a[href^=\"entries\"]");

        for (Element e : hrefUrlElements) {

            pageUrlList.add(e.attr("abs:href"));

        }
        return pageUrlList;
    }




    //RETURN INDIVIDUAL PAGES

    @Override
    public SepPage returnFullSEPPage(int sepPageIndex) throws IOException {

        List<String> urls = returnAllURLS();

        SepPage results = new SepPage();
        String url = urls.get(sepPageIndex);
        Document sepPageDocument = getDocumentFromURL(url);

        results.setSepPageID(sepPageIndex);
        results.setSepPageName(returnSingleTitle(sepPageDocument));
        results.setSepPageBibliography(returnBibliography(sepPageDocument));

        System.out.println(results.toString());
        return results;

    }



    //SET METHODS

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
//        int responseCode = con.getResponseCode();
//        System.out.println("Response code: " + responseCode);


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
