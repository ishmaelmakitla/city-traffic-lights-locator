package app.defensivethinking.co.za.smartcitizentrafficlightspotter.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

/**
 * Created by Profusion on 2015-06-06.
 */
public class TrafficLightSpotterUtils {
    private static final String TRAFFIC_LIGHT_SPOTTER_SERVER_URL = "http://smartcitizen.defensivethinking.co.za/spotters/traffic/lights";  //http://localhost:3000/  //"http://smartcitizen.defensivethinking.co.za/spotters/traffic/lights";
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
                                Log.e(TAG, "Error is " + error.getLocalizedMessage());
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
}
