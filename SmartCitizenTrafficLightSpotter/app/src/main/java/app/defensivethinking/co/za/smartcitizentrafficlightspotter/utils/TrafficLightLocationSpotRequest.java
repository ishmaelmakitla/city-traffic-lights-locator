package app.defensivethinking.co.za.smartcitizentrafficlightspotter.utils;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map.Entry;

public class TrafficLightLocationSpotRequest extends JsonRequest<JSONObject>{
	private static final String TAG = TrafficLightLocationSpotRequest.class.getSimpleName();
	
	//private responseHeaders
	private HashMap<String, String> responseHeaders = new HashMap<String, String>();
	public TrafficLightLocationSpotRequest(String url, String requestBody,Listener<JSONObject> listener, ErrorListener errorListener) {
		super(Method.POST, url, requestBody, listener, errorListener);		
	}
	
	public TrafficLightLocationSpotRequest(String url, JSONObject jsonRequestBody,Listener<JSONObject> listener, ErrorListener errorListener){
		super(Method.POST, url, jsonRequestBody.toString(), listener, errorListener);
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		String data = new String (response.data);
		Log.i(TAG, "My Data"+data);
		JSONObject jsonResponse = null;
		try {
			jsonResponse = new JSONObject(data);
		} catch (JSONException e) {
			Log.e(TAG, "Error at parseNetworkResponse", e);
		}
		getResponseHeader();		
		return Response.success(jsonResponse, getCacheEntry());
	}
	
	public HashMap<String, String> getResponseHeader(){
		
		try {
			Log.d(TAG, "Got "+super.getHeaders().size()+" Headers");
			responseHeaders.putAll(super.getHeaders());
		} catch (AuthFailureError e) {
			Log.e(TAG, "Error at getResponseHeader", e);
		}
		
		StringBuilder builder = new StringBuilder();
		for(Entry<String, String> entry : responseHeaders.entrySet()){
			 builder.append(entry.getKey());
		     builder.append("=");
		     builder.append(entry.getValue());
		     builder.append("\r");
		}
		
		Log.d(TAG, "Headers :: \n"+builder.toString());
       
		return responseHeaders;
	}

}
