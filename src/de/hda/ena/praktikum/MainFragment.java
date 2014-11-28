package de.hda.ena.praktikum;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainFragment extends Fragment {
	
	private ListView mListView;
	private LinearLayout mLinearLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		/*mLinearLayout = (LinearLayout) inflater.inflate(
				R.layout.fragment_main, container, false);*/
		
		mListView = (ListView) rootView.findViewById(R.id.listView1);
		if(mListView == null) {
			Log.e("CreateMainFragment", "mainfail");
		} else {
		
		mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(view.getContext(), TestActivity.class);
				Log.i("catSelect", mListView.getItemAtPosition(position).toString());
				i.putExtra("selctedCategory", "test");
				startActivity(i);
				return true; //accept click
			}
		});
		}
		
		ArrayList<Category> categories = new ArrayList<Category>();
		categories.add(new Category("Studium"));
		categories.get(0).getExpenses().add(new Expense(Calendar.getInstance(), 0f, "Test"));
		
		mListView.setAdapter(new ArrayAdapter<Category>(this.getActivity(), android.R.layout.simple_list_item_1, categories));
		
		return rootView;
	}
}
