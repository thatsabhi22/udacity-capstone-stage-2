package com.udacity.spacebinge.models;

import java.util.List;

public class Collection {
    private String version;
    private String href;
    private Metadata metadata;
    private List<Item> items = null;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHref() {
        return href;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
