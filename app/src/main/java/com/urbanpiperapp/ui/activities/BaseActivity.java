package com.urbanpiperapp.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.urbanpiperapp.utils.Utils;

/**
 * Created by chitra on 10/1/18.
 */

public class BaseActivity extends AppCompatActivity {

    public Activity mActivity;
    public Utils utils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;

        utils = new Utils();
    }
}
