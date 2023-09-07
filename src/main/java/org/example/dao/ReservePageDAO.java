package org.example.dao;

import org.example.model.SepPage;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public interface ReservePageDAO {


    //This should simply return the entire bibliography, formatted, when user searches for a page
    List<String> returnPricing () throws IOException;








}