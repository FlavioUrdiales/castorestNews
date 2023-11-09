package com.castores.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.castores.news.entity.Nota;
@Repository
public interface NotaRepository extends JpaRepository<Nota, Long>{

}
