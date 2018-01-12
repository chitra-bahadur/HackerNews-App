package com.urbanpiperapp.auth;

import android.support.annotation.NonNull;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by chitra on 11/1/18.
 */

public class SignUpAuth {

    private ListenerSignUp callback;

    public SignUpAuth(ListenerSignUp callback) {
        this.callback = callback;
    }

    public interface ListenerSignUp {
        void signUpSuccess();
        void signUpFailure();
    }

    public void signUp(FirebaseAuth auth, String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user.
                        if (!task.isSuccessful()) {
                            callback.signUpFailure();
                            task.getException().printStackTrace();
                        } else {
                            callback.signUpSuccess();
                        }
                    }
                });
    }
}
