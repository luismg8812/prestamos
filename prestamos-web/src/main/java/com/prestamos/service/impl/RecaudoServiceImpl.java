package com.prestamos.service.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prestamos.api.PrestamosException;
import com.prestamos.dao.RecaudoDao;
import com.prestamos.model.Recaudo;
import com.prestamos.service.RecaudoService;



@Service
public class RecaudoServiceImpl implements RecaudoService {

	@Autowired
	private RecaudoDao recaudoDao;

	public void save(Recaudo recaudo) throws PrestamosException {
		recaudoDao.save(recaudo);
	}
	
	public void update(Recaudo recaudo) throws PrestamosException {
		if (recaudo.getRecaudoId() == null) {
			throw new PrestamosException("El id es obligatorio");
		}
		recaudoDao.update(recaudo);
	}

	public void delete(Recaudo recaudo) throws PrestamosException {
		if (recaudo.getRecaudoId() == null) {
			throw new PrestamosException("El documento es obligatorio");
		}
		recaudoDao.delete(recaudo);
	}

	public Recaudo getById(BigDecimal id) throws PrestamosException {
		if (id == null) {
			throw new PrestamosException("El convenioid es obligatorio");
		}
		return recaudoDao.getById(id);
	}

	public List<Recaudo> getByAll() throws PrestamosException {
		return recaudoDao.getByAll();
	}

	@Override
	public List<Recaudo> getByFechas(Date fechaInicio, Date  fechaFin,Long creditoId) throws PrestamosException {
		return recaudoDao.getByFechas(fechaInicio, fechaFin,creditoId);
	}

	@Override
	public List<Recaudo> getByCredito(Long creditoId) throws PrestamosException {
		return recaudoDao.getByCredito(creditoId);
	}

	@Override
	public List<Recaudo> getByFiltros(Long clienteBusqueda, Long cobradorBusqueda, Date fechaInicioBusqueda,
			Date fechaFinBusqueda) {
		return recaudoDao.getByCredito(clienteBusqueda,cobradorBusqueda,fechaInicioBusqueda,fechaFinBusqueda);
	}

	@Override
	public List<Recaudo> getByFechasFechasAndCobrador(Date inicio, Date fin, Long cobradorBusqueda) throws PrestamosException {
		return recaudoDao.getByFechasFechasAndCobrador(inicio,fin,cobradorBusqueda);
	}	
}
