package org.example.blacklist;

import java.util.*;

public class BlackList {
    public HashMap<Character, ArrayList<String>> blackMap = new HashMap<>();

    //Якщо міста немає в Blacklist, метод повертає false і додає місто в Blacklist
    public boolean checkCityInBlackList (String cityName){
        char firstLetter = cityName.charAt(0);
        ArrayList<String> newList = blackMap.get(firstLetter);

        if(newList == null){
            newList = new ArrayList<>();
            blackMap.put(firstLetter,newList);
        }

        boolean result = newList.contains(cityName);

        if(!result){
            newList.add(cityName);
        }
        return result;
    }




}
