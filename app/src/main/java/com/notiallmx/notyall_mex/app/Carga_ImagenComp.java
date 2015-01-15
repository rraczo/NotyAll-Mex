package com.notiallmx.notyall_mex.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;



/**
 * Created by oscar on 06/06/2014.
 */
public class Carga_ImagenComp extends Activity {
    String _IMG = "";
    private ImageView imageView;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix = new Matrix();
    private float scale = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_imagen_comp);
        Bundle bundle = getIntent().getExtras();//sacamos variables
        _IMG = bundle.getString("_IMG");//sacamos variables

        imageView = (ImageView) findViewById(R.id.image_G);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
       // ImageView imagenC = (ImageView)findViewById(R.id.imagenGrande);
        try {
            Log.i("Que shingaos click", _IMG);
            imageView.setImageDrawable(procesosjsoup.dameDrawabledeURL(_IMG));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean onTouchEvent(MotionEvent ev) {
        scaleGestureDetector.onTouchEvent(ev);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 5.0f));
            matrix.setScale(scale, scale);
            imageView.setImageMatrix(matrix);
            return true;
        }
    }


}