package com.example.yasu.panoramiotest;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends ActionBarActivity implements LocationListener{
    private LinearLayout photosLayout;

    // GPS用
    private LocationManager manager = null;


    private class GetPhotoResultTask extends PanoramioAPITask{
        private GetPhotoResultTask(Context context) {
            super(context);
        }

        @Override
        protected void onPostExecute(PhotoResult data) {
            super.onPostExecute(data);

            //データ取得APIの処理が終わったら情報をViewへ表示する
            if(data != null){
                //一旦いまあるやつ消す
                photosLayout.removeAllViews();
                for(PhotoResult.Photo p : data.photoList){
                    //作成しておいたレイアウトに合わせて情報を載せる
                    View row = View.inflate(MainActivity.this, R.layout.photo_row, null);
                    ImageView imageView = (ImageView) row.findViewById(R.id.iv_photo);
                    imageView.setTag(p.url);
                    imageView.setScaleType(ImageView.ScaleType.FIT_START);
                    //imageView.setMinimumWidth(p.width);
                    //imageView.setMinimumHeight(p.height);

                    //画像読み込み処理を開始
                    ImageLoaderTask task = new ImageLoaderTask(MainActivity.this);
                    task.execute(imageView);

                    TextView textView = (TextView)row.findViewById(R.id.tv_title);
                    textView.setText(p.title);

                    //作ったViewを通常の面に追加する
                    photosLayout.addView(row);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photosLayout = (LinearLayout)findViewById(R.id.ll_photos);

        //位置情報取得
        manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gpsFlg = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(gpsFlg) {
            System.out.println("GET LOCATION STATUS OK");
        }
    }

    @Override
    protected void onResume() {
        if(manager != null) {
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, this);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(manager != null) {
            manager.removeUpdates(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLocationChanged(Location location) {
        Double lat = location.getLatitude();
        Double ln = location.getLongitude();
        System.out.println("LAT: " + lat.toString() + " LONG: " + ln.toString());

        new GetPhotoResultTask(this).execute(lat,ln);
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
