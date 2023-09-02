package org.example;

import org.example.blacklist.BlackList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private BlackList blackList;
    private char lastLetter;

    private Map<Character, List<String>> cities;
    private Map<Character, List<String>> computerCities;

    public Main() throws IOException {
        blackList = new BlackList();
        try {
            cities = CitiesListFromWikipedia.readCitiesNameFromFile("src/main/resources/cities.json");
        } catch (FileNotFoundException exception) {
            cities = CitiesListFromWikipedia.getCitiesName("src/main/resources/cities.json");
        }
        computerCities = copyCities(cities);
    }

    private Map<Character, List<String>> copyCities(Map<Character, List<String>> from) {
        return from.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new ArrayList<>(entry.getValue())));
    }

    public char getLastLetter() {
        return lastLetter;
    }

    public void putWordFromUser(String city) {
        List<String> citiesList = computerCities.get(getFirstLetter(city));
        citiesList.removeIf(currentCity -> currentCity.equalsIgnoreCase(city));

        changeLastLetter(city);
    }

    public Optional<String> getWordFromComputer() {
        Optional<String> cityInOptional = CityFinder.cityFinderForLastLetter(computerCities, lastLetter);

        if (cityInOptional.isPresent()) {
            String city = cityInOptional.get();

            computerCities.get(lastLetter).remove(city);
            blackList.checkCityInBlackList(city);

            changeLastLetter(city);
        }

        return cityInOptional;
    }

    public boolean checkNotLetter(String city) {
        return !(city != null && city.length() != 0);
    }

    public boolean isCity(String city) {
        char firstLetter = getFirstLetter(city);

        if (cities.containsKey(firstLetter)) {
            for (String currentCity : cities.get(firstLetter)) {
                if (currentCity.equalsIgnoreCase(city)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkCityFirstLetter(String city) {
        if (lastLetter == '\u0000') {
            return true;
        }

        return getFirstLetter(city) == lastLetter;
    }

    public boolean checkCityInBlackList(String city) {
        return blackList.checkCityInBlackList(city);
    }

    public boolean isCityOnLastLetterOfCity(String city) {
        return !computerCities.get(getLastLetter(city)).isEmpty();
    }

    private Character getFirstLetter(String city) {
        if (city == null || city.length() == 0) {
            throw new NoSuchElementException("String is null or empty");
        }

        return Character.toUpperCase(city.charAt(0));
    }

    private Character getLastLetter(String city) {
        char[] cityLetters = city.toUpperCase().toCharArray();
        for (int i = city.length() - 1; i > -1; i--) {
            if (cities.containsKey(cityLetters[i])) {
                return cityLetters[i];
            }
        }

        throw new NoSuchElementException("String is null or empty");
    }

    private void changeLastLetter(String city) {
        lastLetter = getLastLetter(city);
    }

    private void restart() {
        lastLetter = '\u0000';
        computerCities = copyCities(cities);
        blackList.restart();
    }

    public static void main(String[] args) throws IOException {
        Main game = new Main();

        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------------------------   Гра \"Міста\"   ---------------------------------------------------");
        System.out.println("Правила");
        System.out.println("1. Щоб закінчити гру введіть 'здаюсь'.");
        System.out.println("2. Ви переможете, якщо комп'ютер не матиме міста для відповіді. Доступні тільки всі міста України.");
        System.out.println("3. Щоб почати спочатку введіть 'заново'.");
        System.out.println("Вперед!!!");

        String selectedCity;
        Optional<String> selectedCityInOptional;
        while (true) {
            System.out.println();

            System.out.print("Введіть назву міста: ");
            selectedCity = scanner.nextLine();

            if (game.checkNotLetter(selectedCity)) {
                System.out.println("Ви нічого не ввели!");
                continue;
            }

            if (selectedCity.equals("здаюсь")) {
                System.out.println("На жаль, ви програли!");
                break;
            } else if (selectedCity.equals("заново")) {
                System.out.println("Починаємо спочатку!");
                System.out.println();
                game.restart();
                continue;
            }

            if (!game.isCity(selectedCity)) {
                System.out.println("В Україні такого міста не існує!!!");
                continue;
            }

            if (!game.checkCityFirstLetter(selectedCity)) {
                System.out.println("Це місто не починається з останньої букви попереднього міста: '" + game.getLastLetter() + "' !!!");
                continue;
            }

            if (game.checkCityInBlackList(selectedCity)) {
                System.out.println("Це місто вже називали!!!");
                continue;
            }

            game.putWordFromUser(selectedCity);
            selectedCityInOptional = game.getWordFromComputer();
            if (selectedCityInOptional.isPresent()) {
                System.out.println("Комп'ютер: " + selectedCityInOptional.get());

                if (!game.isCityOnLastLetterOfCity(selectedCityInOptional.get())) {
                    System.out.println("На жаль, ви програли! Всі міста України на букву '" +
                            game.getLastLetter() + "' використані.");
                    break;
                }
            } else {
                System.out.println("Ви перемогли!!! Всі міста України на букву '" +
                        game.getLastLetter() + "' використані.");
                break;
            }
        }
    }
}
