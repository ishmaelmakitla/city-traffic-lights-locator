package app.defensivethinking.co.za.smartcitizentrafficlightspotter.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import app.defensivethinking.co.za.smartcitizentrafficlightspotter.R;
import app.defensivethinking.co.za.smartcitizentrafficlightspotter.models.TrafficLight;
import app.defensivethinking.co.za.smartcitizentrafficlightspotter.models.TrafficLightLocation;

/**
 * Created by Profusion on 2015-06-06.
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
            trafficLightLocation = new TrafficLightLocation(longitude, latitude);
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
                            app.defensivethinking.co.za.smartcitizentrafficlightspotter.models.TrafficLightDataSubmissionResponse submissionResponse = app.defensivethinking.co.za.smartcitizentrafficlightspotter.models.TrafficLightDataSubmissionResponse.asTrafficLightDataSubmissionResponse(response.toString());
                            boolean success = submissionResponse.isSuccess();
                            if(success){
                                Log.i(TAG, "Submission Response :: Successful = " + submissionResponse.isSuccess() + ", ID = " + submissionResponse.getTrafficLight().getId());
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
                        Toast.makeText(context, context.getResources().getString(R.string.traffic_submit_fail) ,Toast.LENGTH_LONG ).show();
                    }
                    catch (NullPointerException err) {Log.e(TAG, err.getLocalizedMessage(), err);}
                }
            });
            queue.add(trafficLightSpottingRequest);

        } catch (JSONException e) {
            Log.e(TAG, "There was an issue while parsing the Traffic-Light Data Object to JSON Object. ", e);
            Toast.makeText(context, context.getResources().getString(R.string.traffic_submit_fail) ,Toast.LENGTH_LONG ).show();
        }
    }

}
