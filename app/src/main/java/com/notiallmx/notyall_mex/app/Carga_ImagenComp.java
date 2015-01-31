package com.notiallmx.notyall_mex.app;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.FileNotFoundException;
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
                File pictureStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File noMedia = new File(pictureStorage, ".nomedia");
                if (!noMedia.exists())
                    noMedia.mkdirs();

                File file = new File(noMedia, "Notyall.png");

                try {
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG,93,fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                shareIntent.setType("image/png");

                startActivity(Intent.createChooser(shareIntent, "Compartir en:"));


            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}