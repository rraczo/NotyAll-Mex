package com.notiallmx.notyall_mex.app.async;

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

/**
 * Created by lord on 15/01/2015.
 */
public class Version extends AsyncTask<Void, Void, String> {
    private ProgressDialog progressDialog;
    private ArrayList<Dominio> DominiosAvailable;
    private ArrayList<Seccion> SeccionesAvailable;
    private ArrayList<Gramatica> GramaticasAvailable;
    private ArrayList<Completa> CompletasAvailable;
    private HttpClient httpClient;
    private HttpPost httpPost;
    private List<NameValuePair> nameValuePairs;
    private ResponseHandler<String> responseHandler;
    private Activity context;
    boolean error;
    private String prePag="http://www.crisoldeideas.com/notiAllMex/";
    String tipo;
    String test;
    String date_version;

    public Version(Activity context, String test) {
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

        //post para obtener los dominios
        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(prePag+"servicios/version.php");
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("test", test));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            responseHandler = new BasicResponseHandler();
            response = httpClient.execute(httpPost, responseHandler);
            aux= response.toString();

            if (aux != null) {
                Log.i("version.php", "respondio correctamente \n ");
            } else {
                Log.e("error","fallo el servicio version");
            }

            jsonObject = new JSONObject(aux);
            JSONArray version = jsonObject.getJSONArray("notyall");

            for (int i = 0; i < version.length(); i++) {
                JSONObject evobject = version.getJSONObject(i);
                date_version=  evobject.getString("date_version");
            }

            String version_cadena=version.toString();
            SharedPreferences prefs = context.getSharedPreferences("ConfiguracionNotyAll",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Version", version_cadena);
            editor.commit();
        } catch (Exception ex) {
            error = true;
            Log.e("error","proceso version.php ");
            return null;
        }
        return date_version;
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
