package com.castores.news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castores.news.entity.Nota;
import com.castores.news.model.Noticias;
import com.castores.news.model.NoticiasComentario;
import com.castores.news.model.NoticiasComentarioRespuestas;
import com.castores.news.model.RequestComentario;
import com.castores.news.model.RequestNota;
import com.castores.news.model.RequestRespuesta;
import com.castores.news.service.NoticiasInterface;

@RestController
@RequestMapping("/Noticias")
public class NoticiasController {
	@Autowired
	NoticiasInterface noticiasService;
	
	@GetMapping()
	public List<Noticias> lstNotas(){
		return noticiasService.getAllNoticias();
	}
	
	@PostMapping("/comentario")
	public NoticiasComentario agregarComentario(@RequestBody RequestComentario comentario) {
		return noticiasService.addComentario(comentario);
	}
	
	@PostMapping("/respuesta")
	public NoticiasComentarioRespuestas agregarRespuesta(@RequestBody RequestRespuesta respuesta) {
		return noticiasService.addRespuesta(respuesta);
	}
	
	@PostMapping()
	public Noticias agregarNoticia(@RequestBody RequestNota nota) {
		return noticiasService.addNoticia(nota);
	}
	
}
