package com.nitin.pole.repository.local;

import com.nitin.pole.repository.pojo.User;

/**
 * Created by Nitin on 3/16/2017.
 */

public class CurrentUserHolder {

    private static CurrentUserHolder mInstance;

    protected String KYC_ID;
    protected String AUTH_TOKEN;
    protected User mUser;

    public String getKYC_ID() {
        return KYC_ID;
    }

    public void setKYC_ID(String KYC_ID) {
        this.KYC_ID = KYC_ID;
    }

    public String getAUTH_TOKEN() {
        return AUTH_TOKEN;
    }

    public void setAUTH_TOKEN(String AUTH_TOKEN) {
        this.AUTH_TOKEN = AUTH_TOKEN;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    private CurrentUserHolder() {

    }


    public static CurrentUserHolder getInstance() {
        if (mInstance == null) {
            mInstance = new CurrentUserHolder();
        }
        return mInstance;
    }


}
