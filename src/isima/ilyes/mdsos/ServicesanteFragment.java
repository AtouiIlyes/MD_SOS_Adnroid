package isima.ilyes.mdsos;

import java.io.BufferedReader;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.R.color;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;


import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;


import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;





@SuppressLint("ResourceAsColor")
public class ServicesanteFragment extends Fragment implements LocationListener, OnItemSelectedListener {

	public ServicesanteFragment(){}
	
	View rootView ;
	private GoogleMap googleMap;
	LocationManager locationManager ;
    LocationListener locationListener;
    String provider;
    Location location;
    SlidingDrawer drawer;
    ListView listView;
    
    Button btn1;
    Button btn2;
    Button btn3;
    
    MarkerOptions m1;
    
   
    RelativeLayout r1;
    RelativeLayout r2;
    RelativeLayout r3;
    RelativeLayout r4;
    RelativeLayout r5;
    RelativeLayout r6;
    ImageView ra;
    
    LinearLayout ln;
    
    Spinner sp;
    
    Marker m;
    
    ArrayList<LatLng> markerPoints;
	TextView tvDistanceDuration;
   
    double latitude []={35.5118676,35.5097411};
	double longitude []={11.0476705,11.0327811};
	 String addrs [] ={"police","hopitale"};
	 
	 
	 double longpharmacie []={35.5088788,35.5048960,35.5007669,35.5136723,35.5049737,35.5027036,35.5271530};
	 double latpharmacie []={11.0528555,11.0641075,11.0486146,11.0428737,11.0481283,11.0698012,11.0331807};
	 String descpharmacie [] ={"Avenue Habib Bourguba prés de boulangerie Sfar","Avenue Habib Bourguba prés de Magazin generale",
			"Avenue Ali Balhwane prés de centre de police Ezzahra","Avenue Habib Bourguba prés de Mosque hiboun",
			"Rue andalos prés de Café Jbali","Rue Borj Othmani prés de Marché centrale","prés de Clinique Rahma"};
	 
	 double longpolice []={35.5118676,35.5076635,35.5011090,35.5068603,35.5037213};
	 double latpolice []={11.0476705,11.0510528,11.0485183,11.0491153,11.0395626};
	 String descpolice [] ={"Avenue Habib Bourguba Hiboune","Avenue du 2 Mars 1934 prés de Carrefoure",
			 "Avenue Ali Balhwane Ezzahra","Avenue Ali Balhwane Rawdha","Avenue Aljomhouriya"};
	 
	 double longclinique []={35.5271340,35.4994327};
	 double latclinique []={11.0322831,11.0583757};
	 String descclinique [] ={"Clinique Rahma pres de foyée universitaire zonne tourisrique","Avenue Taib El Mhiri Ezzwila "};
	 
	 double longhopitale []={35.4991706,35.5099354};
	 double lathopitale []={11.0585673,11.0327337};
	 String deschopitale [] ={"hôpital d'enfant Avenue Taib Mhiri Ezzwila","hôpital universitaire Tahar Sfar "}; 
	 

	 double longprotection []={35.5134744};
	 double latprotection  []={11.0418093};
	 String descprotection [] ={"Protection Civile prés de la Cimétiere hiboune"};
	 
	 double longdispensaire []={35.5178889,35.5006315,35.5066601,35.4911313,35.5072728};
	 double latdispensaire  []={11.0258753,11.0478507,11.0280120,11.0479964,11.0779519};
	 String descdispensaire [] ={"Dispensaire hiboune rue Khairedinne bacha","Dispensaire  rue de la mecque Ezzwila",
			"Avenue de l'environnement Borj Arif","Rue Ezzgana","Rue Sidi Jaber Borj Rass" };
			 
	 
	double latitudeMin=latitude [0];
	double longitudeMin=longitude [0];
	
