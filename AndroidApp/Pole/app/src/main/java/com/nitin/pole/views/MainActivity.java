package com.nitin.pole.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.nitin.pole.PoleApp;
import com.nitin.pole.R;
import com.nitin.pole.repository.local.LocalCache;
import com.nitin.pole.repository.remote.RequestTags;
import com.nitin.pole.utils.CommonUtils;
import com.nitin.pole.views.fragments.DashBoardFragment;
import com.nitin.pole.views.fragments.UserProfileFragment;
import com.nitin.pole.views.helper.BottomNavigationViewBehavior;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private BottomNavigationView mBottomNavigationView;
    private ProgressDialog mProgressDialog;
    private LocalCache mLocalCacheInstance;

    private UserProfileFragment mUserProfileFragment;
    private DashBoardFragment mDashBoardFragment;

    @Override
    protected void onResume() {
        if (mProgressDialog == null) {
            mProgressDialog = CommonUtils.getProgressDialog(this);
        }
        if (mLocalCacheInstance == null) {
            mLocalCacheInstance = LocalCache.getInstance();
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

        PoleApp.getInstance().cancelPendingRequests(RequestTags.SIGN_IN_REQUESTS_TAG);

        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());

        replaceFragment(getUserProfileFragment(), UserProfileFragment.class.getSimpleName());

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    replaceFragment(getUserProfileFragment(), UserProfileFragment.class.getSimpleName());
                    return true;
                case R.id.navigation_dashboard:
                    replaceFragment(getDashboardFragment(), DashBoardFragment.class.getSimpleName());
                    return true;
            }
            return false;
        }

    };

    private UserProfileFragment getUserProfileFragment() {
        if (mUserProfileFragment == null) {
            mUserProfileFragment = UserProfileFragment.getInstance();
        }
        return mUserProfileFragment;
    }


    private DashBoardFragment getDashboardFragment() {
        if (mDashBoardFragment == null) {
            mDashBoardFragment = DashBoardFragment.getInstance();
        }
        return mDashBoardFragment;
    }

    private void replaceFragment(Fragment newFragment, String tag) {
        if (newFragment != null) {
            FragmentManager frgManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = frgManager.beginTransaction();
            fragmentTransaction.setAllowOptimization(false);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.content_frame, newFragment).commit();
            frgManager.executePendingTransactions();
        } else {
            Log.e(TAG, "Replace fragments with null newFragment parameter.");
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

}
