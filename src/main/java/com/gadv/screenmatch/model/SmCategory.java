package com.gadv.screenmatch.model;

public enum SmCategory {
    ACCION("Action", "Acción"),
    ADVENTURE("Adventure", "Aventura"), // extra to course
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comedia"),
    DRAMA("Drama", "Drama"),
    CRIMEN("Crime", "Crimen");

    private String omdbCategory;
    private String espanolCategory;
    SmCategory (String omdbCategory, String espanolCategory){
        this.omdbCategory = omdbCategory;
        this.espanolCategory = espanolCategory;
    }
    public static SmCategory fromString(String text) {
        for (SmCategory smCategory : SmCategory.values()) {
            if (smCategory.omdbCategory.equalsIgnoreCase(text)) {
                return smCategory;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
    public static SmCategory fromEspanol(String text) {
        for (SmCategory smCategory : SmCategory.values()) {
            if (smCategory.espanolCategory.equalsIgnoreCase(text)) {
                return smCategory;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
}
