package com.gadv.screenmatch.main;

import java.util.Arrays;
import java.util.List;

public class StreamsExample {
    public static void showExample(){
        List<String> names = Arrays.asList("Carlos", "Luis", "Gerardo", "Isaac", "Eric", "Maria Fernanda", "Gustavo");
        names.stream()
                .filter(n -> n.startsWith("L"))
                .sorted()
                .limit(4)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
}
