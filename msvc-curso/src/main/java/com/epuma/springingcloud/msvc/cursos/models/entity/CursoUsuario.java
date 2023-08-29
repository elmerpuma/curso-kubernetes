package com.epuma.springingcloud.msvc.cursos.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "curso_Usuario")
public class CursoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "usuarioId",unique = true)
    private Long usuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if(!(obj instanceof CursoUsuario)){
            return false;
        }
        CursoUsuario o= (CursoUsuario) obj;
        return this.usuarioId!=null&&this.usuarioId.equals(o.usuarioId);
    }
}
