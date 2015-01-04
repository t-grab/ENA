package de.hda.ena.praktikum;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoryAdapter extends ArrayAdapter<Category> {
	
	private Interval _grouping = Interval.MONAT;
	
	public CategoryAdapter(Context context,
			List<Category> objects, Interval grouping) {
		super(context, R.layout.categorylistitem, objects);
		
		this._grouping = grouping;
	}
	
	public void setGrouping(Interval grouping) {
		this._grouping = grouping;
	}
	
	public Interval getGrouping() {
		return this._grouping;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(this.getContext().LAYOUT_INFLATER_SERVICE);
		View rowView = li.inflate(R.layout.categorylistitem, parent, false);
		
		Category c = this.getItem(position);
		if(c != null) {
			TextView tvName = (TextView) rowView.findViewById(R.id.category_name);
	        TextView tvValue = (TextView) rowView.findViewById(R.id.category_value);
	        
	        if(tvName != null) {
	        	tvName.setText(c.getTitle());
	        }
	        
	        if(tvValue != null) {
	        	tvValue.setText(String.valueOf(c.sum(this._grouping)));
	        }
		}

		return rowView;
	}
}
