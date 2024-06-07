package common_classes.text_gradient_utility;

import android.content.Context;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.todomo.R;

public class TextGradientUtility {

    public static void applyGradientOnLayout(final Context context, final TextView textView) {
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove the listener to prevent multiple calls
                textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                applyGradient(context, textView);
            }
        });
    }

    private static void applyGradient(Context context, TextView textView) {
        int startColor = ContextCompat.getColor(context, R.color.main_app_gradient_dark_blue);
        int centerColor = ContextCompat.getColor(context, R.color.main_app_gradient_blue);
        int endColor = ContextCompat.getColor(context, R.color.main_app_gradient_purple);

        // Calculate the center coordinates of the text view
        int centerX = textView.getWidth() / 2;
        int centerY = textView.getHeight() / 2;

        // Calculate the radius for the gradient (assuming the radius should be half of the text view width)
        float radius = (float) textView.getWidth() / 2;

        // Create the radial gradient
        Shader shader = new RadialGradient(centerX, centerY, radius,
                new int[]{startColor, centerColor, endColor},
                null, Shader.TileMode.CLAMP);

        // Apply the gradient to the text view's paint
        textView.getPaint().setShader(shader);
    }
}
