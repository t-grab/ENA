package de.hda.ena.praktikum;

import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;

public class Expense implements Parcelable {
	public Expense(Calendar d, double val, String desc) {
		this.date = d; 
		this.value = val;
		this.description = desc;
	}
	
	// Getter
	public Calendar getDate() { return date; }
	public double getValue() { return value; }
	public String getDescription() { return description; }
	
	// Setter
	public void setDate(Calendar d) { this.date = d; }
	public void setValue(double v) { this.value = v; }
	public void setDescription(String desc) { this.description = desc; }
	
	// Methods
	@Override
	public String toString() {
		return this.description + this.value;
	}
	
	// Fields
	private Calendar date;
	private double value;
	private String description;
	
	public Expense(Parcel in){
		this.description = in.readString();
		this.value = in.readDouble();
		this.date = Calendar.getInstance();
		this.date.setTimeInMillis(in.readLong());
	}
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.description);
		dest.writeDouble(this.value);
		dest.writeLong(this.date.getTimeInMillis());
	}
	
	public static final Parcelable.Creator<Expense> CREATOR= new Parcelable.Creator<Expense>() {
		 
		@Override
		public Expense createFromParcel(Parcel source) {
		return new Expense(source);  //using parcelable constructor
		}
		 
		@Override
		public Expense[] newArray(int size) {
		return new Expense[size];
		}
		};
}
