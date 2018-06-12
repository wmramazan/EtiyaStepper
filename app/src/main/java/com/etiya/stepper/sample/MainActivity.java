package com.etiya.stepper.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.etiya.stepper.EtiyaStepperLayout;

public class MainActivity extends AppCompatActivity {

    EtiyaStepperLayout etiyaStepperLayout;

    public static final int STEP_1 = 1;
    public static final int STEP_2 = 2;
    public static final int STEP_3 = 3;
    public static final int STEP_4 = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etiyaStepperLayout = findViewById(R.id.etiya_stepper);

        etiyaStepperLayout.getStep(STEP_1).setStepButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etiyaStepperLayout.setEnabled(false);
                etiyaStepperLayout.getCurrentStep().showProgress();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        etiyaStepperLayout.getCurrentStep().setStepSummaryText("Result 1");
                        etiyaStepperLayout.nextStep();
                        etiyaStepperLayout.setEnabled(true);
                    }
                }, 1000);
            }
        });

        etiyaStepperLayout.getStep(STEP_2).setStepButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etiyaStepperLayout.setEnabled(false);
                etiyaStepperLayout.getCurrentStep().showProgress();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        etiyaStepperLayout.getCurrentStep().setStepSummaryText("Result 2");
                        etiyaStepperLayout.nextStep();
                        etiyaStepperLayout.setEnabled(true);
                    }
                }, 1000);
            }
        });

        etiyaStepperLayout.getStep(STEP_2).setStepSecondaryButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etiyaStepperLayout.showStep(STEP_1);
            }
        });

        etiyaStepperLayout.getStep(STEP_3).setStepButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etiyaStepperLayout.setEnabled(false);
                etiyaStepperLayout.getCurrentStep().showProgress();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        etiyaStepperLayout.nextStep();
                        etiyaStepperLayout.setEnabled(true);
                    }
                }, 1000);
            }
        });

        etiyaStepperLayout.getStep(STEP_4).setStepButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etiyaStepperLayout.setEnabled(false);
                etiyaStepperLayout.getCurrentStep().showProgress();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        etiyaStepperLayout.getCurrentStep().setStepSummaryText("Result 4");
                        etiyaStepperLayout.finish();
                        etiyaStepperLayout.setEnabled(true);
                    }
                }, 1000);
            }
        });
    }
}
