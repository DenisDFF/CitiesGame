package org.example.blacklist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class BlackListTest {
    public static void main(String[] args) {
        BlackList blackList = new BlackList();
        ArrayList<String> cityWithK = new ArrayList<>();
        ArrayList<String> cityWithL = new ArrayList<>();
        ArrayList<String> cityWithD = new ArrayList<>();
        ArrayList<String> cityWithM = new ArrayList<>();

        cityWithK.add("Київ");
        cityWithK.add("Кривий Ріг");
        cityWithK.add("Кропівницький");
        cityWithD.add("Дніпро");
        cityWithD.add("Донецьк");
        cityWithD.add("Джанкой");
        cityWithL.add("Луцьк");
        cityWithL.add("Луганськ");

        Set<Character> keys = blackList.blackMap.keySet();
        System.out.println("Keys: " + keys);

        Collection<ArrayList<String>> values = blackList.blackMap.values();
        System.out.println("Values: " + values);
        System.out.println("-----------------------");
        blackList.blackMap.put('К',cityWithK);
        blackList.blackMap.put('Д',cityWithD);
        blackList.blackMap.put('Л',cityWithL);
        blackList.blackMap.put('М', cityWithM);

        System.out.println("blackList.checkCityInBlackList(\"Київ\") = " + blackList.checkCityInBlackList("Київ"));
        System.out.println("blackList.checkCityInBlackList(\"Миколаїв\") = " + blackList.checkCityInBlackList("Миколаїв"));
        System.out.println("blackList.checkCityInBlackList(\"Миколаїв\") = " + blackList.checkCityInBlackList("Миколаїв"));
        System.out.println("blackList.blackMap.get('М').toString() = " + blackList.blackMap.get('К').toString());
        System.out.println("blackList.blackMap.get('М').toString() = " + blackList.blackMap.get('Д').toString());
        System.out.println("blackList.blackMap.get('М').toString() = " + blackList.blackMap.get('Л').toString());
        System.out.println("blackList.blackMap.get('М').toString() = " + blackList.blackMap.get('М').toString());
        System.out.println("--------------------");
        System.out.println("blackList.checkCityInBlackList(\"Львів\") = " + blackList.checkCityInBlackList("Львів"));
        System.out.println("blackList.checkCityInBlackList(\"Рівне\") = " + blackList.checkCityInBlackList("Рівне"));
        System.out.println("--------------------");
        System.out.println("blackList.blackMap.get('K').toString() = " + blackList.blackMap.get('К').toString());
        System.out.println("blackList.blackMap.get('Д').toString() = " + blackList.blackMap.get('Д').toString());
        System.out.println("blackList.blackMap.get('Л').toString() = " + blackList.blackMap.get('Л').toString());
        System.out.println("blackList.blackMap.get('М').toString() = " + blackList.blackMap.get('М').toString());
        System.out.println("blackList.blackMap.get('Р').toString() = " + blackList.blackMap.get('Р').toString());
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        System.out.println("Keys: " + keys);
        System.out.println("Values: " + values);
    }

}