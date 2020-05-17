package com.example.honoursproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.honoursproject.Model.SimplifiedPainRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity implements View.OnClickListener{

    AnyChartView lineChartView;
    List<SimplifiedPainRecord> painRecordList;
    Button painLocBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        painRecordList = new ArrayList<>();
        painRecordList = (ArrayList)getIntent().getSerializableExtra("painList");

        painLocBtn = findViewById(R.id.pain_loc_btn);
        painLocBtn.setOnClickListener(this);
        lineChartView = findViewById(R.id.line_chart_view);

        createLineChart();
    }

    private void createLineChart(){
        Cartesian cartesian = createCartesian("Trend of Pain level since first entry.", "Pain level");

        List<DataEntry> seriesData = new ArrayList<>();
        for(SimplifiedPainRecord s : painRecordList) {
            seriesData.add(new LineChartActivity.CustomDataEntry(s.getStartDate(), s.getMinPainLevel(), s.getMaxPainLevel(), s.getAvgPainLevel()));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = createLine(cartesian, series1Mapping, "Minimum pain");
        Line series2 = createLine(cartesian, series2Mapping, "Maximum pain");
        Line series3 = createLine(cartesian, series3Mapping, "Average pain");

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        lineChartView.setChart(cartesian);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pain_loc_btn:
                Intent intent = new Intent(LineChartActivity.this, PieChartActivity.class);
                intent.putExtra("painList", (Serializable)painRecordList);
                finish();
                startActivity(intent);
        }


    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }
    }

    public Cartesian createCartesian(String title, String yTitle){
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title(title);

        cartesian.yAxis(0).title(yTitle);
        cartesian.xAxis(0).labels().padding(3d, 3d, 3d, 3d);

        return cartesian;
    }

    public Line createLine(Cartesian cartesian, Mapping seriesMapping, String name){
        Line series = cartesian.line(seriesMapping);
        series.name(name);
        series.hovered().markers().enabled(true);
        series.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        return series;
    }

}
