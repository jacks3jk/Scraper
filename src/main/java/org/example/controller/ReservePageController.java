package org.example.controller;


import org.example.dao.ReservePageDAO;
import org.example.dao.SepPageDAO;
import org.example.model.SepPage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ReservePageController {

    private ReservePageDAO reservePageDAO;

    public ReservePageController(ReservePageDAO reservePageDAO) {
        this.reservePageDAO = reservePageDAO;
    }

    @GetMapping("/price")
    public List<String> fetchAllPrices() throws IOException {

        ////GET call -- http://localhost:3000/price

        return reservePageDAO.returnPricing();
    }

}
