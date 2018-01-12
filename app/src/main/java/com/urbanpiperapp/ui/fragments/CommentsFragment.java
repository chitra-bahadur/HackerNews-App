package com.urbanpiperapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.urbanpiperapp.R;
import com.urbanpiperapp.constant.Constants;
import com.urbanpiperapp.data.Comments;
import com.urbanpiperapp.data.RealmController;
import com.urbanpiperapp.data.RealmInt;
import com.urbanpiperapp.data.Story;
import com.urbanpiperapp.net.ApiGetComments;
import com.urbanpiperapp.ui.adapters.CommentsAdapter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by chitra on 12/1/18.
 */

public class CommentsFragment extends BaseFragment implements ApiGetComments.ListenerComments {

    private ListView listView;
    private ProgressBar progressBar;
    private Realm realm;
    private int storyId;
    private int count, commentsCount;
    private CommentsAdapter adapter;
    public CommentsFragment() {

    }

    public static CommentsFragment newInstance(int storyId){
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.STORY_ID, storyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(adapter != null && listView != null){
            adapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_article, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listView);
        progressBar = view.findViewById(R.id.progressBar);
        storyId = getArguments().getInt(Constants.STORY_ID);
        realm = RealmController.getInstance().getRealm();
        RealmResults<Comments> realmResults = RealmController.with(mActivity).getAllCommentsOnStory(storyId);
        if(realmResults == null || realmResults.size() == 0) {
            getComments();
        } else {
            setAdapter();
        }
    }

    public void getComments(){
        progressBar.setVisibility(View.VISIBLE);
        Story story = RealmController.with(mActivity).getStory(storyId);

        RealmList<RealmInt> kidsArr = story.getRealmInt();
        commentsCount = kidsArr.size();
        for(RealmInt realmInt : kidsArr){
            int id = realmInt.getCommentId();
            ApiGetComments apiGetComments = new ApiGetComments(mActivity, this, storyId,id, realm);
            apiGetComments.getComments();
        }
    }

    public void setAdapter(){
        RealmResults<Comments> commentsList = RealmController.with(mActivity).getAllCommentsOnStory(storyId);
        adapter = new CommentsAdapter(commentsList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onGetCommentsSuccess() {
        if(count == (commentsCount -1)) {
            setAdapter();
            progressBar.setVisibility(View.GONE);
            realm.close();
        } else {
            count++;
        }
    }

    @Override
    public void onGetCommentsIdFailure() {
        progressBar.setVisibility(View.GONE);
    }
}
