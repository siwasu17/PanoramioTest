package com.example.yasu.panoramiotest;

import android.content.Context;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Ref: http://www.panoramio.com/api/data/api.html
 */
public class PanoramioAPI {
    private static final String USER_AGENT = "Panoramio Sample";
    private static final String URL = "http://www.panoramio.com/map/get_panoramas.php?set=public&from=0&to=20&minx=-180&miny=-90&maxx=180&maxy=90&size=medium&mapfilter=true";

    public static PhotoResult getPhotos(Context context) throws IOException,JSONException {
        AndroidHttpClient client = AndroidHttpClient.newInstance(USER_AGENT,context);
        HttpGet get = new HttpGet(URL);

        StringBuilder sb = new StringBuilder();
        try{
            HttpResponse response = client.execute(get);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line = null;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
        }finally{
            client.close();
        }

        System.out.println(sb.toString());
        return new PhotoResult(new JSONObject(sb.toString()));
    }


}
