package org.example.dao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcReservePageDAO implements ReservePageDAO {

    private static JdbcTemplate jdbcTemplate;


    public JdbcReservePageDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<String> returnPricing() throws IOException {



        String urlReservePage = "https://www.decron.com/apartments-reserve-at-chino-hills/";
        String xpathSEP = "//a[starts-with(@href, 'entries')]";

        Document finalDocument = JdbcReservePageDAO.getDocumentFromURL(urlReservePage);

        List<String> pagePriceList = new ArrayList<>();
        Elements hrefUrlElements = finalDocument.select("span[strong*=\"studio\"]");

        for (Element e : hrefUrlElements) {

            String text = e.text();

            pagePriceList.add(text);
        }


        System.out.println(pagePriceList);
        return pagePriceList;

        //return pageTitleList;

    }

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
}
