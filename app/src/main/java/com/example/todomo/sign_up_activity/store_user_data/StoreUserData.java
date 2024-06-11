package com.example.todomo.sign_up_activity.store_user_data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StoreUserData {

    public interface FirebaseCheckCallback {
        void onSuccess(String message);
        void onFailure(String message);
    }

    public static class FirebaseManager {

        private final FirebaseAuth mAuth;
        private final FirebaseFirestore mFirestore;
        private final FirebaseCheckCallback mCallback;

        public FirebaseManager(FirebaseCheckCallback callback) {
            mAuth = FirebaseAuth.getInstance();
            mFirestore = FirebaseFirestore.getInstance();
            mCallback = callback;
        }

        public void registerUser(String firstName, String lastName, String email, String password) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Store user data in Firestore
                                storeUserDataInFirestore(firstName, lastName, email, user.getUid());
                            }
                        } else {
                            // User registration failed
                            String errorMessage = Objects.requireNonNull(task.getException()).getMessage();
                            mCallback.onFailure("Registration failed: " + errorMessage);
                        }
                    });
        }

        private void storeUserDataInFirestore(String firstName, String lastName, String email, String userId) {

            String lowerCaseFirstName = firstName.toLowerCase();
            String lowerCaseLastName = lastName.toLowerCase();

            // Create a user object with additional data
            Map<String, Object> userData = new HashMap<>();
            userData.put("first_name", lowerCaseFirstName);
            userData.put("last_name", lowerCaseLastName);
            userData.put("email", email);

            // Store user data in Firestore
            mFirestore.collection("users")
                    .document(userId)
                    .set(userData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // User registration and data storage successful
                            mCallback.onSuccess("Registration successful");
                        } else {
                            // Error storing user data
                            String errorMessage = Objects.requireNonNull(task.getException()).getMessage();
                            mCallback.onFailure("Registration failed: " + errorMessage);
                        }
                    });
        }
    }
}
