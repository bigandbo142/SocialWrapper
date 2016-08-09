package com.worklab.social_gateway;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleFacebookActivity extends AppCompatActivity
        implements IAuthentication, IPublishMode {

    private CallbackManager mCallbackManager;
    private AccessToken mAccessToken;
    private FacebookManagerCallback mShareCallback = new FacebookManagerCallback() {
        @Override
        public void onDataInvalid() {

        }

        @Override
        public void onSuccess(Sharer.Result result) {

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

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
    public final void doLogout() {
        if(mAccessToken != null)
            LoginManager.getInstance().logOut();
    }

    @Override
    public final boolean isLogged() {
        return mAccessToken != null;
    }

    @Override
    public final List<String> getCurrentPermissions() {
        if(isLogged()){
            return new ArrayList<>(mAccessToken.getPermissions());
        }
        return null;
    }

    @Override
    public final void registerFacebookListener(FacebookManagerCallback pListener) {
        mShareCallback = pListener;
    }

    /**
     * Read mode section
     */

    /**
     * Publish mode section
     */

    @Override
    public final void doShareLink(
            @NonNull String pLink,
            @Nullable String pTitle,
            @Nullable String pContent,
            @Nullable String pImageUrl,
            @Nullable String pQuote
    ) {
        if(mAccessToken != null){
            ShareContent shareContent = new ShareLinkContent.Builder()
                    .setContentTitle(pTitle)
                    .setContentDescription(pContent)
                    .setImageUrl(Uri.parse(pImageUrl))
                    .setQuote(pQuote)
                    .setContentUrl(Uri.parse(pLink))
                    .build();
            ShareApi.share(shareContent, mShareCallback);
        }
    }

    @Override
    public final void doSharePhotos(
            @NonNull List<String> pImageUrls,
            @Nullable String pCaption
    ) {
        if(mAccessToken != null){
            if(pImageUrls.size() > 0) {
                ShareContent shareContent = null;
                if(pImageUrls.size() == 1){
                    SharePhoto sharePhoto = new SharePhoto.Builder()
                            .setImageUrl(Uri.parse(pImageUrls.get(0)))
                            .setCaption(pCaption)
                            .build();
                    shareContent = new SharePhotoContent.Builder()
                            .addPhoto(sharePhoto)
                            .build();
                }else{
                    List<SharePhoto> sharePhotos = new ArrayList<>();
                    for (String url : pImageUrls){
                        SharePhoto loopSharePhoto = new SharePhoto.Builder()
                                .setCaption(pCaption)
                                .setImageUrl(Uri.parse(url))
                                .build();
                        sharePhotos.add(loopSharePhoto);
                    }
                    shareContent = new SharePhotoContent.Builder()
                            .addPhotos(sharePhotos)
                            .build();
                }

                ShareApi.share(shareContent, mShareCallback);

            }
        }
    }

    @Override
    public final void doSharePhotoBitmaps(
            @NonNull List<Bitmap> pImages,
            @Nullable String pCaption
    ) {
        try {
            if (mAccessToken != null) {
                if (pImages.size() > 0) {
                    boolean isDataValid = true;
                    for (Bitmap loopBitmap : pImages) {
                        if(!isImageSizeValid(loopBitmap)){
                            isDataValid = false;
                            break;
                        }
                    }
                    if(isDataValid) {
                        ShareContent shareContent = null;
                        if (pImages.size() == 1) {
                            SharePhoto sharePhoto = new SharePhoto.Builder()
                                    .setBitmap(pImages.get(0))
                                    .setCaption(pCaption)
                                    .build();
                            shareContent = new SharePhotoContent.Builder()
                                    .addPhoto(sharePhoto)
                                    .build();
                        } else {
                            List<SharePhoto> sharePhotos = new ArrayList<>();
                            for (Bitmap bitmap : pImages) {
                                SharePhoto loopSharePhoto = new SharePhoto.Builder()
                                        .setCaption(pCaption)
                                        .setBitmap(bitmap)
                                        .build();
                                sharePhotos.add(loopSharePhoto);
                            }
                            shareContent = new SharePhotoContent.Builder()
                                    .addPhotos(sharePhotos)
                                    .build();
                        }

                        ShareApi.share(shareContent, mShareCallback);
                    }else {
                        mShareCallback.onDataInvalid();
                    }

                }
            }
        }catch (OutOfMemoryError ome){
            ome.printStackTrace();
            mShareCallback.onDataInvalid();
        }
    }

    @Override
    public final void doShareVideos(
            @NonNull Uri pVideo,
            @Nullable String pTitle,
            @Nullable String pContent,
            @Nullable Uri pPreviewPhoto
    ) {
        if(mAccessToken != null){
            try{
                ShareVideo shareVideo = new ShareVideo.Builder()
                        .setLocalUrl(pVideo)
                        .build();
                SharePhoto sharePhoto = new SharePhoto.Builder()
                        .setImageUrl(pPreviewPhoto)
                        .build();
                ShareContent shareContent = new ShareVideoContent.Builder()
                        .setVideo(shareVideo)
                        .setContentTitle(pTitle)
                        .setContentDescription(pContent)
                        .setPreviewPhoto(sharePhoto)
                        .build();
                ShareApi.share(shareContent, mShareCallback);
            }catch (FacebookException fex){
                fex.printStackTrace();
                mShareCallback.onDataInvalid();
            }
        }
    }

    private boolean isImageSizeValid(Bitmap pBitmap){
        return Utilities.byteSizeOf(pBitmap) <= Permissions.FacebookPermissions.IMAGE_SIZE_LIMIT;
    }
}
