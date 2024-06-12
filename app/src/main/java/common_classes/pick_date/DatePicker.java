package common_classes.pick_date;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

import common_classes.button_clicking_animation.ButtonClickingAnimation;

public class DatePicker {

    public static void setDatePicker(final Context context, final EditText editText, ImageButton imageButton) {
        imageButton.setOnClickListener(v -> {
            // Animate the button click
            ButtonClickingAnimation.animateButtonClick(imageButton);

            // Delay launching the date picker dialog slightly
            new Handler().postDelayed(() -> showDatePickerDialog(context, editText), 200);
        });
    }

    private static void showDatePickerDialog(Context context, final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, year1, month1, dayOfMonth1) -> {
                    // Update EditText with selected date
                    String selectedDate = dayOfMonth1 + "/" + (month1 + 1) + "/" + year1;
                    editText.setText(selectedDate);
                },
                year, month, dayOfMonth);

        // Show the date picker dialog
        datePickerDialog.show();
    }
}
