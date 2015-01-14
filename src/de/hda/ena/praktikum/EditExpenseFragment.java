package de.hda.ena.praktikum;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by tgrabosch on 14.01.2015.
 */

public class EditExpenseFragment extends Fragment {

    private RequestCodes mRequest;
    private Category mCategory;

    public EditExpenseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.enter_expense, container, false);

        Bundle params = getActivity().getIntent().getExtras();

        mRequest = (RequestCodes) params.get("Request");

        String catName = (String) params.get("CategoryName");
        mCategory = null;
        for(int i = 0; i < DataStore.cData.size(); ++i) {
            if (DataStore.cData.get(i).getTitle().equals(catName)) {
                mCategory = DataStore.cData.get(i);
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
                EditText txtDesc = (EditText) rootView.findViewById(R.id.txtDescription);
                EditText txtVal = (EditText) rootView.findViewById(R.id.txtValue);

                double val = Double.parseDouble(txtVal.getText().toString());

                if (val < 0) {
                    // Mache etwas sinnvolles
                }

                Expense exp = new Expense(Calendar.getInstance(), val, txtDesc.getText().toString());
                ExpenseActivity parent = (ExpenseActivity) getActivity();
            }
        });

        return rootView;
    }
}

