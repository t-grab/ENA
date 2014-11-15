package de.hda.ena.praktikum;

import java.util.Calendar;

public class Expense {
	public Expense(Calendar d, float val, String desc) {
		this.date = d; 
		this.value = val;
		this.description = desc;
	}
	
	// Getter
	public Calendar getDate() { return date; }
	public float getValue() { return value; }
	public String getDescription() { return description; }
	
	// Setter
	public void setDate(Calendar d) { this.date = d; }
	public void setValue(float v) { this.value = v; }
	public void setDescription(String desc) { this.description = desc; }
	
	// Methods
	@Override
	public String toString() {
		return this.description + this.value;
	}
	
	// Fields
	private Calendar date;
	private float value;
	private String description;
}
