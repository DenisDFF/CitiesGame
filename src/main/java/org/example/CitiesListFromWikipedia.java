package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class CitiesListFromWikipedia {
    public static Map<Character, List<String>> getCitiesName(String pathToFile) throws IOException {

        Connection.Response execute = Jsoup.connect("https://uk.wikipedia.org/w/api.php?action=parse&page=Міста_України_(список)&format=json")
                .ignoreContentType(true)
                .followRedirects(false)
                .method(Connection.Method.GET)
                .execute();

        String stringResponse = execute.body();

        Map<Character, List<String>> result = new HashMap<>();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonObjectOfResponse = gson.fromJson(stringResponse, JsonObject.class);
        String page = jsonObjectOfResponse.get("parse").getAsJsonObject().get("text").getAsJsonObject().get("*").getAsString();

        Document document = Jsoup.parse(page);
        Element tableWithCities = document.getElementsByClass("wikitable").first();
        Elements nameElements = tableWithCities.select("tr td:nth-of-type(2)");

        String currentName;
        char currentFirstLetter;
        List<String> currentListOfCities;
        for (Element nameElement : nameElements) {

            currentName = nameElement.text();
            currentFirstLetter = currentName.charAt(0);

            if ((currentListOfCities = result.get(currentFirstLetter)) != null) {
                currentListOfCities.add(currentName);
            } else {
                currentListOfCities = new ArrayList<>();
                currentListOfCities.add(currentName);
                result.put(currentFirstLetter, currentListOfCities);
            }
        }

        try (FileWriter writer = new FileWriter(pathToFile)) {
            writer.write(gson.toJson(result));
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static Map<Character, List<String>> readCitiesNameFromFile(String pathToFile) throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type type = new TypeToken<Map<Character, List<String>>>() {
        }.getType();
        return gson.fromJson(new FileReader(pathToFile), type);
    }
}