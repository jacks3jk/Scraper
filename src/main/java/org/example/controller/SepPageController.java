package org.example.controller;


import org.example.dao.SepPageDAO;
import org.example.model.SepPage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class SepPageController {

    private SepPageDAO sepPageDAO;

    public SepPageController(SepPageDAO sepPageDAO) {
        this.sepPageDAO = sepPageDAO;
    }



    @GetMapping("/sepPage")
    public List<SepPage> fetchBibliographyByPageName(@RequestParam String pageName) {
    return sepPageDAO.returnBibliography(pageName);
    }

    @GetMapping("/test")
    public List<SepPage> testSepPageMethod () {
        List<SepPage> sepPage = new ArrayList<>();
        sepPage.add(new SepPage(2, "Test", "Test", "Test", "Test"));
        return sepPage;
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
