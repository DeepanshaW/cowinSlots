package com.deepansha.cowinSlots.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.util.*;
import java.io.*;

public class Test {
    public static void main1(String args[]) {

        try {
            Map<String, ?> statessListMap = new ObjectMapper().readValue(new ClassPathResource("state.json").getFile(),
                    Map.class);
            Map<String, ?> cityListMap = new ObjectMapper().readValue(new ClassPathResource("city.json").getFile(),
                    Map.class);

            List<Map.Entry<String, String>> statessList = (List<Map.Entry<String, String>>) statessListMap
                    .get("states");
            ObjectMapper mapper = new ObjectMapper();

            List<Map<Integer, String>> finalStateMap = new ArrayList<>();
            for (int i = 0; i < statessList.size(); i++) {

                Map<String, String> stateMap = (Map<String, String>) statessList.get(i);
                Map<Integer, String> map = new HashMap();
                map.put(Integer.parseInt(String.valueOf(stateMap.get("stateId"))), stateMap.get("stateName"));
                finalStateMap.add(map);
            }

            Map<String, ?> newStateList = new ObjectMapper()
                    .readValue(new ClassPathResource("stateList.json").getFile(), Map.class);
            List<Map<String, Integer>> slt = (List<Map<String, Integer>>) newStateList.get("states");

            List<Map<String, Map<String, Integer>>> finalCityMapList = new ArrayList<>();
            List<Map.Entry<String, String>> districts = (List<Map.Entry<String, String>>) cityListMap.get("districts");
            int previousStateid = 1;
            Map<String, Map<String, Integer>> previousMap = new HashMap();

            for (int i = 0; i < districts.size(); i++) {

                Map<String, String> stateMap = (Map<String, String>) districts.get(i);
                int stateId = Integer.parseInt(String.valueOf(stateMap.get("stateId")));
                String stateName = getStateNameByDistrictId(slt, stateId);
                // Map<String, Integer> innerMap = getMapForDistrictName(finalCityMapList,
                // stateName);
                Map<String, Integer> innerMap = previousMap.get(stateName);
                if (null == innerMap) {
                    innerMap = new HashMap();
                }

                innerMap.put(stateMap.get("districtName"),
                        Integer.parseInt(String.valueOf(stateMap.get("districtId"))));

                previousMap.put(stateName, innerMap);

            }

            mapper.writeValue(new File(
                            "C:\\Documents\\cowinSlots\\\src\\main\\java\\com\\deepansha\\service\\chatbot\\test.json"),
                    previousMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Map<String, Integer> getMapForDistrictName(List<Map<String, Map<String, Integer>>> finalCityMap,
                                                              String districtName) {
        for (int i = 0; i < finalCityMap.size(); i++) {
            Map<String, Map<String, Integer>> cityMap = finalCityMap.get(i);
            Set<Map.Entry<String, Map<String, Integer>>> set = cityMap.entrySet();

            Iterator<Map.Entry<String, Map<String, Integer>>> itr = set.iterator();
            while (itr.hasNext()) {
                Map.Entry<String, Map<String, Integer>> entry = itr.next();
                if (entry.getKey().equals(districtName)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    private static String getStateNameByDistrictId(List<Map<String, Integer>> stateListMap, int stateId) {
        for (int i = 0; i < stateListMap.size(); i++) {
            Map<String, Integer> stateMap = (Map<String, Integer>) stateListMap.get(i);
            Set<Map.Entry<String, Integer>> set = stateMap.entrySet();

            Iterator<Map.Entry<String, Integer>> itr = set.iterator();
            while (itr.hasNext()) {
                Map.Entry<String, Integer> entry = itr.next();
                if (entry.getValue() == stateId) {
                    return entry.getKey();
                }
            }
        }

        return null;
    }
}
