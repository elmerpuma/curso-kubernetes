package com.epuma.springingcloud.msvc.cursos.services;

import com.epuma.springingcloud.msvc.cursos.entity.Curso;

import java.util.Optional;

public interface CursoService {
    Iterable<Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar(Curso curso);
    void eliminar(Long id);
}
