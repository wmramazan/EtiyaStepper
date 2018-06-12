package com.etiya.stepper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * EtiyaStepperLayout
 *
 * This layout is used to separate one task to steps.
 *
 * @author wmramazan
 */

public class EtiyaStepperLayout extends LinearLayout {

    protected Context context;

    protected RelativeLayout rlHeader;
    protected TextView tvTitle;
    protected TextView tvDescription;
    protected View vProgress;
    protected ViewGroup.LayoutParams lpProgress;

    protected String strTitle;
    protected String strDescription;
    protected int current_step, number_of_steps, layout_width;

    protected List<EtiyaStepView> stepViews;

    public EtiyaStepperLayout(Context context) {
        super(context);
        init(context);
    }

    public EtiyaStepperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EtiyaStepperLayout,
                0,0
        );

        try {
            strTitle = typedArray.getString(R.styleable.EtiyaStepperLayout_stepper_title);
            strDescription = typedArray.getString(R.styleable.EtiyaStepperLayout_stepper_description);
        } finally {
            typedArray.recycle();
        }

        init(context);
    }

    /**
     * Initializes EtiyaStepperLayout.
     * @param context Context
     */
    protected void init(Context context) {
        this.context = context;
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.stepper_layout_header, this, true);
    }

    /**
     * Measures width of layout for progress.
     * @param widthMeasureSpec integer value
     * @param heightMeasureSpec integer value
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        layout_width = MeasureSpec.getSize(widthMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Prepares header of EtiyaStepperLayout and adds all of the steps in EtiyaStepperLayout to array. Then shows the first step.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        rlHeader = (RelativeLayout) getChildAt(0);
        layout_width = getWidth();

        tvTitle = (TextView) rlHeader.getChildAt(0);
        tvTitle.setText(strTitle);

        tvDescription = (TextView) rlHeader.getChildAt(1);
        tvDescription.setText(strDescription);

        vProgress = rlHeader.getChildAt(3);
        lpProgress = vProgress.getLayoutParams();

        stepViews = new ArrayList<>();

        int i = 1;
        EtiyaStepView stepView;
        while (i < getChildCount()) {
            stepView = (EtiyaStepView) getChildAt(i);
            stepView.setTag(i++);
            stepViews.add(stepView);
        }

        number_of_steps = getChildCount() - 1;
        showStep(1);
    }

    /**
     * Sets step and progress.
     * @param step integer value of step
     */
    public void setStep(int step) {
        current_step = step;

        lpProgress.width = (layout_width * (current_step - 1)) / number_of_steps;
        vProgress.setLayoutParams(lpProgress);
    }

    /**
     * Goes to next step.
     */
    public void nextStep() {
        if (current_step != number_of_steps) {
            getCurrentStep().hideProgress();
            getCurrentStep().collapseView();
            showStep(++current_step);
        }
    }

    /**
     * Shows the specified step.
     * @param step
     */
    public void showStep(int step) {
        setStep(step);
        getStep(step).expandView();
        getChildAt(step++).setVisibility(VISIBLE);
        while (step <= number_of_steps) {
            getChildAt(step).setVisibility(GONE);
            step++;
        }
    }

    /**
     * Collapses the last step and sets the progress as completed.
     */
    public void finish() {
        if (current_step == number_of_steps) {
            getCurrentStep().hideProgress();
            getCurrentStep().collapseView();
            setStep(current_step + 1);
        }
    }

    /**
     * Returns all of step views.
     * @return List<EtiyaStepView> stepViews
     */
    public List<EtiyaStepView> getSteps() {
        return stepViews;
    }

    /**
     * Returns the specified step.
     * @param step integer value of step
     * @return EtiyaStepView step
     */
    public EtiyaStepView getStep(int step) {
        return stepViews.get(step - 1);
    }

    /**
     * Returns the current step.
     * @return EtiyaStepView step
     */
    public EtiyaStepView getCurrentStep() {
        return getStep(current_step);
    }

    /**
     * Returns index of the current step.
     * @return int index
     */
    public int getCurrentStepIndex() {
        return current_step;
    }

    /**
     * Shows progress in the primary button of current step.
     */
    public void showProgress() {
        getCurrentStep().showProgress();
    }

    /**
     * Hides progress in the primary button of current step.
     */
    public void hideProgress() {
        getCurrentStep().hideProgress();
    }

    /**
     * Sets step name of current step.
     * @param name String
     */
    public void setStepName(String name) {
        getCurrentStep().setStepName(name);
    }

    /**
     * Sets summary text of current step.
     * @param summaryText String
     */
    public void setSummaryText(String summaryText) {
        getCurrentStep().setStepSummaryText(summaryText);
    }
}
