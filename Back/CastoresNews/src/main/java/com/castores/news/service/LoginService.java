package com.castores.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castores.news.entity.Personal;
import com.castores.news.entity.Usuario;
import com.castores.news.model.RequestUser;
import com.castores.news.model.User;
import com.castores.news.repository.PersonalRepository;
import com.castores.news.repository.UsuarioRepository;

@Service
public class LoginService implements LoginInterface{
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	PersonalRepository personalRepository;

	@Override
	public User login(RequestUser user) {
		Usuario usuario = usuarioRepository.findByUsuarioAndPassword(user.getUsuario(), user.getContrasenia());
		Personal personal = null;
		User userResponse = null;
		if(usuario == null) {
			return null;
		}
		userResponse = new User();
		personal = getPersonal(usuario.getIdUsuario());
		userResponse.setNombre(personal.getNombre());
		userResponse.setApellidoPaterno(personal.getApellidoPaterno());
		userResponse.setApellidoMaterno(personal.getApellidoMaterno());
		userResponse.setIdUsuario(usuario.getIdUsuario());
		userResponse.setIdPersonal(personal.getIdPersonal());
		if(usuario.getInternoExterno() == 1) {
			userResponse.setInterno(true);
		}else {
			userResponse.setInterno(false);
		}
		return userResponse;

	}
	private Personal getPersonal(Long idUsuario) {
		Personal personal = personalRepository.findByIdUsuario(idUsuario);
		return personal;
	}

}
