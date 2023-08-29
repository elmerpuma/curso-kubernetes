package com.epuma.springingcloud.msvc.cursos.repositories;

import com.epuma.springingcloud.msvc.cursos.models.entity.Curso;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CursoRepository extends CrudRepository<Curso,Long> {

}
