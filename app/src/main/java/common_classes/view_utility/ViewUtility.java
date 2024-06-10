package common_classes.view_utility;

import android.view.View;

public class ViewUtility {

    public static void disableViews(View... views) {
        for (View view : views) {
            view.setEnabled(false);
        }
    }

    public static void enableViews(View... views) {
        for (View view : views) {
            view.setEnabled(true);
        }
    }

}
