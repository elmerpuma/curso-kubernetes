package com.epuma.springingcloud.msvc.cursos.controllers;

import com.epuma.springingcloud.msvc.cursos.models.Usuario;
import com.epuma.springingcloud.msvc.cursos.models.entity.Curso;
import com.epuma.springingcloud.msvc.cursos.services.CursoService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok((List<Curso>) cursoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> detalle(@PathVariable Long id) {
        Optional<Curso> optionalCurso = cursoService.porIdConUsuarios(id);//cursoService.porId(id);
        if (optionalCurso.isPresent()) {
            return ResponseEntity.ok(optionalCurso.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }

        Curso cursodb = cursoService.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursodb);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<Curso> cursoOptional = cursoService.porId(id);
        if (cursoOptional.isPresent()) {
            Curso cursodb = cursoOptional.get();
            cursodb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(cursodb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Curso> cursoOptional = cursoService.porId(id);
        if (cursoOptional.isPresent()) {
            cursoService.eliminar(cursoOptional.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }

    //enlace con microservicio usuario
    @PutMapping("asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> usuarioOptional;
        try {
            usuarioOptional = cursoService.asignarUsuario(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("mensaje", "No existe el usuario por id o error en la comunicacion" +
                            e.getMessage()));
        }
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> usuarioOptional;
        try {
            usuarioOptional = cursoService.crearUsuario(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("mensaje", "No se pudo crear el usuario oerror en la comunicacion" +
                            e.getMessage()));
        }
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> usuarioOptional;
        try {
            usuarioOptional = cursoService.eliminarUsuario(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("mensaje", "No existe el usuario por id o error en la comunicacion" +
                            e.getMessage()));
        }
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    //fin enlace con microservicio usuario
    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "el campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


}
