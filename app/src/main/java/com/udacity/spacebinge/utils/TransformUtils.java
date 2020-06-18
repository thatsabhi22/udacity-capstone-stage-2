package com.udacity.spacebinge.utils;

import android.net.Uri;

import com.udacity.spacebinge.models.Datum;
import com.udacity.spacebinge.models.Item;
import com.udacity.spacebinge.models.Link;
import com.udacity.spacebinge.models.Result;
import com.udacity.spacebinge.models.VideoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TransformUtils {

    public static final String LOG_TAG = TransformUtils.class.getName();

    /**
     * Return an {@link VideoItem} object by parsing out information
     * about the first movie from the input moviesJSON string.
     *
     * @return
     */
    public static List<VideoItem> extractVideoItemFromResult(Result result) {

        List<Item> itemList = result.getCollection().getItems();
        List<VideoItem> videoItemsList = new ArrayList<>();

        int maxItems = Math.min(itemList.size(), 10);
        for (int i = 0; i < maxItems; i++) {
            Item currentItem = itemList.get(i);
            Datum data = currentItem.getData().get(0);
            Link link = currentItem.getLinks().get(0);
            VideoItem videoItem = new VideoItem();

            videoItem.setTitle(data.getTitle());
            videoItem.setDescription(data.getDescription());
            videoItem.setThumbnail_url(link.getHref());
            videoItem.setVideo_url(buildVideoUrl(currentItem.getHref(), data.getNasaId()));
            videoItem.setNasa_id(data.getNasaId());
            videoItem.setHref(currentItem.getHref());
            videoItem.setMedia_type(data.getMediaType());
            videoItem.setDate_created(data.getDateCreated());

            videoItemsList.add(videoItem);
        }
        return videoItemsList;
    }

    public static String buildVideoUrl(String collectionUrl, String nasaId) {

        //Uri uri = Uri.parse("https://graph.facebook.com/me/home?limit=25&since=1374196005");
        Uri uri = Uri.parse(collectionUrl);

        String protocol = uri.getScheme();
        String server = uri.getAuthority();
        String path = uri.getPath();   //  /video/ARC-20180830-AAV3127-NiSVLive-Ep01-NASAWeb/collection.json
        Set<String> args = uri.getQueryParameterNames();

        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(server);
        sb.append("/");
        sb.append("video");
        sb.append("/");
        sb.append(nasaId);
        sb.append("/");
        sb.append(nasaId);
        sb.append("~");
        sb.append("mobile.mp4");

        return sb.toString();

//        "http://images-assets.nasa.gov/video/NHQ_2014_0825_TWAN/collection.json"
//        "http://images-assets.nasa.gov/video/NHQ_2014_0825_TWAN/NHQ_2014_0825_TWAN~mobile.mp4"
//
//        "http://images-assets.nasa.gov/video/ARC-20180830-AAV3127-NiSVLive-Ep01-NASAWeb/collection.json"
//        "http://images-assets.nasa.gov/video/ARC-20180830-AAV3127-NiSVLive-Ep01-NASAWeb/ARC-20180830-AAV3127-NiSVLive-Ep01-NASAWeb~mobile.mp4"
    }
}
