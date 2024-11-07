package com.gadv.screenmatch.dto;

import com.gadv.screenmatch.model.SmCategory;

public record SerieDTO(
        String title,
        Integer totalSeasons,
        Double rating,
        SmCategory genre,
        String actors,
        String posterURL,
        String sinopsis
) {
}
