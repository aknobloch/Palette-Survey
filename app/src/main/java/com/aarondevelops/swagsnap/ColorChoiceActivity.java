package com.aarondevelops.swagsnap;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ColorChoiceActivity extends AppCompatActivity {

    TextView firstSwatch;
    TextView secondSwatch;
    TextView thirdSwatch;
    TextView fourthSwatch;
    TextView fifthSwatch;
    TextView sixthSwatch;
    TextView seventhSwatch;
    TextView eighthSwatch;

    TextView[] allSwatches;

    TextView[] userColorChoices;
    int userTotalChoices = 0;

    UserData surveyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_choice);

        allSwatches = new TextView[8];
        userColorChoices = new TextView[3];

        // Pay attention to this... had no idea this was possible...
        allSwatches[0] = firstSwatch = (TextView) findViewById(R.id.firstSwatch);
        allSwatches[1] = secondSwatch = (TextView) findViewById(R.id.secondSwatch);
        allSwatches[2] = thirdSwatch = (TextView) findViewById(R.id.thirdSwatch);
        allSwatches[3] = fourthSwatch = (TextView) findViewById(R.id.fourthSwatch);
        allSwatches[4] = fifthSwatch = (TextView) findViewById(R.id.fifthSwatch);
        allSwatches[5] = sixthSwatch = (TextView) findViewById(R.id.sixthSwatch);
        allSwatches[6] = seventhSwatch = (TextView) findViewById(R.id.seventhSwatch);
        allSwatches[7] = eighthSwatch = (TextView) findViewById(R.id.eighthSwatch);

        // grab survey data that was passed along
        Intent callingIntent = getIntent();
        surveyData = (UserData) callingIntent.getSerializableExtra(MainActivity.INTENT_TAG);

        int[] detectedColors = surveyData.getColors();
        for(int i = 0; i < detectedColors.length; i++)
        {
            // occasionally the Palette will not find/contain eight colors
            if(detectedColors[i] != 0)
            {
                allSwatches[i].setBackgroundColor(detectedColors[i]);
            }
            else
            {
                // in the case that it doesn't have enough colors, make sure text view is disabled
                allSwatches[i].setEnabled(false);
                allSwatches[i].setClickable(false);
            }
        }
    }

    public void onSwatchSelect(View view)
    {
        // no more than 3 choices
        if(userTotalChoices == 3)
        {
            Toast alertToast = Toast.makeText(this,
                    "Only three choices! Clear your choices if you'd like!",
                    Toast.LENGTH_LONG);
            alertToast.show();
            return;
        }

        TextView selectedSwatch = (TextView) view;

        // check if color has already been selected
        for(int i = 0; i < userTotalChoices; i++)
        {
            if(selectedSwatch.equals(userColorChoices[i]))
            {
                return;
            }
        }

        ColorDrawable color = (ColorDrawable) selectedSwatch.getBackground();
        userColorChoices[userTotalChoices] = selectedSwatch;

        // user total choices starts at zero for indexing, so increment one more
        selectedSwatch.setText("" + (userTotalChoices + 1));

        // check luminosity of color choices, set as appropriate
        int textColor = findAppropriateTextColor(color.getColor());
        selectedSwatch.setTextColor(textColor);

        // choices up for next time
        userTotalChoices++;
    }

    private int findAppropriateTextColor(int color)
    {
        int red = (color & 0b111111110000000000000000) >> 16;
        int green = (color & 0b000000001111111100000000) >> 8;
        int blue = color & 0b000000000000000011111111;

        int luminosity = (int) (red * 0.2126
                + green * 0.7152
                + blue * 0.0722);

        // if the alpha is low or the luminosity is over 132,
        // the background is considered "bright" and text should be black
        if(luminosity > 132)
        {
            return Color.BLACK;
        }
        else
        {
            return Color.WHITE;
        }
    }

    public void onClear(View view)
    {
        userColorChoices = new TextView[3];
        userTotalChoices = 0;

        for(TextView swatch : allSwatches)
        {
            swatch.setText("");
        }
    }
}
