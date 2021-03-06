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
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.notiallmx.notyall_mex.app.adapters.ViewPagerAdapter;
import com.notiallmx.notyall_mex.app.objects.item_Noticia;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Carga_nota_completa extends android.support.v4.app.FragmentActivity {
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
    private String _IMGURI;

    private ViewPager viewPager;
    private LinearLayout mainLayout;
    private View cell;
    private String images[];
    private int h,w;

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
            //final ImageView imagenC = (ImageView)findViewById(R.id.imagenArti_NotaC);
            Drawable tempimg = null;


            try {
                tituloC.setText(listaNot.get(0).getTitulo());
                fechaC.setText(listaNot.get(0).getFecha());
                //linkC.setText(_LINK);
                linkC.setText(Html.fromHtml("<a href=" + _LINK + ">"+_LINK+"...</a>"));
                linkC.setMovementMethod(LinkMovementMethod.getInstance());


               /** Intento con el archivo Gallery Page Adapter**/
                /*viewPager = (ViewPager) findViewById(R.id._viewPager);
                mainLayout = (LinearLayout) findViewById(R.id._linearLayout);
                images = listaNot.get(0).getFoto().split(",");

                for (int j = 0; j < images.length; j++) {
                    Log.e("url de imagen para pager",images[j]);
                    cell = getLayoutInflater().inflate(R.layout.cell, null);
                    final ImageView imageView = (ImageView) cell.findViewById(R.id._image);
                    viewPager.setVisibility(View.VISIBLE);
                    viewPager.setAdapter(new GalleryPageAdapter(Carga_nota_completa.this, images, w, h));
                    imageView.setId(j);
                    mainLayout.addView(cell);
                }
                viewPager.setCurrentItem(0);
                viewPager.setVisibility(View.VISIBLE);
                */
                /** Intento con el archivo View Page Adapter**/
                images = listaNot.get(0).getFoto().split(",");
                viewPager = (ViewPager) findViewById(R.id.pager);
                // Pass results to ViewPagerAdapter Class
                ViewPagerAdapter adapter = new ViewPagerAdapter(Carga_nota_completa.this,images);
                // Binds the Adapter to the ViewPager
                viewPager.setAdapter(adapter);

                CirclePageIndicator indicador = (CirclePageIndicator) findViewById(R.id.indicator);
                indicador.setViewPager(viewPager);

                if(listaNot.get(0).getFoto()!=null){

                    /*imageLoader.displayImage(listaNot.get(0).getFoto(), imagenC, defaultOptions, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            //progressDialog.dismiss();
                            progressBar.setVisibility(View.INVISIBLE);
                            _IMGURI=guardaimagen(loadedImage);

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
                            intent.putExtra("_IMGURI", _IMGURI.toString());
                            startActivity(intent);
                        }
                    });
                    */
                    //tempimg = procesosjsoup.dameDrawabledeURL(listaNot.get(0).getFoto());
                    //resumenC.setCompoundDrawablesWithIntrinsicBounds( null, tempimg, null, null );
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
public String guardaimagen(Bitmap bitmap){
    File pictureStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    File noMedia = new File(pictureStorage, ".nomedia");
    if (!noMedia.exists())
        noMedia.mkdirs();

    File file = new File(noMedia, "Notyall-notaTemp.png");

    try {
        FileOutputStream fOut = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG,100,fOut);
        fOut.flush();
        fOut.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return file.getAbsolutePath();
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
