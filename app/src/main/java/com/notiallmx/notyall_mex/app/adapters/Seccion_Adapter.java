package com.notiallmx.notyall_mex.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.notiallmx.notyall_mex.app.R;
import com.notiallmx.notyall_mex.app.grammars.Seccion;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lord on 15/01/2015.
 */

class ViewHolder {
    TextView categoria_nombre;
    ImageView categoria_icono;
}
public class Seccion_Adapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Seccion> items;
    private SharedPreferences prefs;
    protected ArrayList<String> academia=new ArrayList<String>(Arrays.asList("academia", "educacion", "académia","educación"));
    protected ArrayList<String> bienestar=new ArrayList<String>(Arrays.asList("bienestar"));
    protected ArrayList<String> capital=new ArrayList<String>(Arrays.asList("capital","d.f.","distrito federal","ciudad de méxico", "ciudad de méxico"));
    protected ArrayList<String> ciencias=new ArrayList<String>(Arrays.asList("ciencia","ciencias","tecnologias","tecnologia","tecnología","tecnologías","ciencia y tecnologia","ciencia y tecnologia"));
    protected ArrayList<String> ciudad=new ArrayList<String>(Arrays.asList("ciudad"));
    protected ArrayList<String> comunidad=new ArrayList<String>(Arrays.asList("comunidad"));
    protected ArrayList<String> cultura=new ArrayList<String>(Arrays.asList("cultura","arte","artes","arte y cultura","expresiones"));
    protected ArrayList<String> data=new ArrayList<String>(Arrays.asList("data","hacker","informatica","informática","computacion","computación"));
    protected ArrayList<String> deportes=new ArrayList<String>(Arrays.asList("deportes"));
    protected ArrayList<String> economia=new ArrayList<String>(Arrays.asList("economia", "economía", "dinero","bolsa","valoes"));
    protected ArrayList<String> ediorial=new ArrayList<String>(Arrays.asList("editorial","opinion","opinión"));
    protected ArrayList<String> edomex=new ArrayList<String>(Arrays.asList("estado de mexico","estado de méxico","edo. de mexico","edo. de méxico"));
    protected ArrayList<String> emprendedor=new ArrayList<String>(Arrays.asList("emprendedor"));
    protected ArrayList<String> estados=new ArrayList<String>(Arrays.asList("estados"));
    protected ArrayList<String> estilo=new ArrayList<String>(Arrays.asList("estilo","moda","moda y estilo"));
    protected ArrayList<String> fotografia=new ArrayList<String>(Arrays.asList("fotografia","fotografía"));
    protected ArrayList<String> inicio=new ArrayList<String>(Arrays.asList("inicio","portada","home"));
    protected ArrayList<String> internacional=new ArrayList<String>(Arrays.asList("internacional","global","mundo"));
    protected ArrayList<String> metropoli=new ArrayList<String>(Arrays.asList("metropoli","urbe"));
    protected ArrayList<String> multimedia=new ArrayList<String>(Arrays.asList("media","multimedia","galeria","fotos","videos"));
    protected ArrayList<String> nacion=new ArrayList<String>(Arrays.asList("nacion", "nacional", "nacionales","pais"));
    protected ArrayList<String> negocios=new ArrayList<String>(Arrays.asList("negocios"));
    protected ArrayList<String> politica=new ArrayList<String>(Arrays.asList("politica","política"));
    protected ArrayList<String> redpolitica=new ArrayList<String>(Arrays.asList("redpolitica","red politica","red política"));
    protected ArrayList<String> seguridad=new ArrayList<String>(Arrays.asList("seguridad","justicia","derecho"));
    protected ArrayList<String> sociedad=new ArrayList<String>(Arrays.asList("sociedad"));
    protected ArrayList<String> tv=new ArrayList<String>(Arrays.asList("tv","television","cine","espectaculos","espectáculos","espectaculo","espectáculo"));


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
            viewhold.categoria_nombre=(TextView) vi.findViewById(R.id.item_titulo);
            viewhold.categoria_icono=(ImageView) vi.findViewById(R.id.item_image);

            Seccion sec = items.get(position);

            viewhold.categoria_nombre.setText(sec.name);
            Drawable icono;
            String nameLowerCase=sec.name.toLowerCase();
            icono=activity.getResources().getDrawable(R.drawable.inicio);
            if (academia.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.academia);
            }
            if (bienestar.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.bienestar);
            }
            if (capital.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.capital);
            }
            if (ciencias.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.ciencias);
            }
            if (ciudad.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.ciudad);
            }
            if (comunidad.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.comunidad);
            }
            if (cultura.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.cultura);
            }
            if (data.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.data);
            }
            if (deportes.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.deportes);
            }
            if (economia.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.economia);
            }
            if (ediorial.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.editorial);
            }
            if (ciudad.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.ciudad);
            }
            if (edomex.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.edomex);
            }
            if (emprendedor.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.emprendedor);
            }
            if (estados.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.estados);
            }
            if (estilo.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.estilo);
            }
            if (fotografia.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.fotografia);
            }
            if (inicio.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.inicio);
            }
            if (internacional.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.internacional);
            }
            if (multimedia.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.multimedia);
            }
            if (nacion.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.nacion);
            }
            if (negocios.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.negocios);
            }
            if (politica.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.polittica);
            }
            if (redpolitica.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.redpolitica);
            }
            if (seguridad.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.seguridad);
            }
            if (sociedad.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.sociedad);
            }
            if (tv.contains(nameLowerCase)) {
                icono=activity.getResources().getDrawable(R.drawable.tv);
            }
            viewhold.categoria_icono.setImageDrawable(icono);

        }
        else{
            viewhold = (ViewHolder)vi.getTag();
        }
        return(vi);
    }

}