	static double latitude1;
	static double longitude1;
	private static LatLng Depart = new LatLng(latitude1, longitude1);
	private static final LatLng Arrivee = new LatLng(35.5118676, 11.0476705);
	private static final LatLng FRANKFURT = new LatLng(35.5174392, 11.0379716);
	private Polyline newPolyline;
	private boolean isTravelingToParis = false;
	private int width, height;
	private LatLngBounds latlngBounds;
	TextView tvAddress;
	
	AppLocationService appLocationService;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
         rootView = inflater.inflate(R.layout.fragment_sevices_plusproches, container, false);
    	
         tvAddress=(TextView) rootView.findViewById(R.id.tvAddress);
         
         appLocationService = new AppLocationService(getActivity());
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
         
         drawer =((SlidingDrawer)rootView.findViewById(R.id.drawer));
         btn1 =((Button) rootView.findViewById(R.id.btn1));
         btn2 =((Button) rootView.findViewById(R.id.btn2));
         r1=((RelativeLayout)rootView.findViewById(R.id.r1));
         r2=((RelativeLayout)rootView.findViewById(R.id.r2));
         r3=((RelativeLayout)rootView.findViewById(R.id.r3));
         r4=((RelativeLayout)rootView.findViewById(R.id.r4));
         r5=((RelativeLayout)rootView.findViewById(R.id.r5));
         r6=((RelativeLayout)rootView.findViewById(R.id.r6));
         
         ra=((ImageView)rootView.findViewById(R.id.ra));
         
         tvDistanceDuration = (TextView) rootView.findViewById(R.id.tv_distance_time);
         markerPoints = new ArrayList<LatLng>();
         
