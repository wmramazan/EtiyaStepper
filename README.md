# EtiyaStepper [![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com) [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [ ![Download](https://api.bintray.com/packages/wmramazan/maven/EtiyaStepper/images/download.svg) ](https://bintray.com/wmramazan/maven/EtiyaStepper/_latestVersion)

An Android library which provides to separate your task to steps.

![preview](https://github.com/wmramazan/EtiyaStepper/blob/master/device-2018-06-18-083623.png)

## Features
- Progress Bar
- Progress Button
- Collapse and Expand Step
- Default and Custom Summary

## Integration
Just add the dependency to your `build.gradle`:

```groovy
dependencies {
    implementation 'com.etiya.stepper:EtiyaStepper:0.1.0'
}
```

## Views

### EtiyaStepperLayout
```xml
<com.etiya.stepper.EtiyaStepperLayout
    android:id="@+id/stepper"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:stepper_title="@string/title"
    app:stepper_description="@string/description">
    
    ...
    
</com.etiya.stepper.EtiyaStepperLayout>
```

### EtiyaStepView
```xml
<com.etiya.stepper.EtiyaStepView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:step_name="@string/step_name"
    app:step_button="@string/step_button">

    <LinearLayout
        android:id="@+id/step_content"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="@dimen/default_padding"
        android:orientation="vertical">
        
        ...
       
    </LinearLayout>

    <RelativeLayout
        android:id="@+id/step_summary"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="@dimen/default_padding">
        
        ...
        
    </RelativeLayout>

</com.etiya.stepper.EtiyaStepView>
```

### EtiyaProgressButton
```xml
<com.etiya.stepper.EtiyaProgressButton
    android:id="@+id/etiya_progress_button"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:button_text="@string/save"/>
```

## Usage
```java
EtiyaStepperLayout stepper = findViewById(R.id.stepper);

stepper.getStep(1).setStepButtonOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        stepper.setEnabled(false);
        stepper.getCurrentStep().showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stepper.nextStep();
                stepper.setEnabled(true);
            }
        }, 1000);
    }
});
```

```java
EtiyaProgressButton btnProgress = findViewById(R.id.btnProgress);
btnProgress.setOnClickListener(...);
btnProgress.showProgress();
```

In order to see other features, you can look at the sample application and read [documentation](https://wmramazan.github.io/EtiyaStepper).

## License
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

## Other Screenshots
![preview](https://github.com/wmramazan/EtiyaStepper/blob/master/device-2018-06-18-083736.png)
![preview](https://github.com/wmramazan/EtiyaStepper/blob/master/device-2018-06-18-083406.png)
![preview](https://github.com/wmramazan/EtiyaStepper/blob/master/device-2018-06-18-083415.png)
![preview](https://github.com/wmramazan/EtiyaStepper/blob/master/device-2018-06-18-083422.png)
![preview](https://github.com/wmramazan/EtiyaStepper/blob/master/device-2018-06-18-083428.png)

***
**Thanks to [omerakkus](https://github.com/omerakkus)**

[Etiya Mobile Team](https://www.etiya.com/)