package org.example;
import java.util.List;

public class CityFinder {
    public static String cityFinderForLastLetter (List<String> words, char lastLetter) {
        String returnSity = "";

        for (String word: words) {
            String toLowerWord = word.toLowerCase();
            if (returnSity.isEmpty()) {
                if (lastLetter == toLowerWord.charAt(0)) {
                    returnSity += word;
                }
            }
        }
        return returnSity;
    }
}
