package com.example.todomo.sign_up_activity.set_password_activity;

import static common_classes.check_edit_text_empty_utility.CheckEditTextEmptyUtility.isEditTextEmpty;
import static common_classes.password_fields_checks.PasswordFieldsChecks.areFieldsEqual;
import static common_classes.password_fields_checks.PasswordFieldsChecks.isLengthGreaterThan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.todomo.R;
import com.example.todomo.sign_in_activity.SignInScreenActivity;
import com.example.todomo.sign_up_activity.get_details_activity.GetDetailsScreenActivity;
import com.example.todomo.sign_up_activity.store_user_data.StoreUserData;

import common_classes.button_clicking_animation.ButtonClickingAnimation;
import common_classes.network_checking.NetworkAvailability;
import common_classes.view_utility.ViewUtility;

public class SetPasswordScreenActionHandler  implements StoreUserData.FirebaseCheckCallback {

    @SuppressLint("StaticFieldLeak")
    private static Button finishButton;
    @SuppressLint("StaticFieldLeak")
    private static ProgressBar progressBar;
    @SuppressLint("StaticFieldLeak")
    private static EditText newPasswordEditText;
    @SuppressLint("StaticFieldLeak")
    private static EditText confirmNewPasswordEditText;

    public static void initializeViews(@NonNull Button button , @NonNull ProgressBar progressBarView, @NonNull EditText newPassword, @NonNull EditText confirmPassword) {
        finishButton = button;
        progressBar = progressBarView;
        newPasswordEditText = newPassword;
        confirmNewPasswordEditText = confirmPassword;

    }

    public static void handleFinishButtonClick(@NonNull Activity activity) {
        finishButton.setOnClickListener(v -> {
            ButtonClickingAnimation.animateButtonClick(finishButton);
            progressBar.setVisibility(View.VISIBLE);
            disableAllViews();

            Intent getIntent = activity.getIntent();

            String newPassword = newPasswordEditText.getText().toString().trim();

            if (validateInputFields(activity)) {

                if (getIntent != null && getIntent.hasExtra("FirstName") && getIntent.hasExtra("LastName") && getIntent.hasExtra("Email")) {

                    String firstName = getIntent.getStringExtra("FirstName");
                    String lastName = getIntent.getStringExtra("LastName");
                    String email = getIntent.getStringExtra("Email");

                    if (!NetworkAvailability.isNetworkAvailable(activity)) {
                        Toast.makeText(activity, "No network connection available", Toast.LENGTH_LONG).show();
                        enableAllViews();
                        progressBar.setVisibility(View.INVISIBLE);
                        return;
                    }

                    StoreUserData.FirebaseManager firebaseManager = new StoreUserData.FirebaseManager(new SetPasswordScreenActionHandler());
                    firebaseManager.registerUser(firstName, lastName, email, newPassword);
                }
            } else {
                enableAllViews();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public static void handleBackButtonClick(@NonNull Activity activity) {
        Intent intent = new Intent(activity, GetDetailsScreenActivity.class);

        intent.removeExtra("FirstName");
        intent.removeExtra("LastName");
        intent.removeExtra("Email");

        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        activity.finish();
    }

    private static boolean validateInputFields(Activity activity) {

        if (isEditTextEmpty(newPasswordEditText)) {
            Toast.makeText(activity, "New password field cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (isEditTextEmpty(confirmNewPasswordEditText)) {
            Toast.makeText(activity, "Confirm new password field cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (!areFieldsEqual(newPasswordEditText, confirmNewPasswordEditText)) {
            Toast.makeText(activity, "New password and confirm password must be the same", Toast.LENGTH_LONG).show();
            return false;
        } else if (!isLengthGreaterThan(newPasswordEditText, 6)) {
            Toast.makeText(activity, "Password must be greater than 6 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onSuccess(String message) {
        progressBar.setVisibility(View.INVISIBLE);
        enableAllViews();

        Toast.makeText(finishButton.getContext(), message, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(finishButton.getContext(), SignInScreenActivity.class);
        finishButton.getContext().startActivity(intent);
        ((Activity) finishButton.getContext()).finish();
    }

    @Override
    public void onFailure(String message) {
        progressBar.setVisibility(View.INVISIBLE);

        Toast.makeText(finishButton.getContext(), message, Toast.LENGTH_LONG).show();
        enableAllViews();
    }

    private static void disableAllViews() {
        ViewUtility.disableViews(finishButton, newPasswordEditText, confirmNewPasswordEditText);
    }

    private static void enableAllViews() {
        ViewUtility.enableViews(finishButton, newPasswordEditText, confirmNewPasswordEditText);
    }
}
