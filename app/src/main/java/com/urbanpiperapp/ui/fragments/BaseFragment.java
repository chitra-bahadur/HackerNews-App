package com.urbanpiperapp.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.urbanpiperapp.utils.Utils;

/**
 * Created by chitra on 12/1/18.
 */

public class BaseFragment extends Fragment {

    public Activity mActivity;
    public Utils utils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        utils = new Utils();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
