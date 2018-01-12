package com.urbanpiperapp.data;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by chitra on 11/1/18.
 */

public class Comments extends RealmObject {
    @PrimaryKey
    private int id;

    private int storyId;

    //comments ids
    private RealmList<RealmInt> realmInt;

    private long time;

    private String submitter;

    private int parent;

    private String text;

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public RealmList<RealmInt> getRealmInt() {
        return realmInt;
    }

    public void setRealmInt(RealmList<RealmInt> realmInt) {
        this.realmInt = realmInt;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }
}
