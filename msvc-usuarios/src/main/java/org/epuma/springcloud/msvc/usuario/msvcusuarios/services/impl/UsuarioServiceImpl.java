package org.epuma.springcloud.msvc.usuario.msvcusuarios.services.impl;

import org.epuma.springcloud.msvc.usuario.msvcusuarios.models.entity.Usuario;
import org.epuma.springcloud.msvc.usuario.msvcusuarios.repositories.UsuarioRepository;
import org.epuma.springcloud.msvc.usuario.msvcusuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Transactional(readOnly = true)
    @Override

    public List<Usuario> listar() {
        return (List<Usuario>) usuarioRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> porId(Long id) {
        return usuarioRepository.findById(id);
    }
    @Transactional
    @Override
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    @Transactional
    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);

    }
}
