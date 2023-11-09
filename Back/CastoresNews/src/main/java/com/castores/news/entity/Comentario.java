package com.castores.news.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comentario")
public class Comentario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7332361084678046957L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcomentario")
	private Long idComentario;
	
	@Column(name = "comentario")
	private String comentario;
	
	@Column(name = "fechahora")
	private Date fechaHora;
	
	@Column(name = "idnota")
	private Long idnota;
	
	@Column(name = "idusuario")
	private Long idUsuario;

	public Long getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(Long idComentario) {
		this.idComentario = idComentario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Long getIdnota() {
		return idnota;
	}

	public void setIdnota(Long idnota) {
		this.idnota = idnota;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
