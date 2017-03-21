package com.nitin.pole.views.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nitin.pole.PoleApp;
import com.nitin.pole.R;
import com.nitin.pole.repository.local.LocalCache;
import com.nitin.pole.repository.pojo.Survey;
import com.nitin.pole.repository.remote.RequestTags;
import com.nitin.pole.utils.CommonUtils;
import com.nitin.pole.views.SurveyDetailsActivity;
import com.nitin.pole.views.adapters.SurveysRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Nitin on 3/16/2017.
 */

public class DashBoardFragment extends Fragment {

    private static final String TAG = UserProfileFragment.class.getSimpleName();
    private ProgressDialog progressDialog;
    private LocalCache localCacheInstance;

    private View rootView;
    private RecyclerView rvSurveys;


    public static DashBoardFragment getInstance() {
        DashBoardFragment dashBoardFragment = new DashBoardFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "value");
        dashBoardFragment.setArguments(bundle);
        return dashBoardFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (localCacheInstance == null) {
            localCacheInstance = LocalCache.getInstance();
        }
        if (progressDialog == null) {
            progressDialog = CommonUtils.getProgressDialog(getContext());
        }
    }

    @Override
    public void onResume() {
        if (localCacheInstance == null) {
            localCacheInstance = LocalCache.getInstance();
        }
        if (progressDialog == null) {
            progressDialog = CommonUtils.getProgressDialog(getContext());
        }

        if (rvSurveys != null) {
            setupSurveyssRecycler();
        }
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        rvSurveys = (RecyclerView) rootView.findViewById(R.id.rv_surveys);

        setupSurveyssRecycler();

        return rootView;
    }

    private void setupSurveyssRecycler() {
        rvSurveys.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Survey> surveys = localCacheInstance.getSurveyArrayList();

        if (surveys.size() > 0) {
            rvSurveys.setAdapter(new SurveysRecyclerViewAdapter(getContext(), surveys, new OnListFragmentInteractionListener() {

                @Override
                public void onListFragmentInteraction(Survey survey) {
                    Log.d(TAG, "onListFragmentInteraction: all docs rv--" + survey);
                    Intent intent = new Intent(getActivity(), SurveyDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(SurveyDetailsActivity.SURVEY_ID_KEY, survey.getSurveyId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }));
        }
    }


    @Override
    public void onStop() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        PoleApp.getInstance().cancelPendingRequests(RequestTags.DASHBOARD_REQUESTS_TAG);
        super.onStop();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Survey survey);
    }


}
