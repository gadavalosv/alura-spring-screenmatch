package com.gadv.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonsData(
    @JsonAlias("Season") Integer seasonNumber,
    @JsonAlias("Episodes") List<EpisodesData> episodesDataList){
}
