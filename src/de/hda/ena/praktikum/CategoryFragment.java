package de.hda.ena.praktikum;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class CategoryFragment extends Fragment {

	private ListView mListView;
	private static final String ARG_SECTION_NUMBER = "section_number";
	private Interval _grouping = Interval.MONAT;

	public static CategoryFragment createInstance(int sectionNumber) {
		CategoryFragment frg = new CategoryFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		frg.setArguments(args);
		return frg;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Bundle b = getArguments();
		if (b != null) {
			int section = b.getInt(ARG_SECTION_NUMBER);
			((MainActivity) activity).onSectionAttached(section);
			Log.i("ENA", String.valueOf(section));
			this._grouping = Interval.values()[section - 1];
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		mListView = (ListView) rootView.findViewById(R.id.listView1);

		mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent,
					View view, int position, long id) {
				
				Intent i = new Intent(view.getContext(), ExpenseActivity.class);
				
				Log.i("ENA", "catSelect" + ((Category)mListView.getItemAtPosition(position)).getTitle());
				
				i.putExtra("ARG_CATEGORY", ((Category)mListView.getItemAtPosition(position)).getTitle());
				
				//i.putExtra("selctedCategory", "test");
				//i.putExtra("data", (Parcelable)mListView.getItemAtPosition(position));
				startActivity(i);
				return true; // accept click
			}
		});
		
		mListView.setAdapter(new CategoryAdapter(this.getActivity(),
				DataStore.cData, this._grouping));

		return rootView;
	}
}
