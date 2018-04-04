package com.forsyslab.talquest10.network;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;


/**
 * Created by nizar on 22/01/2017.
 */

public class VolleyParce {

    private static VolleyParce mInstance;
    RequestQueue requestQueue;
    Activity activity;

    public VolleyParce(Activity activity) {
        this.activity = activity;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(activity);
        return requestQueue;
    }

    public static synchronized VolleyParce getInstance(Activity activity) {

        if (mInstance == null)
            mInstance = new VolleyParce(activity);
        return mInstance;
    }
    public <T> void add(Request<T> request) {
        requestQueue.add(request);
    }
}
