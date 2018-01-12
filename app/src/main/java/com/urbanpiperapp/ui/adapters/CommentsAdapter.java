package com.urbanpiperapp.ui.adapters;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.urbanpiperapp.R;
import com.urbanpiperapp.data.Comments;
import com.urbanpiperapp.data.Story;
import com.urbanpiperapp.utils.DateTimeUtils;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by chitra on 11/1/18.
 */

public class CommentsAdapter extends RealmBaseAdapter<Comments> implements ListAdapter {

    public CommentsAdapter(@Nullable OrderedRealmCollection<Comments> data) {
        super(data);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MainViewHolder holder;
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_comments_fragment, viewGroup, false);
            holder = new MainViewHolder();
            holder.commentTv = view.findViewById(R.id.comments_tv);
            view.setTag(holder);

        } else {
            holder = (MainViewHolder) view.getTag();
        }

        if(adapterData != null){
            final Comments comments = adapterData.get(i);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(comments.getTime());
            SimpleDateFormat month_date = new SimpleDateFormat("MMM");
            String htmlText = "<html><body>" +
                    cal.get(Calendar.DAY_OF_MONTH) + month_date.format(cal.get(Calendar.MONTH)) + "," + cal.get(Calendar.YEAR) +
                     " - " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.SECOND) + " . " +
                    comments.getSubmitter() + "<br>" +
                    comments.getText() +
                    "</body></html>";


            //showing html text in webview
            holder.commentTv.loadUrl(htmlText);

            //Log.d("Adapter", comments.getText());
        }

        return view;
    }

    private class MainViewHolder {
        WebView commentTv;
    }
}
