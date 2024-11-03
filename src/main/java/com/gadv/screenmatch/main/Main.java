package com.gadv.screenmatch.main;

import com.gadv.screenmatch.model.*;
import com.gadv.screenmatch.repository.SerieRepository;
import com.gadv.screenmatch.service.ConsultAPI;
import com.gadv.screenmatch.service.ConvertData;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private ConsultAPI consultAPI = new ConsultAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?apikey=63b7b10&t=";
    private ConvertData convertData = new ConvertData();
    private List<SeriesData> seriesDataList = new ArrayList<>();
    private SerieRepository serieRepository;
    private List<Serie> seriesList;
    private Optional<Serie> searchedSerie;

    public Main(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void showMenu(Scanner scanner){
        int optionMenu = -1;
        while (optionMenu != 0) {
            String menu = """
                    1 - Buscar Series
                    2 - Buscar Episodios
                    3 - Mostrar Series Buscadas
                    4 - Buscar Series por Titulo
                    5 - Top 5 Mejores Series
                    6 - Buscar Series por Categoria
                    7 - Buscar Series por Máximo de Temporadas y Mínimo de Evaluación
                    8 - Buscar Episodios por Título
                    9 - Top 5 Episodios por Serie
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            optionMenu = scanner.nextInt();
            scanner.nextLine();
            switch (optionMenu) {
                case 1:
                    searchSeries(scanner);
                    break;
                case 2:
                    searchEpisodeBySeason(scanner);
                    break;
                case 3:
                    showSearchedSeries();
                    break;
                case 4:
                    searchSeriesByTitle(scanner);
                    break;
                case 5:
                    showTop5Series();
                    break;
                case 6:
                    searchSeriesByCategory(scanner);
                    break;
                case 7:
                    searchSeriesByMaxSeasonsAndMinRating(scanner);
                    break;
                case 8:
                    searchEpisodeByTitle(scanner);
                    break;
                case 9:
                    searchTop5EpisodesPerSerie(scanner);
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void searchTop5EpisodesPerSerie(Scanner scanner) {
        searchSeriesByTitle(scanner);
        if(searchedSerie.isPresent()){
            Serie serie = searchedSerie.get();
            List<Episode> topEpisodes = serieRepository.top5EpisodesBySerie(serie);
            topEpisodes.forEach(episode -> System.out.printf("Serie: %s | Temporada: %s | Episodio: %d - %s | Evaluación: %s\n",
                    episode.getSerie().getTitle(), episode.getSeasonNumber(), episode.getEpisodeNumber(), episode.getTitle(), episode.getRating()));
        }
    }

    private void searchEpisodeByTitle(Scanner scanner) {
        System.out.println("Ingrese el Título del Episodio que desea buscar");
        String episodeTitle = scanner.nextLine();
        serieRepository.episodeByName(episodeTitle)
                .forEach(episode -> System.out.printf("Serie: %s | Temporada: %s | Episodio: %s | Evaluación: %s\n",
                        episode.getSerie().getTitle(), episode.getSeasonNumber(), episode.getTitle(), episode.getRating()));
    }

    private void searchSeriesByMaxSeasonsAndMinRating(Scanner scanner) {
        System.out.println("Ingrese el máximo de Temporadas de la Serie que desea buscar: ");
        Integer maxSeasons = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese el mínimo de Evaluación de la Serie que desea buscar: ");
        Double minRating = scanner.nextDouble();
        scanner.nextLine();
        serieRepository.seriesByMaxSeasonsAndMinRating(maxSeasons, minRating).
                forEach(serie ->
                        System.out.println("Serie: " + serie.getTitle() +
                                " | Temporadas: " + serie.getTotalSeasons() +
                                " | Evaluación: " + serie.getRating()
                        )
                );
//        serieRepository.findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(maxSeasons, minRating).
//                forEach(System.out::println);
    }

    private void searchSeriesByCategory(Scanner scanner) {
        System.out.println("Escriba el genero/categoría de la serie que desea buscar");
        String genre = scanner.nextLine();
        System.out.println("Series de la categoría " + genre + ": ");
        serieRepository.findByGenre(SmCategory.fromEspanol(genre)).
                forEach(System.out::println);
    }

    private void showTop5Series() {
        serieRepository.findTop5ByOrderByRatingDesc().
                forEach(serie -> System.out.println("Serie: " + serie.getTitle() + " Rating: " + serie.getRating()));
    }

    private SeriesData getSeriesData(Scanner scanner){
        System.out.println("Por favor escribe el nombre de la Serie que deseas buscar:");
        String seriesName = scanner.nextLine();
        var json = consultAPI.getData(URL_BASE + URLEncoder.encode(seriesName, StandardCharsets.UTF_8));
        return convertData.getData(json, SeriesData.class);
    }

    private void searchEpisodeBySeason(Scanner scanner) {
        showSearchedSeries();
        System.out.println("Escribe el nombre de una de las series listadas, de la que desees buscar sus episodios");
        String serieName = scanner.nextLine();

        Optional<Serie> optionalSerie = seriesList.stream()
                .filter(serie -> serie.getTitle().toUpperCase().contains(serieName.toUpperCase()))
                .findFirst();

        if(optionalSerie.isPresent()){
            Serie foundedSerie = optionalSerie.get();
            List<SeasonsData> seasonsDataList = new ArrayList<>();
            for (int i = 1; i <= foundedSerie.getTotalSeasons(); i++) {
                String json = consultAPI.getData(URL_BASE + URLEncoder.encode(foundedSerie.getTitle(), StandardCharsets.UTF_8) + "&Season=" + i);
                seasonsDataList.add(convertData.getData(json, SeasonsData.class));
            }
            seasonsDataList.forEach(System.out::println);
            List<Episode> episodeList = seasonsDataList.stream()
                    .flatMap(seasonsData -> seasonsData.episodesDataList().stream()
                            .map(episodesData -> new Episode(seasonsData.seasonNumber(),episodesData)))
                    .collect(Collectors.toList());
            foundedSerie.setEpisodeList(episodeList);
            serieRepository.save(foundedSerie);
        }

    }

    private void searchSeries(Scanner scanner) {
        SeriesData seriesData = getSeriesData(scanner);
        Serie serie = new Serie(seriesData);
        serieRepository.save(serie);
        //seriesDataList.add(seriesData);
        System.out.println(seriesData);
    }

    private void showSearchedSeries() {
        seriesList = serieRepository.findAll();
        seriesList.stream()
                .sorted(Comparator.comparing(Serie::getGenre)) //MY VERSION: .sorted(Comparator.comparing(serie -> serie.getGenre(0)))
                .forEach(System.out::println);
    }

    private void searchSeriesByTitle(Scanner scanner) {
        System.out.println("Escribe el nombre de la serie que desees buscar");
        String serieName = scanner.nextLine();
        searchedSerie = serieRepository.findByTitleContainsIgnoreCase(serieName);
        if(searchedSerie.isPresent()){
            System.out.println("La serie buscada es: " + searchedSerie.get());
        }else {
            System.out.println("Serie No Encontrada");
        }
    }
}