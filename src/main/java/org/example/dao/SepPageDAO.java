package org.example.dao;

import org.example.model.SepPage;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public interface SepPageDAO {


    //This should simply return the entire bibliography, formatted, when user searches for a page
    List<String> returnBibliography (String sepPageName) throws IOException;



    //Should return author and year
    List<SepPage> returnInfo (String sepPageName);


    //When user types author, return pages WRITTEN by this author
    List<SepPage> returnAllPagesByAuthor (String sepPageAuthor);


    //When user searches for writer, this function should return all SEP pages that have this authors work in the bibliography section
    List<SepPage> returnAllPagesMentions (String sepPageBibliography);

    void setBibliography (String sepPageName, Elements ele) throws IOException;

    int checkAuthorCount (String authorName, String sepPage) throws IOException;


    List <String> returnAllBibliographies () throws IOException;







}