package com.prestamos.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prestamos.api.PrestamosException;
import com.prestamos.dao.CobradorDao;
import com.prestamos.model.Cobrador;
import com.prestamos.service.CobradorService;



@Service
public class CobradorServiceImpl implements CobradorService {

	@Autowired
	private CobradorDao cobradorDao;

	public void save(Cobrador cobrador) throws PrestamosException {
		cobradorDao.save(cobrador);
	}
	
	public void update(Cobrador cobrador) throws PrestamosException {
		if (cobrador.getCobradorId() == null) {
			throw new PrestamosException("El id es obligatorio");
		}
		cobradorDao.update(cobrador);
	}

	public void delete(Cobrador cobrador) throws PrestamosException {
		if (cobrador.getCobradorId() == null) {
			throw new PrestamosException("El documento es obligatorio");
		}
		cobradorDao.delete(cobrador);
	}

	public Cobrador getById(Long id) throws PrestamosException {
		if (id == null) {
			throw new PrestamosException("El convenioid es obligatorio");
		}
		return cobradorDao.getById(id);
	}

	public List<Cobrador> getByAll() throws PrestamosException {
		return cobradorDao.getByAll();
	}

	@Override
	public Cobrador getByName(String name) throws PrestamosException {
		if (name == null ||name.isEmpty()) {
			throw new PrestamosException("El nombre es obligatorio");
		}
		return cobradorDao.getByName(name);
	}

	@Override
	public List<Cobrador> getByFiltros(String nombre, String documento) throws PrestamosException {
		return cobradorDao.getByFiltros(nombre,documento);
	}
	
	
	public Cobrador getByDocumento(String documento) throws PrestamosException {
		if (documento == null) {
			throw new PrestamosException("El docuento es obligatorio");
		}
		return cobradorDao.getByDocumento(documento);
	}

	
}
