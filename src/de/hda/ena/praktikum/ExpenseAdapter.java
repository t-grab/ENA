package de.hda.ena.praktikum;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ExpenseAdapter extends ArrayAdapter<Expense> {	
	public ExpenseAdapter(Context context,
			List<Expense> objects) {
		super(context, R.layout.categorylistitem, objects);
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
		Expense ex = this.getItem(position);
		if(ex != null) {
			TextView tvName = holder.tvName;
	        TextView tvValue = holder.tvValue;
	        
	        if(tvName != null) {
	        	tvName.setText(ex.getDescription());
	        }
	        
	        if(tvValue != null) {
	        	tvValue.setText(String.valueOf(ex.getValue()));
	        }
		}

		return rowView;
	}
}
