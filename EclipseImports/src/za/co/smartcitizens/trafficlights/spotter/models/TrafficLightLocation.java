package za.co.smartcitizens.trafficlights.spotter.models;

import java.io.Serializable;

import android.util.Log;

import com.google.gson.Gson;


public class TrafficLightLocation implements Serializable{
	
	private static final long serialVersionUID = 1587797602244691958L;
	private String xcoordinates;
	private String ycoordinates;
	
	//for the cases where the robbot is at an intersection
	private String street1;
	private String street2;
	
	public TrafficLightLocation(){
		
	}
	
	public TrafficLightLocation(String xCoordinates, String yCoordinates) {		
		this.xcoordinates = xCoordinates;
		this.ycoordinates = yCoordinates;
		this.street1 = "";
		this.street2 = "";
	}
	
	public String getXcoordinates() {
		return xcoordinates;
	}

	public void setXcoordinates(String xcoordinates) {
		this.xcoordinates = xcoordinates;
	}

	public String getYcoordinates() {
		return ycoordinates;
	}

	public void setYcoordinates(String ycoordinates) {
		this.ycoordinates = ycoordinates;
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
	 * @param trafficLightJSON
	 * @return
	 */
	public static TrafficLightLocation asTrafficLight(String locationJSON){
		return (new Gson()).fromJson(locationJSON, TrafficLightLocation.class);
	}
	
	
	
}
