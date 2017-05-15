package isima.ilyes.mdsos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import info.androidhive.slidingmenu.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MapAndDirection  extends FragmentActivity  implements LocationListener {
	
	static double latitude1;
	static double longitude1;
	private static LatLng Depart = new LatLng(latitude1, longitude1);
	private static final LatLng Arrivee = new LatLng(35.5118676, 11.0476705);

	private GoogleMap map;
	private SupportMapFragment fragment;
	private LatLngBounds latlngBounds;
	private Button bNavigation;
	private Button retur;
	private Polyline newPolyline;
	private boolean isTravelingToParis = false;
	private int width, height;
	
	TextView tvAddress;
	LocationManager locationManager ;
    LocationListener locationListener;
    String provider;
    Location location;
    
    
    AppLocationService appLocationService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		tvAddress=(TextView) findViewById(R.id.tvAddress);
		getSreenDimanstions();
	    fragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
		map = fragment.getMap(); 	
		appLocationService = new AppLocationService(MapAndDirection.this);
	
		retur=(Button) findViewById(R.id.ret);
		retur.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				Intent nextScreen = new Intent(MapAndDirection.this, MainActivity.class);
 				startActivity(nextScreen);
 			}
 		});
		// Changing map type
		//map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		// map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		// map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		// map.setMapType(GoogleMap.MAP_TYPE_NONE);

		// Showing / hiding your current location
		map.setMyLocationEnabled(true);

		// Enable / Disable zooming controls
		map.getUiSettings().setZoomControlsEnabled(true);

		// Enable / Disable my location button
		map.getUiSettings().setMyLocationButtonEnabled(true);

		// Enable / Disable Compass icon
		map.getUiSettings().setCompassEnabled(true);

		// Enable / Disable Rotate gesture
		map.getUiSettings().setRotateGesturesEnabled(true);

		// Enable / Disable zooming functionality
		map.getUiSettings().setZoomGesturesEnabled(true);
		
		 Location gpsLocation = appLocationService
                 .getLocation(LocationManager.GPS_PROVIDER);
         if (gpsLocation != null) {
              latitude1 = gpsLocation.getLatitude();
              longitude1 = gpsLocation.getLongitude();
              String result = "Latitude: " + latitude1 +
                      " Longitude: " + longitude1;
              tvAddress.setText(result);
            
             
         } else {
             showSettingsAlert();
         }
     
         
         
		
        

		bNavigation = (Button) findViewById(R.id.bNavigation);
		bNavigation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!isTravelingToParis)
				{
					isTravelingToParis = true;
					findDirections( latitude1, longitude1,Arrivee.latitude, Arrivee.longitude, GMapV2Direction.MODE_WALKING );
				}
				
			}
		});
	}
	
	private Context getActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onResume() {
		
		super.onResume();
    	latlngBounds = createLatLngBoundsObject(Depart, Arrivee);
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));

	}

	public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
		PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);

		for(int i = 0 ; i < directionPoints.size() ; i++) 
		{          
			rectLine.add(directionPoints.get(i));
		}
		if (newPolyline != null)
		{
			newPolyline.remove();
		}
		newPolyline = map.addPolyline(rectLine);
		if (isTravelingToParis)
		{
			latlngBounds = createLatLngBoundsObject(Depart, Arrivee);
	        
	        CameraPosition cameraPosition = new CameraPosition.Builder().target(
			        new LatLng(latitude1, longitude1)).zoom(14).build();
			  map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		}
		else
		{
			 CameraPosition cameraPosition = new CameraPosition.Builder().target(
				        new LatLng(latitude1, longitude1)).zoom(14).build();
				  map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		}
		
	}
	
	private void getSreenDimanstions()
	{
		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth(); 
		height = display.getHeight(); 
	}
	
	private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation)
	{
		if (firstLocation != null && secondLocation != null)
		{
			LatLngBounds.Builder builder = new LatLngBounds.Builder();    
			builder.include(firstLocation).include(secondLocation);
			
			return builder.build();
		}
		return null;
	}
	
	public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
		map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
		map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
		map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
		map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);
		
		GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
		asyncTask.execute(map);	
	}
	
	 public void showSettingsAlert() {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
	        		MapAndDirection.this);
	        alertDialog.setTitle("SETTINGS");
	        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
	        alertDialog.setPositiveButton("Settings",
	                new DialogInterface.OnClickListener() {
	                    @Override
						public void onClick(DialogInterface dialog, int which) {
	                        Intent intent = new Intent(
	                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                      MapAndDirection.this.startActivity(intent);
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
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}