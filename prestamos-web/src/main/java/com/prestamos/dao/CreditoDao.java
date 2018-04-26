package com.prestamos.dao;



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.Cliente;
import com.prestamos.model.Credito;



public interface CreditoDao  {

	void save(Credito credito) throws PrestamosException;

	void update(Credito credito) throws PrestamosException;

	void delete(Credito credito) throws PrestamosException;

	Credito getById(BigDecimal idMarca) throws PrestamosException;

	List<Credito> getByAll() throws PrestamosException;

	List<Credito> getByFiltros(Long clienteId, Long cobradorId, Date fechaInicion, Date fechaFin) throws PrestamosException;

	Credito getByDocumento(String documento)  throws PrestamosException;
}
