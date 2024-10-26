package com.gadv.screenmatch.model;

public enum SmCategory {
    ACCION("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedia"),
    DRAMA("Drama"),
    CRIMEN("Crimen");

    private String omdbCategory;
    SmCategory (String omdbCategory){
        this.omdbCategory = omdbCategory;
    }
    public static SmCategory fromString(String text) {
        for (SmCategory smCategory : SmCategory.values()) {
            if (smCategory.omdbCategory.equalsIgnoreCase(text)) {
                return smCategory;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
}
