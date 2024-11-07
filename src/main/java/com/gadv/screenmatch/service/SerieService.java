package com.gadv.screenmatch.service;

import com.gadv.screenmatch.dto.SerieDTO;
import com.gadv.screenmatch.model.Serie;
import com.gadv.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> getAllSeries(){
        return convertToSerieDTO(serieRepository.findAll());
    }

    public List<SerieDTO> getRecentLaunched(){
        return convertToSerieDTO(serieRepository.recentLaunched());
    }

    public List<SerieDTO> getTop5Series() {
        return convertToSerieDTO(serieRepository.findTop5ByOrderByRatingDesc());
    }

    public List<SerieDTO> convertToSerieDTO(List<Serie> series){
        return series.stream()
                .map(serie -> new SerieDTO(
                        serie.getTitle(),
                        serie.getTotalSeasons(),
                        serie.getRating(),
                        serie.getGenre(),
                        serie.getActors(),
                        serie.getPosterURL(),
                        serie.getSinopsis()
                )).collect(Collectors.toList());
    }
}
