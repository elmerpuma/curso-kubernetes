package com.epuma.springingcloud.msvc.cursos.services.impl;

import com.epuma.springingcloud.msvc.cursos.clients.UsuarioClientRest;
import com.epuma.springingcloud.msvc.cursos.models.Usuario;
import com.epuma.springingcloud.msvc.cursos.models.entity.Curso;
import com.epuma.springingcloud.msvc.cursos.models.entity.CursoUsuario;
import com.epuma.springingcloud.msvc.cursos.repositories.CursoRepository;
import com.epuma.springingcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private UsuarioClientRest usuarioClientRest;

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
    @Transactional
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    //metodos con relacion con microservicio usuario
//    @Override
//    @Transactional
//    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
//        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
//        if (cursoOptional.isPresent()) {
//            Usuario usuarioMsvc = usuarioClientRest.detalle(usuario.getId());
//            Curso curso = cursoOptional.get();
//            CursoUsuario cursoUsuario = new CursoUsuario();
//            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
//
//            curso.addCursoUsuarios(cursoUsuario);
//            cursoRepository.save(curso);
//            return Optional.of(usuarioMsvc);
//        }
//        return Optional.empty();
//    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            Usuario usuarioMsvc = usuarioClientRest.detalle(usuario.getId());

            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.addCursoUsuarios(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }
    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            Usuario usuarioNuevoMsvc = usuarioClientRest.crear(usuario);

            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

            curso.addCursoUsuarios(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioNuevoMsvc);
        }
        return Optional.empty();

    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            Usuario usuarioMsvc = usuarioClientRest.detalle(usuario.getId());

            Curso curso = cursoOptional.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso> cursoOptional=cursoRepository.findById(id);
        if(cursoOptional.isPresent()){
            Curso curso=cursoOptional.get();
            if(!curso.getCursoUsuarios().isEmpty()){
              List<Long>ids=curso.getCursoUsuarios().stream().map(cu->cu.getUsuarioId())
                      .collect(Collectors.toList());
              List<Usuario> usuarios=usuarioClientRest.obtenerAlumnosPorCurso(ids);
              curso.setUsuarios(usuarios);

            }
            return  Optional.of(curso);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
      cursoRepository.eliminarCursoUsuarioPorId(id);
    }
}
