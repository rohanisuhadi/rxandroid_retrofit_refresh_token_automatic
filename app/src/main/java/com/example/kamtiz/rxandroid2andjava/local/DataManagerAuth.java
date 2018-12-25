package com.example.kamtiz.rxandroid2andjava.local;

import com.example.kamtiz.rxandroid2andjava.model.AccessToken;
import com.example.kamtiz.rxandroid2andjava.model.User;
import com.example.kamtiz.rxandroid2andjava.network.APIAuthentication;
import com.example.kamtiz.rxandroid2andjava.utils.network.TokenAccount;
import io.reactivex.Observable;

/**
 * @author Rajan Maurya On 16/03/17.
 */
public class DataManagerAuth {

    private final APIAuthentication baseApiManager;
    private final TokenAccount preferencesHelper;

    public DataManagerAuth(APIAuthentication baseApiManager, TokenAccount preferencesHelper) {
        this.baseApiManager = baseApiManager;
        this.preferencesHelper = preferencesHelper;
    }

    public TokenAccount getPreferencesHelper() {
        return preferencesHelper;
    }

    public Observable<User> login(User user) {
        return baseApiManager.loginEmail(user);
    }

    public Observable<AccessToken> refreshToken() {
        return baseApiManager.refreshToken();
    }
}