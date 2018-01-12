package com.urbanpiperapp.data;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by chitra on 11/1/18.
 */

public class Story extends RealmObject {
    @PrimaryKey
    private int id;

    //number of comments on article
    private int totalComments;

    //comments ids
    private RealmList<RealmInt> realmInt;

    //total votes
    private int score;

    private long time;

    private String title;

    private String submitter;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }

    public RealmList<RealmInt> getRealmInt() {
        return realmInt;
    }

    public void setRealmInt(RealmList<RealmInt> realmInt) {
        this.realmInt = realmInt;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }
}
