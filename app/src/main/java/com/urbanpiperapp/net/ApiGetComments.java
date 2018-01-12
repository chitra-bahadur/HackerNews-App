package com.urbanpiperapp.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbanpiperapp.UrbanPiperApp;
import com.urbanpiperapp.constant.ApiConstant;
import com.urbanpiperapp.constant.Constants;
import com.urbanpiperapp.data.Comments;
import com.urbanpiperapp.data.RealmController;
import com.urbanpiperapp.data.RealmInt;
import com.urbanpiperapp.data.Story;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by chitra on 11/1/18.
 */

public class ApiGetComments {
    private static final String MODULE = "ApiGetComments";
    private static String TAG = "", Str_Msg = "";

    private String Str_Url = ApiConstant.TOP_STORIES_URL;
    private Context mContext;
    private ListenerComments callBack;
    private int storyId;
    private int commentId;
    private Realm realm;

    public ApiGetComments(Context mContext, ListenerComments callBack, int storyId, int commentId, Realm realm) {
        this.mContext = mContext;
        this.callBack = callBack;
        this.storyId = storyId;
        this.commentId = commentId;
        this.realm = realm;
        
        Str_Url = ApiConstant.COMMENT_URL + commentId + ApiConstant.ITEM_EXTENSION + ApiConstant.TAIL;
        Log.e(MODULE, Str_Url);
        
    }

    public interface ListenerComments{
        void onGetCommentsSuccess();
        void onGetCommentsIdFailure();
    }

    public void getComments(){
        TAG = "GetStories";
        Log.d(MODULE, TAG);

        try {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, Str_Url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            handleResponse(response);
                            Log.e(MODULE, response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

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
            callBack.onGetCommentsIdFailure();
        }
    }

    public void handleResponse(JSONObject response){
        try {
            RealmList<RealmInt> kidList = new RealmList<>();
            JSONArray ar;
            if(response.has("kids")) {
                ar = response.getJSONArray("kids");
                for(int j=0; j<ar.length(); j++){
                    kidList.add(new RealmInt(ar.getInt(j)));
                }
            }

            final Comments comments = new Comments();
            comments.setId(response.getInt("id"));
            comments.setParent(response.getInt("parent"));
            comments.setRealmInt(kidList);
            if(response.has("by")) {
                comments.setSubmitter(response.getString("by"));
            }
            if(response.has("text")) {
                comments.setText(response.getString("text"));
            }
            comments.setTime(response.getInt("id"));
            comments.setStoryId(storyId);

            Comments temp = RealmController.with(UrbanPiperApp.getInstance()).getComments(comments.getId());
            if(temp == null){
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(comments);
                    }
                });
            }
            callBack.onGetCommentsSuccess();

        } catch (Exception ae){
            ae.printStackTrace();
            callBack.onGetCommentsIdFailure();
        }
    }
}
