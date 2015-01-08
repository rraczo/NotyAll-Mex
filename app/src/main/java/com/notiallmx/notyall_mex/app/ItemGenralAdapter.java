package com.notiallmx.notyall_mex.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemGenralAdapter extends BaseAdapter{
	private Activity activity;
	private ArrayList<item_Noticia> items;
	
	public ItemGenralAdapter(Activity activity, ArrayList<item_Noticia> items) {
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
		  
		  View vi=contentView;
	         
	    if(contentView == null) {
	      LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      vi = inflater.inflate(R.layout.list_item_layout, null);
	    }
	             
	    item_Noticia item = items.get(position);
	    	             
	    TextView titulo= (TextView) vi.findViewById(R.id.titulo);
	    titulo.setText(item.getTitulo());
	         
	    TextView fecha= (TextView) vi.findViewById(R.id.fecha);
	    fecha.setText(item.getFecha());
	    
	    TextView resumen= (TextView) vi.findViewById(R.id.resumen);
	    resumen.setText(item.getResumen());
	    
	    TextView link= (TextView) vi.findViewById(R.id.link);
	    link.setText(item.getLink());
	    
	    return vi;
	  }
	
	
	}