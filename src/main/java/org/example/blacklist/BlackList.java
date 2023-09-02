package org.example.blacklist;

import java.util.*;

public class BlackList {
    public HashMap<Character, ArrayList<String>> blackMap = new HashMap<>();

    //Якщо міста немає в Blacklist, метод повертає false і додає місто в Blacklist
    public boolean checkCityInBlackList (String cityName){
        char firstLetter = Character.toUpperCase(cityName.charAt(0));
        ArrayList<String> citiesList = blackMap.get(firstLetter);

        if (citiesList == null) {
            citiesList = new ArrayList<>();
            blackMap.put(firstLetter, citiesList);
        } else {
            for (String currentCity : citiesList) {
                if (currentCity.equalsIgnoreCase(cityName)) {
                    return true;
                }
            }
        }

        citiesList.add(cityName);
        return false;
    }

    public void restart() {
        blackMap = new HashMap<>();
    }
}
