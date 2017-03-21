package com.nitin.pole.repository.remote;

/**
 * Created by Nitin on 3/15/2017.
 */
public class EndPoints {

    /**
     * Base server url.
     */

//    public static final String BASE_URL = "http://192.168.0.106:8080/easysurvey/api/";
    public static final String BASE_URL = "http://172.16.45.165:80/EasySurvey/api/";



    public static final String SIGN_IN_URL = BASE_URL.concat("signup.php");
    public static final String GET_ALL_SURVEYS = BASE_URL.concat("getList.php");
    public static final String SUBMIT_SURVEYS = BASE_URL.concat("pushSurvey.php");

}
