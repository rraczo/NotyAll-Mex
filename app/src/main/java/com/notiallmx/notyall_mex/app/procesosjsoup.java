package com.notiallmx.notyall_mex.app;


import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.notiallmx.notyall_mex.app.objects.item_Noticia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;




public class procesosjsoup {

	public static List<item_Noticia> cronica(String link){
		
        	//coluno coldos
        	//http://www.cronica.com.mx/noticias.php
			List<item_Noticia> listaNot = new ArrayList<item_Noticia>();
			org.jsoup.nodes.Document doc;
			try {
				InputStream is= new URL(link).openStream();         
                doc = org.jsoup.Jsoup.parse(is , "utf-8", link);            
               //org.jsoup.nodes.Document doc = Jsoup.connect(link).get();
              
                Elements columna1 = doc.select("div.trescolumnas");
                Elements columna2 = doc.select("div.cuatrocolumnas");
                String titulo,fecha,resumen,link1;
              // Elements relevante = columna1.select("div.destacado");
               int donitera=0;
               for (Element itera : columna1.select("div.nota")) {
              	  	                    	 
              	 link1="http://www.cronica.com.mx/"+itera.select("a").attr("href");
              	 titulo=itera.select("a > div.titulo").text()+
              			 itera.select("div.titulo > a").text()+
              			 itera.select("a > div.titulo_principal").text();
              	fecha=itera.select("div.notita > span.fecha").text()+"|"+itera.select("div.notita > span.hora").text()+"|";
                  resumen=itera.select("div.notita > span.texto").text();
                  listaNot.add(new item_Noticia(donitera, titulo,fecha,resumen,link1));
                  donitera++;
                  }
               for (Element itera : columna2.select("div.notita")) {
               	 
              	 link1="http://www.cronica.com.mx/"+itera.select("a").attr("href");
              	 titulo=itera.select("div.tituloPa > a").text()+
              			 itera.select("div.titulo > a").text()+
              			 itera.select("a > div.titulo_principal").text();
              	fecha=itera.select("div.notita > span.fecha").text()+"|"+itera.select("div.notita > span.hora").text()+"|";
                  resumen=itera.select("div.notita > span.texto").text();
                  listaNot.add(new item_Noticia(donitera, titulo,fecha,resumen,link1));
                  donitera++;
               }
                          
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return listaNot;
		  			
	}

	public static List<item_Noticia> cronica_Notacompleta(String link){
		List<item_Noticia> listaNot = new ArrayList<item_Noticia>();
		Log.i("variable list","iniciadndo");
		org.jsoup.nodes.Document doc;
		Log.i("variable doc","iniciadndo");
		
		try {
			InputStream is= new URL(link).openStream();
			Log.i("stream",link);
            doc = org.jsoup.Jsoup.parse(is , "utf-8", link);
            Elements columna1 = doc.select("div.colunoNo");
            String titulo,fecha,resumen="",link1,img;
            
            int donitera=0;
            link1="http://www.cronica.com.mx/"+columna1.select("a").attr("href");
          	titulo=columna1.select("div.titulo_nota").text();
          	fecha=columna1.select("span:nth-child(5)").text();
          	
             resumen=columna1.select("div.notitaNo > span.texto").html();
          	// Elements parrafo=columna1.select("div.notitaNo > span.texto > p");
          	
          	 //for (Element p : parrafo){resumen+=p.text()+"\n";}//agregando salto de renglon
          	
          	img="http://www.cronica.com.mx/"+columna1.select("div.img_nota > img").attr("src").replace("../", "");
          	Log.i("agregamos img a lista",img);
          	 listaNot.add(new item_Noticia(donitera, titulo,fecha,resumen,link1,img));
          	Log.i("agregamos en lista not","iniciadndo");
       
         
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaNot;
		
	}
	public static List<item_Noticia> cronica_columnas(String link){
		List<item_Noticia> listaNot = new ArrayList<item_Noticia>();
		org.jsoup.nodes.Document doc;
				
		try {
			InputStream is= new URL(link).openStream();
			doc = org.jsoup.Jsoup.parse(is , "utf-8", link);			
            Elements columnas = doc.select("div.itemOpinion > div.contenedorItem");
            String titulo,fecha,resumen="",link1,img;
            int donitera=0;
            for (Element itera : columnas) {
            	link1="http://www.cronica.com.mx/"+itera.select("a").attr("href");
             	titulo=itera.select("span.tituloPa > a").text();
             	resumen=itera.select("span.texto").text();
                 img="http://www.cronica.com.mx/"+itera.select("a > img").attr("src").replace("../", "");
                 Log.i("Mete img en aca",img);
                 listaNot.add(new item_Noticia(donitera, titulo,"",resumen,link1,img));
                 donitera++;
                 }
          	
         
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaNot;
	}
	
    public static Drawable dameDrawabledeURL(String lnk) throws Exception {
    	return Drawable.createFromStream((InputStream)new URL(lnk).getContent(), "src");
    }
    public static Bitmap dameBitmapdeURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


	public static Bitmap drawableToBitmap (Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable)drawable).getBitmap();
			}
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap); 
	    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    drawable.draw(canvas);
	
