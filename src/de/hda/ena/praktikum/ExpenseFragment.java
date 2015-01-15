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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
			if(cat != null) {
				Log.i("ENA", "catAttach: " + cat);
				for(Category d : DataStore.cData) {
					if(d.getTitle().equals(cat)) {
						cCat = d;
						break;
					}
				}
				if(cCat == null) {
					throw new IndexOutOfBoundsException("category");
				}
			} else {
				Log.e("ENA", "exFrg:onAtt:sCat");
			}
		} else {
			Log.e("ENA", "exFrg:onAtt:bB");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle b = getArguments();
		
		if (b != null) {
			int section = b.getInt(ARG_SECTION_NUMBER);
			
			((ExpenseActivity) this.getActivity()).onSectionAttached(section);
			
			this._grouping = Interval.values()[section - 1];
			
			String cat = b.getString("ARG_CATEGORY");
			if(cat != null) {
				Log.i("ENA", "catCreat: " + cat);
				for(Category d : DataStore.cData) {
					if(d.getTitle().equals(cat)) {
						cCat = d;
						break;
					}
				}
				if(cCat == null) {
					throw new IndexOutOfBoundsException("category");
				}
			} else {
				Log.e("ENA", "exFrg:onCrea:sCat");
			}
		} else if(savedInstanceState != null) {
			String cat = savedInstanceState.getString("ARG_CATEGORY");
			if(cat != null) {
				Log.i("ENA", "catCreat: " + cat);
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
		
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.catergoryexpenses, container,
				false);

        final TextView viewTitle = (TextView) rootView.findViewById(R.id.category_viewTitle);
        viewTitle.setText(this.cCat.getTitle());

        mListView = (ListView) rootView.findViewById(R.id.listView1);
		
		if(this.cCat == null) {
			Log.e("ENA","cCat EMPTY");
		}
		
		if(this._grouping == null) {
			Log.e("ENA", "grouping empty");
		}
		
		mListView.setAdapter(new ExpenseAdapter(this.getActivity(),
				this.cCat.getExpensesFiltered(this._grouping)));
		
		mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent,
					View view, int position, long id) {
				Log.i("ENA", "exSelect" + ((Expense)mListView.getItemAtPosition(position)).getDescription());

				Intent i = new Intent(view.getContext(), EditExpenseActivity.class);
	            i.putExtra("ARG_REQUEST", RequestCodes.EDIT);
	            i.putExtra("ARG_CATEGORY", cCat.getTitle());
	            i.putExtra("ARG_ID", ((Expense)mListView.getItemAtPosition(position)).getId());
	            startActivityForResult(i, RequestCodes.EDIT.ordinal());
				return true; // accept click
			}
		});
		return rootView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		ExpenseActivity parent = (ExpenseActivity) getActivity();
		parent.onActivityResult(requestCode, resultCode, data);
	}
}
