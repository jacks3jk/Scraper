package org.example.model;

public class SepPage {

    private int sepPageID;

    private String sepPageName;

    private String sepPageBibliography;

    private String sepPageAuthor;

    private String sepPageYear;


    public int getSepPageID (){
     return sepPageID;
    }

    public void setSepPageID (int sepPageID) {
        this.sepPageID = sepPageID;
    }

    public String getSepPageName () {
        return sepPageName;
    }

    public void setSepPageName (String sepPageName) {
        this.sepPageName = sepPageName;
    }

    public String getSepPageBibliography () {
        return sepPageBibliography;
    }

    public void setSepPageBibliography(String sepPageBibliography) {
        this.sepPageBibliography = sepPageBibliography;
    }

    public String getSepPageAuthor() {
        return sepPageAuthor;
    }

    public void setSepPageAuthor(String sepPageAuthor) {
        this.sepPageAuthor = sepPageAuthor;
    }

    public String getSepPageYear() {
        return sepPageYear;
    }

    public void setSepPageYear(String sepPageYear) {
        this.sepPageYear = sepPageYear;
    }
}
