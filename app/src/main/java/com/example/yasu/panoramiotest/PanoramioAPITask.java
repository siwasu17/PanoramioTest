package com.example.yasu.panoramiotest;

import android.content.Context;
import android.os.AsyncTask;

public class PanoramioAPITask extends AsyncTask<String,Void,PhotoResult> {
    private final Context context;
    Exception exception;

    public PanoramioAPITask(Context context) {
        this.context = context;
    }

    @Override
    protected PhotoResult doInBackground(String... params) {
        try{
            return PanoramioAPI.getPhotos(context);
        }catch(Exception e){
            exception = e;
        }

        return null;
    }
}
