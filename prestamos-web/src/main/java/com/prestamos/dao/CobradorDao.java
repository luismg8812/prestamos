package com.prestamos.dao;



import java.util.List;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.Cobrador;



public interface CobradorDao  {

	void save(Cobrador cobrador) throws PrestamosException;

	void update(Cobrador cobrador) throws PrestamosException;

	void delete(Cobrador cobrador) throws PrestamosException;

	Cobrador getById(Long idMarca) throws PrestamosException;

	List<Cobrador> getByAll() throws PrestamosException;

	Cobrador getByName(String name)throws PrestamosException;

	List<Cobrador> getByFiltros(String nombre, String documento) throws PrestamosException;

	Cobrador getByDocumento(String documento)  throws PrestamosException;
}
