package com.etiya.stepper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * EtiyaStepView
 *
 * This view is used to show content and summary of steps.
 *
 * @author wmramazan
 */

public class EtiyaStepView extends CardView {

    public enum State {
        EXPANDED, COLLAPSED, PROGRESSING
    }

    protected final int     DEFAULT_RADIUS = 1;
    protected final float   DEFAULT_CARD_ELEVATION = 0.8f;
    protected final boolean DEFAULT_SET_USE_COMPAT_PADDING = true;

    protected Context context;
    protected ViewGroup content;
    protected ViewGroup summary;
    protected LinearLayout.LayoutParams layoutParams;
    protected RelativeLayout.LayoutParams relativeLayoutParams;

    protected EtiyaProgressButton epbStepButton;
    protected Button btnStepSecondaryButton;
    protected TextView tvStepName;
    protected TextView tvStepSummaryTitle;
    protected TextView tvStepSummaryText;
    protected ImageView ivEditStep;

    protected boolean hideStepName;
    protected String strStepName, strStepButton, strStepSecondaryButton, strStepSummaryTitle, strStepSummaryText;
    protected State state;

    public EtiyaStepView(Context context) {
        super(context);
        init(context);
    }

    public EtiyaStepView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EtiyaStepView,
                0, 0
        );

        try {
            strStepName = typedArray.getString(R.styleable.EtiyaStepView_step_name);
            strStepButton = typedArray.getString(R.styleable.EtiyaStepView_step_button);
            strStepSecondaryButton = typedArray.getString(R.styleable.EtiyaStepView_step_secondary_button);
            strStepSummaryTitle = typedArray.getString(R.styleable.EtiyaStepView_summary_title);
            hideStepName = typedArray.getBoolean(R.styleable.EtiyaStepView_hide_step_name, false);
        } finally {
            typedArray.recycle();
        }

        init(context);
    }

    /**
     * Initializes EtiyaStepView
     * @param context Context
     */
    protected void init(Context context) {
        this.context = context;
        setRadius(DEFAULT_RADIUS);
        setCardElevation(DEFAULT_CARD_ELEVATION);
        setUseCompatPadding(DEFAULT_SET_USE_COMPAT_PADDING);

        state = State.EXPANDED;

        layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, context.getResources().getDimensionPixelSize(R.dimen.etiya_step_view_space), 0,0);

        setLayoutParams(layoutParams);
    }

    /**
     * Prepares content and summary of the step.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        content = (ViewGroup) getChildAt(0);
        summary = (ViewGroup) getChildAt(1);

        addViews();
    }

    /**
     * Adds views to the step and sets attributes to views.
     */
    @SuppressLint("InflateParams")
    protected void addViews() {
        addStepNameView();
        addStepButtonView();

        if (null != strStepSecondaryButton) {
            btnStepSecondaryButton = (Button) inflate(context, R.layout.step_view_secondary_button, null);

            layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    context.getResources().getDimensionPixelSize(R.dimen.etiya_step_button_height)
            );
            layoutParams.setMargins(0, context.getResources().getDimensionPixelSize(R.dimen.etiya_step_secondary_button_margin_top), 0, 0);

            btnStepSecondaryButton.setLayoutParams(layoutParams);
            btnStepSecondaryButton.setText(strStepSecondaryButton);

            content.addView(btnStepSecondaryButton);
        }

        if (null == summary) {
            summary = (RelativeLayout) inflate(context, R.layout.step_view_summary_layout, null);
            //summary.setLayoutParams(layoutParams);

            if (null != strStepName || null != strStepSummaryTitle) {
                tvStepSummaryTitle = (TextView) summary.getChildAt(0);
                tvStepSummaryTitle.setText(null == strStepSummaryTitle ? strStepName : strStepSummaryText);
            }

            tvStepSummaryText = (TextView) summary.getChildAt(1);

            ivEditStep = (ImageView) summary.getChildAt(2);

            summary.setBackgroundResource(R.drawable.left_line_bg);
            addView(summary);
        } else {
            ivEditStep = (ImageView) inflate(context, R.layout.step_view_edit_button, null);
            relativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //edit_layout_params.addRule(RelativeLayout.ALIGN_PARENT_END);
            relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            ivEditStep.setLayoutParams(relativeLayoutParams);
            summary.addView(ivEditStep);
        }

        summary.setBackgroundResource(R.drawable.left_line_bg);

        ivEditStep.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EtiyaStepperLayout) getParent()).showStep((int) getTag());
            }
        });
    }

    /**
     * Adds step name as TextView
     */
    public void addStepNameView() {
        if (null != strStepName) {
            tvStepName = new TextView(context);
            tvStepName.setTextAppearance(context, R.style.EtiyaStepViewHeaderTextStyle);
            tvStepName.setText(strStepName);
            if (hideStepName)
                tvStepName.setVisibility(GONE);
            content.addView(tvStepName, 0);
        }
    }

    /**
     * Adds step button as EtiyaProgressButton
     */
    public void addStepButtonView() {
        if (null != strStepButton) {
            epbStepButton = new EtiyaProgressButton(context);
            layoutParams.setMargins(0, context.getResources().getDimensionPixelSize(R.dimen.etiya_step_button_margin_top), 0, 0);
            epbStepButton.setLayoutParams(layoutParams);

            epbStepButton.setButtonText(strStepButton);

            content.addView(epbStepButton);
        }
    }

    /**
     * Shows progress of the primary button.
     */
    public void showProgress() {
        Log.d("ADN", "Show Progress");
        state = State.PROGRESSING;
        epbStepButton.showProgress();
        epbStepButton.setEnabled(false);
    }

    /**
     * Hides progress of the primary button.
     */
    public void hideProgress() {
        state = State.EXPANDED;
        epbStepButton.hideProgress();
        epbStepButton.setEnabled(true);
    }

    /**
     * Collapses the step and shows the summary.
     */
    public void collapseView() {
        //Show Summary
        content.setVisibility(GONE);
        summary.setVisibility(VISIBLE);

        state = State.COLLAPSED;
    }

    /**
     * Expands the step and shows the content.
     */
    public void expandView() {
        //Hide Summary
        content.setVisibility(VISIBLE);
        if (null != strStepButton)
            epbStepButton.setVisibility(VISIBLE);
        summary.setVisibility(GONE);

        state = State.EXPANDED;
    }

    /**
     * Gets state of the step.
     * @return State state
     */
    public State getState() {
        return state;
    }

    /**
     * Gets name of the step.
     * @return State state
     */
    public String getStepName() {
        return strStepName;
    }

    /**
     * Sets name of the step
     * @param strStepName String
     */
    public void setStepName(String strStepName) {
        this.strStepName = strStepName;
    }

    /**
     * Gets summary text of the step.
     * @return String strStepSummaryText
     */
    public String getStepSummaryText() {
        return strStepSummaryText;
    }

    /**
     * Sets summary text of the step.
     * @param strSummaryText String
     */
    public void setStepSummaryText(String strSummaryText) {
        this.strStepSummaryText = strSummaryText;
        tvStepSummaryText.setText(strStepSummaryText);
    }

    /**
     * Sets OnClickListener to the primary button of step.
     * @param l OnClickListener
     */
    public void setStepButtonOnClickListener(OnClickListener l) {
        epbStepButton.setOnClickListener(l);
    }

    /**
     * Enables or disables the primary button of step.
     * @param enabled boolean value
     */
    public void setStepButtonEnabled(boolean enabled) {
        epbStepButton.setEnabled(enabled);
    }

    /**
     * Sets visibility to the primary button of step.
     * @param visibility integer value
     */
    public void setStepButtonVisibility(int visibility) {
        epbStepButton.setVisibility(visibility);
    }

    /**
     * Sets text of the primary button of step.
     * @param text String
     */
    public void setStepButtonText(String text) {
        strStepButton = text;
        epbStepButton.setButtonText(strStepButton);
    }

    /**
     * Sets OnClickListener to the secondary button of step.
     * @param l OnClickListener
     */
    public void setStepSecondaryButtonOnClickListener(OnClickListener l) {
        btnStepSecondaryButton.setOnClickListener(l);
    }

    /**
     * Enables or disables the secondary button of step.
     * @param enabled boolean value
     */
    public void setStepSecondaryButtonEnabled(boolean enabled) {
        btnStepSecondaryButton.setEnabled(enabled);
    }

    /**
     * Sets text of the secondary button of step.
     * @param text String
     */
    public void setStepSecondaryButtonText(String text) {
        strStepSecondaryButton = text;
        btnStepSecondaryButton.setText(text);
    }

    /**
     * Sets visibility to the secondary button of step.
     * @param visibility integer value
     */
    public void setStepSecondaryButtonVisibility(int visibility) {
        btnStepSecondaryButton.setVisibility(visibility);
    }

    /**
     * Sets OnClickListener to edit button of step.
     * @param l OnClickListener
     */
    public void setStepEditButtonOnClickListener(OnClickListener l) {
        ivEditStep.setOnClickListener(l);
    }
}
