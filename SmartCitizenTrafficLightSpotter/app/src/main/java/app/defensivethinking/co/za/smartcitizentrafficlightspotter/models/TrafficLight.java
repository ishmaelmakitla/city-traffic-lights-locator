package app.defensivethinking.co.za.smartcitizentrafficlightspotter.models;

import java.io.Serializable;
import com.google.gson.Gson;

public class TrafficLight implements Serializable {

    private static final long serialVersionUID = 3689038939854054801L;

    private TrafficLightLocation location;
    private boolean isWorking = false;

    public TrafficLight(){
        
    }

    public TrafficLight(TrafficLightLocation location, boolean isWorking) {
        super();
        this.location = location;
        this.isWorking = isWorking;
    }

    public TrafficLightLocation getLocation() {
        return location;
    }

    public void setLocation(TrafficLightLocation location) {
        this.location = location;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    @Override
    public String toString() {
        return (new Gson()).toJson(this, TrafficLight.class);
    }

    /**
     * Utility method for converting a traffic-light JSON string to TrafficLight object
     * @param trafficLightJSON
     * @return
     */
    public static TrafficLight asTrafficLight(String trafficLightJSON){
        return (new Gson()).fromJson(trafficLightJSON, TrafficLight.class);
    }



}

