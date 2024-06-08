package common_classes.button_clicking_animation;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class ButtonClickingAnimation {

    public static void animateButtonClick(View Button) {
        // Define the scale animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(Button, "scaleX", 1f, 0.95f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(Button, "scaleY", 1f, 0.95f, 1f);
        scaleX.setDuration(100); // Set animation duration
        scaleY.setDuration(100);
        scaleX.setInterpolator(new DecelerateInterpolator()); // Set animation interpolator
        scaleY.setInterpolator(new DecelerateInterpolator());

        // Start the animation
        scaleX.start();
        scaleY.start();
    }
}
