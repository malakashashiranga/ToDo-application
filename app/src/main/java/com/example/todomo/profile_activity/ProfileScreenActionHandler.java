package com.example.todomo.profile_activity;

import static common_classes.check_edit_text_empty_utility.CheckEditTextEmptyUtility.isEditTextEmpty;
import static common_classes.password_fields_checks.PasswordFieldsChecks.areFieldsEqual;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.todomo.R;
import com.example.todomo.developer_activity.DeveloperScreenActivity;
import com.example.todomo.home_activity.HomeScreenActivity;
import com.example.todomo.profile_activity.update_profile.UpdateProfile;
import com.example.todomo.sign_in_activity.SignInScreenActivity;

import common_classes.button_clicking_animation.ButtonClickingAnimation;
import common_classes.confirmation_dialogue.ConfirmationDialogueBox;
import common_classes.network_checking.NetworkAvailability;
import common_classes.view_utility.ViewUtility;

public class ProfileScreenActionHandler {

    private ImageButton backButton;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText currentPassword;
    private EditText newPassword;
    private EditText confirmNewPassword;
    private Button saveChangeNamesButton;
    private Button saveChangePasswordButton;
    private Button logOutButton;
    private TextView devInfo;
    private TextView userNameProfile;
    private ProgressBar progressBar;

    public void initializeViews(@NonNull ImageButton imageButton1,
                                @NonNull EditText eText1, @NonNull EditText eText2, @NonNull EditText eText3, @NonNull EditText eText4, @NonNull EditText eText5, @NonNull EditText eText6,
                                @NonNull Button button1, @NonNull Button button2, @NonNull Button button4,
                                @NonNull TextView textView1, @NonNull TextView textView2,
                                @NonNull ProgressBar progressBar1) {

        backButton = imageButton1;
        firstName = eText1;
        lastName = eText2;
        email = eText3;
        currentPassword = eText4;
        newPassword = eText5;
        confirmNewPassword = eText6;
        saveChangeNamesButton = button1;
        saveChangePasswordButton = button2;
        logOutButton = button4;
        devInfo = textView1;
        userNameProfile =textView2;
        progressBar = progressBar1;
    }

    @SuppressLint("SetTextI18n")
    public void startUpScreen(@NonNull Activity activity) {
        progressBar.setVisibility(View.INVISIBLE);
        loadUserData(activity);
        disableAllViews();
        resetScreen();

        SharedPreferences sharedPreferences = activity.getSharedPreferences("isLoggedInApp", Context.MODE_PRIVATE);
        String firstName = sharedPreferences.getString("first_name", "");
        String lastName = sharedPreferences.getString("last_name", "");

        userNameProfile.setText(capitalizeFirstLetter(firstName) + " " + capitalizeFirstLetter(lastName));
    }

    public void handleBackButtonClick(@NonNull Activity activity) {
        Intent intent = new Intent(activity, HomeScreenActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        activity.finish();
    }

    public void saveChangeNamesButtonClick(@NonNull Activity activity) {
        ButtonClickingAnimation.animateButtonClick(saveChangeNamesButton);

        if (!NetworkAvailability.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "No network connection available", Toast.LENGTH_LONG).show();
            return;
        }

        if (saveChangeNamesButton.getText().toString().equals(activity.getString(R.string.change_names))) {
            saveChangeNamesButton.setText(activity.getString(R.string.save_changes));
            saveChangePasswordButton.setText(activity.getString(R.string.change_password));
            startUpScreen(activity);
            firstName.setEnabled(true);
            lastName.setEnabled(true);
        } else if (saveChangeNamesButton.getText().toString().equals(activity.getString(R.string.save_changes))) {
            progressBar.setVisibility(View.VISIBLE);

            if (isEditTextEmpty(firstName)) {
                Toast.makeText(activity, "First name cannot be empty", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                startUpScreen(activity);
                return;
            } else if (isEditTextEmpty(lastName)) {
                Toast.makeText(activity, "Last name cannot be empty", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                startUpScreen(activity);
                return;
            }

            SharedPreferences sharedPreferences = activity.getSharedPreferences("isLoggedInApp", Context.MODE_PRIVATE);
            String email = sharedPreferences.getString("userEmail", "");

            String firstNameEditText = firstName.getText().toString().trim();
            String lastNameEditText = lastName.getText().toString().trim();

            UpdateProfile.updateNames(activity, email, firstNameEditText, lastNameEditText, new UpdateProfile.FirebaseCheckCallback() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    startUpScreen(activity);
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    startUpScreen(activity);
                }
            });
            progressBar.setVisibility(View.INVISIBLE);
            saveChangeNamesButton.setText(activity.getString(R.string.change_names));
        }
    }


