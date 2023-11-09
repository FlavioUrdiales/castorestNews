package com.castores.news.model;

import java.util.List;

public class Noticias {
	private Long identificador;
	private String nota;
	private String fechaPublicacion;
	private String autor;
	private List<NoticiasComentario> comentarios;
	
	public Noticias() {
	}
	
	public Noticias(Long identificador, String nota, String fechaPublicacion, String autor,
			List<NoticiasComentario> comentarios) {
		this.identificador = identificador;
		this.nota = nota;
		this.fechaPublicacion = fechaPublicacion;
		this.autor = autor;
		this.comentarios = comentarios;
	}

	public Long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
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

	public List<NoticiasComentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<NoticiasComentario> comentarios) {
		this.comentarios = comentarios;
	}
	
}
