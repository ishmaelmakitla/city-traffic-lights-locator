package za.co.smartcitizens.trafficlights.spotter;

import za.co.smartcitizens.trafficlights.spotter.models.TrafficLight;
import za.co.smartcitizens.trafficlights.spotter.models.TrafficLightLocation;
import za.co.smartcitizens.trafficlights.spotter.utils.TrafficLightSpotterUtils;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * This is the main activity that the user uses to report a spotted traffic light in the city/township
 * 
 * @author Ishmael Makitla - Smart Citizens, South Africa
 *
 */
public class MainSpotterActivity extends Activity {
	private static final String TAG = MainSpotterActivity.class.getSimpleName();
	Button spotButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_spotter);
		//initialize the button
		spotButton = (Button)findViewById(R.id.btnSpot);
		spotButton.setOnClickListener(buttonClickListener);
	}
	
	//This is the Click-Listener
	private OnClickListener buttonClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//here we get the current GPS coordinates and then build the traffic light spotting		
			Log.i(TAG, "User Attempts to Submit Traffic-Light Spotting");
			buildTrafficLightSpotting();
		}
	};

	/**
	 * This function is used to construct the traffic light spotting report
	 * 
	 */
	private void buildTrafficLightSpotting(){
		
		TrafficLightLocation location = TrafficLightSpotterUtils.getTrafficLightLocation(this);
		if(location != null){
			TrafficLight trafficLight = new TrafficLight(location, true);
			Log.i(TAG, location.toString());
			TrafficLightSpotterUtils.submitTrafficLightSpotting(trafficLight, this);
		}
	}
}
