package com.etiya.stepper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * EtiyeProgressButton
 *
 * This view is used to show progressBar in button.
 *
 * @author wmramazan
 */

public class EtiyaProgressButton extends LinearLayout {

    protected Context context;

    protected LinearLayout llContent;
    protected TextView tvButtonText;
    protected ProgressBar pbProgress;

    protected String strButtonText;

    public EtiyaProgressButton(Context context) {
        super(context);
        init(context);
    }

    public EtiyaProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EtiyaProgressButton,
                0, 0
        );

        try {
            strButtonText = typedArray.getString(R.styleable.EtiyaProgressButton_button_text);
        } finally {
            typedArray.recycle();
        }

        init(context);
    }

    /**
     * Initializes EtiyaProgressButton.
     * @param context Context
     */
    protected void init(Context context) {
        this.context = context;
        setOrientation(VERTICAL);

        LayoutInflater.from(context).inflate(R.layout.progress_button_layout, this, true);

        llContent = (LinearLayout) getChildAt(0);

        tvButtonText = (TextView) llContent.getChildAt(0);

        if (null != strButtonText)
            tvButtonText.setText(strButtonText);

        pbProgress = (ProgressBar) llContent.getChildAt(1);
        pbProgress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.etiya_progress_color), PorterDuff.Mode.SRC_IN);
    }

    /**
     * Sets button text.
     * @param text String
     */
    public void setButtonText(String text) {
        strButtonText = text;
        tvButtonText.setText(strButtonText);
    }

    /**
     * Shows progressBar and hides button text
     */
    public void showProgress() {
        tvButtonText.setVisibility(GONE);
        pbProgress.setVisibility(VISIBLE);
    }

    /**
     * Shows button text. and hides progressBar
     */
    public void hideProgress() {
        tvButtonText.setVisibility(VISIBLE);
        pbProgress.setVisibility(GONE);
    }

    /**
     * Sets click listener to button
     * @param l OnClickListener
     */
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        llContent.setOnClickListener(l);
    }

    /**
     * Enables or disables the button
     * @param enabled boolean value
     */
    @Override
    public void setEnabled(boolean enabled) {
        if (enabled) {
            llContent.setBackgroundDrawable(getResources().getDrawable(R.drawable.ripple));
            llContent.setClickable(true);
        } else {
            llContent.setBackgroundColor(getResources().getColor(R.color.etiya_ripple_button_disable_color));
            llContent.setClickable(false);
        }
    }
}
