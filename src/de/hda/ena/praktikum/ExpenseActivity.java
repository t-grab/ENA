package de.hda.ena.praktikum;

import java.util.ArrayList;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	ArrayList<Expense> _expenses = new ArrayList<Expense>();
	private String cat;
	private int pos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.cat = getIntent().getStringExtra("ARG_CATEGORY");
		Log.i("ENA", "catExpense: " + cat);
		if(savedInstanceState != null) {
			Log.i("ENA", "SAVED INSTANCE");
			if(cat == null) {
				this.cat = savedInstanceState.getString("ARG_CATEGORY");
				Log.i("ENA", "catExpense: " + cat);
			}
		} else {
			Log.i("ENA", "SAVED INSTANCE EMPTY");
		}
		
		setContentView(R.layout.activity_expense);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		pos = position;
		FragmentManager fragmentManager = getFragmentManager();
		ExpenseFragment exF = ExpenseFragment.createInstance(position + 1, this.cat);
		if(exF == null) {
			Log.e("ENA", "exF empty");
		}
		if(this.cat == null) {
			Log.e("ENA","cat empty");
		}
		fragmentManager
				.beginTransaction()
				.replace(R.id.container_expense,
						exF).commit();
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
        MenuItem btnNewExpense = menu.add("Neu").setOnMenuItemClickListener(
            new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(getBaseContext(), EditExpenseActivity.class);
                i.putExtra("ARG_REQUEST", RequestCodes.NEW);
                i.putExtra("ARG_CATEGORY", cat);
                startActivityForResult(i, RequestCodes.NEW.ordinal());

                return false;
            }
        });

        btnNewExpense.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.test, menu);
			restoreActionBar();
			return true;
		}

		return super.onCreateOptionsMenu(menu);
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("ENA", "called Activity Result");
    	switch(RequestCodes.values()[requestCode]) {
            case NEW:
            case EDIT:
            	 if(resultCode == RESULT_OK){
                     Toast.makeText(this, "Positive Result", Toast.LENGTH_LONG);
                     FragmentManager fragmentManager = getFragmentManager();
             		ExpenseFragment exF = ExpenseFragment.createInstance(pos + 1, this.cat);
             		fragmentManager
             				.beginTransaction()
             				.replace(R.id.container_expense,
             						exF).commit();
             		FileHandler f = new FileHandler(DataStore.sPath,this);
             		f.write(DataStore.cData); //save changes
                 }
            	break;
            default:
                // go crazy. yep, crazy.
    	}
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
