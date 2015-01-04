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

	private static class ViewHolder {
	    TextView tvName;
	    TextView tvValue;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		
		 //If the row is null, it means that we aren't recycling anything - so we have
	    //to inflate the layout ourselves.
	    ViewHolder holder = null;
		
		if(rowView == null) {
			LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(this.getContext().LAYOUT_INFLATER_SERVICE);
			rowView = li.inflate(R.layout.categorylistitem, parent, false);
			holder = new ViewHolder();
			holder.tvName =  (TextView) rowView.findViewById(R.id.category_name);
			holder.tvValue =  (TextView) rowView.findViewById(R.id.category_value);
	        //and store it as the 'tag' of our view
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		Category c = this.getItem(position);
		if(c != null) {
			TextView tvName = holder.tvName;
	        TextView tvValue = holder.tvValue;
	        
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
