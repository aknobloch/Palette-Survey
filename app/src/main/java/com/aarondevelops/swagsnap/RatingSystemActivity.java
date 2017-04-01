package com.aarondevelops.swagsnap;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;

public class RatingSystemActivity extends AppCompatActivity {

    ImageView[] allStars = new ImageView[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_system);

        allStars[0] = (ImageView) findViewById(R.id.firstStar);
        allStars[1] = (ImageView) findViewById(R.id.secondStar);
        allStars[2] = (ImageView) findViewById(R.id.thirdStar);
        allStars[3] = (ImageView) findViewById(R.id.fourthStar);
        allStars[4] = (ImageView) findViewById(R.id.fifthStar);

    }


    public void onStarSelect(View view)
    {
        ImageView selectedStar = (ImageView) view;
        boolean shouldHighlight = true;

        for(int i = 0; i < allStars.length; i++)
        {
            ImageView currentStar = allStars[i];

            if(shouldHighlight)
            {
                currentStar.setImageResource(android.R.drawable.btn_star_big_on);

            }
            else
            {
                currentStar.setImageResource(android.R.drawable.btn_star_big_off);
            }

            // finally, if we've highlighted the selected star the rest shouldn't be
            // and we need to record the rating
            if(currentStar.equals(selectedStar))
            {
                shouldHighlight = false;
                // plus one for zero index
                UserData.accuracyRating = (i + 1);

                pulseAnimateObject(currentStar);
            }
        }
    }

    private void pulseAnimateObject(ImageView animationTarget)
    {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(animationTarget, "scaleX", 1.1f)
                .setDuration(200);

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(animationTarget, "scaleY", 1.1f)
                .setDuration(200);

        animatorX.setRepeatCount(1);
        animatorY.setRepeatCount(1);

        animatorX.setRepeatMode(ValueAnimator.REVERSE);
        animatorY.setRepeatMode(ValueAnimator.REVERSE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.start();
    }

    public void onSubmit(View view)
    {
        Intent brightnessIntent = new Intent(this, BrightnessActivity.class);
        startActivity(brightnessIntent);
    }

}
