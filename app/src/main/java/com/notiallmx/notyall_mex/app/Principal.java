package com.notiallmx.notyall_mex.app;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.notiallmx.notyall_mex.app.adapters.Dominio_Adapter;
import com.notiallmx.notyall_mex.app.async.Version;
import com.notiallmx.notyall_mex.app.grammars.Dominio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class Principal extends Activity {
    Button actualizacion;
    ListView lista ;
    private String ultima_version;
    private String status;
    private Date version_inicial,version_actual,version_configurada;
    private ArrayList<Dominio> availableDominios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //cargamos la configuracion
        SharedPreferences prefs = getSharedPreferences("ConfiguracionNotyAll", Activity.MODE_PRIVATE);
        //verificamos que exista la cadena "Version" y si no la igualamos a null
        String version = prefs.getString("Version", null);
        //formato para comparar las fechas de version
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        //Si es la primera ves que se usa la app se pide la configuracion y version
        if(version==null){
            try {

                //le pedimos que nos regresa una cadena con la fecha para que se ejecuten en orden
                //y la otra con status de exito o fracaso al pedir las gramaticas
                ultima_version =new Version(Principal.this,"test").execute().get();
                status = new Gramaticas(Principal.this,"test").execute().get();

                String Dominios = prefs.getString("Dominios","no configurados");
                Log.i("Status de configuracion", status);
                Log.i("Dominios Configurados", "correcto");

                Log.e("fecha de version",ultima_version);
                version_inicial = formatter.parse(ultima_version);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else{
            //si hay una version configurada pedimos cual es la version actual
            try {
                ultima_version =new Version(Principal.this,"test").execute().get();

                Log.i("fecha de Version actual",ultima_version);
                version_actual = formatter.parse(ultima_version);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                // convertimos la shared preference a Date
                JSONArray arreglo_version = new JSONArray(version);

                for (int i = 0; i < arreglo_version.length(); i++) {
                    JSONObject evobject = arreglo_version.getJSONObject(i);
                    int id_version=evobject.getInt("id_version");
                    String date_version=evobject.getString("date_version");
                    Log.i("fecha de version configurada",date_version);
                    version_configurada = formatter.parse(date_version);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //y las comparamos para ver si necesita pedir las gramaticas o esta actualizado
            if(version_actual.after(version_configurada)){
                try {
                    status =new Gramaticas(Principal.this,"test").execute().get();
                    Log.i("version", "Lanzando actualización");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            if(version_actual.equals(version_configurada)){
                Log.i("version", "La version actual es la mas nueva");
                status="success";
            }

            //traemos preferencias
            String Dominiostmp = prefs.getString("Dominios", "no configurados");
            Log.i("Dominios Configurados", Dominiostmp);
            //creamos ArregloJson
            JSONArray DominiosArray = null;
            try {
                //pasamos las preferencias a arreglojson
                DominiosArray = new JSONArray(Dominiostmp);

                //en lugar de string lo maneamos como lista de Objetos 'Dominio' para poder pedirle la url, indice , etc..
                availableDominios= new ArrayList<Dominio>();
                //for que carga dominios en variable Dominios

                for (int i = 0; i < DominiosArray.length(); i++) {
                    JSONObject evobject = DominiosArray.getJSONObject(i);
                    Dominio dom= new Dominio(evobject.getInt("id_sitio"),evobject.getString("url_sitio"),evobject.getString("nombre_sitio"));
                    availableDominios.add(dom);
                }
                // adaptador para cargar la lista en activity
                Dominio_Adapter dominios = new Dominio_Adapter(this, availableDominios);
                //asignamos a variable lista el componente grafico lista_load_not
                lista = (ListView) findViewById(R.id.lista_Load_not);
                //cargamos adaptador a lista interface
                lista.setAdapter(dominios);
                Log.e("Dominios Configurados", "");

                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                        int id_seccion = ((Dominio) adapter.getAdapter().getItem(
                                position)).id;
                        String name_seccion=((Dominio) adapter.getAdapter().getItem(position)).name;
                        Intent intent = new Intent(Principal.this, Carga_secciones.class);
                        intent.putExtra("_INDICE", id_seccion);
                        intent.putExtra("_NOMBRE", name_seccion);
                        startActivity(intent);
                        Log.i("Iniciando actividad", "Iniciando actividad");
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("Status de configuracion",status);

        }

        actualizacion = (Button) findViewById(R.id.button_update);
        actualizacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new Gramaticas(Principal.this,"test").execute();
            }
        });
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.load__noticieros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}

