package com.notiallmx.notyall_mex.app.grammars;

/**
 * Created by lord on 15/01/2015.
 */
public class Completa {
    public int id;
    public int id_gramatica;
    public String elemento;
    public String titulo;
    public String fecha;
    public String link;
    public String image;
    String parrafo;

    Completa(int id, int id_gramatica, String elemento,String titulo,String fecha,String link,String image,String parrafo ){
        this.id=id;
        this.id_gramatica=id_gramatica;
        this.elemento=elemento;
        this.titulo=titulo;
        this.fecha=fecha;
        this.link=link;
        this.image=image;
        this.parrafo=parrafo;
    }
}
