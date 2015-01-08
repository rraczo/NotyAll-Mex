package com.notiallmx.notyall_mex.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pispi & lord on 02/07/2014.
 */
class ViewHolder {
    TextView categoria_nombre;
}

public class Seccion_Adapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Seccion> items;
    private SharedPreferences prefs;

    public Seccion_Adapter(Activity activity, ArrayList<Seccion> items) {
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

            Seccion sec = items.get(position);


            viewhold.categoria_nombre.setText(sec.name);
        }
        else{
            viewhold = (ViewHolder)vi.getTag();
        }
        return(vi);
    }

}