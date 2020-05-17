package com.example.honoursproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.honoursproject.Model.SimplifiedPainRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity implements View.OnClickListener {

    AnyChartView pieChartView;
    List<SimplifiedPainRecord> painRecordList;
    Button painValBtn;
    int iHead = 0, iTrunk = 0, iLeftArm = 0, iLeftForearm = 0, iLeftLeg = 0, iLeftThigh = 0, iRightArm = 0, iRightForearm = 0, iRightLeg = 0, iRightThigh = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        painRecordList = new ArrayList<>();
        painRecordList = (ArrayList)getIntent().getSerializableExtra("painList");

        painValBtn = findViewById(R.id.pain_loc_btn);
        painValBtn.setOnClickListener(this);
        pieChartView = findViewById(R.id.pie_chart_view);

        createPieChart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pain_loc_btn:
                Intent intent = new Intent(PieChartActivity.this, LineChartActivity.class);
                intent.putExtra("painList", (Serializable)painRecordList);
                finish();
                startActivity(intent);
        }
    }

    public void createPieChart(){
        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(PieChartActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        for(int i = 0; i < painRecordList.size(); i++){
            System.err.println("SIZE: " + painRecordList.size() + " Where we at: " + i);
            for(String s : painRecordList.get(i).getPainLocation()){
                switching(s);
            }
        }
        List<DataEntry> data = checkVal();


        pie.data(data);

        pie.title("Parts where your pain occurs the most");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Body parts")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        pieChartView.setChart(pie);
    }

    public List<DataEntry> checkVal(){
        List<DataEntry> data = new ArrayList<>();
        if(iHead != 0) {
            data.add(new ValueDataEntry("Head", iHead));
        }
        if(iTrunk != 0) {
            data.add(new ValueDataEntry("trunk", iTrunk));
        }
        if(iLeftArm != 0) {
            data.add(new ValueDataEntry("Left Arm", iLeftArm));
        }
        if(iLeftForearm != 0) {
            data.add(new ValueDataEntry("Left Forearm", iLeftForearm));
        }
        if(iLeftLeg != 0) {
            data.add(new ValueDataEntry("Left Leg", iLeftLeg));
        }
        if(iLeftThigh != 0) {
            data.add(new ValueDataEntry("Left Thigh", iLeftThigh));
        }
        if(iRightLeg != 0) {
            data.add(new ValueDataEntry("Right Leg", iRightLeg));
        }
        if(iRightThigh != 0) {
            data.add(new ValueDataEntry("Right Thigh", iRightThigh));
        }
        if(iRightArm != 0) {
            data.add(new ValueDataEntry("Right Arm", iRightArm));
        }
        if(iRightForearm != 0) {
            data.add(new ValueDataEntry("Right Forearm", iRightForearm));
        }
        return data;
    }

    public void switching(String s){
        System.err.println("HEllo hello String is: " + s);
        if(s.equalsIgnoreCase("head")) {
            iHead++;
        }
        else if(s.equalsIgnoreCase("trunk")) {
            iTrunk++;
        }
        else if(s.equalsIgnoreCase("left arm")) {
            iLeftArm++;
        }
        else if(s.equalsIgnoreCase("left forearm")) {
            iLeftForearm++;
        }
        else if(s.equalsIgnoreCase("left leg")) {
            iLeftLeg++;
        }
        else if(s.equalsIgnoreCase("left Thigh")) {
            iLeftThigh++;
        }
        else if(s.equalsIgnoreCase("right arm")){
                iRightArm++;
        }
        else if(s.equalsIgnoreCase("right forearm")){
                iRightForearm++;
        }
        else if(s.equalsIgnoreCase("right leg")){
                iRightLeg++;
        }
        else if(s.equalsIgnoreCase("right thigh")) {
            iRightThigh++;
        }
    }
}
