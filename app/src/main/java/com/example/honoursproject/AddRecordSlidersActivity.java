package com.example.honoursproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class AddRecordSlidersActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private TextView activityText, moodText, sleepText, socialText, walkingText, workText, lifeEnjoymentText;
    private SeekBar activitySeekBar, moodSeekBar, sleepSeekBar, socialSeekBar, walkingSeekBar, workSeekBar, lifeEnjoymentSeekBar;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record_sliders);

        activitySeekBar = findViewById(R.id.activitySeekBar);
        activitySeekBar.setOnSeekBarChangeListener(this);
        activityText = findViewById(R.id.activitySeekBarText);

        moodSeekBar = findViewById(R.id.moodSeekBar);
        moodSeekBar.setOnSeekBarChangeListener(this);
        moodText = findViewById(R.id.moodSeekBarText);

        sleepSeekBar = findViewById(R.id.sleepSeekBar);
        sleepSeekBar.setOnSeekBarChangeListener(this);
        sleepText = findViewById(R.id.sleepSeekBarText);

        socialSeekBar = findViewById(R.id.socialSeekBar);
        socialSeekBar.setOnSeekBarChangeListener(this);
        socialText = findViewById(R.id.socialSeekBarText);

        walkingSeekBar = findViewById(R.id.walkingSeekBar);
        walkingSeekBar.setOnSeekBarChangeListener(this);
        walkingText = findViewById(R.id.walkingSeekBarText);

        workSeekBar = findViewById(R.id.workSeekBar);
        workSeekBar.setOnSeekBarChangeListener(this);
        workText = findViewById(R.id.workSeekBarText);

        lifeEnjoymentSeekBar = findViewById(R.id.lifeEnjoymentSeekBar);
        lifeEnjoymentSeekBar.setOnSeekBarChangeListener(this);
        lifeEnjoymentText = findViewById(R.id.lifeEnjoymentSeekBarText);

        btn_next = findViewById(R.id.btn_next_sliders);
        btn_next.setOnClickListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int val;
        switch(seekBar.getId()){
            case R.id.activitySeekBar:
                val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                activityText.setText("" + progress);
                activityText.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                break;

            case R.id.workSeekBar:
                val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                workText.setText("" + progress);
                workText.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                break;

            case R.id.walkingSeekBar:
                val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                walkingText.setText("" + progress);
                walkingText.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                break;

            case R.id.sleepSeekBar:
                val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                sleepText.setText("" + progress);
                sleepText.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                break;

            case R.id.lifeEnjoymentSeekBar:
                val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                lifeEnjoymentText.setText("" + progress);
                lifeEnjoymentText.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                break;

            case R.id.moodSeekBar:
                val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                moodText.setText("" + progress);
                moodText.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                break;

            case R.id.socialSeekBar:
                val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                socialText.setText("" + progress);
                socialText.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                break;



        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next_sliders:
                startActivity(new Intent(AddRecordSlidersActivity.this, PainIndicatorActivity.class));
                break;
        }
    }
}
