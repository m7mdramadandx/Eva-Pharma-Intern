package com.ramadan.eva.ui.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class VolleyHandler {
    private RequestQueue queue;

    public VolleyHandler(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void callAPI(Response.Listener<JSONObject> listener,
                        Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(
                        Request.Method.GET,
                        "https://run.mocky.io/v3/a086c3ed-e6ba-4bee-bcc0-862eec93d1eb?mocky-delay=1000ms",
                        null,
                        listener,
                        errorListener);
        queue.add(jsonObjectRequest);
        Log.d("Volley", "successful call");
    }
}
