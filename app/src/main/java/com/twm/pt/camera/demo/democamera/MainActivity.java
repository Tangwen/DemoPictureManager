package com.twm.pt.camera.demo.democamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.twm.pt.camera.demo.democamera.pictureManager.PictureManager;
import com.twm.pt.camera.demo.democamera.utility.StorageDirectory;

import java.io.File;

//http://fecbob.pixnet.net/blog/post/39266219-android%E4%BD%BF%E7%94%A8%E7%9B%B8%E6%A9%9F%E7%85%A7%E7%9B%B8%E4%BB%A5%E5%8F%8A%E5%B0%8D%E7%85%A7%E7%89%87%E7%9A%84%E8%A3%81%E5%89%AA
//http://dean-android.blogspot.tw/2013/05/android.html


public class MainActivity extends ActionBarActivity {


    private Button btn_make_pic = null;
    private Button btn_get_pic = null;
    private ImageView img_creama = null;
    private TextView display_text = null;

    private PictureManager mPictureManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();

        File picPath = new File(StorageDirectory.getStorageDirectory(getApplicationContext(), StorageDirectory.StorageType.ST_SDCard_RootDir) + "/Pictures/SoftBall/");
        Log.i("TAG-->", "" + picPath.getAbsolutePath());
        mPictureManager = PictureManager.getInstance(this).setPhotoFilePath(picPath).setNeedCut(true);
    }

    private void initView() {
        btn_make_pic = (Button) findViewById(R.id.btn_make_pic);
        btn_get_pic = (Button) findViewById(R.id.btn_get_pic);
        img_creama = (ImageView) findViewById(R.id.img_creama);
        display_text = (TextView) findViewById(R.id.display_text);

        btn_make_pic.setOnClickListener(makePic_listener);
        btn_get_pic.setOnClickListener(getPic_listener);
    }

    private void setImg(Bitmap photo) {
        if(img_creama==null) return;
        if (photo == null) {
            img_creama.setImageResource(R.mipmap.get_user_photo);
        } else {
            img_creama.setImageBitmap(photo);

        }
    }
    private void setText(String displayText) {
        if(display_text==null) return;
        if(displayText == null) {
            display_text.setText("");
        } else {
            display_text.setText(displayText);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap photo = mPictureManager.onActivityResult(requestCode, resultCode, data);
        setImg(photo);
        setText(mPictureManager.getPhotoFile().getAbsolutePath());
        super.onActivityResult(requestCode, resultCode, data);
    }



    private View.OnClickListener makePic_listener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            mPictureManager.nextPhotoFileName().takePhoto();
        }
    };


    private View.OnClickListener getPic_listener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            mPictureManager.nextPhotoFileName().galleryPhoto();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_camera, menu);
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
