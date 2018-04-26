package com.prestamos.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.Recaudo;





public interface RecaudoService {

	void save(Recaudo cliente)throws PrestamosException;
	
	void update(Recaudo cliente) throws PrestamosException;
	
	void delete(Recaudo cliente) throws PrestamosException;
	
	List<Recaudo> getByAll() throws PrestamosException;

	Recaudo getById(BigDecimal id) throws PrestamosException;

	List<Recaudo> getByFechas(Date fechaInicio, Date  fechaFin,Long creditoId) throws PrestamosException;

	List<Recaudo> getByCredito(Long creditoId)throws PrestamosException;

	List<Recaudo> getByFiltros(Long clienteBusqueda, Long cobradorBusqueda, Date fechaInicioBusqueda,
			Date fechaFinBusqueda);

	List<Recaudo> getByFechasFechasAndCobrador(Date inicio, Date fin, Long cobradorBusqueda)throws PrestamosException;
	
}
