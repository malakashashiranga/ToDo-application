package common_classes.confirmation_dialogue;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.todomo.R;

import common_classes.button_clicking_animation.ButtonClickingAnimation;

public class ConfirmationDialogueBox extends Dialog implements View.OnClickListener {

    private final ConfirmationDialogueBoxListener listener;
    private final String dialogMessage;
    private final String positiveButtonLabel;
    private final String negativeButtonLabel;

    public ConfirmationDialogueBox(Context context, String dialogMessage, String positiveButtonLabel, String negativeButtonLabel, ConfirmationDialogueBoxListener listener) {
        super(context);
        this.listener = listener;
        this.dialogMessage = dialogMessage;
        this.positiveButtonLabel = positiveButtonLabel;
        this.negativeButtonLabel = negativeButtonLabel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.confirmation_dialogue_box);

        TextView messageTextView = findViewById(R.id.dialogMessage);
        Button yesButton = findViewById(R.id.yesButton);
        Button noButton = findViewById(R.id.noButton);

        messageTextView.setText(dialogMessage);
        yesButton.setText(positiveButtonLabel);
        noButton.setText(negativeButtonLabel);

        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        ButtonClickingAnimation.animateButtonClick(v);
        int id = v.getId();
        if (id == R.id.yesButton) {
            listener.onDeleteConfirmed();
        } else if (id == R.id.noButton) {
            listener.onDeleteCanceled();
        }
        dismiss();
    }

    public interface ConfirmationDialogueBoxListener {
        void onDeleteConfirmed();
        void onDeleteCanceled();
    }
}
