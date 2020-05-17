package com.example.honoursproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.honoursproject.Model.SimplifiedPainRecord;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private final int ADD_SIMPLE_RECORD_CODE = 12;
    private SimplifiedPainRecord  record;
    private List<SimplifiedPainRecord> simplePainRecordList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SimplePainRecordAdapter mAdapter;
    private DatabaseReference mReference;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;

    String elementName = "SimpleRecord";
    int nbElements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, AddSimplePainRecordActivity.class);
                intent.putExtra("painList", (Serializable)simplePainRecordList);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.pain_records_recycler_view);
        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new MaterialAlertDialogBuilder(MainPageActivity.this)
                        .setTitle("Modify pain entry")
                        .setMessage("Do you want to modify this pain record?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                return false;
            }
        });
        //feghbihbfce
        mAdapter = new SimplePainRecordAdapter(simplePainRecordList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mReference = mDatabase.getReference(mUser.getUid()).child("simplePainRecords");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    SimplifiedPainRecord value = ds.getValue(SimplifiedPainRecord.class);
                    Log.d("TAG", "Value is: " + value);
                    System.out.println();
                    simplePainRecordList.add(value);
                    mAdapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w( "Failed to read value.", error.toException());
            }
        });

        Toast.makeText(getApplicationContext(), "Retrieving Data...", Toast.LENGTH_LONG).show();
        nbElements = 0;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_page_, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Toast.makeText(MainPageActivity.this, "Not supported yet", Toast.LENGTH_SHORT);

        } else if (id == R.id.nav_records) {

        } else if (id == R.id.nav_statistics) {
            Intent intent = new Intent(MainPageActivity.this, StatPageActivity.class);
            intent.putExtra("painList", (Serializable)simplePainRecordList);
            startActivity(intent);

        } else if (id == R.id.nav_disconnect){
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(MainPageActivity.this, LogInActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putSerializable("painRecords", (Serializable) simplePainRecordList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case ADD_SIMPLE_RECORD_CODE:
                if(resultCode == RESULT_OK){
                    record = (SimplifiedPainRecord)data.getSerializableExtra("PainRecord");
                    int id = simplePainRecordList.size();
                    String ids = id + "_key";
                    mReference.child(ids).setValue(record);
                }
                break;
        }
    }


}
