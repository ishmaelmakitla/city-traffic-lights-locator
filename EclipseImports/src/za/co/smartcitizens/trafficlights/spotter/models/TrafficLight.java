package za.co.smartcitizens.trafficlights.spotter.models;

import java.io.Serializable;

import com.google.gson.Gson;

/**
 * This class represents a Traffic Light as spotted by the smart citizen. A traffic light is identified by its location.
 * 
 * @author Ishmael Makitla
 * 2015
 *
 */
public class TrafficLight implements Serializable {
	
	private static final long serialVersionUID = 3689038939854054801L;
	
	private TrafficLightLocation location;	
	private String _id;
	private boolean isWorking = false;
	
	public TrafficLight(){
		//
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
	
	
	public String getId() {
		return _id;
	}

	public void setId(String _id) {
		this._id = _id;
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
