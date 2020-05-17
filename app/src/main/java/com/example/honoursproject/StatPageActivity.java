package com.example.honoursproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.honoursproject.Model.SimplifiedPainRecord;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.opencsv.CSVWriter;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.Duration;

public class StatPageActivity extends AppCompatActivity implements Button.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    final int LINE = 12, PIE = 13;

    List<SimplifiedPainRecord> painRecordList;
    long frequency, duration;
    Button painLevelsBtn, painLocBtn, csvBtn;
    //Button csvEmailBtn;
    AnyChartView lineChartView, pieChartView;
    String csv;

    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stat_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        painRecordList = new ArrayList<>();
        Intent intent = getIntent();
        painRecordList = (ArrayList)intent.getSerializableExtra("painList");

        painLevelsBtn = findViewById(R.id.pain_values_btn);
        painLevelsBtn.setOnClickListener(this);
        painLocBtn = findViewById(R.id.pain_loc_btn);
        painLocBtn.setOnClickListener(this);

        csvBtn = findViewById(R.id.csv_btn);
        csvBtn.setOnClickListener(this);
        //csvEmailBtn = findViewById(R.id.csv_mail_btn);
        //csvEmailBtn.setOnClickListener(this);
        csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/PainRecords.csv");


        mUser = FirebaseAuth.getInstance().getCurrentUser();

        if(painRecordList.size() != 0) {
            processData();
        }
        else{
            finish();
            Toast.makeText(getApplicationContext(), "Please wait until we get the data from the database", Toast.LENGTH_LONG).show();
            startActivity(new Intent(StatPageActivity.this, MainPageActivity.class));
        }
        lineChartView = findViewById(R.id.line_chart_view);
        pieChartView = findViewById(R.id.pie_chart_view);
    }


    private void processData(){
        int avgPain;
        long nbDayFreq = 0, nbDayDur = 0;

        avgPain = averagePain();
        duration = duration();
        frequency = frequency();


        if(duration > 24){
            nbDayDur = duration/24;
            duration = duration%24;
        }
        if(frequency > 24){
            nbDayFreq = frequency/24;
            frequency = frequency%24;
        }

        System.err.println("avgPain: " + avgPain);
        System.err.println("frequency: " + frequency);
        System.err.println("duration: " + duration);
    }

    private long frequency(){
        Date tmpDate = new Date();
        long frequency;
        try {
            Date theDate = new SimpleDateFormat("yyyy-MM-dd").parse(painRecordList.get(0).getStartDate());
            Date diff = new Date(tmpDate.getTime() - theDate.getTime());
            long period = diff.getTime()/(1000*3600);
            frequency = period / painRecordList.size();
            return frequency;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private long duration(){
        long dur = 0, duration;
        Date tmpEnd, tmpStart;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 0; i < painRecordList.size();i++){
            try {
                tmpEnd = df.parse(painRecordList.get(i).getEndDate());
                tmpStart = df.parse(painRecordList.get(i).getStartDate());
                dur += new Date(tmpEnd.getTime() - tmpStart.getTime()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        dur /= (1000*3600);
        duration = dur/painRecordList.size();
        return duration;
    }

    private int averagePain(){
        int i, avgPain = 0;
        for(i = 0; i < painRecordList.size();i++){
            avgPain += painRecordList.get(i).getAvgPainLevel();
        }
        avgPain = avgPain/i;
        return avgPain;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pain_loc_btn:
                intent = new Intent(StatPageActivity.this, PieChartActivity.class);
                intent.putExtra("painList", (Serializable)painRecordList);
                startActivity(intent);
                break;

            case R.id.pain_values_btn:
                intent = new Intent(StatPageActivity.this, LineChartActivity.class);
                intent.putExtra("painList", (Serializable)painRecordList);
                startActivity(intent);
                break;
            case R.id.csv_btn:
                writeFile();
                Toast.makeText(getApplicationContext(), "File saved in: " + csv + "!", Toast.LENGTH_LONG).show();
                break;
            /*case R.id.csv_mail_btn:
                writeFile();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mUser.getEmail()});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your pain records");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello!\n\tPlease find attached the csv file containing your pain records.\n\n Regards,\nManage my pain app team.");

                File file = new File(csv);
                Uri uri = Uri.fromFile(file);
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
                break;*/
        }
    }

    private void writeFile(){
        CSVWriter writer = null;

        try {
            writer = new CSVWriter(new FileWriter(csv));

            List<String[]> data = new ArrayList<String[]>();
            for(SimplifiedPainRecord s : painRecordList){
                data.add(new String[]{"" + s.getStartDate(), "" + s.getEndDate(), "" + s.getAvgPainLevel(), "" + s.getMaxPainLevel(),
                        "" + s.getMinPainLevel(), "" + s.getPainLocation()});
            }

            String[] headerRecord = {"Start Date", "End Date", "Average pain", "Max pain", "Min pain", "Pain location"};
            writer.writeNext(headerRecord);
            writer.writeAll(data);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Toast.makeText(StatPageActivity.this, "Not supported yet", Toast.LENGTH_SHORT);
        } else if (id == R.id.nav_records) {
            Intent intent = new Intent(StatPageActivity.this, MainPageActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_statistics) {

        } else if (id == R.id.nav_disconnect){
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(StatPageActivity.this, LogInActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
