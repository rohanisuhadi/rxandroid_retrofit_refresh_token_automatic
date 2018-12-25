package com.example.kamtiz.rxandroid2andjava.local;

import com.example.kamtiz.rxandroid2andjava.model.AccessToken;
import com.example.kamtiz.rxandroid2andjava.utils.network.TokenAccount;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Rajan Maurya
 *         On 20/08/17.
 */
public class FineractBaseDataManager {

    private DataManagerAuth dataManagerAuth;
    private TokenAccount preferencesHelper;

    public FineractBaseDataManager(DataManagerAuth dataManagerAuth,
                                   TokenAccount preferencesHelper) {
        this.dataManagerAuth = dataManagerAuth;
        this.preferencesHelper = preferencesHelper;
    }

    public <T> Observable<T> authenticatedObservableApi(Observable<T> observable) {
        return observable.onErrorResumeNext(refreshTokenAndRetryObser(observable));
    }

    public Completable authenticatedCompletableApi(Completable completable) {
        return completable.onErrorResumeNext(refreshTokenAndRetryCompletable(completable));
    }

    public <T> Function<Throwable, ? extends Observable<? extends T>> refreshTokenAndRetryObser(
            final Observable<T> toBeResumed) {
        return new Function<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> apply(Throwable throwable) throws Exception {
                // Here check if the error thrown really is a 403
                if (ExceptionStatusCode.isHttp401Error(throwable)) {
                    /*preferencesHelper.putBoolean(PreferenceKey.PREF_KEY_REFRESH_ACCESS_TOKEN, true);*/
                    return dataManagerAuth.refreshToken().concatMap(new Function<AccessToken,
                            ObservableSource<? extends T>>() {
                        @Override
                        public ObservableSource<? extends T> apply(
                                AccessToken authentication) throws Exception {
                            /*preferencesHelper.putBoolean(
                                    PreferenceKey.PREF_KEY_REFRESH_ACCESS_TOKEN, false);*/
                            preferencesHelper.setTokenAccount(
                                    authentication.getAccessToken());
                            /*preferencesHelper.putSignInUser(authentication);*/
                            return toBeResumed;
                        }
                    });
                }
                // re-throw this error because it's not recoverable from here
                return Observable.error(throwable);
            }
        };
    }

    public Function<Throwable, ? extends CompletableSource> refreshTokenAndRetryCompletable(
            final Completable toBeResumed) {
        return new Function<Throwable, CompletableSource>() {
            @Override
            public CompletableSource apply(Throwable throwable) throws Exception {
                // Here check if the error thrown really is a 403
                if (ExceptionStatusCode.isHttp401Error(throwable)) {
                    /*preferencesHelper.putBoolean(PreferenceKey.PREF_KEY_REFRESH_ACCESS_TOKEN, true);*/
                    return dataManagerAuth.refreshToken().flatMapCompletable(
                            new Function<AccessToken, CompletableSource>() {
                                @Override
                                public CompletableSource apply(AccessToken authentication)
                                        throws Exception {
                                    /*preferencesHelper.putBoolean(
                                            PreferenceKey.PREF_KEY_REFRESH_ACCESS_TOKEN, false);*/
                                    preferencesHelper.setTokenAccount(
                                            authentication.getAccessToken());
                                    /*preferencesHelper.putSignInUser(authentication);*/
                                    return toBeResumed;
                                }
                            });
                }
                // re-throw this error because it's not recoverable from here
                return Completable.error(throwable);
            }
        };
    }
}
