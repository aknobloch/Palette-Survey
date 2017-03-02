package com.aarondevelops.swagsnap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int PICTURE_REQUEST_CODE = 1;
    public static final String INTENT_TAG = "Tag for intent";
    UserData surveyData;


    class PaletteListener implements Palette.PaletteAsyncListener
    {

        Context mainActivityContext;

        PaletteListener(Context mainContext)
        {
            mainActivityContext = mainContext;
        }

        @Override
        public void onGenerated(Palette palette)
        {
            ArrayList<Palette.Swatch> colorList = new ArrayList<>(palette.getSwatches());

            for(Palette.Swatch color : colorList)
            {
                surveyData.addColor(color.getRgb());
            }

            Intent colorChoiceIntent = new Intent(mainActivityContext, ColorChoiceActivity.class);
            colorChoiceIntent.putExtra(INTENT_TAG, surveyData);
            startActivity(colorChoiceIntent);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void takePicture(View view)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takePictureIntent, PICTURE_REQUEST_CODE);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode != PICTURE_REQUEST_CODE || resultCode != RESULT_OK)
        {
            return;
        }

        Button takeSelfieButton = (Button) findViewById(R.id.pictureButton);
        takeSelfieButton.setEnabled(false);

        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");

        surveyData = new UserData(imageBitmap);

        Palette.Builder imagePalette = new Palette.Builder(imageBitmap);
        imagePalette.maximumColorCount(8);
        imagePalette.generate(new PaletteListener(this));


    }
}
