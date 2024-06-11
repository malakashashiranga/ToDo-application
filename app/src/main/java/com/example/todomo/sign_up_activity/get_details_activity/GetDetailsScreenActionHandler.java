package com.example.todomo.sign_up_activity.get_details_activity;

import static common_classes.check_edit_text_empty_utility.CheckEditTextEmptyUtility.isEditTextEmpty;
import static common_classes.email_validator_utility.EmailValidUtility.isValidEmail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.todomo.R;
import com.example.todomo.sign_in_activity.SignInScreenActivity;
import com.example.todomo.sign_up_activity.check_details_validity.CheckDetailsValidity;
import com.example.todomo.sign_up_activity.set_password_activity.SetPasswordScreenActivity;

import common_classes.button_clicking_animation.ButtonClickingAnimation;
import common_classes.clear_shared_preferences.ClearAllPreferencesDataUtility;
import common_classes.view_utility.ViewUtility;

public class GetDetailsScreenActionHandler {

    @SuppressLint("StaticFieldLeak")
    private static Button nextButton;
    @SuppressLint("StaticFieldLeak")
    private static TextView signInLinkTextView;
    @SuppressLint("StaticFieldLeak")
    private static EditText enterFirstNameEditText;
    @SuppressLint("StaticFieldLeak")
    private static EditText enterLastNameEditText;
    @SuppressLint("StaticFieldLeak")
    private static EditText enterEmailEditText;
    @SuppressLint("StaticFieldLeak")

    public static void initializeViews(@NonNull Button button, @NonNull TextView signInLink, @NonNull EditText FirstName, @NonNull EditText LastName, @NonNull EditText Email) {
        nextButton = button;
        signInLinkTextView = signInLink;
        enterFirstNameEditText = FirstName;
        enterLastNameEditText = LastName;
        enterEmailEditText = Email;
    }

    public static void handleSignInLinkClick(@NonNull Activity activity, @NonNull ProgressBar progressBar) {
        signInLinkTextView.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            disableAllViews();

            new Handler().postDelayed(() -> {
                enableAllViews();
                progressBar.setVisibility(View.INVISIBLE);

                ClearAllPreferencesDataUtility.clearSharedPreferences(activity, "AppProcesses");

                Intent intent = new Intent(activity, SignInScreenActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }, 1500);
        });
    }

    public static void handleNextButtonClick(@NonNull Activity activity, @NonNull ProgressBar progressBar) {
        nextButton.setOnClickListener(v -> {
            ButtonClickingAnimation.animateButtonClick(nextButton);
            progressBar.setVisibility(View.VISIBLE);
            disableAllViews();

            new Handler().postDelayed(() -> {
                if (validateInputFields(activity, enterFirstNameEditText, enterLastNameEditText, enterEmailEditText, progressBar)) {

                    String firstName = enterFirstNameEditText.getText().toString().trim();
                    String lastName = enterLastNameEditText.getText().toString().trim();
                    String email = enterEmailEditText.getText().toString().trim();

                    CheckDetailsValidity.checkEmailExists(email, new CheckDetailsValidity.FirebaseCheckCallback() {
                        @Override
                        public void onSuccess(String message) {
                            Intent intent = new Intent(activity, SetPasswordScreenActivity.class);

                            intent.putExtra("FirstName", firstName);
                            intent.putExtra("LastName", lastName);
                            intent.putExtra("Email", email);

                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            activity.finish();
                        }

                        @Override
                        public void onFailure(String message) {
                            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                            enableAllViews();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                } else {
                    enableAllViews();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 1000);
        });
    }

    public static void handleBackButtonClick(@NonNull Activity activity) {
        ClearAllPreferencesDataUtility.clearSharedPreferences(activity, "AppProcesses");

        Intent intent = new Intent(activity, SignInScreenActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        activity.finish();
    }

    private static boolean validateInputFields(Activity activity, EditText firstNameEditText, EditText lastNameEditText, EditText emailEditText, ProgressBar progressBar) {
        String email = emailEditText.getText().toString().trim();

        if (isEditTextEmpty(firstNameEditText)) {
            Toast.makeText(activity, "First name field cannot be empty", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            enableAllViews();
            return false;
        } else if (isEditTextEmpty(lastNameEditText)) {
            Toast.makeText(activity, "Last name field cannot be empty", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            enableAllViews();
            return false;
        } else if (isEditTextEmpty(emailEditText)) {
            Toast.makeText(activity, "Email input field cannot be empty", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            enableAllViews();
            return false;
        } else if (!isValidEmail(email)) {
            // Show toast message if email is invalid
            Toast.makeText(activity, "Please enter a valid email address", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            enableAllViews();
            return false;
        }
        return true;
    }

    private static void disableAllViews() {
        ViewUtility.disableViews(nextButton, signInLinkTextView, enterFirstNameEditText, enterLastNameEditText, enterEmailEditText);
    }

    private static void enableAllViews() {
        ViewUtility.enableViews(nextButton, signInLinkTextView, enterFirstNameEditText, enterLastNameEditText, enterEmailEditText);
    }
}
