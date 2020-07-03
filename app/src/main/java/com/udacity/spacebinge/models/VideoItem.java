package com.udacity.spacebinge.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "VideoItems",indices = {@Index(value = {"nasa_id"},
        unique = true)})
public class VideoItem implements Parcelable {

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

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String video_url;
    private String thumbnail_url;
    private String nasa_id;
    private String href;
    private String media_type;
    private String date_created;
    private boolean is_downloaded;
    private String storage_path;
    private boolean is_in_watchlist;

    public VideoItem(){}

    public VideoItem(int id,
                     String title,
                     String description,
                     String video_url,
                     String thumbnail_url,
                     String nasa_id,
                     String href,
                     String media_type,
                     String date_created
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.video_url = video_url;
        this.thumbnail_url = thumbnail_url;
        this.nasa_id = nasa_id;
        this.href = href;
        this.media_type = media_type;
        this.date_created = date_created;
        this.is_downloaded = false;
        this.storage_path = "";
        this.is_in_watchlist = false;
    }

    @Ignore
    public VideoItem(String title,
                     String description,
                     String video_url,
                     String thumbnail_url,
                     String nasa_id,
                     String href,
                     String media_type,
                     String date_created
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.video_url = video_url;
        this.thumbnail_url = thumbnail_url;
        this.nasa_id = nasa_id;
        this.href = href;
        this.media_type = media_type;
        this.date_created = date_created;
        this.is_downloaded = false;
        this.storage_path = "";
        this.is_in_watchlist = false;
    }

    protected VideoItem(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        video_url = in.readString();
        thumbnail_url = in.readString();
        nasa_id = in.readString();
        href = in.readString();
        media_type = in.readString();
        date_created = in.readString();
        is_downloaded = TextUtils.equals(in.readString(), "true");
        storage_path = in.readString();
        is_downloaded = TextUtils.equals(in.readString(), "true");
    }

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

    public String getThumbnail_url() {
        return thumbnail_url;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public void setNasa_id(String nasa_id) {
        this.nasa_id = nasa_id;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public boolean isIs_downloaded() {
        return is_downloaded;
    }

    public void setIs_downloaded(boolean is_downloaded) {
        this.is_downloaded = is_downloaded;
    }

    public String getStorage_path() {
        return storage_path;
    }

    public void setStorage_path(String storage_path) {
        this.storage_path = storage_path;
    }

    public boolean is_in_watchlist() {
        return is_in_watchlist;
    }

    public void setIs_in_watchlist(boolean is_in_watchlist) {
        this.is_in_watchlist = is_in_watchlist;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(video_url);
        dest.writeString(thumbnail_url);
        dest.writeString(nasa_id);
        dest.writeString(href);
        dest.writeString(media_type);
        dest.writeString(date_created);
        dest.writeString(String.valueOf(is_downloaded));
        dest.writeString(storage_path);
        dest.writeString(String.valueOf(is_in_watchlist));
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
