package app.defensivethinking.co.za.smartcitizentrafficlightspotter.models;

import com.google.gson.Gson;

import java.io.Serializable;


public class TrafficLightLocation implements Serializable{

    private static final long serialVersionUID = 1587797602244691958L;
    private double xcoordinates;
    private double ycoordinates;
    private boolean isWorking = false;

    //for the cases where the robbot is at an intersection
    private String street1;
    private String street2;

    public TrafficLightLocation(){
    }

    public TrafficLightLocation(double xCoordinates, double yCoordinates) {
        this.xcoordinates = xCoordinates;
        this.ycoordinates = yCoordinates;
        this.street1 = "";
        this.street2 = "";
    }

    public double getXcoordinates() { return xcoordinates; }

    public void setXcoordinates(double xcoordinates) { this.xcoordinates = xcoordinates; }

    public double getYcoordinates() {
        return ycoordinates;
    }

    public void setYcoordinates(double ycoordinates) {
        this.ycoordinates = ycoordinates;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    @Override
    public String toString() {
        return (new Gson()).toJson(this, TrafficLightLocation.class);
    }

    /**
     * Utility method for converting a traffic-light JSON string to TrafficLight object
     * @param locationJSON
     * @return
     */
    public static TrafficLightLocation asTrafficLight(String locationJSON){
        return (new Gson()).fromJson(locationJSON, TrafficLightLocation.class);
    }


}