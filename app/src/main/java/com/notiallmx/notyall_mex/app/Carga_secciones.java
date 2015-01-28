package com.notiallmx.notyall_mex.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.notiallmx.notyall_mex.app.adapters.Seccion_Adapter;
import com.notiallmx.notyall_mex.app.grammars.Seccion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Carga_secciones extends Activity {
    String _NOMBRE="";
     int _INDICE=0;
    private ListView listSecciones ;
    ArrayList<Seccion> availableSecciones;
    private Seccion_Adapter secAdapter;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_secciones);
        Bundle bundle = getIntent().getExtras();//sacamos variables
        _NOMBRE=bundle.getString("_NOMBRE");//sacamos variables
        _INDICE=bundle.getInt("_INDICE");//sacamos variables
        setTitle(_NOMBRE);
        new Cargar_secciones(this,"test").execute();
        //activar el boton atras en la actionbar
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    class Cargar_secciones extends AsyncTask<Void, Void, String> {
        private Activity context;
        private ProgressDialog progressDialog;
        Cargar_secciones(Activity context, String test) {
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
            availableSecciones=new ArrayList<Seccion>();
            listSecciones = (ListView) findViewById(R.id.lista_secciones);

            //cargamos la configuracion
            SharedPreferences prefs = getSharedPreferences("ConfiguracionNotyAll", Activity.MODE_PRIVATE);
            final String Secciones=prefs.getString("Secciones","");
            Log.e("Sacando Secciones",Secciones);

            try {
                JSONArray arreglo_secciones = new JSONArray(Secciones);

                for (int i = 0; i < arreglo_secciones.length(); i++) {
                    JSONObject evobject = arreglo_secciones.getJSONObject(i);
                    Log.e("Seccion",evobject.toString());
                    int id_dominio=evobject.getInt("id_sitio");

                    if(id_dominio==_INDICE){
                        Seccion seccion=new Seccion(evobject.getInt("id_categoria"),evobject.getInt("id_sitio"),evobject.getString("url_categoria"),evobject.getString("nombre_categoria"));
                        availableSecciones.add(seccion);
                        Log.e("seccion agregada",seccion.toString());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return response;
        }

        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            progressDialog.dismiss();
            secAdapter = new Seccion_Adapter(context, availableSecciones);

            listSecciones.setAdapter(secAdapter);

            listSecciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    int id_seccion = ((Seccion) adapterView.getAdapter().getItem(
                            position)).id;
                    String name_seccion=((Seccion) adapterView.getAdapter().getItem(position)).name;
                    String link=((Seccion) adapterView.getAdapter().getItem(position)).url;
                    Intent intent = new Intent(Carga_secciones.this, Carga_notas_seccion.class);
                    intent.putExtra("_INDICE", id_seccion);
                    intent.putExtra("_NOMBRE", name_seccion);
                    intent.putExtra("_LINK", link);
                    Log.e("Indice de seccion", Integer.toString(id_seccion));
                    Log.e("Nombre de seccion", name_seccion);
                    Log.e("Link de seccion", link);
                    startActivity(intent);
                    Log.i("Iniciando actividad", "Iniciando actividad");


                }
            });
        }
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
}