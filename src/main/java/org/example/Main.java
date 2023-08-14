package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static String xpathSEP = "//*[@id=\"bibliography\"]/ul/li";


    public static void main(String[] args) throws IOException {

        String urlSEP = "https://plato.stanford.edu/entries/introspection/";
        String urlDDJ = "https://terebess.hu/english/tao/addiss.html";

        Document finalDocument = Main.getDocumentFromURL(urlSEP);
        Elements elements = Main.getElements(finalDocument, "ul.hanging:has(li)");

        //Main.writeFile("texts.txt", elements);

        Main.writeCSV("texts.csv", elements);






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




public static Elements getElements (Document doc, String searchAttribute) {


    //Elements texts = doc.getElementsByTag(searchAttribute);




    //Elements texts = doc.selectXpath(searchAttribute);



    Elements texts = doc.select(searchAttribute);

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
}



public static void writeCSV (String fileName, Elements elements) throws IOException {
    Writer writer = Files.newBufferedWriter(Paths.get(fileName));
    CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
    for (Element e : elements){

        String text = e.toString();
        //results.add(text);

        printer.printRecord(text);
        //printer.println();
        System.out.println(text);

    }
}






}