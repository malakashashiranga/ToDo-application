package common_classes.responsive_seek_bar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

import androidx.core.content.ContextCompat;

import com.example.todomo.R;

public class SeekBarChanges extends androidx.appcompat.widget.AppCompatSeekBar {
    private final int[] colors = {R.color.seek_bar_green, R.color.seek_bar_blue, R.color.seek_bar_red};

    public SeekBarChanges(Context context) {
        super(context);
        init();
    }

    public SeekBarChanges(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SeekBarChanges(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public static void setCustomColorSeekBar(SeekBar prioritySeekBar) {
        SeekBarChanges seekBarChanges = new SeekBarChanges(prioritySeekBar.getContext());
        prioritySeekBar.setOnSeekBarChangeListener(seekBarChanges.new SeekBarChangeListener());
    }

    private void init() {}

    private int getColorForProgress(int progress) {
        if (progress >= 0 && progress < colors.length) {
            return ContextCompat.getColor(getContext(), colors[progress]);
        } else {
            return ContextCompat.getColor(getContext(), colors[0]); // Default to the first color
        }
    }

    private class SeekBarChangeListener implements OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int progressColor = getColorForProgress(progress);
            seekBar.getProgressDrawable().setColorFilter(progressColor, android.graphics.PorterDuff.Mode.SRC_IN);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    }
}
