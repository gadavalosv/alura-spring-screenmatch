package com.gadv.screenmatch.main;

import com.gadv.screenmatch.model.SeasonsData;
import com.gadv.screenmatch.model.SeriesData;
import com.gadv.screenmatch.service.ConsultAPI;
import com.gadv.screenmatch.service.ConvertData;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    private ConsultAPI consultAPI = new ConsultAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?apikey=63b7b10&t=";
    private ConvertData convertData = new ConvertData();
    private List<SeriesData> seriesDataList = new ArrayList<>();
    public void showMenu(Scanner scanner){
        int optionMenu = -1;
        while (optionMenu != 0) {
            String menu = """
                    1 - Buscar Series
                    2 - Buscar Episodios
                    3 - Mostrar Series Buscadas
                    
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
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private SeriesData getSeriesData(Scanner scanner){
        System.out.println("Por favor escribe el nombre de la Serie que deseas buscar:");
        String seriesName = scanner.nextLine();
        var json = consultAPI.getData(URL_BASE + URLEncoder.encode(seriesName, StandardCharsets.UTF_8));
        return convertData.getData(json, SeriesData.class);
    }

    private void searchEpisodeBySeason(Scanner scanner) {
        SeriesData seriesData = getSeriesData(scanner);
        List<SeasonsData> seasonsDataList = new ArrayList<>();
        for (int i = 1; i <= seriesData.totalSeasons(); i++) {
            String json = consultAPI.getData(URL_BASE + URLEncoder.encode(seriesData.title(), StandardCharsets.UTF_8) + "&Season=" + i);
            seasonsDataList.add(convertData.getData(json, SeasonsData.class));
        }
        seasonsDataList.forEach(System.out::println);
    }

    private void searchSeries(Scanner scanner) {
        SeriesData seriesData = getSeriesData(scanner);
        seriesDataList.add(seriesData);
        System.out.println(seriesData);
    }

    private void showSearchedSeries() {
        seriesDataList.forEach(System.out::println);
    }
}