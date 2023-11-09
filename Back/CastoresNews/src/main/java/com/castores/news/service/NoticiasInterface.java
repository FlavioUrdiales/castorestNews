package com.castores.news.service;

import java.util.List;

import com.castores.news.model.Noticias;
import com.castores.news.model.NoticiasComentario;
import com.castores.news.model.NoticiasComentarioRespuestas;
import com.castores.news.model.RequestComentario;
import com.castores.news.model.RequestNota;
import com.castores.news.model.RequestRespuesta;

public interface NoticiasInterface {
	public List<Noticias> getAllNoticias();
	public Noticias findNoticias(Long idNota);
	public Noticias addNoticia(RequestNota nota);
	public NoticiasComentario addComentario(RequestComentario comentario);
	public NoticiasComentarioRespuestas addRespuesta(RequestRespuesta respuesta);
}
