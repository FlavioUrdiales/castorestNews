package com.castores.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.castores.news.entity.Respuesta;
@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long>{
    public static final String FIND_BY_ID_COMENTARIO = "SELECT * FROM respuesta WHERE idcomentario = :idComentario";

    @Query(value = FIND_BY_ID_COMENTARIO, nativeQuery = true)
    public List<Respuesta> findByIdComentario(@Param("idComentario") Long idComentario);

}
