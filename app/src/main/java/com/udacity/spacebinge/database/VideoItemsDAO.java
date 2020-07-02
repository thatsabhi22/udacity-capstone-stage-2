package com.udacity.spacebinge.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.udacity.spacebinge.models.VideoItem;

import java.util.List;

@Dao
public interface VideoItemsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertVideoItem(VideoItem videoItem);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateVideoItem(VideoItem videoItem);

    @Query("DELETE FROM VideoItems where nasa_id = :nasa_id")
    void deleteVideoItem(String nasa_id);

    @Query("SELECT * FROM VideoItems ORDER BY id DESC")
    LiveData<List<VideoItem>> loadAllVideoItems();

    @Query("SELECT * FROM VideoItems where id = :id")
    VideoItem getVideoItemById(int id);
}
