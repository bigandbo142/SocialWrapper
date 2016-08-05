package com.worklab.simplesocialgateway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.List;

public class SimpleFacebookActivity extends AppCompatActivity {

    CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TEST", "DO LOGIN SUCCESS");
            }

            @Override
            public void onCancel() {
                Log.d("TEST", "DO LOGIN CANCEL");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TEST", "DO LOGIN ERROR");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("TEST", "DO LOGIN onActivityResult");
    }

    protected void doLogin(){
        doLogin(Arrays.asList("public_profile"));
    }

    protected void doLogin(List<String> pPermissions){
        LoginManager.getInstance().logInWithReadPermissions(this, pPermissions);
    }
}
