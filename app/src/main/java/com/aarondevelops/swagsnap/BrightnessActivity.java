package com.aarondevelops.swagsnap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class BrightnessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brightness);
    }

    public void onSubmit(View view)
    {
        RadioGroup buttonGroup = (RadioGroup) findViewById(R.id.radioGrouping);
        RadioButton selectedButton = (RadioButton) findViewById(buttonGroup.getCheckedRadioButtonId());

        UserData.luminosityChoice = selectedButton.getText().toString();

        Intent commentsIntent = new Intent(this, ThanksActivity.class);
        startActivity(commentsIntent);
    }
}
