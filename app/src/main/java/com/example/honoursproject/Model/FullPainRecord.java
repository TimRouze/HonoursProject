package com.example.honoursproject.Model;

import java.util.Map;

public class FullPainRecord {

    private String id;
    private SimplifiedPainRecord painRecord;
    private boolean triedSomething;
    private String thingTried;
    private boolean tookMedicine;
    private boolean medicineWorked;
    private String medicineName;
    private PainType pType;
    private Map<String, Integer> painIncidenceOnActivities;
}
