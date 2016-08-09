package com.worklab.social_gateway;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by skywander on 8/8/16.
 */
public interface IPublishMode {

    @SuppressWarnings("unused") void doShareLink(
            @NonNull String pLink,
            @Nullable String pTitle,
            @Nullable String pContent,
            @Nullable String pImageUrl,
            @Nullable String pQuote
    );

    @SuppressWarnings("unused") void doSharePhotos(
            @NonNull List<String> pImageUrls,
            @Nullable String pCaption
    );

    @SuppressWarnings("unused") void doSharePhotoBitmaps(
            @NonNull List<Bitmap> pImageUrls,
            @Nullable String pCaption
    );

    @SuppressWarnings("unused") void doShareVideos(
            @NonNull Uri pVideo,
            @Nullable String pTitle,
            @Nullable String pContent,
            @Nullable Uri pPreviewPhoto
    );
}
