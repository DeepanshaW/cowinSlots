package com.deepansha.cowinSlots.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Utils {

    public static String formatStateData(List<Map<String, Integer>> list) {
        StringBuilder builder = new StringBuilder();
        String preText = "Select state: \n\n";
        builder.append(preText);

        for (int i = 0; i < list.size(); i++) {
            formatStateOrCity((Map<String, Integer>) list.get(i), builder);
        }
        return builder.toString();
    }

    public static String formatCityData(Map<String, Integer> map) {
        StringBuilder builder = new StringBuilder();
        String preText = "Select city: \n\n";
        builder.append(preText);
        return formatStateOrCity(map, builder);
    }

    private static String formatStateOrCity(Map<String, Integer> map, StringBuilder builder) {
        Map<String, Integer> sortedMap = new TreeMap<String, Integer>(map);
        Set<Map.Entry<String, Integer>> set = sortedMap.entrySet();
        Iterator<Map.Entry<String, Integer>> itr = set.iterator();
        while (itr.hasNext()) {
            Map.Entry<String, Integer> entry = itr.next();
            builder.append(entry.getKey());
            builder.append("\n\n");
        }
        return builder.toString();
    }

    public static List<String> getStateList(List<Map<String, Integer>> list) {
        List<String> states = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Set<Map.Entry<String, Integer>> set = ((Map<String, Integer>) list.get(i)).entrySet();
            Iterator<Map.Entry<String, Integer>> itr = set.iterator();
            while (itr.hasNext()) {
                Map.Entry<String, Integer> entry = itr.next();
                states.add(entry.getKey().toLowerCase());
            }
        }
        return states;
    }

    public static List<String> getCityList(Map<String, Map<String, Integer>> map) {
        List<String> cityList = new ArrayList<>();

        for (int i = 0; i < map.size(); i++) {
            Set<Map.Entry<String, Map<String, Integer>>> set = ((Map<String, Map<String, Integer>>) map).entrySet();

            Iterator<Map.Entry<String, Map<String, Integer>>> itr = set.iterator();
            while (itr.hasNext()) {
                Map.Entry<String, Map<String, Integer>> entry = itr.next();
                Map<String, Integer> innerMap = entry.getValue();
                Set<Map.Entry<String, Integer>> innerSet = innerMap.entrySet();

                Iterator<Map.Entry<String, Integer>> innerIterator = innerSet.iterator();
                while (innerIterator.hasNext()) {
                    Map.Entry<String, Integer> innerEntry = innerIterator.next();
                    cityList.add(innerEntry.getKey().toLowerCase());
                }
            }
        }

        return cityList;
    }

    public static boolean isPinCodeValid(String pincode) {
        try {
            int pin = Integer.parseInt(pincode);
            return pin >= 100000 && pin <= 999999;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int getDistrictId(Map<String, Map<String, Integer>> map, String cityName) {

        for (int i = 0; i < map.size(); i++) {
            Set<Map.Entry<String, Map<String, Integer>>> set = ((Map<String, Map<String, Integer>>) map).entrySet();
            Iterator<Map.Entry<String, Map<String, Integer>>> itr = set.iterator();

            while (itr.hasNext()) {
                Map.Entry<String, Map<String, Integer>> entry = itr.next();
                Map<String, Integer> innerMap = new HashMapCaseInsensitive<String, Integer>(entry.getValue());
                if (innerMap.containsKey(cityName)) {
                    return innerMap.get(cityName);
                }
            }
        }

        return 0;
    }
}
