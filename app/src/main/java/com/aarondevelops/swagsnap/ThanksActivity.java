package com.aarondevelops.swagsnap;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ThanksActivity extends AppCompatActivity {

    private static final String DIRECTORY_PATH = "SurveyData";
    private static final String FILE_NAME = "Records";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    private static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

        if(isExternalStorageWritable())
        {

            verifyStoragePermissions(this);

            File directory = new File(Environment.
                    getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                    DIRECTORY_PATH);

            directory.mkdirs();

            File records = new File(directory.getAbsolutePath() + FILE_NAME);

            try
            {
                FileWriter out = new FileWriter(records, true);
                Log.d("TEST", "" + records.getAbsolutePath());
                out.write("TESTING");
                out.close();

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Log.e("WRITE ERROR", "Error writing to disk. Ext Storage not available.");
        }

    }

    public boolean isExternalStorageWritable()
    {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
