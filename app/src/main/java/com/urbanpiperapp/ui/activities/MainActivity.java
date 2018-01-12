package com.urbanpiperapp.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.urbanpiperapp.R;
import com.urbanpiperapp.data.RealmController;
import com.urbanpiperapp.data.Story;
import com.urbanpiperapp.net.ApiGetStory;
import com.urbanpiperapp.net.ApiGetTopStories;
import com.urbanpiperapp.ui.adapters.MainAdapter;
import com.urbanpiperapp.utils.NotificationUtils;
import com.urbanpiperapp.utils.PrefManager;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity implements
        ApiGetTopStories.ListenerStories, ApiGetStory.ListenerStory{

    private ListView mListView;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private TextView lastUpdatedTv;
    private MainAdapter mAdapter;
    private Realm realm;
    private PrefManager pref;
    private int count, storyCount;

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        //fetching story ids
        if(!pref.isFirstTimeLaunch()) {
            getStoryIds();
        } else {
            setAdapter();
        }

        setTitle();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Story story = (Story) adapterView.getAdapter().getItem(i);
                utils.goToArticlScreen(mActivity, story.getId());
            }
        });
    }

    private void setTitle(){
        long lastUpdated = pref.getLastUpdated();
        if(lastUpdated == 0){
            pref.setLastUpdated();
            lastUpdated = pref.getLastUpdated();
        }

        //calculate the time  between current time and last updated time
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(lastUpdated);
        int minutes = cal.get(Calendar.MINUTE);

        title = "Updated " + minutes + " mins ago";
        lastUpdatedTv.setText(title);

    }

    private void init(){
        toolbar = findViewById(R.id.toolbarLayout);
        lastUpdatedTv = toolbar.findViewById(R.id.last_updated_tv);
        //set the custome toolbar
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);

        // Creating a button - Load More
        Button btnLoadMore = new Button(this);
        btnLoadMore.setText("Load More");

        // Adding button to listview at footer
        mListView.addFooterView(btnLoadMore);

        //intialize realm
        realm = RealmController.with(mActivity).getRealm();

        pref = new PrefManager(mActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mListView != null && mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void getStoryIds(){
        progressBar.setVisibility(View.VISIBLE);
        //fetching story ids using api's
        ApiGetTopStories apiGetTopStories = new ApiGetTopStories(mActivity, this);
        apiGetTopStories.getTopStories();
    }

    public void setAdapter(){
        //fetching all the records from realm
        RealmResults<Story> realmResult = RealmController.with(mActivity).getAllStory();
        mAdapter = new MainAdapter(realmResult);
        mListView.setAdapter(mAdapter);

    }

    @Override
    public void onGetStoryIdSuccess(ArrayList<Integer> idList) {
        storyCount = idList.size();
        //every story has different ids. calling and fetching story based on id
        for(Integer id : idList){
            ApiGetStory apiGetStory = new ApiGetStory(mActivity, this, id, realm);
            apiGetStory.getStory();
        }
    }

    @Override
    public void onGetStoryIdFailure() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onGetStorySuccess() {
        if(count == (storyCount -1)){
            progressBar.setVisibility(View.GONE);
            setAdapter();
            pref.setFirstTimeLaunch(true);
        } else {
            count++;
        }
    }

    @Override
    public void onGetStoryFailure() {
        progressBar.setVisibility(View.GONE);
    }
}
