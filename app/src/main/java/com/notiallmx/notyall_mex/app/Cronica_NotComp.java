package com.notiallmx.notyall_mex.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;




public class Cronica_NotComp extends Activity {
	List<item_Noticia> listaNot = new ArrayList<item_Noticia>();
	String _LINK="";
    String share_titulo,share_todo;

    //	para el menu compartir
    private ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_cronica__not_comp);
		Bundle bundle = getIntent().getExtras();//sacamos variables
		_LINK=bundle.getString("_LINK");//sacamos variables
		StrictMode.ThreadPolicy cron=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(cron);
		damenotas();

	}
	protected void damenotas() {
	    new Thread() {
	            public void run() {//corremos hilo
	            	Log.i("Todo bien despues de run","");
	            	listaNot = procesosjsoup.cronica_Notacompleta(_LINK);//
	            	Log.i("despues de llamar a cronica notacompleta","");
	                runOnUiThread(new Runnable() {//cargamos interface aunque no halla terminado peticiones http
	                	public void run() {//cuando termine de procesar el html
	                		TextView tituloC = (TextView)findViewById(R.id.tituloC);
	                		TextView fechaC = (TextView)findViewById(R.id.fechaC);
	                		TextView resumenC = (TextView)findViewById(R.id.resumenC);
	                		TextView linkC = (TextView)findViewById(R.id.linkC);
                            ImageView imagenC = (ImageView)findViewById(R.id.imagenArti);
	                		tituloC.setText(listaNot.get(0).getTitulo());
	                		fechaC.setText(listaNot.get(0).getFecha());
	                			                		
	                		Drawable tempimg = null;
							try {
								//tempimg = procesosjsoup.dameDrawabledeURL(listaNot.get(0).getFoto());
                                imagenC.setImageDrawable(procesosjsoup.dameDrawabledeURL(listaNot.get(0).getFoto()));
								//Log.i("Intentamos poner imagen",listaNot.get(0).getFoto());
                                imagenC.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.i("Que shingaos click","Entered onClick method");
                                        Intent intent = new Intent(Cronica_NotComp.this, Cronica_ImagenComp.class);
                                        intent.putExtra("_IMG", listaNot.get(0).getFoto());
                                        startActivity(intent);
                                    }});

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							resumenC.setCompoundDrawablesWithIntrinsicBounds( null, tempimg, null, null );
							resumenC.setText(Html.fromHtml(listaNot.get(0).getResumen()));
	                		
	                		//resumenC.setText(listaNot.get(0).getResumen());//cargamos texto dentro del scroll
	                		
	                		//linkC.setText(_LINK);
                            linkC.setText(Html.fromHtml("<a href=" + _LINK + ">"+_LINK+"...</a>"));
                            linkC.setMovementMethod(LinkMovementMethod.getInstance());


                            /** Setting a share intent */
                            share_titulo=(listaNot.get(0).getTitulo());
                            share_todo=(listaNot.get(0).getTitulo()+"\n\n"+
                                    listaNot.get(0).getFecha()+"\n\n"+
                                    listaNot.get(0).getResumen()+"\n\n"+
                                    _LINK);
                            mShareActionProvider.setShareIntent(getDefaultShareIntent());
	                			   }
	                			});
	                		
	                	
	                }
	            }.start();
	            }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /** Inflating the current activity's menu with res/menu/items.xml */
        getMenuInflater().inflate(R.menu.cronica_not_comp, menu);

        /** Getting the actionprovider associated with the menu item whose id is share */
        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_item_share).getActionProvider();



        return super.onCreateOptionsMenu(menu);

    }

    /** Returns a share intent */
    private Intent getDefaultShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, share_titulo);
        intent.putExtra(Intent.EXTRA_TEXT,share_todo);
        return intent;
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cronica_not_comp, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);
        myShareActionProvider = (ShareActionProvider)item.getActionProvider();
        myShareActionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        myShareActionProvider.setShareIntent(createShareIntent());
        return true;
    }
    private Intent createShareIntent() {
        String tit=listaNot.get(0).getTitulo();
        String res =listaNot.get(0).getResumen();
        String fot =listaNot.get(0).getFoto();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.putExtra(Intent.EXTRA_EMAIL, tit);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, tit);
        shareIntent.putExtra(Intent.EXTRA_TEXT, res);

        if(fot != null){
            shareIntent.putExtra(Intent.EXTRA_STREAM, fot);
            shareIntent.setType("image/png");
        }else{
            shareIntent.setType("plain/text");
        }

        return shareIntent;
    }

    private void setShareIntent(Intent shareIntent) {
        if (myShareActionProvider != null) {
            myShareActionProvider.setShareIntent(shareIntent);
        }
    }*/

}
