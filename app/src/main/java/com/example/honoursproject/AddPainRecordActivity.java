package com.example.honoursproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddPainRecordActivity extends AppCompatActivity implements View.OnClickListener, Spinner.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener{

    private Calendar calendar;
    private Button startDateBtn, endDateBtn, startTimeBtn, endTimeBtn, nextBtn;
    private RadioGroup radioStillPainGrp, radioTakeMedicineGrp, radioEffectiveGrp, radioDoneSomethingGrp;
    private int year, month, day, hour, minute;
    private LinearLayout endDateLayout, tookMedicineLayout, triedThingsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pain_record);
        nextBtn = findViewById(R.id.nextButton);
        nextBtn.setOnClickListener(this);

        Spinner triedSpinner = (Spinner) findViewById(R.id.thingsTriedSpinner);
        ArrayAdapter<CharSequence> betterAdapter = ArrayAdapter.createFromResource(this,
                R.array.things_tried_array, android.R.layout.simple_spinner_item);
        betterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        triedSpinner.setAdapter(betterAdapter);
        triedSpinner.setOnItemSelectedListener(this);

        Spinner worseSpinner = (Spinner) findViewById(R.id.painWorstSpinner);
        ArrayAdapter<CharSequence> worseAdapter = ArrayAdapter.createFromResource(this,
                R.array.things_worse_array, android.R.layout.simple_spinner_item);
        worseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        worseSpinner.setAdapter(worseAdapter);
        worseSpinner.setOnItemSelectedListener(this);


        startDateBtn = findViewById(R.id.selectStartDateButton);
        startDateBtn.setOnClickListener(this);
        endDateBtn = findViewById(R.id.selectEndDateButton);
        endDateBtn.setOnClickListener(this);
        startTimeBtn = findViewById(R.id.selectStartTimeButton);
        startTimeBtn.setOnClickListener(this);
        endTimeBtn = findViewById(R.id.selectEndTimeButton);
        endTimeBtn.setOnClickListener(this);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        endDateLayout = findViewById(R.id.endDateLayout);
        tookMedicineLayout = findViewById(R.id.tookMedicineLayout);
        triedThingsLayout = findViewById(R.id.triedThingsLayout);

        radioStillPainGrp = (RadioGroup) findViewById(R.id.stillPainRadioGrp);
        radioStillPainGrp.setOnCheckedChangeListener(this);

        radioTakeMedicineGrp = (RadioGroup) findViewById(R.id.medicineRadioGrp);
        radioTakeMedicineGrp.setOnCheckedChangeListener(this);

        radioDoneSomethingGrp = (RadioGroup) findViewById(R.id.doneSomethingRadioGrp);
        radioDoneSomethingGrp.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog;
        TimePickerDialog timePickerDialog;
        switch(v.getId()){
            case R.id.selectStartDateButton:
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                startDateBtn.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
                break;

            case R.id.selectStartTimeButton:
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                startTimeBtn.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
                break;

            case R.id.selectEndTimeButton:
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                endTimeBtn.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
                break;

            case R.id.selectEndDateButton:
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                endDateBtn.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
                break;

            case R.id.nextButton:
                startActivity(new Intent(AddPainRecordActivity.this, AddRecordSlidersActivity.class));
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem;
        switch (view.getId()){
            case R.id.painWorstSpinner:
                selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equalsIgnoreCase("other")){
                    findViewById(R.id.otherPainWorstEditText).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.otherPainWorstEditText).setVisibility(View.GONE);
                }
                break;

            case R.id.thingsTriedSpinner:
                selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equalsIgnoreCase("other")){
                    findViewById(R.id.OtherTriedEditText).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.OtherTriedEditText).setVisibility(View.GONE);
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(group.getId()){
            case R.id.doneSomethingRadioGrp:
                switch(checkedId)
                {
                    case R.id.yesDoneSomethingRadio:
                        triedThingsLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.noDoneSomethingRadio:
                        triedThingsLayout.setVisibility(View.GONE);
                        break;
                }
                break;

            case R.id.medicineEffectiveRadioGrp:

                break;

            case R.id.medicineRadioGrp:
                switch(checkedId)
                {
                    case R.id.yesMedicineRadio:
                        tookMedicineLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.noMedicineRadio:
                        tookMedicineLayout.setVisibility(View.GONE);
                        break;
                }
                break;

            case R.id.stillPainRadioGrp:
                switch(checkedId)
                {
                    case R.id.yesStillPainRadio:
                        endDateLayout.setVisibility(View.GONE);
                        break;
                    case R.id.noStillPainRadio:
                        endDateLayout.setVisibility(View.VISIBLE);
                        break;
                }
                break;
        }
    }
}
