package za.co.smartcitizens.trafficlights.spotter.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import za.co.smartcitizens.trafficlights.spotter.models.TrafficLight;
import za.co.smartcitizens.trafficlights.spotter.models.TrafficLightDataSubmissionResponse;
import za.co.smartcitizens.trafficlights.spotter.models.TrafficLightLocation;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * This is a general-purpose utility class.
 * @author Ishmael Makitla
 * 2015
 *
 */
public class TrafficLightSpotterUtils {
	private static final String TRAFFIC_LIGHT_SPOTTER_SERVER_URL ="http://smartcitizen.defensivethinking.co.za/spotters/traffic/lights";
	private static final String TAG = TrafficLightSpotterUtils.class.getSimpleName();
	
	public static TrafficLightLocation getTrafficLightLocation(Context context){
		Log.d(TAG, "getTrafficLightLocation...");
		TrafficLightLocation trafficLightLocation = null;
		
		GPSTracker gps = new GPSTracker(context);
		
		double latitude = 0;
		double longitude = 0;
		if(gps.canGetLocation()){
			Log.d(TAG, "getTrafficLightLocation...canGetLocation");
        	latitude = gps.getLatitude();
        	longitude = gps.getLongitude();    
        	Log.d(TAG, "TrafficLightLocation: (x: "+longitude+", y: "+latitude+")");
        	trafficLightLocation = new TrafficLightLocation(String.valueOf(longitude), String.valueOf(latitude));  
        	Log.d(TAG, trafficLightLocation.toString());
        }
		else{
        	// can't get location
        	// GPS or Network is not enabled
        	// Ask user to enable GPS/network in settings
        	gps.showSettingsAlert();
        	Log.d(TAG, "After Settings Dialog...");
        }			
		
		if(trafficLightLocation == null){ getTrafficLightLocation(context); }
		else{
			
		}
		
		return trafficLightLocation;
	}
	
	/**
	 * This is a utility method to submit the spotted traffic light to the server
	 * @return
	 */
	public static boolean submitTrafficLightSpotting(TrafficLight trafficLight, final Context context){
		Log.i(TAG,"submitTrafficLightSpotting :: Data = "+trafficLight);
		
				
		boolean successful = false;
		RequestQueue queue = Volley.newRequestQueue(context);
				
		try{		
			
			Request<String> trafficLightSubmissionRequest = new TrafficLightMultiPartSubmissionRequest(
				TRAFFIC_LIGHT_SPOTTER_SERVER_URL,
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						try {								
							//problems - alert the user
							Toast.makeText(context, "There was a problem submitting your Spotted Traffic Light.", Toast.LENGTH_LONG).show();
							Log.e(TAG, "Error is "+error.getLocalizedMessage());
						} 
						catch (NullPointerException err) {Log.e(TAG, err.getLocalizedMessage(), err);}
					}
				},
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {								
						Log.d(TAG, "onResponse - Response is "+response);							
						try {
							JSONObject responseJson = new JSONObject(response);	
							Toast.makeText(context, "Traffic Light Spotting Successful. THANK YOU!", Toast.LENGTH_LONG).show();
						}
						catch (JSONException je) {								
							Log.e(TAG, "There was an issue while parsing the response to JSON Object. ", je);
						}
					}
				}, trafficLight);
			
			Log.d(TAG, trafficLightSubmissionRequest.toString());
			
			queue.add(trafficLightSubmissionRequest);
		}
		catch(Exception e){
			//
			Log.e(TAG, "There were some problems while trying to submit the Traffic Light data. ", e);
		}		
		return successful;
	}
	
	/**
	 * 
	 * @param trafficLight
	 * @param context
	 */
	public static void sendTrafficLightSpottingData(TrafficLight trafficLight, final Context context){
		if(trafficLight == null){ return; }
		
		try {
			RequestQueue queue = Volley.newRequestQueue(context);
			JSONObject trafficLightSpottingData = new JSONObject(trafficLight.toString());
			
			TrafficLightLocationSpotRequest trafficLightSpottingRequest = new TrafficLightLocationSpotRequest(TRAFFIC_LIGHT_SPOTTER_SERVER_URL, trafficLightSpottingData,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {	
							//submission response object - parse to our response-model				
							TrafficLightDataSubmissionResponse submissionResponse = TrafficLightDataSubmissionResponse.asTrafficLightDataSubmissionResponse(response.toString());
							boolean success = submissionResponse.isSuccess();
							if(success){
								Log.i(TAG, "Submission Response :: Successful = "+submissionResponse.isSuccess()+", ID = "+submissionResponse.getTrafficLight().getId());
								Toast.makeText(context, "Traffic Light Spotting Successful. THANK YOU!", Toast.LENGTH_LONG).show();	
							}
							else{
								Toast.makeText(context, "Traffic Light Spotting Unsuccessful!. Please try again. Thanks", Toast.LENGTH_LONG).show();	
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							try {
								Toast.makeText(context, "There were some problems while trying to submit the Traffic Light data. Please Try again", Toast.LENGTH_LONG).show();	
								Log.e(TAG, "There were some problems while trying to submit the Traffic Light data. Error is ",error);
							} 
							catch (NullPointerException err) {Log.e(TAG, err.getLocalizedMessage(), err);}
						}
				});					
				queue.add(trafficLightSpottingRequest);
			
		} catch (JSONException e) {
			Log.e(TAG, "There was an issue while parsing the Traffic-Light Data Object to JSON Object. ", e);
		}
	}

}
