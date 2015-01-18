package com.notiallmx.notyall_mex.app;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by oscar on 06/06/2014.
 * Usando libreria de http://blog.sephiroth.it/2011/04/04/imageview-zoom-and-scroll/
 */
public class Carga_ImagenComp extends Activity {
    String _IMG = "";
    ImageViewTouch mImage;

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
        Bitmap bitmap = procesosjsoup.dameBitmapdeURL(_IMG);
        if (null != bitmap) {
            mImage.setImageBitmap(bitmap, null, - 1, 8f);
        }
        else {
            Toast.makeText(this, "Failed to load the image", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}