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
import java.util.Formatter;

/**
 * Ref: http://www.panoramio.com/api/data/api.html
 */
public class PanoramioAPI {
    private static final String USER_AGENT = "Panoramio Sample";
    private static final String URL = "http://www.panoramio.com/map/get_panoramas.php?set=full&from=0&to=20&size=medium&mapfilter=true";

    public static PhotoResult getPhotos(Context context,Double[] latln) throws IOException,JSONException {
        //引数の緯度経度取得
        Double lat = latln[0];
        Double ln = latln[1];
        Double offset = 0.002;
        String rangeParam = "&minx=" + (ln - offset)
                + "&miny=" + (lat - offset)
                + "&maxx=" + (ln + offset)
                + "&maxy=" + (lat + offset);
        System.out.println(URL + rangeParam);

        AndroidHttpClient client = AndroidHttpClient.newInstance(USER_AGENT,context);
        HttpGet get = new HttpGet(URL + rangeParam);

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
