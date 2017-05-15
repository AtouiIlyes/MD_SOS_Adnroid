package isima.ilyes.mdsos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import info.androidhive.slidingmenu.*;

public class PersonnalcardFragment extends Fragment {
	
	public PersonnalcardFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_personnal_card, container, false);
         
        return rootView;
    }
}
