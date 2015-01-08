package com.notiallmx.notyall_mex.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Excelsior extends Activity {
    private String _LINK="";
    private String _TITULO="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excelsior);
        ListView listaExN = (ListView) findViewById(R.id.listaExN);

        // Defined Array values to show in ListView
        final String[] values = new String[] { "Noticias Relevantes",
                "Noticias Nacional",
                "Noticias Global",
                "Noticias Dinero",
                "Noticias Comunidad",
                "Noticias Adrenalina",
                "Noticias Función",
                "Noticias Hacker",
                "Noticias Expresiones",
                "Noticias Opinión",
                "Noticias Suplementos",
                "Noticias Multimedia"
        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listaExN.setAdapter(adapter);
        listaExN.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // ListView Clicked item index

                switch(position) {
                    case 0:
                        _LINK="http://www.excelsior.com.mx/";
                        _TITULO=values[position];
                        break;
                    case 1:
                        _LINK="http://www.excelsior.com.mx/nacional";
                        _TITULO=values[position];
                        break;
                    case 2:
                        _LINK="http://www.excelsior.com.mx/global";
                        _TITULO=values[position];
                        break;
                    case 3:
                        _LINK="http://www.excelsior.com.mx/dineroenimagen";
                        _TITULO=values[position];
                        break;
                    case 4:
                        _LINK="http://www.excelsior.com.mx/comunidad";
                        _TITULO=values[position];
                        break;
                    case 5:
                        _LINK="http://www.excelsior.com.mx/adrenalina";
                        _TITULO=values[position];
                        break;
                    case 6:
                        _LINK="http://www.excelsior.com.mx/funcion";
                        _TITULO=values[position];
                        break;
                    case 7:
                        _LINK="http://www.excelsior.com.mx/hacker";
                        _TITULO=values[position];
                        break;
                    case 8:
                        _LINK="http://www.excelsior.com.mx/expresiones";
                        _TITULO=values[position];
                        break;
                    case 9:
                        _LINK="http://www.excelsior.com.mx/opinion";
                        _TITULO=values[position];
                        break;
                    case 10:
                        _LINK="http://www.excelsior.com.mx/suplementos";
                        _TITULO=values[position];
                        break;
                    case 11:
                        _LINK="http://www.excelsior.com.mx/multimedia/videos";
                        _TITULO=values[position];
                        break;
                }
                Intent intent;
                intent = new Intent(Excelsior.this, Excelsior_NotRel.class);
                Log.e("link",_LINK);
                Log.e("titulo", _TITULO);
                intent.putExtra("_LINK", _LINK);
                intent.putExtra("_TITULO", _TITULO);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.excelsior, menu);
        return true;
    }

}

