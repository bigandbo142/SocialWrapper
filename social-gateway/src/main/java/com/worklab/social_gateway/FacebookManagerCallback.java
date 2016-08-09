package com.worklab.social_gateway;

import com.facebook.FacebookCallback;
import com.facebook.share.Sharer;

/**
 * Created by skywander on 8/9/16.
 */
public interface FacebookManagerCallback extends FacebookCallback<Sharer.Result> {
    void onDataInvalid();
}