	    return bitmap;
	}


    ////Gramaticas de  Excelcior
    public static List<item_Noticia> excelsior(String link){

        //estas son las gramaticas que se usan las secciones del sitio (faltan algunas)
        //esto era lo que te decia, con un servicio php se pide una cadena con todas las gramaticas en json y se recontruyen facil
        //y en lugar de ponerlas aqui separarlas en parametros desde que se escoge la seccion para cargar la url

        String secciones_gramaticas="["+
                "{\"url\":\"http://www.excelsior.com.mx/\",\"selector\":\".block-excelsior-notas-principales>#top-front-artices>div>ul\",\"element\":\"li\",\"titulo\":\"a>.ntitle\",\"fecha\":\"a>span>.ntime\",\"resumen\":\"a>.top-front-artices-items-text>.ndescription\",\"link\":\"a\"},"+
                "{\"url\":\"http://www.excelsior.com.mx/\",\"selector\":\"#top-post\",\"element\":\".side1\",\"titulo\":\"h1>a\",\"fecha\":\"Hoy\",\"resumen\":\".ndescription\",\"link\":\"h1>a\"},"+
                "{\"url\":\"http://www.excelsior.com.mx/nacional\",\"selector\":\"#taxonomy-term-principal\",\"element\":\".left\",\"titulo\":\"#taxonomy-term-principal-title>a\",\"fecha\":\"a>span>.ntime\",\"resumen\":\".ndescription\",\"link\":\"#taxonomy-term-principal-title>a\"},"+
                "{\"url\":\"http://www.excelsior.com.mx/nacional\",\"selector\":\".top-articles-grid-wrapper\",\"element\":\"li\",\"titulo\":\"a>.ntitle\",\"fecha\":\"a>.ndescription>.ntime\",\"resumen\":\"a>.ndescription\",\"link\":\"a\"},"+
                "{\"url\":\"http://www.excelsior.com.mx/global\",\"selector\":\"#taxonomy-term-principal\",\"element\":\".left\",\"titulo\":\"#taxonomy-term-principal-title>a\",\"fecha\":\"a>span>.ntime\",\"resumen\":\".ndescription\",\"link\":\"#taxonomy-term-principal-title>a\"},"+
                "{\"url\":\"http://www.excelsior.com.mx/global\",\"selector\":\".top-articles-grid-wrapper\",\"element\":\"li\",\"titulo\":\"a>.ntitle\",\"fecha\":\"a>.ndescription>.ntime\",\"resumen\":\"a>.ndescription\",\"link\":\"a\"},"+
                "{\"url\":\"http://www.excelsior.com.mx/funcion\",\"selector\":\"#taxonomy-term-principal\",\"element\":\".left\",\"titulo\":\"#taxonomy-term-principal-title>a\",\"fecha\":\"a>span>.ntime\",\"resumen\":\".ndescription\",\"link\":\"#taxonomy-term-principal-title>a\"},"+
                "{\"url\":\"http://www.excelsior.com.mx/funcion\",\"selector\":\".top-articles-grid-wrapper\",\"element\":\"li\",\"titulo\":\"a>.ntitle\",\"fecha\":\"a>.ndescription>.ntime\",\"resumen\":\"a>.ndescription\",\"link\":\"a\"},"+
                "{\"url\":\"http://www.excelsior.com.mx/expresiones\",\"selector\":\"#taxonomy-term-principal\",\"element\":\".left\",\"titulo\":\"#taxonomy-term-principal-title>a\",\"fecha\":\"a>span>.ntime\",\"resumen\":\".ndescription\",\"link\":\"#taxonomy-term-principal-title>a\"},"+
                "{\"url\":\"http://www.excelsior.com.mx/expresiones\",\"selector\":\".top-articles-grid-wrapper\",\"element\":\"li\",\"titulo\":\"a>.ntitle\",\"fecha\":\"a>.ndescription>.ntime\",\"resumen\":\"a>.ndescription\",\"link\":\"a\"},"+
                "{\"url\":\"http://www.excelsior.com.mx/hacker\",\"selector\":\"#taxonomy-term-principal\",\"element\":\".left\",\"titulo\":\"#taxonomy-term-principal-title>a\",\"fecha\":\"a>span>.ntime\",\"resumen\":\".ndescription\",\"link\":\"#taxonomy-term-principal-title>a\"},"+
                "{\"url\":\"http://www.excelsior.com.mx/hacker\",\"selector\":\".top-articles-grid-wrapper\",\"element\":\"li\",\"titulo\":\"a>.ntitle\",\"fecha\":\"a>.ndescription>.ntime\",\"resumen\":\"a>.ndescription\",\"link\":\"a\"},"+
                "{\"url\":\"http://www.excelsior.com.mx/dineroenimagen\",\"selector\":\".flujo-item-0\",\"element\":\".imx-flujo-3-body\",\"titulo\":\"a>span\",\"fecha\":\"Hoy\",\"resumen\":\"span\",\"link\":\"a\"},"+
                "{\"url\":\"http://www.excelsior.com.mx/dineroenimagen\",\"selector\":\"#topicos-wrapper\",\"element\":\"ul\",\"titulo\":\".items-top>.topico-item prelative topico-item-1>a>.topico-title\",\"fecha\":\"Hoy\",\"resumen\":\"a>.ndescription\",\"link\":\".items-top>.topico-item prelative topico-item-1>a\"}"+
                "]";

        Log.e("arreglo nuevo",secciones_gramaticas);

        List<item_Noticia> listaNot = new ArrayList<item_Noticia>();
        org.jsoup.nodes.Document doc;
        try {
            InputStream is= new URL(link).openStream();
            doc = org.jsoup.Jsoup.parse(is , "utf-8", link);
            //org.jsoup.nodes.Document doc = Jsoup.connect(link).get();
            try {
                JSONArray objetos_gramaticas = new JSONArray(secciones_gramaticas);
                for (int i = 0; i < objetos_gramaticas.length(); i++) {
                    JSONObject grammar = objetos_gramaticas.getJSONObject(i);
                    //Log.e("encontrada",grammar.getString("selector"));
                    if(grammar.getString("url").equals(link)){
                      //  Log.e("encontrada","una gramatica coincide");

                        Elements columna1 = doc.select(grammar.getString("selector"));
                        //Log.e("selector",grammar.getString("selector"));

                        String titulo,fecha,resumen,link1;

                        int donitera=0;
                        for (Element itera : columna1.select(grammar.getString("element"))) {

                            link1=itera.select(grammar.getString("link")).attr("href");
                            titulo=itera.select(grammar.getString("titulo")).text();
                            fecha=itera.select(grammar.getString("fecha")).text();
                            if(fecha.equals(""))
                            {
                                fecha="Hoy";
                            }
                            resumen=itera.select(grammar.getString("resumen")).text().substring(0,150)+"...";
                            listaNot.add(new item_Noticia(donitera, titulo,fecha,resumen,link1));
                            donitera++;
                        }

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return listaNot;

    }

    ////Saca Gramaticas y resumendenotas

    public static List<item_Noticia> SacaNotasResumen(String link, JSONObject grammar){

        //estas son las gramaticas que se usan las secciones del sitio (faltan algunas)
        //esto era lo que te decia, con un servicio php se pide una cadena con todas las gramaticas en json y se recontruyen facil
        //y en lugar de ponerlas aqui separarlas en parametros desde que se escoge la seccion para cargar la url

        List<item_Noticia> listaNot = new ArrayList<item_Noticia>();
        org.jsoup.nodes.Document doc;
        try {
            InputStream is= new URL(link).openStream();
            Log.e("Abriendo stream de",link);
            doc = org.jsoup.Jsoup.parse(is , "utf-8", link);
            //org.jsoup.nodes.Document doc = Jsoup.connect(link).get();
            try {
                        Elements columna1 = doc.select(grammar.getString("selector_gramatica"));
                        Log.i("Elements=", grammar.getString("selector_gramatica"));

                        String titulo,fecha,resumen,link1;
                        Log.i("Element Itera=",grammar.getString("elemento_gramatica"));
                        int donitera=0;
                        for (Element itera : columna1.select(grammar.getString("elemento_gramatica"))) {

                            link1=itera.select(grammar.getString("link")).attr("href");
                            titulo=itera.select(grammar.getString("titulo")).text();
                            if(!grammar.getString("fecha").equals("")){
                                fecha=itera.select(grammar.getString("fecha")).text();
                            }
                            else{
                                fecha="";
                            }

                            if(!grammar.getString("descripcion").equals("")){
                                resumen=itera.select(grammar.getString("descripcion")).text();
                                if(resumen.length()>150){//si es muy larga ka descripcion la corpamos
                                    resumen=resumen.substring(0,150)+"...";
                                }
                            }
                            else {
                                resumen="";
                            }
                            //solo agrega item si existe titulo y descripcion si no lo ignora y sigue con el siguiente elemento
                            if(titulo!=null&&titulo!=""&&titulo!=" "&&resumen!=""&&resumen!=null&&resumen!=" "){
                                listaNot.add(new item_Noticia(donitera, titulo,fecha,resumen,link1));
                            }
                            donitera++;
                        }
                Log.e("Trabajaremos con los siguientes gramaticas", grammar.getString("link") + "\n" +
                        grammar.getString("titulo") + "\n" +
                        grammar.getString("fecha") + "\n" +
                        grammar.getString("descripcion")+"\n"+
                        grammar.getString("imagen_completa"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return listaNot;

    }

    public static List<item_Noticia> SacaNotacompleta(String link, JSONObject grammar){
        List<item_Noticia> listaNot = new ArrayList<item_Noticia>();
        Log.i("variable list","iniciadndo");
        org.jsoup.nodes.Document doc;
        Log.i("variable doc","iniciadndo");

        try {
            InputStream is= new URL(link).openStream();
            Log.i("stream",link);
            doc = org.jsoup.Jsoup.parse(is , "utf-8", link);
            //sacamos selector de configuracion
            Elements columna1 = doc.select(grammar.getString("elemento_gramatica_completa"));
            Log.e("elemento" ,columna1.toString());
            String titulo,fecha,resumen="",img;
            int donitera=0;

            titulo=columna1.select(grammar.getString("titulo_completa")).text();
            Log.e("titulo" ,titulo);
            fecha=columna1.select(grammar.getString("fecha_completa")).text();
            Log.e("fecha" ,fecha);
            resumen=columna1.select(grammar.getString("cuerpo")).html();
            Log.e("resumen" ,resumen);
            img=columna1.select(grammar.getString("imagen_completa")).attr("src");
            Log.e("imagen" ,img);
            if(img.length()==0){
                listaNot.add(new item_Noticia(donitera, titulo,fecha,resumen,link));
            }else{
            Log.i("agregamos img a lista",img.toString());
            listaNot.add(new item_Noticia(donitera, titulo,fecha,resumen,link,img));
            Log.i("agregamos en lista not","iniciadndo");
            }


        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaNot;

    }



}
