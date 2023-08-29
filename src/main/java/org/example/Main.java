package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Map<Character, List<String>> citiesFromWikipedia = CitiesListFromWikipedia.getCitiesName("src/main/resources/cities.json");
        System.out.println(citiesFromWikipedia);
        Map<Character, List<String>> citiesFromFile = CitiesListFromWikipedia.readCitiesNameFromFile("src/main/resources/cities.json");
        System.out.println(citiesFromFile);

    }
}
