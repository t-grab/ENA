package de.hda.ena.praktikum;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by tgrabosch on 14.01.2015.
 */

public class EditExpenseFragment extends Fragment {

	private RequestCodes mRequest;
	private Category mCategory;
	private Expense eEdit;

	public EditExpenseFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			final Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.enter_expense,
				container, false);

		Bundle params = getActivity().getIntent().getExtras();

		mRequest = (RequestCodes) params.get("ARG_REQUEST");

		String catName = (String) params.get("ARG_CATEGORY");
		mCategory = null;
		for (int i = 0; i < DataStore.cData.size(); ++i) {
			if (DataStore.cData.get(i).getTitle().equals(catName)) {
				mCategory = DataStore.cData.get(i);
				if (mRequest == RequestCodes.EDIT) {
					eEdit = mCategory.getExpense((int) params.get("ARG_ID"));
					EditText txtDesc = (EditText) rootView
							.findViewById(R.id.txtDescription);
					EditText txtVal = (EditText) rootView
							.findViewById(R.id.txtValue);
					txtDesc.setText(eEdit.getDescription());
					txtVal.setText(String.valueOf(eEdit.getValue()));
				}
				break;
			}
		}

		if (mCategory == null) {
			// Fehler sinnvoll behandeln
		}

		Button commit = (Button) rootView.findViewById(R.id.btnCommit);
		commit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText txtDesc = (EditText) rootView
						.findViewById(R.id.txtDescription);
				EditText txtVal = (EditText) rootView
						.findViewById(R.id.txtValue);
				double val;
				
				if(txtDesc.getText().toString().trim().equals("")) {
					Toast.makeText(rootView.getContext(), "Please enter a description", Toast.LENGTH_SHORT);
					return;
				}
				
				try {
					val = Double.parseDouble(txtVal.getText().toString());
				}catch(NumberFormatException ex) {
					Toast.makeText(rootView.getContext(), "Please enter an amount", Toast.LENGTH_SHORT);
					return;
				}
				
				if (val < 0) {
					Toast.makeText(rootView.getContext(),
							"Invalid Expense Amount", Toast.LENGTH_SHORT);
					return;
				}

				if (mRequest == RequestCodes.NEW) {
					Expense exp = new Expense(Calendar.getInstance(), val,
							txtDesc.getText().toString());
					mCategory.addExpense(exp);
				} else if (mRequest == RequestCodes.EDIT) {
					eEdit.setDescription(txtDesc.getText().toString());
					eEdit.setValue(val);
					mCategory.setExpense(eEdit);
				}

				EditExpenseActivity parent = (EditExpenseActivity) getActivity();
				// parent.finishActivity(mRequest.ordinal());
				Intent returnIntent = new Intent();
				parent.setResult(android.app.Activity.RESULT_OK, returnIntent);
				parent.finish();
			}
		});

		Button btnDelete = (Button) rootView.findViewById(R.id.btnDelete);

		btnDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCategory.removeExpense(eEdit);
				EditExpenseActivity parent = (EditExpenseActivity) getActivity();
				Intent returnIntent = new Intent();
				parent.setResult(android.app.Activity.RESULT_OK, returnIntent);
				parent.finish();
			}
		});

		if (mRequest != RequestCodes.EDIT) {
			btnDelete.setVisibility(View.GONE);
		}

		return rootView;
	}
}
