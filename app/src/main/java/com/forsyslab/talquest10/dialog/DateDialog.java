package com.forsyslab.talquest10.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import com.forsyslab.talquest10.R;

import java.util.Calendar;


/**
 * Created by LENOVO on 02/02/2017.
 */

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText creationDate;
    TextView creationDateTextview;
    public DateDialog(View view)
    {
        creationDate = (EditText)view;
    }

public DateDialog(TextView textView) {creationDateTextview = textView;}
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = day+"-"+(month+1)+"-"+year;
        creationDate.setText(date);
    }
}
