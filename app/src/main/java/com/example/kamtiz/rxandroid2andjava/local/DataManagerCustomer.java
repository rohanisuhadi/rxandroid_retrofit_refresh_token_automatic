package com.example.kamtiz.rxandroid2andjava.local;

import android.util.Log;
import com.example.kamtiz.rxandroid2andjava.model.AudioBook;
import com.example.kamtiz.rxandroid2andjava.network.APIAuthentication;
import com.example.kamtiz.rxandroid2andjava.utils.network.TokenAccount;
import io.reactivex.Observable;

import java.util.List;
import java.util.Map;

/**
 * @author Rajan Maurya
 *         On 20/06/17.
 */
public class DataManagerCustomer extends FineractBaseDataManager {

    private final APIAuthentication baseApiManager;
    private final TokenAccount preferencesHelper;

    public DataManagerCustomer(APIAuthentication baseApiManager, TokenAccount preferencesHelper,
                               DataManagerAuth dataManagerAuth) {
        super(dataManagerAuth, preferencesHelper);
        this.baseApiManager = baseApiManager;
        this.preferencesHelper = preferencesHelper;
    }

    public Observable<List<AudioBook>> fetchCustomers(Map<String, String> params) {
        Log.e("DataManagerCustomer",preferencesHelper.getTokenAccount());
        return authenticatedObservableApi(baseApiManager.getAudioBook(params));
    }
}
