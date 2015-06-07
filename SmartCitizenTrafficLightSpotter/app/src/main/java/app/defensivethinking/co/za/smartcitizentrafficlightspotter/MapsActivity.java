package app.defensivethinking.co.za.smartcitizentrafficlightspotter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import app.defensivethinking.co.za.smartcitizentrafficlightspotter.dao.HttpManager;
import app.defensivethinking.co.za.smartcitizentrafficlightspotter.models.TrafficLightLocation;
import app.defensivethinking.co.za.smartcitizentrafficlightspotter.parser.TrafficLightParser;
import app.defensivethinking.co.za.smartcitizentrafficlightspotter.utils.GlobalConstants;

/**
 * Created by Naledi Madlopha on 2015/06/07.
 */

public class MapsActivity extends ActionBarActivity {

    private GoogleMap mMap;
    public List<TrafficLightLocation> trafficLightLocationList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
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

            MarkerOptions markerOptions = new MarkerOptions().position(
                    new LatLng(trafficLightLocationList.get(i).getYcoordinates(),
                            trafficLightLocationList.get(i).getXcoordinates()))
                    .title(
                            "Street 1: " + trafficLightLocationList.get(i).getStreet1() +
                            "Street 2: " + trafficLightLocationList.get(i).getStreet2() +
                            "Updated: " + trafficLightLocationList.get(i).getUpdateTimeStamp() +
                            "Working: " + trafficLightLocationList.get(i).isWorking()
                    );

            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.traffic_light_icon));

            mMap.addMarker(markerOptions); // Add the markers on the map
        }
    }

    public class SetUpMap extends AsyncTask<Void, Void, List<TrafficLightLocation>> {

        @Override
        protected List<TrafficLightLocation> doInBackground(Void... params) {

            try {
                String content = HttpManager.getData(GlobalConstants.LIGHT_JSON);
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
    }
}
