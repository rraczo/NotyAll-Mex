package com.notiallmx.notyall_mex.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.notiallmx.notyall_mex.app.async.ImgdeUrl;
import com.notiallmx.notyall_mex.app.R;
import com.notiallmx.notyall_mex.app.objects.item_Noticia;

import java.util.ArrayList;

public class ItemImageAdapter extends BaseAdapter{
	private Activity activity;
	private ArrayList<item_Noticia> items;
	
	public ItemImageAdapter(Activity activity, ArrayList<item_Noticia> items) {
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
		
	    return items.get(position).getId();
	  }
	 
	  @Override
	  public View getView(int position, View contentView, ViewGroup parent) {
		/*
		  View vi=contentView;
	         
	    if(contentView == null) {
	      LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      vi = inflater.inflate(R.layout.list_item_image, null);
	    }
	             
	    item_Noticia item = items.get(position);
	    	             
	    TextView titulo= (TextView) vi.findViewById(R.id.titulo1);
	    titulo.setText(item.getTitulo());
	         
	    ImageView img= (ImageView) vi.findViewById(R.id.imagen1);
	    try {
	    	Log.i("FOTO a Cargar:",item.getFoto());
            //llamamos metodo que visita url y la convierte a objeto drawable
			img.setImageDrawable(procesosjsoup.dameDrawabledeURL(item.getFoto()));
            new ImgdeUrl(img).execute(item.getFoto());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    TextView resumen= (TextView) vi.findViewById(R.id.resumen1);
	    resumen.setText(item.getResumen());
	    
	    TextView link= (TextView) vi.findViewById(R.id.link1);
	    link.setText(item.getLink());
	    
	    return vi;
          */
          ViewHolder holder;
          if (contentView == null) {
              LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              contentView = inflater.inflate(R.layout.list_item_image, null);
              holder = new ViewHolder();
              holder.titulo = (TextView) contentView.findViewById(R.id.titulo1);
              holder.resumen = (TextView) contentView.findViewById(R.id.resumen1);
              holder.link = (TextView) contentView.findViewById(R.id.link1);
              holder.imageView = (ImageView) contentView.findViewById(R.id.imagen1);
              contentView.setTag(holder);
          } else {
              holder = (ViewHolder) contentView.getTag();
          }

          item_Noticia item = items.get(position);

          holder.titulo.setText(item.getTitulo());
          holder.resumen.setText(item.getResumen());
          holder.link.setText(item.getLink());

          if (holder.imageView != null) {
              new ImgdeUrl(holder.imageView).execute(item.getFoto());
          }else{//si la imagen no existe le ponemos un color rascuacho
              holder.imageView.setBackgroundColor(Color.rgb(20,20,20));
          }

          return contentView;
	  }
    static class ViewHolder {
        TextView titulo;
        TextView resumen;
        TextView link;
        ImageView imageView;
    }
	
	
	}