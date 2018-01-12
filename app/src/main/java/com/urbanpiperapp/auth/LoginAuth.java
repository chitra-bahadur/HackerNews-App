package com.urbanpiperapp.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by chitra on 11/1/18.
 */

public class LoginAuth {

    ListenerLogin callback;
    public LoginAuth(ListenerLogin callback){
        this.callback = callback;
    }

    public interface ListenerLogin {
        void loginSuccess();
        void loginFailure(String msg);
    }

    public void login(FirebaseAuth auth, String email, final String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                callback.loginFailure("Minimum Password length");
                            } else {
                                callback.loginFailure("Authentication Failed");
                            }
                        } else {
                            callback.loginSuccess();
                        }
                    }
                });
    }
}
