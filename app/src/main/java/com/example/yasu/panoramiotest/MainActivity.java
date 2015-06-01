package com.example.yasu.panoramiotest;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    private LinearLayout photosLayout;
    private TextView textView;

    private class GetPhotoResultTask extends PanoramioAPITask{
        private GetPhotoResultTask(Context context) {
            super(context);
        }

        @Override
        protected void onPostExecute(PhotoResult data) {
            super.onPostExecute(data);

            //データ取得APIの処理が終わったら情報をViewへ表示する
            if(data != null){
                for(PhotoResult.Photo p : data.photoList){
                    //作成しておいたレイアウトに合わせて情報を載せる
                    View row = View.inflate(MainActivity.this, R.layout.photo_row, null);
                    ImageView imageView = (ImageView) row.findViewById(R.id.iv_photo);
                    imageView.setTag(p.url);
                    //画像読み込み処理を開始
                    ImageLoaderTask task = new ImageLoaderTask(MainActivity.this);
                    task.execute(imageView);

                    //作ったViewを通常の面に追加する
                    photosLayout.addView(row);
                }
            }

            textView.setText("OK");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photosLayout = (LinearLayout)findViewById(R.id.ll_photos);
        textView = (TextView)findViewById(R.id.tv_main);

        new GetPhotoResultTask(this).execute("hoge");
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
}
