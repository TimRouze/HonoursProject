package com.example.honoursproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class SimplifiedPainRecord implements Serializable {
        private String id;
        private Integer minPainLevel;
        private Integer maxPainLevel;
        private Integer avgPainLevel;
        private String startDate;
        private String endDate;
        private String painLocation;


        public SimplifiedPainRecord(){

        }
        public SimplifiedPainRecord(String id, Integer minPainLevel, Integer maxPainLevel, Integer avgPainLevel, String sDate, String eDate, String pLoc) {
            this.id = id;
            this.minPainLevel = minPainLevel;
            this.maxPainLevel = maxPainLevel;
            this.avgPainLevel = avgPainLevel;
            startDate = sDate;
            if(eDate != null){
                endDate = eDate;
            }
            else{
                endDate = "";
            }
            painLocation = pLoc;
        }

    public SimplifiedPainRecord(Integer minPainLevel, Integer maxPainLevel, Integer avgPainLevel, String sDate, String eDate, String pLoc) {
        this.minPainLevel = minPainLevel;
        this.maxPainLevel = maxPainLevel;
        this.avgPainLevel = avgPainLevel;
        startDate = sDate;
        if(eDate != null){
            endDate = eDate;
        }
        else{
            endDate = "";
        }
        painLocation = pLoc;
    }

    public Integer getMinPainLevel() {
        return minPainLevel;
    }

    public void setMinPainLevel(Integer minPainLevel) {
        this.minPainLevel = minPainLevel;
    }

    public Integer getMaxPainLevel() {
        return maxPainLevel;
    }

    public void setMaxPainLevel(Integer maxPainLevel) {
        this.maxPainLevel = maxPainLevel;
    }

    public Integer getAvgPainLevel() {
        return avgPainLevel;
    }

    public void setAvgPainLevel(Integer avgPainLevel) {
        this.avgPainLevel = avgPainLevel;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPainLocation() {
        return painLocation;
    }

    public void setPainLocation(String painLocation) {
        this.painLocation = painLocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
        public String toString() {
            return "Simplified Pain Record";
        }


}
