package org.example;

import org.example.blacklist.BlackList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {
    private BlackList blackList;
    private char lastLetter;

    private Map<Character, List<String>> computerCities;

    public Main() throws IOException {
        blackList = new BlackList();
        try {
            computerCities = CitiesListFromWikipedia.readCitiesNameFromFile("src/main/resources/cities.json");
        } catch (FileNotFoundException exception) {
            computerCities = CitiesListFromWikipedia.getCitiesName("src/main/resources/cities.json");
        }
    }

    public boolean putWordFromUser(String city) {
        if (lastLetter != '\u0000') {
            char firstLetter = Character.toLowerCase(city.charAt(0));
            if (firstLetter != lastLetter) {
                return false;
            }
        }

        computerCities.get(Character.toUpperCase(Character.toLowerCase(city.charAt(0)))).remove(city);

        if (blackList.checkCityInBlackList(city)) {
            return false;
        }

        findLastLetter(city);

        return true;
    }

    private void findLastLetter(String city) {
        char[] cityLetters = city.toCharArray();
        for (int i = city.length() - 1; i > -1; i--) {
            if (computerCities.containsKey(Character.toUpperCase(cityLetters[i]))) {
                lastLetter = Character.toLowerCase(cityLetters[i]);
                break;
            }
        }
    }

    public String getWordFromComputer() {
        String city = CityFinder.cityFinderForLastLetter(computerCities, lastLetter);

        if (city.equals("")) {
            return "game exit";
        }

        computerCities.get(Character.toUpperCase(lastLetter)).remove(city);

        findLastLetter(city);

        return city;
    }

    public static void main(String[] args) throws IOException {
        Main game = new Main();

        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------------------------   Гра \"Міста\"   ---------------------------------------------------");

        String selectedCity;
        while (true) {
            System.out.print("Введіть назву міста: ");
            selectedCity = scanner.nextLine();

            if (selectedCity.equals("здаюсь")) {
                System.out.println("На жаль, ви програли!");
                break;
            }

            if (game.putWordFromUser(selectedCity)) {
                selectedCity = game.getWordFromComputer();
                if (selectedCity.equals("game exit")) {
                    System.out.println("Ви перемогли!!!");
                    break;
                }
                System.out.println("Комп'ютер: " + selectedCity);
            } else {
                System.out.println("Це місто вже називали або ваше місто не починається з останньої букви попереднього міста!!!");
            }

            System.out.println();
        }
    }
}
