package app.defensivethinking.co.za.smartcitizentrafficlightspotter.models;

import com.google.gson.Gson;

import java.io.Serializable;


public class TrafficLightLocation implements Serializable{

    private static final long serialVersionUID = 1587797602244691958L;

    private int _id;
    private int __v;
    private double xcoordinates;
    private double ycoordinates;

    //for the cases where the robot is at an intersection
    private String street1;
    private String street2;
    private String updateTimeStamp;
    private boolean isWorking = false;

    public TrafficLightLocation(){
    }

    public TrafficLightLocation(double xCoordinates, double yCoordinates) {
        this.xcoordinates = xCoordinates;
        this.ycoordinates = yCoordinates;
        this.street1 = "";
        this.street2 = "";
    }

    public int get_id() { return _id; }

    public void set_id(int _id) { this._id = _id; }

    public int get__v() { return __v; }

    public void set__v(int __v) { this.__v = __v; }

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

    public String getUpdateTimeStamp() { return updateTimeStamp; }

    public void setUpdateTimeStamp(String updateTimeStamp) { this.updateTimeStamp = updateTimeStamp; }

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