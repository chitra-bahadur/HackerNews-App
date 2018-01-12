package com.urbanpiperapp.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import java.util.Calendar;


/**
 * Created by chitra on 12/1/18.
 */

public class DateTimeUtils {
    static final long WEEK = 24 * 60 * 60 * 7;
    static final long DAY = 24 * 60 * 60;
    static final long HOUR = 60 * 60;
    static final long MINUTE = 60;
    public static CharSequence getFormattedTime(long time){
        //Log.d("DateUtils", "" + DateUtils.formatElapsedTime(time));

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);

        long now = System.currentTimeMillis();
        long timeGap = now - cal.getTimeInMillis() / 1000;

        if (timeGap < 0) {
            return "" + time;
        } else if (timeGap < MINUTE) {
            return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.SECOND_IN_MILLIS);
        } else if (timeGap < HOUR) {
            return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
        } else if (timeGap < DAY) {
            return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.HOUR_IN_MILLIS);
        } else if (timeGap < WEEK) {
            return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.DAY_IN_MILLIS);
        } else if (timeGap < WEEK * 4) {
            return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.WEEK_IN_MILLIS);
        }  else if (timeGap < WEEK * 8) {
            return "1 month ago";
        } else if (timeGap < WEEK * 12) {
            return "2 months ago";
        } else if (timeGap < WEEK * 16) {
            return "3 months ago";
        } else if (timeGap < WEEK * 20) {
            return "4 months ago";
        } else if (timeGap < WEEK * 24) {
            return "5 months ago";
        } else if (timeGap < WEEK * 28) {
            return "6 months ago";
        } else if (timeGap < WEEK * 32) {
            return "7 months ago";
        } else if (timeGap < WEEK * 36) {
            return "8 months ago";
        } else if (timeGap < WEEK * 40) {
            return "9 months ago";
        } else {
            return "About an year ago";
        }
    }

}
