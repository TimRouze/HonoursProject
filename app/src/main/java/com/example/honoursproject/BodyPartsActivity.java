package com.example.honoursproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.honoursproject.Model.SimplifiedPainRecord;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BodyPartsActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView headIV, trunkIV, leftArmIV, leftForearmIV, rightArmIV, rightForearmIV, rightLegIV, rightThighIV, leftLegIV, leftThighIV;
    private List<String> bodyPartsList;
    private SimplifiedPainRecord painRecord;
    private Button addRecordBtn;

    private DatabaseReference mReference;
    private FirebaseDatabase mDatabase;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_parts);

        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mReference = mDatabase.getReference(mUser.getUid()).child("simplePainRecords");

        addRecordBtn = findViewById(R.id.add_record_btn);
        addRecordBtn.setOnClickListener(this);

        painRecord = (SimplifiedPainRecord)getIntent().getSerializableExtra("PainRecord");
        bodyPartsList = new ArrayList<>();

        headIV = findViewById(R.id.head_image);
        headIV.setOnClickListener(this);
        trunkIV = findViewById(R.id.body_image);
        trunkIV.setOnClickListener(this);
        leftArmIV = findViewById(R.id.left_arm_image);
        leftArmIV.setOnClickListener(this);
        leftForearmIV = findViewById(R.id.left_forearm_image);
        leftForearmIV.setOnClickListener(this);
        leftLegIV = findViewById(R.id.left_leg_image);
        leftLegIV.setOnClickListener(this);
        leftThighIV = findViewById(R.id.left_thigh_image);
        leftThighIV.setOnClickListener(this);
        rightArmIV = findViewById(R.id.right_arm_image);
        rightArmIV.setOnClickListener(this);
        rightForearmIV = findViewById(R.id.right_forearm_image);
        rightForearmIV.setOnClickListener(this);
        rightLegIV = findViewById(R.id.right_leg_image);
        rightLegIV.setOnClickListener(this);
        rightThighIV = findViewById(R.id.right_thigh_image);
        rightThighIV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(bodyPartsList.size()>0) {
            System.err.println(bodyPartsList.get(0));
        }
        if(v.getId() == R.id.head_image) {
            for (String s : bodyPartsList) {
                if (s.equalsIgnoreCase("head")) {
                    headIV.setImageResource(R.drawable.head);
                    bodyPartsList.remove(s);
                    return;
                }
            }
            headIV.setImageResource(R.drawable.red_head);
            bodyPartsList.add("head");
        }

        else if(v.getId() == R.id.left_arm_image) {
            for (String s : bodyPartsList) {
                if (s.equalsIgnoreCase("left arm")) {
                    leftArmIV.setImageResource(R.drawable.left_arm);
                    bodyPartsList.remove(s);
                    return;
                }
            }
            leftArmIV.setImageResource(R.drawable.red_left_arm);
            bodyPartsList.add("left arm");
        }

        else if(v.getId() == R.id.left_forearm_image) {
            for (String s : bodyPartsList) {
                if (s.equalsIgnoreCase("left forearm")) {
                    leftForearmIV.setImageResource(R.drawable.left_forearm);
                    bodyPartsList.remove(s);
                    return;
                }
            }
            leftForearmIV.setImageResource(R.drawable.red_left_forearm);
            bodyPartsList.add("left forearm");
        }

        else if(v.getId() == R.id.left_leg_image) {
            for (String s : bodyPartsList) {
                if (s.equalsIgnoreCase("left leg")) {
                    leftLegIV.setImageResource(R.drawable.left_leg);
                    bodyPartsList.remove(s);
                    return;
                }
            }
            leftLegIV.setImageResource(R.drawable.red_left_leg);
            bodyPartsList.add("left leg");
        }

        else if(v.getId() == R.id.left_thigh_image) {
            for (String s : bodyPartsList) {
                if (s.equalsIgnoreCase("left thigh")) {
                    leftThighIV.setImageResource(R.drawable.left_thigh);
                    bodyPartsList.remove(s);
                    return;
                }
            }
            leftThighIV.setImageResource(R.drawable.red_left_thigh);
            bodyPartsList.add("left thigh");
        }

        else if(v.getId() == R.id.right_arm_image) {
            for (String s : bodyPartsList) {
                if (s.equalsIgnoreCase("right arm")) {
                    rightArmIV.setImageResource(R.drawable.right_arm);
                    bodyPartsList.remove(s);
                    return;
                }
            }
            rightArmIV.setImageResource(R.drawable.red_right_arm);
            bodyPartsList.add("right arm");
        }

        else if(v.getId() == R.id.right_forearm_image) {
            for (String s : bodyPartsList) {
                if (s.equalsIgnoreCase("right forearm")) {
                    rightForearmIV.setImageResource(R.drawable.right_forearm);
                    bodyPartsList.remove(s);
                    return;
                }
            }
            rightForearmIV.setImageResource(R.drawable.red_right_forearm);
            bodyPartsList.add("right forearm");
        }

        else if(v.getId() == R.id.right_leg_image) {
            for (String s : bodyPartsList) {
                if (s.equalsIgnoreCase("right leg")) {
                    rightLegIV.setImageResource(R.drawable.right_leg);
                    bodyPartsList.remove(s);
                    return;
                }
            }
            rightLegIV.setImageResource(R.drawable.red_right_leg);
            bodyPartsList.add("right leg");
        }

        else if(v.getId() == R.id.right_thigh_image) {
            for (String s : bodyPartsList) {
                if (s.equalsIgnoreCase("right thigh")) {
                    rightThighIV.setImageResource(R.drawable.right_thigh);
                    bodyPartsList.remove(s);
                    return;
                }
            }
            rightThighIV.setImageResource(R.drawable.red_right_thigh);
            bodyPartsList.add("right thigh");
        }

        else if(v.getId() == R.id.body_image){
            for (String s : bodyPartsList) {
                if (s.equalsIgnoreCase("trunk")) {
                    trunkIV.setImageResource(R.drawable.trunk);
                    bodyPartsList.remove(s);
                    return;
                }
            }
            trunkIV.setImageResource(R.drawable.red_trunk);
            bodyPartsList.add("trunk");
        }

        else if(v.getId() == R.id.add_record_btn){
            if(bodyPartsList.size() == 0){
                Toast.makeText(getApplicationContext(), "Please select a body part where your pain occured.", Toast.LENGTH_SHORT).show();
                return;
            }
            new MaterialAlertDialogBuilder(BodyPartsActivity.this)
                    .setTitle("Add pain entry")
                    .setMessage("Are you sure you want to add this pain record?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String ids = "";
                            List<SimplifiedPainRecord> simplePainRecordList = (ArrayList)getIntent().getSerializableExtra("painList");
                            if(simplePainRecordList == null){
                                ids = "0_key";
                            }
                            else{
                                int id = simplePainRecordList.size();
                                ids = id + "_key";
                            }
                            painRecord.setPainLocation(bodyPartsList);
                            mReference.child(ids).setValue(painRecord);
                            startActivity(new Intent(BodyPartsActivity.this, MainPageActivity.class));
                            /*painRecord.setPainLocation(bodyPartsList);
                            Intent response = new Intent(BodyPartsActivity.this, AddSimplePainRecordActivity.class);
                            response.putExtra("PainRecord", painRecord);
                            response.putExtra("from", "bodyParts");
                            startActivity(response);
                            //setResult(RESULT_OK, response);*/
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }
    }
}
