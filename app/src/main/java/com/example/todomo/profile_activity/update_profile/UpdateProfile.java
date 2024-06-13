package com.example.todomo.profile_activity.update_profile;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.Objects;

public class UpdateProfile {

    public interface FirebaseCheckCallback {
        void onSuccess(String message);
        void onFailure(String message);
    }

    public static void updateNames(Context context, String email, String newFirstName, String newLastName, @NonNull FirebaseCheckCallback callback) {
        String lowerCaseNewFirstName = newFirstName.toLowerCase();
        String lowerCaseNewLastName = newLastName.toLowerCase();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            WriteBatch batch = db.batch();
                            for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                batch.update(document.getReference(), "first_name", lowerCaseNewFirstName);
                                batch.update(document.getReference(), "last_name", lowerCaseNewLastName);
                            }
                            batch.commit().addOnSuccessListener(aVoid -> {
                                SharedPreferences sharedPreferences = context.getSharedPreferences("isLoggedInApp", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("first_name", lowerCaseNewFirstName);
                                editor.putString("last_name", lowerCaseNewLastName);
                                editor.apply();
                                callback.onSuccess("Names updated successfully");
                            }).addOnFailureListener(e -> callback.onFailure("Failed to update names: " + e.getMessage()));
                        } else {
                            callback.onFailure("No user found with the provided email");
                        }
                    } else {
                        callback.onFailure("Failed to query user: " + Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }

    public static void updatePassword(String email, String currentPassword, String newPassword, String confirmPassword, FirebaseCheckCallback callback) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            callback.onFailure("User not logged in");
            return;
        }

        currentUser.reauthenticate(com.google.firebase.auth.EmailAuthProvider.getCredential(email, currentPassword))
                .addOnSuccessListener(aVoid -> {
                    if (!newPassword.equals(confirmPassword)) {
                        callback.onFailure("New password and confirm password do not match");
                        return;
                    }

                    currentUser.updatePassword(newPassword)
                            .addOnSuccessListener(aVoid1 -> callback.onSuccess("Password updated successfully"))
                            .addOnFailureListener(e -> callback.onFailure("Failed to update password: " + e.getMessage()));
                })
                .addOnFailureListener(e -> callback.onFailure("Failed to re-authenticate user: " + e.getMessage()));
    }
}
