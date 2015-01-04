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

		mListView
		.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent,
					View view, int position, long id) {
				Intent i = new Intent(view.getContext(),
						ExpenseActivity.class);
				Log.i("catSelect", mListView
						.getItemAtPosition(position).toString());
				i.putExtra("selctedCategory", "test");
				startActivity(i);
				return true; // accept click
			}
		});

		ArrayList<Category> categories = new ArrayList<Category>();
		categories.add(new Category("Studium"));

		categories.get(0).getExpenses().add(new Expense(Calendar.getInstance(), 7.0, "Test1"));
		categories.get(0).getExpenses().add(new Expense(Calendar.getInstance(), 5.0, "Test2"));

		categories.add(new Category("Debug"));

		categories.get(1).getExpenses().add(new Expense(Calendar.getInstance(), 12.0, "Test1"));
		categories.get(1).getExpenses().add(new Expense(Calendar.getInstance(), 5.0, "Test2"));
		
		categories.add(new Category("Debug-PAST"));

		
		Calendar datum = Calendar.getInstance();
		datum.set(2015, 1, 7);
		
		categories.get(2).getExpenses().add(new Expense(datum, 13.0, "Test1"));
		categories.get(2).getExpenses().add(new Expense(datum, 7.0, "Test2"));

		FileHandler fh = new FileHandler(Environment
				.getExternalStorageDirectory().getPath() + "/expenses.json",
				rootView.getContext());
		fh.write(categories);

		categories = fh.read(rootView.getContext());
		
		mListView.setAdapter(new CategoryAdapter(this.getActivity(),
				categories, this._grouping));

		return rootView;
	}
}
