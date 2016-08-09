package com.worklab.social_gateway;

import java.util.List;

/**
 * Created by skywander on 8/8/16.
 */
public interface IAuthentication {
    @SuppressWarnings("unused") void doLogin();
    @SuppressWarnings("unused") void doLoginWithReadMode(List<String> pPermissions);
    @SuppressWarnings("unused") void doLoginWithPublishMode(List<String> pPermissions);
    @SuppressWarnings("unused") void doLogout();
    @SuppressWarnings("unused") boolean isLogged();
    @SuppressWarnings("unused") List<String> getCurrentPermissions();
    @SuppressWarnings("unused") void registerFacebookListener(FacebookManagerCallback pListener);
}
