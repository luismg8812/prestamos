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
import com.prestamos.dao.CreditoDao;
import com.prestamos.model.Credito;


@Repository
public class CreditoDaoImpl extends HibernateDaoSupport implements CreditoDao {

	// private SessionFactory sessionFactory;

	@Autowired
	public CreditoDaoImpl(SessionFactory sessionFactory1) {
		super.setSessionFactory(sessionFactory1);
	}

	@Override
	@Transactional
	public void save(Credito cliente) throws PrestamosException {
		getHibernateTemplate().saveOrUpdate(cliente);
	}

	@Override
	@Transactional
	public void update(Credito cliente) throws PrestamosException {
		getHibernateTemplate().saveOrUpdate(cliente);

	}

	@Override
	@Transactional
	public void delete(Credito cliente) throws PrestamosException {
		getHibernateTemplate().delete(cliente);
	}

	@Override
	@Transactional
	public Credito getById(BigDecimal id) throws PrestamosException {
		final Credito entity = (Credito) getHibernateTemplate().load(Credito.class, id);
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Credito> getByAll() throws PrestamosException {
		List<Credito> documentoList = new ArrayList<>();
		DetachedCriteria detached = DetachedCriteria.forClass(Credito.class);
		detached.addOrder(org.hibernate.criterion.Order.asc("nombre"));
		documentoList = (List<Credito>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Credito> getByFiltros(Long clienteId,Long cobradorId, Date fechaInicio, Date fechaFin) throws PrestamosException {
		List<Credito> documentoList = new ArrayList<>();
		DetachedCriteria detached = DetachedCriteria.forClass(Credito.class);
		if(clienteId!=null){
			detached.add(Restrictions.eq("clienteId.clienteId", clienteId));
		}
		if(cobradorId!=null ){
			detached.add(Restrictions.eq("cobradorId.cobradorId", cobradorId));	
		}
		if(fechaInicio!=null ){
			detached.add(Restrictions.ge("fechaInicio", fechaInicio));	
		}
		if(fechaFin!=null ){
			detached.add(Restrictions.le("fechaFin", fechaFin));	
		}
		detached.addOrder(org.hibernate.criterion.Order.desc("fechaRegistro"));
		documentoList = (List<Credito>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Credito getByDocumento(String documento) throws PrestamosException {
		List<Credito> credito ;
		DetachedCriteria detached = DetachedCriteria.forClass(Credito.class);
		detached.add(Restrictions.eq("documento", documento));
		credito = (List<Credito>) getHibernateTemplate().findByCriteria(detached);
		return credito.isEmpty()?null:credito.get(0);
	}
	
}