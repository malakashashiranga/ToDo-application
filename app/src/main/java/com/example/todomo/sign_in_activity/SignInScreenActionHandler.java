package com.example.todomo.sign_in_activity;

import static common_classes.check_edit_text_empty_utility.CheckEditTextEmptyUtility.isEditTextEmpty;
import static common_classes.email_validator_utility.EmailValidUtility.isValidEmail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.todomo.R;
import com.example.todomo.home_activity.HomeScreenActivity;
import com.example.todomo.sign_in_activity.check_details.UserDetailsValidation;
import com.example.todomo.sign_up_activity.get_details_activity.GetDetailsScreenActivity;

import common_classes.button_clicking_animation.ButtonClickingAnimation;
import common_classes.network_checking.NetworkAvailability;
import common_classes.view_utility.ViewUtility;

public class SignInScreenActionHandler implements UserDetailsValidation.FirebaseCheckCallback {

    @SuppressLint("StaticFieldLeak")
    private static Button signButton;
    @SuppressLint("StaticFieldLeak")
    private static EditText userEmailEditText;
    @SuppressLint("StaticFieldLeak")
    private static EditText passwordEditText;
    @SuppressLint("StaticFieldLeak")
    private static TextView signUpLinkTextView;
    @SuppressLint("StaticFieldLeak")
    private static ProgressBar progressBar;

    public static void initializeViews(@NonNull Button button, @NonNull EditText userEmail, @NonNull EditText password, @NonNull TextView signUpLink, @NonNull ProgressBar progress) {
        signButton = button;
        userEmailEditText = userEmail;
        passwordEditText = password;
        signUpLinkTextView = signUpLink;
        progressBar = progress;
    }

    public static void handleSignButtonClick(@NonNull Activity activity) {
        signButton.setOnClickListener(v -> {
            ButtonClickingAnimation.animateButtonClick(signButton);
            progressBar.setVisibility(View.VISIBLE);
            disableAllViews();

            new Handler().postDelayed(() -> {
                enableAllViews();
                progressBar.setVisibility(View.INVISIBLE);

                String email = userEmailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!NetworkAvailability.isNetworkAvailable(activity)) {
                    Toast.makeText(activity, "No network connection available", Toast.LENGTH_LONG).show();
                    enableAllViews();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                if (isEditTextEmpty(userEmailEditText)) {
                    Toast.makeText(activity, "Email input field cannot be empty", Toast.LENGTH_LONG).show();
                    enableAllViews();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else if (!isValidEmail(email)) {
                    Toast.makeText(activity, "Please enter a valid email address", Toast.LENGTH_LONG).show();
                    enableAllViews();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else if (isEditTextEmpty(passwordEditText)) {
                    Toast.makeText(activity, "Password input field cannot be empty", Toast.LENGTH_LONG).show();
                    enableAllViews();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                UserDetailsValidation.FirebaseCheckCallback callback = new SignInScreenActionHandler();
                UserDetailsValidation userDetailsValidation = new UserDetailsValidation(callback, activity);

                userDetailsValidation.signIn(email, password);
            }, 1500);
        });
    }

    public static void handleSignUpLinkClick(@NonNull Activity activity) {
        signUpLinkTextView.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            disableAllViews();

            new Handler().postDelayed(() -> {
                enableAllViews();
                progressBar.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(activity, GetDetailsScreenActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }, 1500);
        });
    }

    @Override
    public void onSuccess(String message) {
        String email = userEmailEditText.getText().toString().trim();
        // Hide progress bar
        progressBar.setVisibility(View.INVISIBLE);
        enableAllViews();

        SharedPreferences sharedPreferences = signButton.getContext().getSharedPreferences("isLoggedInApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isSuccessfully", true);
        editor.putString("userEmail", email);
        editor.apply();

        // Show success message
        Toast.makeText(signButton.getContext(), message, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(signButton.getContext(), HomeScreenActivity.class);
        signButton.getContext().startActivity(intent);
        ((Activity) signButton.getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        ((Activity) signButton.getContext()).finish();
    }

    @Override
    public void onFailure(String message) {
        progressBar.setVisibility(View.INVISIBLE);

        Toast.makeText(signButton.getContext(), message, Toast.LENGTH_LONG).show();
        enableAllViews();

        userEmailEditText.setText("");
        passwordEditText.setText("");
    }

    private static void disableAllViews() {
        ViewUtility.disableViews(signButton, userEmailEditText, passwordEditText, signUpLinkTextView);
    }

    private static void enableAllViews() {
        ViewUtility.enableViews(signButton, userEmailEditText, passwordEditText, signUpLinkTextView);
    }
}