    public void saveChangePasswordButtonClick(@NonNull Activity activity) {
        ButtonClickingAnimation.animateButtonClick(saveChangePasswordButton);

        if (!NetworkAvailability.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "No network connection available", Toast.LENGTH_LONG).show();
            return;
        }

        if (saveChangePasswordButton.getText().toString().equals(activity.getString(R.string.change_password))) {
            startUpScreen(activity);

            saveChangeNamesButton.setText(activity.getString(R.string.change_names));
            saveChangePasswordButton.setText(activity.getString(R.string.save_password));

            currentPassword.setEnabled(true);
            newPassword.setEnabled(true);
            confirmNewPassword.setEnabled(true);

        } else if (saveChangePasswordButton.getText().toString().equals(activity.getString(R.string.save_password))) {
            progressBar.setVisibility(View.VISIBLE);

            if (isEditTextEmpty(currentPassword)) {
                Toast.makeText(activity, "Current password cannot be empty", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                saveChangePasswordButton.setText(activity.getString(R.string.change_password));
                startUpScreen(activity);
                return;
            } else if (isEditTextEmpty(newPassword)) {
                Toast.makeText(activity, "New password cannot be empty", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                saveChangePasswordButton.setText(activity.getString(R.string.change_password));
                startUpScreen(activity);
                return;
            } else if (isEditTextEmpty(confirmNewPassword)) {
                Toast.makeText(activity, "Retype password cannot be empty", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                saveChangePasswordButton.setText(activity.getString(R.string.change_password));
                startUpScreen(activity);
                return;
            } else if (!areFieldsEqual(newPassword, confirmNewPassword)) {
                Toast.makeText(activity, "New password and confirm password must be the same", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                saveChangePasswordButton.setText(activity.getString(R.string.change_password));
                startUpScreen(activity);
                return;
            }

            SharedPreferences sharedPreferences = activity.getSharedPreferences("isLoggedInApp", Context.MODE_PRIVATE);
            String email = sharedPreferences.getString("userEmail", "");

            String currentPasswordEditText = currentPassword.getText().toString().trim();
            String newPasswordEditText = newPassword.getText().toString().trim();
            String confirmNewPasswordEditText = confirmNewPassword.getText().toString().trim();

            UpdateProfile.updatePassword(email, currentPasswordEditText, newPasswordEditText, confirmNewPasswordEditText, new UpdateProfile.FirebaseCheckCallback() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    saveChangePasswordButton.setText(activity.getString(R.string.change_password));
                    startUpScreen(activity);
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    saveChangePasswordButton.setText(activity.getString(R.string.change_password));
                    startUpScreen(activity);
                }
            });

            progressBar.setVisibility(View.INVISIBLE);
            saveChangePasswordButton.setText(activity.getString(R.string.change_password));
        }
    }

    public void logOutButtonClick(@NonNull Activity activity) {

        new ConfirmationDialogueBox(activity, "Would you like to log out?", "Yes", "No", new ConfirmationDialogueBox.ConfirmationDialogueBoxListener() {
            @Override
            public void onDeleteConfirmed() {
                SharedPreferences sharedPreferences = activity.getSharedPreferences("isLoggedInApp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isSuccessfully", false);
                editor.apply();

                Intent intent = new Intent(activity, SignInScreenActivity.class);
                Toast.makeText(activity, "Log out successfully!", Toast.LENGTH_LONG).show();
                activity.startActivity(intent);
                activity.finish();
            }

            @Override
            public void onDeleteCanceled() {

            }
        }).show();
    }

    public void devInfoClick (@NonNull Activity activity) {
        Intent intent = new Intent(activity, DeveloperScreenActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void disableAllViews() {
        ViewUtility.disableViews(backButton, firstName, lastName, email, currentPassword, newPassword, confirmNewPassword, saveChangeNamesButton, saveChangePasswordButton, logOutButton, devInfo, progressBar);
    }

    private void resetScreen() {
        currentPassword.setText("");
        newPassword.setText("");
        confirmNewPassword.setText("");
        backButton.setEnabled(true);
        saveChangeNamesButton.setEnabled(true);
        saveChangePasswordButton.setEnabled(true);
        logOutButton.setEnabled(true);
        devInfo.setEnabled(true);
    }

    private void loadUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("isLoggedInApp", Context.MODE_PRIVATE);
        String savedFirstName = sharedPreferences.getString("first_name", "");
        String savedLastName = sharedPreferences.getString("last_name", "");
        String savedEmail = sharedPreferences.getString("userEmail", "");

        firstName.setText(capitalizeFirstLetter(savedFirstName));
        lastName.setText(capitalizeFirstLetter(savedLastName));
        email.setText(savedEmail);
    }

    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
