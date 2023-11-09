package com.castores.news.model;

import java.util.List;

public class NoticiasComentario {
	private Long identificador;
	private String comentario;
	private String fechaPublicacion;
	private String autor;
	private List<NoticiasComentarioRespuestas> respuestas;
	
	public NoticiasComentario() {
	}

	public NoticiasComentario(Long identificador, String comentario, String fechaPublicacion, String autor,
			List<NoticiasComentarioRespuestas> respuestas) {
		this.identificador = identificador;
		this.comentario = comentario;
		this.fechaPublicacion = fechaPublicacion;
		this.autor = autor;
		this.respuestas = respuestas;
	}

	public Long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
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

	public List<NoticiasComentarioRespuestas> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<NoticiasComentarioRespuestas> respuestas) {
		this.respuestas = respuestas;
	}

}
