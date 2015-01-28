package com.notiallmx.notyall_mex.app;

/**
 * Created by lord and pispi on 20/06/2014.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.notiallmx.notyall_mex.app.grammars.Completa;
import com.notiallmx.notyall_mex.app.grammars.Dominio;
import com.notiallmx.notyall_mex.app.grammars.Gramatica;
import com.notiallmx.notyall_mex.app.grammars.Seccion;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


class Gramaticas extends AsyncTask<Void, Void, String> {
    private ProgressDialog progressDialog;
    private ArrayList<Dominio> DominiosAvailable;
    private ArrayList<Seccion> SeccionesAvailable;
    private ArrayList<Gramatica> GramaticasAvailable;
    private ArrayList<Completa> CompletasAvailable;
    private HttpClient httpClient;
    private HttpPost httpPost;
    private  List<NameValuePair> nameValuePairs;
    private ResponseHandler<String> responseHandler;
    private Activity context;
    boolean error;
    private String prePag="http://www.crisoldeideas.com/notiAllMex/";
    String tipo;
    String test;

    Gramaticas(Activity context,String test) {

        this.context = context;
        this.test=test;
    }

    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Actualizando, espere por favor...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... arg0) {
        String response;
        String aux;
        JSONObject jsonObject;

        //en estas listas se guardan las respuestas de los servicios
        //y despues se pueden usar para rellenar los listview
        DominiosAvailable = new ArrayList<Dominio>();
        SeccionesAvailable = new ArrayList<Seccion>();
        GramaticasAvailable = new ArrayList<Gramatica>();
        CompletasAvailable = new ArrayList<Completa>();

        //post para obtener los dominios
        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(prePag+"servicios/dominio.php");
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("test", test));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            responseHandler = new BasicResponseHandler();
            response = httpClient.execute(httpPost, responseHandler);
            aux= response.toString();

            if (aux != null) {
                Log.i("dominio.php",aux);
                Log.i("dominio.php","respondio correctamente \n ");
            } else {
                Log.e("error","fallo el servicio dominio");
            }



            //recuperamos la respuesta y la convertimos en arreglo con json
            jsonObject = new JSONObject(aux);
            JSONArray dominios = jsonObject.getJSONArray("notyall");

            //la almacenamos en el cel para convertirlas cuando se ocupen
            String dominios_cadena=dominios.toString();
            SharedPreferences prefs = context.getSharedPreferences("ConfiguracionNotyAll",context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Dominios", dominios_cadena);
            editor.commit();

            //Mas o menos asi se recuperarian para llenar un arraylist
            //y asi cargar los listview
            /*
            for (int i = 0; i < dominios.length(); i++) {
                JSONObject evobject = dominios.getJSONObject(i);
                Dominio dom = new Dominio(evobject.getInt("id_sitio"),evobject.getString("url_sitio"),evobject.getString("nombre_sitio"));
                DominiosAvailable.add(dom);
            }
            */
        } catch (Exception ex) {
            error = true;
            Log.e("error",ex.toString());
        }

        //post para obtener las secciones

        try {
            httpClient = new DefaultHttpClient();

            httpPost = new HttpPost(prePag+"servicios/seccion.php");
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("test", test));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            responseHandler = new BasicResponseHandler();
            response = httpClient.execute(httpPost, responseHandler);
            aux= response.toString();
            if (aux != null) {
                Log.i("seccion.php","respondio correctamente \n ");
                Log.i("seccion.php",aux);
            } else {
                Log.e("error","fallo el servicio seccion");
            }

            jsonObject = new JSONObject(aux);
            JSONArray secciones = jsonObject.getJSONArray("notyall");

            String secciones_cadena=secciones.toString();
            SharedPreferences prefs = context.getSharedPreferences("ConfiguracionNotyAll",context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Secciones", secciones_cadena);
            editor.commit();
            /*
            for (int i = 0; i < dominios.length(); i++) {
                JSONObject evobject = dominios.getJSONObject(i);
                Seccion sec = new Seccion(evobject.getInt("id_categoria"),evobject.getInt("id_sitio"),evobject.getString("url_categroia"),evobject.getString("nombre_categoria"));
                SeccionesAvailable.add(sec);
            }
            */
        } catch (Exception ex) {
            error = true;
            Log.e("error",ex.toString());
        }

        //post para obtener las gramaticas
        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(prePag+"servicios/gramaticas.php");
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("test", test));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            responseHandler = new BasicResponseHandler();
            response = httpClient.execute(httpPost, responseHandler);
            aux= response.toString();

            if (aux != null) {
                Log.i("gramaticas.php","respondio correctamente \n ");
                Log.i("seccion.php",aux);
            } else {
                Log.e("error","fallo el servicio gramaticas");
            }

            jsonObject = new JSONObject(aux);
            JSONArray gramaticas = jsonObject.getJSONArray("notyall");

            String gramaticas_cadena=gramaticas.toString();
            SharedPreferences prefs = context.getSharedPreferences("ConfiguracionNotyAll",context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Gramaticas", gramaticas_cadena);
            editor.commit();
            /*
            for (int i = 0; i < gramaticas.length(); i++) {
                JSONObject evobject = gramaticas.getJSONObject(i);
                Gramatica gra = new Gramatica(evobject.getInt("id_gramatica"),evobject.getInt("id_categoria"),
                        evobject.getString("selector_gramatica"),evobject.getString("elemento_gramatica"),
                        evobject.getString("titulo"),evobject.getString("fecha"),evobject.getString("link"),
                        evobject.getString("descripcion"));
                GramaticasAvailable.add(gra);
            }
            */
        } catch (Exception ex) {
            error = true;
            Log.e("error",ex.toString());
            return "error";
        }

        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(prePag+"servicios/gramatica.php");
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("test", test));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            responseHandler = new BasicResponseHandler();
            response = httpClient.execute(httpPost, responseHandler);
            aux= response.toString();

            if (aux != null) {
                Log.i("gramatica.php","respondio correctamente \n ");
                Log.i("seccion.php",aux);
            } else {
                Log.e("error","fallo el servicio gramatica");
            }

            jsonObject = new JSONObject(aux);
            JSONArray gramaticas = jsonObject.getJSONArray("notyall");

            String gramaticas_cadena=gramaticas.toString();
            SharedPreferences prefs = context.getSharedPreferences("ConfiguracionNotyAll",context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Gramaticas", gramaticas_cadena);
            editor.commit();
            /*
            for (int i = 0; i < gramaticas.length(); i++) {
                JSONObject evobject = gramaticas.getJSONObject(i);
                Gramatica gra = new Gramatica(evobject.getInt("id_gramatica"),evobject.getInt("id_categoria"),
                        evobject.getString("selector_gramatica"),evobject.getString("elemento_gramatica"),
                        evobject.getString("titulo"),evobject.getString("fecha"),evobject.getString("link"),
                        evobject.getString("descripcion"));
                GramaticasAvailable.add(gra);
            }
            */
        } catch (Exception ex) {
            error = true;
            Log.e("error",ex.toString());
            return "error";
        }

        return "Success";
    }

    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        progressDialog.hide();

        if (!error) {
            Log.d("configuración","Configuración actualizada correctamente");
        } else {
            Log.d("configuración","Error al actualizar la configuración");
        }
        progressDialog.dismiss();
    }

}

