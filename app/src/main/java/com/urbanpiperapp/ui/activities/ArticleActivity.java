package com.urbanpiperapp.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.urbanpiperapp.R;
import com.urbanpiperapp.constant.Constants;
import com.urbanpiperapp.data.RealmController;
import com.urbanpiperapp.data.Story;
import com.urbanpiperapp.ui.fragments.HomeFragment;
import com.urbanpiperapp.utils.DateTimeUtils;

public class ArticleActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView lastUpdatedTv;
    private TextView titleTv;
    private TextView toolbarTitleTv;
    private TextView urlTv;
    private TextView timeAndUserTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        int storyId = getIntent().getIntExtra(Constants.STORY_ID, 0);
        toolbar = findViewById(R.id.toolbarLayout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleTv = toolbar.findViewById(R.id.title_tv);
        lastUpdatedTv = toolbar.findViewById(R.id.last_updated_tv);
        titleTv.setVisibility(View.GONE);
        lastUpdatedTv.setVisibility(View.GONE);

        toolbarTitleTv = findViewById(R.id.toolbar_title_tv);
        urlTv = findViewById(R.id.url_tv);
        timeAndUserTv = findViewById(R.id.time_and_user_tv);

        Story story = RealmController.with(mActivity).getStory(storyId);
        int totalComments = story.getRealmInt().size();

        toolbarTitleTv.setText(story.getTitle());
        urlTv.setText("www.example.com");
        timeAndUserTv.setText(DateTimeUtils.getFormattedTime(story.getTime())
                + " . " + story.getSubmitter());

        setFragment(HomeFragment.newInstance(storyId, totalComments), "HomeFragment");
    }

    //set the fragment for tabs
    private void setFragment(Fragment fragment, String tag){
        FragmentManager mManager = getSupportFragmentManager();
        FragmentTransaction mTransaction = mManager.beginTransaction();
        mTransaction.replace(R.id.content_frame, fragment, tag);
        mTransaction.addToBackStack(tag);
        mTransaction.commit();

    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
