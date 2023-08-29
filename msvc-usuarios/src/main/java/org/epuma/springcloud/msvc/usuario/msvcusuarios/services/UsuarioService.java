package org.epuma.springcloud.msvc.usuario.msvcusuarios.services;

import org.epuma.springcloud.msvc.usuario.msvcusuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
 List<Usuario> listar();
 Optional<Usuario> porId(Long id);
 Usuario guardar(Usuario usuario);
 void eliminar(Long id);
 Optional<Usuario> porEmail(String email);
 boolean existePorEmail(String email);
 List<Usuario> listarPorId(Iterable<Long> ids);

}
