package com.castores.news.model;

public class RequestComentario {
	private String comentario;
	private Long idUsuario;
	private Long idNota;
	
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdNota() {
		return idNota;
	}
	public void setIdNota(Long idNota) {
		this.idNota = idNota;
	}
	
	
}
