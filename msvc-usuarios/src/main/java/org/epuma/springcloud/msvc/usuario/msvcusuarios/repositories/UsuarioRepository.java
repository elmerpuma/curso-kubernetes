package org.epuma.springcloud.msvc.usuario.msvcusuarios.repositories;

import org.epuma.springcloud.msvc.usuario.msvcusuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario,Long> {
    Optional<Usuario> findByEmail(String email);

    //validacion con query
    @Query("select u from Usuario u where u.email=?1")
    Optional<Usuario> porEmail(String email);

    //por utlizando palabra clave
    boolean existsByEmail(String email);
}
