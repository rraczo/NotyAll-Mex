package com.notiallmx.notyall_mex.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Carga_notas_seccion extends ActionBarActivity {
    String _NOMBRE="";
    int _INDICE=0;
    String _LINK="";
    JSONObject _GRAMATIC=null;
    private List<item_Noticia> listaNot = new ArrayList<item_Noticia>();
    JSONObject evobject;

    private ListView listnotas ;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_notas_seccion);

        Bundle bundle = getIntent().getExtras();//sacamos variables
        _NOMBRE=bundle.getString("_NOMBRE");//sacamos variables
        _INDICE=bundle.getInt("_INDICE");//sacamos variables
        _LINK=bundle.getString("_LINK");//sacamos variables
        setTitle(_NOMBRE);

        Log.e("NOMBRE",_INDICE+"--"+_NOMBRE+"--"+_LINK);

        new Cargar_resumen_nota(this,"test").execute();
    }
    class Cargar_resumen_nota extends AsyncTask<Void, Void, String> {
        private Activity context;

        Cargar_resumen_nota(Activity context, String test) {
            this.context = context;
        }

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Cargando, espere por favor...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            //aqui el pedo
        }
        @Override
        protected String doInBackground(Void... arg0) {
            String response=null;
            listnotas = (ListView) findViewById(R.id.lista_notas_seccion);
            //cargamos la configuracion
            SharedPreferences prefs = getSharedPreferences("ConfiguracionNotyAll", Activity.MODE_PRIVATE);
            final String gramaticas=prefs.getString("Gramaticas","");
            Log.e("Sacando Gramaticas", gramaticas);
            JSONObject gramaticaCorrecta_tmp = null;
            try {
                JSONArray arreglo_gramaticas = new JSONArray(gramaticas);
                for (int i = 0; i < arreglo_gramaticas.length(); i++) {
                    evobject = arreglo_gramaticas.getJSONObject(i);
                    int id_categoria=evobject.getInt("id_categoria");
                    if(id_categoria==_INDICE){
                        Log.e("Sacamos seccion correcta",evobject.toString());
                        gramaticaCorrecta_tmp=evobject;
                        _GRAMATIC=evobject;                    }
                }
                //damenotas(gramaticaCorrecta_tmp);
                String link = _LINK;
                listaNot=procesosjsoup.SacaNotasResumen(link,gramaticaCorrecta_tmp);//procesamos pagina
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            final ListView lv = (ListView)findViewById(R.id.lista_notas_seccion);//cargamos el listview

            lv.setAdapter(dameAdaptador());//ponemos adaptador a la lista
            Log.e("Realizamos adaptador","Completo");

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                    Intent intent = new Intent(Carga_notas_seccion.this, Carga_nota_completa.class);
                    intent.putExtra("_LINK", listaNot.get(position).getLink());
                    intent.putExtra("_TITULO", listaNot.get(position).getTitulo());
                    intent.putExtra("_GRAMATIC", _GRAMATIC.toString());

                    startActivity(intent);}});
        }
    }

    public ItemGenralAdapter dameAdaptador(){
        ItemGenralAdapter adapter = new ItemGenralAdapter(this, (ArrayList<item_Noticia>) listaNot);
        return adapter;
    }
}
