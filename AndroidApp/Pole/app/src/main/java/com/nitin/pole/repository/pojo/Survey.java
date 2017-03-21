package com.nitin.pole.repository.pojo;

import java.util.ArrayList;

/**
 * Created by Nitin on 3/16/2017.
 */

public class Survey {

    public static final int TYPE_MULTI_CHOICE = 111;
    public static final int TYPE_SINGLE_CHOICE = 112;
    public static final int TYPE_OPINION = 113;


    private String surveyId;
    private String name;
    private String departmentId;
    private int STATUS_FLAG;
    private int TYPE_FLAG;
    private String time;
    private String surveyText;
    private ArrayList<Option> optionArrayList;

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public int getSTATUS_FLAG() {
        return STATUS_FLAG;
    }

    public void setSTATUS_FLAG(int STATUS_FLAG) {
        this.STATUS_FLAG = STATUS_FLAG;
    }

    public int getTYPE_FLAG() {
        return TYPE_FLAG;
    }

    public void setTYPE_FLAG(int TYPE_FLAG) {
        this.TYPE_FLAG = TYPE_FLAG;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public ArrayList<Option> getOptions() {
        return optionArrayList;
    }

    public void setOptions(ArrayList<Option> optionArrayList) {
        this.optionArrayList = optionArrayList;
    }

    public String getSurveyText() {
        return surveyText;
    }

    public void setSurveyText(String surveyText) {
        this.surveyText = surveyText;
    }
}


