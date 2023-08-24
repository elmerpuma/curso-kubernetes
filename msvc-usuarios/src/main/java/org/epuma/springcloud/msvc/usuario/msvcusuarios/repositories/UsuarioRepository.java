package org.epuma.springcloud.msvc.usuario.msvcusuarios.repositories;

import org.epuma.springcloud.msvc.usuario.msvcusuarios.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario,Long> {

}