          sp = (Spinner) rootView.findViewById(R.id.spinner);
         
         
         ArrayList<String> List = new ArrayList<String>();
         List.add("TYPE NORMAL");
         List.add("TYPE HYBRID");
         List.add("TYPE SATELLITE");
         List.add("TYPE TERRAIN");
         List.add("TYPE NONE");
        
         ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rootView.getContext(),
                 android.R.layout.simple_spinner_dropdown_item,List);
       
                      // Set the Adapter
       sp.setAdapter(arrayAdapter);
       sp.setOnTouchListener(new OnTouchListener() {

           @Override
           public boolean onTouch(View v, MotionEvent event) {
               sp.setBackgroundResource(R.drawable.myspinner_selector);
               
               return false;
           }
       });
        
       sp.setOnItemSelectedListener(this);
            
       // Google Map
    	
        	
    			// Loading map
    			initilizeMap();
    			
    			// Changing map type
    			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    			//googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    			// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    			// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    			// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

    			// Showing / hiding your current location
    			googleMap.setMyLocationEnabled(true);

    			// Enable / Disable zooming controls
    			googleMap.getUiSettings().setZoomControlsEnabled(true);

    			// Enable / Disable my location button
    			googleMap.getUiSettings().setMyLocationButtonEnabled(false);

    			// Enable / Disable Compass icon
    			googleMap.getUiSettings().setCompassEnabled(true);

    			// Enable / Disable Rotate gesture
    			googleMap.getUiSettings().setRotateGesturesEnabled(true);

    			// Enable / Disable zooming functionality
    			googleMap.getUiSettings().setZoomGesturesEnabled(true);
    			
    			
    			
    			
    			locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);        
		        
		        // Creating an empty criteria object
		        Criteria criteria = new Criteria();
		        
		        // Getting the name of the provider that meets the criteria
		        provider = locationManager.getBestProvider(criteria, false);
    			
		        if(provider!=null && !provider.equals("")){
		        	
				       // Get the location from the given provider 
				             location = locationManager.getLastKnownLocation(provider);
				                        
				            
				             onMyLocationChange();
				            
				            					                 
				                 ra.setOnClickListener(new View.OnClickListener() {
				         			
				         			@Override
				         			public void onClick(View v) {
				         				Intent nextScreen = new Intent(getActivity(), MapAndDirection.class);
				         				startActivity(nextScreen);
				         			}
				         		});
				         	}
				                
        	
    			
    			r1.setOnClickListener(new OnClickListener() {
					
				
					@Override
					public void onClick(View v) {
						
						
						r1.setBackgroundResource(R.drawable.bg_relativelinear2);
						r2.setBackgroundResource(R.drawable.bg_relativelinear);
						r3.setBackgroundResource(R.drawable.bg_relativelinear);
						r4.setBackgroundResource(R.drawable.bg_relativelinear);
						r5.setBackgroundResource(R.drawable.bg_relativelinear);
						r6.setBackgroundResource(R.drawable.bg_relativelinear);
						
						googleMap.clear();
						onMyLocationChange();
						 for (int i = 0; i < longpharmacie.length; i++) {
						
							 
							 Marker m= googleMap.addMarker(new MarkerOptions().position(new LatLng(longpharmacie[i], latpharmacie[i]))
								.title("Pharmacie").snippet(descpharmacie[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker5))
		                          .alpha(0.7f));
							 
							 m.showInfoWindow();
							 
							 Marker marker = googleMap.addMarker(new MarkerOptions()
		                     .position(new LatLng(35.5030926,11.0692867))
		                     .title("Pharmacie de nuit")
		                     .snippet("Rue Borj Othmani prés de Marché centrale")
		                     .icon(BitmapDescriptorFactory
		                     .fromResource(R.drawable.marker2)));
						  marker.showInfoWindow();
						  
						
							 
							 CircleOptions co =new CircleOptions();
							    co.center(new LatLng(longpharmacie[i], latpharmacie[i]));
							    co.radius(10);
							    co.strokeColor(color.jaun);
							    co.strokeWidth(5);
							    co.fillColor(color.jaun);
							    googleMap.addCircle(co);
							    
							    CircleOptions co2 =new CircleOptions();
							    co2.center(new LatLng(longpharmacie[i], latpharmacie[i]));
							    co2.radius(40);
							    co2.strokeColor(color.jaun);
							    co2.strokeWidth(3);
							    co2.fillColor(R.color.jaun);
							    googleMap.addCircle(co2);
							 
							 CameraPosition cameraPosition = new CameraPosition.Builder().target(
								        new LatLng(35.5041923, 11.0531953)).zoom(14).build();
								  googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
								  
								  drawer.close();
								 
						 }
						 
					}
				    				
						
					
				});
    			
    			
    			r2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						r2.setBackgroundResource(R.drawable.bg_relativelinear2);
						r1.setBackgroundResource(R.drawable.bg_relativelinear);
						r3.setBackgroundResource(R.drawable.bg_relativelinear);
						r4.setBackgroundResource(R.drawable.bg_relativelinear);
						r5.setBackgroundResource(R.drawable.bg_relativelinear);
						r6.setBackgroundResource(R.drawable.bg_relativelinear);
						googleMap.clear();
						onMyLocationChange();
						
						 for (int i = 0; i < longpolice.length; i++) {
						
							 MarkerOptions m1 =new MarkerOptions().position(new LatLng(longpolice[i], latpolice[i]))
								.title("Police").snippet(descpolice[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2))
		                          .alpha(0.7f);
							 googleMap.addMarker(m1);
							 
							 CircleOptions co =new CircleOptions();
							    co.center(new LatLng(longpolice[i], latpolice[i]));
							    co.radius(10);
							    co.strokeColor(color.jaun);
							    co.strokeWidth(5);
							    co.fillColor(color.jaun);
							    googleMap.addCircle(co);
							    
							    CircleOptions co2 =new CircleOptions();
							    co2.center(new LatLng(longpolice[i], latpolice[i]));
							    co2.radius(40);
							    co2.strokeColor(color.jaun);
							    co2.strokeWidth(3);
							    co2.fillColor(R.color.jaun);
							    googleMap.addCircle(co2);
							 
							 CameraPosition cameraPosition = new CameraPosition.Builder().target(
								        new LatLng(35.5041923, 11.0531953)).zoom(14).build();
								  googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
						
								  drawer.close();
					}
					}});
    			
    			r3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						r3.setBackgroundResource(R.drawable.bg_relativelinear2);
						r1.setBackgroundResource(R.drawable.bg_relativelinear);
						r2.setBackgroundResource(R.drawable.bg_relativelinear);
						r4.setBackgroundResource(R.drawable.bg_relativelinear);
						r5.setBackgroundResource(R.drawable.bg_relativelinear);
						r5.setBackgroundResource(R.drawable.bg_relativelinear);
						r6.setBackgroundResource(R.drawable.bg_relativelinear);
						googleMap.clear();
						onMyLocationChange();
						
						 for (int i = 0; i < longclinique.length; i++) {
						
							 MarkerOptions m1 =new MarkerOptions().position(new LatLng(longclinique[i], latclinique[i]))
								.title("Clinique").snippet(descclinique[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker3))
		                          .alpha(0.7f);
							 googleMap.addMarker(m1);
							 
							 CircleOptions co =new CircleOptions();
							    co.center(new LatLng(longclinique[i], latclinique[i]));
							    co.radius(10);
							    co.strokeColor(color.jaun);
							    co.strokeWidth(5);
							    co.fillColor(color.jaun);
							    googleMap.addCircle(co);
							    
							    CircleOptions co2 =new CircleOptions();
							    co2.center(new LatLng(longclinique[i], latclinique[i]));
							    co2.radius(40);
							    co2.strokeColor(color.jaun);
							    co2.strokeWidth(3);
							    co2.fillColor(R.color.jaun);
							    googleMap.addCircle(co2);
							 
							 CameraPosition cameraPosition = new CameraPosition.Builder().target(
								        new LatLng(35.5041923, 11.0531953)).zoom(14).build();
								  googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
						
								  drawer.close();
					}
					}});
    			
    			r4.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						r4.setBackgroundResource(R.drawable.bg_relativelinear2);
						r1.setBackgroundResource(R.drawable.bg_relativelinear);
						r2.setBackgroundResource(R.drawable.bg_relativelinear);
						r3.setBackgroundResource(R.drawable.bg_relativelinear);
						r5.setBackgroundResource(R.drawable.bg_relativelinear);
						r6.setBackgroundResource(R.drawable.bg_relativelinear);
						googleMap.clear();
						onMyLocationChange();
						
						 for (int i = 0; i < longhopitale.length; i++) {
						
							 MarkerOptions m1 =new MarkerOptions().position(new LatLng(longhopitale[i], lathopitale[i]))
								.title("hôpital").snippet(deschopitale[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker3))
		                          .alpha(0.7f);
							 googleMap.addMarker(m1);
							 
							 CircleOptions co =new CircleOptions();
							    co.center(new LatLng(longhopitale[i], lathopitale[i]));
							    co.radius(10);
							    co.strokeColor(color.jaun);
							    co.strokeWidth(5);
							    co.fillColor(color.jaun);
							    googleMap.addCircle(co);
							    
							    CircleOptions co2 =new CircleOptions();
							    co2.center(new LatLng(longhopitale[i], lathopitale[i]));
							    co2.radius(40);
							    co2.strokeColor(color.jaun);
							    co2.strokeWidth(3);
							    co2.fillColor(R.color.jaun);
							    googleMap.addCircle(co2);
							 
							 CameraPosition cameraPosition = new CameraPosition.Builder().target(
								        new LatLng(35.5041923, 11.0531953)).zoom(14).build();
								  googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
						
								  drawer.close();
					}
					}});
    			r5.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						r5.setBackgroundResource(R.drawable.bg_relativelinear2);
						r1.setBackgroundResource(R.drawable.bg_relativelinear);
						r2.setBackgroundResource(R.drawable.bg_relativelinear);
						r3.setBackgroundResource(R.drawable.bg_relativelinear);
						r4.setBackgroundResource(R.drawable.bg_relativelinear);
						r6.setBackgroundResource(R.drawable.bg_relativelinear);
						googleMap.clear();
						onMyLocationChange();
						
						
						 for (int i = 0; i < longprotection.length; i++) {
						
							 MarkerOptions m1 =new MarkerOptions().position(new LatLng(longprotection[i], latprotection[i]))
								.title("Protection Civile").snippet(descprotection[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker1))
		                          .alpha(0.7f);
							 googleMap.addMarker(m1);
							 
							 CircleOptions co =new CircleOptions();
							    co.center(new LatLng(longprotection[i], latprotection[i]));
							    co.radius(10);
							    co.strokeColor(color.jaun);
							    co.strokeWidth(5);
							    co.fillColor(color.jaun);
							    googleMap.addCircle(co);
							    
							    CircleOptions co2 =new CircleOptions();
							    co2.center(new LatLng(longprotection[i], latprotection[i]));
							    co2.radius(40);
							    co2.strokeColor(color.jaun);
							    co2.strokeWidth(3);
							    co2.fillColor(R.color.jaun);
							    googleMap.addCircle(co2);
							 
							 CameraPosition cameraPosition = new CameraPosition.Builder().target(
								        new LatLng(35.5178889, 11.0258753)).zoom(14).build();
								  googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
						
								  drawer.close();
					}
					}});
    			
    			r6.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						r6.setBackgroundResource(R.drawable.bg_relativelinear2);
						r1.setBackgroundResource(R.drawable.bg_relativelinear);
						r2.setBackgroundResource(R.drawable.bg_relativelinear);
						r3.setBackgroundResource(R.drawable.bg_relativelinear);
						r4.setBackgroundResource(R.drawable.bg_relativelinear);
						r5.setBackgroundResource(R.drawable.bg_relativelinear);
						googleMap.clear();
						onMyLocationChange();
						
						
						 for (int i = 0; i < longdispensaire.length; i++) {
						
						MarkerOptions m1 =new MarkerOptions().position(new LatLng(longdispensaire[i], latdispensaire[i]))
	.title("Dispensaire").snippet(descdispensaire[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker1))
		                          .alpha(0.7f);
							 googleMap.addMarker(m1);
							 
							 CircleOptions co =new CircleOptions();
							    co.center(new LatLng(longdispensaire[i], latdispensaire[i]));
							    co.radius(10);
							    co.strokeColor(color.jaun);
							    co.strokeWidth(5);
							    co.fillColor(color.jaun);
							    googleMap.addCircle(co);
							    
							    CircleOptions co2 =new CircleOptions();
							    co2.center(new LatLng(longdispensaire[i], latdispensaire[i]));
							    co2.radius(40);
							    co2.strokeColor(color.jaun);
							    co2.strokeWidth(3);
							    co2.fillColor(R.color.jaun);
							    googleMap.addCircle(co2);
							 
							 CameraPosition cameraPosition = new CameraPosition.Builder().target(
								        new LatLng(35.5178889, 11.0258753)).zoom(13).build();
								  googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
						
								  drawer.close();
					}
					}});
    			
