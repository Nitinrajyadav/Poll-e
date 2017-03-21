package com.nitin.pole.views.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nitin.pole.R;
import com.nitin.pole.repository.local.CurrentUserHolder;
import com.nitin.pole.repository.local.LocalCache;
import com.nitin.pole.utils.CommonUtils;


/**
 * Created by Nitin on 3/15/2017.
 */

public class UserProfileFragment extends Fragment {

    private static final String TAG = UserProfileFragment.class.getSimpleName();
    private ProgressDialog progressDialog;
    private LocalCache localCacheInstance;

    private View rootView;

    private TextView tvUserName;
    private TextView tvUserDetails;

    public static UserProfileFragment getInstance() {
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "llallala");
        userProfileFragment.setArguments(bundle);
        return userProfileFragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
//            name = bundle.getString("name");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (localCacheInstance == null) {
            localCacheInstance = LocalCache.getInstance();
        }
        if (progressDialog == null) {
            progressDialog = CommonUtils.getProgressDialog(getContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        tvUserName = (TextView) rootView.findViewById(R.id.tv_user_name);
        tvUserDetails = (TextView) rootView.findViewById(R.id.tv_user_details);

        tvUserName.setText(CurrentUserHolder.getInstance().getmUser().getNameEng());
        String big = CurrentUserHolder.getInstance().getmUser().toString();
        tvUserDetails.setText(big);

        readBundle(getArguments());

        return rootView;
    }

    @Override
    public void onStop() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        super.onStop();
    }

}

