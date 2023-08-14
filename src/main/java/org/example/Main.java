package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static String xpathSEP = "//*[@id=\"bibliography\"]/ul/li";


    public static void main(String[] args) throws IOException {

        String urlSEP = "https://plato.stanford.edu/entries/introspection/";
        String urlDDJ = "https://terebess.hu/english/tao/addiss.html";

        Document finalDocument = Main.getDocumentFromHTML(urlSEP);
        Elements elements = Main.getElements(finalDocument, xpathSEP);
        Main.writeFile("texts.txt", elements);




    }

public static Document getDocumentFromHTML (String url) throws IOException {


//    URL obj = new URL(url);
//    //Setting and Testing Connection
//    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//    // optional request header
//    //con.setRequestProperty("User-Agent", "Mozilla/5.0");
//    int responseCode = con.getResponseCode();
//    System.out.println("Response code: " + responseCode);
//
//
//    //Optional HTML to String
//    String inputLine;
//    StringBuilder response = new StringBuilder();
//    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//    while ((inputLine = in.readLine()) != null) {
//        response.append(inputLine);
//    }
//    in.close();
//    String html = response.toString();

    Document doc = Jsoup.connect(url).get();
    System.out.println(doc);
    return doc;
}




public static Elements getElements (Document doc, String searchAttribute) {
    //Elements texts = doc.getElementsByTag(searchAttribute);

    Elements texts = doc.selectXpath(searchAttribute);
    return texts;
}




public static void writeFile (String fileName, Elements elements) throws IOException {

    List<String> results = new ArrayList<>();
    FileWriter myWriter = new FileWriter(fileName);

    for (Element e : elements){

        String text = e.text();
        results.add(text);


    }
        myWriter.write(String.valueOf(results));
        myWriter.close();



//CSV Output
//                  Writer writer = Files.newBufferedWriter(Paths.get("authors.csv"));
//                  CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
//                  printer.printRecord(authors);
//                  printer.println();

    }

}