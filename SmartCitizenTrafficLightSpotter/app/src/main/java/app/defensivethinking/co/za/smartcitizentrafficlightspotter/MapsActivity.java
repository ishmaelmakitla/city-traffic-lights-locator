package app.defensivethinking.co.za.smartcitizentrafficlightspotter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import app.defensivethinking.co.za.smartcitizentrafficlightspotter.models.TrafficLightLocation;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    private final String TAG = MapsActivity.this.getClass().getSimpleName();

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
                setUpMap();
            }
        }
    }

    private void setUpMap() {

          List<TrafficLightLocation> trafficLightLocationList = new ArrayList<TrafficLightLocation>();

          TrafficLightLocation trafficLightLocation = new TrafficLightLocation();
          trafficLightLocation.setXcoordinates(-25.988620);
          trafficLightLocation.setYcoordinates(27.528992);
          trafficLightLocation.setWorking(true);

            trafficLightLocationList.add(trafficLightLocation);

          TrafficLightLocation trafficLightLocation0 = new TrafficLightLocation();
          trafficLightLocation0.setXcoordinates(-26.346056);
          trafficLightLocation0.setYcoordinates(26.328735);
          trafficLightLocation0.setWorking(false);

        trafficLightLocationList.add(trafficLightLocation0);

          TrafficLightLocation trafficLightLocation1 = new TrafficLightLocation();
          trafficLightLocation1.setXcoordinates(-25.658278);
          trafficLightLocation1.setYcoordinates(28.056335);
          trafficLightLocation1.setWorking(false);

        trafficLightLocationList.add(trafficLightLocation1);

        addMarkersToMap(trafficLightLocationList);
    }

    private void addMarkersToMap(List<TrafficLightLocation> trafficLightLocationList) {
        mMap.clear();

        for (int i = 0; i < trafficLightLocationList.size(); i++) {

            MarkerOptions markerOptions = new MarkerOptions().position(
                    new LatLng(trafficLightLocationList.get(i).getXcoordinates(),
                            trafficLightLocationList.get(i).getYcoordinates()))
                    .title("tITLE");

            switch (trafficLightLocationList.get(i).isWorking()? 1:0) {
                case 1:
                    // Changing marker icon
                    markerOptions.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    break;
                case 0:
                    // Changing marker icon
                    markerOptions.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    break;
            }

            mMap.addMarker(markerOptions);
        }
    }
}
