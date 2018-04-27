package com.prestamos.service.impl;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prestamos.api.PrestamosException;
import com.prestamos.dao.OpcionUsuarioDao;
import com.prestamos.model.OpcionUsuario;
import com.prestamos.service.OpcionUsuarioService;



@Service
public class OpcionUsuarioServiceImpl implements OpcionUsuarioService {

	@Autowired
	private OpcionUsuarioDao opcionUsuarioDao;

	public void save(OpcionUsuario opcionUsuario) throws PrestamosException {
		opcionUsuarioDao.save(opcionUsuario);
	}
	
	public void update(OpcionUsuario opcionUsuario) throws PrestamosException {
		if (opcionUsuario.getOpcionUsuarioId() == null) {
			throw new PrestamosException("El id es obligatorio");
		}
		opcionUsuarioDao.update(opcionUsuario);
	}

	public void delete(OpcionUsuario opcionUsuario) throws PrestamosException {
		if (opcionUsuario.getOpcionUsuarioId() == null) {
			throw new PrestamosException("El documento es obligatorio");
		}
		opcionUsuarioDao.delete(opcionUsuario);
	}

	public OpcionUsuario getById(BigDecimal id) throws PrestamosException {
		if (id == null) {
			throw new PrestamosException("El convenioid es obligatorio");
		}
		return opcionUsuarioDao.getById(id);
	}

	public List<OpcionUsuario> getByAll() throws PrestamosException {
		return opcionUsuarioDao.getByAll();
	}

	@Override
	public OpcionUsuario getByName(String name) throws PrestamosException {
		if (name == null ||name.isEmpty()) {
			throw new PrestamosException("El nombre es obligatorio");
		}
		return opcionUsuarioDao.getByName(name);
	}

	@Override
	public List<OpcionUsuario> getByUsuario(Long usuarioId) throws PrestamosException {
		return opcionUsuarioDao.getByUsuario(usuarioId);
	}

	
	
	
	

	
}
