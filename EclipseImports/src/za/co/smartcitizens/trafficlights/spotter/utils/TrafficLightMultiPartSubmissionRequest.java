package za.co.smartcitizens.trafficlights.spotter.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder; 
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import za.co.smartcitizens.trafficlights.spotter.models.TrafficLight;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog; 

public class TrafficLightMultiPartSubmissionRequest extends Request<String> {
    	
	private static final String TAG = TrafficLightMultiPartSubmissionRequest.class.getSimpleName();
	
	private MultipartEntityBuilder entity =  MultipartEntityBuilder.create();
	HttpEntity multiPartEntity; 
    //We are sending the traffic-light JSON data in a multi-part HTTP-Request to avoid having to send it like a file via upload
    private static final String DATA_PART = "trafficLight";
    //this is the actual Traffic-Light pyalod
    private TrafficLight mTrafficLight;
    
    private  Response.Listener<String> mListener;
    Response.ErrorListener errorListener;
           
    public TrafficLightMultiPartSubmissionRequest(int method, String url,ErrorListener listener) {
		super(method, url, listener);		
	}

    public TrafficLightMultiPartSubmissionRequest(String url, Response.ErrorListener errorListener, Response.Listener<String> listener, TrafficLight trafficLight)
    {
        super(Method.POST, url, errorListener);
        this.errorListener = errorListener;
        this.mListener = listener;
        this.mTrafficLight = trafficLight;
        buildMultipartEntity();
    }

    /**
     * Helper method to build the multi-part entity
     */
    private void buildMultipartEntity()
    {   
    	if(mTrafficLight == null) {return;}
    
        try
        {   //check that we have data to send        	
        	entity.addPart(DATA_PART, new StringBody(mTrafficLight.toString(), ContentType.TEXT_PLAIN)); 
            entity.setBoundary("-------------------");            
            multiPartEntity = entity.build();          
        }
        catch (Exception e)
        {
            VolleyLog.e("UnsupportedEncodingException");
        }
    }

    @Override
    public String getBodyContentType()
    {  String entityString = multiPartEntity.getContentType().getValue();
       VolleyLog.v(TAG, entityString);
    
       return entityString;
    }

    @Override
    public byte[] getBody() throws AuthFailureError
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {  
        	multiPartEntity.writeTo(bos);            
        }
        catch (IOException e)
        {  e.printStackTrace();
            VolleyLog.e("IOException writing to ByteArrayOutputStream", e);
        }
        return bos.toByteArray();
   }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response)
    {   String serverResponse = new String(response.data);
        return Response.success(serverResponse, getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response)
    {
        mListener.onResponse(response);
    }
    
        
    @Override
	public void deliverError(VolleyError error) {
    	errorListener.onErrorResponse(error);    	
	}

	@Override
	protected VolleyError parseNetworkError(VolleyError volleyError) {
    	Log.e(TAG, "Volley Error", volleyError);
		return super.parseNetworkError(volleyError);
	}
}
