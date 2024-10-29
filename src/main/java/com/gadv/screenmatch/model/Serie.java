package com.gadv.screenmatch.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalDouble;

public class Serie {
    private String title;
    private Integer totalSeasons;
    private Double rating;
    private ArrayList<SmCategory> genres = new ArrayList<>();
    private String actors;
    private String posterURL;
    private String sinopsis;

    public Serie(SeriesData seriesData) {
        this.title = seriesData.title();
        this.totalSeasons = seriesData.totalSeasons();
        this.rating = OptionalDouble.of(Double.parseDouble(seriesData.rating())).orElse(0);
        Arrays.stream(seriesData.genre().split(", ")).forEach(genre -> this.genres.add(SmCategory.fromString(genre))); //this.genre = SmCategory.fromString(seriesData.genre().split(",")[0].trim());
        this.actors = seriesData.actors();
        this.posterURL = seriesData.posterURL();
        this.sinopsis = seriesData.sinopsis();
    }

    @Override
    public String toString() {
        return "genres=" + genres +
                ", title='" + title + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", rating=" + rating +
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

    public ArrayList<SmCategory> getGenres() {
        return genres;
    }

    public SmCategory getGenre(int index) {
        return genres.get(index);
    }

    public void setGenres(ArrayList<SmCategory> genre) {
        this.genres = genre;
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
