package com.gadv.screenmatch.model;

import java.util.OptionalDouble;

public class Serie {
    private String title;
    private Integer totalSeasons;
    private Double rating;
    private SmCategory genre;
    private String actors;
    private String posterURL;
    private String sinopsis;

    public Serie(SeriesData seriesData) {
        this.title = seriesData.title();
        this.totalSeasons = seriesData.totalSeasons();
        this.rating = OptionalDouble.of(Double.parseDouble(seriesData.rating())).orElse(0);
        this.genre = SmCategory.fromString(seriesData.genre().split(",")[0].trim());
        this.actors = seriesData.actors();
        this.posterURL = seriesData.posterURL();
        this.sinopsis = seriesData.sinopsis();
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", rating=" + rating +
                ", genre=" + genre +
                ", actors='" + actors + '\'' +
                ", posterURL='" + posterURL + '\'' +
                ", sinopsis='" + sinopsis + '\'';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public SmCategory getGenre() {
        return genre;
    }

    public void setGenre(SmCategory genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
}