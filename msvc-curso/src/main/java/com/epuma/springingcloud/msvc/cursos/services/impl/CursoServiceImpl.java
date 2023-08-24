package com.epuma.springingcloud.msvc.cursos.services.impl;

import com.epuma.springingcloud.msvc.cursos.entity.Curso;
import com.epuma.springingcloud.msvc.cursos.repositories.CursoRepository;
import com.epuma.springingcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class CursoServiceImpl implements CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return ((List<Curso>) cursoRepository.findAll());
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public void eliminar(Long id) {
  cursoRepository.deleteById(id);
    }
}
