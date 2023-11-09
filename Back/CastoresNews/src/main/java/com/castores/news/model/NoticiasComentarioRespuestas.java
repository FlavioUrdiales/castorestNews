package com.castores.news.model;

public class NoticiasComentarioRespuestas {
	private Long identificador;
	private String respuesta;
	private String fechaPublicacion;
	private String autor;
	
	public NoticiasComentarioRespuestas() {

	}
	
	public NoticiasComentarioRespuestas(Long identificador, String respuesta, String fechaPublicacion, String autor) {
		this.identificador = identificador;
		this.respuesta = respuesta;
		this.fechaPublicacion = fechaPublicacion;
		this.autor = autor;
	}
	
	public Long getIdentificador() {
		return identificador;
	}
	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}

}
