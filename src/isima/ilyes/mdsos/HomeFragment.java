package isima.ilyes.mdsos;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import info.androidhive.slidingmenu.*;
import isima.ilyes.mdsos.loginandregistration.helper.SQLiteHandler;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	
	ImageButton btnNum;
	ImageView sos;
	TextView tvAddress;
	TextView tvLongeLang;
	
	AppLocationService appLocationService;
	
	double latitude;
	double longitude;
	String result;
	String locationAddress;
	private ProgressDialog pDialog;
	private SQLiteHandler db;
	
	String strtext;
	ImageButton btnServ;
	public HomeFragment(){}
	View rootView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
       
         rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        
        
        tvAddress = (TextView) rootView.findViewById(R.id.tvAddress);
        
        
        btnServ=(ImageButton)rootView.findViewById(R.id.btnServ);
		btnServ.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Fragment fragment = new ServicesanteFragment();

				FragmentManager fragmentManager = getFragmentManager();

				fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
				
			}
		}); 
        
        btnNum=(ImageButton) rootView.findViewById(R.id.btnNume);
        btnNum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Bundle arguments = new Bundle();
			    arguments.putString("adresse", locationAddress);
				Fragment fragment = new NumerosFragment();
				fragment.setArguments(arguments);;
				FragmentManager fragmentManager = getFragmentManager();

				fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
				
			}
		}); 
        
        sos=(ImageView) rootView.findViewById(R.id.sos);
        tvLongeLang = (TextView) rootView.findViewById(R.id.tvLongeLang);
        appLocationService = new AppLocationService(getActivity());

         strtext = getArguments().getString("a");
        
        db = new SQLiteHandler(getActivity());
        
        HashMap<String, String> user = db.getUserDetails();
       
        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
 
        
        
        StrictMode.ThreadPolicy policy = 
				new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
        
        Location location = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);
        
        	longelang();
        
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            LocationAddress.getAddressFromLocation(latitude, longitude,
                    getActivity(), new GeocoderHandler());
        } else {
            showSettingsAlert();
        }
        
        
        sos.setOnClickListener(new OnClickListener() {
        	InputStream is = null;
			@Override
			public void onClick(View v) {
				
				
				
				String danger="danger extreme";
				String agent="tout";
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("email",strtext));
				nameValuePairs.add(new BasicNameValuePair("agent",agent));
    	   		nameValuePairs.add(new BasicNameValuePair("danger",danger));
    	   		nameValuePairs.add(new BasicNameValuePair("latitude",Double.toString(latitude)));
    	   		nameValuePairs.add(new BasicNameValuePair("longitude",Double.toString(longitude)));
    	   		nameValuePairs.add(new BasicNameValuePair("adresse",locationAddress));
    	   		

       		try{
       			HttpClient httpclient = new DefaultHttpClient();
       			HttpPost httpPost = new HttpPost("http://192.168.100.1/declare.php");
       			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
       			HttpResponse response = httpclient.execute(httpPost);
       			HttpEntity entity = response.getEntity();
       			is = entity.getContent();
       			
       			
       		 
       		} catch (ClientProtocolException e) {
    				Log.e("ClientProtocol","log_tag");
    				e.printStackTrace();
    			}
       		catch(Exception e){
       			Log.e("log_tag", "Error in http connection " + e.toString());
       		}
				
			}
			
		});
		
			 
        
        return rootView;
    }
	
	
	 public void showSettingsAlert() {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
	                getActivity());
	        alertDialog.setTitle("SETTINGS");
	        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
	        alertDialog.setPositiveButton("Settings",
	                new DialogInterface.OnClickListener() {
	                    @Override
						public void onClick(DialogInterface dialog, int which) {
	                        Intent intent = new Intent(
	                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                        getActivity().startActivity(intent);
	                    }
	                });
	        alertDialog.setNegativeButton("Cancel",
	                new DialogInterface.OnClickListener() {
	                    @Override
						public void onClick(DialogInterface dialog, int which) {
	                        dialog.cancel();
	                    }
	                });
	        alertDialog.show();
	    }

	 void longelang(){
		 Location gpsLocation = appLocationService
                 .getLocation(LocationManager.GPS_PROVIDER);
         if (gpsLocation != null) {
              latitude = gpsLocation.getLatitude();
              longitude = gpsLocation.getLongitude();
              result = "Latitude:   " + gpsLocation.getLatitude() +
                     "\n Longitude: " + gpsLocation.getLongitude();
             tvLongeLang.setText(result);
         } else {
             showSettingsAlert();
         }
	 }
	    private class GeocoderHandler extends Handler {
	        @Override
	        public void handleMessage(Message message) {
	          
	            switch (message.what) {
	                case 1:
	                    Bundle bundle = message.getData();
	                    locationAddress = bundle.getString("address");
	                    break;
	                default:
	                    locationAddress = null;
	            }
	            tvAddress.setText(locationAddress);
	        }
	    }
	    
	    
	    private void showDialog() {
	        if (!pDialog.isShowing())
	            pDialog.show();
	    }
	 
	    private void hideDialog() {
	        if (pDialog.isShowing())
	            pDialog.dismiss();
	    }
	    
	 
	    
}
