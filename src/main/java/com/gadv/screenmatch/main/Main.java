package com.gadv.screenmatch.main;

import com.gadv.screenmatch.model.Episode;
import com.gadv.screenmatch.model.EpisodesData;
import com.gadv.screenmatch.model.SeasonsData;
import com.gadv.screenmatch.model.SeriesData;
import com.gadv.screenmatch.service.ConsultAPI;
import com.gadv.screenmatch.service.ConvertData;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private ConsultAPI consultAPI = new ConsultAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?apikey=63b7b10&t=";
    private ConvertData convertData = new ConvertData();
    public void showMenu(Scanner scanner){
        System.out.println("Por favor escribe el nombre de la Serie que deseas buscar:");
        String seriesName = scanner.nextLine();
        var json = consultAPI.getData(URL_BASE + URLEncoder.encode(seriesName, StandardCharsets.UTF_8));
        var data = convertData.getData(json, SeriesData.class);
        System.out.println(data);
        List<SeasonsData> seasonsDataList = new ArrayList<>();
        for (int i = 1; i <= data.totalSeasons(); i++) {
            json = consultAPI.getData(URL_BASE + URLEncoder.encode(seriesName, StandardCharsets.UTF_8) + "&Season=" + i);
            seasonsDataList.add(convertData.getData(json, SeasonsData.class));
        }
        seasonsDataList.forEach(System.out::println);

        //Mostrar el titulo de cada episodio por temporada
        for (int i = 0; i < data.totalSeasons(); i++) {
            List<EpisodesData> episodesPerSeasonDataList = seasonsDataList.get(i).episodesDataList();
            for (EpisodesData episodesData : episodesPerSeasonDataList) {
                System.out.println(episodesData.title());
            }
        }
        //Equivalente en funcion lambda
        seasonsDataList.forEach(s -> s.episodesDataList().forEach(e -> System.out.println("Episodio: " + e.episodeNumber() + " Temporada: " + s.seasonNumber() + " Titulo: " + e.title())));

        //Convertir toda la informacion a una lista de EpisodesData
        List<EpisodesData> episodesDataList = seasonsDataList.stream()
                .flatMap(t -> t.episodesDataList().stream())
                .collect(Collectors.toList());

        //Top 5 episodios
        System.out.println("Top 5 Episodios: ");
        episodesDataList.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                //.peek(e -> System.out.println("Primer filtro (N/A) " + e))
                .sorted(Comparator.comparing(EpisodesData::rating).reversed())
                //.peek(e -> System.out.println("Segundo filtro Ordenación (M>m) " + e))
                .limit(5)
                //.peek(e -> System.out.println("Tercer filtro Limite (Top 5) " + e))
                .forEach(System.out::println);

        //Convirtiendo los datos a una lista de tipo Episode
        List<Episode> episodeList = seasonsDataList.stream()
                .flatMap(t -> t.episodesDataList().stream()
                        .map(d -> new Episode(t.seasonNumber(), d)))
                .collect(Collectors.toList());
        episodeList.forEach(System.out::println);

        //Busqueda de episodios a partir de x año
        System.out.println("Por favor indica el año a partir del cual deseas ver los episodios");
        int year = scanner.nextInt();
        scanner.nextLine();
        LocalDate dateToSearch = LocalDate.of(year, 1, 1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodeList.stream()
                .filter(e -> e.getReleaseDate() != null && (e.getReleaseDate().isAfter(dateToSearch) || e.getReleaseDate().isEqual(dateToSearch)))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getSeasonNumber() +
                                " Episodio: " + e.getEpisodeNumber() +
                                " Fecha de Lanzamiento: " + e.getReleaseDate().format(dateTimeFormatter)
                ));

        //Busca episodios por fragmentos del titulo
        System.out.println("Por favor Ingrese el titulo de episodio que desea buscar:");
        String titleFragmentToSearch = scanner.nextLine();
        Optional<Episode> searchedEpisode = episodeList.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(titleFragmentToSearch.toUpperCase()))
                .findFirst();
        if (searchedEpisode.isPresent()) {
            System.out.println("Episodio Encontrado");
            System.out.println("Los datos son: " + searchedEpisode.get());
        } else {
            System.out.println("Episodio No Encontrado");
        }
        Map<Integer, Double> ratingPerSeason = episodeList.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeasonNumber,
                        Collectors.averagingDouble(Episode::getRating)));
        System.out.println("Evaluacion por temporada: ");
        System.out.println(ratingPerSeason);

        DoubleSummaryStatistics episodeListDoubleSummaryStatistics = episodeList.stream() // NOTA: tambien se debe mencionar el equivalente para int: IntSummaryStatistics
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));
        System.out.println("Media de las evaluaciones: " + episodeListDoubleSummaryStatistics.getAverage());
        System.out.println("Maxima evaluacion: " + episodeListDoubleSummaryStatistics.getMax());
        System.out.println("Evaluacion minima: " + episodeListDoubleSummaryStatistics.getMin());
        //Ejemplo IntSummaryStatistics
//        Random r = new Random();
//        IntSummaryStatistics intSummaryStatistics =
//                Stream.generate(r::nextInt)
//                        .limit(10000)
//                        .collect(Collectors.summarizingInt(Integer::intValue));
//        System.out.println(intSummaryStatistics);
    }
}
