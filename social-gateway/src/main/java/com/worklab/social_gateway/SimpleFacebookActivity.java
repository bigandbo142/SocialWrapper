package com.worklab.social_gateway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleFacebookActivity extends AppCompatActivity
        implements IAuthentication {

    private CallbackManager mCallbackManager;
    private AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {;
                mAccessToken = AccessToken.getCurrentAccessToken();
            }

            @Override
            public void onCancel() {
                mAccessToken = null;
            }

            @Override
            public void onError(FacebookException error) {
                mAccessToken = null;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Authentication section
     */

    @Override
    public final void doLogin(){
        doLoginWithReadMode(Arrays.asList("public_profile"));
    }

    @Override
    public final void doLoginWithReadMode(List<String> pPermissions) {
        LoginManager.getInstance().logInWithReadPermissions(this, pPermissions);
    }

    @Override
    public final void doLoginWithPublishMode(List<String> pPermissions) {
        LoginManager.getInstance().logInWithPublishPermissions(this, pPermissions);
    }

    @Override
    public void doLogout() {
        if(mAccessToken != null)
            LoginManager.getInstance().logOut();
    }

    @Override
    public boolean isLogged() {
        return mAccessToken != null;
    }

    @Override
    public List<String> getCurrentPermissions() {
        if(isLogged()){
            return new ArrayList<>(mAccessToken.getPermissions());
        }
        return null;
    }

    /**
     * Read mode section
     */

    /**
     * Publish mode section
     */
}
