package de.hda.ena.praktikum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosemaxmoney);
		Button btnConfirm = (Button) findViewById(R.id.button1);

		final SettingsActivity me = this;

		EditText txtE = (EditText) findViewById(R.id.editText1);
		txtE.setText(String.valueOf(DataStore.dMaxExpense));

		btnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				EditText txtE = (EditText) findViewById(R.id.editText1);

				double val;
				try {
					val = Double.parseDouble(txtE.getText().toString());
				} catch (NumberFormatException ex) {
					Toast.makeText(me, "Please enter an amount",
							Toast.LENGTH_SHORT);
					return;
				}

				File dest = new File(DataStore.sSettingsPath);
				try {
					dest.createNewFile();
					FileOutputStream stream = new FileOutputStream(dest);
					OutputStreamWriter writer = new OutputStreamWriter(stream);

					JSONObject json = new JSONObject();

					json.put("dMaxExpense", val);

					writer.append(json.toString());
					writer.flush();
					writer.close();
					stream.flush();
					stream.close();
				} catch (Exception ex) {
					Log.e("ENA", ex.getMessage());
				}

				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
