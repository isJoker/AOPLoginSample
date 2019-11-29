package com.jokerwan.aoploginsample.utils;

/**
 * Created by JokerWan on 2019-11-29.
 * Function:
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Created by Jokerwan on 2016/11/3.
 * Fix by feling on 2019/08/27.
 * Function：SP存储工具类
 */
public class SharedPreferencesUtil {

    private SharedPreferencesUtil() {
        throw new UnsupportedOperationException(":you can't instantiate me.");
    }

    /**
     * put string into preferences.
     *
     * @param context The context of SharedPreferences.
     * @param key     The name of the preference to modify.
     * @param value   The new value for the preference.
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putString(@NonNull Context context, @NonNull String key, @Nullable String value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * get string preferences
     *
     * @param context      context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a string
     */
    @Nullable
    public static String getString(@NonNull Context context, @NonNull String key, @Nullable String defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(key, defaultValue);
    }

    /**
     * put int into preferences.
     *
     * @param context The context of SharedPreferences.
     * @param key     The name of the preference to modify.
     * @param value   The new value for the preference.
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(@NonNull Context context, @NonNull String key, int value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * get int from preferences.
     *
     * @param context      The context of SharedPreferences.
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return The preference value if it exists, or defValue.
     * @throws ClassCastException if there is a preference with this name that is not a int.
     */
    public static int getInt(@NonNull Context context, @NonNull String key, int defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }

    /**
     * put long into preferences.
     *
     * @param context The context of SharedPreferences.
     * @param key     The name of the preference to modify.
     * @param value   The new value for the preference.
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(@NonNull Context context, @NonNull String key, long value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long from preferences.
     *
     * @param context      The context of SharedPreferences.
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return The preference value if it exists, or defValue.
     * @throws ClassCastException if there is a preference with this name that is not a long.
     */
    public static long getLong(@NonNull Context context, @NonNull String key, long defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getLong(key, defaultValue);
    }


    /**
     * put float into preferences.
     *
     * @param context The context of SharedPreferences.
     * @param key     The name of the preference to modify.
     * @param value   The new value for the preference.
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(@NonNull Context context, @NonNull String key, float value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get float from preferences.
     *
     * @param context      The context of SharedPreferences.
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return The preference value if it exists, or defValue.
     * @throws ClassCastException if there is a preference with this name that is not a float.
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * put boolean into preferences.
     *
     * @param context The context of SharedPreferences.
     * @param key     The name of the preference to modify.
     * @param value   The new value for the preference.
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(@NonNull Context context, @NonNull String key, boolean value) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean from preferences.
     *
     * @param context      The context of SharedPreferences.
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     * @return The preference value if it exists, or defValue.
     * @throws ClassCastException if there is a preference with this name that is not a boolean.
     */
    public static boolean getBoolean(@NonNull Context context, @NonNull String key, boolean defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * clear preferences data.
     *
     * @param context The context of SharedPreferences.
     */
    public static boolean clearSPData(@NonNull Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        return editor.commit();
    }
}
