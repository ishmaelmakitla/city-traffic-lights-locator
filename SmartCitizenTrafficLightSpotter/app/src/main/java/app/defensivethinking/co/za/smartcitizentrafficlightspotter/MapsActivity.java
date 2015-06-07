package app.defensivethinking.co.za.smartcitizentrafficlightspotter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import app.defensivethinking.co.za.smartcitizentrafficlightspotter.dao.HttpManager;
import app.defensivethinking.co.za.smartcitizentrafficlightspotter.models.TrafficLightLocation;
import app.defensivethinking.co.za.smartcitizentrafficlightspotter.parser.TrafficLightParser;
import app.defensivethinking.co.za.smartcitizentrafficlightspotter.utils.mGlobalConstants;

/**
 * Created by Naledi Madlopha on 2015/06/07.
 */

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    public List<TrafficLightLocation> trafficLightLocationList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                new SetUpMap().execute();
            }
        }
    }

    private void addMarkersToMap(List<TrafficLightLocation> trafficLightLocationList) {
        mMap.clear();

        for (int i = 0; i < trafficLightLocationList.size(); i++) {

            // TODO: Fix the location title
            MarkerOptions markerOptions = new MarkerOptions().position(
                    new LatLng(trafficLightLocationList.get(i).getYcoordinates(),
                            trafficLightLocationList.get(i).getXcoordinates()))
                    .title("tITLE");

            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.traffic_light_icon));

            mMap.addMarker(markerOptions); // Add the markers on the map
        }
    }

    public class SetUpMap extends AsyncTask<Void, Void, List<TrafficLightLocation>> {

        @Override
        protected List<TrafficLightLocation> doInBackground(Void... params) {

            try {
                String content = HttpManager.getData(mGlobalConstants.LIGHT_JSON);
                trafficLightLocationList = TrafficLightParser.parseFeed(content);

                addMarkersToMap(trafficLightLocationList);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return trafficLightLocationList;
        }

        @Override
        protected void onPostExecute(List<TrafficLightLocation> result) {
            addMarkersToMap(result);
        }

        //          List<TrafficLightLocation> trafficLightLocationList = new ArrayList<TrafficLightLocation>();
//
//          TrafficLightLocation trafficLightLocation = new TrafficLightLocation();
//          trafficLightLocation.setXcoordinates(27.528992);
//          trafficLightLocation.setYcoordinates(-25.988620);
//          trafficLightLocation.setWorking(true);
//
//            trafficLightLocationList.add(trafficLightLocation);
//
//          TrafficLightLocation trafficLightLocation0 = new TrafficLightLocation();
//          trafficLightLocation0.setXcoordinates(26.328735);
//          trafficLightLocation0.setYcoordinates(-26.346056);
//          trafficLightLocation0.setWorking(false);
//
//        trafficLightLocationList.add(trafficLightLocation0);
//
//          TrafficLightLocation trafficLightLocation1 = new TrafficLightLocation();
//          trafficLightLocation1.setXcoordinates(28.056335);
//          trafficLightLocation1.setYcoordinates(-25.658278);
//          trafficLightLocation1.setWorking(false);
//
//        trafficLightLocationList.add(trafficLightLocation1);
//
//        addMarkersToMap(trafficLightLocationList);
    }
}
