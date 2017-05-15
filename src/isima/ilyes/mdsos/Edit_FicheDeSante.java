package isima.ilyes.mdsos;



import java.io.File;

import info.androidhive.slidingmenu.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;






















import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class Edit_FicheDeSante extends Fragment {
	
	    private TextView dateView;
	    private int pYear;
	    private int pMonth;
	    private int pDay;
	    static final int DATE_DIALOG_ID = 0;
        TextView date;
        TextView assurence;
        View rootView;
        
        private ImageView imageView;
    	final int TAKE_PICTURE = 1;
        final int ACTIVITY_SELECT_IMAGE = 2;
        File file;
        
        Button mButton;
        
        EditText name;
        private TextView dateofbirth;
        Spinner typesang;
        EditText nassurance;
        ToggleButton toggle;
        TextView typassurence;
        EditText pathologie1;
        EditText pathologie2;
        EditText allergie1;
        EditText allergie2;
        EditText traitement;
        EditText antfamil1;
        EditText antfamil2;
        EditText medecin;
        
        String sname;
        String sdateofbirth;
        String sdonorgane;
        String stypeassurence;
        String snassurance;
        String spathologie1;
        String spathologie2;
        String sallergie1;
        String sallergie2;
        String stypesang;
        String santfamil1;
        String santfamil2;
        String straitement;
        String smedecin;
        
        InputStream is=null;
    	String result=null;
    	String line=null;
    	int code;
    	
    	ProgressDialog PD;
        
        
        
        
public Edit_FicheDeSante(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
         rootView = inflater.inflate(R.layout.edit_fichedesante, container, false);
         
         StrictMode.ThreadPolicy policy = 
 				new StrictMode.ThreadPolicy.Builder().permitAll().build();
 		StrictMode.setThreadPolicy(policy);
 		
 		
         
          name=(EditText)rootView.findViewById(R.id.et1);
          dateofbirth=(TextView)rootView.findViewById(R.id.tv2);
          typesang=(Spinner) rootView.findViewById(R.id.spinner);
          typassurence=(TextView) rootView.findViewById(R.id.tv5);
          nassurance=(EditText)rootView.findViewById(R.id.et3);
 		  toggle = (ToggleButton)rootView.findViewById(R.id.tb1);
 		  typassurence=(TextView)rootView.findViewById(R.id.tv5);
 		
 		  pathologie1=(EditText)rootView.findViewById(R.id.et4);
 		  pathologie2=(EditText)rootView.findViewById(R.id.et8);
 		
 		  allergie1=(EditText)rootView.findViewById(R.id.et10);
 		  allergie2=(EditText)rootView.findViewById(R.id.et11);
 		
 		  traitement=(EditText)rootView.findViewById(R.id.et7);
         
 		  antfamil1=(EditText)rootView.findViewById(R.id.et14);
 		  antfamil2=(EditText)rootView.findViewById(R.id.et12);
 		
 		  medecin=(EditText)rootView.findViewById(R.id.et13);
 		
 		  
 	
 		  
 		
 		
         Button btnscan =(Button)rootView.findViewById(R.id.imgb4);
         btnscan.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View arg0) {
 				
 				IntentIntegrator integrator =new IntentIntegrator(getActivity());
 				integrator.initiateScan();
 			}
         });
         mButton=(Button)rootView.findViewById(R.id.b1);
         mButton.setOnClickListener(new OnClickListener() {
        	 InputStream is = null;
 			@Override
			public void onClick(View v) {
 				
 				ajouter();
 				
 		
 			}
 			
 		});
         
         
         assurence = (TextView)rootView.findViewById(R.id.tv5);
         assurence.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			 		  
				chooseAssurance();
				
			}
		});
         
         imageView = (ImageView) rootView.findViewById(R.id.imageView1);

 		
 		imageView.setOnClickListener(new OnClickListener() {

 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				selectImage();
 			}
 		});
 	
         
         
          
         dateofbirth.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onCreateDialog(0).show();
				
			}
		});
         
         
         /** Get the current date */
         final Calendar cal = Calendar.getInstance();
         pYear = cal.get(Calendar.YEAR);
         pMonth = cal.get(Calendar.MONTH);
         pDay = cal.get(Calendar.DAY_OF_MONTH);
  
         /** Display the current date in the TextView */
         updateDisplay();
        
 return rootView;
}
	
	
	/** Callback received when the user "picks" a date in the dialog */
    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
 
                @Override
				public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();
                    displayToast();
                }
            };
     
	
	
	
	 /** Updates the date in the TextView */
    private void updateDisplay() {
        dateofbirth.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(pMonth + 1).append("/")
                    .append(pDay).append("/")
                    .append(pYear).append(" "));
    }
	
	   

	/** Displays a notification when the date is updated */
    private void displayToast() {
        Toast.makeText(getActivity(), new StringBuilder().append("Votre Date de naissance est le : ")
        		.append(date.getText()),  Toast.LENGTH_LONG).show();
             
    }

	
	/** Create a new dialog for date picker */
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(getActivity(), 
                        pDateSetListener,
                        pYear, pMonth, pDay);
        }
        return null;
    }
    
   
    
    
    
    public void selectImage()
    {
         final CharSequence[] options = { "prendre une photo", "Choisissez parmi Galerie","supprimer la photo","annuler" };
         AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Ajoutez une photo!");
            builder.setItems(options,new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    if(options[which].equals("prendre une photo"))
                    {
                    	Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                        /*create instance of File with name img.jpg*/
                       
                        startActivityForResult(cameraIntent, TAKE_PICTURE);
                    }
                    else if(options[which].equals("Choisissez parmi Galerie"))
                    {
                        Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        
                        startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
                    }
                    
                    else if(options[which].equals("supprimer la photo"))
                    {
                    	imageView.setImageResource(R.drawable.profil);
                    }
                    else if(options[which].equals("annuler"))
                    {
                        dialog.dismiss();
                    }

                }
            });
            
            AlertDialog dialog = builder.create();

            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.windowAnimations =R.style.Animations_SmileWindow;
            dialog.show();
            dialog.getWindow().setAttributes(lp);
    }
    
    
    
    
    @Override
	public void onActivityResult(int requestcode,int resultcode,Intent intent){
    	
        super.onActivityResult(requestcode, resultcode, intent);
        
        IntentResult scanresult = IntentIntegrator.parseActivityResult(requestcode, resultcode, intent);
		if(scanresult !=null){
			String barcode;
			barcode = scanresult.getContents();
			EditText et7=(EditText) rootView.findViewById(R.id.et7);
			et7.setText(barcode);
		}
        if(resultcode==Activity.RESULT_OK)
        {
            if(requestcode==TAKE_PICTURE)
            {
          	  Bitmap photo = (Bitmap)intent.getExtras().get("data");
          	 
                
                imageView.setImageBitmap(photo);
                
                
            }
            
          
            else if(requestcode==ACTIVITY_SELECT_IMAGE)
            {
          	  	
          	  Uri selectedImage = intent.getData();
                String[] filePath = { MediaColumns.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                
                imageView.setImageBitmap(thumbnail);
          	  }
            }
            
            
    }
    
    
    
    public void chooseAssurance(){
    	final CharSequence[] options = { "CNAM", "COMAR","STAR","FTUSA","COTUNACE","Maghrebia","Autre","Aucun" };
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    	 builder.setItems(options,new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				 if(options[which].equals("CNAM"))
                 {
                 	assurence.setText("CNAM");
                 }
                 if(options[which].equals("COMAR"))
                 {
                	 assurence.setText("COMAR");
                 }
                 if(options[which].equals("STAR"))
                 {
                	 assurence.setText("STAR");
                 }
                 if(options[which].equals("FTUSA"))
                 {
                	 assurence.setText("FTUSA");
                 }
                 if(options[which].equals("COTUNACE"))
                 {
                	 assurence.setText("COTUNACE");
                 }
                 if(options[which].equals("Maghrebia"))
                 {
                	 assurence.setText("Maghrebia");
                 }
                 if(options[which].equals("Autre"))
                 {
                	 assurence.setText("Autre");
                 }
                 if(options[which].equals("Aucun"))
                 {
                	 assurence.setText("Aucun");
                 }
                 
			
			}
    		 
    	 } );
    	 AlertDialog dialog = builder.create();

         WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
         lp.windowAnimations =R.style.Animations_SmileWindow;
         dialog.show();
         dialog.getWindow().setAttributes(lp);

	 }
   
    
 void ajouter() {
	sname=name.getText().toString();
	sdateofbirth=dateofbirth.getText().toString();
		snassurance=nassurance.getText().toString();
		spathologie1=pathologie1.getText().toString();
		spathologie2=pathologie2.getText().toString();
		sallergie1=allergie1.getText().toString();
		sallergie2=allergie2.getText().toString();
		santfamil1=antfamil1.getText().toString();
		santfamil2=antfamil2.getText().toString();
		stypeassurence=typassurence.getText().toString();
		straitement=traitement.getText().toString();
		stypesang = typesang.getSelectedItem().toString();
		smedecin=medecin.getText().toString();
		if (toggle.isChecked()==(true)) {
	 		 sdonorgane="Oui";
	 		}
	 		if (toggle.isChecked()==(false)) {
	 			 sdonorgane="Non";
	 		}
	
	String certification="non certifiee";
	String email="chiheb";
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	nameValuePairs.add(new BasicNameValuePair("email", email));
	nameValuePairs.add(new BasicNameValuePair("sname", sname));
		//nameValuePairs.add(new BasicNameValuePair("sdateofbirth", "gg"));
		nameValuePairs.add(new BasicNameValuePair("snassurance", snassurance));
		nameValuePairs.add(new BasicNameValuePair("sdonorgane", sdonorgane));
		nameValuePairs.add(new BasicNameValuePair("stypesang", stypesang));
		nameValuePairs.add(new BasicNameValuePair("stypeassurence", stypeassurence));
		nameValuePairs.add(new BasicNameValuePair("spathologie1", spathologie1));
		nameValuePairs.add(new BasicNameValuePair("spathologie2", spathologie2));
		nameValuePairs.add(new BasicNameValuePair("sallergie1", sallergie1));
		nameValuePairs.add(new BasicNameValuePair("sallergie2", sallergie2));
		nameValuePairs.add(new BasicNameValuePair("santfamil1", santfamil1));
		nameValuePairs.add(new BasicNameValuePair("santfamil2", santfamil2));
		nameValuePairs.add(new BasicNameValuePair("straitement", straitement));
		nameValuePairs.add(new BasicNameValuePair("smedecin", smedecin));
		nameValuePairs.add(new BasicNameValuePair("certification", certification));
	 
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://192.168.100.1/fichesante.php");
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
    
    

    

