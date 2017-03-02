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

import java.util.ArrayList;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_choice);

        allSwatches = new TextView[8];

        // had no idea this was possible...
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

        for(int i = 0; i < 8; i++)
        {

            try
            {
                int color = UserData.colors.get(i);
                allSwatches[i].setBackgroundColor(color);
            }
            // occasionally the Palette will not find/contain eight colors
            catch(IndexOutOfBoundsException iob)
            {
                // in the case that it doesn't have enough colors, make sure text view is disabled
                allSwatches[i].setEnabled(false);
                allSwatches[i].setClickable(false);
            }
        }
    }

    private int getSwatchIndex(TextView swatch)
    {
        for(int i = 0; i < allSwatches.length; i++)
        {
            if(swatch.equals(allSwatches[i]))
            {
                return i;
            }
        }

        // not found
        return -1;
    }

    public void onSwatchSelect(View view)
    {
        // no more than 3 choices
        if(UserData.chosenColorIndices.size() == 3)
        {
            Toast alertToast = Toast.makeText(this,
                    "Only three choices! Clear your choices if you'd like!",
                    Toast.LENGTH_LONG);
            alertToast.show();
            return;
        }

        TextView selectedSwatch = (TextView) view;

        // check if color has already been selected
        for(int i = 0; i < UserData.chosenColorIndices.size(); i++)
        {
            int colorChoice = UserData.chosenColorIndices.get(i);
            if(selectedSwatch.equals(allSwatches[colorChoice]))
            {
                return;
            }
        }

        // get selected color
        ColorDrawable color = (ColorDrawable) selectedSwatch.getBackground();
        UserData.chosenColorIndices.add(getSwatchIndex(selectedSwatch));

        // show selection ranking (1, 2, 3)
        selectedSwatch.setText("" + UserData.chosenColorIndices.size());

        // check luminosity of color choices, set as appropriate
        int textColor = findAppropriateTextColor(color.getColor());
        selectedSwatch.setTextColor(textColor);

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
        UserData.chosenColorIndices = new ArrayList<>();

        for(TextView swatch : allSwatches)
        {
            swatch.setText("");
        }
    }

    public void onSubmit(View view)
    {

    }
}
