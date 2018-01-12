package com.urbanpiperapp.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.urbanpiperapp.UrbanPiperApp;
import com.urbanpiperapp.constant.ApiConstant;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by chitra on 11/1/18.
 */

public class ApiGetTopStories {
    private static final String MODULE = "ApiGetTopStories";
    private static String TAG = "", Str_Msg = "";

    private String Str_Url = ApiConstant.TOP_STORIES_URL;
    private Context mContext;
    private ListenerStories callBack;

    public ApiGetTopStories(Context mContext, ListenerStories callBack) {
        this.mContext = mContext;
        this.callBack = callBack;
    }

    public interface ListenerStories{
        void onGetStoryIdSuccess(ArrayList<Integer> idList);
        void onGetStoryIdFailure();
    }

    public void getTopStories(){
        TAG = "GetStories";
        Log.d(MODULE, TAG);

        try {
            JsonArrayRequest req = new JsonArrayRequest(Str_Url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    handleResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    callBack.onGetStoryIdFailure();
                }
            });

            int socketTimeout = 60000;// 30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            req.setRetryPolicy(policy);
            UrbanPiperApp.getInstance().getRequestQueue().add(req);
        } catch (Exception ae){
            ae.printStackTrace();
            callBack.onGetStoryIdFailure();
        }
    }

    public void handleResponse(JSONArray response){
        try {
            ArrayList<Integer> storyIdList = new ArrayList<>();
            for(int i=0; i< response.length(); i++){
                storyIdList.add(Integer.parseInt(response.get(i).toString()));
            }

            if(storyIdList.size() > 0){
                callBack.onGetStoryIdSuccess(storyIdList);
            }
        } catch (Exception ae){
            ae.printStackTrace();
        }
    }
}
