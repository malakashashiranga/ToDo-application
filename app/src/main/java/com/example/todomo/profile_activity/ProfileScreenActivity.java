package com.example.todomo.profile_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todomo.R;

public class ProfileScreenActivity extends AppCompatActivity {

    private ProfileScreenActionHandler profileScreenActionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        ProgressBar progressBar = findViewById(R.id.EventSpinnerBar);
        progressBar.setVisibility(View.INVISIBLE);

        profileScreenActionHandler = new ProfileScreenActionHandler();

        ImageButton backButton = findViewById(R.id.BackArrow);
        Button saveChangeNamesButton = findViewById(R.id.SaveChangeNamesButton);
        Button saveChangePasswordButton = findViewById(R.id.SaveChangePasswordButton);
        TextView devInfo = findViewById(R.id.DevInfo);
        Button logOutButton = findViewById(R.id.LogOutButton);

        backButton.setOnClickListener(v -> profileScreenActionHandler.handleBackButtonClick(this));
        saveChangeNamesButton.setOnClickListener(v -> profileScreenActionHandler.saveChangeNamesButtonClick(this));
        saveChangePasswordButton.setOnClickListener(v -> profileScreenActionHandler.saveChangePasswordButtonClick(this));
        devInfo.setOnClickListener(v -> profileScreenActionHandler.devInfoClick(this));
        logOutButton.setOnClickListener(v -> profileScreenActionHandler.logOutButtonClick(this));

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                profileScreenActionHandler.handleBackButtonClick(ProfileScreenActivity.this);
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
