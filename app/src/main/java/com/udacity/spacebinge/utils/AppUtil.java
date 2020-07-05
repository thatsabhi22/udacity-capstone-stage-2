package com.udacity.spacebinge.utils;

import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.Locale;

public class AppUtil {

    public static boolean isEmptyOrNullString(String input) {
        if ((input != null) && (!input.trim().isEmpty())) {
            return false;
        } else {
            return true;
        }
    }

    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes){
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }

    public static String getRootDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),
                    null)[0];
            return file.getAbsolutePath();
        } else {
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        }
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static float spToPx(Context context, float sp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float px = (sp * displayMetrics.scaledDensity);
        return px;
    }
}
