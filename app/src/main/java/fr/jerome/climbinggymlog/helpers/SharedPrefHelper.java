package fr.jerome.climbinggymlog.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import fr.jerome.climbinggymlog.activities.LoginActivity;
import fr.jerome.climbinggymlog.activities.MainActivity;
import fr.jerome.climbinggymlog.models.Client;

/**
 * Created by jerome on 21/06/15.
 */
public class SharedPrefHelper {

    public static void saveClientNum(Context context, String numClient) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.PREFKEY_NUM_CLIENT, numClient);

        hideLoginScreenForNextRun(editor);
        editor.commit();
    }

    private static void hideLoginScreenForNextRun(SharedPreferences.Editor editor) {
        editor.putBoolean(MainActivity.PREFKEY_SHOW_LOG_ACT, false);
    }
}
