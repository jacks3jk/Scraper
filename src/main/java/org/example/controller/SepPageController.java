package org.example.controller;

import com.example.daodejing.dao.ChapterTextDAO;
import com.example.daodejing.model.ChapterText;
import com.example.daodejing.model.Quote;
import org.example.dao.SepPageDAO;
import org.example.model.SepPage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
