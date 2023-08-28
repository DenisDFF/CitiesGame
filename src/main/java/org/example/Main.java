package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<String> words = CityPushToList.pushCityToList();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select city!");
        String selectedWords = scanner.nextLine();

        char lastLetter = Character.toLowerCase(selectedWords.charAt(selectedWords.length() - 1));

        System.out.println("Last letter:" + lastLetter);

        String result = CityFinder.cityFinderForLastLetter(words, lastLetter);
        System.out.println(result);
    }
    }
