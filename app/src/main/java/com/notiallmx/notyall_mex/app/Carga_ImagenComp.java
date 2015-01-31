package com.notiallmx.notyall_mex.app;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * Created by oscar on 06/06/2014.
 * Usando libreria de http://blog.sephiroth.it/2011/04/04/imageview-zoom-and-scroll/
 */
public class Carga_ImagenComp extends Activity {
    String _IMG = "";
    ImageViewTouch mImage;
    Boolean visible;
    LinearLayout layoutShare;
    Button shareImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();//sacamos variables
        _IMG = bundle.getString("_IMG");//sacamos variables
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_imagen_comp);
    }
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mImage = (ImageViewTouch) findViewById(R.id.image_G);
        final Bitmap bitmap = procesosjsoup.dameBitmapdeURL(_IMG);

        visible=false;

        layoutShare=(LinearLayout)findViewById(R.id.layoutShareButton);

        shareImageButton=(Button)findViewById(R.id.shareImage);

        if (null != bitmap) {
            mImage.setImageBitmap(bitmap, null, - 1, 8f);
        }
        else {
            Toast.makeText(this, "Failed to load the image", Toast.LENGTH_LONG).show();
        }

        mImage.setSingleTapListener(
                new ImageViewTouch.OnImageViewTouchSingleTapListener() {

                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void onSingleTapConfirmed() {
                        if (visible) {
                            Log.e("tocado", "esconder layout");
                            visible = false;
                            ObjectAnimator animation3 = ObjectAnimator.ofFloat(layoutShare,"alpha", 0);
                            animation3.setDuration(800);
                            animation3.start();
                        } else {
                            Log.e("tocado", "mostrar layout");

                            visible = true;
                            ObjectAnimator animation3 = ObjectAnimator.ofFloat(layoutShare,"alpha", 0.8F);
                            animation3.setDuration(800);
                            animation3.start();
                        }
                        Log.e("single touch", "onSingleTapConfirmed");


                        //startActivity(sharingIntent);
                    }
                });

        shareImageButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                File f = new File(Environment.getExternalStorageDirectory() + File.separator + "NotyAll-ImageShare.jpg");
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/NotyAll-ImageShar.jpg"));
                startActivity(Intent.createChooser(sharingIntent, "Share Image"));
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}