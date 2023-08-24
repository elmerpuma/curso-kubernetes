package com.epuma.springingcloud.msvc.cursos.controllers;

import com.epuma.springingcloud.msvc.cursos.entity.Curso;
import com.epuma.springingcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CursoController {
  @Autowired
  private CursoService cursoService;
  @GetMapping
    public ResponseEntity<List<Curso>>  listar(){
      return ResponseEntity.ok((List<Curso>) cursoService.listar());
  }
  @GetMapping("/{id}")
    public ResponseEntity<Curso>  detalle(@PathVariable Long id){
      Optional<Curso> optionalCurso=cursoService.porId(id);
      if(optionalCurso.isPresent()){
          return ResponseEntity.ok(optionalCurso.get());
      }
      return  ResponseEntity.notFound().build();
  }
  @PostMapping("/")
    public ResponseEntity<?> crear(@RequestBody Curso curso){
      Curso cursodb=cursoService.guardar(curso);
      return  ResponseEntity.status(HttpStatus.CREATED).body(cursodb);

  }
  @PutMapping("/{id}")
    public  ResponseEntity<Curso> editar(@RequestBody Curso curso,@PathVariable Long id){
      Optional<Curso> cursoOptional=cursoService.porId(id);
      if(cursoOptional.isPresent()){
          Curso cursodb=cursoOptional.get();
          cursodb.setNombre(curso.getNombre());
          return  ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(cursodb));
      }
      return  ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
      Optional<Curso> cursoOptional=cursoService.porId(id);
      if(cursoOptional.isPresent()){
          cursoService.eliminar(cursoOptional.get().getId());
          return  ResponseEntity.noContent().build();
      }
      return  ResponseEntity.notFound().build();

  }
}
