package com.example.honoursproject.Model;

import java.io.Serializable;
import java.util.Date;

public class SimplifiedPainRecord implements Serializable {
        private String id;
        private Integer minPainLevel;
        private Integer maxPainLevel;
        private Integer avgPainLevel;
        private Date startDate;
        private Date endDate;
        private String painLocation;


        public SimplifiedPainRecord(){

        }
        public SimplifiedPainRecord(String id, Integer minPainLevel, Integer maxPainLevel, Integer avgPainLevel, Date sDate, Date eDate, String pLoc) {
            this.id = id;
            this.minPainLevel = minPainLevel;
            this.maxPainLevel = maxPainLevel;
            this.avgPainLevel = avgPainLevel;
            startDate = sDate;
            if(eDate != null){
                endDate = eDate;
            }
            else{
                endDate = new Date();
            }
            painLocation = pLoc;
        }

    public SimplifiedPainRecord(Integer minPainLevel, Integer maxPainLevel, Integer avgPainLevel, Date sDate, Date eDate, String pLoc) {
        this.minPainLevel = minPainLevel;
        this.maxPainLevel = maxPainLevel;
        this.avgPainLevel = avgPainLevel;
        startDate = sDate;
        if(eDate != null){
            endDate = eDate;
        }
        else{
            endDate = new Date();
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
