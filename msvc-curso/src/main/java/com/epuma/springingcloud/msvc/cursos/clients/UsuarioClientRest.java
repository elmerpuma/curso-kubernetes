package com.epuma.springingcloud.msvc.cursos.clients;

import com.epuma.springingcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios",url="localhost:8001")
public interface UsuarioClientRest {
    @GetMapping("/{id}")
    public Usuario detalle(@PathVariable Long id);
    @PostMapping("/")
    public Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-por-curso")
    public List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);


}
