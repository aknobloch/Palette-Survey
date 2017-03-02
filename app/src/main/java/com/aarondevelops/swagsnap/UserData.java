package com.aarondevelops.swagsnap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;


public class UserData implements Serializable
{
    enum ColorPreference
    {
        LIGHT, DARK, OK;
    }

    int[] colors;
    int totalColorsAdded;
    byte[] picture;
    int[] chosenColors;
    int accuracyRating;
    ColorPreference choice;

    public UserData(Bitmap userPicture)
    {
        this.colors = new int[8];
        totalColorsAdded = 0;

        // bitmaps cannot be sent across intents, must convert first
        ByteArrayOutputStream streamWriter = new ByteArrayOutputStream();
        userPicture.compress(Bitmap.CompressFormat.PNG, 100, streamWriter);
        picture = streamWriter.toByteArray();

        chosenColors = new int[3];
    }

    public void setAccuracyRating(int rating)
    {
        this.accuracyRating = rating;
    }

    public void setChoice(ColorPreference choice)
    {
        this.choice = choice;
    }

    public void addColor(int rgbColor)
    {
        colors[totalColorsAdded] = rgbColor;
        totalColorsAdded++;
    }

    public int[] getColors()
    {
        return this.colors;
    }

    public Bitmap getBitmap()
    {
        return BitmapFactory.decodeByteArray(this.picture, 0, this.picture.length);
    }

}
