package com.nitin.pole.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nitin.pole.PoleApp;
import com.nitin.pole.R;
import com.nitin.pole.repository.local.CurrentUserHolder;
import com.nitin.pole.repository.local.LocalCache;
import com.nitin.pole.repository.remote.EndPoints;
import com.nitin.pole.repository.remote.JSONRequest;
import com.nitin.pole.repository.remote.KEYS;
import com.nitin.pole.repository.remote.RequestTags;
import com.nitin.pole.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONObject;


public class SignInActivity extends AppCompatActivity {

    private static final String TAG = SignInActivity.class.getCanonicalName();


    // UI references.
    private TextInputLayout inputUserIdWrapper;
    private View mContainerSignIn;
    private View mContainerValidateOtp;
    private View mProgressView;
    private Button actionValidateUserId;
    private Button actionValidateOtp;
    private TextView actionChangeUserId;
    private boolean isWaitingToValidateOtp = false;

    private ProgressDialog mProgressDialog;
    private LocalCache localCacheInstance;

    @Override
    protected void onResume() {
        if (mProgressDialog == null) {
            mProgressDialog = CommonUtils.getProgressDialog(this);
        }
        if (localCacheInstance == null) {
            localCacheInstance = LocalCache.getInstance();
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
        setContentView(R.layout.activity_signin);

        // Set up the login form.
        mContainerSignIn = findViewById(R.id.container_sign_in);
        inputUserIdWrapper = (TextInputLayout) findViewById(R.id.input_user_id_wrapper);
        inputUserIdWrapper.getEditText().setOnEditorActionListener(onInputUserIdEditorActionListener);

        actionValidateUserId = (Button) findViewById(R.id.action_validate);
        actionValidateUserId.setOnClickListener(onValidateUserIdClickListener);

        mContainerValidateOtp = findViewById(R.id.container_verify_otp);
        actionChangeUserId = (TextView) findViewById(R.id.action_change_user_id);
        actionChangeUserId.setOnClickListener(onChangeUserIdClickListener);

        actionValidateOtp = (Button) findViewById(R.id.action_validate_otp);
        actionValidateOtp.setOnClickListener(onValidateOtpClickListener);

        mProgressView = findViewById(R.id.sign_in_progress);
    }

    private View.OnClickListener onChangeUserIdClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            showUserIdContainer();
        }
    };

    private View.OnClickListener onValidateUserIdClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            attemptLogin();

        }
    };

    private View.OnClickListener onValidateOtpClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private TextView.OnEditorActionListener onInputUserIdEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                attemptLogin();
                return true;
            }
            return false;
        }
    };


    private void attemptLogin() {

        // Reset errors.
        inputUserIdWrapper.setError(null);
        inputUserIdWrapper.setErrorEnabled(false);

        // Store values at the time of the login attempt.
        String userId = inputUserIdWrapper.getEditText().getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid user id, if the user entered one.
        if (TextUtils.isEmpty(userId)) {
            inputUserIdWrapper.setErrorEnabled(true);
            inputUserIdWrapper.setError(getString(R.string.error_invalid_user_id));
            focusView = inputUserIdWrapper;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            JSONObject payload = new JSONObject();
            try {
                payload.put(KEYS.KEY_BHAMASHAH_ID, userId);
                makeUserIdValidationRequest(payload);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void makeUserIdValidationRequest(final JSONObject payload) {
        mProgressDialog.show();
        mProgressDialog.setMessage("Sign In");
        JSONRequest validateUserIdRequest = new JSONRequest(
                Request.Method.POST,
                EndPoints.SIGN_IN_URL,
                payload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "onResponse: makeUserIdValidationRequest -->" + response);
                        try {
                            JSONObject dataObj = response.getJSONObject(KEYS.RESIDENT_DETAILS);
                            localCacheInstance.parseUserJson(dataObj);
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
                        mProgressDialog.hide();
                        Log.d(TAG, "onErrorResponse:  makeUserIdValidationRequest-->" + error);
                    }
                }
        );

        validateUserIdRequest.setTag(RequestTags.SIGN_IN_REQUESTS_TAG);
        validateUserIdRequest.setRetryPolicy(PoleApp.getDefaultRetryPolice());
        PoleApp.getInstance().getRequestQueue().add(validateUserIdRequest);

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
                            localCacheInstance.saveSurveysFromJson(jsonArray);
                            goToMainActivity();
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

        getAllSurveysRequest.setTag(RequestTags.SIGN_IN_REQUESTS_TAG);
        getAllSurveysRequest.setRetryPolicy(PoleApp.getDefaultRetryPolice());
        PoleApp.getInstance().getRequestQueue().add(getAllSurveysRequest);

    }


    private void goToMainActivity() {
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        finish();
    }

    private void showValidateOtpContainer() {
        Log.d(TAG, "showValidateOtpContainer: ");
        isWaitingToValidateOtp = true;
        mContainerSignIn.setVisibility(View.GONE);
        mProgressView.setVisibility(View.GONE);
        mContainerValidateOtp.setVisibility(View.VISIBLE);

    }

    private void showUserIdContainer() {
        inputUserIdWrapper.getEditText().setText("");
        isWaitingToValidateOtp = false;
        mProgressView.setVisibility(View.GONE);
        mContainerValidateOtp.setVisibility(View.GONE);
        mContainerSignIn.setVisibility(View.VISIBLE);
    }


}

