package com.castores.news.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castores.news.entity.Comentario;
import com.castores.news.entity.Nota;
import com.castores.news.entity.Personal;
import com.castores.news.entity.Respuesta;
import com.castores.news.entity.Usuario;
import com.castores.news.model.Noticias;
import com.castores.news.model.NoticiasComentario;
import com.castores.news.model.NoticiasComentarioRespuestas;
import com.castores.news.model.RequestComentario;
import com.castores.news.model.RequestNota;
import com.castores.news.model.RequestRespuesta;
import com.castores.news.repository.ComentarioRepository;
import com.castores.news.repository.NotaRepository;
import com.castores.news.repository.PersonalRepository;
import com.castores.news.repository.RespuestaRepository;
import com.castores.news.repository.UsuarioRepository;

@Service
public class NoticiasService implements NoticiasInterface{
	@Autowired
	NotaRepository notaRepository;

	@Autowired
	ComentarioRepository comentarioRepository;

	@Autowired
	RespuestaRepository respuestaRepository;

	@Autowired
	PersonalRepository personalRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public List<Noticias> getAllNoticias() {
		List<Nota> lstNota = null;
		List<Noticias> lstNoticias = new ArrayList<>();
		
		try {
			lstNota = notaRepository.findAll();
			for(Nota nota : lstNota) {
				Noticias noticia = new Noticias();
				noticia.setIdentificador(nota.getIdNota());
				noticia.setNota(nota.getNota());
				noticia.setFechaPublicacion(dateToString(nota.getFechaHora()));
				noticia.setComentarios(getComentarios(nota.getIdNota()));
				noticia.setAutor(getAutor(nota.getIdpersonal()));
				lstNoticias.add(noticia);
			}
			return lstNoticias;
		}catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public Noticias findNoticias(Long idNota) {
		Nota nota = null;
		Noticias noticia = new Noticias();
		try {
			nota = notaRepository.findById(idNota).get();
			noticia.setIdentificador(nota.getIdNota());
			noticia.setNota(nota.getNota());
			noticia.setFechaPublicacion(dateToString(nota.getFechaHora()));
			noticia.setComentarios(getComentarios(nota.getIdNota()));
			noticia.setAutor(getAutor(nota.getIdpersonal()));
			return noticia;
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public Noticias addNoticia(RequestNota requestNota) {
		Nota nota = new Nota();
		Noticias noticia = new Noticias();
		nota.setNota(requestNota.getNota());
		nota.setFechaHora(new Date());
		nota.setIdpersonal(requestNota.getIdPersonal());
		notaRepository.save(nota);
		noticia.setIdentificador(nota.getIdNota());
		noticia.setNota(nota.getNota());
		noticia.setFechaPublicacion(dateToString(nota.getFechaHora()));
		noticia.setComentarios(getComentarios(nota.getIdNota()));
		noticia.setAutor(getAutor(nota.getIdpersonal()));
		return noticia;
	}

	@Override
	public NoticiasComentario addComentario(RequestComentario requestComentario) {
		Comentario comentario = new Comentario();
		NoticiasComentario noticiaComentario = new NoticiasComentario();
		comentario.setComentario(requestComentario.getComentario());
		comentario.setFechaHora(new Date());
		comentario.setIdnota(requestComentario.getIdNota());
		comentario.setIdUsuario(requestComentario.getIdUsuario());
		comentarioRepository.save(comentario);
		noticiaComentario.setIdentificador(comentario.getIdComentario());
		noticiaComentario.setComentario(comentario.getComentario());
		noticiaComentario.setFechaPublicacion(dateToString(comentario.getFechaHora()));
		noticiaComentario.setRespuestas(getRespuestas(comentario.getIdComentario()));
		noticiaComentario.setAutor(getAutorByIdUsuario(comentario.getIdUsuario()));
		return noticiaComentario;
	}

	@Override
	public NoticiasComentarioRespuestas addRespuesta(RequestRespuesta RequestRespuesta) {
		Respuesta respuesta = new Respuesta();
		NoticiasComentarioRespuestas noticiaComentarioRespuesta = new NoticiasComentarioRespuestas();
		respuesta.setRespuesta(RequestRespuesta.getRespuesta());
		respuesta.setFechaHora(new Date());
		respuesta.setIdComentario(RequestRespuesta.getIdComentario());
		respuesta.setIdUsuario(RequestRespuesta.getIdUsuario());
		respuestaRepository.save(respuesta);
		noticiaComentarioRespuesta.setIdentificador(respuesta.getIdRespuesta());
		noticiaComentarioRespuesta.setRespuesta(respuesta.getRespuesta());
		noticiaComentarioRespuesta.setFechaPublicacion(dateToString(respuesta.getFechaHora()));
		noticiaComentarioRespuesta.setAutor(getAutorByIdUsuario(respuesta.getIdUsuario()));
		return noticiaComentarioRespuesta;
	}

	private List<NoticiasComentarioRespuestas> getRespuestas(Long idComentario){
		List<Respuesta> lstRespuesta = null;
		List<NoticiasComentarioRespuestas> lstNoticiasComentarioRespuestas = new ArrayList<>();
		try {
			lstRespuesta = respuestaRepository.findByIdComentario(idComentario);
			for(Respuesta respuesta : lstRespuesta) {
				NoticiasComentarioRespuestas noticiaComentarioRespuesta = new NoticiasComentarioRespuestas();
				noticiaComentarioRespuesta.setIdentificador(respuesta.getIdRespuesta());
				noticiaComentarioRespuesta.setRespuesta(respuesta.getRespuesta());
				noticiaComentarioRespuesta.setFechaPublicacion(dateToString(respuesta.getFechaHora()));
				noticiaComentarioRespuesta.setAutor(getAutorByIdUsuario(respuesta.getIdUsuario()));
				lstNoticiasComentarioRespuestas.add(noticiaComentarioRespuesta);
			}
			return lstNoticiasComentarioRespuestas;
		}catch (Exception e) {
			return null;
		}
	}

	private List<NoticiasComentario> getComentarios(Long idNota){
		List<Comentario> lstComentario = null;
		List<NoticiasComentario> lstNoticiasComentario = new ArrayList<>();
		try {
			lstComentario = comentarioRepository.findByIdNota(idNota);
			for(Comentario comentario : lstComentario) {
				NoticiasComentario noticiaComentario = new NoticiasComentario();
				noticiaComentario.setIdentificador(comentario.getIdComentario());
				noticiaComentario.setComentario(comentario.getComentario());
				noticiaComentario.setFechaPublicacion(dateToString(comentario.getFechaHora()));
				noticiaComentario.setRespuestas(getRespuestas(comentario.getIdComentario()));
				noticiaComentario.setAutor(getAutorByIdUsuario(comentario.getIdUsuario()));
				lstNoticiasComentario.add(noticiaComentario);
			}
			return lstNoticiasComentario;
		}catch (Exception e) {
			return null;
		}
	}

	private String dateToString(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHoraString = formato.format(fecha);
        return fechaHoraString;
    }

	private String getAutorByIdUsuario(Long idUsuario) {
		Long idPersonal = personalRepository.findByIdUsuario(idUsuario).getIdPersonal();
		return getAutor(idPersonal);
	}

	private String getAutor(Long idPersonal) {
		Personal personal = personalRepository.findById(idPersonal).get();
		return personal.getNombre() + " " + personal.getApellidoPaterno() + " " + personal.getApellidoMaterno() + " [" + tipoUsuario(personal.getIdUsuario()) + "]";
	}
	
	public String tipoUsuario(long idUsuario) {
		String [] tipo = {"Externo","Interno"};
		Usuario usuario = usuarioRepository.findById(idUsuario).get();
		return tipo[usuario.getInternoExterno()];
	}

}
