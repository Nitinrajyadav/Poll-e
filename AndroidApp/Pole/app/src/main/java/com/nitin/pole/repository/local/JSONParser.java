package com.nitin.pole.repository.local;

import android.util.ArrayMap;

import com.nitin.pole.repository.pojo.Option;
import com.nitin.pole.repository.pojo.Survey;
import com.nitin.pole.repository.pojo.User;
import com.nitin.pole.repository.remote.KEYS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nitin on 3/16/2017.
 */

public class JSONParser {


    protected User parseUserJson(JSONObject jsonObject) {
        User user = new User();
        try {
            if (jsonObject.has(KEYS.CATEGORY_DESC_ENG)) {
                user.setCategoryDescEng(jsonObject.getString(KEYS.CATEGORY_DESC_ENG));
            }
            if (jsonObject.has(KEYS.AADHAR_ID)) {
                user.setAadharId(jsonObject.getLong(KEYS.AADHAR_ID));
            }
            if (jsonObject.has(KEYS.STATE)) {
                user.setState(jsonObject.getString(KEYS.STATE));
            }
            if (jsonObject.has(KEYS.MOTHER_NAME_ENG)) {
                user.setMotherNameEng(jsonObject.getString(KEYS.MOTHER_NAME_ENG));
            }
            if (jsonObject.has(KEYS.RELATION_ENG)) {
                user.setRelationEng(jsonObject.getString(KEYS.RELATION_ENG));
            }
            if (jsonObject.has(KEYS.DOB)) {
                user.setDOB(jsonObject.getString(KEYS.DOB));
            }
            if (jsonObject.has(KEYS.ECONOMIC_GROUP)) {
                user.setEconomicGroup(jsonObject.getString(KEYS.ECONOMIC_GROUP));
            }
            if (jsonObject.has(KEYS.BUILDING_NO_ENG)) {
                user.setBuildingNoEng(jsonObject.getString(KEYS.BUILDING_NO_ENG));
            }
            if (jsonObject.has(KEYS.BHAMASHAH_ID)) {
                user.setBhamashahId(jsonObject.getString(KEYS.BHAMASHAH_ID));
            }
            if (jsonObject.has(KEYS.STREET_NAME_ENG)) {
                user.setStreetNameEng(jsonObject.getString(KEYS.STREET_NAME_ENG));
            }
            if (jsonObject.has(KEYS.IFSC_CODE)) {
                user.setIfscCode(jsonObject.getString(KEYS.IFSC_CODE));
            }
            if (jsonObject.has(KEYS.M_ID)) {
                user.setmId(jsonObject.getString(KEYS.M_ID));
            }
            if (jsonObject.has(KEYS.FAMILYIDNO)) {
                user.setFamilyIdNo(jsonObject.getString(KEYS.FAMILYIDNO));
            }
            if (jsonObject.has(KEYS.PIN_CODE)) {
                user.setPinCode(jsonObject.getInt(KEYS.PIN_CODE));
            }
            if (jsonObject.has(KEYS.LANDLINE_NO)) {
                user.setLandLineNo(jsonObject.getString(KEYS.LANDLINE_NO));
            }
            if (jsonObject.has(KEYS.VILLAGE_NAME)) {
                user.setVillageName(jsonObject.getString(KEYS.VILLAGE_NAME));
            }
            if (jsonObject.has(KEYS.HOUSE_NUMBER_ENG)) {
                user.setHouseNoEng(jsonObject.getString(KEYS.HOUSE_NUMBER_ENG));
            }
            if (jsonObject.has(KEYS.GP_WARD)) {
                user.setGpWard(jsonObject.getString(KEYS.GP_WARD));
            }
            if (jsonObject.has(KEYS.EMAIL)) {
                user.setEmail(jsonObject.getString(KEYS.EMAIL));
            }
            if (jsonObject.has(KEYS.SPOUCE_NAME_ENG)) {
                user.setSpouceNameEng(jsonObject.getString(KEYS.SPOUCE_NAME_ENG));
            }
            if (jsonObject.has(KEYS.IS_RURAL)) {
                user.setRural(jsonObject.getBoolean(KEYS.IS_RURAL));
            }
            if (jsonObject.has(KEYS.DISTRICT)) {
                user.setDistrict(jsonObject.getString(KEYS.DISTRICT));
            }
            if (jsonObject.has(KEYS.EDUCATION_DESC_ENG)) {
                user.setEducationDescEng(jsonObject.getString(KEYS.EDUCATION_DESC_ENG));
            }
            if (jsonObject.has(KEYS.ACC_NO)) {
                user.setBankAccountNo(jsonObject.getLong(KEYS.ACC_NO));
            }
            if (jsonObject.has(KEYS.ADDRESS)) {
                user.setAddress(jsonObject.getString(KEYS.ADDRESS));
            }
            if (jsonObject.has(KEYS.INCOME_DESC_ENG)) {
                user.setIncomeDescEng(jsonObject.getString(KEYS.INCOME_DESC_ENG));
            }
            if (jsonObject.has(KEYS.BANK_NAME)) {
                user.setBankName(jsonObject.getString(KEYS.BANK_NAME));
            }
            if (jsonObject.has(KEYS.LAND_MARK_ENG)) {
                user.setLandMarkEng(jsonObject.getString(KEYS.LAND_MARK_ENG));
            }
            if (jsonObject.has(KEYS.RATION_CARD_NO)) {
                user.setRationCardNo(jsonObject.getLong(KEYS.RATION_CARD_NO));
            }
            if (jsonObject.has(KEYS.NAME_ENG)) {
                user.setNameEng(jsonObject.getString(KEYS.NAME_ENG));
            }
            if (jsonObject.has(KEYS.MOBILE_NO)) {
                user.setMobileNo(jsonObject.getLong(KEYS.MOBILE_NO));
            }
            if (jsonObject.has(KEYS.GENDER)) {
                user.setGender(jsonObject.getString(KEYS.GENDER));
            }
            if (jsonObject.has(KEYS.FATHER_NAME_ENG)) {
                user.setFatherNameEng(jsonObject.getString(KEYS.FATHER_NAME_ENG));
            }
            if (jsonObject.has(KEYS.FAX_NO)) {
                user.setFaxNo(jsonObject.getString(KEYS.FAX_NO));
            }
            if (jsonObject.has(KEYS.BLOCK_CITY)) {
                user.setBlockCity(jsonObject.getString(KEYS.BLOCK_CITY));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    protected void parseSurveysJsonArray(final ArrayList<Survey> surveys,
                                         final ArrayMap<String, Survey> surveysMap,
                                         final JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject surveyObj = jsonArray.getJSONObject(i);
                Survey survey = parseSurveyJsonObject(surveyObj);
                surveys.add(survey);
                surveysMap.put(survey.getSurveyId(), survey);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    protected Survey parseSurveyJsonObject(JSONObject jsonObject) {
        Survey survey = new Survey();
        try {
            survey.setSurveyText(jsonObject.getString("name"));
            survey.setSurveyId(jsonObject.getString("id"));
            survey.setDepartmentId(jsonObject.getString("adminName"));
            survey.setSTATUS_FLAG(jsonObject.getInt("user-response"));

            int type = jsonObject.getInt("type");
            if (type == 1) {
                survey.setTYPE_FLAG(Survey.TYPE_SINGLE_CHOICE);
            } else if (type == 2) {
                survey.setTYPE_FLAG(Survey.TYPE_MULTI_CHOICE);
            } else if (type == 3) {
                survey.setTYPE_FLAG(Survey.TYPE_OPINION);
            }

            survey.setTime(jsonObject.getString("created_date"));

            JSONArray jsonArray = jsonObject.getJSONArray("options");
            ArrayList<Option> options = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject qObj = jsonArray.getJSONObject(i);
                Option option = new Option();
                option.setId(qObj.getString("id"));
                option.setValue(qObj.getString("name"));
                option.setCount(qObj.getInt("count"));
                options.add(option);
            }
            survey.setOptions(options);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return survey;
    }

//    {
//        "documents": [
//        {
//            "id": "1",
//                "name": "Who is the next PM ?",
//                "type": "1",
//                "admin": "1",
//                "created_date": "2017-03-18 05:09:58",
//                "user-response": "0",
//                "adminName": "SuperAdmin",
//                "options": [
//            {
//                "id": "1",
//                    "entry_id": "1",
//                    "name": "Narendra Modi",
//                    "count": "0"
//            },
//            {
//                "id": "2",
//                    "entry_id": "1",
//                    "name": "Rahul Gandhi",
//                    "count": "0"
//            }
//            ]
//        },
//        {
//            "id": "2",
//                "name": "Main Reasons for last Accident",
//                "type": "2",
//                "admin": "1",
//                "created_date": "2017-03-18 05:09:58",
//                "user-response": "0",
//                "adminName": "SuperAdmin",
//                "options": [
//            {
//                "id": "3",
//                    "entry_id": "2",
//                    "name": "Heavy Traffic",
//                    "count": "0"
//            },
//            {
//                "id": "4",
//                    "entry_id": "2",
//                    "name": "Bad Roads",
//                    "count": "0"
//            },
//            {
//                "id": "5",
//                    "entry_id": "2",
//                    "name": "Heavy Rainfall",
//                    "count": "0"
//            },
//            {
//                "id": "6",
//                    "entry_id": "2",
//                    "name": "No Order",
//                    "count": "0"
//            }
//            ]
//        },
//        {
//            "id": "3",
//                "name": "New Road is Necessary in Village ",
//                "type": "3",
//                "admin": "1",
//                "created_date": "2017-03-18 05:10:45",
//                "user-response": "0",
//                "adminName": "SuperAdmin",
//                "options": []
//        }
//    ]
//    }

}