double latitude = 35.5178625;
double longitude = 11.0377353;
    			
    			
//Moving Camera to a Location with animation
CameraPosition cameraPosition = new CameraPosition.Builder().target(
        new LatLng(latitude, longitude)).zoom(15).build();
    			
    			
    			
    			
    			
    	

    	locationManager.requestLocationUpdates(provider, 20000, 1, this);
    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    
    
    
    googleMap.setOnMapClickListener(new OnMapClickListener() {
		
		@Override
		public void onMapClick(LatLng point) {
			
			// Already two locations				
			if(markerPoints.size()>1){
				markerPoints.clear();
				googleMap.clear();					
			}
			
			// Adding new item to the ArrayList
			markerPoints.add(point);				
			
			// Creating MarkerOptions
			MarkerOptions options = new MarkerOptions();
			
			// Setting the position of the marker
			options.position(point);
			
			/** 
			 * For the start location, the color of marker is GREEN and
			 * for the end location, the color of marker is RED.
			 */
			if(markerPoints.size()==1){
				options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
			}else if(markerPoints.size()==2){
				options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			}
						
			
			// Add new marker to the Google Map Android API V2
			googleMap.addMarker(options);
			
			// Checks, whether start and end locations are captured
			if(markerPoints.size() >= 2){					
				LatLng origin = markerPoints.get(0);
				LatLng dest = markerPoints.get(1);
				
				// Getting URL to the Google Directions API
				String url = getDirectionsUrl(origin, dest);				
				
				DownloadTask downloadTask = new DownloadTask();
				
				// Start downloading json data from Google Directions API
				downloadTask.execute(url);
			}
			
		}
	});
    
    				
    		
	   return rootView;
    	}

    	

		private LocationManager getSystemService(String locationService) {
		// TODO Auto-generated method stub
		return null;
	}

		@Override
    	public void onResume() {
    		super.onResume();
    		initilizeMap();
    		
    	}

    	/**
    	 * function to load map If map is not created it will create it for you
    	 * */
    	private void initilizeMap() {
    		if (googleMap == null) {
    			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
    					R.id.map)).getMap();

    			// check if map is created successfully or not
    			if (googleMap == null) {
    				Toast.makeText(getApplicationContext(),
    						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
    						.show();
    			}
    		}
    	}
    	
    	public static double distance(double lat1, double lon1, double lat2, double lon2) {
            // haversine great circle distance approximation, returns meters
            double theta = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                    + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                    * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60; // 60 nautical miles per degree of seperation
            dist = dist * 1852; // 1852 meters per nautical mile
            return (dist);
        }

        private static double deg2rad(double deg) {
            return (deg * Math.PI / 180.0);
        }

        private static double rad2deg(double rad) {
            return (rad * 180.0 / Math.PI);
        }

		private Context getApplicationContext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void onLocationChanged(Location arg0) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void onProviderDisabled(String provider) {
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

		 public void onMyLocationChange()  {
			 
			 try{
			 if(location!=null){
					//onLocationChanged(location);
					//juste remplacer les latitude et la longitude si dessous par celles de l'isima  
					// ajouter cette permission  au manifest <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />  
						 LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				         MarkerOptions markerOptions = new MarkerOptions();
				         markerOptions.title("Your Location");
				         markerOptions.snippet("you are here");
				         markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_me));
				         markerOptions.position(latLng);
				           googleMap.addMarker(markerOptions);
											
					} else{
				Toast.makeText(getActivity().getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
					 } 

			 } catch (Exception e) {
				 e.printStackTrace();
			 }
         }



		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int pos,
				long id) {
			switch (pos) {
			case 1:
				googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    			break;
			case 2:
				googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				break;
			case 3 :
				googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				break;
			case 4 :
				googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				break;
			case 5 :
				googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
				break;
			}
		}
			



		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		 
		
    	

		private String getDirectionsUrl(LatLng origin,LatLng dest){
						
			// Origin of route
			String str_origin = "origin="+origin.latitude+","+origin.longitude;
			
			// Destination of route
			String str_dest = "destination="+dest.latitude+","+dest.longitude;		
			
						
			// Sensor enabled
			String sensor = "sensor=false";			
						
			// Building the parameters to the web service
			String parameters = str_origin+"&"+str_dest+"&"+sensor;
						
			// Output format
			String output = "json";
			
			// Building the url to the web service
			String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
			
			
			return url;
		}
		
		/** A method to download json data from url */
	    private String downloadUrl(String strUrl) throws IOException{
	        String data = "";
	        InputStream iStream = null;
	        HttpURLConnection urlConnection = null;
	        try{
	                URL url = new URL(strUrl);

	                // Creating an http connection to communicate with url 
	                urlConnection = (HttpURLConnection) url.openConnection();

	                // Connecting to url 
	                urlConnection.connect();

	                // Reading data from url 
	                iStream = urlConnection.getInputStream();

	                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

	                StringBuffer sb  = new StringBuffer();

	                String line = "";
	                while( ( line = br.readLine())  != null){
	                        sb.append(line);
	                }
	                
	                data = sb.toString();

	                br.close();

	        }catch(Exception e){
	                Log.d("Exception while downloading url", e.toString());
	        }finally{
	                iStream.close();
	                urlConnection.disconnect();
	        }
	        return data;
	     }

		
		
		// Fetches data from url passed
		private class DownloadTask extends AsyncTask<String, Void, String>{			
					
			// Downloading data in non-ui thread
			@Override
			protected String doInBackground(String... url) {
					
				// For storing data from web service
				String data = "";
						
				try{
					// Fetching the data from web service
					data = downloadUrl(url[0]);
				}catch(Exception e){
					Log.d("Background Task",e.toString());
				}
				return data;		
			}
			
			// Executes in UI thread, after the execution of
			// doInBackground()
			@Override
			protected void onPostExecute(String result) {			
				super.onPostExecute(result);			
				
				ParserTask parserTask = new ParserTask();
				
				// Invokes the thread for parsing the JSON data
				parserTask.execute(result);
					
			}		
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
		
		/** A class to parse the Google Places in JSON format */
	    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
	    	
	    	// Parsing the data in non-ui thread    	
			@Override
			protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
				
				JSONObject jObject;	
				List<List<HashMap<String, String>>> routes = null;			           
	            
	            try{
	            	jObject = new JSONObject(jsonData[0]);
	            	DirectionsJSONParser parser = new DirectionsJSONParser();
	            	
	            	// Starts parsing data
	            	routes = parser.parse(jObject);    
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
	            return routes;
			}
			
			

			
			
			// Executes in UI thread, after the parsing process
			@Override
			protected void onPostExecute(List<List<HashMap<String, String>>> result) {
				ArrayList<LatLng> points = null;
				PolylineOptions lineOptions = null;
				MarkerOptions markerOptions = new MarkerOptions();
				String distance = "";
				String duration = "";
				
				
				
				if(result.size()<1){
					Toast.makeText(getActivity(), "No Points", Toast.LENGTH_SHORT).show();
					return;
				}
					
				
				// Traversing through all the routes
				for(int i=0;i<result.size();i++){
					points = new ArrayList<LatLng>();
					lineOptions = new PolylineOptions();
					
					// Fetching i-th route
					List<HashMap<String, String>> path = result.get(i);
					
					// Fetching all the points in i-th route
					for(int j=0;j<path.size();j++){
						HashMap<String,String> point = path.get(j);	
						
						if(j==0){	// Get distance from the list
							distance = point.get("distance");						
							continue;
						}else if(j==1){ // Get duration from the list
							duration = point.get("duration");
							continue;
						}
						
						double lat = Double.parseDouble(point.get("lat"));
						double lng = Double.parseDouble(point.get("lng"));
						LatLng position = new LatLng(lat, lng);	
						
						points.add(position);						
					}
					
					// Adding all the points in the route to LineOptions
					lineOptions.addAll(points);
					lineOptions.width(2);
					lineOptions.color(Color.RED);	
					
				}
				
				tvDistanceDuration.setText("Distance:"+distance + ", Duration:"+duration);
				
				// Drawing polyline in the Google Map for the i-th route
				googleMap.addPolyline(lineOptions);							
			}			
	    }   
	    
	    
		


		
}

    

