package com.example.blue_books;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment{
    private DatePickerDialog.OnDateSetListener dateSetListener;
    DatePickerDialog datePickerDialog;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
     //   super.onCreate(savedInstanceState);

       final Calendar c = Calendar.getInstance();
       int year = c.get(Calendar.YEAR);
       int month = c.get(Calendar.MONTH);
       int day= c.get(Calendar.DAY_OF_MONTH);

       dateSetListener = (DatePickerDialog.OnDateSetListener)getTargetFragment();
        datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month+1, day);
       return datePickerDialog;

}}