package com.gadv.screenmatch.model;

import java.time.LocalDate;

public class Episode {
    private Integer seasonNumber;
    private String title;
    private Integer episodeNumber;
    private Double rating;
    private LocalDate releaseDate;

    public Episode(Integer seasonNumber, EpisodesData d) {
        this.seasonNumber = seasonNumber;
        this.episodeNumber = d.episodeNumber();
        this.title = d.title();
        try {
            this.rating = Double.valueOf(d.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }
        try {
            this.releaseDate = LocalDate.parse(d.releaseDate());
        } catch (Exception e) {
            this.releaseDate = null;
        }
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer season) {
        this.seasonNumber = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer seasonNumber) {
        this.episodeNumber = seasonNumber;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "seasonNumber=" + seasonNumber +
                ", title='" + title + '\'' +
                ", episodeNumber=" + episodeNumber +
                ", rating=" + rating +
                ", releaseDate=" + releaseDate;
    }
}
