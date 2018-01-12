package com.urbanpiperapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * Created by chitra on 11/1/18.
 */

public class PrefManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    //shared pref mode
    private int private_mode = 0;

    //shared prefrence file name
    private static final String PREF_NAME = "UrbanPiperApp";
    private static final String IS_FIRST_TIME_LAUNCH = "isFirstTimeLaunch";
    private static final String LAST_UPDATED = "lastUpdated";

    public PrefManager(Context context){
        _context = context;
        pref = _context.getSharedPreferences(PREF_NAME, private_mode);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTimeLaunch){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH,  isFirstTimeLaunch);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, false);
    }

    public void setLastUpdated(){
        editor.putLong(LAST_UPDATED, System.currentTimeMillis());
        editor.commit();
    }

    public long getLastUpdated(){
        return pref.getLong(LAST_UPDATED, System.currentTimeMillis());
    }
}
