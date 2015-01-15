package com.notiallmx.notyall_mex.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_nota_completa);
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
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Actualizando, espere por favor...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
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
            progressDialog.dismiss();
            TextView tituloC = (TextView)findViewById(R.id.tituloNotaC);
            TextView fechaC = (TextView)findViewById(R.id.fecha_NotaC);
            TextView resumenC = (TextView)findViewById(R.id.resumenNotaC);
            TextView linkC = (TextView)findViewById(R.id.linkNotaC);
            ImageView imagenC = (ImageView)findViewById(R.id.imagenArti_NotaC);
            Drawable tempimg = null;
            try {
                tituloC.setText(listaNot.get(0).getTitulo());
                fechaC.setText(listaNot.get(0).getFecha());
                //linkC.setText(_LINK);
                linkC.setText(Html.fromHtml("<a href=" + _LINK + ">"+_LINK+"...</a>"));
                linkC.setMovementMethod(LinkMovementMethod.getInstance());
                if(listaNot.get(0).getFoto()!=null){
                    imagenC.setImageDrawable(procesosjsoup.dameDrawabledeURL(listaNot.get(0).getFoto()));
                    Log.i("Intentamos poner imagen",listaNot.get(0).getFoto());
                    imagenC.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("Que shingaos click", "Entered onClick method");
                            Intent intent = new Intent(Carga_nota_completa.this, Cronica_ImagenComp.class);
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

    /** Returns a share intent */
    private Intent getDefaultShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, share_titulo);
        intent.putExtra(Intent.EXTRA_TEXT,share_todo);
        return intent;
    }

}
