package com.notiallmx.notyall_mex.app.objects;

public class item_Noticia {
	 protected long id;
	 private String titulo;
	 private String fecha;
	 private String resumen;
	 private String link;
	 private String foto;
	
	public item_Noticia(){
		 this.titulo="";
		 this.fecha="";
		 this.resumen="";
		 this.link="";
		 this.foto="";
	}
	
	
	public item_Noticia(long id, String titulo,String fecha,String resumen, String link){
		this.id = id; 
		this.titulo=titulo;
		 this.fecha=fecha;
		 this.resumen=resumen;
		 this.link=link;	 
	 }
	public item_Noticia(long id, String titulo,String fecha,String resumen, String link,String foto){
		this.id = id; 
		this.titulo=titulo;
		 this.fecha=fecha;
		 this.resumen=resumen;
		 this.link=link;
		 this.foto=foto;
	 }
	public String getLink() {return link;	}
	public void setLink(String link) {this.link = link;	}
	public String getResumen() {return resumen;	}
	public void setResumen(String resumen) {this.resumen = resumen;	}
	public String getFecha() {return fecha;	}
	public void setFecha(String fecha) {this.fecha = fecha;	}
	public String getTitulo() { return titulo;	}
	public void setTitulo(String titulo) {this.titulo= titulo;	}
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}


	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}
}
