package com.udacity.spacebinge.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "VideoItems")
public class VideoItem implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String video_url;
    private String nasa_id;
    private String href;
    private String media_type;
    private String date_created;
    private List<String> keywords;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getNasa_id() {
        return nasa_id;
    }

    public String getHref() {
        return href;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getDate_created() {
        return date_created;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public VideoItem(int id,
                     String title,
                     String description,
                     String video_url,
                     String nasa_id,
                     String href,
                     String media_type,
                     String date_created,
                     List<String> keywords) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.video_url = video_url;
        this.nasa_id = nasa_id;
        this.href = href;
        this.media_type = media_type;
        this.date_created = date_created;
        this.keywords = keywords;
    }

    @Ignore
    public VideoItem(String title,
                     String description,
                     String video_url,
                     String nasa_id,
                     String href,
                     String media_type,
                     String date_created,
                     List<String> keywords) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.video_url = video_url;
        this.nasa_id = nasa_id;
        this.href = href;
        this.media_type = media_type;
        this.date_created = date_created;
        this.keywords = keywords;
    }

    protected VideoItem(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        video_url = in.readString();
        nasa_id = in.readString();
        href = in.readString();
        media_type = in.readString();
        date_created = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(video_url);
        dest.writeString(nasa_id);
        dest.writeString(href);
        dest.writeString(media_type);
        dest.writeString(date_created);
    }

    public static final Creator<VideoItem> CREATOR = new Creator<VideoItem>() {
        @Override
        public VideoItem createFromParcel(Parcel in) {
            return new VideoItem(in);
        }

        @Override
        public VideoItem[] newArray(int size) {
            return new VideoItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
