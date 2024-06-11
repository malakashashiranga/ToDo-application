package com.example.todomo.sign_up_activity.check_details_validity;

import com.google.firebase.firestore.FirebaseFirestore;

public class CheckDetailsValidity {
    public interface FirebaseCheckCallback {
        void onSuccess(String message);
        void onFailure(String message);
    }

    public static void checkEmailExists(String email, FirebaseCheckCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .whereEqualTo("email", email.toLowerCase())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        callback.onFailure("Email already registered in the system.");
                    } else {
                        if (task.isSuccessful()) {
                            callback.onSuccess("Email does not exist in the system.");
                        } else {
                            callback.onFailure(task.getException() != null ? task.getException().getMessage() : "Failed to check email existence.");
                        }
                    }
                });
    }
}
