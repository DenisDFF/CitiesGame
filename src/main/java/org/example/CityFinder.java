package org.example;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CityFinder {
    public static Optional<String> cityFinderForLastLetter(Map<Character, List<String>> citiesMap, char lastLetter) {
        List<String> citiesList = citiesMap.get(lastLetter);

        if (citiesList != null && !citiesList.isEmpty()) {
            return Optional.of(citiesList.get(0));
        }

        return Optional.empty();
    }
}

