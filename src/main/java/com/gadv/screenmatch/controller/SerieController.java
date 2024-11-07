package com.gadv.screenmatch.controller;

import com.gadv.screenmatch.dto.SerieDTO;
import com.gadv.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {
    @Autowired
    private SerieService serieService;
    @GetMapping("/series")
    public List<SerieDTO> getAllSeries(){
        return serieService.getAllSeries();
    }
    @GetMapping("/series/top5")
    public List<SerieDTO> getTop5Series(){
        return serieService.getTop5Series();
    }
}
