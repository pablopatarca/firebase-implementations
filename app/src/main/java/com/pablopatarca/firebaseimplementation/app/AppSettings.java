package com.pablopatarca.firebaseimplementation.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pablopatarca.firebaseimplementation.models.User;

/**
 * Created by Pablo on 30/7/16.
 */
public class AppSettings {

    private static Context sContext;

    public AppSettings() {
    }

    public static void init(Context ctx){
        sContext = ctx;
    }

    public static void destroy(){
        sContext = null;
    }

    /**
     * Clear app data. Used where user is logged out.
     * WARNING, RESETS ALL THE SHARED PREFERENCES
     */
    public static void cleanSettings() {

        sContext.getSharedPreferences(KeysSetting.FIREBASE_SETTINGS.name(), Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();
    }


    private static SharedPreferences getSharedPreferences() {

        return sContext.getSharedPreferences(KeysSetting.FIREBASE_SETTINGS.name(), Context.MODE_PRIVATE);
    }

    public static void setTokenValue(String value) {
        getSharedPreferences().edit().putString(KeysSetting.TOKEN_VALUE.name(), value).apply();
    }

    public static String getTokenValue() {
        return getSharedPreferences().getString(KeysSetting.TOKEN_VALUE.name(), null);
    }

    public static void setUserData(User user) {
        String jsonUser = new Gson().toJson(user);
        getSharedPreferences().edit().putString(KeysValues.USER_DATA.name(), jsonUser).commit();
    }

    public static User getUser() {
        String jsonUser = getSharedPreferences().getString(KeysValues.USER_DATA.name(), null);
        return new Gson().fromJson(jsonUser, User.class);
    }

    public static boolean isLoggedIn() {
        return getTokenValue() != null;
    }

    enum KeysSetting {
        FIREBASE_SETTINGS,
        TOKEN_VALUE
    }

    enum KeysValues {
        USER_DATA
    }

}
