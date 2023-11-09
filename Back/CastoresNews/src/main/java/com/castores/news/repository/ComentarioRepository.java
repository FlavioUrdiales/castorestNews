package com.castores.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.castores.news.entity.Comentario;
@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
    public static final String FIND_BY_ID_NOTA = "SELECT * FROM comentario WHERE idnota = :idnota";

    @Query(value = FIND_BY_ID_NOTA, nativeQuery = true)
    public List<Comentario> findByIdNota(@Param("idnota") Long idNota);

}
