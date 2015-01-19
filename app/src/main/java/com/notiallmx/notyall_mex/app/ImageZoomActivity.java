package com.notiallmx.notyall_mex.app;

/**
 * Created by lord on 18/01/2015.
 */

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

public class ImageZoomActivity extends Activity {

    private ImageViewTouch mImage;
    String _IMG;
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        Log.i("Image Zoom Activity", "Iniciamos Actividad");
        Bundle bundle = getIntent().getExtras();//sacamos variables
        _IMG = bundle.getString("_IMG");//sacamos variables
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.imagetouch );
        getWindow().addFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN );

        mImage = (ImageViewTouch) findViewById(R.id.imageView1);
        mImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_IF_BIGGER);

        try {
            mImage.setImageDrawable(procesosjsoup.dameDrawabledeURL(_IMG));
        }
        catch ( IOException e ) {
            Toast.makeText( this, e.toString(), Toast.LENGTH_LONG ).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onContentChanged() {
        super.onContentChanged();

        mImage = (ImageViewTouch) findViewById(R.id.imageView1);
        mImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_IF_BIGGER);
        mImage.setSingleTapListener(
                new ImageViewTouch.OnImageViewTouchSingleTapListener() {
                    @Override
                    public void onSingleTapConfirmed() {
                        Log.d("Imagen Tap", "onSingleTapConfirmed");
                    }
                }
        );
        mImage.setDoubleTapListener(
                new ImageViewTouch.OnImageViewTouchDoubleTapListener() {
                    @Override
                    public void onDoubleTap() {
                        Log.d("Imagen Tap", "onDoubleTap");
                    }
                }
        );
        mImage.setOnDrawableChangedListener(
                new ImageViewTouchBase.OnDrawableChangeListener() {
                    @Override
                    public void onDrawableChanged(Drawable drawable) {
                        Log.i("Imagen", "onBitmapChanged: " + drawable);
                    }
                }
        );
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}