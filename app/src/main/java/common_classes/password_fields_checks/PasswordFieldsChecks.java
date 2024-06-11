package common_classes.password_fields_checks;

import android.widget.EditText;

public class PasswordFieldsChecks {

    public static boolean isLengthGreaterThan(EditText editText, int length) {
        String text = editText.getText().toString().trim();
        return text.length() > length;
    }

    public static boolean areFieldsEqual(EditText editText1, EditText editText2) {
        String text1 = editText1.getText().toString().trim();
        String text2 = editText2.getText().toString().trim();
        return text1.equals(text2);
    }
}
