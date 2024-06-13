package common_classes.pick_time;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

import common_classes.button_clicking_animation.ButtonClickingAnimation;

public class TimePicker {

    public static void setTimePicker(final Context context, final EditText editText, ImageButton imageButton) {
        imageButton.setOnClickListener(v -> {
            // Animate the button click
            ButtonClickingAnimation.animateButtonClick(imageButton);

            // Delay launching the date picker dialog slightly
            new Handler().postDelayed(() -> showTimePickerDialog(context, editText), 200);
        });
    }

    private static void showTimePickerDialog(Context context, final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                (android.widget.TimePicker view, int hourOfDay, int minute1) -> {
                    // Update EditText with selected time
                    @SuppressLint("DefaultLocale") String selectedTime = String.format("%02d:%02d", hourOfDay, minute1);
                    editText.setText(selectedTime);
                },
                hour, minute, true);

        // Show the time picker dialog
        timePickerDialog.show();
    }
}

