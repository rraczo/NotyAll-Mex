package com.notiallmx.notyall_mex.app;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class Excelsior_NotRel extends Activity {
    String _LINK="";//variables que vienen de actividad principal cronica
    String _TITULO="";
    int _ESCOLUMNA=0;
    private List<item_Noticia> listaNot = new ArrayList<item_Noticia>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronica__not_rel);
        Bundle bundle = getIntent().getExtras();//sacamos variables
        _LINK=bundle.getString("_LINK");//sacamos variables
        _TITULO=bundle.getString("_TITULO");//sacamos variables
        //_ESCOLUMNA=bundle.getInt("_ESCOLUMNA");//sacamos variables
        setTitle(_TITULO);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        damenotas();//creamos hilo para realizar peticiones http
    }

    protected void damenotas() {
        new Thread() {
            public void run() {//corremos hilo
                String link = _LINK;
                //Log.e("link recivido",link);
                    listaNot=procesosjsoup.excelsior(link);//procesamos pagina

                runOnUiThread(new Runnable() {//cargamos interface aunque no halla terminado peticiones http
                    public void run() {//cuando termine de procesar el html
                        final ListView lv = (ListView)findViewById(R.id.lista_CrRel);//cargamos el listview

                            lv.setAdapter(dameAdaptador());//ponemos adaptador a la lista


                        lv.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                                Intent intent = new Intent(Excelsior_NotRel.this, Cronica_NotComp.class);
                                intent.putExtra("_LINK", listaNot.get(position).getLink());
                                startActivity(intent);
                               // Log.i("Iniciando actividad","Iniciando actividad");
                            }
                        });
                    }
                });
            }

        }.start();
    }

    public ItemGenralAdapter dameAdaptador(){
        ItemGenralAdapter adapter = new ItemGenralAdapter(this, (ArrayList<item_Noticia>) listaNot);
        return adapter;
    }
    public ItemImageAdapter dameAdaptadorconfoto(){
        ItemImageAdapter adapter = new ItemImageAdapter(this, (ArrayList<item_Noticia>) listaNot);
        return adapter;
    }
}

