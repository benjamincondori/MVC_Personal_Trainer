package com.android.kotlin.personaltrainer.utils;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class DatePickerDialog {

    public static void showDatePickerDialog(Context context, TextInputLayout inputText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(context, (view, selectedYear, selectedMonth, selectedDay) -> {
            inputText.getEditText().setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
        }, year, month, day);

        datePickerDialog.show();
    }

}
