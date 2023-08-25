package com.epuma.springingcloud.msvc.cursos.controllers;

import com.epuma.springingcloud.msvc.cursos.entity.Curso;
import com.epuma.springingcloud.msvc.cursos.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> crear(@Valid  @RequestBody Curso curso,BindingResult result){
    if(result.hasErrors()) {
      return validar(result);
    }

      Curso cursodb=cursoService.guardar(curso);
      return  ResponseEntity.status(HttpStatus.CREATED).body(cursodb);

  }
  @PutMapping("/{id}")
    public  ResponseEntity<?> editar(@Valid @RequestBody Curso curso,BindingResult result, @PathVariable Long id){
    if(result.hasErrors()) {
      return validar(result);
    }
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
  private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
    Map<String, String> errores= new HashMap<>();
    result.getFieldErrors().forEach(err->{
      errores.put(err.getField(), "el campo "+err.getField()+" "+err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errores);
  }
}
