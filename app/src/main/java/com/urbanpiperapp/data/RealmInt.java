package com.urbanpiperapp.data;

import io.realm.RealmObject;

/**
 * Created by chitra on 11/1/18.
 */

public class RealmInt extends RealmObject {
    private int commentId;

    public RealmInt() {
    }

    public RealmInt(int commentId) {
        this.commentId = commentId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
}
