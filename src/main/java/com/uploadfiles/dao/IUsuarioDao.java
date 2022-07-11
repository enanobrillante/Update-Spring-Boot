package com.uploadfiles.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uploadfiles.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario,Long> {

}
