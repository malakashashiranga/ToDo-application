package com.example.todomo.sign_in_activity.check_details;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDetailsValidation {

    public interface FirebaseCheckCallback {
        void onSuccess(String message);
        void onFailure(String message);
    }

    private final FirebaseAuth mAuth;
    private final FirebaseFirestore mFirestore;
    private final FirebaseCheckCallback mCallback;
    private final Context mContext;

    public UserDetailsValidation(FirebaseCheckCallback callback, Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mCallback = callback;
        mContext = context;
    }

    public void signIn(String email, String password) {
        mFirestore.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(authTask -> {
                                    if (authTask.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            retrieveUserDetails(user.getUid());
                                        } else {
                                            mCallback.onFailure("Failed to get user details");
                                        }
                                    } else {
                                        mCallback.onFailure("Password is incorrect");
                                    }
                                });
                    } else {
                        mCallback.onFailure("Email not registered in system");
                    }
                });
    }

    private void retrieveUserDetails(String userId) {
        mFirestore.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot document = task.getResult();
                        String firstName = document.getString("first_name");
                        String lastName = document.getString("last_name");

                        saveUserDetailsToSharedPreferences(firstName, lastName);

                        mCallback.onSuccess("Sign in successfully!");
                    } else {
                        mCallback.onFailure("Failed to retrieve user details");
                    }
                });
    }

    private void saveUserDetailsToSharedPreferences(String firstName, String lastName) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("isLoggedInApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("first_name", firstName);
        editor.putString("last_name", lastName);
        editor.apply();
    }
}
