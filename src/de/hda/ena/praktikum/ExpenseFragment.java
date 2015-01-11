package de.hda.ena.praktikum;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class ExpenseFragment extends Fragment {

	private ListView mListView;
	private static final String ARG_SECTION_NUMBER = "section_number";
	private Interval _grouping = Interval.MONAT;

	private Category cCat;
	
	public static ExpenseFragment createInstance(int sectionNumber, String cat) {
		ExpenseFragment frg = new ExpenseFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putString("ARG_CATEGORY", cat);
		
		frg.setArguments(args);
		return frg;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Bundle b = getArguments();
		if (b != null) {
			int section = b.getInt(ARG_SECTION_NUMBER);
			((ExpenseActivity) activity).onSectionAttached(section);
			this._grouping = Interval.values()[section - 1];
			
			String cat = b.getString("ARG_CATEGORY");
			
			for(Category d : DataStore.cData) {
				if(d.getTitle().equals(cat)) {
					cCat = d;
					break;
				}
			}
			if(cCat == null) {
				throw new IndexOutOfBoundsException("category");
			}
			 
			
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.catergoryexpenses, container,
				false);

		mListView = (ListView) rootView.findViewById(R.id.listView1);

		mListView.setAdapter(new ExpenseAdapter(this.getActivity(),
				this.cCat.getExpensesFiltered(this._grouping)));
		
		return rootView;
	}
}
