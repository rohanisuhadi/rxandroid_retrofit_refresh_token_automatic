/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.example.kamtiz.rxandroid2andjava.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.example.kamtiz.rxandroid2andjava.utils.network.TokenAccount;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author Rajan Maurya
 * @since 17/03/2017
 */
public class FineractInterceptor implements Interceptor {

    public static final String HEADER_TENANT = "X-Tenant-Identifier";
    public static final String HEADER_AUTH = "Authorization";
    private static final String HEADER_ACCEPT_JSON = "Accept";
    private static final String HEADER_CONTENT_TYPE = "Content-type";
    public static final String HEADER_USER = "User";

    TokenAccount preferencesHelper;

    public FineractInterceptor(Context context) {

        this.preferencesHelper = new TokenAccount(context);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request chainRequest = chain.request();
        Builder builder = chainRequest.newBuilder();

        //TODO fix call single time instead of calling every request
        String authToken = preferencesHelper.getTokenAccount();

        builder.header(HEADER_ACCEPT_JSON, "application/json");
        builder.header(HEADER_CONTENT_TYPE, "application/json");

        if (!TextUtils.isEmpty(authToken)) {
            builder.header(HEADER_AUTH, "Bearer "+authToken);
        }

        Request request = builder.build();
        return chain.proceed(request);
    }
}
