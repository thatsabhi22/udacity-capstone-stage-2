package com.udacity.spacebinge.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.DisplayMetrics;

import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppUtil {

    public static boolean isEmptyOrNullString(String input) {
        if ((input != null) && (!input.trim().isEmpty())) {
            return false;
        } else {
            return true;
        }
    }

    @NotNull
    public static String formatDate(String date) {
        Date date1 = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            date1 = sdf.parse(date);
            sdf = new SimpleDateFormat("MMMM dd, yyyy");
            date = sdf.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes) {
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

    /**
     * No Network Condition handled based on link below
     * https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
     */

    public static boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return false;
        } catch (IOException e) {
            return true;
        }
    }

    public static class CheckOnlineStatus extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            //This is a background thread, when it finishes executing will return the result from your function.
            Boolean isOffline;
            isOffline = isOnline();
            return isOffline;
        }
    }
}
