package de.hda.ena.praktikum;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	
	private ArrayList<Category> _lData = new ArrayList<Category>();
	
	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		
		//mMainFragment = (MainFragment) getFragmentManager().findFragmentById(R.id.LinearLayout1);
		
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		//load data
		if(DataStore.cData == null) {
			FileHandler fh = new FileHandler(Environment
					.getExternalStorageDirectory().getPath() + "/expenses.json",
					getApplicationContext());
			
			/*ArrayList<Category> categories = new ArrayList<Category>();
			categories.add(new Category("Studium"));

			categories.get(0).getExpenses().add(new Expense(Calendar.getInstance(), 7.0, "Test1"));
			categories.get(0).getExpenses().add(new Expense(Calendar.getInstance(), 5.0, "Test2"));

			categories.add(new Category("Debug"));

			categories.get(1).getExpenses().add(new Expense(Calendar.getInstance(), 12.0, "Test1"));
			categories.get(1).getExpenses().add(new Expense(Calendar.getInstance(), 5.0, "Test2"));
			
			categories.add(new Category("Debug-PAST"));

			
			Calendar datum = Calendar.getInstance();
			datum.set(2015, Calendar.JANUARY, 3);
			
			categories.get(2).getExpenses().add(new Expense(datum, 13.0, "Debug1"));
			categories.get(2).getExpenses().add(new Expense(datum, 7.0, "Debug2"));
			
			fh.write(categories);*/
			
			DataStore.cData = fh.read();
		}
		
	
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		Log.d("ENA", "created CategoryFragment");
		
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						CategoryFragment.createInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_drawer_day);
			break;
		case 2:
			mTitle = getString(R.string.title_drawer_week);
			break;
		case 3:
			mTitle = getString(R.string.title_drawer_month);
			break;
		case 4:
			mTitle = getString(R.string.title_drawer_year);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if (id == R.id.action_settings) {
			Log.i("ENA","Settings clicked");
			//TODO ADD INTENT
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
