package isima.ilyes.mdsos;

import info.androidhive.slidingmenu.R;
import isima.ilyes.mdsos.adapter.NavDrawerListAdapter;
import isima.ilyes.mdsos.model.NavDrawerItem;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


@SuppressLint("NewApi")
public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	String value;
	Button btnServ;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    value = extras.getString("email");
		}

		
		
		
		mTitle= mDrawerTitle = getTitle(); 
		
		ActionBar actionBar;

		
		
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		
		
		
		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		

		navDrawerItems = new ArrayList<NavDrawerItem>();
		
		
		

		// adding nav drawer items to array
		
	
		
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// separotor
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],  true, "3"));
		// Personal card
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(1, -1)));
		// Fiche de santé 
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(2, -1)));
		// SMS
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(3, -1)));
		//separator Help
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5],  true, "2"));
		//Demander urgence
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(4, -1)));
		//Services santés plus proches
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(5, -1)));
		//separator follow us
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8],  true, "3"));
		//Page FB
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(6, -1)));
		//Page tweeter
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], navMenuIcons.getResourceId(7, -1)));
		//Page google+
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], navMenuIcons.getResourceId(8, -1)));
		//separator Connection
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[12],  true, "1+"));
		//Deconnection
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[13], navMenuIcons.getResourceId(9, -1)));
		
		
		// Recycle the typed array
		
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
		
		mDrawerList.setAdapter(adapter);
		

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			@Override
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle); 
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	
	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		
		case 2:
			fragment = new PersonnalcardFragment();
			break;
		case 3:
			fragment = new FichesanteFragment();
			break;
		case 4:
			fragment = new SmsFragment();
			break;
		case 6:
			fragment = new DmanderurgenceFragment();
			break;
		case 7:
			fragment = new ServicesanteFragment();
			break;
		case 9:
			fragment = new PagefbFragment();
			break;	
		case 10:
			fragment = new PagetweeterFragment();
			break;
		case 11:
			fragment = new PagegoogleFragment();
			break;	
		case 13:
			fragment = new DeconnectionFragment();
			break;		
			
		default:
			fragment = new HomeFragment();
			Bundle args = new Bundle();
            args.putString("a", value);
            fragment.setArguments(args);
	        
			break;
		}

		if (fragment != null) {
			
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	
	

}
