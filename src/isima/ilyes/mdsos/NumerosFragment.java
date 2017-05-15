package isima.ilyes.mdsos;

import info.androidhive.slidingmenu.R;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class NumerosFragment  extends Fragment {
	
	public NumerosFragment(){}
	View rootView;
	private GridviewAdapter mAdapter;
    private ArrayList<String> listNumero;
    private ArrayList<String> listNames;
    TextView tvAddress;
 
    private GridView gridView;
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // set the title for the action bar
        final ActionBar actionBar = activity.getActionBar();
        actionBar.setTitle("Numeros utiles");
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
		
       
         rootView = inflater.inflate(R.layout.fragment_numeros, container, false);
         tvAddress =(TextView) rootView.findViewById(R.id.tvAddress);
		Bundle bundle = this.getArguments();
		String adresse = bundle.getString("adresse");
		tvAddress.setText(adresse);

         prepareList();
         
         // prepared arraylist and passed it to the Adapter class
         mAdapter = new GridviewAdapter(getActivity(),listNumero,listNames);
  
         // Set custom adapter to gridview
         gridView = (GridView) rootView.findViewById(R.id.grid_view);
         gridView.setAdapter(mAdapter);
  
         // Implement On Item click listener
         gridView.setOnItemClickListener(new OnItemClickListener()
         {
             @Override
             public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                     long arg3) {
            	 String num = (String) mAdapter.getItem(position);
            	 call(num);
                 
             }
         });
  
     
  
    
         
         
         return rootView;
	}


public void prepareList()
{
	 listNumero = new ArrayList<String>();

	   listNumero.add("197");
	   listNumero.add("198");
	   listNumero.add("190");
	   listNumero.add("71351500");
	   listNumero.add("71744215");
	   listNumero.add("71725555");
	   listNumero.add("71801211");
	   listNumero.add("71780000");
	   listNumero.add("71522381");
	   listNumero.add("71569600");
	   listNumero.add("71880777");
	   listNumero.add("71862222");
	   listNumero.add("71224444");
       listNumero.add("71249226");
       
       
  	 listNames = new ArrayList<String>();

  	listNames.add("Police secours");
  	listNames.add("Protection civile");
  	listNames.add("SAMU ");
  	listNames.add("Urgence secours");
  	listNames.add("SOS Médecins");
  	listNames.add("SOS Ambulances");
  	listNames.add("SOS Remorquage");
  	listNames.add("Allo docteur");
  	listNames.add("S.O.S médecin");
  	listNames.add("Allo Tabib");
  	listNames.add("Ambulances Delta");
  	listNames.add("Tunisie Ambulance");
  	listNames.add("Médecins de nuit ");
  	listNames.add("Garde médicale");
  	
}

public void call(String uri) {
    Intent in=new Intent(Intent.ACTION_CALL);
    in.setData(Uri.parse("tel:"+uri));
       startActivity(in);
	//Toast.makeText(getActivity(), uri, Toast.LENGTH_SHORT).show();
   
 }

}