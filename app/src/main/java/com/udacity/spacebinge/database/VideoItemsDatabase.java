package com.udacity.spacebinge.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.udacity.spacebinge.models.VideoItem;

@Database(entities = {VideoItem.class}, version = 1, exportSchema = false)
public abstract class VideoItemsDatabase extends RoomDatabase {

    private static final String LOG_TAG = VideoItemsDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "videoItems.db";
    private static volatile VideoItemsDatabase sInstance;

    public static VideoItemsDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    Log.d(LOG_TAG, "Creating new database instance");
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            VideoItemsDatabase.class, VideoItemsDatabase.DATABASE_NAME)
                            .build();
                }
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract VideoItemsDAO videoItemsDAO();
}
