package com.prestamos.dao;



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.Recaudo;



public interface RecaudoDao  {

	void save(Recaudo recaudo) throws PrestamosException;

	void update(Recaudo recaudo) throws PrestamosException;

	void delete(Recaudo recaudo) throws PrestamosException;

	Recaudo getById(BigDecimal idMarca) throws PrestamosException;

	List<Recaudo> getByAll() throws PrestamosException;

	List<Recaudo> getByFechas(Date fechaInicio, Date fechaFin,Long creditoId)throws PrestamosException;

	List<Recaudo> getByCredito(Long creditoId)throws PrestamosException;

	List<Recaudo> getByCredito(Long clienteBusqueda, Long cobradorBusqueda, Date fechaInicioBusqueda,
			Date fechaFinBusqueda)throws PrestamosException;

	List<Recaudo> getByFechasFechasAndCobrador(Date inicio, Date fin, Long cobradorBusqueda)throws PrestamosException;

}
