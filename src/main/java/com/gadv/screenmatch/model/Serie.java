package com.gadv.screenmatch.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name="series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String title;
    private Integer totalSeasons;
    private Double rating;
    @Enumerated(EnumType.STRING)
    private SmCategory genre; // MY VERSION: private ArrayList<SmCategory> genres = new ArrayList<>();
    private String actors;
    private String posterURL;
    private String sinopsis;
    //@Transient Para ignorar el List<Episode> mientras no se habia configurado
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episode> episodeList;

    public Serie(){}

    public Serie(SeriesData seriesData) {
        this.title = seriesData.title();
        this.totalSeasons = seriesData.totalSeasons();
        this.rating = OptionalDouble.of(Double.valueOf(seriesData.rating())).orElse(0);
        this.genre = SmCategory.fromString(seriesData.genre().split(",")[0].trim()); //MY VERSION: Arrays.stream(seriesData.genre().split(", ")).forEach(genre -> this.genres.add(SmCategory.fromString(genre)));
        this.actors = seriesData.actors();
        this.posterURL = seriesData.posterURL();
        this.sinopsis = seriesData.sinopsis();
    }

    @Override
    public String toString() {
        return "genre=" + genre +
                ", title='" + title + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", rating=" + rating +
                ", actors='" + actors + '\'' +
                ", posterURL='" + posterURL + '\'' +
                ", sinopsis='" + sinopsis + '\'' +
                ", episodios='" + episodeList + '\'';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    //MY VERSION:
//    public ArrayList<SmCategory> getGenres() {
//        return genres;
//    }
//
//    public SmCategory getGenre(int index) {
//        return genres.get(index);
//    }
//
//    public void setGenres(ArrayList<SmCategory> genre) {
//        this.genres = genre;
//    }

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

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        episodeList.forEach(episode -> episode.setSerie(this)); //CODIGO SEGUN CURSO
        this.episodeList = episodeList;
        //CODIGO IMPROVISADO POR MI ANTES DE VER VIDEO:
        //this.episodeList.forEach(episode -> episode.setSerie(this));//NO EJECUTAR HASTA VER VIDEO
    }
}
