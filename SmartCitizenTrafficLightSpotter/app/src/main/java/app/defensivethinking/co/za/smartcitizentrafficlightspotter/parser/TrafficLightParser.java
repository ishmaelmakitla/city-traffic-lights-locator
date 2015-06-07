package app.defensivethinking.co.za.smartcitizentrafficlightspotter.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.defensivethinking.co.za.smartcitizentrafficlightspotter.models.TrafficLightLocation;

/**
 * Created by Naledi Madlopha on 2015/06/07.
 */
public class TrafficLightParser {

    public static List<TrafficLightLocation> parseFeed(String content) {

        try {
            JSONArray jsonArray = new JSONArray(content);
            ArrayList<TrafficLightLocation> trafficLightLocationList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                TrafficLightLocation trafficLightLocation = new TrafficLightLocation();

                trafficLightLocation.setXcoordinates(jsonObject.getDouble("x"));
                trafficLightLocation.setYcoordinates(jsonObject.getDouble("y"));
                trafficLightLocation.setWorking(jsonObject.getBoolean("working"));

                trafficLightLocationList.add(trafficLightLocation);
            }

            return trafficLightLocationList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
