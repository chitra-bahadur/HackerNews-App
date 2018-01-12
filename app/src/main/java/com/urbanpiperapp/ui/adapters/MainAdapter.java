package com.urbanpiperapp.ui.adapters;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.urbanpiperapp.R;
import com.urbanpiperapp.data.Story;
import com.urbanpiperapp.utils.DateTimeUtils;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by chitra on 11/1/18.
 */

public class MainAdapter extends RealmBaseAdapter<Story> implements ListAdapter {

    public MainAdapter(@Nullable OrderedRealmCollection<Story> data) {
        super(data);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MainViewHolder holder;
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_main_activity, viewGroup, false);
            holder = new MainViewHolder();
            holder.totalCommentsTv = view.findViewById(R.id.comments_tv);
            holder.timeTv = view.findViewById(R.id.time_tv);
            holder.titleTv = view.findViewById(R.id.title_tv);
            holder.upvoteTv = view.findViewById(R.id.upvote_tv);
            holder.userTv = view.findViewById(R.id.user_tv);
            holder.urlTv = view.findViewById(R.id.url_tv);

            view.setTag(holder);

        } else {
            holder = (MainViewHolder) view.getTag();
        }

        if(adapterData != null){
            final Story story = adapterData.get(i);
            holder.totalCommentsTv.setText(String.valueOf(story.getTotalComments()));

            holder.timeTv.setText(DateTimeUtils.getFormattedTime(story.getTime()) + "  .  ");
            holder.titleTv.setText(story.getTitle());
            holder.upvoteTv.setText(String.valueOf(story.getScore()));
            holder.urlTv.setText("www.example.com");
            holder.userTv.setText(story.getSubmitter());
        }

        return view;
    }

    private class MainViewHolder {
        TextView upvoteTv, titleTv, urlTv, timeTv, userTv, totalCommentsTv;
    }
}
