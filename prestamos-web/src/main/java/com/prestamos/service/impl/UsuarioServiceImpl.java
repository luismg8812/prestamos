package com.prestamos.service.impl;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prestamos.api.PrestamosException;
import com.prestamos.dao.UsuarioDao;
import com.prestamos.model.Rol;
import com.prestamos.model.Usuario;
import com.prestamos.service.UsuarioService;



@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;

	public void save(Usuario usuario) throws PrestamosException {
		usuarioDao.save(usuario);
	}
	
	public void update(Usuario usuario) throws PrestamosException {
		if (usuario.getUsuarioId() == null) {
			throw new PrestamosException("El id es obligatorio");
		}
		usuarioDao.update(usuario);
	}

	public void delete(Usuario usuario) throws PrestamosException {
		if (usuario.getUsuarioId() == null) {
			throw new PrestamosException("El documento es obligatorio");
		}
		usuarioDao.delete(usuario);
	}

	public Usuario getById(BigDecimal id) throws PrestamosException {
		if (id == null) {
			throw new PrestamosException("El convenioid es obligatorio");
		}
		return usuarioDao.getById(id);
	}

	public List<Usuario> getByAll() throws PrestamosException {
		return usuarioDao.getByAll();
	}

	@Override
	public Usuario getByName(String name) throws PrestamosException {
		if (name == null ||name.isEmpty()) {
			throw new PrestamosException("El nombre es obligatorio");
		}
		return usuarioDao.getByName(name);
	}

	@Override
	public List<Usuario> getByFiltros(String nom, String documentoBusqueda) throws PrestamosException {
		return usuarioDao.getByFiltros(nom,documentoBusqueda);
	}

	@Override
	public Usuario getByIdentificacion(String identificacion) throws PrestamosException {
		return usuarioDao.getByIdentificacion(identificacion);
	}

	@Override
	public List<Rol> getByAllRoles() throws PrestamosException {
		return usuarioDao.getByAllRoles();
	}
	
	

	


	
	


	
}
