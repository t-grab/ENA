package de.hda.ena.praktikum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class FileHandler {
	private final String path;
	private final Context ct;
	
	public FileHandler(String p, Context ct) {
		this.path = p;
		this.ct  = ct;
	}
	
	public ArrayList<Category> read() {
		File src = new File(path);
		
		try {
			if (!src.exists()) return null;
			
			ArrayList<Category> cats = new ArrayList<Category>();
			FileInputStream stream = new FileInputStream(src);			
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			
			String total = "";
			String line = "";
			while((line = reader.readLine()) != null) {
				total += line;
			}
			reader.close();
			stream.close();
			
			JSONArray jsonArr = new JSONArray(total);
			for(int i = 0; i < jsonArr.length(); ++i) {
				JSONObject json = jsonArr.getJSONObject(i);
				String title = (String)json.get("title");
				ArrayList<Expense> exp = this.JSONArrayToExpenses((JSONArray)json.get("expenses"));
				Category cat = new Category(title);
				cat.setExpenses(exp);
				
				cats.add(cat);
			}
			
			
			return cats;
		}
		catch(Exception e) {
			Log.e("ENA", e.getMessage());
			Toast.makeText(ct, R.string.error_read, Toast.LENGTH_SHORT).show();
			return null;
		}
	}
	
	public void write(ArrayList<Category> list) {
		File dest = new File(this.path);
		
		try {
			dest.createNewFile();
			FileOutputStream stream = new FileOutputStream(dest);
			OutputStreamWriter writer = new OutputStreamWriter(stream);
			
			JSONArray jsonArr = new JSONArray();
			for(int i = 0; i < list.size(); ++i) {
				JSONObject json = new JSONObject();
				Category cat = list.get(i);
				
				json.put("title", cat.getTitle());
				json.put("expenses", this.ExpensesToJSON(cat.getExpenses()));
				
				jsonArr.put(json);
			}
			
			writer.append(jsonArr.toString());
			writer.flush();
			writer.close();
			stream.flush();
			stream.close();
		}
		catch(Exception e) {
			Log.e("ENA", e.getMessage());
			Toast.makeText(ct, R.string.error_write, Toast.LENGTH_SHORT).show();
		}
	}
	
	private JSONArray ExpensesToJSON(ArrayList<Expense> list) throws JSONException {
		JSONArray jsonArr = new JSONArray();
		
		for(int i = 0; i < list.size(); ++i) {
			JSONObject json = new JSONObject();
			Expense exp = list.get(i);
			
			json.put("description", exp.getDescription());
			json.put("value", exp.getValue());
			json.put("date", exp.getDate().getTimeInMillis());
			
			jsonArr.put(json);
		}
		
		return jsonArr;
	}
	
	private ArrayList<Expense> JSONArrayToExpenses(JSONArray arr) throws JSONException {
		ArrayList<Expense> list = new ArrayList<Expense>();
		
		for(int i = 0; i < arr.length(); ++i) {
			JSONObject json = arr.getJSONObject(i);
			String description = json.getString("description");
			double value = json.getDouble("value");
			
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(json.getLong("date"));
			Expense exp = new Expense(date, value, description);
			list.add(exp);
		}
		
		return list;
	}
}
