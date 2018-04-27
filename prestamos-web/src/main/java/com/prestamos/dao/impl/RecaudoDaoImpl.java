package com.prestamos.dao.impl;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.prestamos.api.PrestamosException;
import com.prestamos.dao.RecaudoDao;
import com.prestamos.model.Recaudo;


@Repository
public class RecaudoDaoImpl extends HibernateDaoSupport implements RecaudoDao {

	// private SessionFactory sessionFactory;

	@Autowired
	public RecaudoDaoImpl(SessionFactory sessionFactory1) {
		super.setSessionFactory(sessionFactory1);
	}

	@Override
	@Transactional
	public void save(Recaudo recaudo) throws PrestamosException {
		getHibernateTemplate().saveOrUpdate(recaudo);
	}

	@Override
	@Transactional
	public void update(Recaudo recaudo) throws PrestamosException {
		getHibernateTemplate().saveOrUpdate(recaudo);

	}

	@Override
	@Transactional
	public void delete(Recaudo recaudo) throws PrestamosException {
		getHibernateTemplate().delete(recaudo);
	}

	@Override
	@Transactional
	public Recaudo getById(BigDecimal id) throws PrestamosException {
		final Recaudo entity = (Recaudo) getHibernateTemplate().load(Recaudo.class, id);
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Recaudo> getByAll() throws PrestamosException {
		List<Recaudo> documentoList = new ArrayList<>();
		DetachedCriteria detached = DetachedCriteria.forClass(Recaudo.class);
		detached.addOrder(org.hibernate.criterion.Order.asc("nombre"));
		documentoList = (List<Recaudo>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recaudo> getByFechas(Date fechaInicio, Date fechaFin,Long creditoId) throws PrestamosException {
		List<Recaudo> documentoList = new ArrayList<>();		
		DetachedCriteria detached = DetachedCriteria.forClass(Recaudo.class);
		detached.add(Restrictions.ge("fechaRecaudo", fechaInicio));
		detached.add(Restrictions.le("fechaRecaudo", fechaFin));
		if(creditoId!=null){
			detached.add(Restrictions.eq("creditoId.creditoId", creditoId));
		}
		documentoList = (List<Recaudo>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recaudo> getByCredito(Long creditoId) throws PrestamosException {
		List<Recaudo> documentoList = new ArrayList<>();		
		DetachedCriteria detached = DetachedCriteria.forClass(Recaudo.class);
		detached.add(Restrictions.eq("creditoId.creditoId", creditoId));
		documentoList = (List<Recaudo>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recaudo> getByCredito(Long clienteBusqueda, Long cobradorBusqueda, Date fechaInicioBusqueda,
			Date fechaFinBusqueda) throws PrestamosException {
		List<Recaudo> documentoList ;		
		DetachedCriteria detached = DetachedCriteria.forClass(Recaudo.class);
		if(fechaInicioBusqueda!=null){
			detached.add(Restrictions.ge("fechaRecaudo", fechaInicioBusqueda));
		}
		if(fechaFinBusqueda!=null){
			detached.add(Restrictions.le("fechaRecaudo", fechaFinBusqueda));
		}	
		if(clienteBusqueda!=null){
			detached.add(Restrictions.eq("creditoId.clienteId.clienteId", clienteBusqueda));
		}
		if(cobradorBusqueda!=null){
			detached.add(Restrictions.eq("creditoId.cobradorId.cobradorId", cobradorBusqueda));
		}
		documentoList = (List<Recaudo>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recaudo> getByFechasFechasAndCobrador(Date inicio, Date fin, Long cobradorBusqueda)
			throws PrestamosException {
		List<Recaudo> documentoList ;		
		DetachedCriteria detached = DetachedCriteria.forClass(Recaudo.class);
		if(inicio!=null){
			detached.add(Restrictions.ge("fechaRecaudo", inicio));
		}
		if(fin!=null){
			detached.add(Restrictions.le("fechaRecaudo", fin));
		}	
		//if(cobradorBusqueda!=null){
		//	detached.add(Restrictions.eq("creditoId.cobradorId.cobradorId", cobradorBusqueda));
		//}	
		documentoList = (List<Recaudo>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}

	
	
}