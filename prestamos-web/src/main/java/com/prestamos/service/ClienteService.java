package com.prestamos.service;

import java.math.BigDecimal;
import java.util.List;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.Cliente;





public interface ClienteService {

	void save(Cliente cliente)throws PrestamosException;
	
	void update(Cliente cliente) throws PrestamosException;
	
	void delete(Cliente cliente) throws PrestamosException;
	
	List<Cliente> getByAll() throws PrestamosException;

	Cliente getById(BigDecimal id) throws PrestamosException;

	Cliente getByName(String name) throws PrestamosException;

	List<Cliente> getByFiltros(String nombre, String documento)  throws PrestamosException;

	Cliente getByDocumento(String documento)  throws PrestamosException;

	
	
}
