package com.gadv.screenmatch.repository;

import com.gadv.screenmatch.model.Episode;
import com.gadv.screenmatch.model.Serie;
import com.gadv.screenmatch.model.SmCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTitleContainsIgnoreCase(String seriesName);
    List<Serie> findTop5ByOrderByRatingDesc();
    List<Serie> findByGenre(SmCategory category);
    //List<Serie> findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(Integer totalSeasons, Double rating);
    @Query("SELECT s FROM Serie s WHERE s.totalSeasons <= :totalSeasons AND s.rating >= :rating")
    List<Serie> seriesByMaxSeasonsAndMinRating(Integer totalSeasons, Double rating);
    @Query("SELECT e FROM Serie s JOIN s.episodeList e WHERE e.title ILIKE %:nameEpisode%")
    List<Episode> episodeByName(String nameEpisode);
    @Query("SELECT e FROM Serie s JOIN s.episodeList e WHERE s = :serie ORDER BY e.rating DESC LIMIT 5")
    List<Episode> top5EpisodesBySerie(Serie serie);
}
