package com.prestamos.service.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prestamos.api.PrestamosException;
import com.prestamos.dao.CreditoDao;
import com.prestamos.model.Credito;
import com.prestamos.service.CreditoService;



@Service
public class CreditoServiceImpl implements CreditoService {

	@Autowired
	private CreditoDao creditoDao;

	public void save(Credito credito) throws PrestamosException {
		creditoDao.save(credito);
	}
	
	public void update(Credito credito) throws PrestamosException {
		if (credito.getCreditoId() == null) {
			throw new PrestamosException("El id es obligatorio");
		}
		creditoDao.update(credito);
	}

	public void delete(Credito credito) throws PrestamosException {
		if (credito.getClienteId() == null) {
			throw new PrestamosException("El documento es obligatorio");
		}
		creditoDao.delete(credito);
	}

	public Credito getById(BigDecimal id) throws PrestamosException {
		if (id == null) {
			throw new PrestamosException("El convenioid es obligatorio");
		}
		return creditoDao.getById(id);
	}

	public List<Credito> getByAll() throws PrestamosException {
		return creditoDao.getByAll();
	}

	

	@Override
	public List<Credito> getByFiltros(Long clienteId, Long cobradorId, Date fechaInicio, Date fechaFin) throws PrestamosException {
		return creditoDao.getByFiltros(clienteId,cobradorId,fechaInicio,fechaFin);
	}
	
	
	public Credito getByDocumento(String documento) throws PrestamosException {
		if (documento == null) {
			throw new PrestamosException("El docuento es obligatorio");
		}
		return creditoDao.getByDocumento(documento);
	}

	
}
