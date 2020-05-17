package com.example.honoursproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.honoursproject.Model.SimplifiedPainRecord;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddSimplePainRecordActivity extends AppCompatActivity implements View.OnClickListener, SwitchMaterial.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener{

    private Calendar calendar;
    private SwitchMaterial stillPainSwitch;
    private Button startDateBtn, endDateBtn, startTimeBtn, endTimeBtn, nextSeekerBtn;
    private int year, month, day, hour, minute, startYear, startMonth, startDay, startHour, startMinute, endYear, endMonth, endDay, endHour, endMinute;
    private LinearLayout endDateLayout;
    private SimplifiedPainRecord painRecord;
    private SeekBar painSeekBar;
    private ImageView painImageView;
    private TypedArray imageList;
    private TextView seekerTextView;
    private int maxPain, minPain, avgPain, state;
    private final int ADD_SIMPLE_RECORD_CODE = 12;


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

        stillPainSwitch = findViewById(R.id.stillPainSwitch);
        stillPainSwitch.setOnCheckedChangeListener(this);

        painSeekBar = findViewById(R.id.painSeekBar);
        painSeekBar.setOnSeekBarChangeListener(this);
        painImageView = findViewById(R.id.painImageView);
        imageList = getApplicationContext().getResources().obtainTypedArray(R.array.pain_imgs_array);
        seekerTextView = findViewById(R.id.painSliderText);
        state = 0;

        String callingFrom = getIntent().getStringExtra("from");
        if(callingFrom != null)
        {
            getRecord(getIntent());
        }
    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog;
        TimePickerDialog timePickerDialog;
        if(v.getId() == R.id.selectStartDateButton) {
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
        }

        else if(v.getId() == R.id.selectStartTimeButton) {
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
        }

        else if(v.getId() == R.id.selectEndTimeButton) {
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
        }

        else if(v.getId() == R.id.selectEndDateButton) {
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
        }

        else if(v.getId() == R.id.nextSeekerBtn) {
            if (state == 0) {
                if(painSeekBar.getProgress() == 0){
                    Toast.makeText(getApplicationContext(), "Maximum pain can't be equal to 0.", Toast.LENGTH_SHORT).show();
                    return;
                }
                maxPain = painSeekBar.getProgress();
                painSeekBar.setProgress(0);
                painImageView.setImageResource(imageList.getResourceId(0, 0));
                seekerTextView.setText(R.string.min_pain_slider_text);
                state++;
            } else if (state == 1) {
                minPain = painSeekBar.getProgress();
                painSeekBar.setProgress(0);
                painImageView.setImageResource(imageList.getResourceId(0, 0));
                seekerTextView.setText(R.string.avg_pain_slider_text);
                state++;
            } else if (state == 2) {
                if(painSeekBar.getProgress() == 0){
                    Toast.makeText(getApplicationContext(), "Average pain can't be equal to 0.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String startDateString = startYear + "-" + startMonth + "-" + startDay + " " + startHour + ":" + startMinute;
                String endDateString = endYear + "-" + endMonth + "-" + endDay + " " + endHour + ":" + endMinute;
                if(startDateString.equalsIgnoreCase("0-0-0 0:0") || endDateString.equalsIgnoreCase("0-0-0 0:0") ||
                startHour == 0 || endHour == 0 || startYear == 0 || endYear == 0){
                    Toast.makeText(getApplicationContext(), "Please enter values for the dates AND times.", Toast.LENGTH_SHORT).show();
                    return;
                }
                avgPain = painSeekBar.getProgress();
                painSeekBar.setProgress(0);
                painImageView.setImageResource(imageList.getResourceId(0, 0));
                state = 0;
                painRecord = new SimplifiedPainRecord(minPain, maxPain, avgPain, startDateString, endDateString);
                Intent intent = new Intent(AddSimplePainRecordActivity.this, BodyPartsActivity.class);
                intent.putExtra("PainRecord", painRecord);
                intent.putExtra("painList", getIntent().getSerializableExtra("painList"));
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case ADD_SIMPLE_RECORD_CODE:
                if(resultCode == RESULT_OK){
                    System.err.println("HELLO");
                    SimplifiedPainRecord record = (SimplifiedPainRecord)data.getSerializableExtra("PainRecord");
                    Intent response = new Intent(AddSimplePainRecordActivity.this, MainPageActivity.class);
                    response.putExtra("PainRecord", record);
                    setResult(RESULT_OK, response);
                    finish();
                }
                break;
        }
    }

    private void getRecord(Intent intent){
        painRecord = (SimplifiedPainRecord)intent.getSerializableExtra("PainRecord");
        if(painRecord != null) {
            System.err.println("hellooooooo");
            Intent response = new Intent(AddSimplePainRecordActivity.this, MainPageActivity.class);
            response.putExtra("PainRecord", painRecord);
            setResult(RESULT_OK, response);
            finish();
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(!isChecked){
            endDateLayout.setVisibility(View.VISIBLE);
            return;
        }
        else{
            endDateLayout.setVisibility(View.GONE);
            return;
        }
    }
}
