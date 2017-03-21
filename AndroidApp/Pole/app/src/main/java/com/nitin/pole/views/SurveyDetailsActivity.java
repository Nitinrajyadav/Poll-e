package com.nitin.pole.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nitin.pole.PoleApp;
import com.nitin.pole.R;
import com.nitin.pole.repository.local.CurrentUserHolder;
import com.nitin.pole.repository.local.LocalCache;
import com.nitin.pole.repository.pojo.Option;
import com.nitin.pole.repository.pojo.Survey;
import com.nitin.pole.repository.remote.EndPoints;
import com.nitin.pole.repository.remote.JSONRequest;
import com.nitin.pole.repository.remote.KEYS;
import com.nitin.pole.repository.remote.RequestTags;
import com.nitin.pole.utils.CommonUtils;
import com.nitin.pole.views.helper.PieChart;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SurveyDetailsActivity extends AppCompatActivity {

    private static final String TAG = SurveyDetailsActivity.class.getSimpleName();
    public static final String SURVEY_ID_KEY = "survey_id";

    private Toolbar mToolbar;
    private ProgressDialog mProgressDialog;
    private LocalCache mLocalCache;
    private ButtonBarLayout buttonBarLayout;

    private PieChart pieChart;
    private ListView mListView;
    private SeekBar seekBar;
    private TextView tvSeekVal;
    private TextView tvSurveyText;
    private TextView tvSurveyDate;
    private TextView tvSurveyFrom;
    private Button actionCancel;
    private Button actionSubmit;
    private Survey mSurvey;
    private JSONArray selectedOptions = new JSONArray();

    @Override
    protected void onResume() {

        if (mProgressDialog == null) {
            mProgressDialog = CommonUtils.getProgressDialog(this);
        }
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_details);

        mLocalCache = LocalCache.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.toolbar_survey);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        tvSurveyText = (TextView) findViewById(R.id.tv_survey_text);
        tvSurveyFrom = (TextView) findViewById(R.id.tv_survey_from);
        tvSurveyDate = (TextView) findViewById(R.id.tv_survey_date);

        mListView = (ListView) findViewById(R.id.list_questions);

        buttonBarLayout = (ButtonBarLayout) findViewById(R.id.button_bar_layout);
        actionCancel = (Button) findViewById(R.id.action_cancel);
        actionCancel.setOnClickListener(onCancelListener);
        actionSubmit = (Button) findViewById(R.id.action_submit);
        actionSubmit.setOnClickListener(onSubmitListener);

        seekBar = (SeekBar) findViewById(R.id.progressBar2);
        tvSeekVal = (TextView) findViewById(R.id.tv_seek_val);

        pieChart = (PieChart) findViewById(R.id.pie);
        Intent intent = getIntent();
        if (intent != null) {
            try {
                String surveyId = intent.getStringExtra(SURVEY_ID_KEY);
                mSurvey = mLocalCache.getSurveyArrayMap().get(surveyId);
                setupSurveyDetailsIntoUi(mSurvey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void setupSurveyDetailsIntoUi(Survey survey) {
        if (survey == null) {
            Log.e(TAG, "setupUI: null object survey...");
            return;
        }

        mToolbar.setTitle(survey.getName());
        tvSurveyText.setText(survey.getSurveyText());
        tvSurveyFrom.setText(survey.getDepartmentId());
        tvSurveyDate.setText(survey.getTime());

        if (survey.getSTATUS_FLAG() == 0) {
            ArrayList<Option> options = survey.getOptions();
            if (survey.getTYPE_FLAG() == Survey.TYPE_SINGLE_CHOICE) {
                setupSingleChoiceQuestionsList(options);
            } else if (survey.getTYPE_FLAG() == Survey.TYPE_MULTI_CHOICE) {
                setupMultiChoiceQuestionsList(options);
            } else if (survey.getTYPE_FLAG() == Survey.TYPE_OPINION) {
                mListView.setVisibility(View.GONE);
                seekBar.setVisibility(View.VISIBLE);
                seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
                tvSeekVal.setVisibility(View.VISIBLE);
            }
        } else {
            mListView.setVisibility(View.GONE);
            buttonBarLayout.setVisibility(View.GONE);

            float[] values = new float[mSurvey.getOptions().size()];
            for (int i = 0; i < mSurvey.getOptions().size(); i++) {
                values[i] = (float) mSurvey.getOptions().get(i).getCount();
            }

            setupPieChart(values);
        }

    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            tvSeekVal.setText(seekBar.getProgress() + " / 10");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            tvSeekVal.setTextColor(ContextCompat.getColor(SurveyDetailsActivity.this, R.color.colorNegative));
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            tvSeekVal.setTextColor(ContextCompat.getColor(SurveyDetailsActivity.this, R.color.colorAccent));
        }
    };

    private View.OnClickListener onCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    };

    private View.OnClickListener onSubmitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            JSONObject payload = new JSONObject();
            try {
                payload.put(KEYS.KEY_KYC_ID, CurrentUserHolder.getInstance().getKYC_ID());
                payload.put("entry_id", mSurvey.getSurveyId());

                if (mSurvey.getTYPE_FLAG() == Survey.TYPE_SINGLE_CHOICE) {
                    payload.put("options", selectedOptions);
                    payload.put("type", 1);
                } else if (mSurvey.getTYPE_FLAG() == Survey.TYPE_MULTI_CHOICE) {
                    payload.put("options", selectedOptions);
                    payload.put("type", 2);
                } else if (mSurvey.getTYPE_FLAG() == Survey.TYPE_OPINION) {
                    payload.put("type", 3);
                    payload.put("options", seekBar.getProgress());
                }

                makeSubmitSurveyRequest(payload);
            } catch (Exception e) {
                e.printStackTrace();
                mProgressDialog.hide();

            }
        }
    };

    private void setupSingleChoiceQuestionsList(ArrayList<Option> options) {
        mListView.setAdapter(null);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<Option> questionArrayAdapter = new ArrayAdapter<Option>(this,
                R.layout.item_single_choice, options);
        mListView.setAdapter(questionArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedOptions = new JSONArray();
                selectedOptions.put(((Option) mListView.getItemAtPosition(i)).getId());
                Log.e(TAG, "onItemClick: single_choice i->" + i + "aa-->" + adapterView.getSelectedItem());
            }
        });
        setListViewHeightBasedOnChildren(mListView);
    }


    private void setupMultiChoiceQuestionsList(final ArrayList<Option> options) {
        mListView.setAdapter(null);

        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<Option> questionArrayAdapter = new ArrayAdapter<Option>(this,
                R.layout.item_multi_choice, options);
        mListView.setAdapter(questionArrayAdapter);
        setListViewHeightBasedOnChildren(mListView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SparseBooleanArray sparseBooleanArray = mListView.getCheckedItemPositions();
                selectedOptions = new JSONArray();
                for (int k = 0; k < options.size(); k++) {
                    if (sparseBooleanArray.get(k)) {
                        selectedOptions.put(((Option) mListView.getItemAtPosition(k)).getId());
                    }
                    Log.e(TAG, "onItemClick: multi_choice -->" + sparseBooleanArray.get(k) + "-->" + mListView.getItemAtPosition(k));
                }
            }
        });
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, FrameLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void setupPieChart(float[] values) {
        pieChart.setVisibility(View.VISIBLE);
        pieChart.setValues(values);

    }


    private void makeSubmitSurveyRequest(JSONObject payload) {
        mProgressDialog.show();

        JSONRequest submitSurveyRequest = new JSONRequest(
                Request.Method.POST,
                EndPoints.SUBMIT_SURVEYS,
                payload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "addDocRequest  onResponse: " + response);

                        try {
                            JSONObject payload = new JSONObject();
                            payload.put(KEYS.KEY_KYC_ID, CurrentUserHolder.getInstance().getKYC_ID());
                            makeGetAllSurveysRequest(payload);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mProgressDialog.hide();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: " + error);
                        mProgressDialog.hide();
                    }
                }
        );
        submitSurveyRequest.setRetryPolicy(PoleApp.getDefaultRetryPolice());
        PoleApp.getInstance().addToRequestQueue(submitSurveyRequest, RequestTags.SURV_DET_REQUESTS_TAG);
    }

    private void makeGetAllSurveysRequest(final JSONObject payload) {
        mProgressDialog.show();
        mProgressDialog.setMessage("Retrieving Documents");
        JSONRequest getAllSurveysRequest = new JSONRequest(
                Request.Method.POST,
                EndPoints.GET_ALL_SURVEYS,
                payload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "onResponse: makeGetAllSurveysRequest -->" + response);
                        try {
                            JSONArray jsonArray = response.getJSONArray("documents");
                            mLocalCache.saveSurveysFromJson(jsonArray);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mProgressDialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.hide();
                        Log.d(TAG, "onErrorResponse:  makeGetAllSurveysRequest-->" + error);
                    }
                }
        );

        getAllSurveysRequest.setTag(RequestTags.SURV_DET_REQUESTS_TAG);
        getAllSurveysRequest.setRetryPolicy(PoleApp.getDefaultRetryPolice());
        PoleApp.getInstance().getRequestQueue().add(getAllSurveysRequest);

    }


    @Override
    protected void onStop() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        super.onStop();
    }
}
