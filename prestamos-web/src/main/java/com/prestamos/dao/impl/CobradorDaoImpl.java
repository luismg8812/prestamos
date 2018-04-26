package com.prestamos.dao.impl;



import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.prestamos.api.PrestamosException;
import com.prestamos.dao.CobradorDao;
import com.prestamos.model.Cobrador;


@Repository
public class CobradorDaoImpl extends HibernateDaoSupport implements CobradorDao {

	// private SessionFactory sessionFactory;

	@Autowired
	public CobradorDaoImpl(SessionFactory sessionFactory1) {
		super.setSessionFactory(sessionFactory1);
	}

	@Override
	@Transactional
	public void save(Cobrador cobrador) throws PrestamosException {
		getHibernateTemplate().saveOrUpdate(cobrador);
	}

	@Override
	@Transactional
	public void update(Cobrador cobrador) throws PrestamosException {
		getHibernateTemplate().saveOrUpdate(cobrador);

	}

	@Override
	@Transactional
	public void delete(Cobrador cobrador) throws PrestamosException {
		getHibernateTemplate().delete(cobrador);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Cobrador getById(Long id) throws PrestamosException {
		List<Cobrador> cobrador ;
		DetachedCriteria detached = DetachedCriteria.forClass(Cobrador.class);
		detached.add(Restrictions.eq("cobradorId", id));
		cobrador = (List<Cobrador>) getHibernateTemplate().findByCriteria(detached);
		return cobrador.isEmpty()?null:cobrador.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Cobrador> getByAll() throws PrestamosException {
		List<Cobrador> documentoList = new ArrayList<>();
		DetachedCriteria detached = DetachedCriteria.forClass(Cobrador.class);
		detached.addOrder(org.hibernate.criterion.Order.asc("nombre"));
		documentoList = (List<Cobrador>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Cobrador getByName(String name) throws PrestamosException {
		List<Cobrador> cobrador ;
		DetachedCriteria detached = DetachedCriteria.forClass(Cobrador.class);
		detached.add(Restrictions.eq("login", name));
		cobrador = (List<Cobrador>) getHibernateTemplate().findByCriteria(detached);
		return cobrador.isEmpty()?null:cobrador.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Cobrador> getByFiltros(String nombre,String documento) throws PrestamosException {
		List<Cobrador> documentoList = new ArrayList<>();
		DetachedCriteria detached = DetachedCriteria.forClass(Cobrador.class);
		if(nombre!=null && !nombre.isEmpty()){
			detached.add(Restrictions.eq("nombre", nombre));
		}
		if(documento!=null && !documento.isEmpty()){
			detached.add(Restrictions.eq("documento", documento));	
		}
		
		detached.addOrder(org.hibernate.criterion.Order.asc("nombre"));
		documentoList = (List<Cobrador>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Cobrador getByDocumento(String documento) throws PrestamosException {
		List<Cobrador> cliente ;
		DetachedCriteria detached = DetachedCriteria.forClass(Cobrador.class);
		detached.add(Restrictions.eq("documento", documento));
		cliente = (List<Cobrador>) getHibernateTemplate().findByCriteria(detached);
		return cliente.isEmpty()?null:cliente.get(0);
	}
	
}