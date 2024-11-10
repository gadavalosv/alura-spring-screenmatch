package com.gadv.screenmatch.service;

import com.gadv.screenmatch.dto.EpisodeDTO;
import com.gadv.screenmatch.dto.SerieDTO;
import com.gadv.screenmatch.model.Serie;
import com.gadv.screenmatch.model.SmCategory;
import com.gadv.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                        serie.getId(),
                        serie.getTitle(),
                        serie.getTotalSeasons(),
                        serie.getRating(),
                        serie.getGenre(),
                        serie.getActors(),
                        serie.getPosterURL(),
                        serie.getSinopsis()
                )).collect(Collectors.toList());
    }

    public SerieDTO getById(Long id) {
        Optional<Serie> serieOptional = serieRepository.findById(id);
        if(serieOptional.isPresent()){
            Serie serie = serieOptional.get();
            return new SerieDTO(
                    serie.getId(),
                    serie.getTitle(),
                    serie.getTotalSeasons(),
                    serie.getRating(),
                    serie.getGenre(),
                    serie.getActors(),
                    serie.getPosterURL(),
                    serie.getSinopsis()
            );
        }
        return null;
    }

    public List<EpisodeDTO> getAllSeasons(Long id) {
        Optional<Serie> serieOptional = serieRepository.findById(id);
        if(serieOptional.isPresent()){
            Serie serie = serieOptional.get();
            return serie.getEpisodeList().stream()
                    .map(episode -> new EpisodeDTO(
                            episode.getSeasonNumber(),
                            episode.getTitle(),
                            episode.getEpisodeNumber()
                    )).collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodeDTO> getSeasonByNumber(Long id, Integer seasonNumber) {
        return serieRepository.getSeasonByNumber(id, seasonNumber).stream()
                .map(episode -> new EpisodeDTO(
                        episode.getSeasonNumber(),
                        episode.getTitle(),
                        episode.getEpisodeNumber()
                )).collect(Collectors.toList());
        //Mi codigo pre curso:
//        Optional<Serie> serieOptional = serieRepository.findById(id);
//        if(serieOptional.isPresent()){
//            Serie serie = serieOptional.get();
//            return serie.getEpisodeList().stream()
//                    .filter(episode -> episode.getSeasonNumber().equals(seasonNumber))
//                    .map(episode -> new EpisodeDTO(
//                            episode.getSeasonNumber(),
//                            episode.getTitle(),
//                            episode.getEpisodeNumber()
//                    )).collect(Collectors.toList());
//        }
//        return null;
    }

    public List<SerieDTO> getSeriesByCategory(String category) {
        SmCategory smCategory = SmCategory.fromEspanol(category);
        return convertToSerieDTO(serieRepository.findByGenre(smCategory));
    }

    public List<EpisodeDTO> getTopEpisodes(Long id) {
        return serieRepository.top5EpisodesBySerie(serieRepository.findById(id).get()).stream()
                .map(episode -> new EpisodeDTO(
                        episode.getSeasonNumber(),
                        episode.getTitle(),
                        episode.getEpisodeNumber()
                )).collect(Collectors.toList());
    }
}
