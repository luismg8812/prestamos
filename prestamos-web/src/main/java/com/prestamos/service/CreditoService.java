package com.prestamos.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.Credito;





public interface CreditoService {

	void save(Credito credito)throws PrestamosException;
	
	void update(Credito credito) throws PrestamosException;
	
	void delete(Credito credito) throws PrestamosException;
	
	List<Credito> getByAll() throws PrestamosException;

	Credito getById(BigDecimal id) throws PrestamosException;

	List<Credito> getByFiltros(Long clienteId, Long cobradorId,Date fechaInicion, Date fechaFin)  throws PrestamosException;

	Credito getByDocumento(String documento)  throws PrestamosException;

	
	
}
