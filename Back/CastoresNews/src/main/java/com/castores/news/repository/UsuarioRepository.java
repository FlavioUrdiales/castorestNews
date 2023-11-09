package com.castores.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.castores.news.entity.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	public static final String FIND_BY_USUARIO_AND_PASSWORD = "SELECT * FROM usuario WHERE usuario = :usuario AND contrasenia = :contrasenia";
	@Query(value = FIND_BY_USUARIO_AND_PASSWORD, nativeQuery = true)
	Usuario findByUsuarioAndPassword(@Param("usuario") String usuario, @Param("contrasenia") String contrasenia);

}
