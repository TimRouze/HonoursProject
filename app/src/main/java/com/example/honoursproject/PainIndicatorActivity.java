package com.example.honoursproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

public class PainIndicatorActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    SeekBar painSeekBar;
    ImageView painImageView;
    TypedArray imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain_indicator);

        painSeekBar = findViewById(R.id.painSeekBar);
        painSeekBar.setOnSeekBarChangeListener(this);
        painImageView = findViewById(R.id.painImageView);
        imageList = getApplicationContext().getResources().obtainTypedArray(R.array.pain_imgs_array);
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
