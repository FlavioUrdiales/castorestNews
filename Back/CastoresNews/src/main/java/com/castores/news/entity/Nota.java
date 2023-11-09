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
@Table(name = "nota")
public class Nota implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1216010625410163963L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idnota")
	private Long idNota;
	
	@Column(name = "nota")
	private String nota;
	
	@Column(name = "fechahora")
	private Date fechaHora;
	
	@Column(name = "idpersonal")
	private Long idpersonal;

	public Long getIdNota() {
		return idNota;
	}

	public void setIdNota(Long idNota) {
		this.idNota = idNota;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Long getIdpersonal() {
		return idpersonal;
	}

	public void setIdpersonal(Long idpersonal) {
		this.idpersonal = idpersonal;
	}
	
}
