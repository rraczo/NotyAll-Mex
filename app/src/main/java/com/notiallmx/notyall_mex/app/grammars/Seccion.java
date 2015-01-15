package com.notiallmx.notyall_mex.app.grammars;

/**
 * Created by lord on 15/01/2015.
 */
public class Seccion{
    public int id;
    public int id_dominio;
    public String name;
    public String url;

    public Seccion(int id, int id_dominio, String url, String name){
        this.id=id;
        this.id_dominio=id_dominio;
        this.url=url;
        this.name=name;
    }
}
