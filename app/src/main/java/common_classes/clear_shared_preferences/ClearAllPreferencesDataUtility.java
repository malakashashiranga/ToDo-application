package common_classes.clear_shared_preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ClearAllPreferencesDataUtility {

    public static void clearSharedPreferences(Activity activity, String preferencesName) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
