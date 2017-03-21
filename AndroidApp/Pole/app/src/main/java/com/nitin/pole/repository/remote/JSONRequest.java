package com.nitin.pole.repository.remote;

import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nitin on 3/15/2017.
 * <p>
 * A request for retrieving a {@link JSONObject} response body at a given URL, allowing for an
 * optional {@link JSONObject} to be passed in as part of the request body.
 * Usable for authorized and unauthorized requests.
 */
public class JSONRequest extends JsonObjectRequest {

    /**
     * User token for authorized requests.
     */
    private final String accessToken;
    private int requestStatusCode;
    private FragmentManager fragmentManager;
    private String requestUrl;

    /**
     * Create a new authorized request.
     *
     * @param method          The HTTP method to use
     * @param requestUrl      URL to fetch the JSON from
     * @param payload         A {@link JSONObject} to post with the request. Null is allowed and
     *                        indicates no parameters will be posted along with request.
     * @param successListener Listener to retrieve successful response
     * @param errorListener   Error listener, or null to ignore errors
     * @param fragmentManager Manager to create re-login dialog on HTTP status 403. Null is allowed.
     * @param accessToken     Token identifying user used for user specific requests.
     */
    public JSONRequest(int method,
                       String requestUrl,
                       JSONObject payload,
                       Response.Listener<JSONObject> successListener,
                       Response.ErrorListener errorListener,
                       FragmentManager fragmentManager,
                       String accessToken) {
        super(method, requestUrl, payload, successListener, errorListener);
        this.requestUrl = requestUrl;
        this.fragmentManager = fragmentManager;
        this.accessToken = accessToken;
        Log.d("JSONRequest: url--> ", this.requestUrl);
        if (payload != null) Log.d("JSONRequest payload--> ", payload.toString());
    }

    /**
     * Create a new unauthorized request.
     *
     * @param method          The HTTP method to use
     * @param requestUrl      URL to fetch the JSON from
     * @param payload         A {@link JSONObject} to post with the request. Null is allowed and
     *                        indicates no parameters will be posted along with request.
     * @param successListener Listener to retrieve successful response
     * @param errorListener   Error listener, or null to ignore errors
     */
    public JSONRequest(int method,
                       String requestUrl,
                       JSONObject payload,
                       Response.Listener<JSONObject> successListener,
                       Response.ErrorListener errorListener) {

        this(method, requestUrl, payload, successListener, errorListener, null, null);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();

        // Determine if request should be authorized.
        if (accessToken != null && !accessToken.isEmpty()) {
            String credentials = accessToken + ":";
            String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            headers.put("Authorization", "Basic " + encodedCredentials);
        }
        return headers;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            requestStatusCode = response.statusCode;

        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
        return super.parseNetworkResponse(response);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null) {
            // Save request status code
            requestStatusCode = volleyError.networkResponse.statusCode;

            // If AccessToken expired. Logout user and redirect to home page.
            if (getStatusCode() == HttpURLConnection.HTTP_FORBIDDEN && fragmentManager != null) {
            }
        } else {
            requestStatusCode = RequestTags.MissingStatusCode;
        }
        return super.parseNetworkError(volleyError);
    }

    /**
     * Method returns result statusCode of invoked request.
     *
     * @return HTTP status code.
     */
    public int getStatusCode() {
        return requestStatusCode;
    }
}
