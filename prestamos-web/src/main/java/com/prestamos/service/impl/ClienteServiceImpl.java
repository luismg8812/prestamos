package com.prestamos.service.impl;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prestamos.api.PrestamosException;
import com.prestamos.dao.ClienteDao;
import com.prestamos.model.Cliente;
import com.prestamos.service.ClienteService;



@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteDao clienteDao;

	public void save(Cliente cliente) throws PrestamosException {
		clienteDao.save(cliente);
	}
	
	public void update(Cliente cliente) throws PrestamosException {
		if (cliente.getClienteId() == null) {
			throw new PrestamosException("El id es obligatorio");
		}
		clienteDao.update(cliente);
	}

	public void delete(Cliente cliente) throws PrestamosException {
		if (cliente.getClienteId() == null) {
			throw new PrestamosException("El documento es obligatorio");
		}
		clienteDao.delete(cliente);
	}

	public Cliente getById(BigDecimal id) throws PrestamosException {
		if (id == null) {
			throw new PrestamosException("El convenioid es obligatorio");
		}
		return clienteDao.getById(id);
	}

	public List<Cliente> getByAll() throws PrestamosException {
		return clienteDao.getByAll();
	}

	@Override
	public Cliente getByName(String name) throws PrestamosException {
		if (name == null ||name.isEmpty()) {
			throw new PrestamosException("El nombre es obligatorio");
		}
		return clienteDao.getByName(name);
	}

	@Override
	public List<Cliente> getByFiltros(String nombre, String documento) throws PrestamosException {
		return clienteDao.getByFiltros(nombre,documento);
	}
	
	
	public Cliente getByDocumento(String documento) throws PrestamosException {
		if (documento == null) {
			throw new PrestamosException("El docuento es obligatorio");
		}
		return clienteDao.getByDocumento(documento);
	}

	
}
