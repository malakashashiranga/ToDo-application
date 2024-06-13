package common_classes.check_edit_text_empty_utility;

import android.widget.EditText;

public class CheckEditTextEmptyUtility {

    public static boolean isEditTextEmpty(EditText editText) {
        String text = editText.getText().toString().trim();
        return text.isEmpty();
    }
}
