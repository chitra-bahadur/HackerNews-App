package com.urbanpiperapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.urbanpiperapp.R;
import com.urbanpiperapp.constant.Constants;
import com.urbanpiperapp.data.Comments;
import com.urbanpiperapp.data.RealmController;
import com.urbanpiperapp.data.Story;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by chitra on 12/1/18.
 */

public class ArticleFragment extends BaseFragment {

    private ListView listView;
    private ProgressBar progressBar;
    private int storyId;
    private WebView webView;

    public ArticleFragment() {
    }

    public static ArticleFragment newInstance(int storyId){
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.STORY_ID, storyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listView);
        listView.setVisibility(View.GONE);
        progressBar = view.findViewById(R.id.progressBar);
        webView = view.findViewById(R.id.webView);
        webView.setVisibility(View.VISIBLE);
        storyId = getArguments().getInt(Constants.STORY_ID);
        Story story = RealmController.with(mActivity).getStory(storyId);

        if(!TextUtils.isEmpty(story.getUrl())){
            loadUrl(story.getUrl());
        }
    }

    public void loadUrl(String url){
        webView.loadUrl(url);
    }
}
