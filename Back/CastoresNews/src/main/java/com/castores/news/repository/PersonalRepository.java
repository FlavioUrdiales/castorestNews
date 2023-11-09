package com.castores.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.castores.news.entity.Personal;
@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long>{
    public static final String FIND_BY_ID_USUARIO = "SELECT * FROM personal WHERE idusuario = :idusuario";

    @Query(value = FIND_BY_ID_USUARIO, nativeQuery = true)
    public Personal findByIdUsuario(@Param("idusuario") Long idUsuario);
}
