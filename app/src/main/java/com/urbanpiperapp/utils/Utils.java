package com.urbanpiperapp.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.urbanpiperapp.constant.Constants;
import com.urbanpiperapp.ui.activities.ArticleActivity;
import com.urbanpiperapp.ui.activities.MainActivity;

/**
 * Created by chitra on 10/1/18.
 */

public class Utils {

    public void showToastMsg(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void goToMainScreen(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public void goToArticlScreen(Context context, int storyId){
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(Constants.STORY_ID, storyId);
        context.startActivity(intent);
    }
}
