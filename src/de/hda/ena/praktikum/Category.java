package de.hda.ena.praktikum;

import java.util.ArrayList;
import java.util.Calendar;

public class Category {
	public Category(String t) {
		this.title = t;
		this.expenses = new ArrayList<Expense>();
	}
	
	// Getter
	public String getTitle() { return this.title; }
	public ArrayList<Expense> getExpenses() { return this.expenses; }
	public ArrayList<Expense> getExpensesFiltered(Interval interval) {
		ArrayList<Expense> lExpense = new ArrayList<Expense>();
		
		Calendar start = Calendar.getInstance();
		
		switch(interval) {
		case TAG:
			start.set(Calendar.HOUR_OF_DAY, 0);
		case WOCHE:
			start.set(Calendar.DAY_OF_WEEK, start.getActualMinimum(Calendar.DAY_OF_WEEK));
		default:
			start.set(Calendar.DAY_OF_MONTH, 1);
		}
		
		for(int i = 0; i < this.expenses.size(); ++i) {
			Expense ex = this.expenses.get(i);
			if(ex.getDate().after(start))
				lExpense.add(ex);
		}
		
		return lExpense;
	}
	
	// Setter
	public void setTitle(String t) { this.title = t; }
	public void setExpenses(ArrayList<Expense> exp) { this.expenses = exp; }
	
	// Methods
	@Override
	public String toString() {
		return this.title + this.sum(Interval.MONAT);
	}
	
	public float sum(Interval interval) {
		Calendar start = Calendar.getInstance();
		
		switch(interval) {
		case TAG:
			start.set(Calendar.HOUR_OF_DAY, 0);
		case WOCHE:
			start.set(Calendar.DAY_OF_WEEK, start.getActualMinimum(Calendar.DAY_OF_WEEK));
		default:
			start.set(Calendar.DAY_OF_MONTH, 1);
		}
		
		float sum = 0f;
		for(int i = 0; i < this.expenses.size(); ++i) {
			if(this.expenses.get(i).getDate().after(start))
				sum += this.expenses.get(i).getValue();
		}
		return sum;
	}
	
	// Fields
	private String title;
	private ArrayList<Expense> expenses;
}