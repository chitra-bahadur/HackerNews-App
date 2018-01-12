package com.urbanpiperapp.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.urbanpiperapp.R;
import com.urbanpiperapp.auth.LoginAuth;
import com.urbanpiperapp.auth.SignUpAuth;

public class LoginActivity extends BaseActivity implements View.OnClickListener,
        LoginAuth.ListenerLogin, SignUpAuth.ListenerSignUp{

    private static final String TAG = "LoginActivity";

    private EditText mEmailEt, mPasswordEt;
    private Button mLoginBtn, mSignUpBtn;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            // User is logged in
            utils.goToMainScreen(mActivity);
            finish();
        }

        init();
    }

    private void init(){
        mEmailEt = findViewById(R.id.email);
        mPasswordEt = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.sign_in_button);
        mSignUpBtn = findViewById(R.id.sign_up_button);
        mProgressBar = findViewById(R.id.progressBar);

        mSignUpBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
    }

    //Register
    private void signUp(){
        String email = mEmailEt.getText().toString();
        String password = mPasswordEt.getText().toString();
        if (isFieldEmpty(email)) {
            utils.showToastMsg(mActivity, "Enter email address!");
            return;
        }

        if (isFieldEmpty(email)) {
            utils.showToastMsg(mActivity, "Enter password!");
            return;
        }

        if (password.length() < 6) {
            utils.showToastMsg(mActivity, "Password too short, enter minimum 6 characters");
            return;
        }
        mProgressBar.setVisibility(View.VISIBLE);

        //create user
        SignUpAuth auth = new SignUpAuth(this);
        auth.signUp(mAuth, email, password);

    }

    //Login
    private void login(){
        String email = mEmailEt.getText().toString();
        final String password = mPasswordEt.getText().toString();
        if (isFieldEmpty(email)) {
            utils.showToastMsg(mActivity, "Enter email address!");
            return;
        }

        if (isFieldEmpty(email)) {
            utils.showToastMsg(mActivity, "Enter password!");
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);
        //authenticate user
        LoginAuth authenticator = new LoginAuth(this);
        authenticator.login(mAuth, email, password);
    }

    //check password or emails not empty
    private boolean isFieldEmpty(String email){
        if(TextUtils.isEmpty(email)){
            return true;
        } else {
            return false;
        }
    }

    //clearing email and password after sign up
    private void clearFields(){
        mEmailEt.setText("");
        mPasswordEt.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_in_button :
                login();
                break;

            case R.id.sign_up_button :
                signUp();
                break;
        }
    }

    @Override
    public void loginSuccess() {
        mProgressBar.setVisibility(View.GONE);
        utils.goToMainScreen(mActivity);
        finish();
    }

    @Override
    public void loginFailure(String msg) {
        mProgressBar.setVisibility(View.GONE);
        if (msg.equalsIgnoreCase("Minimum Password length")) {
            mPasswordEt.setError("Minimum Password length");
        } else {
            utils.showToastMsg(mActivity, "Authentication Failed");
        }
    }

    @Override
    public void signUpSuccess() {
        mProgressBar.setVisibility(View.GONE);
        utils.showToastMsg(mActivity, "Authentication Success");
        clearFields();
    }

    @Override
    public void signUpFailure() {
        mProgressBar.setVisibility(View.GONE);
        utils.showToastMsg(mActivity, "Authentication failed");
    }
}
