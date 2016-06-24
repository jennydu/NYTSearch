package com.codepath.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.nytimessearch.DatePickerFragment;
import com.codepath.nytimessearch.Filters;
import com.codepath.nytimessearch.R;

import java.util.Calendar;

public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String order;
    String topic;
    String date;

    Filters myFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myFilter = new Filters();

        Spinner spOrder = (Spinner) findViewById(R.id.spOrder);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,
                getResources().getStringArray(R.array.order_array));
// Apply the adapter to the spinner
        spOrder.setAdapter(adapter);
        order = spOrder.getSelectedItem().toString();

        Spinner spTopics = (Spinner) findViewById(R.id.spTopics);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item,
                getResources().getStringArray(R.array.topics_array));
        spTopics.setAdapter(adapter1);
        topic = spTopics.getSelectedItem().toString();


        myFilter.setSort(order.toLowerCase());
        myFilter.setNews_desk(topic);


    }

    public void onSubmit(View v){

        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result

        // maybe i should make it not pass anything back if the person did not choose anything--------------
        data.putExtra("filters", myFilter);

        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent

    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String month = String.valueOf(monthOfYear + 1);
        String day = String.valueOf(dayOfMonth);
        String yr = String.valueOf(year);

        TextView dateChosen = (TextView) findViewById(R.id.date_chosen);
        dateChosen.setText(month+"/" + day + "/" + yr);

        if (month.length() < 2){
            month = String.format("0%s" , month);
        }

        date = yr+month+day;
        myFilter.setBegin_date(date);
    }



}
