package com.gadv.screenmatch.main;

import com.gadv.screenmatch.model.EpisodesData;
import com.gadv.screenmatch.model.SeasonsData;
import com.gadv.screenmatch.model.SeriesData;
import com.gadv.screenmatch.service.ConsultAPI;
import com.gadv.screenmatch.service.ConvertData;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        //seasonsDataList.forEach(System.out::println);

        //Mostrar el titulo de cada episodio por temporada
//        for (int i = 0; i < data.totalSeasons(); i++) {
//            List<EpisodesData> episodesPerSeasonDataList = seasonsDataList.get(i).episodesDataList();
//            for (int j = 0; j < episodesPerSeasonDataList.size(); j++) {
//                System.out.println(episodesPerSeasonDataList.get(j).title());
//            }
//        }
        //Equivalente en funcion lambda
        seasonsDataList.forEach(s -> s.episodesDataList().forEach(e -> System.out.println("Episodio: " + e.episodeNumber() + " Temporada: " + s.seasonNumber() + " Titulo: " + e.title())));
    }
}
