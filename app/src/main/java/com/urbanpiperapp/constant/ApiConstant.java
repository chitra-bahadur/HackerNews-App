package com.urbanpiperapp.constant;

/**
 * Created by chitra on 10/1/18.
 */

public class ApiConstant {
    public static final String BASE_URL = "https://hacker-news.firebaseio.com/v0/";
    public static final String TAIL = "?print=pretty";
    public static final String TOP_STORIES_URL = BASE_URL + "topstories.json" + TAIL;
    public static final String ITEM_EXTENSION = ".json";
    public static final String STORY_URL = BASE_URL + "item/";
    public static final String COMMENT_URL = BASE_URL + "item/" ;

    //https://hacker-news.firebaseio.com/v0/item/2921983.json?print=pretty
}
