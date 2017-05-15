package isima.ilyes.mdsos;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import info.androidhive.slidingmenu.R;
import android.app.AlertDialog;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class DmanderurgenceFragment extends Fragment {
	private ListView mDrawerList;
	AppLocationService appLocationService;
	
	double latitude;
	double longitude;
	String result;
	String locationAddress;

    String[] itemname ={
    "Accident",
    "Accouchement",
    "Agression",
    "Incendie",
    "Violoence",
    "Malaise",
    "Vol",
    "Gaz",
    "Explosition"
    };
    
    String[] agentname ={
    	    "ambulatoire",
    	    "ambulatoire",
    	    "policier",
    	    "pompier",
    	    "policier",
    	    "ambulatoire",
    	    "policier",
    	    "ambulatoire",
    	    "pompier"
    	    };

    Integer[] imgid={
    R.drawable.accident,
    R.drawable.pic2,
    R.drawable.pic3,
    R.drawable.fire,
    R.drawable.pic5,
    R.drawable.blessure,
    R.drawable.vol,
    R.drawable.pic8,
    R.drawable.pic9,
    };
    
    String Slecteditem;
    String Slectedagent;
	public DmanderurgenceFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_demander_urgence, container, false);
         
        appLocationService = new AppLocationService(getActivity());
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
        CustomListAdapter adapter=new CustomListAdapter(getActivity(), itemname, imgid,agentname);
        mDrawerList = (ListView) rootView.findViewById(R.id.list_slidermenu);
        mDrawerList.setAdapter(adapter);
        
       mDrawerList.setOnItemClickListener(new OnItemClickListener() {
        	 
        	 @Override
        	 public void onItemClick(AdapterView<?> parent, View view,
        	 int position, long id) {
        	 // TODO Auto-generated method stub
        		
        	  Slecteditem= itemname[+position];
        	  Slectedagent =agentname[+position];
        	 Toast.makeText(getActivity(), Slecteditem, Toast.LENGTH_SHORT).show();
        	 envoyer(Slecteditem,Slectedagent);
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
            
        }
}
    
    
    private void envoyer(String slecteditem,String agent) {
    	InputStream is = null;
    	
    	
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email","ilyes"));
		nameValuePairs.add(new BasicNameValuePair("agent",agent));
   		nameValuePairs.add(new BasicNameValuePair("danger",slecteditem));
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

		
	}
    
    
