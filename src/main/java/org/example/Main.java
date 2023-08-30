package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select city!");
        String selectedWords = scanner.nextLine();

//        String selectedWords = "Луцк";

        char lastLetter = Character.toLowerCase(selectedWords.charAt(selectedWords.length() - 1));
        if (lastLetter == 'ї' || lastLetter == 'ь') {
            lastLetter = Character.toLowerCase(selectedWords.charAt(selectedWords.length() - 2));
        }

        System.out.println("Last letter:" + lastLetter);

        //тут работа с файлом

        Map<Character, List<String>> citiesFromFile = CitiesListFromWikipedia.readCitiesNameFromFile("src/main/resources/cities.json");

        String result = CityFinder.cityFinderForLastLetter(citiesFromFile, lastLetter);
        System.out.println(result);
    }
    }
