package com.notiallmx.notyall_mex.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.notiallmx.notyall_mex.app.R;
import com.notiallmx.notyall_mex.app.grammars.Dominio;

import java.util.ArrayList;

/**
 * Created by pispi & lord on 02/07/2014.
 */


public class Dominio_Adapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Dominio> items;
    private SharedPreferences prefs;

    public Dominio_Adapter(Activity activity, ArrayList<Dominio> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  items.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;
        ViewHolder viewhold;

        if(vi == null|| !(vi.getTag() instanceof ViewHolder)) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.seccion_item, null);
            viewhold=new ViewHolder();
            viewhold.categoria_nombre=(TextView) vi.findViewById(R.id.titulo);

            Dominio dom = items.get(position);

            viewhold.categoria_nombre.setText(dom.name);
        }
        else{
            viewhold = (ViewHolder)vi.getTag();
        }
        return(vi);
    }

}