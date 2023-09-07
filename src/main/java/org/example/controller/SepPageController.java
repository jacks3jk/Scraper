package org.example.controller;


import org.example.dao.SepPageDAO;
import org.example.model.SepPage;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class SepPageController {

    private SepPageDAO sepPageDAO;

    public SepPageController(SepPageDAO sepPageDAO) {
        this.sepPageDAO = sepPageDAO;
    }




    @GetMapping("/fullpage")
    public SepPage fetchWholePage(@RequestParam("id") int id) throws IOException {

        //GET call -- http://localhost:3000/fullpage?id=1

        return sepPageDAO.returnFullSEPPage(id);
    }
    @GetMapping("/alltitles")
    public List<String> fetchAllTitles() throws IOException {
        System.out.println(sepPageDAO.returnAllTitles().toString());

        return sepPageDAO.returnAllTitles();
    }

    @GetMapping("/all")
    public List<String> fetchAllBibliography() throws IOException {

        ////GET call -- http://localhost:3000/sepPage?page=introspection

        System.out.println(sepPageDAO.returnAllURLS().toString());

        //System.out.println(sepPageDAO.returnAllURLS().get(1));
        return sepPageDAO.returnAllURLS();

    }

    @GetMapping("/sepPage")
    public List<String> fetchBibliographyByPageName(@RequestParam("page") String pageName) throws IOException {

    ////POST call -- http://localhost:3000/sepPage?page=introspection

    return sepPageDAO.returnBibliography(pageName);
    }

//    @GetMapping("/test")
//    public List<SepPage> testSepPageMethod (@RequestParam("id") int id) {
//
//        //POST call -- http://localhost:3000/test?id=1
//        //POST call -- http://localhost:3000/test?id=2
//
//        List<SepPage> sepPage = new ArrayList<>();
//
//        if (id == 1) {
//            sepPage.add(new SepPage(1, "Test", "Test", "Test", "Test"));
//        }
//        else if (id == 2) {
//            sepPage.add(new SepPage(2, "Test2", "Test2", "Test2", "Test2"));
//            }
//            return sepPage;
//        }


    @GetMapping("/author")
    public int getAuthorCount (@RequestParam("author") String authorName) throws IOException {

        //POST call -- http://localhost:3000/author?author=Alston

        return sepPageDAO.checkAuthorCount(authorName, "introspection");
    }


    }

//    @GetMapping("/translations")
//    public List<ChapterText> fetchTranslationsByChapter(@RequestParam int chapterNumber) {
//        return chapterTextDAO.returnAllChapters(chapterNumber);
//    }
//
//    @GetMapping("/quote")
//    public List<Quote> listQuotes (@RequestParam String searchText) {
//        return chapterTextDAO.returnAllMatching(searchText);
//    }
//
//    @GetMapping("/authors")
//    public List<ChapterText> listTranslations () {
//        return chapterTextDAO.returnAllTranslations();
//    }
//
//    @GetMapping("/word")
//    public int wordCount(@RequestParam String searchWord) {
//        return chapterTextDAO.returnWordCount(searchWord);
//    }
//}
