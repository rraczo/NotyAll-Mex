package com.notiallmx.notyall_mex.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.notiallmx.notyall_mex.app.objects.item_Noticia;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Carga_nota_completa extends Activity {
    List<item_Noticia> listaNot = new ArrayList<item_Noticia>();
    String _LINK="";
    String _TITULO="";
    JSONObject _GRAMATIC=null;
    String share_titulo,share_todo;
    //	para el menu compartir
    private ShareActionProvider mShareActionProvider;
    private ProgressDialog progressDialog;
    private ListView listnotas ;
    DisplayImageOptions defaultOptions;
    ImageLoaderConfiguration config;
    ImageLoader imageLoader;
    private ProgressBar progressBar;
    private int progressStatus = 0;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_nota_completa);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        Bundle bundle = getIntent().getExtras();//sacamos variables
        _LINK=bundle.getString("_LINK");//sacamos variables
        _TITULO=bundle.getString("_TITULO");//sacamos variables
        Log.e("_NOMBRE",_TITULO);
        try {
            _GRAMATIC=new JSONObject(bundle.getString("_GRAMATIC"));
        } catch (JSONException e) {
            Log.e("Error","no puede parsear Json objet _GRAMATIC");
        }
        try{
            Log.i("_Nombre", _TITULO);
            if(_TITULO.length()>20) {
                setTitle(_TITULO.substring(0, 20) + "..");
            }
            else{
                setTitle(_TITULO);
            }
        }
        catch (Exception e){
            Log.e("Error en set title", _TITULO);

        }
        StrictMode.ThreadPolicy cron=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(cron);
        new Cargar_nota_completa(this,"test").execute();
        //activar el boton atras en la actionbar
        getActionBar().setDisplayHomeAsUpEnabled(true);


        defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        config = new ImageLoaderConfiguration.Builder(
                Carga_nota_completa.this)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

    }

    class Cargar_nota_completa extends AsyncTask<Void, Void, String> {
        private Activity context;
        private ProgressDialog progressDialog;
        Cargar_nota_completa(Activity context, String test) {
            this.context = context;
        }

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setMessage("Cargando, espere por favor...");
//            progressDialog.setIndeterminate(true);
//            progressDialog.show();
            //aqui el pedo
        }

        @Override
        protected String doInBackground(Void... arg0) {
            String response=null;
            //damenotas(_GRAMATIC);
            String link = _LINK;
            Log.e("Entramos en metodo damenotas con link",link);
            listaNot=procesosjsoup.SacaNotacompleta(link, _GRAMATIC);//procesamos pagina
            Log.e("Salimos del proceso sacanotas resumen",listaNot.toString());
            return response;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            TextView tituloC = (TextView)findViewById(R.id.tituloNotaC);
            TextView fechaC = (TextView)findViewById(R.id.fecha_NotaC);
            TextView resumenC = (TextView)findViewById(R.id.resumenNotaC);
            TextView linkC = (TextView)findViewById(R.id.linkNotaC);
            final ImageView imagenC = (ImageView)findViewById(R.id.imagenArti_NotaC);
            Drawable tempimg = null;
            try {
                tituloC.setText(listaNot.get(0).getTitulo());
                fechaC.setText(listaNot.get(0).getFecha());
                //linkC.setText(_LINK);
                linkC.setText(Html.fromHtml("<a href=" + _LINK + ">"+_LINK+"...</a>"));
                linkC.setMovementMethod(LinkMovementMethod.getInstance());
                if(listaNot.get(0).getFoto()!=null){
                    imageLoader.displayImage(listaNot.get(0).getFoto(), imagenC, defaultOptions, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            //progressDialog.dismiss();
                            progressBar.setVisibility(View.INVISIBLE);

                        }}, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {

                        }
                    });

                    //imagenC.setImageDrawable(procesosjsoup.dameDrawabledeURL(listaNot.get(0).getFoto()));
                    Log.i("Intentamos poner imagen",listaNot.get(0).getFoto());
                    imagenC.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("Que shingaos click", "Entered onClick method");
                            Intent intent = new Intent(Carga_nota_completa.this, Carga_ImagenComp.class);
                            intent.putExtra("_IMG", listaNot.get(0).getFoto());
                            startActivity(intent);
                        }
                    });
                    //tempimg = procesosjsoup.dameDrawabledeURL(listaNot.get(0).getFoto());
                    resumenC.setCompoundDrawablesWithIntrinsicBounds( null, tempimg, null, null );
                    resumenC.setText(Html.fromHtml(listaNot.get(0).getResumen()));


                    /** Setting a share intent */
                    share_titulo=(listaNot.get(0).getTitulo());
                    share_todo=(listaNot.get(0).getTitulo()+"\n\n"+
                            listaNot.get(0).getFecha()+"\n\n"+
                            resumenC.getText()+"\n\n"+
                            _LINK);
                    //poner en la barra la parte de compartir de api 14 para adelante
                    mShareActionProvider.setShareIntent(getDefaultShareIntent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /** Inflating the current activity's menu with res/menu/items.xml */
        getMenuInflater().inflate(R.menu.carga_nota_completa, menu);
        /** Getting the actionprovider associated with the menu item whose id is share */
        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_item_carga_nota_completa).getActionProvider();
        return super.onCreateOptionsMenu(menu);
    }
    //cuando precionan el boton back del menu superior
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /** Returns a share intent */
    private Intent getDefaultShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, share_titulo);
        intent.putExtra(Intent.EXTRA_TEXT,share_todo);
        return intent;
    }

}
