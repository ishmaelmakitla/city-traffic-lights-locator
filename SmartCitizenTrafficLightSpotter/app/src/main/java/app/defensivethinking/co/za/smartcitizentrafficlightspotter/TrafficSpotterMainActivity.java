package app.defensivethinking.co.za.smartcitizentrafficlightspotter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import app.defensivethinking.co.za.smartcitizentrafficlightspotter.models.TrafficLight;
import app.defensivethinking.co.za.smartcitizentrafficlightspotter.models.TrafficLightLocation;
import app.defensivethinking.co.za.smartcitizentrafficlightspotter.utils.TrafficLightSpotterUtils;

public class TrafficSpotterMainActivity extends ActionBarActivity {

    private static String TAG = TrafficSpotterMainActivity.class.getSimpleName();
    Context context;
    Button spotButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_spotter_main);
        context = getApplicationContext();


        spotButton = (Button)findViewById(R.id.btnSpot);
        spotButton.setOnClickListener(buttonClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_traffic_spotter_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
        //}

        return super.onOptionsItemSelected(item);
    }


    //This is the Click-Listener
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {

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

        TrafficLightLocation location = TrafficLightSpotterUtils.getTrafficLightLocation(context);

        if(location != null) {
            TrafficLight trafficLight = new TrafficLight(location, true);
            Log.i(TAG, location.toString());
            TrafficLightSpotterUtils.sendTrafficLightSpottingData(trafficLight,context);
        }
        else
        {
           Toast.makeText(context , getResources().getString(R.string.enabled_location) , Toast.LENGTH_LONG ).show();
        }
    }

}
