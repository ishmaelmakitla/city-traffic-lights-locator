package app.defensivethinking.co.za.smartcitizentrafficlightspotter.models;

import com.google.gson.Gson;


/**
 * This data represents the response from the SmartCitizens Server AFTER the traffic light was submitted; 
 * @author Ishmael Makitla, GDG-Pretoria, RHoK-2015
 *
 */
public class TrafficLightDataSubmissionResponse {

	private boolean success;
	private TrafficLight trafficLight;
	
	public TrafficLightDataSubmissionResponse() {
		super();
	}
	
	/**
	 * This is a utility function that converts a JSON string into a Java model representing a Traffic Light
	 * @param trafficLightDataJSON
	 * @return
	 */
	public static TrafficLightDataSubmissionResponse asTrafficLightDataSubmissionResponse(String trafficLightDataJSON){
		return (new Gson()).fromJson(trafficLightDataJSON, TrafficLightDataSubmissionResponse.class);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public TrafficLight getTrafficLight() {
		return trafficLight;
	}

	public void setTrafficLight(TrafficLight trafficLight) {
		this.trafficLight = trafficLight;
	}
	
		
}
