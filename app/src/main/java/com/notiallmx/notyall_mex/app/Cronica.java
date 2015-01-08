package com.notiallmx.notyall_mex.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Cronica extends Activity {
	final String _LINK="";
	final String _TITULO="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cronica);
	      // Get ListView object from xml
	      ListView listaCrN = (ListView) findViewById(R.id.listaCrN);

		    
		    // Defined Array values to show in ListView
		    final String[] values = new String[] { "Noticias Relevantes",
		    									"Noticias Nacional",
									    		"Noticias Ciudad",
									    		"Noticias Mundo",
											    "Noticias Negocios",
											    "Noticias Espectáculos",
											    "Noticias Deportes",
											    "Noticias Academia",
											    "Noticias Opinión"
											    };
		    
		    // Define a new Adapter
		    // First parameter - Context
		    // Second parameter - Layout for the row
		    // Third parameter - ID of the TextView to which the data is written
		    // Forth - the Array of data
		
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		      android.R.layout.simple_list_item_1, android.R.id.text1, values);
		
		
		    // Assign adapter to ListView
		    listaCrN.setAdapter(adapter); 
		    listaCrN.setOnItemClickListener(new OnItemClickListener() {
		    	@Override
		    	public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		    		// ListView Clicked item index
		    		Intent intent;

		    		switch(position) {
		    		case 0:
		    			intent = new Intent(Cronica.this, Cronica_NotRel.class);
		    			intent.putExtra("_LINK", "http://www.cronica.com.mx/noticias.php");
		    			intent.putExtra("_TITULO", values[position]);
		    			startActivity(intent);
		                
		    			break;
		    		case 1:
		        	    intent = new Intent(Cronica.this, Cronica_NotRel.class);
		        	    intent.putExtra("_LINK", "http://www.cronica.com.mx/seccion.php?seccion=nacional&id=1");
		        	    intent.putExtra("_TITULO", values[position]);
		                startActivity(intent);
		    			break;
		    		case 2:
		        	    intent = new Intent(Cronica.this, Cronica_NotRel.class);
		        	    intent.putExtra("_LINK", "http://www.cronica.com.mx/seccion.php?seccion=ciudad&id=2");
		        	    intent.putExtra("_TITULO", values[position]);
		                startActivity(intent);
		    			break;
		    		case 3:
		        	    intent = new Intent(Cronica.this, Cronica_NotRel.class);
		        	    intent.putExtra("_LINK", "http://www.cronica.com.mx/seccion.php?seccion=mundo&id=3");
		        	    intent.putExtra("_TITULO", values[position]);
		                startActivity(intent);
		    			break;
		    		case 4:
		        	    intent = new Intent(Cronica.this, Cronica_NotRel.class);
		        	    intent.putExtra("_LINK", "http://www.cronica.com.mx/seccion.php?seccion=negocios&id=4");
		        	    intent.putExtra("_TITULO", values[position]);
		                startActivity(intent);
		    			break;
		    		case 5:
		        	    intent = new Intent(Cronica.this, Cronica_NotRel.class);
		        	    intent.putExtra("_LINK", "http://www.cronica.com.mx/seccion.php?seccion=espectaculos&id=23");
		        	    intent.putExtra("_TITULO", values[position]);
		                startActivity(intent);
		    			break;
		    		case 6:
		        	    intent = new Intent(Cronica.this, Cronica_NotRel.class);
		        	    intent.putExtra("_LINK", "http://www.cronica.com.mx/seccion.php?seccion=deportes&id=5");
		        	    intent.putExtra("_TITULO", values[position]);
		                startActivity(intent);
		    			break;
		    		case 7:
		        	    intent = new Intent(Cronica.this, Cronica_NotRel.class);
		        	    intent.putExtra("_LINK", "http://www.cronica.com.mx/seccion.php?seccion=academia&id=22");
		        	    intent.putExtra("_TITULO", values[position]);
		                startActivity(intent);
		    			break;
		    		case 8:
		        	    intent = new Intent(Cronica.this, Cronica_NotRel.class);
		        	    intent.putExtra("_LINK", "http://www.cronica.com.mx/opinion.php");
		        	    intent.putExtra("_TITULO", values[position]);
		        	    intent.putExtra("_ESCOLUMNA", position);
		                startActivity(intent);
		    			break;
		    		}
		    		}
		    	});
	   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
        //BARRA COMPARTIR
		//getMenuInflater().inflate(R.menu.cronica, menu);
		return true;
	}

}
