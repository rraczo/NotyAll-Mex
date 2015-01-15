package com.notiallmx.notyall_mex.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by oscar on 06/06/2014.
 */
public class Carga_ImagenComp extends Activity {
    String _IMG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_imagen_comp);
        Bundle bundle = getIntent().getExtras();//sacamos variables
        _IMG = bundle.getString("_IMG");//sacamos variables
        ImageView imagenC = (ImageView)findViewById(R.id.imagenGrande);
        try {
            Log.i("Que shingaos click", _IMG);
            imagenC.setImageDrawable(procesosjsoup.dameDrawabledeURL(_IMG));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}