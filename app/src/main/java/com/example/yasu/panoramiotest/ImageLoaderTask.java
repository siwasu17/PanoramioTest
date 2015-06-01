package com.example.yasu.panoramiotest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public class ImageLoaderTask extends AsyncTask<ImageView,Void,Bitmap> {
    private static final String USER_AGENT = "Panoramio Sample";
    private final Context context;

    Exception exception;
    ImageView imageView;

    public ImageLoaderTask(Context context) {
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(ImageView... params) {
        imageView = params[0];
        String url = (String)imageView.getTag();
        try{
            return getImage(context,url);
        }catch (Exception e){
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }
    }

    private static Bitmap getImage(Context context,String uri) throws IOException{
        AndroidHttpClient client = AndroidHttpClient.newInstance(USER_AGENT,context);
        HttpGet get = new HttpGet(uri);

        try{
            HttpResponse response = client.execute(get);
            return BitmapFactory.decodeStream(response.getEntity().getContent());
        }finally{
            client.close();
        }

    }
}
