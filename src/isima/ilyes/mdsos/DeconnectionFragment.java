package isima.ilyes.mdsos;

import java.util.HashMap;
import info.androidhive.slidingmenu.*;
import isima.ilyes.mdsos.loginandregistration.LoginActivity;
import isima.ilyes.mdsos.loginandregistration.helper.SQLiteHandler;
import isima.ilyes.mdsos.loginandregistration.helper.SessionManager;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DeconnectionFragment extends Fragment {

	 public DeconnectionFragment(){}
	 
	 	private TextView txtName;
		private TextView txtEmail;
		private Button btnLogout;

		private SQLiteHandler db;
		private SessionManager session;

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View V = inflater.inflate(R.layout.fragment_deconnection, container, false);
        

		txtName = (TextView) V.findViewById(R.id.name);
		txtEmail = (TextView) V.findViewById(R.id.email);
		btnLogout = (Button) V.findViewById(R.id.btnLogout);
		
		db = new SQLiteHandler(getActivity());

		// session manager
		session = new SessionManager(getActivity());

		if (!session.isLoggedIn()) {
			logoutUser();
		}

		// Fetching user details from sqlite
		HashMap<String, String> user = db.getUserDetails();

		String name = user.get("name");
		String email = user.get("email");

		// Displaying the user details on the screen
		txtName.setText(name);
		txtEmail.setText(email);

		// Logout button click event
		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logoutUser();
			}
		});
         
        return V;
    }
	

	/**
	 * Logging out the user. Will set isLoggedIn flag to false in shared
	 * preferences Clears the user data from sqlite users table
	 * */
	private void logoutUser() {
		session.setLogin(false);

		db.deleteUsers();

		// Launching the login activity
		Intent intent = new Intent(getActivity(), LoginActivity.class);
		startActivity(intent);
		
	}
}
