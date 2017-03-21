package com.nitin.pole.repository.local;


import android.util.ArrayMap;

import com.nitin.pole.repository.pojo.Option;
import com.nitin.pole.repository.pojo.Survey;
import com.nitin.pole.repository.pojo.User;
import com.nitin.pole.repository.remote.KEYS;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nitin on 3/15/2017.
 */

public class LocalCache {

    private static LocalCache localCacheInstance;
    private static JSONParser mJSONParser;


    private ArrayMap<String, Survey> surveyArrayMap;
    private ArrayList<Survey> surveyArrayList;

    private LocalCache() {

        surveyArrayMap = new ArrayMap<>();
        surveyArrayList = new ArrayList<>();
        loadDummySurveys();

    }

    private void loadDummySurveys() {
        for (int i = 0; i < 10; i++) {
            Survey survey = new Survey();
            survey.setName("dummy survey");
            survey.setDepartmentId("xx-dept-id" + i);
            survey.setSurveyId("xx-survey-id" + i);
            survey.setTYPE_FLAG(111 + (i % 2));
            survey.setSTATUS_FLAG(i % 2);
            surveyArrayList.add(survey);
            surveyArrayMap.put(survey.getSurveyId(), survey);
            ArrayList<Option> options = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                Option q = new Option();
                q.setValue(j + 1 + ".  question no shsghgfhd " + j * i + " njfj ?");
                q.setId("xx-qus-id" + i + "-" + j);
                q.setCount(i * j % (i + 1));
                options.add(q);
            }
            survey.setOptions(options);
        }
    }


    public static LocalCache getInstance() {
        if (localCacheInstance == null) localCacheInstance = new LocalCache();
        mJSONParser = new JSONParser();
        return localCacheInstance;
    }


    public void parseUserJson(JSONObject jsonObject) {
        User user = mJSONParser.parseUserJson(jsonObject);
        CurrentUserHolder currentUserHolder = CurrentUserHolder.getInstance();
        try {
            currentUserHolder.setmUser(user);
            currentUserHolder.setKYC_ID(jsonObject.getString(KEYS.JSON_ID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSurveysFromJson(JSONArray jsonArray) {
        surveyArrayMap.clear();
        surveyArrayList.clear();
        mJSONParser.parseSurveysJsonArray(surveyArrayList, surveyArrayMap, jsonArray);
    }

    public ArrayMap<String, Survey> getSurveyArrayMap() {
        return surveyArrayMap;
    }

    public ArrayList<Survey> getSurveyArrayList() {
        return surveyArrayList;
    }


}
