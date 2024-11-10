package com.gadv.screenmatch.controller;

import com.gadv.screenmatch.dto.EpisodeDTO;
import com.gadv.screenmatch.dto.SerieDTO;
import com.gadv.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {
    @Autowired
    private SerieService serieService;
    @GetMapping()
    public List<SerieDTO> getAllSeries(){
        return serieService.getAllSeries();
    }
    @GetMapping("/top5")
    public List<SerieDTO> getTop5Series(){
        return serieService.getTop5Series();
    }
    @GetMapping("/lanzamientos")
    public List<SerieDTO> getRecentLaunched(){
        return serieService.getRecentLaunched();
    }
    @GetMapping("/{id}")
    public SerieDTO getById(@PathVariable Long id){
        return serieService.getById(id);
    }
    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> getAllSeasons(@PathVariable Long id){
        return serieService.getAllSeasons(id);
    }
    @GetMapping("/{id}/temporadas/top")
    public List<EpisodeDTO> getTopEpisodes(@PathVariable Long id){
        return serieService.getTopEpisodes(id);
    }
    @GetMapping("/{id}/temporadas/{seasonNumber}")
    public List<EpisodeDTO> getSeasonByNumber(@PathVariable Long id, @PathVariable Integer seasonNumber){
        return serieService.getSeasonByNumber(id, seasonNumber);
    }
    @GetMapping("/categoria/{category}")
    public List<SerieDTO> getSeriesByCategory(@PathVariable String category){
        return serieService.getSeriesByCategory(category);
    }
}
