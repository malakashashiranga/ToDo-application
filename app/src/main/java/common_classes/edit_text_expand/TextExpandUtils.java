package common_classes.edit_text_expand;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TextExpandUtils {

    public static void setDynamicLayoutParams(Context context, int editTextId) {
        EditText editText = ((Activity) context).findViewById(editTextId);

        if (editText != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            editText.setLayoutParams(params);
            editText.setMaxLines(5);
            editText.setHorizontallyScrolling(false);
        }
    }
}
