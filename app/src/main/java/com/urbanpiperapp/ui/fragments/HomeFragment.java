package com.urbanpiperapp.ui.fragments;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.urbanpiperapp.R;
import com.urbanpiperapp.constant.Constants;
import com.urbanpiperapp.data.Comments;
import com.urbanpiperapp.ui.adapters.TabAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chitra on 12/1/18.
 */

public class HomeFragment extends BaseFragment {
    private View view;
    private ViewPager viewPager;
    private TabLayout tabs;
    private TabAdapter tabAdapter;
    private int storyId;
    private int totalComments;
    public static HomeFragment newInstance(int storyId, int totalComments){
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.STORY_ID, storyId);
        args.putInt(Constants.TOTAL_COMMENTS, totalComments);
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            storyId = getArguments().getInt(Constants.STORY_ID);
            totalComments = getArguments().getInt(Constants.TOTAL_COMMENTS);
            getComments();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTabs();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.view_pager);
        tabs = view.findViewById(R.id.tabs);

        return view;
    }

    public void setTabs(){
        //adding fragments
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(CommentsFragment.newInstance(storyId));
        fragmentList.add(ArticleFragment.newInstance(storyId));

        //adding fragments title
        List<String> titleList = new ArrayList<>();
        titleList.add(totalComments + " COMMENTS");
        titleList.add("ARTICLE");
        tabAdapter = new TabAdapter(getChildFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(tabAdapter);
        viewPager.setOffscreenPageLimit(1);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setTabMode(TabLayout.MODE_FIXED);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(mActivity, "" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void getComments(){

    }
}
