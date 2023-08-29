package com.epuma.springingcloud.msvc.cursos.services;

import com.epuma.springingcloud.msvc.cursos.models.Usuario;
import com.epuma.springingcloud.msvc.cursos.models.entity.Curso;

import java.util.Optional;

public interface CursoService {
    Iterable<Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar(Curso curso);
    void eliminar(Long id);
    Optional<Usuario> asignarUsuario(Usuario usuario,Long cursoId);
    Optional<Usuario> crearUsuario(Usuario usuario,Long cursoId);
    Optional<Usuario> eliminarUsuario(Usuario usuario,Long cursoId);
    Optional<Curso> porIdConUsuarios(Long id);
}
