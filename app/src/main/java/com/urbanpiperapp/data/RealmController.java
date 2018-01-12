package com.urbanpiperapp.data;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by chitra on 11/1/18.
 */

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application){
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment){
        if(instance == null){
            instance = new RealmController(fragment.getActivity().getApplication());
        }

        return instance;
    }

    public static RealmController with(Activity activity){
        if(instance == null){
            instance = new RealmController(activity.getApplication());
        }

        return instance;
    }

    public static RealmController with(Application application){
        if(instance == null){
            instance = new RealmController(application);
        }

        return instance;
    }

    public static RealmController getInstance(){
        return instance;
    }

    public Realm getRealm(){
        return realm;
    }

    //Refresh the realm instance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Story.class
    public void clearAll() {

        realm.beginTransaction();
        realm.delete(Story.class);
        realm.commitTransaction();
    }

    //find all objects in the Story.class
    public RealmResults<Story> getAllStory() {
        return realm.where(Story.class).findAll();
    }

    //query a single item with the given id
    public Story getStory(int id) {
        return realm.where(Story.class).equalTo("id", id).findFirst();
    }

    //find all comments
    public RealmResults<Comments> getAllCommentsOnStory(int storyId){
        return realm.where(Comments.class).equalTo("storyId", storyId).findAll();
    }

    //find comments by id
    public Comments getComments(int id) {
        return realm.where(Comments.class).equalTo("storyId", id).findFirst();
    }

}
