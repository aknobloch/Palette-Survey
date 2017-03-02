package com.aarondevelops.swagsnap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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
    private static boolean picturePermission = false;

    class PaletteListener implements Palette.PaletteAsyncListener
    {
        @Override
        public void onGenerated(Palette palette)
        {
            ArrayList<Palette.Swatch> colorList = new ArrayList<>(palette.getSwatches());

            for(Palette.Swatch color : colorList)
            {
                UserData.colors.add(color.getRgb());
            }

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

    /*
    Method to as the user for permission to store their picture.
     */
    private void getPicturePermission(Bitmap selfie)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("We'd like to save this picture to see how we did later, is that cool?" +
                            "You can still help us out either way, so no hard feelings!");
        builder.setTitle("Privacy Alert!");

        builder.setPositiveButton("Sure!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                MainActivity.picturePermission = true;
            }
        });

        builder.setNegativeButton("Nah...", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                MainActivity.picturePermission = false;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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

        getPicturePermission(imageBitmap);

        Palette.Builder imagePalette = new Palette.Builder(imageBitmap);
        imagePalette.maximumColorCount(8);
        imagePalette.generate(new PaletteListener());

    }
}
