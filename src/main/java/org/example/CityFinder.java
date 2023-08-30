package org.example;
import java.util.List;
import java.util.Map;

public class CityFinder {
    public static String cityFinderForLastLetter(Map<Character, List<String>> citiesMap, char lastLetter) {
        String returnCity = "";

        char upperCaseLastLetter = Character.toUpperCase(lastLetter);
        List<String> citiesList = citiesMap.get(upperCaseLastLetter);

        if (citiesList != null && !citiesList.isEmpty()) {
            returnCity = citiesList.get(0);
        }

        return returnCity;
    }
}

