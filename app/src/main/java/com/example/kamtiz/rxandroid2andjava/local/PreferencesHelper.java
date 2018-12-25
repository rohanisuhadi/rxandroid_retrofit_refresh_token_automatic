package com.example.kamtiz.rxandroid2andjava.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import com.example.kamtiz.rxandroid2andjava.model.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Set;

public class PreferencesHelper {

    private final SharedPreferences preferences;
    private final Gson gson;

    public PreferencesHelper(Context context) {
        preferences = context.getSharedPreferences(PreferenceKey.PREF_MIFOS, Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz")
                .create();
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void clear() {
        getPreferences().edit().clear().apply();
    }

    public int getInt(String preferenceKey, int preferenceDefaultValue) {
        return getPreferences().getInt(preferenceKey, preferenceDefaultValue);
    }

    public void putInt(String preferenceKey, int preferenceValue) {
        getPreferences().edit().putInt(preferenceKey, preferenceValue).apply();
    }

    public long getLong(String preferenceKey, long preferenceDefaultValue) {
        return getPreferences().getLong(preferenceKey, preferenceDefaultValue);
    }

    public void putLong(String preferenceKey, long preferenceValue) {
        getPreferences().edit().putLong(preferenceKey, preferenceValue).apply();
    }

    public float getFloat(String preferenceKey, float preferenceDefaultValue) {
        return getPreferences().getFloat(preferenceKey, preferenceDefaultValue);
    }

    public void putFloat(String preferenceKey, float preferenceValue) {
        getPreferences().edit().putFloat(preferenceKey, preferenceValue).apply();
    }

    public boolean getBoolean(String preferenceKey, boolean preferenceDefaultValue) {
        return getPreferences().getBoolean(preferenceKey, preferenceDefaultValue);
    }

    public void putBoolean(String preferenceKey, boolean preferenceValue) {
        getPreferences().edit().putBoolean(preferenceKey, preferenceValue).apply();
    }

    public String getString(String preferenceKey, String preferenceDefaultValue) {
        return getPreferences().getString(preferenceKey, preferenceDefaultValue);
    }

    public void putString(String preferenceKey, String preferenceValue) {
        getPreferences().edit().putString(preferenceKey, preferenceValue).apply();
    }

    public void putStringSet(String preferenceKey, Set<String> stringSet) {
        getPreferences().edit().putStringSet(preferenceKey, stringSet).apply();
    }

    public Set<String> getStringSet(String preferenceKey) {
        return getPreferences().getStringSet(preferenceKey, null);
    }

    public void putAccessToken(String accessToken) {
        getPreferences().edit().putString(PreferenceKey.PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Nullable
    public String getAccessToken() {
        return getPreferences().getString(PreferenceKey.PREF_KEY_ACCESS_TOKEN, null);
    }

    public void putTenantIdentifier(String tenantIdentifier) {
        getPreferences().edit().putString(PreferenceKey.PREF_KEY_TENANT_IDENTIFIER,
                tenantIdentifier).apply();
    }

    public String getTenantIdentifier() {
        return getPreferences().getString(PreferenceKey.PREF_KEY_TENANT_IDENTIFIER, null);
    }

    public void putSignInUser(AccessToken user) {
        getPreferences().edit().putString(PreferenceKey.PREF_KEY_SIGNED_IN_USER,
                user.getAccessToken()).apply();
    }

    public String getSignedInUser() {
        String userJson = getPreferences().getString(PreferenceKey.PREF_KEY_SIGNED_IN_USER, null);
        if (userJson == null) return null;
        return userJson;
    }

    public void putUserName(String username) {
        getPreferences().edit().putString(PreferenceKey.PREF_KEY_USER_NAME, username).apply();
    }

    public String getUserName() {
        return getPreferences().getString(PreferenceKey.PREF_KEY_USER_NAME, null);
    }
}
