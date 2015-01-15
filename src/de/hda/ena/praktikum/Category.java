package de.hda.ena.praktikum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Category implements Parcelable {
	public Category(String t) {
		this.title = t;
		this.expenses = new ArrayList<Expense>();
		this.maxId = 0;
	}
	// Getter
	public String getTitle() {
		return this.title;
	}

	public ArrayList<Expense> getExpenses() {
		return this.expenses;
	}

	public ArrayList<Expense> getExpensesFiltered(Interval interval) {
		ArrayList<Expense> lExpense = new ArrayList<Expense>();

		Calendar start = Calendar.getInstance();

		switch (interval) {
		case TAG: {
			start.set(Calendar.HOUR_OF_DAY, 0);
			break;
		}
		case WOCHE: {
			start.set(Calendar.DAY_OF_WEEK, 
					start.getFirstDayOfWeek());
			start.set(Calendar.HOUR_OF_DAY, 0);
			break;
		}
		default: {
			start.set(Calendar.DAY_OF_MONTH, 1);
			start.set(Calendar.HOUR_OF_DAY, 0);
			break;
		}
		}

		for (int i = 0; i < this.expenses.size(); ++i) {
			Expense ex = this.expenses.get(i);
			if (ex.getDate().after(start))
				lExpense.add(ex);
		}

		return lExpense;
	}

	// Setter
	public void setTitle(String t) {
		this.title = t;
	}

	public void setExpenses(ArrayList<Expense> exp) {
		//Collections.reverse(exp);
		this.expenses = exp;
		this.maxId = exp.get(exp.size()-1).getId() + 1;
	}

	// Methods
	@Override
	public String toString() {
		return this.title + this.sum(Interval.MONAT);
	}

	public float sum(Interval interval) {
		Calendar start = Calendar.getInstance();

		switch (interval) {
		case TAG: {
			start.set(Calendar.HOUR_OF_DAY, 0);
			break;
		}
		case WOCHE: {
			start.set(Calendar.DAY_OF_WEEK, 
					start.getFirstDayOfWeek());
			start.set(Calendar.HOUR_OF_DAY, 0);
			break;
		}
		default: {
			start.set(Calendar.DAY_OF_MONTH, 1);
			start.set(Calendar.HOUR_OF_DAY, 0);
			break;
		}
		}

		float sum = 0f;
		for (int i = 0; i < this.expenses.size(); ++i) {
			if (this.expenses.get(i).getDate().after(start)) {
				sum += this.expenses.get(i).getValue();
			}
		}
		return sum;
	}

	public Expense getExpense(int id) {
		for(Expense exp : this.expenses)
			if(exp.getId() == id)
				return exp;
		return null;
	}
	
	public void setExpense(Expense exp) {
		Expense e = getExpense(exp.getId());
		if(e != null) {
			e.setDate(exp.getDate());
			e.setDescription(exp.getDescription());
			e.setValue(exp.getValue());
		}
	}
	
	public void removeExpense(Expense ex) {
		this.expenses.remove(ex);
	}
	
	public void addExpense(Expense exp) {
		if (exp.getId() != -1)
			return;
		this.expenses.add(0, new Expense(exp.getDate(), exp.getValue(), exp.getDescription(), this.maxId));
		this.maxId++;
	}
	
	// Fields
	private String title;
	private ArrayList<Expense> expenses = new ArrayList<Expense>();
	private int maxId = 0;
	
	public Category(Parcel in){
		this.title= in.readString();
		Expense ex;
		do  {
		ex = in.<Expense>readParcelable(Expense.class.getClassLoader());
		if(ex != null) {
			this.expenses.add(ex);
		}
		} while(ex != null);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.title);
		for(Expense ex : this.expenses) {
			dest.writeParcelable(ex, flags);
		}
	}
	
	public static final Parcelable.Creator<Category> CREATOR= new Parcelable.Creator<Category>() {
		 
		@Override
		public Category createFromParcel(Parcel source) {
		return new Category(source);  //using parcelable constructor
		}
		 
		@Override
		public Category[] newArray(int size) {
		return new Category[size];
		}
		};
}