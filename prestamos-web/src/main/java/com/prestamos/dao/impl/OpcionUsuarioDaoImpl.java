package com.prestamos.dao.impl;



import java.math.BigDecimal;
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
import com.prestamos.dao.OpcionUsuarioDao;
import com.prestamos.model.OpcionUsuario;


@Repository
public class OpcionUsuarioDaoImpl extends HibernateDaoSupport implements OpcionUsuarioDao {

	// private SessionFactory sessionFactory;

	@Autowired
	public OpcionUsuarioDaoImpl(SessionFactory sessionFactory1) {
		super.setSessionFactory(sessionFactory1);
	}

	@Override
	@Transactional
	public void save(OpcionUsuario opcionUsuario) throws PrestamosException {
		getHibernateTemplate().saveOrUpdate(opcionUsuario);
	}

	@Override
	@Transactional
	public void update(OpcionUsuario opcionUsuario) throws PrestamosException {
		getHibernateTemplate().saveOrUpdate(opcionUsuario);

	}

	@Override
	@Transactional
	public void delete(OpcionUsuario opcionUsuario) throws PrestamosException {
		getHibernateTemplate().delete(opcionUsuario);
	}

	@Override
	@Transactional
	public OpcionUsuario getById(BigDecimal id) throws PrestamosException {
		final OpcionUsuario entity = (OpcionUsuario) getHibernateTemplate().load(OpcionUsuario.class, id);
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<OpcionUsuario> getByAll() throws PrestamosException {
		List<OpcionUsuario> documentoList = new ArrayList<>();
		DetachedCriteria detached = DetachedCriteria.forClass(OpcionUsuario.class);
		detached.addOrder(org.hibernate.criterion.Order.asc("nombre"));
		documentoList = (List<OpcionUsuario>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OpcionUsuario getByName(String name) throws PrestamosException {
		List<OpcionUsuario> opcionUsuario ;
		DetachedCriteria detached = DetachedCriteria.forClass(OpcionUsuario.class);
		detached.add(Restrictions.eq("nombre", name));
		opcionUsuario = (List<OpcionUsuario>) getHibernateTemplate().findByCriteria(detached);
		return opcionUsuario.isEmpty()?null:opcionUsuario.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OpcionUsuario> getByUsuario(Long usuarioId) {
		List<OpcionUsuario> documentoList = new ArrayList<>();		
		DetachedCriteria detached = DetachedCriteria.forClass(OpcionUsuario.class);
		detached.add(Restrictions.eq("usuarioId.usuarioId", usuarioId));
		documentoList = (List<OpcionUsuario>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}

	
	
}