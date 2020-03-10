package com.example.honoursproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.honoursproject.Model.SimplifiedPainRecord;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddSimplePainRecordActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener{

    private Calendar calendar;
    private RadioGroup radioStillPainGrp;
    private Button startDateBtn, endDateBtn, startTimeBtn, endTimeBtn, nextSeekerBtn;
    private int year, month, day, hour, minute, startYear, startMonth, startDay, startHour, startMinute, endYear, endMonth, endDay, endHour, endMinute;
    private LinearLayout endDateLayout;
    private SimplifiedPainRecord painRecord;
    private SeekBar painSeekBar;
    private ImageView painImageView;
    private TypedArray imageList;
    private TextView seekerTextView;
    private int maxPain, minPain, avgPain, state;
    private boolean stillInPain = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_simple_pain_record);

        nextSeekerBtn = findViewById(R.id.nextSeekerBtn);
        nextSeekerBtn.setOnClickListener(this);
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

        radioStillPainGrp = findViewById(R.id.stillPainRadioGrp);
        radioStillPainGrp.setOnCheckedChangeListener(this);

        painSeekBar = findViewById(R.id.painSeekBar);
        painSeekBar.setOnSeekBarChangeListener(this);
        painImageView = findViewById(R.id.painImageView);
        imageList = getApplicationContext().getResources().obtainTypedArray(R.array.pain_imgs_array);
        seekerTextView = findViewById(R.id.painSliderText);
        state = 0;
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

                                startYear = year;
                                startMonth = monthOfYear;
                                startDay = dayOfMonth;
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
                                startHour = hourOfDay;
                                startMinute = minute;
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
                                endHour = hourOfDay;
                                endMinute = minute;
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

                                endYear = year;
                                endMonth = monthOfYear;
                                endDay = dayOfMonth;
                                endDateBtn.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
                break;

            case R.id.nextSeekerBtn:
                if(state == 0){
                    maxPain = painSeekBar.getProgress();
                    painSeekBar.setProgress(0);
                    painImageView.setImageResource(imageList.getResourceId(0, 0));
                    seekerTextView.setText(R.string.min_pain_slider_text);
                    state++;
                }
                else if(state == 1){
                    minPain = painSeekBar.getProgress();
                    painSeekBar.setProgress(0);
                    painImageView.setImageResource(imageList.getResourceId(0, 0));
                    seekerTextView.setText(R.string.avg_pain_slider_text);
                    nextSeekerBtn.setText(R.string.btn_end);
                    state++;
                }
                else if(state == 2){
                    avgPain = painSeekBar.getProgress();
                    painSeekBar.setProgress(0);
                    painImageView.setImageResource(imageList.getResourceId(0, 0));
                    new MaterialAlertDialogBuilder(AddSimplePainRecordActivity.this)
                            .setTitle("Add pain entry")
                            .setMessage("Are you sure you want to add this pain record?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String startDateString = startYear + "-" + startMonth + "-" + startDay + " " + startHour + ":" + startMinute;
                                    String endDateString = endYear + "-" + endMonth + "-" + endDay + " " + endHour + ":" + endMinute;
                                    painRecord = new SimplifiedPainRecord(minPain, maxPain, avgPain, startDateString, endDateString, "head");

                                    Intent response = new Intent(AddSimplePainRecordActivity.this, MainPageActivity.class);
                                    response.putExtra("PainRecord", painRecord);
                                    setResult(RESULT_OK, response);
                                    finish();
                                    state = 0;
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();

                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(group.getId()){
            case R.id.stillPainRadioGrp:
                switch(checkedId)
                {
                    case R.id.yesStillPainRadio:
                        endDateLayout.setVisibility(View.GONE);
                        stillInPain = false;
                        break;
                    case R.id.noStillPainRadio:
                        endDateLayout.setVisibility(View.VISIBLE);
                        stillInPain = true;
                        break;
                }
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        painImageView.setImageResource(imageList.getResourceId(progress, 0));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
