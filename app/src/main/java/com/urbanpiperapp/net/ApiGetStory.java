package com.urbanpiperapp.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.urbanpiperapp.UrbanPiperApp;
import com.urbanpiperapp.constant.ApiConstant;
import com.urbanpiperapp.data.RealmController;
import com.urbanpiperapp.data.RealmInt;
import com.urbanpiperapp.data.Story;

import org.json.JSONArray;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by chitra on 11/1/18.
 */

public class ApiGetStory {
    private static final String MODULE = "ApiGetStory";
    private static String TAG = "", Str_Msg = "";

    private String Str_Url = ApiConstant.STORY_URL;
    private Context mContext;
    private ListenerStory callBack;
    private Realm realm;

    public ApiGetStory(Context mContext, ListenerStory callBack, Integer storyId, Realm realm) {
        this.mContext = mContext;
        this.callBack = callBack;
        this.realm = realm;
        Str_Url = Str_Url + storyId + ApiConstant.ITEM_EXTENSION + ApiConstant.TAIL;
        Log.e(MODULE, Str_Url);
    }

    public interface ListenerStory{
        void onGetStorySuccess();
        void onGetStoryFailure();
    }

    public void getStory(){
        TAG = "GetStories";
        Log.d(MODULE, TAG);

        try {

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, Str_Url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            handleResponse(response);
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
            callBack.onGetStoryFailure();
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

            final Story story = new Story();
            story.setRealmInt(kidList);
            story.setId(response.getInt("id"));
            story.setScore(response.getInt("score"));
            story.setSubmitter(response.getString("by"));
            story.setTime(response.getLong("time"));
            story.setTitle(response.getString("title"));

            //not fetching sub comments yet
            /*if(response.has("descendants")) {
                story.setTotalComments(response.getInt("descendants"));
            }*/
            story.setTotalComments(kidList.size());
            if(response.has("url")){
                story.setUrl(response.getString("url"));
            }

            if(RealmController.with(UrbanPiperApp.getInstance()).getStory(story.getId()) == null) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(story);
                    }
                });
            }
            callBack.onGetStorySuccess();
        } catch (Exception ae){
            ae.printStackTrace();
        }
    }
}
