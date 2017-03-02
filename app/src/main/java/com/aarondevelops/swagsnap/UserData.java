package com.aarondevelops.swagsnap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class UserData implements Serializable
{

    static ArrayList<Integer> colors;
    static Bitmap picture;
    // which colors did the chose? First item in list correspondes to the index of the colors
    // array, and was their first choice. Second item is the index of their second choice, etc.
    static ArrayList<Integer> chosenColorIndices;
    static int accuracyRating;
    static String luminosityChoice;

    static {
        colors = new ArrayList<>();
        chosenColorIndices = new ArrayList<>();
    }
}
