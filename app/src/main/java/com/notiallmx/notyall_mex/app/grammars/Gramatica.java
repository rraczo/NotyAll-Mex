package com.notiallmx.notyall_mex.app.grammars;

/**
 * Created by lord on 15/01/2015.
 */
public class Gramatica{
    public int id;
    public int id_seccion;
    public String selector;
    public String elemento;
    public String titulo;
    public String fecha;
    public String link;
    public String parrafo;

    Gramatica(int id, int id_seccion, String selector, String elemento, String titulo,String fecha,String link,String parrafo ){
        this.id=id;
        this.id_seccion=id_seccion;
        this.selector=selector;
        this.elemento=elemento;
        this.titulo=titulo;
        this.fecha=fecha;
        this.link=link;
        this.parrafo=parrafo;
    }
}
