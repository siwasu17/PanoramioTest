package com.example.yasu.panoramiotest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhotoResult {

    public final List<Photo> photoList = new ArrayList<Photo>();

    public PhotoResult(JSONObject jsonObject) throws JSONException{
        JSONArray photos = jsonObject.getJSONArray("photos");

        for(int i = 0;i < photos.length();i++){
            JSONObject photoJson = photos.getJSONObject(i);
            Photo p = new Photo(photoJson);
            photoList.add(p);
        }
    }

    public class Photo{

        public final String title;
        public final String url;
        public final int width;
        public final int height;
        public final double longitude;
        public final double latitude;

        public Photo(JSONObject jsonObject) throws JSONException{
            title = jsonObject.getString("photo_title");
            url = jsonObject.getString("photo_file_url");
            width = jsonObject.getInt("width");
            height = jsonObject.getInt("height");
            longitude = jsonObject.getDouble("longitude");
            latitude = jsonObject.getDouble("latitude");
        }
    }

}
