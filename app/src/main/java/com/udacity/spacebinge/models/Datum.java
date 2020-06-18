package com.udacity.spacebinge.models;

import java.util.List;

public class Datum {

    private String nasa_id;
    private String center;
    private String media_type;
    private List<String> album = null;
    private String date_created;
    private String description;
    private String title;
    private List<String> keywords = null;
    private String secondary_creator;
    private String location;
    private String photographer;

    public String getNasaId() {
        return nasa_id;
    }

    public void setNasaId(String nasaId) {
        this.nasa_id = nasaId;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getMediaType() {
        return media_type;
    }

    public void setMediaType(String mediaType) {
        this.media_type = mediaType;
    }

    public List<String> getAlbum() {
        return album;
    }

    public void setAlbum(List<String> album) {
        this.album = album;
    }

    public String getDateCreated() {
        return date_created;
    }

    public void setDateCreated(String dateCreated) {
        this.date_created = dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getSecondaryCreator() {
        return secondary_creator;
    }

    public void setSecondaryCreator(String secondaryCreator) {
        this.secondary_creator = secondaryCreator;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

}
